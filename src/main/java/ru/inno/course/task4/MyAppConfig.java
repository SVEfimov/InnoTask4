package ru.inno.course.task4;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@ComponentScan("ru.inno.course.task4")
@PropertySource("classpath:application.properties")
@EnableJpaRepositories
public class MyAppConfig {
    public static final String pathLogFiles = "D:\\WORK\\";


}

