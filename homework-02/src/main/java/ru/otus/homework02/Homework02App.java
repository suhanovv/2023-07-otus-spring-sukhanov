package ru.otus.homework02;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import ru.otus.homework02.services.TestRunner;

@ComponentScan
public class Homework02App {

    public static void main(String[] args) {

        AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(Homework02App.class);
        TestRunner service = ctx.getBean(TestRunner.class);
        service.runTest();

    }

}
