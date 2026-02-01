package se.maje.scb_movements_backend.repository;

import org.springframework.r2dbc.core.DatabaseClient;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import se.maje.scb_movements_backend.dto.MovementFilterDto;
import se.maje.scb_movements_backend.model.MovementRecord;

@Repository
public class MovementRepositoryCustomImpl implements MovementRepositoryCustom {

    private final DatabaseClient dbClient;

    public MovementRepositoryCustomImpl(DatabaseClient dbClient) {
        this.dbClient = dbClient;
    }

    @Override
    public Flux<MovementRecord> filter(MovementFilterDto filters) {
        StringBuilder sql = new StringBuilder("SELECT * FROM movement_records WHERE 1=1");

        if (filters.getRegions() != null && !filters.getRegions().isEmpty()) {
            sql.append(" AND region_code IN (:regions)");
        }
        if (filters.getSexes() != null && !filters.getSexes().isEmpty()) {
            sql.append(" AND sex IN (:sexes)");
        }
        if (filters.getAges() != null && !filters.getAges().isEmpty()) {
            sql.append(" AND age IN (:ages)");
        }
        if (filters.getMoveTypes() != null && !filters.getMoveTypes().isEmpty()) {
            sql.append(" AND move_type IN (:moveTypes)");
        }
        if (filters.getYears() != null && !filters.getYears().isEmpty()) {
            sql.append(" AND year IN (:years)");
        }
        if (filters.getStartDate() != null) {
            sql.append(" AND date >= :startDate");
        }
        if (filters.getEndDate() != null) {
            sql.append(" AND date <= :endDate");
        }

        DatabaseClient.GenericExecuteSpec spec = dbClient.sql(sql.toString());

        if (filters.getRegions() != null && !filters.getRegions().isEmpty()) {
            spec = spec.bind("regions", filters.getRegions());
        }
        if (filters.getSexes() != null && !filters.getSexes().isEmpty()) {
            spec = spec.bind("sexes", filters.getSexes());
        }
        if (filters.getAges() != null && !filters.getAges().isEmpty()) {
            spec = spec.bind("ages", filters.getAges());
        }
        if (filters.getMoveTypes() != null && !filters.getMoveTypes().isEmpty()) {
            spec = spec.bind("moveTypes", filters.getMoveTypes());
        }
        if (filters.getYears() != null && !filters.getYears().isEmpty()) {
            spec = spec.bind("years", filters.getYears());
        }
        if (filters.getStartDate() != null) {
            spec = spec.bind("startDate", filters.getStartDate());
        }
        if (filters.getEndDate() != null) {
            spec = spec.bind("endDate", filters.getEndDate());
        }

        return spec.map(row -> {
            MovementRecord rec = new MovementRecord();
            rec.setId(row.get("id", Long.class));
            rec.setRegionCode(row.get("region_code", String.class));
            rec.setMunicipalityCode(row.get("municipality_code", String.class));
            rec.setSex(row.get("sex", String.class));
            rec.setAge(row.get("age", String.class));
            rec.setMoveType(row.get("move_type", String.class));
            rec.setDate(row.get("date", java.time.LocalDate.class));
            rec.setInflow(row.get("inflow", Integer.class));
            rec.setOutflow(row.get("outflow", Integer.class));
            rec.setYear(row.get("year", Integer.class));
            return rec;
        }).all();
    }
}
