package ru.inno.course.task4.services;

import org.springframework.stereotype.Service;
import ru.inno.course.task4.LogBody;
import ru.inno.course.task4.LogTransformation;

import java.sql.Timestamp;

/**чтение/запись выполняем через класс LogBody для реализации слабой связи
 и независимости сервиса записи в БД от изменения структуры загружаемого лог-файла
 */
@Service
public class LogBodyCreator implements LogBodyCreatorable {
    @LogTransformation
    @Override
    public LogBody initLLogBodyList(String [] arr){
        return new LogBody(arr[0], arr[1], arr[2], arr[3], Timestamp.valueOf(arr[4]+" "+arr[5]), arr[6]);
    }
}
