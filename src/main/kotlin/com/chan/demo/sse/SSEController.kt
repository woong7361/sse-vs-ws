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
        val emitter = SseEmitter()
        emitters.add(emitter)
        emitter.onCompletion { emitters.remove(emitter) }
        emitter.onTimeout { emitters.remove(emitter) }
        return emitter
    }

    @PostMapping("/sse/broadcast")
    fun broadcast(): String {
        emitters.forEach {
            try {
                it.send("0,Hello")
            } catch (e: Exception) {
                emitters.remove(it)
            }
        }
        return "Message sent to all SSE clients."
    }
}
