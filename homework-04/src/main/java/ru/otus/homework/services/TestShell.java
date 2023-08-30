package ru.otus.homework.services;

import org.springframework.shell.Availability;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellMethodAvailability;
import org.springframework.shell.standard.ShellOption;
import ru.otus.homework.domain.Student;
import ru.otus.homework.services.localization.LocalizationService;
import ru.otus.homework.services.student.StudentIdentificationService;

@ShellComponent
public class TestShell {
    private final TestRunner testRunner;

    private final LocalizationService localizationService;

    private final StudentIdentificationService studentIdentificationService;

    private Student student;

    public TestShell(
            TestRunner testRunner,
            LocalizationService localizationService,
            StudentIdentificationService studentIdentificationService) {
        this.testRunner = testRunner;
        this.localizationService = localizationService;
        this.studentIdentificationService = studentIdentificationService;
    }

    @ShellMethod(key = {"i", "identification"}, value = "Introduce yourself")
    public String identification(
            @ShellOption({"firstname", "f"}) String firstName,
            @ShellOption({"lastname", "l"}) String lastName) {
        student = studentIdentificationService.identificateByFirstNameAndLastName(firstName, lastName);
        return getIdentificationOkMessage();
    }

    @ShellMethod(key = {"r", "run-test"}, value = "Run Test")
    @ShellMethodAvailability(value = "isStudentLogin")
    public void runTest() {
        testRunner.runForStudent(student);
    }

    private String getIdentificationOkMessage() {
        return localizationService.getMessage("shell.identification.ok",
                this.student.getLastName(),
                this.student.getFirstName());
    }

    private Availability isStudentLogin() {
        if (student == null) {
            return Availability.unavailable(localizationService.getMessage("shell.identification.required"));
        }
        return Availability.available();
    }
}
