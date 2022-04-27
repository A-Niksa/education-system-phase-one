package gui.standing;

import gui.MainFrame;
import logic.menus.standing.ScoresListManager;
import logic.models.abstractions.StudentStatus;
import utils.logging.LogIdentifier;
import utils.logging.MasterLogger;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;

public class ProtestSubmissionHandler implements ActionListener {
    private MainFrame mainFrame;
    private TemporaryStandingView temporaryStandingView;
    private StudentStatus correspondingStatus;

    public ProtestSubmissionHandler(MainFrame mainFrame, TemporaryStandingView temporaryStandingView, StudentStatus correspondingStatus) {
        this.mainFrame = mainFrame;
        this.temporaryStandingView = temporaryStandingView;
        this.correspondingStatus = correspondingStatus;
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        MasterLogger.log("opened the protest submission option panel", LogIdentifier.INFO,
                "actionPerformed", "gui.standing.TemporaryStanding");

        String protest = JOptionPane.showInputDialog(mainFrame, "Please enter the desired protest message:");
        MasterLogger.log("student protested to their score of " + correspondingStatus.getCourseName(),
                LogIdentifier.INFO, "actionPerformed", "gui.standing.TemporaryStanding");
        correspondingStatus.setProtestOfStudent(protest);
        ScoresListManager.updateStudentsTranscript(correspondingStatus);

        updateTable();
    }

    private void updateTable() {
        LinkedList<StudentStatus> temporaryAcademicStatus = temporaryStandingView.getTemporaryAcademicStatuses();
        temporaryStandingView.setTableData(temporaryAcademicStatus);
        String[] columns = temporaryStandingView.getColumns();
        String[][] data = temporaryStandingView.getData();
        DefaultTableModel tableModel = new DefaultTableModel(data, columns);
        JTable scoresTable = temporaryStandingView.getScoresTable();
        scoresTable.setModel(tableModel);
    }
}
