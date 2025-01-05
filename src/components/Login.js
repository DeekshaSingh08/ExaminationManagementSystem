import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import './Login.css'; // Import external CSS file
import axios from 'axios';

function Login() {
  const [username, setUsername] = useState('');
  const [password, setPassword] = useState('');
  const [error, setError] = useState('');
  const navigate = useNavigate();

  const handleLogin = async (e) => {
    e.preventDefault();
    try {
     
      const values = { username:username, password: password };
          localStorage.setItem('username', username);
          const response =await axios.post('http://localhost:8081/api/examinationmanagementsystem/login', values);
          if(response.status){
          localStorage.setItem('authtoken', response.data.token);
          response.data.userRole==='Student'?navigate('/student-dashboard'):navigate('/dashboard');
          
          }

    } catch (error) {
      setError('Invalid credentials!');
    }
  };

  return (
    <div className="login-container">
      <div className="login-box">
        <h2 className="login-title">Login</h2>
        <form onSubmit={handleLogin}>
          <div className="form-group">
            <label className="form-label">Username</label>
            <input
              type="text"
              value={username}
              onChange={(e) => setUsername(e.target.value)}
              className="form-input"
            />
          </div>
          <div className="form-group">
            <label className="form-label">Password</label>
            <input
              type="password"
              value={password}
              onChange={(e) => setPassword(e.target.value)}
              className="form-input"
            />
          </div>
          <button type="submit" className="login-button">Login</button>
          {error && <p className="error-message">{error}</p>}
        </form>
      </div>
    </div>
  );
}

export default Login;
