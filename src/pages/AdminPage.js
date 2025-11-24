import React from 'react';

export default function AdminPage() {
    return (
        <div className="container mt-4">
            <h2>Admin Dashboard</h2>
            <p>Här kan du administrera användare, roller och se systemloggar.</p>
            <ul>
                <li>Hantera användare</li>
                <li>Hantera roller och permissions</li>
                <li>Systemstatistik</li>
                <li>Eventuella notifikationer eller e-postköer</li>
            </ul>
        </div>
    );
}