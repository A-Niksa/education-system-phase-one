package gui.enrolment;

import gui.MainFrame;
import gui.Template;
import gui.addition.CourseAdder;
import gui.addition.ProfessorAdderOfDean;
import gui.main.MainMenu;
import logic.menus.enrolment.DepartmentListManager;
import logic.models.abstractions.Course;
import logic.models.roles.Professor;
import utils.logging.MasterLogger;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;

public class CoursesListEditor extends Template {
    private Professor operatingProfessor;
    private JButton goBackButton;
    private JButton addCourseButton;
    private JTable coursesTable;
    private String[] columns;
    private String[][] data;
    private LinkedList<Course> coursesList;
    private LinkedList<JButton> editButtonsList;

    public CoursesListEditor(MainFrame mainFrame, MainMenu mainMenu, Professor operatingProfessor) {
        super(mainFrame, mainMenu);
        this.operatingProfessor = operatingProfessor;
        columns = new String[] {"Course ID", "Name", "Exam Date and Time", "Instructor", "Number of Credits",
                "Course Level"};
        coursesList = DepartmentListManager.getDepartmentCourses(operatingProfessor);
        setTableData();
        drawPanel();
    }

    private void setTableData() {
        data = new String[coursesList.size()][];
        Course course;
        Professor teachingProfessor;
        for (int i = 0; i < coursesList.size(); i++) {
            course = coursesList.get(i);
            teachingProfessor = course.getTeachingProfessor();
            data[i] = new String[] {course.getCourseID(),
                    course.getCourseName(),
                    course.getExamTimeString(),
                    teachingProfessor.getFirstName() + " " + teachingProfessor.getLastName(),
                    course.getNumberOfCredits() + "",
                    course.getCourseLevelString()};
        }
    }

    @Override
    protected void initializeComponents() {
        editButtonsList = new LinkedList<>();

        goBackButton = new JButton("Back");
        addCourseButton = new JButton("Add");

        coursesTable = new JTable(data, columns);

        for (int i = 0; i < coursesList.size(); i++) {
            JButton button = new JButton("Edit");
            editButtonsList.add(button);
        }
    }

    @Override
    protected void alignComponents() {
        goBackButton.setBounds(140, 622, 80, 30);
        add(goBackButton);
        addCourseButton.setBounds(236, 622, 80, 30);
        add(addCourseButton);

        coursesTable.setRowHeight(30);
        JScrollPane scrollPane = new JScrollPane(coursesTable);
        scrollPane.setBounds(50, 50, 785, 530);
        add(scrollPane);

        int currentX = 850, currentY = 72;
        int buttonHeight = 27;
        for (JButton editButton : editButtonsList) {
            editButton.setBounds(currentX, currentY, 80, buttonHeight);
            add(editButton);
            currentY += buttonHeight + 3;
        }
    }

    @Override
    protected void connectListeners() {
        goBackButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                MasterLogger.info("went back to courses list view", getClass());
                mainFrame.setCurrentPanel(new CoursesListManager(mainFrame, mainMenu, operatingProfessor));
            }
        });

        addCourseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                MasterLogger.info("deputy opened the course addition section", getClass());
                mainFrame.setCurrentPanel(new CourseAdder(mainFrame, mainMenu, operatingProfessor));
            }
        });

        JButton editButton;
        Course editableCourse;
        for (int i = 0; i < editButtonsList.size(); i++) {
            editButton = editButtonsList.get(i);
            editableCourse = coursesList.get(i);
            editButton.addActionListener(new CourseEditHandler(mainFrame, mainMenu, operatingProfessor, editableCourse));
        }
    }
}
