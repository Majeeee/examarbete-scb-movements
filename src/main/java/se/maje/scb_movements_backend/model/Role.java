package se.maje.scb_movements_backend.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.MappedCollection;
import org.springframework.data.relational.core.mapping.Table;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table("roles")
public class Role {

    @Id
    private Long id;

    private String name;

    @MappedCollection(idColumn = "role_id")
    @Builder.Default
    private Set<Permission> permissions = Set.of(); // Builder behöver default

    public Set<Permission> getPermissions() {
        return permissions != null ? permissions : Set.of();
    }
}
