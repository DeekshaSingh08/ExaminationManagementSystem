
import React, { useEffect, useState } from 'react';
import './StudentDashboard.css';
import axios from 'axios';
import Header from './Header';


function StudentDetailsTab() {
  const [student, setStudent] = useState(null);
  const [availableSubjects, setAvailableSubjects] = useState([]);
  const [selectedSubjects, setSelectedSubjects] = useState([]);

  useEffect(() => {


    const username = localStorage.getItem('username')
    axios.get(`http://localhost:8081/api/examinationmanagementsystem/students/getStudentByRollNo?studentRollNo=${username}`)
    .then(response=>setStudent(response.data))
    .catch(error => console.log(error));
  
    axios.get(`http://localhost:8081/api/examinationmanagementsystem/students/subject/getSubjects`)
    .then(response=>setAvailableSubjects(response.data))
    .catch(error => console.log(error));
 
  }, []);

  const handleEnrollSubjects = () => {
    if (selectedSubjects.length > 0) {

      axios.post('http://localhost:8081/api/examinationmanagementsystem/students/subject/addSubjects', { subjectIds: selectedSubjects, studentRollNo:student.studentRollNo })
      .then((response) => setStudent(response.data))
      .catch(error => alert('Enrollment failed: ' + error));
    }
  };

  const handleSubjectChange = (e) => {
    const options = Array.from(e.target.selectedOptions);
    setSelectedSubjects(options.map((option) =>Number( option.value)));
  };

  return (
    <>
    <Header title="Student Dashboard" />
    <div className="student-dashboard">
      <h2>My Details</h2>
      {student && (
        <div>
          <p>Name: {student.name}</p>
          <p>Class: {student.className}</p>
          <p>House: {student.house}</p>
          <p>
            Seat:{' '}
        {student.seat.seatRow!==0 && student.seat.seatColumn !== 0
              ? `Row ${student.seat.seatRow}, Column ${student.seat.seatColumn}`
              : 'Not allocated'}
          </p>
          <h4>Enrolled Subjects</h4>
            <div className="enrolled-subjects">
                <ul>
                    {student.subjects?.length ? (
                    student.subjects.map((subject) => (
                        <li key={subject.subjectId}>{subject.subjectName} - {subject.score>=0? - subject.score:''}</li>
                    ))
                    ) : (
                    'Not Enrolled To Any Subject'
                    )}
                </ul>
            </div>
        </div>
      )}

      <h2>Enroll in Subjects</h2>
      <div>
        <select
          multiple
          value={selectedSubjects}
          onChange={handleSubjectChange}
          style={{ height: '150px', width: '200px' }}
        >
          {availableSubjects.map((subject) => (
            <option key={subject.subjectId} value={subject.subjectId}>
              {subject.subjectName}
            </option>
          ))}
        </select>
        <br />
        <button onClick={handleEnrollSubjects}>Enroll Selected Subjects</button>
      </div>
    </div>
    </>
  );
}

export default StudentDetailsTab;
