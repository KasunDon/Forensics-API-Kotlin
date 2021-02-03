package uk.co.which.api.forensics.value

import java.util.Objects.requireNonNull

data class Position(val coordinate: Coordinate, val direction: Direction) {

    companion object {
        fun create(x: Int, y: Int, direction: Direction) = Position(Coordinate(x, y), requireNonNull(direction))
    }
}