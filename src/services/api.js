import axios from "axios";

// Anpassa bas-URL om backend körs på annan port eller domän
const api = axios.create({
    baseURL: "http://localhost:8080/api",
    auth: {
        username: "devuser",  // samma som i Spring Security
        password: "devpass"
    }
});

export const getMovements = async (filters) => {
    const params = {};

    if (filters.regionCode) params.regionCode = filters.regionCode;
    if (filters.yearFrom) params.yearFrom = filters.yearFrom;
    if (filters.yearTo) params.yearTo = filters.yearTo;
    if (filters.sex) params.sex = filters.sex;
    if (filters.ageGroup) params.ageGroup = filters.ageGroup;
    const res = await api.get("/movements", { params });
    return res.data;
}