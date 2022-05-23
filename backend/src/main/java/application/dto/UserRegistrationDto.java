package application.dto;

import application.constants.UserRole;
import application.models.User;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserRegistrationDto {
    private String username;
    private String password;
    private String email;
    private UserRole userRole;

    public UserRegistrationDto(User user) {
        this.username = user.getUsername();
        this.password = "it is forbidden to show";
        this.email = user.getEmail();
        this.userRole = user.getRole();
    }
}
