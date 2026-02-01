package se.maje.scb_movements_backend.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Table("user_roles")
public class UserRole {

    @Id
    private Long id;
    private Long userId;
    private Long roleId;
}
