package sample.load

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class SampleLoadApplication

fun main(args: Array<String>) {
    runApplication<SampleLoadApplication>(*args)
}