package ru.otus.homework;

import com.github.cloudyrock.spring.v5.EnableMongock;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

// cd {GITHUB_ROOT_DIRECTORY}/homework-11-ui && npm install
// cd {GITHUB_ROOT_DIRECTORY}/homework-11/target && java -jar homework-0.0.1-SNAPSHOT.jar
// npm run dev
// open http://localhost:9000

@EnableMongock
@EnableMongoRepositories
@SpringBootApplication
public class Homework11Application {
    public static void main(String[] args) {
        SpringApplication.run(Homework11Application.class, args);
    }

}
