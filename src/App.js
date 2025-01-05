import React from 'react';
import { BrowserRouter as Router, Route, Routes } from 'react-router-dom';
import Login from './components/Login';
import PrincipalDashboard from './components/PrincipalDashboard';
import SeatingArrangement from './components/SeatingArrangement';
import StudentDashboard from './components/StudentDashboard';

function App() {
  return (
    <Router>
      <Routes>
        <Route path="/login" element={<Login/>} />
        <Route path="/" element={<Login/>} />
        <Route path="/dashboard" element={<PrincipalDashboard />} />
        <Route path="/student-dashboard" element={<StudentDashboard />} />
        <Route path="/seating-arrangement/:classroomId" element={<SeatingArrangement />} />
      </Routes>
    </Router>
  );
}

export default App;