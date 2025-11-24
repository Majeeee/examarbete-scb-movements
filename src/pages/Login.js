import React, {useState} from 'react';
import { login } from '../services/api';
import { useNavigate } from 'react-router-dom';

export default function Login() {
    const [form,setForm] = useState({ username:'', password:''});
    const navigate = useNavigate();

    const submit = async (e) => {
        e.preventDefault();
        try {
            await login(form); // server sets auth cookie / returns token
            navigate('/dashboard');
        } catch (err) {
            alert(err.response?.data?.message || 'Login failed');
        }
    };

    return (
        <div className="container mt-4">
            <h3>Login</h3>
            <form onSubmit={submit}>
                <input className="form-control mb-2" value={form.username} onChange={e=>setForm({...form, username:e.target.value})} placeholder="Username" />
                <input type="password" className="form-control mb-2" value={form.password} onChange={e=>setForm({...form, password:e.target.value})} placeholder="Password" />
                <button className="btn btn-primary">Login</button>
            </form>
        </div>
    );
}
