package ru.inno.course.task4.services;

import java.io.IOException;
import java.util.ArrayList;

public interface NoDateAccessLogWriteable {
    public ArrayList<String []> writeNoDateAccessLog(ArrayList<String []> parsList) throws IOException;
}
