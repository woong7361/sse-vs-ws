package com.chan.demo.sse

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter
import java.util.concurrent.CopyOnWriteArrayList

@RestController
class SSEController {

    private val emitters = CopyOnWriteArrayList<SseEmitter>()

    @GetMapping("/sse")
    fun connect(): SseEmitter {
        System.out.println("Connect.")
        val emitter = SseEmitter()
        emitters.add(emitter)
        emitter.onCompletion { emitters.remove(emitter) }
        emitter.onTimeout { emitters.remove(emitter) }
        return emitter
    }

    @PostMapping("/sse/send")
    fun broadcast(): String {
        System.out.println("브로드캐스트 시작")
        emitters.forEach {
            try {
                it.send("0,Hello")
                System.out.println("메시지 전송 완료")
            } catch (e: Exception) {
                emitters.remove(it)
                System.out.println("에러 발생: ${e.message}")
            }
        }
        return "Send."
    }

}
