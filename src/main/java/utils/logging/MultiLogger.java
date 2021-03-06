package utils.logging;

import java.util.Arrays;
import java.util.LinkedList;

public class MultiLogger implements Logger {
    private LinkedList<Logger> loggersList;

    private MultiLogger() {
        loggersList = new LinkedList<Logger>();
    }

    public static MultiLogger createMultiLogger(Logger... inputLoggersList) {
        MultiLogger multiLogger = new MultiLogger();
        multiLogger.loggersList.addAll(Arrays.asList(inputLoggersList));
        return multiLogger;
    }

    @Override
    public void log(String message, LogIdentifier logIdentifier, String methodName, String className) {
        for (Logger logger : loggersList) {
            logger.log(message, logIdentifier, methodName, className);
        }
    }

    @Override
    public void info(String message, Class<?> clazz) {
        for (Logger logger : loggersList) {
            logger.info(message, clazz);
        }
    }

    @Override
    public void error(String message, Class<?> clazz) {
        for (Logger logger : loggersList) {
            logger.error(message, clazz);
        }
    }

    @Override
    public void fatal(String message, Class<?> clazz) {
        for (Logger logger : loggersList) {
            logger.fatal(message, clazz);
        }
    }
}
