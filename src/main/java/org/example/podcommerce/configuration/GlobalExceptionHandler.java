package org.example.podcommerce.configuration;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import java.util.Objects;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.server.ResponseStatusException;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(CustomException.class)
    public ProblemDetail handleCustomException(CustomException exception) {
        ErrorCode errorCode = exception.getErrorCode();
        HttpStatus status = errorCode.getHttpStatus();
        String message = errorCode.getMessage();

        log.warn("[{}] CustomException - {}: {}", status.value(), errorCode.getCode(), message);
        return build(status, message);
    }


    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ProblemDetail handleMethodArgumentNotValid(MethodArgumentNotValidException exception) {
        String errorMessage = stringify(exception);
        log.warn("[400] MethodArgumentNotValidException: {}", errorMessage);
        return build(HttpStatus.BAD_REQUEST, errorMessage);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ProblemDetail handleConstraintViolation(ConstraintViolationException exception) {
        String errorMessage = stringify(exception);
        log.warn("[400] ConstraintViolationException: {}", errorMessage);
        return build(HttpStatus.BAD_REQUEST, errorMessage);
    }

    @ExceptionHandler(ResponseStatusException.class)
    public ProblemDetail handleResponseStatusException(ResponseStatusException exception) {
        HttpStatus status = (HttpStatus) exception.getStatusCode();
        String message =
            exception.getReason() != null ? exception.getReason() : status.getReasonPhrase();

        log.warn("[{}] ResponseStatusException: {}", status.value(), message);
        return build(status, message);
    }

    @ExceptionHandler(AuthenticationException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ProblemDetail handleAuthenticationException(AuthenticationException exception) {
        String message = "인증이 필요합니다. 로그인 후 다시 시도해주세요.";
        log.warn("[401] AuthenticationException: {}", exception.getMessage());
        return build(HttpStatus.UNAUTHORIZED, message);
    }

    @ExceptionHandler(AccessDeniedException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ProblemDetail handleAccessDeniedException(AccessDeniedException exception) {
        String message = "접근 권한이 없습니다.";
        log.warn("[403] AccessDeniedException: {}", exception.getMessage());
        return build(HttpStatus.FORBIDDEN, message);
    }


    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ProblemDetail handleMethodArgumentTypeMismatch(
        MethodArgumentTypeMismatchException exception) {
        String expectedType = exception.getRequiredType() != null ?
            exception.getRequiredType().getSimpleName() : "Unknown";
        String message = String.format("파라미터 '%s'는 %s 타입이어야 합니다.",
            exception.getName(), expectedType);

        log.warn("[400] MethodArgumentTypeMismatchException: {}", message);
        return build(HttpStatus.BAD_REQUEST, message);
    }

    @ExceptionHandler(org.springframework.web.HttpRequestMethodNotSupportedException.class)
    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    public ProblemDetail handleMethodNotSupported(
        org.springframework.web.HttpRequestMethodNotSupportedException exception) {
        String supportedMethods = String.join(", ",
            Objects.requireNonNull(exception.getSupportedMethods()));
        String message = String.format("'%s' 메서드는 지원하지 않습니다. 지원하는 메서드: %s",
            exception.getMethod(), supportedMethods);

        log.warn("[405] HttpRequestMethodNotSupportedException: {}", message);
        return build(HttpStatus.METHOD_NOT_ALLOWED, message);
    }

    @ExceptionHandler(org.springframework.http.converter.HttpMessageNotReadableException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ProblemDetail handleHttpMessageNotReadable(
        org.springframework.http.converter.HttpMessageNotReadableException exception) {
        String message = "요청 본문을 읽을 수 없습니다. JSON 형식을 확인해주세요.";

        log.warn("[400] HttpMessageNotReadableException: {}",
            exception.getMostSpecificCause().getMessage());
        return build(HttpStatus.BAD_REQUEST, message);
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ProblemDetail handleGenericException(Exception exception) {
        String message = "서버 내부 오류가 발생했습니다.";

        log.error("[500] Unhandled Exception: {}", exception.getClass().getSimpleName(), exception);
        return build(HttpStatus.INTERNAL_SERVER_ERROR, message);
    }

    private ProblemDetail build(HttpStatus status, String message) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(status, message);
        problemDetail.setTitle(status.getReasonPhrase());
        return problemDetail;
    }

    private String stringify(MethodArgumentNotValidException exception) {
        StringBuilder errorMessageBuilder = new StringBuilder();
        for (FieldError fieldError : exception.getBindingResult().getFieldErrors()) {
            errorMessageBuilder.append(fieldError.getField()).append(": ");
            errorMessageBuilder.append(fieldError.getDefaultMessage()).append(", ");
        }
        errorMessageBuilder.deleteCharAt(errorMessageBuilder.length() - 2);
        return errorMessageBuilder.toString();
    }

    private String stringify(ConstraintViolationException exception) {
        StringBuilder errorMessageBuilder = new StringBuilder();
        for (ConstraintViolation<?> violation : exception.getConstraintViolations()) {
            errorMessageBuilder.append(violation.getPropertyPath()).append(": ");
            errorMessageBuilder.append(violation.getMessage()).append(", ");
        }
        if (errorMessageBuilder.length() > 2) {
            errorMessageBuilder.deleteCharAt(errorMessageBuilder.length() - 2);
        }
        return errorMessageBuilder.toString();
    }
}
