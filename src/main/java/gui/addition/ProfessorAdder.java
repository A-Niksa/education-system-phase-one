package gui.addition;

import gui.MainFrame;
import gui.Template;
import gui.main.MainMenu;
import logic.menus.addition.ProfessorConstruction;
import logic.models.abstractions.Department;
import logic.models.roles.Professor;
import utils.database.data.DepartmentsDB;
import utils.logging.MasterLogger;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;

public class ProfessorAdder extends Template {
    protected Professor operatingProfessor;
    private JTextField teachingIDField;
    private JTextField passwordField;
    private JTextField nationalIDField;
    private JTextField firstNameField;
    private JTextField lastNameField;
    private JTextField phoneNumberField;
    private JTextField emailAddressField;
    private JTextField officeNumberField;
    private String[] academicRankNames;
    private JComboBox<String> academicRankBox;
    private LinkedList<JTextField> textFieldsList;
    private LinkedList<JComboBox<String>> comboBoxesList;
    private JButton addProfessorButton;

    public ProfessorAdder(MainFrame mainFrame, MainMenu mainMenu, Professor operatingProfessor) {
        super(mainFrame, mainMenu);
        this.operatingProfessor = operatingProfessor;
        drawPanel();
    }

    @Override
    protected void initializeComponents() {
        textFieldsList = new LinkedList<>();
        comboBoxesList = new LinkedList<>();

        teachingIDField = new JTextField("Teaching ID...");
        textFieldsList.add(teachingIDField);
        passwordField = new JTextField("Password...");
        textFieldsList.add(passwordField);
        nationalIDField = new JTextField("National ID...");
        textFieldsList.add(nationalIDField);
        firstNameField = new JTextField("First Name...");
        textFieldsList.add(firstNameField);
        lastNameField = new JTextField("Last Name...");
        textFieldsList.add(lastNameField);
        phoneNumberField = new JTextField("Phone Number...");
        textFieldsList.add(phoneNumberField);
        emailAddressField = new JTextField("Email Address...");
        textFieldsList.add(emailAddressField);
        officeNumberField = new JTextField("Office Number...");
        textFieldsList.add(officeNumberField);

        academicRankNames = new String[]{"Assistant Professor", "Associate Professor", "Full Professor"};
        academicRankBox = new JComboBox<>(academicRankNames);
        comboBoxesList.add(academicRankBox);

        addProfessorButton = new JButton("Add Professor");
    }

    @Override
    protected void alignComponents() {
        int currentX = 350, currentY = 140;
        for (JTextField textField : textFieldsList) {
            textField.setBounds(currentX, currentY, 300, 30);
            add(textField);
            currentY += 40;
        }
        for (JComboBox<String> comboBox : comboBoxesList) {
            comboBox.setBounds(currentX, currentY, 300, 30);
            add(comboBox);
            currentY += 40;
        }
        addProfessorButton.setBounds(currentX + 30, currentY + 10, 240, 30);
        add(addProfessorButton);
    }

    @Override
    protected void connectListeners() {
        addProfessorButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                String teachingID = teachingIDField.getText();
                String password = passwordField.getText();
                String nationalID = nationalIDField.getText();
                String firstName = firstNameField.getText();
                String lastName = lastNameField.getText();
                String phoneNumber = phoneNumberField.getText();
                String emailAddress = emailAddressField.getText();
                int officeNumber = Integer.parseInt(officeNumberField.getText());
                String academicRankString = (String) academicRankBox.getSelectedItem();
                Department department = DepartmentsDB.getProfessorsDepartment(operatingProfessor);

                ProfessorConstruction.constructProfessor(teachingID, password, nationalID, firstName, lastName, phoneNumber,
                        emailAddress, officeNumber, academicRankString, department);
                MasterLogger.info("added professor (ID: " + teachingID + ") to the system", getClass());
            }
        });
    }
}
