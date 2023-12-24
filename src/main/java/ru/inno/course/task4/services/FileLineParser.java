package ru.inno.course.task4.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.inno.course.task4.AppConfig;
import ru.inno.course.task4.LogBody;
import ru.inno.course.task4.LogTransformation;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

@Service
public class FileLineParser implements FileLineParserable{
    @Autowired
    FileReaderable fileReader;
    @Autowired
    FIOUpperCaseable upperCaseService;
    @Autowired
    LogBodyCreatorable logBodyCreator;
    @Autowired
    NoDateAccessLogWriteable noDateAccessLogWriter;
    @Autowired
    ApplicationRefactorable applicationRefactor;
    @LogTransformation
    public ArrayList<LogBody> runParser() throws IOException {
        ArrayList<String []> parsList = new ArrayList<>();
        ArrayList<LogBody> retList = new ArrayList<>();
        /**
         * С помочщью сервиса FileProcessorable вычитываем строки файлов из указанной папки,
         * парсим и помещаем в коллекцию
        */
        fileReader.fileScaner(AppConfig.pathLogFiles)
                .forEach(str ->{
                                parsList.add(str.split("\\ "));
                                }
                         );
        /**
         * С помощью сервиса NoDateAccessLogWriteable убираем из полученной коллекции
         * строки без даны подключения м пишем их в отдельный лог-файл
         *
         *  С помощью сервиса ApplicationRefactorable преобразуем незаявленные сервисы к виду “other:”+значение.
         *
         * С помощью сервиса UserNameUpperCaseable преобразуем ФИО таким образом, чтобы они начинались с заглавной буквы
         *
         * С помощью сервиса LogBodyProcessorable преобразуем коллекцию массивов в коллекцию LogBody
         */
        noDateAccessLogWriter.writeNoDateAccessLog(parsList).
                forEach(arr -> {
                                  applicationRefactor.refactor(upperCaseService.doUpperCase(arr));
                                  retList.add(logBodyCreator.initLLogBodyList(arr));
                                }
                         );
        return retList;
    }



}
