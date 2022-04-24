package gui.addition;

import gui.MainFrame;
import gui.Template;
import gui.main.MainMenu;
import logic.menus.addition.StudentConstruction;
import logic.menus.enrolment.DepartmentListManager;
import logic.models.abstractions.Department;
import logic.models.roles.Professor;
import utils.database.data.DepartmentsDB;
import utils.logging.MasterLogger;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;

public class StudentAdder extends Template {
    private Professor operatingProfessor;
    private JTextField studentIDField;
    private JTextField passwordField;
    private JTextField nationalIDField;
    private JTextField firstNameField;
    private JTextField lastNameField;
    private JTextField phoneNumberField;
    private JTextField emailAddressField;
    private JTextField yearOfEntryField;
    private String[] academicStatusNames;
    private JComboBox<String> academicStatusBox;
    private String[] soughtDegreeNames;
    private JComboBox<String> soughtDegreeBox;
    private String[] professorNames;
    private JComboBox<String> advisingProfessorNameBox;
    private LinkedList<JTextField> textFieldsList;
    private LinkedList<JComboBox<String>> comboBoxesList;
    private JButton addStudentButton;

    public StudentAdder(MainFrame mainFrame, MainMenu mainMenu, Professor operatingProfessor) {
        super(mainFrame, mainMenu);
        this.operatingProfessor = operatingProfessor;
        drawPanel();
    }

    @Override
    protected void initializeComponents() {
        textFieldsList = new LinkedList<>();
        comboBoxesList = new LinkedList<>();

        studentIDField = new JTextField("Student ID...");
        textFieldsList.add(studentIDField);
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
        yearOfEntryField = new JTextField("Year of Entry...");
        textFieldsList.add(yearOfEntryField);

        academicStatusNames = new String[] {"Currently Studying", "Graduated", "Dropped Out"};
        academicStatusBox = new JComboBox<>(academicStatusNames);
        comboBoxesList.add(academicStatusBox);
        soughtDegreeNames = new String[] {"Bachelors", "Graduate", "PhD"};
        soughtDegreeBox = new JComboBox<>(soughtDegreeNames);
        comboBoxesList.add(soughtDegreeBox);
        professorNames = DepartmentListManager.getProfessorsNames(operatingProfessor); // for the professor's department
        advisingProfessorNameBox = new JComboBox<>(professorNames);
        comboBoxesList.add(advisingProfessorNameBox);

        addStudentButton = new JButton("Add Student");
    }

    @Override
    protected void alignComponents() {
        int currentX = 350, currentY = 100;
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
        addStudentButton.setBounds(currentX + 30, currentY + 10, 240, 30);
        add(addStudentButton);
    }

    @Override
    protected void connectListeners() {
        addStudentButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                String studentID = studentIDField.getText();
                String password = passwordField.getText();
                String nationalID = nationalIDField.getText();
                String firstName = firstNameField.getText();
                String lastName = lastNameField.getText();
                String phoneNumber = phoneNumberField.getText();
                String emailAddress = emailAddressField.getText();
                int yearOfEntry = Integer.parseInt(yearOfEntryField.getText());
                String academicStatusString = (String) academicStatusBox.getSelectedItem();
                String soughtDegreeString = (String) soughtDegreeBox.getSelectedItem();
                String advisingProfessorName = (String) advisingProfessorNameBox.getSelectedItem();
                Department department = DepartmentsDB.getProfessorsDepartment(operatingProfessor);

                StudentConstruction.constructStudent(studentID, password, nationalID, firstName, lastName, phoneNumber,
                        emailAddress, yearOfEntry, academicStatusString, soughtDegreeString, advisingProfessorName, department);
                MasterLogger.info("added student (ID: " + studentID + ") to the system", getClass());
            }
        });
    }
}
