//import logo from './logo.svg';
import './App.css';
import React, { useState } from "react";
import { Container } from "react-bootstrap";
import Filters from "./components/Filters";
import MovementChart from "./components/MovementChart";
import { getMovements } from "./services/api";



function App() {
  const [filters, setFilters] = useState({});
  const [data, setData] = useState([]);

  const onSearch = async () => {
    const res = await getMovements(filters);
    setData(res);
  };
  return (
      <Container className="mt-4">
        <h2>SCB Flyttningsstatistik</h2>
        <Filters filters={filters} setFilters={setFilters} onSearch={onSearch} />
        <MovementChart data={data} />
      </Container>
  );
}

export default App;


