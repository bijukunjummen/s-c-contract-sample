package sample.load

import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.cloud.contract.stubrunner.spring.AutoConfigureStubRunner
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.web.reactive.function.client.WebClient
import reactor.core.publisher.Mono
import reactor.test.StepVerifier

@RunWith(SpringRunner::class)
@SpringBootTest
@AutoConfigureStubRunner(ids = arrayOf("org.bk.samples.contract:sample-producer:+:stubs"), workOffline = true)
class PassThroughContractTest {

    @Value("\${stubrunner.runningstubs.sample-producer.port}")
    var port:Int = 0

    @Test
    fun testPassThroughCall() {
        
        val passThroughHandler = PassThroughHandler(WebClient.create("http://localhost:$port"))

        val resp:Mono<MessageAck> = passThroughHandler.passThrough(Message(id = "id", payload = "payload", delay = 0L))
        
        StepVerifier.create(resp)
                .expectNextMatches({r -> r.id == "id" && r.received == "payload"})
                .expectComplete()
                .verify()
    }
    
    @EnableAutoConfiguration
    class SpringConfig
}