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

public class ProtestResponseHandler implements ActionListener {
    private MainFrame mainFrame;
    private TemporaryStandingManager temporaryStandingManager;
    private StudentStatus studentStatus;

    public ProtestResponseHandler(MainFrame mainFrame, TemporaryStandingManager temporaryStandingManager,
                                  StudentStatus studentStatus) {
        this.mainFrame = mainFrame;
        this.temporaryStandingManager = temporaryStandingManager;
        this.studentStatus = studentStatus;
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        MasterLogger.log("opened the protest response option panel", LogIdentifier.INFO,
                "actionPerformed", "gui.standing.ProtestResponseHandler");

        String studentProtest = studentStatus.getProtestOfStudent();
        if (studentProtest.equals("")) {
            JOptionPane.showMessageDialog(mainFrame, "There is no protest to respond to.");
            MasterLogger.log("cannot respond to an empty protest", LogIdentifier.ERROR,
                    "actionPerformed", "gui.standing.ProtestResponseHandler");
            return;
        }

        String response = JOptionPane.showInputDialog(mainFrame, "Please enter the desired response to the " +
                "student's protest:");
        studentStatus.setResponseOfProfessor(response);
        ScoresListManager.updateStudentsTranscript(studentStatus);
        String studentID = studentStatus.getStudentID();
        MasterLogger.log("responded to student protest (student ID: " + studentID + ")", LogIdentifier.INFO,
                "actionPerformed", "gui.standing.ProtestResponseHandler");

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