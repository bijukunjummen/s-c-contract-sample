package sample.load.web

import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.http.MediaType
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.asyncDispatch
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultHandlers.print
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*
import sample.load.MessageController

@RunWith(SpringRunner::class)
@WebMvcTest(MessageController::class)
class MessageControllerTest {

    @Autowired
    lateinit var mockMvc: MockMvc

    @Test
    fun `posting a message should return an ack`() {
        val mvcResult = mockMvc.perform(
                post("/messages")
                        .content("{\n  \"id\" : \"id\",\n  \"payload\": \"payload\",\n  \"delay\" : 0\n}")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(request().asyncStarted())
                .andReturn()

        mockMvc.perform(asyncDispatch(mvcResult))
                .andExpect(status().isOk)
                .andDo(print())
                .andExpect(content().json("{\n\"id\" : \"id\",\n\"received\": \"payload\",\n\"ack\" : \"ack\"\n}\n"))
    }
}