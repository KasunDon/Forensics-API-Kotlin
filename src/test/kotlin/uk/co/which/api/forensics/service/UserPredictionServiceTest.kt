package uk.co.which.api.forensics.service

import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import uk.co.which.api.forensics.exception.UserPredictionDuplicatedException
import uk.co.which.api.forensics.exception.UserPredictionExceededException
import uk.co.which.api.forensics.value.Coordinate

internal class UserPredictionServiceTest {

    private val underTest = UserPredictionService()

    companion object {
        const val email = "email@email.com"
    }

    @Test
    fun `stores given prediction coordinates by email`() {
        underTest.store(email, Coordinate(0, 0))
        assertTrue(underTest.predictions.size == 1)
    }

    @Test
    fun `deduplicate prediction coordinates supplied by user`() {
        assertThrows<UserPredictionDuplicatedException> {
            underTest.store(email, Coordinate(0, 0))
            underTest.store(email, Coordinate(0, 0))
        }
    }

    @Test
    fun `throw an exception when exceeds 5 predictions per email`() {
        assertThrows<UserPredictionExceededException> {
            underTest.store(email, Coordinate(0, 0))
            underTest.store(email, Coordinate(0, 1))
            underTest.store(email, Coordinate(0, 2))
            underTest.store(email, Coordinate(0, 3))
            underTest.store(email, Coordinate(0, 4))
            underTest.store(email, Coordinate(0, 5))
        }
    }

    @Test
    fun `each prediction matches against grid and returns true if matches`() {
        assertTrue(underTest.locate(email, Coordinate(0, 0)))
    }

    @Test
    fun `each prediction matches against grid and returns false if matches`() {
        assertFalse(underTest.locate(email, Coordinate(1, 0)))
    }
}