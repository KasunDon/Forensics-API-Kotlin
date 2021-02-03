package uk.co.which.api.forensics.service

import org.springframework.stereotype.Component
import uk.co.which.api.forensics.exception.UserPredictionDuplicatedException
import uk.co.which.api.forensics.exception.UserPredictionExceededException
import uk.co.which.api.forensics.value.Coordinate
import uk.co.which.api.forensics.value.Grid
import java.util.concurrent.ConcurrentHashMap

@Component
class UserPredictionService(
    val grid: Grid = Grid(),
    val predictions: MutableMap<String, MutableSet<Coordinate>> = ConcurrentHashMap()
) {

    fun store(
        email: String,
        coordinate: Coordinate
    ) {
        val coordinates: MutableSet<Coordinate> = predictions.getOrDefault(email, HashSet())

        if (coordinates.size == 5) {
            throw UserPredictionExceededException()
        }

        if (!coordinates.add(coordinate)) {
            throw UserPredictionDuplicatedException()
        }

        predictions[email] = coordinates
    }

    fun locate(email: String, coordinate: Coordinate): Boolean {
        store(email, coordinate)
        return grid.positions.peek().coordinate == coordinate
    }
}