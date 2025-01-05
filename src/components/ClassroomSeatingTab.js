// import axios from '../Axios';
import axios from 'axios';
import React, { useEffect, useState } from 'react';
import { Button, Card, Col, Row } from 'react-bootstrap';
import { Link, useNavigate } from 'react-router-dom';


const ClassroomSeatingTab = () => {
    const [classrooms, setClassrooms] = useState([]);
    const navigate = useNavigate();
 
  const handleManageSeatingClick = (classroomId) => {
    navigate(`/seating-arrangement/${classroomId.examRoomName}`, { state: { activeTab: 'classrooms' } });
  };

    useEffect(() => {
        axios.get('http://localhost:8081/api/examinationmanagementsystem/exam-room/getAllExamRooms')
          .then(response => setClassrooms(response.data))
          .catch(error => console.log(error));

      }, []);

  return (
    <div className="container">
      <h3>Exam Room Seating Arrangement</h3>
      <Card className="mb-4 classroom">
        <Card.Header className="bg-primary text-white">
          <h4>Exam Rooms</h4>
        </Card.Header>
        <Card.Body className="card-container">
          <Row>
            {classrooms.map((classroom) => (
              <Col md={6} className="mb-3">
                <Card>
                  <Card.Body>
                    <Card.Title>{classroom.examRoomName}</Card.Title>
                    <Card.Text>OverallCapacity: {classroom.overallCapacity}</Card.Text>
                    <Card.Text>RemainingCapacity: {classroom.remainingCapacity}</Card.Text>
                    <Button variant="primary" onClick={() => handleManageSeatingClick(classroom)}>Manage Seating</Button>
                  </Card.Body>
                </Card>
              </Col>
            ))}
          </Row>
        </Card.Body>
      </Card>
    </div>
  );
};

export default ClassroomSeatingTab;
