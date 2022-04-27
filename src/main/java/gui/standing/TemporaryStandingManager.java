package gui.standing;

import gui.MainFrame;
import gui.Template;
import gui.main.MainMenu;
import logic.menus.standing.ScoresListManager;
import logic.models.abstractions.StudentStatus;
import logic.models.roles.Professor;
import utils.database.data.StudentsDB;
import utils.logging.MasterLogger;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;

public class TemporaryStandingManager extends Template {
    private Professor operatingProfessor;
    private String[] coursesNames;
    private JComboBox<String> coursesBox;
    private String selectedCourseName;
    private JButton viewCourse;
    private JTable scoresTable;
    private JScrollPane scrollPane;
    private String[] columns;
    private String[][] data;
    private JButton saveTemporaryScores;
    private JButton finalizeAllScores;
    private LinkedList<StudentStatus> studentStatusesList;
    private LinkedList<StudentStatus> studentStatusesListClone;
    private LinkedList<JButton> addScoreButtonsList;
    private LinkedList<JButton> respondToProtestButtonsList;

    public TemporaryStandingManager(MainFrame mainFrame, MainMenu mainMenu, Professor operatingProfessor) {
        super(mainFrame, mainMenu);
        this.operatingProfessor = operatingProfessor;
        columns = new String[] {"Name and Surname", "Current Score", "Student's Protest", "Your Response", "Finalized"};
        drawPanel();
    }

    private void setInteractiveTableForFirstTime() {
        setStudentStatusesList();
        setTableData();
        scoresTable = new JTable(data, columns);
        alignTable();
        initializeInteractiveButtons();
        alignInteractiveButtons();
        connectInteractiveListeners();
        repaint();
        validate();
    }

    private void setInteractiveTable() {
        removePreviousButtons();
        setStudentStatusesList();
        updateTable();
        initializeInteractiveButtons();
        alignInteractiveButtons();
        connectInteractiveListeners();
        repaint();
        validate();
    }

    private void removePreviousButtons() {
        for (int i = 0; i < studentStatusesList.size(); i++) {
            remove(addScoreButtonsList.get(i));
            remove(respondToProtestButtonsList.get(i));
        }
    }

    private void updateTable() {
        setTableData();
        DefaultTableModel tableModel = new DefaultTableModel(data, columns);
        scoresTable.setModel(tableModel);
    }

    private void setStudentStatusesList() {
        studentStatusesList = ScoresListManager.getStudentStatusesWithCourseName(selectedCourseName);
        studentStatusesListClone = (LinkedList<StudentStatus>) studentStatusesList.clone();
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
            data[i] = new String[] {studentNameAndSurname,
                                    studentStatus.getScoreString(),
                                    studentStatus.getProtestOfStudent(),
                                    studentStatus.getResponseOfProfessor(),
                                    studentStatus.scoreIsFinalizedString()};
        }
    }

    private void alignTable() {
        scoresTable.setRowHeight(30);
        scrollPane = new JScrollPane(scoresTable);
        scrollPane.setBounds(50, 100, 735, 480);
        add(scrollPane);
    }

    private void initializeInteractiveButtons() {
        addScoreButtonsList = new LinkedList<>();
        respondToProtestButtonsList = new LinkedList<>();

        for (int i = 0; i < studentStatusesList.size(); i++) {
            JButton addScoreButton = new JButton("Score");
            addScoreButtonsList.add(addScoreButton);
            JButton respondButton = new JButton("Respond");
            respondToProtestButtonsList.add(respondButton);
        }
    }

    private void alignInteractiveButtons() {
        int currentX = 794, currentY = 122;
        int buttonHeight = 27;
        for (int i = 0; i < studentStatusesList.size(); i++) {
            JButton addScoreButton = addScoreButtonsList.get(i);
            addScoreButton.setBounds(currentX, currentY, 90, buttonHeight);
            add(addScoreButton);

            JButton respondButton = respondToProtestButtonsList.get(i);
            respondButton.setBounds(currentX + 95, currentY, 90, buttonHeight);
            add(respondButton);

            currentY += buttonHeight + 3;
        }
    }

    private void connectInteractiveListeners() {
        JButton addScoreButton;
        JButton respondButton;
        StudentStatus studentStatus;
        StudentStatus studentStatusClone;
        for (int i = 0; i < studentStatusesList.size(); i++) {
            addScoreButton = addScoreButtonsList.get(i);
            respondButton = respondToProtestButtonsList.get(i);
            studentStatus = studentStatusesList.get(i);
            studentStatusClone = studentStatusesListClone.get(i);

            addScoreButton.addActionListener(new ScoreAdditionHandler(mainFrame, this,
                    studentStatusClone));
            respondButton.addActionListener(new ProtestResponseHandler(mainFrame, this,
                    studentStatus));
        }
    }

    @Override
    protected void initializeComponents() {
        coursesNames = ScoresListManager.getProfessorsCoursesNames(operatingProfessor);
        coursesBox = new JComboBox<>(coursesNames);
        viewCourse = new JButton("View Course");

        saveTemporaryScores = new JButton("Save Temporary Scores");
        finalizeAllScores = new JButton("Finalize Scores");
    }

    @Override
    protected void alignComponents() {
        coursesBox.setBounds(330, 50, 200, 30);
        add(coursesBox);
        viewCourse.setBounds(540, 50, 110, 30);
        add(viewCourse);

        saveTemporaryScores.setBounds(140, 622, 185, 30);
        add(saveTemporaryScores);
        finalizeAllScores.setBounds(340, 622, 123, 30);
        add(finalizeAllScores);
    }

    @Override
    protected void connectListeners() {
        viewCourse.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                String courseName = (String) coursesBox.getSelectedItem();
                setSelectedCourseName(courseName);

                if (scoresTable == null) {
                    setInteractiveTableForFirstTime();
                } else {
                    setInteractiveTable();
                }

                MasterLogger.info("professor opened the scoring table of " + courseName, getClass());
            }
        });

        saveTemporaryScores.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if (selectedCourseName == null) {
                    MasterLogger.error("no course has been selected", getClass());
                    JOptionPane.showMessageDialog(mainFrame, "You have to choose a course.");
                    return;
                }

                if (!ScoresListManager.allStudentsHaveScores(studentStatusesListClone)) {
                    MasterLogger.error("cannot save temporary scores; some students don't have scores", getClass());
                    JOptionPane.showMessageDialog(mainFrame, "You cannot save temporary scores before assigning" +
                            " temporary scores to all students in a course.");
                    return;
                }

                ScoresListManager.mapAllStudentsToScores(studentStatusesListClone);
                MasterLogger.info("professor saved temporary scores of " + selectedCourseName, getClass());
            }
        });

        finalizeAllScores.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if (selectedCourseName == null) {
                    MasterLogger.error("no course has been selected", getClass());
                    JOptionPane.showMessageDialog(mainFrame, "You have to choose a course.");
                    return;
                }

                if (!ScoresListManager.allStudentsHaveBeenGivenScores(studentStatusesList)) {
                    MasterLogger.error("cannot finalize score; scores have to be temporarily saved at first",
                            getClass());
                    JOptionPane.showMessageDialog(mainFrame, "You have to assign temporary scores to all students" +
                            " of this course before commencing the finalization process.");
                    return;
                }

                ScoresListManager.finalizeAllScores(studentStatusesList, operatingProfessor.getDepartmentName());
                // operatingProfessor.getDepartmentName() gives us the department name of the course
                // ^ this is a valid choice since professors teach at their own departments
                MasterLogger.info("professor finalized scores of " + selectedCourseName, getClass());
            }
        });
    }

    public void setSelectedCourseName(String selectedCourseName) {
        this.selectedCourseName = selectedCourseName;
    }

    public String getSelectedCourseName() {
        return selectedCourseName;
    }

    public LinkedList<StudentStatus> getStudentStatusesList() {
        return studentStatusesList;
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