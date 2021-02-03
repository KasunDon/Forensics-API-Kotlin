package uk.co.which.api.forensics.controller

import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import uk.co.which.api.forensics.service.UserPredictionService
import uk.co.which.api.forensics.value.Coordinate
import uk.co.which.api.forensics.value.Position
import java.util.*

@RestController
@RequestMapping("/api")
class ForensicsRestController(private val userPredictionService: UserPredictionService) {

    @GetMapping(path = ["/{email}/directions"], produces = [MediaType.APPLICATION_JSON_VALUE])
    fun directions(
        @PathVariable("email") email: String?
    ): Stack<Position> {
        return userPredictionService.grid.positions
    }

    @GetMapping(path = ["/{email}/location/{x}/{y}"], produces = [MediaType.APPLICATION_JSON_VALUE])
    fun location(
        @PathVariable("email") email: String,
        @PathVariable("x") x: Int,
        @PathVariable("y") y: Int
    ): ResponseEntity<*> {
        val coordinate = Coordinate(x, y)
        return ResponseEntity<Any?>(
            mapOf("isFound" to userPredictionService.locate(email, coordinate)),
            HttpStatus.OK
        )
    }

}