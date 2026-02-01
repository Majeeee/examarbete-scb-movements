package se.maje.scb_movements_backend.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.MappedCollection;
import org.springframework.data.relational.core.mapping.Table;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table("users")
public class User {

    @Id
    private Long id;

    private String firstname;
    private String lastname;
    private String username;
    private String email;
    private String password;
    private Boolean enabled;

    // User → Roles (via user_roles)
    @MappedCollection(idColumn = "user_id")
    private Set<Role> roles;

    // Säker null-safe access
    public Set<Role> getRoles() {
        return roles != null ? roles : Set.of();
    }

    public boolean isEnabled() {
        return enabled != null && enabled;
    }
}
