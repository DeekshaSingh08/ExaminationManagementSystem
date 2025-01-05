package com.seating.examinationManagementSystem.data;

import com.github.javafaker.Faker;
import com.seating.examinationManagementSystem.entity.Student;
import com.seating.examinationManagementSystem.entity.UserDetails;
import com.seating.examinationManagementSystem.repository.StudentRepository;
import com.seating.examinationManagementSystem.repository.UserDetailsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

@Component
public class DataInitializerForStudent implements CommandLineRunner {

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserDetailsRepository userDetailsRepository;

    @Override
    public void run(String... args) throws Exception {
        // Check if the number of students is less than 125

        UserDetails principalUserDetails = new UserDetails();
        principalUserDetails.setUserName("Deeksha");
        principalUserDetails.setRole("Principal");
        principalUserDetails.setPassword(getEncodedPassword("12345"));
        userDetailsRepository.save(principalUserDetails);

        long studentCount = studentRepository.count();

        if (studentCount < 125) {
            Faker faker = new Faker();
            List<String> houseColors = Arrays.asList("Blue", "Green", "Red", "Yellow");
            Random random = new Random();

            // Create 125 student records
            for (long i = studentCount + 1; i <= 125; i++) {
                Student student = new Student();
                student.setStudentRollNo(i);
                student.setStudentName(faker.name().fullName());
                student.setClassName("Class " + faker.number().numberBetween(1, 5));
                student.setHouse(houseColors.get(random.nextInt(houseColors.size())) + " House");
                student.setAddress(faker.address().fullAddress());
                student.setSeatRow(faker.number().numberBetween(0, 0));
                student.setSeatColumn(faker.number().numberBetween(0, 0));

                UserDetails userDetails = new UserDetails();
                userDetails.setUserName(String.valueOf(student.getStudentRollNo()));
                userDetails.setRole("Student");
                userDetails.setPassword(getEncodedPassword(String.valueOf(student.getStudentRollNo())));

                student.setUser(userDetailsRepository.save(userDetails));

                // Save the student to the database
                studentRepository.save(student);
            }
            System.out.println("Inserted new students into the database.");
        } else {
            System.out.println("The database already has enough students.");
        }
    }
    public String getEncodedPassword(String password) {
        return passwordEncoder.encode(password);
    }
}

