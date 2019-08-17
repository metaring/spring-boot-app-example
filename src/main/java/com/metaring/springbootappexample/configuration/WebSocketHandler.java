package com.metaring.springbootappexample.configuration;

import org.json.JSONObject;
import org.springframework.web.socket.BinaryMessage;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.AbstractWebSocketHandler;

import java.io.IOException;

public class WebSocketHandler extends AbstractWebSocketHandler {
    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws IOException {
        System.out.println("New Text Message Received " + session.getId());
        JSONObject jsonObject = new JSONObject(message.getPayload());
        session.sendMessage(new TextMessage("Hello " + jsonObject.get("firstName") + " " + jsonObject.get("lastName") + " " + "with session id" + " " + session.getId()));
    }

    @Override
    protected void handleBinaryMessage(WebSocketSession session, BinaryMessage message) throws IOException {
        System.out.println("New Binary Message Received " + session.getId());
        session.sendMessage(message);
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        System.out.println("New Binary Message Received " + session.getId());
    }
}
