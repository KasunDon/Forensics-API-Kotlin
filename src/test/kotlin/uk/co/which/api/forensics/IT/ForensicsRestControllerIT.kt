package uk.co.which.api.forensics.IT

import org.hamcrest.CoreMatchers.`is`
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType.APPLICATION_JSON
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.content
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@AutoConfigureMockMvc
@SpringBootTest
internal class ForensicsRestControllerIT {

    @Autowired
    private lateinit var mvc: MockMvc

    companion object {
        const val email1 = "email1@email.com"
        const val email2 = "email2@email.com"
    }

    @Test
    fun `get all gathered directions`() {
        mvc.perform(get("/api/${email1}/directions").contentType(APPLICATION_JSON))
            .andExpect { status().isOk }
            .andExpect { content().contentTypeCompatibleWith(APPLICATION_JSON) }
            .andExpect { jsonPath("$[0].direction", `is`("NORTH")) }
            .andExpect { jsonPath("$[0].coordinate.x", `is`(0)) }
            .andExpect { jsonPath("$[0].coordinate.y", `is`(0)) }
    }

    @Test
    fun `allow users to predict coordinates`() {
        mvc.perform(
            get("/api/${email1}/location/0/1")
                .contentType(APPLICATION_JSON)
        )
            .andExpect { status().isOk }
            .andExpect { content().contentTypeCompatibleWith(APPLICATION_JSON) }
    }

    @Test
    fun `returns 409 for duplicated predictions`() {
        mvc.perform(
            get("/api/${email1}/location/0/1")
                .contentType(APPLICATION_JSON)
        )
            .andExpect { status().isConflict }
            .andExpect { content().contentTypeCompatibleWith(APPLICATION_JSON) }
    }

    @Test
    fun `returns true predicted coordinates found a match`() {
        mvc.perform(
            get("/api/${email1}/location/0/0")
                .contentType(APPLICATION_JSON)
        )
            .andExpect { status().isOk }
            .andExpect { content().contentTypeCompatibleWith(APPLICATION_JSON) }
            .andExpect { jsonPath("$.isFound", `is`(true)) }
    }

    @Test
    fun `returns false predicted coordinates found a match`() {
        mvc.perform(
            get("/api/${email1}/location/0/2")
                .contentType(APPLICATION_JSON)
        )
            .andExpect(status().isOk)
            .andExpect(content().contentTypeCompatibleWith(APPLICATION_JSON))
            .andExpect(jsonPath("$.isFound", `is`(false)))
    }

    @Test
    fun `returns http 406 when user exceeds more than 5 predictions`() {
        mvc.perform(get("/api/${email2}/location/0/1").contentType(APPLICATION_JSON))
            .andExpect { status().isOk }
            .andExpect { content().contentTypeCompatibleWith(APPLICATION_JSON) }

        mvc.perform(get("/api/${email2}/location/0/2").contentType(APPLICATION_JSON))
            .andExpect { status().isOk }
            .andExpect { content().contentTypeCompatibleWith(APPLICATION_JSON) }

        mvc.perform(get("/api/${email2}/location/0/3").contentType(APPLICATION_JSON))
            .andExpect { status().isOk }
            .andExpect { content().contentTypeCompatibleWith(APPLICATION_JSON) }

        mvc.perform(get("/api/${email2}/location/0/4").contentType(APPLICATION_JSON))
            .andExpect { status().isOk }
            .andExpect { content().contentTypeCompatibleWith(APPLICATION_JSON) }

        mvc.perform(get("/api/${email2}/location/0/5").contentType(APPLICATION_JSON))
            .andExpect { status().isOk }
            .andExpect { content().contentTypeCompatibleWith(APPLICATION_JSON) }

        mvc.perform(get("/api/${email2}/location/0/6").contentType(APPLICATION_JSON))
            .andExpect { status().isNotAcceptable }
    }
}