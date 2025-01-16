package com.chan.demo

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class testController {

    @GetMapping("/test")
    fun TESTController(): Int {
        System.out.println("input");
        return 999;
    }
}