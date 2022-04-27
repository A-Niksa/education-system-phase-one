package gui.enrolment;

import gui.MainFrame;
import gui.Template;
import gui.main.MainMenu;
import logic.models.abstractions.Course;
import logic.models.abstractions.Department;
import logic.models.roles.Professor;
import utils.database.data.CoursesDB;
import utils.database.data.DepartmentsDB;
import utils.database.data.ProfessorsDB;
import utils.database.data.StudentsDB;
import utils.logging.MasterLogger;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static logic.menus.addition.CourseConstruction.getLevelEnum;

public class CourseEditor extends Template {
    private Professor deputy;
    private Course course;
    private JLabel courseNameLabel;
    private JTextField newCourseName;
    private JButton changeCourseName;
    private JTextField newCourseID;
    private JButton changeCourseID;
    private JTextField newInstructor;
    private JButton changeInstructor;
    private JTextField newNumberOfCredits;
    private JButton changeNumberOfCredits;
    private JComboBox<String> newCourseLevel;
    private JButton changeCourseLevel;
    private JButton goBackButton;
    private JButton removeCourse;

    public CourseEditor(MainFrame mainFrame, MainMenu mainMenu, Professor deputy, Course course) {
        super(mainFrame, mainMenu);
        this.deputy = deputy;
        this.course = course;
        drawPanel();
    }

    @Override
    protected void initializeComponents() {
        goBackButton = new JButton("Back");

        courseNameLabel = new JLabel(course.getCourseName(), SwingConstants.CENTER);

        newCourseName = new JTextField("New Course Name...");
        changeCourseName = new JButton("Change");
        newCourseID = new JTextField("New Course ID...");
        changeCourseID = new JButton("Change");
        newInstructor = new JTextField("Name of New Instructor...");
        changeInstructor = new JButton("Change");
        newNumberOfCredits = new JTextField("Changed Number of Credits...");
        changeNumberOfCredits = new JButton("Change");
        newCourseLevel = new JComboBox<>(new String[]{"Bachelors", "Graduate", "PhD"});
        changeCourseLevel = new JButton("Change");

        removeCourse = new JButton("Remove Course");
    }

    @Override
    protected void alignComponents() {
        goBackButton.setBounds(140, 622, 80, 30);
        add(goBackButton);

        courseNameLabel.setBounds(405, 110, 200, 50);
        courseNameLabel.setFont(new Font("", Font.BOLD, 16));
        add(courseNameLabel);

        int currentX = 300, currentY = 200;
        newCourseName.setBounds(currentX, currentY, 250, 30);
        add(newCourseName);
        changeCourseName.setBounds(currentX + 265, currentY, 150, 30);
        add(changeCourseName);
        currentY += 45;

        newCourseID.setBounds(currentX, currentY, 250, 30);
        add(newCourseID);
        changeCourseID.setBounds(currentX + 265, currentY, 150, 30);
        add(changeCourseID);
        currentY += 45;

        newInstructor.setBounds(currentX, currentY, 250, 30);
        add(newInstructor);
        changeInstructor.setBounds(currentX + 265, currentY, 150, 30);
        add(changeInstructor);
        currentY += 45;

        newNumberOfCredits.setBounds(currentX, currentY, 250, 30);
        add(newNumberOfCredits);
        changeNumberOfCredits.setBounds(currentX + 265, currentY, 150, 30);
        add(changeNumberOfCredits);
        currentY += 45;

        newCourseLevel.setBounds(currentX, currentY, 250, 30);
        add(newCourseLevel);
        changeCourseLevel.setBounds(currentX + 265, currentY, 150, 30);
        add(changeCourseLevel);
        currentY += 45;

        removeCourse.setBounds(currentX, currentY, 415, 30);
        add(removeCourse);
    }

    @Override
    protected void connectListeners() {
        goBackButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                MasterLogger.info("went back to courses list editor", getClass());
                mainFrame.setCurrentPanel(new CoursesListEditor(mainFrame, mainMenu, deputy));
            }
        });

        changeCourseName.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                String courseName = newCourseName.getText();
                String previousCourseName = courseNameLabel.getText();
                course.setCourseName(courseName);
                course.updateInDatabase();
                courseNameLabel.setText(courseName);
                MasterLogger.info("course name changed from " + previousCourseName + " to " + courseName,
                        getClass());
            }
        });

        changeCourseID.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                String courseID = newCourseID.getText();
                course.setCourseID(courseID);
                course.updateInDatabase();
                MasterLogger.info(courseNameLabel.getText() + "'s ID changed to " + courseID, getClass());
            }
        });

        changeInstructor.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                String instructorName = newInstructor.getText();
                Professor professor = ProfessorsDB.getProfessorWithName(instructorName);
                if (professor == null) {
                    JOptionPane.showMessageDialog(mainFrame,
                            "No such professor exists in your department.");
                    MasterLogger.error("entered non-existent professor name", getClass());
                    return;
                }

                course.setTeachingProfessor(professor);
                course.updateInDatabase();
                MasterLogger.info(courseNameLabel.getText() + "'s instructor changed to " + instructorName,
                        getClass());
            }
        });

        changeNumberOfCredits.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                int numberOfCredits = Integer.parseInt(newNumberOfCredits.getText());
                course.setNumberOfCredits(numberOfCredits);
                course.updateInDatabase();
                MasterLogger.info(courseNameLabel.getText() + "'s number of credits changed to " + numberOfCredits,
                        getClass());
            }
        });

        changeCourseLevel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                String courseLevelString = (String) newCourseLevel.getSelectedItem();
                Course.CourseLevel courseLevel = getLevelEnum(courseLevelString);
                course.setCourseLevel(courseLevel);
                course.updateInDatabase();
                MasterLogger.info(courseNameLabel.getText() + "'s level changed to " + courseLevelString,
                        getClass());
            }
        });

        removeCourse.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                Department department = DepartmentsDB.getProfessorsDepartment(deputy);

                StudentsDB.removeCourseFromTranscripts(course);
                department.removeCourse(course);
                CoursesDB.removeFromDatabase(course);
                MasterLogger.info("removed the selected course", getClass());
            }
        });
    }
}
