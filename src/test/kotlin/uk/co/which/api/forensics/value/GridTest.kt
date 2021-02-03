package uk.co.which.api.forensics.value

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import uk.co.which.api.forensics.value.Direction.EAST
import uk.co.which.api.forensics.value.Direction.NORTH
import uk.co.which.api.forensics.value.Direction.SOUTH
import uk.co.which.api.forensics.value.Direction.WEST

internal class GridTest {
    private val underTest = Grid()

    @Test
    fun `default grid position should face north 0 0`() {
        val (coordinate, direction) = underTest.positions.peek()

        assertEquals(0, coordinate.x)
        assertEquals(0, coordinate.y)
        assertEquals(NORTH, direction)
    }

    @Test
    fun `360 right turn`() {
        assertEquals(NORTH, underTest.currentDirection)

        underTest.turnRight()
        assertEquals(EAST, underTest.currentDirection)

        underTest.turnRight()
        assertEquals(SOUTH, underTest.currentDirection)

        underTest.turnRight()
        assertEquals(WEST, underTest.currentDirection)

        underTest.turnRight()
        assertEquals(NORTH, underTest.currentDirection)
    }

    @Test
    fun `360 left turn`() {
        assertEquals(NORTH, underTest.currentDirection)

        underTest.turnLeft()
        assertEquals(WEST, underTest.currentDirection)

        underTest.turnLeft()
        assertEquals(SOUTH, underTest.currentDirection)

        underTest.turnLeft()
        assertEquals(EAST, underTest.currentDirection)

        underTest.turnLeft()
        assertEquals(NORTH, underTest.currentDirection)
    }

    @Test
    fun `capture coordinates when heading east`() {
        val grid = Grid()
        val (coordinate) = underTest.positions.peek()

        assertEquals(0, coordinate.x)
        assertEquals(0, coordinate.y)
        grid.turnRight()

        val move = grid.move()
        val (x, y) = move.coordinate

        assertEquals(1, x)
        assertEquals(0, y)
        assertEquals(EAST, move.direction)
    }

    @Test
    fun `capture coordinates when heading south`() {
        val grid = Grid()
        val (coordinate) = underTest.positions.peek()

        assertEquals(0, coordinate.x)
        assertEquals(0, coordinate.y)
        grid.turnRight()
        grid.turnRight()

        val move = grid.move()
        val (x, y) = move.coordinate

        assertEquals(0, x)
        assertEquals(1, y)
        assertEquals(SOUTH, move.direction)
    }

    @Test
    fun `capture coordinates when heading west`() {
        val grid = Grid()
        val (coordinate) = underTest.positions.peek()
        assertEquals(0, coordinate.x)
        assertEquals(0, coordinate.y)

        grid.turnRight()
        grid.turnRight()
        grid.move()
        grid.turnLeft()
        grid.move()
        grid.move()
        grid.turnRight()
        grid.turnRight()

        val move = grid.move()
        val (x, y) = move.coordinate

        assertEquals(1, x)
        assertEquals(1, y)
        assertEquals(WEST, move.direction)
    }

    @Test
    fun `capture coordinates when heading north`() {
        val grid = Grid()
        val (coordinate) = underTest.positions.peek()
        assertEquals(0, coordinate.x)
        assertEquals(0, coordinate.y)

        grid.turnRight()
        grid.turnRight()
        grid.move()
        grid.turnLeft()
        grid.move()
        grid.turnRight()
        grid.turnRight()
        grid.turnRight()

        val move = grid.move()
        val (x, y) = move.coordinate

        assertEquals(1, x)
        assertEquals(0, y)
        assertEquals(NORTH, move.direction)
    }
}