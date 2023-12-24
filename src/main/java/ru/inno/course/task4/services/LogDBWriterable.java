package ru.inno.course.task4.services;

import java.io.IOException;
import java.sql.SQLException;

public interface LogDBWriterable {

    void write(String connectParam) throws IOException, SQLException;
}
