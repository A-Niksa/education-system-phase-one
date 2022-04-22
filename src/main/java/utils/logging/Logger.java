package utils.logging;

import java.lang.reflect.Method;

public interface Logger {
    public void log(String message, LogIdentifier logIdentifier, String methodName, String className);

    public void info(String message, Class<?> clazz);

    public void error(String message, Class<?> clazz);

    public void fatal(String message, Class<?> clazz);
}
