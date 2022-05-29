package application.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data

public class AuthResponse {
    private String token;
    private UserDto profile;

    public AuthResponse(String token) {
        this.token = token;
    }
}
