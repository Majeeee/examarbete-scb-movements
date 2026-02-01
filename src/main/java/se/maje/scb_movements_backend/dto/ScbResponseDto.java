package se.maje.scb_movements_backend.dto;

import lombok.Data;

import java.util.List;

@Data
public class ScbResponseDto {

    private List<Column> columns;
    private List<DataRow> data;

    @Data
    public static class Column {
        private String code;
        private String text;
    }

    @Data
    public static class DataRow {
        private List<String> key;
        private List<String> values;
    }
}
