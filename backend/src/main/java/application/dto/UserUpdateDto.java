package application.dto;

import application.constants.UserRole;
import application.models.User;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserUpdateDto {
    Long id;
    String userName;
    String email;
    UserRole role;
    String password;

    public User valueOf(UserUpdateDto userDto) {
        User user = new User();
        user.setId(userDto.getId());
        user.setUsername(userDto.userName);
        user.setEmail(userDto.getEmail());
        user.setRole(userDto.getRole());
        return user;
    }
}
