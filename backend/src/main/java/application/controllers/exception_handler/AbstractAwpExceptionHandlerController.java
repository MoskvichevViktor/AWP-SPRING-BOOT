package application.controllers.exception_handler;

import application.controllers.exception_handler.dto.ExceptionResponseDto;
import application.exception.AwpException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public abstract class AbstractAwpExceptionHandlerController extends ResponseEntityExceptionHandler {

    private final HttpStatus DEFAULT_HTTP_STATUS = HttpStatus.BAD_REQUEST;

    @ExceptionHandler(AwpException.class)
    public ResponseEntity<ExceptionResponseDto> handleException(AwpException e) {
        ExceptionResponseDto responseDto = new ExceptionResponseDto(e.getMessage());
        return new ResponseEntity<>(responseDto, DEFAULT_HTTP_STATUS);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        ExceptionResponseDto exceptionResponseDto = new ExceptionResponseDto("Не правильный JSON", ex.getMessage());
        return new ResponseEntity<>(exceptionResponseDto, status);
    }
}
