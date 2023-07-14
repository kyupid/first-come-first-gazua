package com.example.firstcomefirstgazua.web.handler;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

@Component
public class WebSocketHandler extends TextWebSocketHandler {

    private Queue<WebSocketSession> sessions = new ConcurrentLinkedQueue<>();
    private static final String REDIRECT_URL = "https://www.google.com";

    @Override
    public void afterConnectionEstablished(WebSocketSession session) {
        sessions.add(session);
    }

    @Scheduled(fixedRate = 100)
    public void sendMessageToClients() throws IOException {
        if (!sessions.isEmpty()) {
            WebSocketSession session = sessions.poll();
            if (session != null && session.isOpen()) {
                session.sendMessage(new TextMessage(REDIRECT_URL));
            }
        }
    }
}
