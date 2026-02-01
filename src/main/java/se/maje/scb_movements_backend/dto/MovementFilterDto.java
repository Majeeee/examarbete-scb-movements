package se.maje.scb_movements_backend.dto;

import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class MovementFilterDto {

    private List<String> regions;
    private List<String> sexes;
    private List<String> ages;
    private List<String> moveTypes;
    private List<Integer> years;
    private LocalDate startDate;
    private LocalDate endDate;
    private String type;
}