package application.dto;

import application.exception.ResourceNotFoundException;
import application.models.security.User;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Date;

@Data
@NoArgsConstructor
public class UserDto {
    private Long id;
    private String userName;
    private String email;
    private String role;
    private Date created_at;
    private Date updated_at;

    public UserDto(User user) {
        this.id = user.getId();
        this.userName = user.getUsername();
        this.email = user.getEmail();
        if (!user.getRoles().isEmpty()) {
            this.role = new ArrayList<>(user.getRoles()).get(0).getName();
        } else {
            throw new ResourceNotFoundException("The user :" + this.userName + " does not have a role");
        }
        this.created_at = user.getCreated_at();
        this.created_at = user.getUpdated_at();
    }
}
