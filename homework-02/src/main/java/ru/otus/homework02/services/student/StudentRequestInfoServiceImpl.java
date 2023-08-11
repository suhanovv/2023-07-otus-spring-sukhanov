package ru.otus.homework02.services.student;

import ru.otus.homework02.domain.Student;
import ru.otus.homework02.services.io.IOService;

public class StudentRequestInfoServiceImpl implements StudentRequestInfoService {
    private final IOService ioService;

    public StudentRequestInfoServiceImpl(IOService ioService) {
        this.ioService = ioService;
    }

    @Override
    public Student getStudent() {
        return new Student(getFirstName(), getLastName());
    }

    private String getFirstName() {
        return ioService.readStringWithPrompt("Enter your first name: ");
    }

    private String getLastName() {
        return ioService.readStringWithPrompt("Enter your last name: ");
    }
}
