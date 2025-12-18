package com.example.webfluxstudy.controller

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Flux

@RestController
@RequestMapping("/reactive")
class ReactiveProgrammingExampleController {

    @GetMapping("onenine/list")
    fun produceOneToNine(): List<Int> {
        val sink: MutableList<Int> = mutableListOf()
        for(i in 1..9) {
            Thread.sleep(500)
            sink.add(i)
        }

        return sink
    }

    // 리액티브 스트림 구현체 Flux, Mono를 사용하여 데이터를 리액티브하게 처리
    @GetMapping("onenine/flux")
    fun produceOneToNineFlux(): Flux<Int> {
        return Flux.create{ sink ->
            for(i in 1..9) {
                Thread.sleep(500) // 리액티브는 비동기로 동작해야하며, 반드시 논 블로킹하게 동작해야 한다.
                sink.next(i)
            }
            sink.complete()
        }
    }
}
