package application.dto;

import application.constants.UserRole;
import application.models.User;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
public class UserDto {
    private Long id;
    private String userName;
    private String email;
    private UserRole role;
    private Date createdAt;
    private Date updatedAt;

    public UserDto(User user) {
        this.id = user.getId();
        this.userName = user.getUsername();
        this.email = user.getEmail();
        this.role = user.getRole();
        this.createdAt = user.getCreatedAt();
        this.createdAt = user.getUpdatedAt();
    }
}
