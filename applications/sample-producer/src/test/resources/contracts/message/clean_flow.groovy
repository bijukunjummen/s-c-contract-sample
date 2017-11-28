package message

org.springframework.cloud.contract.spec.Contract.make {
    request {
        method 'POST'
        url '/messages'
        body([
                id     : $(anyNonBlankString()),
                payload: $(anyNonBlankString()),
                delay  : $(anyNumber())
        ])
        headers {
            contentType('application/json')
        }
    }
    response {
        status 200
        body([
                id: fromRequest().body("\$.id"),
                received: fromRequest().body("\$.payload"),
                ack: "ack"
        ])
        headers {
            contentType('application/json')
        }
    }
}