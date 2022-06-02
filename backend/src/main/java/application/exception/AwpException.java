package application.exception;

import lombok.NoArgsConstructor;

import java.util.Date;

@NoArgsConstructor
public class AwpException extends Exception {
    private String message;

    public AwpException(String message) {
        super(message);
        this.message = new Date() + ": " + message;
    }

}
