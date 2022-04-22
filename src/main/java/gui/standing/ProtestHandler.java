package gui.standing;

import gui.MainFrame;
import logic.models.abstractions.StudentStatus;
import utils.logging.LogIdentifier;
import utils.logging.MasterLogger;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;

public class ProtestHandler implements ActionListener {
    private MainFrame mainFrame;
    private TemporaryStanding temporaryStanding;
    private StudentStatus correspondingStatus;

    public ProtestHandler(MainFrame mainFrame, TemporaryStanding temporaryStanding, StudentStatus correspondingStatus) {
        this.mainFrame = mainFrame;
        this.temporaryStanding = temporaryStanding;
        this.correspondingStatus = correspondingStatus;
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        String protest = JOptionPane.showInputDialog(mainFrame, "Please enter the desired protest message:");
        MasterLogger.log("student protested to their score of " + correspondingStatus.getCourseName(),
                LogIdentifier.INFO, "actionPerformed", "gui.standing.TemporaryStanding");
        correspondingStatus.setProtestOfStudent(protest);
        LinkedList<StudentStatus> temporaryAcademicStatus = temporaryStanding.getTemporaryAcademicStatuses();
        temporaryStanding.setTableData(temporaryAcademicStatus);
        String[] columns = temporaryStanding.getColumns();
        String[][] data = temporaryStanding.getData();
        DefaultTableModel tableModel = new DefaultTableModel(data, columns);
        JTable scoresTable = temporaryStanding.getScoresTable();
        scoresTable.setModel(tableModel);
    }
}
