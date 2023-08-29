package ru.otus.homework.services.student;

import ru.otus.homework.domain.Student;

public interface StudentIdentificationService {
    Student identificateByFirstNameAndLastName(String firstName, String lastname);
}
