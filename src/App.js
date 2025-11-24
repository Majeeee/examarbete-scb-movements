import React, { useState } from "react";
import { BrowserRouter, Routes, Route, Link } from "react-router-dom";
import { Container } from "react-bootstrap";
import AdminPage from "./pages/AdminPage";
import Register from './pages/Register';
import Login from './pages/Login';
import ProtectedRoute from './components/ProtectedRoute';
import Filters from "./components/Filters";
import MovementChart from "./components/MovementChart";
import { getMovements, logout } from './services/api';

function Dashboard() {
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

function App() {
    const doLogout = async () => {
        await logout();
        window.location.href = '/login';
    };

    return (
        <BrowserRouter>
            <nav className="navbar navbar-expand bg-light">
                <div className="container">
                    <Link className="navbar-brand" to="/">SCB App</Link>
                    <div>
                        <Link className="btn btn-link" to="/dashboard">Dashboard</Link>
                        <Link className="btn btn-link" to="/admin">Admin</Link>
                        <button className="btn btn-outline-secondary" onClick={doLogout}>Logout</button>
                    </div>
                </div>
            </nav>

            <Routes>
                <Route path="/register" element={<Register />} />
                <Route path="/login" element={<Login />} />
                <Route path="/dashboard" element={
                    <ProtectedRoute>
                        <Dashboard />
                    </ProtectedRoute>
                } />
                <Route path="/admin" element={
                    <ProtectedRoute>
                        <AdminPage />
                    </ProtectedRoute>
                } />
                <Route path="/" element={<Login />} />
            </Routes>
        </BrowserRouter>
    );
}

export default App;
