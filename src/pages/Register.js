import React, { useState } from 'react';
import { register } from '../services/api';
import { useNavigate } from 'react-router-dom';

export default function Register() {
    const [form, setForm] = useState({ username:'', email:'', password:'' });
    const navigate = useNavigate();

    const submit = async (e) => {
        e.preventDefault();
        try {
            await register(form);
            alert('Registration successful. Check email for activation.');
            navigate('/login');
        } catch (err) {
            alert(err.response?.data?.message || 'Registration failed');
        }
    };

    return (
        <div className="container mt-4">
            <h3>Register</h3>
            <form onSubmit={submit}>
                <input className="form-control mb-2" placeholder="Username" value={form.username} onChange={e=>setForm({...form, username:e.target.value})} />
                <input className="form-control mb-2" placeholder="Email" value={form.email} onChange={e=>setForm({...form, email:e.target.value})} />
                <input type="password" className="form-control mb-2" placeholder="Password" value={form.password} onChange={e=>setForm({...form, password:e.target.value})} />
                <button className="btn btn-primary">Register</button>
            </form>
        </div>
    );
}
