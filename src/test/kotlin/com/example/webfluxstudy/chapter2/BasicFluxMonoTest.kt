package com.example.webfluxstudy.chapter2

import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import java.time.Duration
import kotlin.test.Test

class BasicFluxMonoTest {
    @Test
    fun testBasicFluxMono() {
        // flux 생성 방법은 빈 함수로부터와 데이터로 시작가능
        Flux.just<Int>(1, 2, 3, 4, 5) // just 데이터로 시작
            .map { it * 2 }
            .filter { it % 4 == 0 } // map, filter로 데이터 가공
            .subscribe { println(it) } // 데이터 방출

        // Flux는 0개 이상의 데이터를 방출할 수 있지만, Mono는 0개부터 최대 1개의 데이터만 방출할 수 있다.
        // Mono는 Optional 정도, Flux는 리스트, 스트림 방출

        Mono.just<Int>(2) // just 데이터로 시작
            .map { it * 2 }
            .filter { it % 4 == 0 } // map, filter로 데이터 가공
            .subscribe { println(it) } // 데이터 방출

        // 에러 발생했을때 empty 방출하고 로그 출력용
        Mono.empty<String>()
            .subscribe(
                { println("값: $it") },          // onNext
                { println("에러") },            // onError
                { println("끝!") }              // onComplete
            )
    }

    @Test
    fun testFluxMonoBlock() {
        val mono = Mono.just("Hello, Mono!").delayElement(Duration.ofSeconds(5))

        println("Mono 결과를 기다리는 중...")
        val result = mono.block() // Mono의 결과를, Block 해놓고 결과를 가져온다.
        println(result) // "Hello, Mono!" 출력
    }

    // Mono에서 데이터 방출수가 많아져서 Flux로 바꿔야할 때, flatMapMany을 이용하여 Mono에서 Flux로 변환 가능
    @Test
    fun monoToFlux() {
        Mono.just(1) // 만들어질때는 하나였는데 flatMapMany를 이용하여 Flux로 바꿔서 여러개로 방출
            .flatMapMany { number ->
                Flux.just(number, number * 2, number * 3) // Mono의 값을 이용해서 Flux로 여러개 방출
            }
            .subscribe { println(it) }
    }
}