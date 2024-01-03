package ru.inno.course.task4.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.inno.course.task4.MyAppConfig;
import ru.inno.course.task4.DAO.LoginsRepository;
import ru.inno.course.task4.DTO.Login;
import ru.inno.course.task4.DTO.User;
import ru.inno.course.task4.DAO.UsersRepository;
import ru.inno.course.task4.LogBody;
import ru.inno.course.task4.LogTransformation;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

//компонента записи данных в БД
@Service
public class LogDBWriter implements LogDBWriterable {

    private UsersRepository usersRepository;
    private LoginsRepository loginsRepository;
    private FileLineParserable fileParser;

    @Autowired
    public LogDBWriter(UsersRepository usersRepository, LoginsRepository loginsRepository, FileLineParserable fileParser) {
        this.usersRepository = usersRepository;
        this.loginsRepository = loginsRepository;
        this.fileParser = fileParser;
    }

    @Override
    @LogTransformation(nameLogFile = "SurerServiceLog")
    public void write() throws IOException {
        File folder = new File(MyAppConfig.pathLogFiles + "MyAppLog\\");
        if (!folder.exists()) {
            folder.mkdir();
        }
        File[] listOfFiles = folder.listFiles();
        assert listOfFiles != null;
        for (File delFile : listOfFiles) {
            delFile.delete();
        }

       //получаем готовые для записи в базу данные
       ArrayList<LogBody> logList = fileParser.runParser();
       User addUser;
       //зписываем данные в БД
       for (LogBody logBody : logList) {
           ArrayList<User> findUsers = usersRepository.findByUserName(logBody.getLogin());
           //обеспечим уникальность по username
           if (findUsers.isEmpty()) {
               addUser = new User(null, logBody.getLogin(), logBody.getFIO());
               usersRepository.save(addUser);
           } else {
               addUser = findUsers.getFirst();
           }
           //уникальность в Logins по сочетанию accessDate+user+application
           ArrayList<Login> findLogins = loginsRepository.findByAccessDateAndUserAndApplication(logBody.getAccessDate(),addUser, logBody.getApplication());
           if (findLogins.isEmpty()) {
               Login addLog = new Login(null, logBody.getAccessDate(), addUser, logBody.getApplication());
               loginsRepository.save(addLog);
           }
        }

    }
}
