package ru.inno.course.task4.services;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.inno.course.task4.MyAppConfig;
import ru.inno.course.task4.DAO.LoginsRepository;
import ru.inno.course.task4.DTO.Logins;
import ru.inno.course.task4.DTO.Users;
import ru.inno.course.task4.DAO.UsersRepository;
import ru.inno.course.task4.LogBody;
import ru.inno.course.task4.LogTransformation;

import javax.sql.DataSource;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

//компонента записи данных в БД
@Service
public class LogDBWriter implements LogDBWriterable {

    UsersRepository usersRepository;
    LoginsRepository loginsRepository;
    FileLineParserable fileParser;

    @Autowired
    public void setFileParser(FileLineParserable fileParser) {
        this.fileParser = fileParser;
    }

    @Autowired
    public void setUsersRepository(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    @Autowired
    public void setLoginsRepository(LoginsRepository loginsRepository) {
        this.loginsRepository = loginsRepository;
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
       Users addUser;
       //зписываем данные в БД
       for (LogBody logBody : logList) {
           ArrayList<Users> findUsers = usersRepository.findByUserName(logBody.getLogin());
           //обеспечим уникальность по username
           if (findUsers.isEmpty()) {
               addUser = new Users(null, logBody.getLogin(), logBody.getFIO());
               usersRepository.save(addUser);
           } else {
               addUser = findUsers.getFirst();
           }
           //уникальность в Logins по сочетанию accessDate+user+application
           ArrayList<Logins> findLogins = loginsRepository.findByAccessDateAndUserAndApplication(logBody.getAccessDate(),addUser, logBody.getApplication());
           if (findLogins.isEmpty()) {
               Logins addLog = new Logins(null, logBody.getAccessDate(), addUser, logBody.getApplication());
               loginsRepository.save(addLog);
           }
        }

    }
}
