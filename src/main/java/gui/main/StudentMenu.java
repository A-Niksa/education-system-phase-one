package gui.main;

import gui.MainFrame;
import gui.enrolment.CoursesListView;
import gui.enrolment.ProfessorsListView;
import gui.profile.StudentProfile;
import gui.services.ExamsListView;
import gui.services.WeeklySchedule;
import gui.services.requests.*;
import gui.standing.CurrentStanding;
import gui.standing.TemporaryStanding;
import logic.models.roles.Professor;
import logic.models.roles.Student;
import logic.models.roles.User;
import utils.logging.MasterLogger;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class StudentMenu extends MainMenu {
    private User user;
    private JLabel academicStatusLabel;
    private JLabel advisingProfessorName;
    private JLabel isAllowedToEnrol;
    private JLabel enrolmentTime;
    private JSeparator separator;
    private JMenuBar menuBar;
    private JMenu registrationAffairs;
    private JMenu academicServices;
    private JMenu academicStanding;
    private JMenu userProfile;
    private JMenu requestsSubMenu;
    private JMenuItem listOfCourses;
    private JMenuItem listOfProfessors;
    private JMenuItem weeklySchedule;
    private JMenuItem listOfExams;
    private JMenuItem recommendationLetter;
    private JMenuItem enrolmentCertificate;
    private JMenuItem minor;
    private JMenuItem droppingOut;
    private JMenuItem dormitory;
    private JMenuItem defenseSlot;
    private JMenuItem temporaryScores;
    private JMenuItem currentAcademicStanding;
    private JMenuItem editUserProfile;

    public StudentMenu(MainFrame mainFrame, User user) {
        super(mainFrame, user);
        this.user = user;
        initializeComponents();
        alignComponents();
        connectListeners();
    }

    private void initializeComponents() {
        Student studentUser = (Student) user; //welp
        academicStatusLabel = new JLabel("Academic Status: " + studentUser.getAcademicStatusString());
        Professor advisingProfessor = studentUser.getAdvisingProfessor();
        advisingProfessorName = new JLabel("Advising Professor: " +
                advisingProfessor.getFirstName() + " " + advisingProfessor.getLastName());
        isAllowedToEnrol = new JLabel("This student is permitted to enrol at university classes.");
        enrolmentTime = new JLabel("Enrolment Time: 25/07/2022 - 9 AM");
        separator = new JSeparator();
        menuBar = new JMenuBar();
        registrationAffairs = new JMenu("Registration Affairs");
        academicServices = new JMenu("Academic Services");
        academicStanding = new JMenu("Academic Standing");
        userProfile = new JMenu("User Profile");
        listOfCourses = new JMenuItem("Courses List");
        listOfProfessors = new JMenuItem("Professors List");
        weeklySchedule = new JMenuItem("Weekly Schedule");
        listOfExams = new JMenuItem("Exams List");
        requestsSubMenu = new JMenu("Requests");
        recommendationLetter = new JMenuItem("Recommendation Letter");
        enrolmentCertificate = new JMenuItem("Enrolment Certificate");
        minor = new JMenuItem("Minor");
        droppingOut = new JMenuItem("Dropping Out");
        dormitory = new JMenuItem("Dorm Request");
        defenseSlot = new JMenuItem("Defense Slot Request");
        temporaryScores = new JMenuItem("Temporary Scores");
        currentAcademicStanding = new JMenuItem("Current Academic Standing");
        editUserProfile = new JMenuItem("Edit Profile");
    }

    private void alignComponents() {
        academicStatusLabel.setBounds(20, 205, 400, 60);
        add(academicStatusLabel);
        advisingProfessorName.setBounds(20, 275, 400, 60);
        add(advisingProfessorName);
        isAllowedToEnrol.setBounds(20, 345, 500, 60);
        add(isAllowedToEnrol);
        enrolmentTime.setBounds(20, 415, 400, 60);
        add(enrolmentTime);
        separator.setOrientation(SwingConstants.VERTICAL);
        separator.setBackground(Color.BLACK);
        separator.setForeground(Color.BLACK);
        separator.setBounds(425, 210, 15, 250);
        add(separator);
        menuBar.setBounds(480, 210, 450, 40);
        add(menuBar);
        menuBar.add(registrationAffairs);
        registrationAffairs.add(listOfCourses);
        registrationAffairs.add(listOfProfessors);
        menuBar.add(academicServices);
        alignServicesMenu();
        menuBar.add(academicStanding);
        academicStanding.add(temporaryScores);
        academicStanding.add(currentAcademicStanding);
        menuBar.add(userProfile);
        userProfile.add(editUserProfile);
    }

    private void alignServicesMenu() {
        academicServices.add(weeklySchedule);
        academicServices.add(listOfExams);
        academicServices.add(requestsSubMenu);
        requestsSubMenu.add(droppingOut);
        requestsSubMenu.add(enrolmentCertificate);

        Student studentUser = (Student) user;
        switch (studentUser.getSoughtDegree()) {
            case BACHELORS:
                requestsSubMenu.add(recommendationLetter);
                requestsSubMenu.add(minor);
                return;
            case GRADUATE:
                requestsSubMenu.add(recommendationLetter);
                requestsSubMenu.add(dormitory);
                return;
            case PHD:
                requestsSubMenu.add(defenseSlot);
                return;
        }
    }

    private void connectListeners() {
        MainMenu mainMenu = this;

        editUserProfile.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                MasterLogger.info("opened the profile editor in the user profile", getClass());
                mainFrame.setCurrentPanel(new StudentProfile(mainFrame, mainMenu, user));
            }
        });

        listOfCourses.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                MasterLogger.info("opened the courses list in educational services", getClass());
                mainFrame.setCurrentPanel(new CoursesListView(mainFrame, mainMenu));
            }
        });

        listOfProfessors.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                MasterLogger.info("opened the professors list in educational services", getClass());
                mainFrame.setCurrentPanel(new ProfessorsListView(mainFrame, mainMenu));
            }
        });

        temporaryScores.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                MasterLogger.info("opened temporary scores in academic standing", getClass());
                mainFrame.setCurrentPanel(new TemporaryStanding(mainFrame, mainMenu, user));
            }
        });

        currentAcademicStanding.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                MasterLogger.info("opened current academic standing in academic standing", getClass());
                mainFrame.setCurrentPanel(new CurrentStanding(mainFrame, mainMenu, user));
            }
        });

        weeklySchedule.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                MasterLogger.info("opened weekly schedule in academic services", getClass());
                mainFrame.setCurrentPanel(new WeeklySchedule(mainFrame, mainMenu, user));
            }
        });

        listOfExams.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                MasterLogger.info("opened list of exams in academic services", getClass());
                mainFrame.setCurrentPanel(new ExamsListView(mainFrame, mainMenu, user));
            }
        });

        droppingOut.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                MasterLogger.info("opened the dropping out subsection in academic requests", getClass());
                mainFrame.setCurrentPanel(new DroppingOutSubmission(mainFrame, mainMenu, user));
            }
        });

        enrolmentCertificate.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                MasterLogger.info("opened the enrolment certificate subsection in academic requests", getClass());
                mainFrame.setCurrentPanel(new CertificateSubmission(mainFrame, mainMenu, user));
            }
        });

        recommendationLetter.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                MasterLogger.info("opened the recommendation letter subsection in academic requests", getClass());
                mainFrame.setCurrentPanel(new RecommendationSubmission(mainFrame, mainMenu, user));
            }
        });

        minor.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                MasterLogger.info("opened minor request subsection in academic requests", getClass());
                mainFrame.setCurrentPanel(new MinorSubmission(mainFrame, mainMenu, user));
            }
        });

        dormitory.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                MasterLogger.info("opened dorm request subsection in academic requests", getClass());
                mainFrame.setCurrentPanel(new DormSubmission(mainFrame, mainMenu, user));
            }
        });

        defenseSlot.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                MasterLogger.info("opened defense slot selection in academic requests", getClass());
                mainFrame.setCurrentPanel(new DefenseSubmission(mainFrame, mainMenu, user));
            }
        });
    }
}