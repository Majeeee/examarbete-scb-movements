import { LineChart, Line, XAxis, YAxis, Tooltip, CartesianGrid, ResponsiveContainer } from "recharts";

function MovementChart({ data }) {
    if (!data || data.length === 0) return null;
    return (
        <div style={{ width: "100%", height: 400 }}>
            <ResponsiveContainer>
                <LineChart data={data}>
                    <CartesianGrid strokeDasharray="3 3" />
                    <XAxis dataKey="year" />
                    <YAxis />
                    <Tooltip />
                    <Line type="monotone" dataKey="count" stroke="#007bff" />
                </LineChart>
            </ResponsiveContainer>
        </div>
    );
}

export default MovementChart;
