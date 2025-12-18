package com.example.webfluxstudy

import org.springframework.boot.test.context.SpringBootTest
import reactor.core.publisher.Flux
import kotlin.test.Test

@SpringBootTest
class SubscriberPublisherAsyncTest {

    @Test
    fun produceOneToNineFlux() {
        val intFlux = Flux.create{ sink ->
            for(i in 1..9) {
                Thread.sleep(500)
                sink.next(i)
            }
            sink.complete()
        }

        intFlux.subscribe{data ->
            println(data)
        }
        println("Netty 이벤트 루프로 스레드 복귀")
    }
}