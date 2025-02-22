package com.chan.demo.sse

import lombok.Getter
import lombok.extern.slf4j.Slf4j
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter
import java.util.concurrent.CopyOnWriteArrayList

@RestController
class SSEController {

    private val emitters = CopyOnWriteArrayList<SseEmitter>()

    @GetMapping("/sse")
    fun connect(): SseEmitter {
        System.out.println("Connect.")
        val emitter = SseEmitter(1000000)
        emitters.add(emitter)
        emitter.onCompletion { emitters.remove(emitter) }
        emitter.onTimeout { emitters.remove(emitter) }

        if (emitters.size % 30 ==  0) {
            println("connection count = ${emitters.size}")
        }

        return emitter
    }

    @GetMapping("/sse/send")
    fun broadcast(): String {
        System.out.println("브로드캐스트 시작")
        emitters.forEach {
            try {
                it.send("0,Hello")
                println("send complete")
            } catch (e: Exception) {
                emitters.remove(it)
                System.out.println("에러 발생: ${e.message}")
            }
        }
        println("emitters.size = ${emitters.size}")
        return "Send."
    }

}
