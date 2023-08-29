package ru.otus.homework.services.student;

import org.springframework.stereotype.Service;
import ru.otus.homework.domain.Student;

@Service
public class StudentIdentificationServiceImpl implements StudentIdentificationService {
    @Override
    public Student identificateByFirstNameAndLastName(String firstName, String lastname) {
        return new Student(firstName, lastname);
    }
}
