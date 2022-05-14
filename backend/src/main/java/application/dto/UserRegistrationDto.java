package application.dto;

import application.constants.UserRole;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserRegistrationDto {
    private String username;
    private String password;
    private String email;
    private UserRole userRole;
}
