package uk.co.which.api.forensics.controller

import org.springframework.http.HttpStatus.CONFLICT
import org.springframework.http.HttpStatus.NOT_ACCEPTABLE
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler
import uk.co.which.api.forensics.exception.UserPredictionDuplicatedException
import uk.co.which.api.forensics.exception.UserPredictionExceededException

@ControllerAdvice
class RestControllerAdvice : ResponseEntityExceptionHandler() {

    @ExceptionHandler(UserPredictionExceededException::class)
    @ResponseStatus(NOT_ACCEPTABLE)
    @ResponseBody
    fun userPredictionExceededException(): ResponseEntity<*> {
        return ResponseEntity<Any>(NOT_ACCEPTABLE)
    }

    @ExceptionHandler(UserPredictionDuplicatedException::class)
    @ResponseStatus(NOT_ACCEPTABLE)
    @ResponseBody
    fun userPredictionDuplicatedException(): ResponseEntity<*> {
        return ResponseEntity<Any>(CONFLICT)
    }
}