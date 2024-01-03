package ru.inno.course.task4.DAO;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.inno.course.task4.DTO.Login;
import ru.inno.course.task4.DTO.User;

import java.sql.Timestamp;
import java.util.ArrayList;

@Repository
public interface LoginsRepository extends JpaRepository<Login, Long> {
    ArrayList<Login> findByAccessDateAndUserAndApplication(Timestamp dateAccess, User user, String app);
}
