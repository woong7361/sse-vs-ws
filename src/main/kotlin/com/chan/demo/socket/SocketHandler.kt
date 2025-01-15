package com.chan.demo.socket

import org.springframework.web.socket.TextMessage
import org.springframework.web.socket.WebSocketSession
import org.springframework.web.socket.handler.TextWebSocketHandler

class SocketHandler : TextWebSocketHandler() {
    override fun afterConnectionEstablished(session: WebSocketSession) {
        session.sendMessage(TextMessage("0,Hello"))
    }
}
