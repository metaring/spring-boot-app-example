/**
 *    Copyright 2019 MetaRing s.r.l.
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

package com.metaring.springbootappexample.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

import com.metaring.springbootappexample.controller.SockJSController;

@Configuration
@EnableWebSocket
class SockJSConfiguration implements WebSocketConfigurer {

    private final SockJSController sockJSController;

    public SockJSConfiguration(SockJSController sockJSController) {
        this.sockJSController = sockJSController;
    }

    @Override
    public final void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(sockJSController, "/ws-p2p").setAllowedOrigins("*").withSockJS();
    }
}