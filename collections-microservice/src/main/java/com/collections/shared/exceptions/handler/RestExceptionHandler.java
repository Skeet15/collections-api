package com.collections.shared.exceptions.handler;

import com.collections.gateway.response.ResponseHandler;
import com.collections.shared.exceptions.UnsplashForbiddenException;
import com.collections.shared.exceptions.UnsplashUnauthorizedUserException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.io.PrintWriter;
import java.io.StringWriter;

@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    private String stackTraceToString(Exception ex) {
        StringWriter stringWriter = new StringWriter();
        PrintWriter printWriter = new PrintWriter(stringWriter);
        ex.printStackTrace(printWriter);
        return stringWriter.toString();
    }

    @ExceptionHandler({ UnsplashUnauthorizedUserException.class })
    public ResponseEntity<Object> handleUnauthorized(final UnsplashUnauthorizedUserException ex, final WebRequest request) {
        logger.error("Unsplash Unauthorized user exception");
        logger.error(stackTraceToString(ex));
        return ResponseHandler.generateResponse("Unauthorized user, contact with an administrator", HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler({ UnsplashForbiddenException.class })
    public ResponseEntity<Object> handleForbidden(final UnsplashForbiddenException ex, final WebRequest request) {
        logger.error("Unsplash Forbidden User exception");
        logger.error(stackTraceToString(ex));
        return ResponseHandler.generateResponse("Missing permissions to perform request", HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler({ NullPointerException.class, IllegalArgumentException.class, IllegalStateException.class })
    public ResponseEntity<Object> handleInternalServerError(final RuntimeException ex, final WebRequest request) {
        logger.error("Internal Server Error");
        logger.error(stackTraceToString(ex));
        return ResponseHandler.generateResponse("Internal Server Error", HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
