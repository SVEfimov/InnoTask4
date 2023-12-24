package ru.inno.course.task4;


import org.hibernate.annotations.processing.SQL;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import ru.inno.course.task4.services.LogDBWriterable;
import java.io.IOException;
import java.sql.SQLException;

@SpringBootApplication
@EnableAspectJAutoProxy
public class MyApp {

    public static void main(String[] args) throws IOException, SQLException {
        ApplicationContext context =  SpringApplication.run(MyApp.class, args);
        LogDBWriterable logDBWriter = context.getBean(LogDBWriterable.class);
        logDBWriter.write(AppConfig.connectParam);
    }
}
