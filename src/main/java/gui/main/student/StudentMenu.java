package gui.main.student;

import gui.MainFrame;
import gui.main.MainMenu;
import logic.models.roles.Professor;
import logic.models.roles.Student;
import logic.models.roles.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Line2D;

public class StudentMenu extends MainMenu {
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

    public StudentMenu(MainFrame mainFrame, User user) {
        super(mainFrame, user);
        initializeComponents();
        alignComponents();
        connectListeners();
    }

    private void initializeComponents() {
        Student studentUser = (Student) user;
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
        userProfile.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {

            }
        });
    }
}