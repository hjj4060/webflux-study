package com.example.webfluxstudy

import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class WebfluxStudyApplicationTests {

    @Test
    fun produceOneToNine() {
        var sink: MutableList<Int> = mutableListOf()
        for(i in 1..9) {
            sink.add(i)
        }

        sink = map(sink, {number -> number * 4})
        sink = filter(sink, {number -> number % 4 == 0})
        forEach(sink, {number -> println(number)})
    }

    @Test
    fun produceOneToNineStream() {
        var sink: MutableList<Int> = mutableListOf()
        for(i in 1..9) {
            sink.add(i)
        }

        sink.map { data -> data * 4 }
            .filter {data -> data % 4 ==0 }
            .forEach { data -> println(data) }
    }

    private fun forEach(sink: MutableList<Int>, consumer:(Int) -> Unit) {
        for (i in 0..<sink.size) {
            consumer(sink.get(i))
        }
    }

    private fun filter(sink: MutableList<Int>, predicate: (Int) -> Boolean ): MutableList<Int> {
        var sink1 = sink
        val newSink2: MutableList<Int> = mutableListOf()
        for (i in 0..8) {
            if (predicate(sink1.get(i))) {
                newSink2.add(sink1.get(i))
            }
        }
        sink1 = newSink2
        return sink1
    }

    private fun map(sink: MutableList<Int>, mapper: (Int) -> Int): MutableList<Int> {
        var sink1 = sink
        val newSink1: MutableList<Int> = mutableListOf()
        for (i in 0..8) {
            newSink1.add(mapper(sink1.get(i)))
        }
        sink1 = newSink1
        return sink1
    }
}
