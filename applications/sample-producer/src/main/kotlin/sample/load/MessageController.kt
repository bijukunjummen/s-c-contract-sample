package sample.load

import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Mono
import java.time.Duration

@RestController
class MessageController {
    
    @PostMapping("/messages")
    fun handleMessage(@RequestBody m: Message): Mono<MessageAck> {
        return Mono
                .fromCallable({ MessageAck(id = m.id, received = m.payload, ack = "ack") })
                .delayElement(Duration.ofMillis(m.delay))
    }
}