package com.example.webfluxstudy

import org.springframework.boot.test.context.SpringBootTest
import reactor.core.publisher.Flux
import java.util.stream.IntStream
import kotlin.test.Test

@SpringBootTest
class FunctionalProgrammingTest {

    @Test
    fun produceOneToNineFlux() {
        val intFlux = Flux.create{ sink ->
            for(i in 1..9) {
                sink.next(i)
            }
            sink.complete()
        }

        intFlux.subscribe{data ->
            println(data)
        }
        println("Netty 이벤트 루프로 스레드 복귀")
    }

    @Test
    fun produceOneToNineFluxOperator() {
//        Flux.fromIterable<>(IntStream.range(1, 9).boxed().toList())
//            .map{data -> data * 4}
//            .filter { data -> data % 4 ==0 }

        Flux.fromIterable<Int>(1 until 9)
            .map{ it * 4 }
            .filter { it % 4 == 0}
            .subscribe{ println(it)}
    }
}