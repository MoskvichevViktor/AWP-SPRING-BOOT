package application.services;

import application.exception.ResourceNotFoundException;
import application.models.security.Role;
import application.repositories.RolesRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RolesService {
    private final RolesRepository rolesRepository;

    public Optional<Role> findByName(String name) {
        return rolesRepository.findByName(name);
    }

    public Role getEmptyRole() {
        return findByName("ROLE_EMPTY").orElseThrow(() -> new ResourceNotFoundException("Role <ROLE_USER> not exists, in list roles"));
    }
}
