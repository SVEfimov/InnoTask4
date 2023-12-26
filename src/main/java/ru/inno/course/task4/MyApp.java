package ru.inno.course.task4;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import ru.inno.course.task4.services.LogDBWriterable;
import java.io.IOException;

@SpringBootApplication
@EnableAspectJAutoProxy
public class MyApp {
    public static void main(String[] args) throws IOException {
        ApplicationContext context =  SpringApplication.run(MyApp.class, args);
        context.getBean(LogDBWriterable.class).write();
    }
}
