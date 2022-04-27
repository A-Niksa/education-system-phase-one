package gui.standing;

import gui.MainFrame;
import logic.menus.standing.ScoreFormatChecker;
import logic.models.abstractions.StudentStatus;
import utils.logging.LogIdentifier;
import utils.logging.MasterLogger;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ScoreAdditionHandler implements ActionListener {
    private MainFrame mainFrame;
    private TemporaryStandingManager temporaryStandingManager;
    private StudentStatus studentStatusClone;

    public ScoreAdditionHandler(MainFrame mainFrame, TemporaryStandingManager temporaryStandingManager,
                                StudentStatus studentStatusClone) {
        this.mainFrame = mainFrame;
        this.temporaryStandingManager = temporaryStandingManager;
        this.studentStatusClone = studentStatusClone;
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        MasterLogger.log("opened the temporary scoring option panel", LogIdentifier.INFO,
                "actionPerformed", "gui.standing.ScoreAdditionHandler");

        String scoreText = JOptionPane.showInputDialog(mainFrame, "Please enter the desired score (0-20):");
        Double score;
        if (scoreText == null) {
            return;
        }

        try {
            score = Double.valueOf(scoreText);
        } catch (NumberFormatException e) {
            MasterLogger.log("the entered score's format does match the format of a double value",
                    LogIdentifier.FATAL, "actionPerformed", "gui.standing.ScoreAdditionHandler");
            JOptionPane.showMessageDialog(mainFrame, "FATAL: The input has to be a number.");
            return;
        }

        if (!ScoreFormatChecker.isInValidRange(score)) {
            MasterLogger.log("the entered score is out of the valid range of [0, 20]", LogIdentifier.ERROR,
                    "actionPerformed", "gui.standing.ScoreAdditionHandler");
            JOptionPane.showMessageDialog(mainFrame, "The score has to be in the range between 0 and 20 " +
                    "(inclusive).");
            return;
        }

        studentStatusClone.setScore(score);
        studentStatusClone.setHasBeenScored(true);

        MasterLogger.log("professor drafted a temporary score for the selected student", LogIdentifier.INFO,
                "actionPerformed", "gui.standing.ScoreAdditionHandler");
        updateTable();
    }

    private void updateTable() {
        temporaryStandingManager.setTableData();
        String[] columns = temporaryStandingManager.getColumns();
        String[][] data = temporaryStandingManager.getData();
        DefaultTableModel tableModel = new DefaultTableModel(data, columns);
        JTable scoresTable = temporaryStandingManager.getScoresTable();
        scoresTable.setModel(tableModel);
    }
}
