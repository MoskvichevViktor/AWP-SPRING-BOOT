package application.exception;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
public class AwpError {
    private String message;
    private Date date;

    public AwpError(String message) {
        this.message = message;
        this.date = new Date();
    }

}
