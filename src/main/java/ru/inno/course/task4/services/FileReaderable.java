package ru.inno.course.task4.services;

import java.io.FileNotFoundException;
import java.util.ArrayList;

public interface FileReaderable {
    public ArrayList<String> fileScaner(String path) throws FileNotFoundException;

}
