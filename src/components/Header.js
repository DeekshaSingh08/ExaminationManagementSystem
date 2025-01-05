import React from 'react';
import './Header.css';

function Header({ title, onLogout }) {
  const handleLogout = () => {
    // Clear user session (localStorage, cookies, etc.)
    localStorage.removeItem('user');
    // Redirect to the login page
    window.location.href = '/login';
  };

  return (
    <header className="header">
      <h1>{title}</h1>
      <button className="logout-button" onClick={onLogout || handleLogout}>
        Logout
      </button>
    </header>
  );
}

export default Header;