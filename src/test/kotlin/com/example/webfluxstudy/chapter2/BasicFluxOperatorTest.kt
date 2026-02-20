package com.example.webfluxstudy.chapter2

import reactor.core.publisher.Flux
import kotlin.test.Test

class BasicFluxOperatorTest {
    /**
     * Flux
     * 데이터 : just, empty, from-시리즈
     * 함수 : defer, create
     */
    @Test
    fun testFluxFromData() {
        val list = listOf(1, 2, 3, 4, 5)
        Flux.fromIterable(list) //fromIterable은 자바 iterable를 상속한 객체를 받아서 Flux로 만들어준다.
            .subscribe{ println(it) }


    }
}