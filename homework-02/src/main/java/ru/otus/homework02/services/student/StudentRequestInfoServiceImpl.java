package ru.otus.homework02.services.student;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.otus.homework02.domain.Student;
import ru.otus.homework02.services.io.IOService;

@Service
public class StudentRequestInfoServiceImpl implements StudentRequestInfoService {
    private final IOService ioService;

    @Autowired
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
