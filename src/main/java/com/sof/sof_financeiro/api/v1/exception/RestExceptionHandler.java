package com.sof.sof_financeiro.api.v1.exception;

import com.sof.sof_financeiro.api.v1.exception.model.ApiError;
import com.sof.sof_financeiro.api.v1.exception.model.ApiErrors;
import com.sof.sof_financeiro.api.v1.exception.model.ValidationError;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Stream;

/**
 * Created By : Berkson Ximenes
 * Date : 28/07/2025
 **/

@RestControllerAdvice
@Slf4j
public class RestExceptionHandler extends ResponseEntityExceptionHandler {
    private final MessageSource messageSource;

    public RestExceptionHandler(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @ExceptionHandler({EntityNotFoundException.class, NoSuchElementException.class})
    public ResponseEntity<Object> handleEntityNotFound(WebRequest request) {
        ApiError apiError = ApiError.builder()
                .status(HttpStatus.NOT_FOUND.value())
                .message(messageSource.getMessage("not.found", new Object[]{}, LocaleContextHolder.getLocale()))
                .path(getPath(request))
                .build();
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ApiErrors.builder().error(apiError).build());
    }


    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  @NonNull HttpHeaders headers,
                                                                  @NonNull HttpStatusCode status,
                                                                  @NonNull WebRequest request) {

        List<String> validationErrors = extractValidationErrors(ex.getBindingResult());

        String message = messageSource.getMessage("validation.error", new Object[]{}, LocaleContextHolder.getLocale());

        ApiError apiError = ValidationError.builder()
                .status(status.value())
                .message(message)
                .path(getPath(request))
                .details(validationErrors)
                .build();

        return ResponseEntity.status(status).body(ApiErrors.builder().error(apiError).build());
    }


    @ExceptionHandler({IllegalArgumentException.class})
    public ResponseEntity<Object> handleBadRequests(Exception ex, WebRequest request) {
        return buildErrorResponse(HttpStatus.BAD_REQUEST, ex, request);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleUnexpectedExceptions(Exception ex, WebRequest request) {
        log.error("Erro inesperado no endpoint: {}", getPath(request), ex);

        String message = messageSource
                .getMessage("internal.error", new Object[]{}, LocaleContextHolder.getLocale());

        ApiError apiError = new ApiError(HttpStatus.INTERNAL_SERVER_ERROR, message, getPath(request));

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ApiErrors.builder().error(apiError).build());
    }

    private ResponseEntity<Object> buildErrorResponse(HttpStatus status, Exception ex, WebRequest request) {
        ApiError apiError = new ApiError(status, ex.getLocalizedMessage(),
                getPath(request));
        return ResponseEntity.status(status).body(ApiErrors.builder().error(apiError).build());
    }

    private static List<String> extractValidationErrors(BindingResult result) {
        return Stream.concat(
                        result.getFieldErrors()
                                .stream().map(error -> error.getField() + ": " + error.getDefaultMessage()),
                        result.getGlobalErrors().
                                stream().map(error -> error.getObjectName() + ": " + error.getDefaultMessage()))
                .toList();
    }

    private String getPath(WebRequest request) {
        return ((ServletWebRequest) request).getRequest().getRequestURI();
    }
}
