package com.chan.demo.socket

import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.internal.synchronized
import org.springframework.web.socket.TextMessage
import org.springframework.web.socket.WebSocketSession
import org.springframework.web.socket.handler.TextWebSocketHandler
import java.util.concurrent.CopyOnWriteArraySet

class SocketHandler : TextWebSocketHandler() {

    companion object {
        // 모든 WebSocket 세션을 저장
        private val sessions: MutableSet<WebSocketSession> = CopyOnWriteArraySet()
    }

    override fun afterConnectionEstablished(session: WebSocketSession) {
        sessions.add(session)
        System.out.println("Connect!")
        session.sendMessage(TextMessage("Hello, Connector."))

        if (sessions.size % 50 == 0) {
            println("websocket connection count= ${sessions.size}")
        }
    }

    override fun afterConnectionClosed(session: WebSocketSession, status: org.springframework.web.socket.CloseStatus) {
        sessions.remove(session)
    }

    // 모든 연결된 세션에 메시지 전송
    @OptIn(InternalCoroutinesApi::class)
    fun broadcastMessage(message: String) {
        sessions.forEach { session ->
            if (session.isOpen) {
                synchronized(session) {
                    println("Send : $message Session : ${session.id}")
                    session.sendMessage(TextMessage(message))
                }
            } else {
                println("Session ${session.id} closed.")
            }
        }
    }
}
