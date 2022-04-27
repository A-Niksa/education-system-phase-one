package gui.enrolment;

import gui.MainFrame;
import gui.main.MainMenu;
import logic.models.abstractions.Course;
import logic.models.roles.Professor;
import utils.logging.LogIdentifier;
import utils.logging.MasterLogger;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CourseEditHandler implements ActionListener {
    private MainFrame mainFrame;
    private MainMenu mainMenu;
    private Professor educationDeputy;
    private Course correspondingCourse;

    public CourseEditHandler(MainFrame mainFrame, MainMenu mainMenu, Professor educationDeputy,
                             Course correspondingCourse) {
        this.mainFrame = mainFrame;
        this.mainMenu = mainMenu;
        this.educationDeputy = educationDeputy;
        this.correspondingCourse = correspondingCourse;
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        String courseName = correspondingCourse.getCourseName();
        MasterLogger.log("opened course editor for " + courseName, LogIdentifier.INFO,
                "actionPerformed", "gui.enrolment.CourseEditHandler");
        mainFrame.setCurrentPanel(new CourseEditor(mainFrame, mainMenu, educationDeputy, correspondingCourse));
    }
}
