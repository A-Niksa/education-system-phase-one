package gui.profile;

import gui.MainFrame;
import gui.Template;
import gui.main.MainMenu;
import logic.models.roles.Professor;
import logic.models.roles.User;
import utils.logging.MasterLogger;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;

public class ProfessorProfile extends Template {
    Professor professor;
    private JLabel profilePicture;
    private JLabel name;
    private JLabel nationalID;
    private JLabel teachingID;
    private JLabel phoneNumber;
    private JLabel emailAddress;
    private JLabel department;
    private JLabel officeNumber;
    private JLabel academicRank;
    private LinkedList<JLabel> labelsList;
    private JSeparator separator;
    private JLabel enterNewEmailAddress;
    private JTextField newEmailAddress;
    private JButton changeEmailAddress;
    private JLabel enterNewPhoneNumber;
    private JTextField newPhoneNumber;
    private JButton changePhoneNumber;

    public ProfessorProfile(MainFrame mainFrame, MainMenu mainMenu, User user) {
        super(mainFrame, mainMenu);
        professor = (Professor) user;
        drawPanel();
    }

    @Override
    protected void initializeComponents() {
        ImageIcon profilePictureIcon = new ImageIcon(professor.getProfilePicture());
        profilePicture = new JLabel(profilePictureIcon);

        labelsList = new LinkedList<JLabel>();
        name = new JLabel("Name and Surname: " + professor.getFirstName() + " " + professor.getLastName());
        labelsList.add(name);
        nationalID = new JLabel("National ID: " + professor.getNationalID());
        labelsList.add(nationalID);
        teachingID = new JLabel("Teaching ID: " + professor.getTeachingID());
        labelsList.add(teachingID);
        phoneNumber = new JLabel("Phone Number: " + professor.getPhoneNumber());
        labelsList.add(phoneNumber);
        emailAddress = new JLabel("Email Address " + professor.getEmailAddress());
        labelsList.add(emailAddress);
        department = new JLabel("Department: " + professor.getDepartmentName());
        labelsList.add(department);
        officeNumber = new JLabel("Office Number: " + professor.getOfficeNumber());
        labelsList.add(officeNumber);
        academicRank = new JLabel("Academic Status: " + professor.getAcademicRankString());
        labelsList.add(academicRank);
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

        int currentX = 35, currentY = 70;
        int increment = 50;
        for (JLabel label : labelsList) {
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
                String newPhoneNumberText = newPhoneNumber.getText();
                MasterLogger.info("professor changed phone number to " + newPhoneNumberText, getClass());
                professor.setPhoneNumber(newPhoneNumberText);
                phoneNumber.setText("Phone Number: " + newPhoneNumberText);
            }
        });

        changeEmailAddress.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                String newEmailAddressText = newEmailAddress.getText();
                MasterLogger.info("professor changed email address to " + newEmailAddressText, getClass());
                professor.setEmailAddress(newEmailAddressText);
                emailAddress.setText("Email Address: " + newEmailAddressText);
            }
        });
    }
}
