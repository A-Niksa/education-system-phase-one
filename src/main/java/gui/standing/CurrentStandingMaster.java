package gui.standing;

import gui.MainFrame;
import gui.Template;
import gui.main.MainMenu;
import logic.menus.standing.ScoresListMaster;
import logic.menus.standing.TranscriptsMaster;
import logic.models.abstractions.Course;
import logic.models.abstractions.Transcript;
import logic.models.roles.Professor;
import logic.models.roles.Student;
import utils.database.data.CoursesDB;
import utils.database.data.StudentsDB;
import utils.logging.MasterLogger;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;

public class CurrentStandingMaster extends Template {
    private enum CurrentMode {
        ID_LOOKUP,
        NAME_LOOKUP
    }

    private Professor operatingProfessor;
    private String[] studentIDs;
    private JComboBox<String> studentIDsBox;
    private JButton searchWithStudentID;
    private String selectedStudentID;
    private String[] studentNames;
    private JComboBox<String> studentNamesBox;
    private JButton searchWithStudentName;
    private String selectedStudentName;
    private Student selectedStudent;
    private Transcript transcript;
    private JTable transcriptTable;
    private JScrollPane scrollPane;
    private String[] columns;
    private String[][] data;
    private JButton seeGPAButton;
    private JLabel numberOfPassesCreditsLabel;
    private CurrentMode currentMode;

    public CurrentStandingMaster(MainFrame mainFrame, MainMenu mainMenu, Professor operatingProfessor) {
        super(mainFrame, mainMenu);
        this.operatingProfessor = operatingProfessor;
        drawPanel();
    }

    private void setInteractiveTableForFirstTime() {
        setTranscript();
        setSelectedStudent();
        setColumns();
        setTableData();
        transcriptTable = new JTable(data, columns);
        alignTable();
        setSeeGPAButton();
        setNumberOfPassesCreditsLabel();
        repaint();
        validate();
    }

    private void setInteractiveTable() {
        setTranscript();
        setSelectedStudent();
        setColumns();
        updateTable();
        setSeeGPAButton();
        setNumberOfPassesCreditsLabel();
        repaint();
        validate();
    }

    private void setTranscript() {
        if (currentMode == CurrentMode.ID_LOOKUP) {
            transcript = TranscriptsMaster.getTranscriptWithStudentID(selectedStudentID);
        } else { // in this case, will be NAME_LOOKUP by design
            transcript = TranscriptsMaster.getTranscriptWithStudentName(selectedStudentName);
        }
    }

    private void setSelectedStudent() {
        if (currentMode == CurrentMode.ID_LOOKUP) {
            selectedStudent = StudentsDB.getStudentWithID(selectedStudentID);
        } else { // in this case, will be NAME_LOOKUP by design
            selectedStudent = StudentsDB.getStudentWithName(selectedStudentName);
        }
    }

    private void setColumns() {
        if (currentMode == CurrentMode.ID_LOOKUP) {
            columns = new String[]{"Name and Surname", "Course Name", "Score"};
        } else { // PROFESSOR_VIEW or STUDENT_VIEW by design
            columns = new String[]{"Student ID", "Course Name", "Score"};
        }
    }

    void setTableData() {
        LinkedList<String> passedCoursesIDsList = transcript.getPassedCoursesIDs();
        data = new String[passedCoursesIDsList.size()][];
        Course course;
        for (int i = 0; i < passedCoursesIDsList.size(); i++) {
            course = CoursesDB.getCourseWithID(passedCoursesIDsList.get(i));
            if (currentMode == CurrentMode.ID_LOOKUP) {
                String studentNameAndSurname = selectedStudent.getFirstName() + " " + selectedStudent.getLastName();
                data[i] = new String[]{studentNameAndSurname,
                        course.getCourseName(),
                        course.getStudentsScoreString(selectedStudent)};
            } else { // NAME_LOOKUP by design
                data[i] = new String[]{selectedStudent.getStudentID(),
                        course.getCourseName(),
                        course.getStudentsScoreString(selectedStudent)};
            }
        }
    }

    private void alignTable() {
        transcriptTable.setRowHeight(30);
        scrollPane = new JScrollPane(transcriptTable);
        scrollPane.setBounds(50, 100, 885, 480);
        add(scrollPane);
    }

    private void updateTable() {
        setTableData();
        DefaultTableModel tableModel = new DefaultTableModel(data, columns);
        transcriptTable.setModel(tableModel);
    }

    private void setSeeGPAButton() {
        if (seeGPAButton != null) { // resetting the seeGPAButton if there was a previous seeGPAButton set up (which
            // won't be null)
            remove(seeGPAButton);
        }

        seeGPAButton = new JButton("GPA");
        seeGPAButton.setBounds(140, 622, 80, 30);
        add(seeGPAButton);

        seeGPAButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                String studentsTotalGPA = selectedStudent.getTotalGPAString();

                JOptionPane.showMessageDialog(mainFrame, "Total GPA: " + studentsTotalGPA);
                MasterLogger.info("deputy viewed the total GPA of the selected student", getClass());
            }
        });
    }


    private void setNumberOfPassesCreditsLabel() {
        if (numberOfPassesCreditsLabel != null) { // this condition helps us with finding labels for credits that we
            // need to clear because they belong to other students
            remove(numberOfPassesCreditsLabel);
        }

        numberOfPassesCreditsLabel = new JLabel("Number of Passed Credits: " + selectedStudent.getPassedCredits());
        numberOfPassesCreditsLabel.setBounds(762, 60, 180, 30);
        add(numberOfPassesCreditsLabel);
    }

    @Override
    protected void initializeComponents() {
        String departmentName = operatingProfessor.getDepartmentName();

        studentIDs = ScoresListMaster.getDepartmentsStudentsIDs(departmentName);
        studentIDsBox = new JComboBox<>(studentIDs);
        searchWithStudentID = new JButton("Search");

        studentNames = TranscriptsMaster.getDepartmentsStudentsNames(departmentName);
        studentNamesBox = new JComboBox<>(studentNames);
        searchWithStudentName = new JButton("Search");
    }

    @Override
    protected void alignComponents() {
        int currentX = 50;
        studentIDsBox.setBounds(currentX, 50, 150, 40);
        add(studentIDsBox);
        searchWithStudentID.setBounds(currentX + 155, 50, 125, 40);
        add(searchWithStudentID);
        currentX += 292;

        studentNamesBox.setBounds(currentX, 50, 150, 40);
        add(studentNamesBox);
        searchWithStudentName.setBounds(currentX + 155, 50, 125, 40);
        add(searchWithStudentName);
    }

    @Override
    protected void connectListeners() {
        searchWithStudentID.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                selectedStudentID = (String) studentIDsBox.getSelectedItem();
                currentMode = CurrentMode.ID_LOOKUP;

                checkIfFirstTimeAndSetScoresTable();

                MasterLogger.info("deputy opened the transcript of student (ID: " + selectedStudentID + ")",
                        getClass());
            }
        });

        searchWithStudentName.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                selectedStudentName = (String) studentNamesBox.getSelectedItem();
                currentMode = CurrentMode.NAME_LOOKUP;

                checkIfFirstTimeAndSetScoresTable();

                MasterLogger.info("deputy opened the transcript of student (name: " + selectedStudentName + ")",
                        getClass());
            }
        });
    }

    private void checkIfFirstTimeAndSetScoresTable() {
        if (transcriptTable == null) {
            setInteractiveTableForFirstTime();
        } else {
            setInteractiveTable();
        }
    }
}
