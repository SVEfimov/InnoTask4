package ru.inno.course.task4.DAO;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.inno.course.task4.AppConfig;
import ru.inno.course.task4.DTO.Logins;
import ru.inno.course.task4.DTO.Users;

import java.sql.Timestamp;
import java.util.ArrayList;

@Repository
public interface LoginsRepository extends JpaRepository<Logins, Long> {
    ArrayList<Logins> findByAccessDateAndUserAndApplication(Timestamp dateAccess, Users user, String app);
}
