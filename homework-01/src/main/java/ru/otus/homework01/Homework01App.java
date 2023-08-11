package ru.otus.homework01;

import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.otus.homework01.services.TestService;


public class Homework01App {

    public static void main(String[] args) {

        ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("/spring-context.xml");
        TestService service = ctx.getBean(TestService.class);
        service.runTest();

    }

}
