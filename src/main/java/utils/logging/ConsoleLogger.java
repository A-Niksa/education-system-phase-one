package utils.logging;

import java.io.PrintStream;

public class ConsoleLogger extends StreamLogger {
    public ConsoleLogger() {
        super(System.out);
    }
}
