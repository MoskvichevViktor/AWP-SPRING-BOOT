package application.controllers.exception_handler.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
public class ExceptionResponseDto {
    private String message;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String debugMessage;

    public ExceptionResponseDto(String message, String debugMessage) {
        this.message = message;
        this.debugMessage = debugMessage;
    }

    public ExceptionResponseDto(String message) {
        this.message = message;
    }

    public ExceptionResponseDto() {
    }
}
