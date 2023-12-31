package ru.inno.course.task4.DTO;

//import javax.persistence.*;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "logins")
public class Login {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "access_date")
    private Timestamp accessDate;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "application")
    private String application;

}
