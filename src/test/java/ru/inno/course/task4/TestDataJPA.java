package ru.inno.course.task4;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import ru.inno.course.task4.DAO.LoginsRepository;
import ru.inno.course.task4.DAO.UsersRepository;
import ru.inno.course.task4.DTO.Logins;
import ru.inno.course.task4.DTO.Users;

import java.sql.Timestamp;
import java.util.ArrayList;

@DataJpaTest
public class TestDataJPA {

    UsersRepository usersRepository;
    LoginsRepository loginsRepository;

    @Autowired
    public void setUsersRepository(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }
    @Autowired
    public void setLoginsRepository(LoginsRepository loginsRepository) {
        this.loginsRepository = loginsRepository;
    }

    @Test
    @DisplayName("Тестирование UsersRepository ")
    public void testUserRepository(){
        ArrayList<Users> listUsers = new ArrayList<>();
        Users testUser = new Users(null, "testuser", "Михайлов Юрий Владимирович");
        usersRepository.save(testUser);
        listUsers = usersRepository.findByUserName("testuser");
        Assertions.assertFalse(listUsers.isEmpty());
    }

    @Test
    @DisplayName("Тестирование LoginsRepository ")
    public void testLoginsRepository(){

        ArrayList<Logins> listLogins = new ArrayList<>();
        Users testUser = new Users(null, "testuser", "Михайлов Юрий Владимирович");
        usersRepository.save(testUser);
        Logins testLog = new Logins(null, Timestamp.valueOf("2023-09-30 00:00:00.000000000"), testUser, "web");
        loginsRepository.save(testLog);
        listLogins = loginsRepository.findByAccessDateAndUserAndApplication(Timestamp.valueOf("2023-09-30 00:00:00.000000000"),testUser,"web");
        Assertions.assertFalse(listLogins.isEmpty());
    }
}
