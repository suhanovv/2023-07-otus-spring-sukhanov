package ru.otus.homework02.domain;

import lombok.Getter;

@Getter
public class Student {
    private final String firstName;

    private final String lastName;

    public Student(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

}
