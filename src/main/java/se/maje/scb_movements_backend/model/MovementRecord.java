package se.maje.scb_movements_backend.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Table("movementsRecord")
public class MovementRecord {
    @Id
    private Long id;
    private String regionCode;
    private String municipalityCode;
    private String sex;
    private String age;
    private String moveType;
    private LocalDate date;
    private Integer inflow;
    private Integer outflow;
    private Integer year;
}
