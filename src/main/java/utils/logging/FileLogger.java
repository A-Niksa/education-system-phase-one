package utils.logging;

import java.io.*;

import static utils.Timing.TimeManager.getTime;

public class FileLogger extends StreamLogger {
    private static final String pathToLog = "src/main/resources/logs/master.log";

    public FileLogger() {
        super(initializeFileStream());
    }

    private static PrintStream initializeFileStream() {
        File file = new File(pathToLog);
        if (!file.exists()) {
            try {
                if (!file.createNewFile()) {
                    System.out.println(String.format("%s - %s - %s - %s - %s", getTime(), LogIdentifier.FATAL,
                            "The log file could not be created", "initializeFileStream", "utils.logging.FileLogger"));
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            return new PrintStream(new FileOutputStream(file, true));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }
}
