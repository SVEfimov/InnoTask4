package ru.inno.course.task4;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import java.io.File;
import java.io.FileWriter;
import java.lang.reflect.Method;
import java.sql.Timestamp;
import java.util.Arrays;

@Component
//Для логгирования используем AOP
@Aspect
public class MyAppLogger {

    @Pointcut("@annotation(LogTransformation)")
    public void loggableMethod() {}

    @Around("loggableMethod()")
    public Object logWebServiceCall(ProceedingJoinPoint thisJoinPoint) throws Throwable {
        String className = thisJoinPoint.getSignature().getDeclaringType().getSimpleName();
        String methodName = thisJoinPoint.getSignature().getName();
        String methodArgs = Arrays.toString(thisJoinPoint.getArgs());
        Method runMethod = ((MethodSignature) thisJoinPoint.getSignature()).getMethod();
        String logFile = MyAppConfig.pathLogFiles + "MyAppLog\\"+ runMethod.getAnnotation(LogTransformation.class).nameLogFile()+".log";

        long startTime = System.currentTimeMillis();
        Object result = thisJoinPoint.proceed();
        long executionTime = System.currentTimeMillis() - startTime;

        //пишем лог-файл
        File folder = new File(MyAppConfig.pathLogFiles + "MyAppLog\\");
        if (!folder.exists()) {
            folder.mkdir();
        }
        FileWriter writer = new FileWriter(logFile, true);
        writer.write( new Timestamp(System.currentTimeMillis()) + " запущен метод: " + methodName + " класса: " + className + " с параметрами: "+ methodArgs +". Результат выполнения метода: " + result + ". Время выполнения метода: " + executionTime +"\n");
        writer.close();

        return result;
    }
}
