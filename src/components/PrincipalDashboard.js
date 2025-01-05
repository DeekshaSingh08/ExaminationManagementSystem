import React from 'react';
import { Tabs, Tab } from 'react-bootstrap';
import './PrincipalDashboard.css'; // Add any custom styles here
import StudentsDetailsTab from './StudentsDetailsTab';
import ClassroomSeatingTab from './ClassroomSeatingTab';

function PrincipalDashboard() {
  return (
    <div className="principal-dashboard">
      <h2 className="text-center mb-4">Principal Dashboard</h2>
      <Tabs defaultActiveKey="students" id="principal-dashboard-tabs" className="mb-3">
        <Tab eventKey="students" title="Display Students Details">
          <StudentsDetailsTab />
        </Tab>
        <Tab eventKey="classrooms" title="Exam Room Seating Arrangement Details">
          <ClassroomSeatingTab />
        </Tab>
      </Tabs>
    </div>
  );
}

export default PrincipalDashboard;
