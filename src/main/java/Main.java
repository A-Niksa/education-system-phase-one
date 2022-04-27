import gui.MainFrame;
import utils.logging.LogIdentifier;
import utils.logging.MasterLogger;

public class Main {
    public static void main(String[] args) {
        MasterLogger.log("start of program", LogIdentifier.INFO, "psvm", "Main");
        new MainFrame();
    }
}