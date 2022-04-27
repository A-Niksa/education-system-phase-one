package gui.standing;

import gui.MainFrame;
import gui.Template;
import gui.main.MainMenu;
import logic.models.abstractions.StudentStatus;
import logic.models.roles.Student;
import logic.models.roles.User;

import javax.swing.*;
import java.util.LinkedList;

public class TemporaryStandingView extends Template {
    private Student student;
    private JTable scoresTable;
    private JScrollPane scrollPane;
    private String[] columns;
    private String[][] data;
    private LinkedList<StudentStatus> temporaryAcademicStatuses;
    private LinkedList<JButton> protestButtonsList;

    public TemporaryStandingView(MainFrame mainFrame, MainMenu mainMenu, User user) {
        super(mainFrame, mainMenu);
        student = (Student) user;
        columns = new String[] {"Course Name", "Current Score", "Protest to Score", "Instructor's Response"};
        temporaryAcademicStatuses = student.getTemporaryAcademicStatuses();
        setTableData(temporaryAcademicStatuses);
        protestButtonsList = new LinkedList<>();
        drawPanel();
    }

    void setTableData(LinkedList<StudentStatus> statusesList) {
        data = new String[statusesList.size()][];
        StudentStatus studentStatus;
        for (int i = 0; i < statusesList.size(); i++) {
            studentStatus = statusesList.get(i);
            data[i] = new String[]{studentStatus.getCourseName(),
                                    studentStatus.getScoreString(),
                                    studentStatus.getProtestOfStudent(),
                                    studentStatus.getResponseOfProfessor()};
        }
    }

    @Override
    protected void initializeComponents() {
        scoresTable = new JTable(data, columns);

        for (int i = 0; i < temporaryAcademicStatuses.size(); i++) {
            JButton button = new JButton("Protest");
            protestButtonsList.add(button);
        }
    }

    @Override
    protected void alignComponents() {
        scoresTable.setRowHeight(30);
        scrollPane = new JScrollPane(scoresTable);
        scrollPane.setBounds(50, 50, 785, 530);
        add(scrollPane);

        int currentX = 850, currentY = 72;
        int buttonHeight = 27;
        for (JButton protestButton : protestButtonsList) {
            protestButton.setBounds(currentX, currentY, 80, buttonHeight);
            add(protestButton);
            currentY += buttonHeight + 3;
        }
    }

    @Override
    protected void connectListeners() {
        JButton protestButton;
        StudentStatus studentStatus;
        for (int i = 0; i < protestButtonsList.size(); i++) {
            protestButton = protestButtonsList.get(i);
            studentStatus = temporaryAcademicStatuses.get(i);
            protestButton.addActionListener(new ProtestSubmissionHandler(mainFrame, this, studentStatus));
        }
    }

    public LinkedList<StudentStatus> getTemporaryAcademicStatuses() {
        return temporaryAcademicStatuses;
    }

    public String[] getColumns() {
        return columns;
    }

    public String[][] getData() {
        return data;
    }

    public JTable getScoresTable() {
        return scoresTable;
    }
}
