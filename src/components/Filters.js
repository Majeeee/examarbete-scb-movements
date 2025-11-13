import React from "react";
import { Form, Button, Row, Col } from "react-bootstrap";

function Filters({ filters, setFilters, onSearch }) {
    const handleChange = (e) => {
        setFilters({ ...filters, [e.target.name]: e.target.value });
    };

    return (
        <Form className="mb-3">
            <Row className="g-2">
                <Col md={2}>
                    <Form.Control
                        name="regionCode"
                        placeholder="Regionkod (t.ex. 01)"
                        value={filters.regionCode}
                        onChange={handleChange}
                    />
                </Col>
                <Col md={2}>
                    <Form.Control
                        type="number"
                        name="yearFrom"
                        placeholder="Från år"
                        value={filters.yearFrom}
                        onChange={handleChange}
                    />
                </Col>
                <Col md={2}>
                    <Form.Control
                        type="number"
                        name="yearTo"
                        placeholder="Till år"
                        value={filters.yearTo}
                        onChange={handleChange}
                    />
                </Col>
                <Col md={2}>
                    <Form.Select name="sex" value={filters.sex} onChange={handleChange}>
                        <option value="">Kön (alla)</option>
                        <option value="M">Män</option>
                        <option value="F">Kvinnor</option>
                        <option value="Tot">Totalt</option>
                    </Form.Select>
                </Col>
                <Col md={2}>
                    <Form.Control
                        name="ageGroup"
                        placeholder="Åldersgrupp (t.ex. 20-24)"
                        value={filters.ageGroup}
                        onChange={handleChange}
                    />
                </Col>
                <Col md={2}>
                    <Button variant="primary" onClick={onSearch}>
                        Sök
                    </Button>
                </Col>
            </Row>
        </Form>
    );
}

export default Filters;
