import React from 'react';
import { Navigate } from 'react-router-dom';

// Simple: you can call backend /auth/me to check; here assume token cookie exists and server checks
export default function ProtectedRoute({ children }) {
    // implement a simple client-side guard or rely on server's 401 responses in fetches
    const loggedIn = !!document.cookie.match(/(^|;)\\s*AUTH_TOKEN=/);
    return loggedIn ? children : <Navigate to="/login" replace />;
}