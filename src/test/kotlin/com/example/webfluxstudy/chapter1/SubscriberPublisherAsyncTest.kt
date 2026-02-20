package com.example.webfluxstudy.chapter1

import org.springframework.boot.test.context.SpringBootTest
import reactor.core.publisher.Flux
import reactor.core.scheduler.Schedulers
import kotlin.test.Test

@SpringBootTest
class SubscriberPublisherAsyncTest {

    @Test
    fun produceOneToNineFlux1() {
        val intFlux = Flux.create{ sink ->
            for(i in 1..9) {
                Thread.sleep(500)
                sink.next(i)
            }
            sink.complete()
        }

        // 스레드 1개로는 블로킹 못피한다.
        intFlux.subscribe{data ->
            println(data)
        }

        println("Netty 이벤트 루프로 스레드 복귀")
    }

    @Test
    fun produceOneToNineFlux2() {
        // 스케쥴러를 이용해서 스레드를 추가적으로 생성하여 블로킹을 회피한다.
        val intFlux = Flux.create{ sink ->
            for(i in 1..9) {
                Thread.sleep(500)
                sink.next(i)
            }
            sink.complete()
        }.subscribeOn(Schedulers.boundedElastic())

        intFlux.subscribe{data ->
            println("$data : ${Thread.currentThread().name}")
        }
        Thread.sleep(5000) // 스케쥴러는 별도 스레드로 작동돼서, 메인스레드를 잡아놔야한다.

        println("Netty 이벤트 루프로 스레드 복귀")
    }
}