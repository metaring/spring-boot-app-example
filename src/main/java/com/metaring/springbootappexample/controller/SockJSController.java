/**
 * Copyright 2019 MetaRing s.r.l.
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.metaring.springbootappexample.controller;

import com.metaring.springbootappexample.service.PersonService;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.AbstractWebSocketHandler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

@Component
public class SockJSController extends AbstractWebSocketHandler implements DisposableBean {

    private static final List<SockJSController> CONTROLLERS = new ArrayList<>();

    private final Map<String, WebSocketSession> sessions = new HashMap<>();

    private static final void newController(SockJSController controller) {
        synchronized (CONTROLLERS) {
            CONTROLLERS.add(controller);
        }
    }

    private static final void controllerDisposed(SockJSController controller) {
        synchronized (CONTROLLERS) {
            CONTROLLERS.remove(controller);
            if(CONTROLLERS.isEmpty()) {
                controller.sessions.values().forEach(it -> {
                    try {
                        it.close(CloseStatus.GOING_AWAY);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });
                return;
            }
            SockJSController lessLoaded = CONTROLLERS.stream().sorted((a, b) -> a.sessions.size() >= b.sessions.size() ? 1 : 0).findFirst().get();
            controller.sessions.forEach((key, value) -> lessLoaded.sessions.put(key, value));
        }
    }

    private static final void forAllControllers(final Consumer<WebSocketSession> action) {
        synchronized (CONTROLLERS) {
            CONTROLLERS.forEach(controller -> controller.sessions.values().forEach(action::accept));
        }
    }

    private static final void forAllControllers(final BiConsumer<WebSocketSession, SockJSController> action) {
        synchronized (CONTROLLERS) {
            CONTROLLERS.forEach(controller -> controller.sessions.values().forEach(session -> action.accept(session, controller)));
        }
    }

    public static final void broadcast(final String message) {
        final TextMessage txt = new TextMessage(message);
        final Consumer<WebSocketSession> action = session -> {
            try {
                session.sendMessage(txt);
            } catch (Exception e) {
                try {
                    session.close(CloseStatus.BAD_DATA);
                } catch (Exception e1) {
                }
            }
        };
        forAllControllers(action);
    }

    private Executor asyncExecutor;

    private PersonService personService;

    public SockJSController(Executor asyncExecutor, PersonService personService) {
        super();
        this.asyncExecutor = asyncExecutor;
        this.personService = personService;
        newController(this);
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        sessions.put(session.getId(), session);
        System.out.println("New Connection: " + session.getId());
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        sessions.remove(session.getId());
        System.out.println("Connection Lost: " + session.getId());
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws IOException {
        System.out.println("New Text Message Received from " + session.getId());
        onMessage(message.getPayload(), session);
    }

    private final CompletableFuture<Void> onMessage(final String m, final WebSocketSession session) {
        return CompletableFuture.runAsync(() -> {
            String[] message = m.split("=");
            if(message[0].equalsIgnoreCase("message")) {
                broadcast(message[1]);
                return;
            }
            try {
                personService.getPersonsByLastName(message[1]).whenCompleteAsync((result, exception) -> {
                    String response = "{}";
                    if(exception != null) {
                        exception.printStackTrace();
                    }
                    if(result != null && !result.isEmpty() && result.get(0) != null) {
                        response = result.get(0).toString();
                    }
                    try {
                        session.sendMessage(new TextMessage(response));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }, asyncExecutor);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }, asyncExecutor);
    }

    @Override
    public void destroy() throws Exception {
        controllerDisposed(this);
    }
}