package ru.inno.course.task4.services;

import org.springframework.stereotype.Service;
import ru.inno.course.task4.LogTransformation;
import java.util.ArrayList;
import java.util.Arrays;

@Service
public class ApplicationRefactor implements ApplicationRefactorable{
    ArrayList<String> listApp = new ArrayList<>(Arrays.asList("web", "mobile"));
    @LogTransformation(nameLogFile = "AppRefactor")
    @Override
    public String [] refactor(String [] arr){
        if(!listApp.contains(arr[6])){
            arr[6] = "other: " + arr[6];
        }
        return arr;
    };
}
