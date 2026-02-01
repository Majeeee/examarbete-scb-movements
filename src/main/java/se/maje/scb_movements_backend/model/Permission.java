package se.maje.scb_movements_backend.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Table("permissions")
public class Permission {

    @Id
    private Long id;

    /**
     * READ_MOVEMENTS
     * DELETE_USER
     * VIEW_ADMIN_PAGE
     */
    private String name;
}
