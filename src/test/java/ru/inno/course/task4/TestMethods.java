package ru.inno.course.task4;

import org.springframework.stereotype.Component;

import java.io.File;

@Component
public class TestMethods {
    @LogTransformation(nameLogFile = "TestLog")
    public int testMethod(int x) throws InterruptedException {
        int a = 10;
        Thread.sleep(100);
        return a*x;
    }
}
