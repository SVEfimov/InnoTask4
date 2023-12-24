package ru.inno.course.task4;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.springframework.beans.factory.annotation.Autowired;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import ru.inno.course.task4.DAO.LoginsRepository;
import ru.inno.course.task4.DAO.UsersRepository;
import ru.inno.course.task4.DTO.Logins;
import ru.inno.course.task4.DTO.Users;
import ru.inno.course.task4.services.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;


@SpringBootTest

public class TestServices {

    @Autowired
    UsersRepository usersRepository;
    @Autowired
    LoginsRepository loginsRepository;
    @Autowired
    ApplicationRefactorable applicationRefactor;
    @Autowired
    FIOUpperCaseable fioUpperCase;
    @Autowired
    NoDateAccessLogWriteable noDateAccessLogWriter;
    @Autowired
    LogDBWriterable logDBWriter;
    @Autowired
    FileReaderable fileReader;

    @Test
    @DisplayName("Тестирование сервиса преобразования application")
    public void testAppRefactor(){
        String[] listStrTest;
        String[] listStr = {"ivanovi", "иванов", "иван", "ианович", "2023-09-30", "00:00:00.000000000", "online"};
        listStrTest = applicationRefactor.refactor(listStr);
        Assertions.assertEquals("other: online", listStrTest[6]);
    }

    @Test
    @DisplayName("Тестирование сервиса преобразование ФИО таким образом, чтобы они начинались с заглавной буквы")
    public void testUpperCase(){
        String[] listStrTest;
        String[] listStr = {"ivanovi", "иванов", "иван", "иванович", "2023-09-30", "00:00:00.000000000", "online"};
        listStrTest = fioUpperCase.doUpperCase(listStr);
        Assertions.assertEquals("Иванов Иван Иванович", listStrTest[1] + " " + listStrTest[2] +" " + listStrTest[3]);
    }

    @Test
    @DisplayName("Тестирование сервиса проверки даты доступа")
    public void testDateAccess() throws IOException {
        ArrayList<String []> logList = new ArrayList<>();
        ArrayList<String []> logListResult= new ArrayList<>();
        ArrayList<String []> logListExpect= new ArrayList<>();
        String[] listStr1 = {"ivanovi", "иванов", "иван", "иванович", "online"};
        String[] listStr2 = {"admin", "админов", "админ", "админович", "2023-08-22", "22:00:00.000000000", "web"};
        logList.add(listStr1);
        logList.add(listStr2);
        logListExpect.add(listStr2);
        logListResult = noDateAccessLogWriter.writeNoDateAccessLog(logList);
        //сервис возвращает коллекцию без строки с неполными данными
        Assertions.assertEquals(logListExpect, logListResult);
        List<String> lines = Files.readAllLines(Paths.get(AppConfig.pathLogFiles + "MyAppLog\\NoDateAccessLog.txt"));
        for (String line : lines) {
            //сервис записывает строку с неполными данными в лог файл
            Assertions.assertEquals("ivanovi иванов иван иванович online", line);
        }
        //Удаляем тестовый файл
        File delFile = new File(AppConfig.pathLogFiles + "MyAppLog\\NoDateAccessLog.txt");
        Assertions.assertTrue(delFile.delete());
    }

    @Test
    @DisplayName("Тестирование сервиса чтения данных из файла")
    public void testFileReader() throws IOException {
        String testStr = "junit Jюнитов Jюнит Jюнитович 2023-08-22 00:00:00.000000000 testservice";
        FileWriter writer = new FileWriter(AppConfig.pathLogFiles + "Test_log.txt");
        writer.write(testStr);
        writer.close();
        Assertions.assertTrue(fileReader.fileScaner(AppConfig.pathLogFiles).contains(testStr));
        //Удаляем тестовый файл
        File delFile = new File(AppConfig.pathLogFiles + "Test_log.txt");
        Assertions.assertTrue(delFile.delete());
    }

    @Test
    @DisplayName("Тестирование сервиса записи лога в БД")
    public void testDBWrite() throws IOException, SQLException {
        FileWriter writer = new FileWriter(AppConfig.pathLogFiles + "Test_log.txt");
        writer.write("junit Jюнитов Jюнит Jюнитович 2023-08-22 00:00:00.000000000 testservice");
        writer.close();

        //ищем соответствующщие записи в БД
        logDBWriter.write(AppConfig.connectParam);
        ArrayList<Users> listUsers = usersRepository.findByUserName("junit");
        Assertions.assertFalse(listUsers.isEmpty());
        ArrayList<Logins> listLogins = loginsRepository.findByAccessDateAndUserAndApplication(Timestamp.valueOf("2023-08-22 00:00:00.000000000"), new Users(listUsers.getFirst().getId(),listUsers.getFirst().getUserName(),listUsers.getFirst().getFio()) ,"other: testservice");
        Assertions.assertFalse(listLogins.isEmpty());

        //зачищаем тестовую запись
        loginsRepository.deleteById(listLogins.getFirst().getId());
        usersRepository.deleteById(listUsers.getFirst().getId());

        //Удаляем тестовый файл
        File delFile = new File(AppConfig.pathLogFiles + "Test_log.txt");
        Assertions.assertTrue(delFile.delete());
    }
}
