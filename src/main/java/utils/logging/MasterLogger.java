package utils.logging;

public class MasterLogger {
    private static MasterLogger masterLogger;

    private MultiLogger multiLogger;

    private MasterLogger() {
        ConsoleLogger consoleLogger = new ConsoleLogger();
        FileLogger fileLogger = new FileLogger();
        multiLogger = MultiLogger.createMultiLogger(consoleLogger, fileLogger);
    }

    private static MasterLogger getInstance() {
        if (masterLogger == null) {
            masterLogger = new MasterLogger();
        }
        return masterLogger;
    }

    public static void log(String message, LogIdentifier logIdentifier, String methodName, String className) {
        getInstance().logByInstance(message, logIdentifier, methodName, className);
    }

    private void logByInstance(String message, LogIdentifier logIdentifier, String methodName, String className) {
        multiLogger.log(message, logIdentifier, methodName, className);
    }

    public static void info(String message, Class<?> clazz) {
        getInstance().logInfoByInstance(message, clazz);
    }

    private void logInfoByInstance(String message, Class<?> clazz) {
        multiLogger.info(message, clazz);
    }

    public static void error(String message, Class<?> clazz) {
        getInstance().logErrorByInstance(message, clazz);
    }

    private void logErrorByInstance(String message, Class<?> clazz) {
        multiLogger.error(message, clazz);
    }

    public static void fatal(String message, Class<?> clazz) {
        getInstance().logFatalErrorByInstance(message, clazz);
    }

    private void logFatalErrorByInstance(String message, Class<?> clazz) {
        multiLogger.fatal(message, clazz);
    }
}
