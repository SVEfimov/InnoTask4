package ru.inno.course.task4;

import lombok.Getter;

import java.sql.Timestamp;

public class LogBody {
    @Getter
    private String login;
    @Getter
    private String surName;
    @Getter
    private String firstName;
    @Getter
    private String patronymic;
    @Getter
    private Timestamp accessDate;
    @Getter
    private String application;

    public LogBody(String login, String surName, String firstName, String patronymic, Timestamp accessDate, String application) {
        this.login = login;
        this.surName = surName;
        this.firstName = firstName;
        this.patronymic = patronymic;
        this.accessDate = accessDate;
        this.application = application;
    }

    public String getFIO(){
        return surName + " " + firstName + " " +patronymic;
    }

    @Override
    public String toString() {
        return "LogBody{" +
                "login='" + login + '\'' +
                ", surName='" + surName + '\'' +
                ", firstName='" + firstName + '\'' +
                ", patronymic='" + patronymic + '\'' +
                ", acsessDate=" + accessDate +
                ", application='" + application + '\'' +
                '}';
    }
}
