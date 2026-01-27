package afsj.efm.shared.web.error;

import afsj.efm.product.domain.exceptions.InsufficientStockException;
import afsj.efm.product.domain.exceptions.InvalidMovementQuantityException;
import afsj.efm.shared.application.exceptions.ConflictException;
import afsj.efm.shared.application.exceptions.ResourceNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.Instant;
import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler {

   @ExceptionHandler(MethodArgumentNotValidException.class)
   public ResponseEntity<ValidationError> handleValidation(
           MethodArgumentNotValidException ex,
           HttpServletRequest request
   ) {
      List<FieldErrorResponse> errors = ex.getBindingResult()
              .getFieldErrors()
              .stream()
              .map(err -> new FieldErrorResponse(
                      err.getField(),
                      err.getDefaultMessage()
              ))
              .toList();

      ValidationError error = new ValidationError(
              Instant.now(),
              HttpStatus.BAD_REQUEST.value(),
              HttpStatus.BAD_REQUEST.getReasonPhrase(),
              "Validation error",
              request.getRequestURI(),
              errors
      );

      return ResponseEntity.badRequest().body(error);
   }

   @ExceptionHandler(HttpMessageNotReadableException.class)
   public ResponseEntity<StandardError> handleMalformedJson(
           HttpMessageNotReadableException ex,
           HttpServletRequest request
   ) {
      StandardError error = new StandardError(
              Instant.now(),
              HttpStatus.BAD_REQUEST.value(),
              HttpStatus.BAD_REQUEST.getReasonPhrase(),
              "Malformed JSON request",
              request.getRequestURI()
      );
      return ResponseEntity.badRequest().body(error);
   }

   @ExceptionHandler(ResourceNotFoundException.class)
   public ResponseEntity<StandardError> handleNotFound(
           ResourceNotFoundException ex,
           HttpServletRequest request
   ) {
      StandardError error = new StandardError(
              Instant.now(),
              HttpStatus.NOT_FOUND.value(),
              HttpStatus.NOT_FOUND.getReasonPhrase(),
              ex.getMessage(),
              request.getRequestURI()
      );
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
   }

   @ExceptionHandler(ConflictException.class)
   public ResponseEntity<StandardError> handleConflict(
           ConflictException ex,
           HttpServletRequest request
   ) {
      StandardError error = new StandardError(
              Instant.now(),
              HttpStatus.CONFLICT.value(),
              HttpStatus.CONFLICT.getReasonPhrase(),
              ex.getMessage(),
              request.getRequestURI()
      );
      return ResponseEntity.status(HttpStatus.CONFLICT).body(error);
   }

   @ExceptionHandler(Exception.class)
   public ResponseEntity<StandardError> handleGeneric(
           Exception ex,
           HttpServletRequest request
   ) {
      StandardError error = new StandardError(
              Instant.now(),
              HttpStatus.INTERNAL_SERVER_ERROR.value(),
              HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(),
              "Unexpected error",
              request.getRequestURI()
      );
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
   }

   @ExceptionHandler(InsufficientStockException.class)
   public ResponseEntity<StandardError> handleInsufficientStock(
           RuntimeException ex,
           HttpServletRequest request
   ) {
      StandardError error = new StandardError(
              Instant.now(),
              HttpStatus.BAD_REQUEST.value(),
              HttpStatus.BAD_REQUEST.getReasonPhrase(),
              "Not enough stock available to complete the operation",
              request.getRequestURI()
      );

      return ResponseEntity.badRequest().body(error);
   }
}
