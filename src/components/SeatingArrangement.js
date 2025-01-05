
import React, { useState, useEffect } from 'react';
import { useLocation, useNavigate, useParams } from 'react-router-dom';
import { Button, Modal, Form } from 'react-bootstrap';
import axios from '../Axios';
import Header from './Header';

function SeatingArrangement() {
  const { classroomId } = useParams();
  const [classroom, setClassroom] = useState(null);
  const [students, setStudents] = useState([]);
  const [selectedStudent, setSelectedStudent] = useState(null);
  const [showModal, setShowModal] = useState(false);
  const [selectedSeat, setSelectedSeat] = useState(null);

  const navigate = useNavigate();
  const location = useLocation();

  useEffect(() => {
    axios.get(`http://localhost:8081/api/examinationmanagementsystem/exam-room/${classroomId}/seating-arrangement`)
      .then(response => setClassroom(response.data))
      .catch(error => console.log(error));

    
  }, [classroomId]);

  const handleBackClick = () => {
    navigate('/dashboard', { state: { activeTab: location.state?.activeTab } });
  };

  const handleSeatClick = (row, col, seat) => {
    setSelectedSeat({ row, col });
    axios.get(`http://localhost:8081/api/examinationmanagementsystem/exam-room/${classroomId}/possible-students?seatRow=${row}&seatColumn=${col}`)
      .then(response => setStudents(response.data))
      .catch(error => console.log(error));
    if(seat?.student){
      setSelectedStudent(seat?.student.id);
    }
    setShowModal(true);
  };

  const handleSeatAssignment = () => {
    if (selectedStudent) {
    
      axios.post(`http://localhost:8081/api/examinationmanagementsystem/exam-room/${classroomId}/assign-seat?studentId=${selectedStudent}&seatRow=${selectedSeat.row}&seatColumn=${selectedSeat.col}`
       ).then((response) => {
          axios.get(`http://localhost:8081/api/examinationmanagementsystem/exam-room/${classroomId}/seating-arrangement`)
          .then ((response)=> {
          setClassroom(response.data)
          })
      }).catch(err => alert('Error assigning seat: ' + err));
      setShowModal(false);
    } else {
      alert('Please select a student');
    }
    setSelectedStudent(null);
  };

  const onSelectStudentChange = (e) => {
    setSelectedStudent(Number(e.target.value))
  }

  const onClose = () => {
    setShowModal(false);
    setSelectedStudent(null);
  }

  return (
    <div className='seating-arrangement-container'>
      <Header title={`Seating Arrangement- ${classroom ? classroom.name : 'Loading...'}`} />
      <div style={{ marginTop: '10px', marginBottom: '20px', alignSelf: 'flex-start' }}>
        <Button variant="secondary" onClick={handleBackClick}>
          Back to Classroom Seating
        </Button>
      </div>

      <div
        className="seating-grid"
        style={{
          display: 'grid',
          gridTemplateColumns: 'repeat(5, 1fr)', 
          gap: '15px', 
          padding: '20px', 
          backgroundColor: '#f4f4f4', 
          border: '2px solid #ddd', 
          borderRadius: '10px', 
          margin: '80px'
        }}
      >
        {classroom &&
          classroom[classroomId].map((row, rowIndex) =>
            row.map((seat, colIndex) => (
              <div
                key={`${rowIndex}-${colIndex}`}
                style={{
                  width: '70px',
                  height: '70px',
                  border: '2px solid #333', 
                  borderRadius: '8px',
                  display: 'flex',
                  alignItems: 'center',
                  justifyContent: 'center',
                  fontWeight: 'bold',
                  fontSize: '14px',
                  backgroundColor: seat ? '#87CEEB' : '#ffffff', 
                  color: seat ? '#ffffff' : '#333', 
                  boxShadow: '0px 4px 6px rgba(0, 0, 0, 0.1)', 
                  cursor: 'pointer', 
                }}
                onClick={() => handleSeatClick(rowIndex, colIndex, seat)}
              >
                {seat ? seat.studentName : 'Empty'}
              </div>
            ))
          )}
      </div>



      <Modal show={showModal} onHide={onClose}>
        <Modal.Header closeButton>
          <Modal.Title>Select Student for Seat</Modal.Title>
        </Modal.Header>
        <Modal.Body>
          <Form>
            <Form.Group controlId="studentSelect">
              <Form.Label>Choose Student</Form.Label>
              <Form.Control
                as="select"
                value={selectedStudent || ''}
                onChange={onSelectStudentChange}
              >
                <option value="">Select Student</option>
                {students.map((student) => (
                  <option key={student.studentRollNo} value={student.studentRollNo}>
                    {student.studentName}
                  </option>
                ))}
              </Form.Control>
            </Form.Group>
          </Form>
        </Modal.Body>
        <Modal.Footer>
          <Button variant="secondary" onClick={onClose}>
            Close
          </Button>
          <Button variant="primary" onClick={handleSeatAssignment}>
            Assign Seat
          </Button>
        </Modal.Footer>
      </Modal>
    </div>
  );
}

export default SeatingArrangement;
