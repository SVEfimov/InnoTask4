package ru.inno.course.task4.services;

import org.springframework.stereotype.Service;
import ru.inno.course.task4.LogTransformation;

//компонета преобразования ФИО с большой буквы
@Service
public class FIOUpperCase implements FIOUpperCaseable {
    @LogTransformation
    public String [] doUpperCase(String [] arrString){
        for (int i = 1; i<=3; i++) {
            arrString[i]= arrString[i].substring(0, 1).toUpperCase() + arrString[i].substring(1);
        }
        return arrString;
    }
}
