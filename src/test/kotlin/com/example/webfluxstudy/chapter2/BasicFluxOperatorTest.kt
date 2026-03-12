package com.example.webfluxstudy.chapter2

import reactor.core.publisher.Flux
import reactor.core.publisher.FluxSink
import java.util.concurrent.atomic.AtomicInteger
import kotlin.test.Test

class BasicFluxOperatorTest {
    /**
     * Flux
     * 데이터 : just, empty, from-시리즈
     * 함수 : defer, create
     */

    // 데이터로 부터 Flux 만들기
    @Test
    fun testFluxFromData() {
        val list = listOf(1, 2, 3, 4, 5)
        Flux.fromIterable(list) //fromIterable은 자바 iterable를 상속한 객체를 받아서 Flux로 만들어준다.
            .subscribe{ println(it) }


        Flux.just(1,2,3,4)
            .subscribe{ data ->
                println("data : $data")
            }
    }

    /**
     * Flux defer -> 안에서 Flux 객체를 반환
     * Flux create -> 안에서 동기적인 객체를 반환
     */
    @Test
    fun testFluxFromFunction() {
        Flux.defer{
            Flux.just(1,2,3,4)
        }.subscribe{ data ->
            println("data from defer : $data")
        }

        Flux.create { sink ->
            for(i in 1..5) {
                sink.next(i)
            }
            sink.complete()
        }.subscribe{ data ->
            println("data from sink : $data")
        }
    }

    // sink는 복잡한 로직속에서 방출 타이밍 제어 하기 위해서 사용한다. 재귀적으로 호출하면서 데이터를 방출하는 예시
    @Test
    fun testSinkDetail() {
        Flux.create{ sink ->
            val counter = AtomicInteger(0)
            recursiveFunction(sink, counter)
        }.subscribe { data ->
            println("data from sink : $data")
        }
    }

    fun recursiveFunction(sink: FluxSink<String>, counter: AtomicInteger) {
        if(counter.incrementAndGet() < 10) {
            sink.next("sink count $counter")
            recursiveFunction(sink, counter)
        } else {
            sink.complete()
        }
    }

}