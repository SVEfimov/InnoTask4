package ru.inno.course.task4.services;

import ru.inno.course.task4.LogBody;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

public interface FileLineParserable {
    public  ArrayList<LogBody> runParser() throws IOException;
}
