package com.chan.demo.socket

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class SocketController {

    private val socketHandler = SocketHandler()

    @GetMapping("/socket/send")
    fun broadcastMessage(): String {
        socketHandler.broadcastMessage("0, HI")
        return "Broadcasted '0, HI'"
    }
}
