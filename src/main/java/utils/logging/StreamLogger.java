package utils.logging;

import java.io.PrintStream;
import java.lang.reflect.Method;

import static utils.timing.TimeManager.getTime;

public class StreamLogger implements Logger {
    private PrintStream printStream;

    public StreamLogger(PrintStream printStream) {
        this.printStream = printStream;
    }

    @Override
    public void log(String message, LogIdentifier logIdentifier, String methodName, String className) {
        printStream.println(String.format("%s - %s - %s - %s - %s", getTime(), logIdentifier.toString(), message,
                methodName, className));
    }

    @Override
    public void info(String message, Class<?> clazz) {
        printStream.println(String.format("%s - %s - %s - %s - %s", getTime(), LogIdentifier.INFO.toString(), message,
                clazz.getEnclosingMethod().getName(), clazz.getName())); //welp: classname with $?
    }

    @Override
    public void error(String message, Class<?> clazz) {
        printStream.println(String.format("%s - %s - %s - %s - %s", getTime(), LogIdentifier.ERROR.toString(), message,
                clazz.getEnclosingMethod().getName(), clazz.getName()));
    }

    @Override
    public void fatal(String message, Class<?> clazz) { //welp : raw use <?>
        printStream.println(String.format("%s - %s - %s - %s - %s", getTime(), LogIdentifier.FATAL.toString(), message,
                clazz.getEnclosingMethod().getName(), clazz.getName()));
    }
}
