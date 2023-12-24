package ru.inno.course.task4.services;


import org.springframework.stereotype.Service;
import ru.inno.course.task4.LogTransformation;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

//компонента чтения файлов из указанной папки
@Service
public class FileReader implements FileReaderable {
    //Компонента чтения файлов по указанной директории
    @Override
    @LogTransformation
    public ArrayList<String> fileScaner(String path) throws FileNotFoundException {
        ArrayList<String> listAuthorization = new ArrayList<>();
        File folder = new File(path);
        File[] listOfFiles = folder.listFiles();

        assert listOfFiles != null;
        for (File file : listOfFiles) {
            if (file.isFile()) {
                Scanner scanner = new Scanner(file);
                while (scanner.hasNextLine()) {
                    listAuthorization.add(scanner.nextLine());
                }
                scanner.close();
            }
        }
        return listAuthorization;
    }

}
