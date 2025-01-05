import React, { useState } from 'react';
import { Tabs, Tab } from 'react-bootstrap';
import { useLocation } from 'react-router-dom'; 
import './PrincipalDashboard.css';
import StudentsDetailsTab from './StudentsDetailsTab';
import ClassroomSeatingTab from './ClassroomSeatingTab';
import Header from './Header';

function PrincipalDashboard() {
  const location = useLocation(); 
  const [activeTab, setActiveTab] = useState(location.state?.activeTab || 'students'); 

  return (
    <div className="principal-dashboard">
      <Header title="Principal Dashboard" />
      <Tabs
        activeKey={activeTab} 
        onSelect={(k) => setActiveTab(k)} 
        id="principal-dashboard-tabs"
        className="mb-3"
      >
        <Tab eventKey="students" title="Display Students Details">
          <StudentsDetailsTab />
        </Tab>
        <Tab eventKey="classrooms" title="ExamRoom Seating Arrangement Details">
          <ClassroomSeatingTab />
        </Tab>
      </Tabs>
    </div>
  );
}

export default PrincipalDashboard;