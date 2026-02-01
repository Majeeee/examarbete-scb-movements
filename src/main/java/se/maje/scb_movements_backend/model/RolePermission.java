package se.maje.scb_movements_backend.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Table("role_permissions")
public class RolePermission {

    @Id
    private Long id;
    private Long roleId;
    private Long permissionId;
}
