package gui.enrolment;

import gui.MainFrame;
import gui.Template;
import gui.main.MainMenu;
import logic.models.abstractions.Course;
import logic.models.roles.Professor;
import utils.database.data.CoursesDB;
import utils.logging.MasterLogger;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;

public class CoursesListView extends Template {
    private JTable coursesTable;
    private JScrollPane scrollPane;
    private String[] columns;
    private String[][] data;
    private JTextField courseIDFilterField;
    private JButton filterOnCourseID;
    private JTextField numberOfCreditsFilterField;
    private JButton filterOnNumberOfCredits;
    private JComboBox<String> courseLevelFilterCombo;
    private JButton filterOnCourseLevel;
    private JButton resetButton;

    public CoursesListView(MainFrame mainFrame, MainMenu mainMenu) {
        super(mainFrame, mainMenu);
        columns = new String[]{"Course ID", "Name", "Department", "Exam Date and Time", "Instructor",
                "Number of Credits", "Course Level"};
        setTableData(CoursesDB.getList());
        drawPanel();
    }

    private void setTableData(LinkedList<Course> coursesList) {
        data = new String[coursesList.size()][];
        Course course;
        Professor teachingProfessor;
        for (int i = 0; i < coursesList.size(); i++) {
            course = coursesList.get(i);
            teachingProfessor = course.getTeachingProfessor();
            data[i] = new String[]{course.getCourseID(),
                    course.getCourseName(),
                    course.getDepartmentName(),
                    course.getExamTimeString(),
                    teachingProfessor.getFirstName() + " " + teachingProfessor.getLastName(),
                    course.getNumberOfCredits() + "",
                    course.getCourseLevelString()};
        }
    }

    @Override
    protected void initializeComponents() {
        coursesTable = new JTable(data, columns);
        courseIDFilterField = new JTextField("Course ID...");
        filterOnCourseID = new JButton("Filter");
        numberOfCreditsFilterField = new JTextField("Number of Credits...");
        filterOnNumberOfCredits = new JButton("Filter");
        courseLevelFilterCombo = new JComboBox<>(new String[]{"Bachelors", "Graduate", "PhD"});
        filterOnCourseLevel = new JButton("Filter");
        resetButton = new JButton("Reset");
    }

    @Override
    protected void alignComponents() {
        coursesTable.setRowHeight(25);
        scrollPane = new JScrollPane(coursesTable);
        scrollPane.setBounds(50, 120, 885, 400);
        add(scrollPane);

        int currentX = 70;
        courseIDFilterField.setBounds(currentX, 50, 150, 40);
        add(courseIDFilterField);
        filterOnCourseID.setBounds(currentX + 160, 50, 70, 40);
        add(filterOnCourseID);
        currentX += 250;

        numberOfCreditsFilterField.setBounds(currentX, 50, 150, 40);
        add(numberOfCreditsFilterField);
        filterOnNumberOfCredits.setBounds(currentX + 160, 50, 70, 40);
        add(filterOnNumberOfCredits);
        currentX += 250;

        courseLevelFilterCombo.setBounds(currentX, 50, 150, 40);
        add(courseLevelFilterCombo);
        filterOnCourseLevel.setBounds(currentX + 160, 50, 70, 40);
        add(filterOnCourseLevel);
        currentX += 280;

        resetButton.setBounds(currentX, 50, 70, 40);
        add(resetButton);
    }

    @Override
    protected void connectListeners() {
        filterOnCourseID.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                String desiredCourseID = courseIDFilterField.getText();
                MasterLogger.info("filtered courses based on course id: " + desiredCourseID, getClass());
                setTableData(CoursesDB.getFilteredList(FilterKey.COURSE_ID, desiredCourseID));
                DefaultTableModel tableModel = new DefaultTableModel(data, columns);
                coursesTable.setModel(tableModel);
            }
        });

        filterOnNumberOfCredits.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                String desiredNumberOfCredits = numberOfCreditsFilterField.getText();
                MasterLogger.info("filtered courses based on number of credits: " +
                        desiredNumberOfCredits, getClass());
                setTableData(CoursesDB.getFilteredList(FilterKey.NUMBER_OF_CREDITS, desiredNumberOfCredits + ""));
                DefaultTableModel tableModel = new DefaultTableModel(data, columns);
                coursesTable.setModel(tableModel);
            }
        });

        filterOnCourseLevel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                String desiredCourseLevel = (String) courseLevelFilterCombo.getSelectedItem();
                MasterLogger.info("filtered courses based on course level: " + desiredCourseLevel, getClass());
                setTableData(CoursesDB.getFilteredList(FilterKey.COURSE_LEVEL, desiredCourseLevel));
                DefaultTableModel tableModel = new DefaultTableModel(data, columns);
                coursesTable.setModel(tableModel);
            }
        });

        resetButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                MasterLogger.info("filters were reset", getClass());
                setTableData(CoursesDB.getList());
                DefaultTableModel tableModel = new DefaultTableModel(data, columns);
                coursesTable.setModel(tableModel);
            }
        });
    }
}
