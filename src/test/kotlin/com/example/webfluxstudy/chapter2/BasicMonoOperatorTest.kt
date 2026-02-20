package com.example.webfluxstudy.chapter2

import reactor.core.publisher.Mono
import reactor.core.scheduler.Schedulers
import kotlin.test.Test

class BasicMonoOperatorTest {
    /**
     * Mono는 함수로 부터 시작가능하다.
     * fromCallable과 defer가 있다.
     *
     * fromCallable는 동기적인객체 모든 객체 반환 가능
     * defer는 Mono를 반환할때 사용
     */
    @Test
    fun startMonoFromFunction() {
        // fromCallable로 String 반환
        Mono.fromCallable{ callRestTemplate("안녕!") }
            .subscribeOn(Schedulers.boundedElastic())
            .subscribe{println(it)}

        // defer로 Mono<String> 반환
        val defer = Mono.defer {
            callWebClient("안녕!")
        }
        defer.subscribe{ println(it) } // subscribe 해야 callWebClient("안녕!") 실행됨

        val just = callWebClient("안녕!") // 바로 callWebClient("안녕!") 실행됨
        just.subscribe{ println(it) }
    }

    fun callWebClient(request: String): Mono<String> {
        return Mono.just(request + " API 응답 데이터")
    }

    fun callRestTemplate(request: String): String {
        return "$request RestTemplate 응답 데이터"
    }
}