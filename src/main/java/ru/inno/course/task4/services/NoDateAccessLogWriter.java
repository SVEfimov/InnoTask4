package ru.inno.course.task4.services;

import org.springframework.stereotype.Service;
import ru.inno.course.task4.MyAppConfig;
import ru.inno.course.task4.LogTransformation;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

/**Сервис убирает из лога для записи в БД строки без dateAccess
*и записывет их в лог-файл NoDateAccessLog.log.
*/
@Service
public class NoDateAccessLogWriter implements NoDateAccessLogWriteable {
    @LogTransformation(nameLogFile = "NoDataAccessLоger")
    @Override
    public ArrayList<String []> writeNoDateAccessLog(ArrayList<String []> parsList) throws IOException {
        String logFile = MyAppConfig.pathLogFiles + "MyAppLog\\NoDateAccessLog.log";
        ArrayList<String []> logList = new ArrayList<>();
        String logStr;

        for (int i = 0; i < parsList.size(); i++) {
            if (Arrays.stream(parsList.get(i)).count() < 6){
                logList.add(parsList.get(i));
                parsList.remove(parsList.get(i));
            }
        }

        if (!logList.isEmpty()){
            File folder = new File(MyAppConfig.pathLogFiles + "MyAppLog\\");
            if (!folder.exists()) {
                folder.mkdir();
            }

            logList.forEach(arr->{
                try {
                    FileWriter writer = new FileWriter(logFile,true);
                    writer.write(getStringFromArray(arr) + "\n");
                    writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        }
        return  parsList;
    }
    private static String getStringFromArray(String [] arr){
        String ret ="";
        for (int i = 0; i < arr.length; i++) {
            if ( i> 0) {
                ret += " ";
            }
            ret += String.valueOf(arr[i]);
        }
        return  ret;
    }

}
