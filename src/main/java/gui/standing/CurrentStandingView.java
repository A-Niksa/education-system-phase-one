package gui.standing;

import gui.MainFrame;
import gui.Template;
import gui.main.MainMenu;
import logic.models.abstractions.Course;
import logic.models.abstractions.Transcript;
import logic.models.roles.Student;
import logic.models.roles.User;
import utils.database.data.CoursesDB;

import javax.swing.*;
import java.util.LinkedList;

public class CurrentStandingView extends Template {
    private Student student;
    private Transcript transcript;
    private JLabel totalGPA;
    private JLabel numberOfPassedCredits;
    private JTable transcriptTable;
    private JScrollPane scrollPane;
    private String[] columns;
    private String[][] data;

    public CurrentStandingView(MainFrame mainFrame, MainMenu mainMenu, User user) {
        super(mainFrame, mainMenu);
        student = (Student) user;
        transcript = student.getTranscript();
        columns = new String[]{"Course Name", "Score"};
        setTableData();
        drawPanel();
    }

    private void setTableData() {
        LinkedList<String> passedCoursesIDsList = transcript.getPassedCoursesIDs();
        data = new String[passedCoursesIDsList.size()][];
        Course course;
        for (int i = 0; i < passedCoursesIDsList.size(); i++) {
            course = CoursesDB.getCourseWithID(passedCoursesIDsList.get(i));
            data[i] = new String[]{course.getCourseName(),
                    course.getStudentsScoreString(student)};
        }
    }

    @Override
    protected void initializeComponents() {
        totalGPA = new JLabel("GPA: " + student.getTotalGPAString());
        numberOfPassedCredits = new JLabel("Number of Passed Credits: " + student.getPassedCredits());
        transcriptTable = new JTable(data, columns);
    }

    @Override
    protected void alignComponents() {
        transcriptTable.setRowHeight(25);
        scrollPane = new JScrollPane(transcriptTable);
        scrollPane.setBounds(50, 120, 885, 400);
        add(scrollPane);

        numberOfPassedCredits.setBounds(50, 60, 300, 50);
        add(numberOfPassedCredits);
        totalGPA.setBounds(866, 60, 300, 50);
        add(totalGPA);
    }

    @Override
    protected void connectListeners() {
    }
}
