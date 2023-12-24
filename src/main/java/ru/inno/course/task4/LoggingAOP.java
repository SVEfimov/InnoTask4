package ru.inno.course.task4;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.nio.file.Files;
import java.sql.Timestamp;
import java.util.logging.Logger;
import java.util.logging.Level;

@Component
//Для логгирования используем AOP
@Aspect
public class LoggingAOP {
    private final Logger logger = Logger.getLogger(LoggingAOP.class.getName());
    @Pointcut("@annotation(LogTransformation)")
    public void loggableMethod() {}

    @Around("loggableMethod()")
    public Object logWebServiceCall(ProceedingJoinPoint thisJoinPoint) throws Throwable {
        String methodName = thisJoinPoint.getSignature().getName();
        String logFile = AppConfig.pathLogFiles + "MyAppLog\\LogTransformation.txt";
        Object[] methodArgs = thisJoinPoint.getArgs();

        long startTime = System.currentTimeMillis();
        Object result = thisJoinPoint.proceed();
        long executionTime = System.currentTimeMillis() - startTime;


        //пишем лог в Debug
        logger.log(Level.INFO,"Call method " + methodName + " with args " + methodArgs);
        logger.log(Level.INFO,"Method " + methodName + " returns " + result);

        //Выбросим лог в файл
        File file = new File(logFile);
        FileWriter writer = new FileWriter(logFile, true);

        writer.write( new Timestamp(System.currentTimeMillis()) + " запущен метод: " + methodName + " с параметрами: "+ methodArgs +". Результат выполнения метода: " + result + ". Время выполнения метода: " + executionTime +"\n");
        writer.close();
        return result;
    }
}
