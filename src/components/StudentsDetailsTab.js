import React, { useEffect, useState } from 'react';
import { Card, Table, Button, Modal, Form } from 'react-bootstrap';
import {cloneDeep} from 'lodash';
// import axios from '../Axios';
import axios from 'axios';
import './PrincipalDashboard.css';

const StudentsDetailsTab = () => {
    const [students, setStudents] = useState([]);
    const [selectedStudent, setSelectedStudent] = useState(null);
    const [showModal, setShowModal] = useState(false);
    const [searchQuery, setSearchQuery] = useState('');

    useEffect(() => {
       
        axios.get('http://localhost:8081/api/examinationmanagementsystem/students/getStudentByName')
          .then(response => setStudents(response.data))
          .catch(error => console.log(error));
    
    }, []);

    const handleSearch = async () => {
        if (searchQuery) {
          const response = await axios.get(`http://localhost:8081/api/examinationmanagementsystem/students/getStudentByName?studentName=${searchQuery}`);
          setStudents(response.data);
        }
        else {
            axios.get('http://localhost:8081/api/examinationmanagementsystem/students/getStudentByName')
            .then(response => setStudents(response.data))
            .catch(error => console.log(error));
        }
      };

    const handleAllocateScores = (student) => {
        setSelectedStudent(student);
        setShowModal(true);
    };

    const handleScoreChange = (subjectIndex, value) => {
        const updatedStudent = cloneDeep(selectedStudent);
        updatedStudent.subjects[subjectIndex].score = value;
        setSelectedStudent(updatedStudent);
    };

    const handleSaveScores = () => {
        axios.post('http://localhost:8081/api/examinationmanagementsystem/students/subject/scoreAllocation',{subjects: selectedStudent.subjects, studentRollNo:selectedStudent.studentRollNo})
            .then(()=>{
            axios.get('http://localhost:8081/api/examinationmanagementsystem/students/getStudentByName')
            .then(response => setStudents(response.data))
            }
        ).catch(e=>console.log(e))

        setShowModal(false);
        setSelectedStudent(null);
    };

    const onClose = () => {
        setShowModal(false);
        setSelectedStudent(null);
    }

    return (
        <div>
            <input
                className='search-bar'
                type="text"
                value={searchQuery}
                onChange={(e) => setSearchQuery(e.target.value)}
                placeholder="Search Students by Name"
            />
            <button className='search-bar-btn' onClick={handleSearch}>Search</button>
            <Card className="mb-4">
                <Card.Header className="bg-primary text-white">
                    <h4>Students</h4>
                </Card.Header>
                <Card.Body>
                    <div className="table-container">
                        <Table striped bordered hover responsive>
                            <thead>
                                <tr>
                                    <th>Roll Number</th>
                                    <th>Name</th>
                                    <th>Class</th>
                                    <th>House</th>
                                    <th>Actions</th>
                                </tr>
                            </thead>
                            <tbody>
                                {students.map((student) => (
                                    <tr key={student.id}>
                                        <td>{student.studentRollNo}</td>
                                        <td>{student.studentName}</td>
                                        <td>{student.className}</td>
                                        <td>{student.house}</td>
                                        <td>
                                            <Button
                                                variant="primary"
                                                size="sm"
                                                onClick={() => handleAllocateScores(student)}
                                                disabled={!student.subjects?.length}
                                            >
                                                Allocate Scores
                                            </Button>
                                        </td>
                                    </tr>
                                ))}
                            </tbody>
                        </Table>
                    </div>
                </Card.Body>
            </Card>

            <Modal show={showModal} onHide={onClose}>
                <Modal.Header closeButton>
                    <Modal.Title>Allocate Scores for {selectedStudent?.name}</Modal.Title>
                </Modal.Header>
                <Modal.Body style={{ maxHeight: '400px', overflowY: 'auto' }}>
                    {selectedStudent?.subjects.map((subject, index) => (
                        <Form.Group className="mb-3" key={index}>
                            <Form.Label>{subject.subjectName}</Form.Label>
                            <Form.Control
                                type="number"
                                placeholder="Enter score"
                                value={subject.score || ''}
                                onChange={(e) => handleScoreChange(index, e.target.value)}
                            />
                        </Form.Group>
                    ))}
                </Modal.Body>
                <Modal.Footer>
                    <Button variant="secondary" onClick={onClose}>
                        Cancel
                    </Button>
                    <Button variant="primary" onClick={handleSaveScores}>
                        Save Scores
                    </Button>
                </Modal.Footer>
            </Modal>
        </div>
    );
};

export default StudentsDetailsTab;
