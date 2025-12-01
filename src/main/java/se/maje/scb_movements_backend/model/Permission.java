package se.maje.scb_movements_backend.model;

import lombok.Data;
import org.springframework.data.relational.core.mapping.Table;

@Data
@Table("user_roles")
public class Permission {

    private Long userId;
    private Long roleId;
}