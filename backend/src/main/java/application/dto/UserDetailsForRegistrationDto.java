package application.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserDetailsForRegistrationDto {
    private String username;
    private String password;
//    private String email;
}
