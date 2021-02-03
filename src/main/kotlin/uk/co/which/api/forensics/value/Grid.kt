package uk.co.which.api.forensics.value

import uk.co.which.api.forensics.value.Direction.EAST
import uk.co.which.api.forensics.value.Direction.NORTH
import uk.co.which.api.forensics.value.Direction.SOUTH
import uk.co.which.api.forensics.value.Direction.WEST
import uk.co.which.api.forensics.value.Position.Companion.create
import java.util.*

class Grid(
    var currentDirection: Direction = NORTH,
    val positions: Stack<Position> = Stack<Position>()
) {
    companion object {
        private val RIGHT: Map<Direction, Direction> = mapOf(
            NORTH to EAST,
            EAST to SOUTH,
            SOUTH to WEST,
            WEST to NORTH
        )

        private val LEFT: Map<Direction, Direction> = mapOf(
            NORTH to WEST,
            WEST to SOUTH,
            SOUTH to EAST,
            EAST to NORTH
        )
    }

    init {
        positions.push(create(0, 0, NORTH))
    }

    fun move(): Position {
        val (coordinate) = positions.peek()
        when (currentDirection) {
            EAST -> {
                val newPosition: Position = create(coordinate.x + 1, coordinate.y, currentDirection)
                positions.push(newPosition)
                return newPosition
            }

            SOUTH -> {
                val newPosition: Position = create(coordinate.x, coordinate.y + 1, currentDirection)
                positions.push(newPosition)
                return newPosition
            }
            NORTH -> {
                val newPosition: Position = create(coordinate.x, coordinate.y - 1, currentDirection)
                positions.push(newPosition)
                return newPosition
            }
            else -> {
                val newPosition: Position = create(coordinate.x - 1, coordinate.y, currentDirection)
                positions.push(newPosition)
                return newPosition
            }
        }
    }

    fun turnRight() {
        currentDirection = RIGHT[currentDirection]!!
    }

    fun turnLeft() {
        currentDirection = LEFT[currentDirection]!!
    }
}