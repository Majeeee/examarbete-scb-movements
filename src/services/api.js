import axios from "axios";

// Bas-URL via miljövariabel, fallback till localhost
const API_BASE = process.env.REACT_APP_API_BASE || "http://localhost:8080/api";

// Axios-instans med flexibelt auth
const axiosInstance = axios.create({
    baseURL: API_BASE,
    withCredentials: true, // för cookie-baserad JWT
    auth: process.env.NODE_ENV === "development" ? {
        username: "devuser",
        password: "devpass"
    } : undefined
});

// Generell fetch-funktion för GET/POST
const fetchData = async ({ endpoint, method = "get", params = {}, data = {} }) => {
    try {
        const response = await axiosInstance({
            url: endpoint,
            method,
            params: method.toLowerCase() === "get" ? params : undefined,
            data: method.toLowerCase() !== "get" ? data : undefined
        });
        return response.data;
    } catch (error) {
        console.error(`Error fetching ${endpoint}:`, error);
        throw error;
    }
};

// Auth endpoints
export const register = (payload) => fetchData({ endpoint: "/auth/register", method: "post", data: payload });
export const login = (payload) => fetchData({ endpoint: "/auth/login", method: "post", data: payload });
export const logout = () => fetchData({ endpoint: "/auth/logout", method: "post" });

// Movements endpoints
export const getMovements = (filters) => fetchData({ endpoint: "/movements", method: "get", params: filters });
export const fetchMovements = (filters) => fetchData({ endpoint: "/movements/filter", method: "post", data: filters });