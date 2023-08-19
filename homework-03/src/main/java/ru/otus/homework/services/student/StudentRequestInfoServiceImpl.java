package ru.otus.homework.services.student;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.otus.homework.domain.Student;
import ru.otus.homework.services.io.IOService;
import ru.otus.homework.services.localization.LocalizationService;

@Service
public class StudentRequestInfoServiceImpl implements StudentRequestInfoService {
    private final IOService ioService;

    private final LocalizationService localizationService;

    @Autowired
    public StudentRequestInfoServiceImpl(IOService ioService, LocalizationService localizationService) {
        this.ioService = ioService;
        this.localizationService = localizationService;
    }

    @Override
    public Student getStudent() {
        return new Student(getFirstName(), getLastName());
    }

    private String getFirstName() {
        return ioService.readStringWithPrompt(
                localizationService.getMessage("student.prompt.firstname") + ": ");
    }

    private String getLastName() {
        return ioService.readStringWithPrompt(
                localizationService.getMessage("student.prompt.lastname") + ": ");
    }
}
