package com.collections.shared.exceptions.handler;

import com.collections.gateway.response.ResponseHandler;
import com.collections.shared.exceptions.BadRequestException;
import com.collections.shared.exceptions.ForbiddenException;
import com.collections.shared.exceptions.UnauthorizedUserException;
import java.io.PrintWriter;
import java.io.StringWriter;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;



@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

  // Esta clase de encarga de tratar todos los flujos de error correspondientes a los estados HTTP diferentes de 200 OK

  // Dependiendo de la excepcion que se levante, se ejecuta un método u otro (ver handleResponse en CollectionsHttpClientImpl.java)

  @Override
  protected ResponseEntity<Object> handleNoHandlerFoundException(NoHandlerFoundException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
    return ResponseHandler.generateResponse("The URL you have reached is not in service at this time", HttpStatus.NOT_FOUND);
  }

  @Override
  protected ResponseEntity<Object> handleMissingServletRequestParameter(
          MissingServletRequestParameterException ex, HttpHeaders headers,
          HttpStatus status, WebRequest request) {
    return ResponseHandler.generateResponse("Missing parameter " + ex.getParameterName(), HttpStatus.BAD_REQUEST);
  }

  private String stackTraceToString(Exception ex) {
    StringWriter stringWriter = new StringWriter();
    PrintWriter printWriter = new PrintWriter(stringWriter);
    ex.printStackTrace(printWriter);
    return stringWriter.toString();
  }

  @ExceptionHandler({UnauthorizedUserException.class})
  public ResponseEntity<Object> handleUnauthorized(final UnauthorizedUserException ex, final WebRequest request) {
    logger.error("Unauthorized user exception");
    logger.error(stackTraceToString(ex));
    return ResponseHandler.generateResponse("Unauthorized user, contact with an administrator", HttpStatus.UNAUTHORIZED);
  }

  @ExceptionHandler({ForbiddenException.class})
  public ResponseEntity<Object> handleForbidden(final ForbiddenException ex, final WebRequest request) {
    logger.error("Forbidden User exception");
    logger.error(stackTraceToString(ex));
    return ResponseHandler.generateResponse("Missing permissions to perform request", HttpStatus.FORBIDDEN);
  }

  @ExceptionHandler({BadRequestException.class})
  public ResponseEntity<Object> handleBadRequest(final BadRequestException ex, final WebRequest request) {
    logger.error("Bad Request exception");
    logger.error(stackTraceToString(ex));
    return ResponseHandler.generateResponse("BAD REQUEST", HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler({NullPointerException.class, IllegalArgumentException.class, IllegalStateException.class})
  public ResponseEntity<Object> handleInternalServerError(final RuntimeException ex, final WebRequest request) {
    logger.error("Internal Server Error");
    logger.error(stackTraceToString(ex));
    return ResponseHandler.generateResponse("Internal hi Error", HttpStatus.INTERNAL_SERVER_ERROR);
  }
}
