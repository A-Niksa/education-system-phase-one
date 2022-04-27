package gui.standing;

import gui.MainFrame;
import gui.Template;
import gui.main.MainMenu;
import logic.menus.standing.ScoresListManager;
import logic.menus.standing.ScoresListMaster;
import logic.models.abstractions.StudentStatus;
import logic.models.roles.Professor;
import utils.database.data.StudentsDB;
import utils.logging.MasterLogger;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;

public class TemporaryStandingMaster extends Template {
    public enum CurrentMode {
        COURSE_VIEW,
        PROFESSOR_VIEW,
        STUDENT_VIEW
    }

    private Professor operatingProfessor;
    private String departmentName;
    private String[] coursesNames;
    private JComboBox<String> coursesBox;
    private String selectedCourseName;
    private JButton viewCourse;
    private String[] professorsNames;
    private JComboBox<String> professorsBox;
    private String selectedProfessorName;
    private JButton viewProfessor;
    private String[] studentsIDs;
    private JComboBox<String> studentsBox;
    private String selectedStudentID;
    private JButton viewStudent;
    private JTable scoresTable;
    private JScrollPane scrollPane;
    private String[] columns;
    private String[][] data;
    private LinkedList<StudentStatus> studentStatusesList;
    private JButton statsButton;
    private CurrentMode currentMode;

    public TemporaryStandingMaster(MainFrame mainFrame, MainMenu mainMenu, Professor operatingProfessor) {
        super(mainFrame, mainMenu);
        this.operatingProfessor = operatingProfessor;
        departmentName = operatingProfessor.getDepartmentName();
        drawPanel();
    }

    private void setInteractiveTableForFirstTime() {
        setStudentStatusesList();
        setColumns();
        setTableData();
        scoresTable = new JTable(data, columns);
        alignTable();
        setStatsButton();
        repaint();
        validate();
    }

    private void setInteractiveTable() {
        setStudentStatusesList();
        setColumns();
        updateTable();
        setStatsButton();
        repaint();
        validate();
    }

    private void setColumns() {
        if (currentMode == CurrentMode.COURSE_VIEW) {
            columns = new String[] {"Name and Surname", "Current Score", "Student's Protest", "Instructor's Response",
                    "Finalized"};
        } else { // PROFESSOR_VIEW or STUDENT_VIEW by design
            columns = new String[] {"Subject", "Name and Surname", "Current Score", "Student's Protest",
                    "Instructor's Response", "Finalized"};
        }
    }

    private void updateTable() {
        setTableData();
        DefaultTableModel tableModel = new DefaultTableModel(data, columns);
        scoresTable.setModel(tableModel);
    }

    private void setStudentStatusesList() {
        switch (currentMode) {
            case COURSE_VIEW:
                studentStatusesList = ScoresListManager.getStudentStatusesWithCourseName(selectedCourseName);
                break;
            case PROFESSOR_VIEW:
                studentStatusesList = ScoresListMaster.getStudentStatusesWithProfessorName(selectedProfessorName);
                break;
            case STUDENT_VIEW:
                studentStatusesList = ScoresListMaster.getStudentStatusesWithStudentName(selectedStudentID);
                break;
        }
    }

    void setTableData() {
        data = new String[studentStatusesList.size()][];
        StudentStatus studentStatus;
        String studentID;
        String studentNameAndSurname;
        for (int i = 0; i < studentStatusesList.size(); i++) {
            studentStatus = studentStatusesList.get(i);
            studentID = studentStatus.getStudentID();
            studentNameAndSurname = StudentsDB.getStudentsNameWithID(studentID);
            if (currentMode == CurrentMode.COURSE_VIEW) {
                data[i] = new String[] {studentNameAndSurname,
                        studentStatus.getScoreString(),
                        studentStatus.getProtestOfStudent(),
                        studentStatus.getResponseOfProfessor(),
                        studentStatus.scoreIsFinalizedString()};
            } else { // PROFESSOR_VIEW or STUDENT_VIEW by design
                data[i] = new String[] {studentStatus.getCourseName(),
                        studentNameAndSurname,
                        studentStatus.getScoreString(),
                        studentStatus.getProtestOfStudent(),
                        studentStatus.getResponseOfProfessor(),
                        studentStatus.scoreIsFinalizedString()};
            }
        }
    }

    private void alignTable() {
        scoresTable.setRowHeight(30);
        scrollPane = new JScrollPane(scoresTable);
        scrollPane.setBounds(50, 100, 885, 480);
        add(scrollPane);
    }

    private void setStatsButton() {
        if (currentMode == CurrentMode.COURSE_VIEW) {
            if (statsButton != null) { // resetting the statsButton if there was a previous statsButton set up (which
                // won't be null)
                remove(statsButton);
            }

            statsButton = new JButton("Stats");
            statsButton.setBounds(140, 622, 80, 30);
            System.out.println(selectedCourseName);
            statsButton.addActionListener(new StatsViewHandler(mainFrame, this, selectedCourseName,
                    departmentName));
            add(statsButton);
        } else if (statsButton != null) {
            remove(statsButton);
        }
    }

    @Override
    protected void initializeComponents() {
        String departmentName = operatingProfessor.getDepartmentName();

        coursesNames = ScoresListMaster.getDepartmentsCoursesNames(departmentName);
        coursesBox = new JComboBox<>(coursesNames);
        viewCourse = new JButton("View Course");

        professorsNames = ScoresListMaster.getDepartmentsProfessorsNames(departmentName);
        professorsBox = new JComboBox<>(professorsNames);
        viewProfessor = new JButton("View Professor");

        studentsIDs = ScoresListMaster.getDepartmentsStudentsIDs(departmentName);
        studentsBox = new JComboBox<>(studentsIDs);
        viewStudent = new JButton("View Student");
    }

    @Override
    protected void alignComponents() {
        int currentX = 50;
        coursesBox.setBounds(currentX, 50, 150, 40);
        add(coursesBox);
        viewCourse.setBounds(currentX + 155, 50, 125, 40);
        add(viewCourse);
        currentX += 301;

        professorsBox.setBounds(currentX, 50, 150, 40);
        add(professorsBox);
        viewProfessor.setBounds(currentX + 155, 50, 125, 40);
        add(viewProfessor);
        currentX += 301;

        studentsBox.setBounds(currentX, 50, 150, 40);
        add(studentsBox);
        viewStudent.setBounds(currentX + 155, 50, 125, 40);
        add(viewStudent);
    }

    @Override
    protected void connectListeners() {
        viewCourse.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                selectedCourseName = (String) coursesBox.getSelectedItem();
                currentMode = CurrentMode.COURSE_VIEW;

                checkIfFirstTimeAndSetScoresTable();

                MasterLogger.info("deputy opened the scores tables of " + selectedCourseName, getClass());
            }
        });

        viewProfessor.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                selectedProfessorName = (String) professorsBox.getSelectedItem();
                currentMode = CurrentMode.PROFESSOR_VIEW;

                checkIfFirstTimeAndSetScoresTable();

                MasterLogger.info("deputy opened the scores table of professor " + selectedProfessorName,
                        getClass());
            }
        });

        viewStudent.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                selectedStudentID = (String) studentsBox.getSelectedItem();
                currentMode = CurrentMode.STUDENT_VIEW;

                checkIfFirstTimeAndSetScoresTable();

                MasterLogger.info("deputy opened the scores table of student with ID: " + selectedStudentID,
                        getClass());
            }
        });
    }

    private void checkIfFirstTimeAndSetScoresTable() {
        if (scoresTable == null) {
            setInteractiveTableForFirstTime();
        } else {
            setInteractiveTable();
        }
    }
}