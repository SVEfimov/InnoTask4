package ru.inno.course.task4.services;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.inno.course.task4.DAO.LoginsRepository;
import ru.inno.course.task4.DTO.Logins;
import ru.inno.course.task4.DTO.Users;
import ru.inno.course.task4.DAO.UsersRepository;
import ru.inno.course.task4.LogBody;
import ru.inno.course.task4.LogTransformation;

import javax.sql.DataSource;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

//компонента записи данных в БД
@Service
public class LogDBWriter implements LogDBWriterable {
    @Autowired
    UsersRepository usersRepository;
    @Autowired
    LoginsRepository loginsRepository;
    @Autowired
    FileLineParserable fileParser;

    @Override
    @LogTransformation
    public void write(String connectParam) throws IOException, SQLException {
       String [] connectParamArr = connectParam.split("\\ ");
       HikariDataSource dataConfig = new HikariDataSource();
       dataConfig.setJdbcUrl(connectParamArr[0]);
       dataConfig.setUsername(connectParamArr[1]);
       dataConfig.setPassword(connectParamArr[2]);
       HikariDataSource dataSource = new HikariDataSource(dataConfig);

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
