import gui.MainFrame;
import utils.database.testdata.TestDataBuilder;
import utils.logging.LogIdentifier;
import utils.logging.MasterLogger;

public class Main {
    public static void main(String[] args) {
        MasterLogger.log("start of program", LogIdentifier.INFO, "psvm", "Main");
//        TestDataBuilder.build();
        new MainFrame();
    }
}