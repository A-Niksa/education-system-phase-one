package gui.main;

import gui.MainFrame;
import gui.addition.ProfessorAdder;
import gui.addition.StudentAdder;
import gui.enrolment.CoursesListView;
import gui.enrolment.ProfessorsListManager;
import gui.enrolment.ProfessorsListView;
import gui.profile.ProfessorProfile;
import logic.models.roles.Professor;
import logic.models.roles.User;
import utils.logging.MasterLogger;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ProfessorMenu extends MainMenu {
    private Professor professorUser;
    private Professor.AdministrativeRole role;
    private JMenuBar menuBar;
    private JMenu registrationAffairs;
    private JMenu academicServices;
    private JMenu academicStanding;
    private JMenu userProfile;
    private JMenu studentRequestsSubMenu;
    private JMenuItem listOfCourses;
    private JMenuItem listOfProfessors;
    private JMenuItem weeklySchedule;
    private JMenuItem listOfExams;
    private JMenuItem recommendationLetter;
    private JMenuItem minor;
    private JMenuItem droppingOut;
    private JMenuItem temporaryScores;
    private JMenuItem viewStudentsAcademicStanding;
    private JMenuItem editUserProfile;
    private JButton addStudent;
    private JButton addProfessor;

    public ProfessorMenu(MainFrame mainFrame, User user) {
        super(mainFrame, user);
        professorUser = (Professor) user;
        role = professorUser.getAdministrativeRole();
        initializeComponents();
        alignComponents();
        connectListeners();
    }

    private void initializeComponents() {
        menuBar = new JMenuBar();
        registrationAffairs = new JMenu("Registration Affairs");
        academicServices = new JMenu("Academic Services");
        academicStanding = new JMenu("Academic Standing");
        userProfile = new JMenu("User Profile");
        listOfCourses = new JMenuItem("Courses List");
        listOfProfessors = new JMenuItem("Professors List");
        weeklySchedule = new JMenuItem("Weekly Schedule");
        listOfExams = new JMenuItem("Exams List");
        studentRequestsSubMenu = new JMenu("Student Requests");
        recommendationLetter = new JMenuItem("Recommendation Letter");
        minor = new JMenuItem("Minor");
        droppingOut = new JMenuItem("Dropping Out");
        temporaryScores = new JMenuItem("Temporary Scores");
        viewStudentsAcademicStanding = new JMenuItem("View Students' Academic Standing");
        editUserProfile = new JMenuItem("Edit Profile");
        addStudent = new JButton("Add Student");
        addProfessor = new JButton("Add Professor");
    }

    private void alignComponents() {
        menuBar.setBounds(270, 235, 450, 40);
        add(menuBar);
        menuBar.add(registrationAffairs);
        registrationAffairs.add(listOfCourses);
        registrationAffairs.add(listOfProfessors);
        menuBar.add(academicServices);
        academicServices.add(weeklySchedule);
        academicServices.add(listOfExams);
        academicServices.add(studentRequestsSubMenu);
        alignRequestsSubMenu();
        menuBar.add(academicStanding);
        academicStanding.add(temporaryScores);
        academicStanding.add(viewStudentsAcademicStanding);
        menuBar.add(userProfile);
        userProfile.add(editUserProfile);
        addUserAdditionButtons();
    }

    private void addUserAdditionButtons() {
        if (role == Professor.AdministrativeRole.EDUCATION_DEPUTY) {
            addStudent.setBounds(340, 400, 150, 40);
            add(addStudent);
            addProfessor.setBounds(505, 400, 150, 40);
            add(addProfessor);
        }
    }

    private void alignRequestsSubMenu() {
        studentRequestsSubMenu.add(recommendationLetter);

        if (role == Professor.AdministrativeRole.EDUCATION_DEPUTY) {
            studentRequestsSubMenu.add(droppingOut);
            studentRequestsSubMenu.add(minor);
        }
    }

    private void connectListeners() {
        MainMenu mainMenu = this;

        editUserProfile.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                MasterLogger.info("opened the profile editor in the user profile", getClass());
                mainFrame.setCurrentPanel(new ProfessorProfile(mainFrame, mainMenu, user));
            }
        });

        listOfCourses.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                MasterLogger.info("opened the courses list in educational services", getClass());
                if (role == Professor.AdministrativeRole.EDUCATION_DEPUTY) {

                } else {
                    mainFrame.setCurrentPanel(new CoursesListView(mainFrame, mainMenu));
                }
            }
        });

        listOfProfessors.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                MasterLogger.info("opened the professors list in educational services", getClass());
                if (role == Professor.AdministrativeRole.DEAN) {
                    mainFrame.setCurrentPanel(new ProfessorsListManager(mainFrame, mainMenu, professorUser));
                } else {
                    mainFrame.setCurrentPanel(new ProfessorsListView(mainFrame, mainMenu));
                }
            }
        });

        addStudent.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                MasterLogger.info("deputy opened the student addition section", getClass());
                mainFrame.setCurrentPanel(new StudentAdder(mainFrame, mainMenu, professorUser));
            }
        });

        addProfessor.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                MasterLogger.info("deputy opened the professor addition section", getClass());
                mainFrame.setCurrentPanel(new ProfessorAdder(mainFrame, mainMenu, professorUser));
            }
        });
    }
}
