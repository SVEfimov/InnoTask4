package ru.inno.course.task4.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.inno.course.task4.MyAppConfig;
import ru.inno.course.task4.LogBody;
import ru.inno.course.task4.LogTransformation;
import java.io.IOException;
import java.util.ArrayList;

@Service
public class FileLineParser implements FileLineParserable{

    FileReaderable fileReader;
    FIOUpperCaseable upperCaseService;
    LogBodyCreatorable logBodyCreator;
    NoDateAccessLogWriteable noDateAccessLogWriter;
    ApplicationRefactorable applicationRefactor;

    @Autowired
    public void setFileReader(FileReaderable fileReader) {
        this.fileReader = fileReader;
    }

    @Autowired
    public void setUpperCaseService(FIOUpperCaseable upperCaseService) {
        this.upperCaseService = upperCaseService;
    }

    @Autowired
    public void setLogBodyCreator(LogBodyCreatorable logBodyCreator) {
        this.logBodyCreator = logBodyCreator;
    }

    @Autowired
    public void setNoDateAccessLogWriter(NoDateAccessLogWriteable noDateAccessLogWriter) {
        this.noDateAccessLogWriter = noDateAccessLogWriter;
    }

    @Autowired
    public void setApplicationRefactor(ApplicationRefactorable applicationRefactor) {
        this.applicationRefactor = applicationRefactor;
    }

    @LogTransformation(nameLogFile = "Parser")
    @Override
    public ArrayList<LogBody> runParser() throws IOException {
        ArrayList<String []> parsList = new ArrayList<>();
        ArrayList<LogBody> retList = new ArrayList<>();
        /**
         * С помочщью сервиса FileReaderable вычитываем строки файлов из указанной папки,
         * парсим и помещаем в коллекцию
        */
        fileReader.fileScaner(MyAppConfig.pathLogFiles)
                .forEach(str ->{
                                parsList.add(str.split("\\ "));
                                }
                         );
        /**
         * С помощью сервиса NoDateAccessLogWriteable убираем из полученной коллекции
         * строки без даnы подключения м пишем их в отдельный лог-файл
         *
         *  С помощью сервиса ApplicationRefactorable преобразуем незаявленные сервисы к виду “other:”+значение.
         *
         * С помощью сервиса FIOUpperCaseable преобразуем ФИО таким образом, чтобы они начинались с заглавной буквы
         *
         * С помощью сервиса LogBodyCreatorable преобразуем коллекцию массивов в коллекцию LogBody
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
