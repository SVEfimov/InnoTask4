package ru.inno.course.task4.DAO;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.inno.course.task4.DTO.User;

import java.util.ArrayList;

@Repository
public interface UsersRepository extends JpaRepository<User, Long> {
    ArrayList<User> findByUserName(String userName);
}
