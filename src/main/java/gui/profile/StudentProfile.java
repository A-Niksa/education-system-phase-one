package gui.profile;

import gui.MainFrame;
import gui.Template;
import gui.main.MainMenu;
import logic.models.roles.Professor;
import logic.models.roles.Student;
import logic.models.roles.User;
import utils.logging.MasterLogger;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;

public class StudentProfile extends Template {
    private User user;
    private JLabel profilePicture;
    private JLabel name;
    private JLabel nationalID;
    private JLabel studentID;
    private JLabel phoneNumber;
    private JLabel emailAddress;
    private JLabel totalGPA;
    private JLabel department;
    private JLabel advisingProfessor;
    private JLabel yearOfEntry;
    private JLabel soughtDegree;
    private JLabel academicStatus;
    private LinkedList<JLabel> lablesList;
    private JSeparator separator;
    private JLabel enterNewEmailAddress;
    private JTextField newEmailAddress;
    private JButton changeEmailAddress;
    private JLabel enterNewPhoneNumber;
    private JTextField newPhoneNumber;
    private JButton changePhoneNumber;

    public StudentProfile(MainFrame mainFrame, MainMenu mainMenu, User user) {
        super(mainFrame, mainMenu);
        this.user = user;
        lablesList = new LinkedList<>();
        drawPanel();
    }

    @Override
    protected void initializeComponents() {
        Student student = (Student) user;

        ImageIcon profilePictureIcon = new ImageIcon(student.getProfilePicture());
        profilePicture = new JLabel(profilePictureIcon);
        name = new JLabel("Name and Surname: " + student.getFirstName() + " " + student.getLastName());
        lablesList.add(name);
        nationalID = new JLabel("National ID: " + student.getNationalID());
        lablesList.add(nationalID);
        studentID = new JLabel("Student ID: " + student.getStudentID());
        lablesList.add(studentID);
        phoneNumber = new JLabel("Phone Number: " + student.getPhoneNumber());
        lablesList.add(phoneNumber);
        emailAddress = new JLabel("Email Address " + student.getEmailAddress());
        lablesList.add(emailAddress);
        totalGPA = new JLabel("Total GPA: " + student.getTotalGPA());
        lablesList.add(totalGPA);
        department = new JLabel("Department: " + student.getDepartmentName());
        lablesList.add(department);
        Professor advisingProfessorObject = student.getAdvisingProfessor();
        advisingProfessor = new JLabel("Advising Professor: " +
                advisingProfessorObject.getFirstName() + " " + advisingProfessorObject.getLastName());
        lablesList.add(advisingProfessor);
        yearOfEntry = new JLabel("Year of Entry: " + student.getYearOfEntry());
        lablesList.add(yearOfEntry);
        soughtDegree = new JLabel("Sought Degree: " + student.getSoughtDegreeString());
        lablesList.add(soughtDegree);
        academicStatus = new JLabel("Academic Status: " + student.getAcademicStatusString());
        lablesList.add(academicStatus);
        separator = new JSeparator();
        enterNewEmailAddress = new JLabel("New Email Address:");
        newEmailAddress = new JTextField();
        changeEmailAddress = new JButton("Change");
        enterNewPhoneNumber = new JLabel("New Phone Number:");
        newPhoneNumber = new JTextField();
        changePhoneNumber = new JButton("Change");
    }

    @Override
    protected void alignComponents() {
        profilePicture.setBounds(15, 15, 60, 60);
        add(profilePicture);

        int currentX = 20, currentY = 52;
        int increment = 40;
        for (JLabel label : lablesList) {
            label.setBounds(currentX, currentY, 350, 220);
            add(label);
            currentY += increment;
        }

        separator.setOrientation(SwingConstants.VERTICAL);
        separator.setBackground(Color.BLACK);
        separator.setForeground(Color.BLACK);
        separator.setBounds(375, 150, 15, 420);
        add(separator);
        enterNewPhoneNumber.setBounds(500, 200, 250, 40);
        add(enterNewPhoneNumber);
        newPhoneNumber.setBounds(500, 240, 250, 30);
        add(newPhoneNumber);
        changePhoneNumber.setBounds(765, 240, 150, 30);
        add(changePhoneNumber);
        enterNewEmailAddress.setBounds(500, 280, 250, 40);
        add(enterNewEmailAddress);
        newEmailAddress.setBounds(500, 320, 250, 30);
        add(newEmailAddress);
        changeEmailAddress.setBounds(765, 320, 150, 30);
        add(changeEmailAddress);
    }

    @Override
    protected void connectListeners() {
        changePhoneNumber.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                MasterLogger.info("student changed phone number", getClass());
                user.setPhoneNumber(newPhoneNumber.getText());
                phoneNumber.setText("Phone Number: " + newPhoneNumber.getText());
            }
        });

        changeEmailAddress.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                MasterLogger.info("student changed email address", getClass());
                user.setEmailAddress(newEmailAddress.getText());
                emailAddress.setText("Email Address: " + newEmailAddress.getText());
            }
        });
    }
}
