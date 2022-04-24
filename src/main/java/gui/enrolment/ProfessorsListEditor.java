package gui.enrolment;

import gui.MainFrame;
import gui.Template;
import gui.addition.ProfessorAdderOfDean;
import gui.main.MainMenu;
import logic.menus.enrolment.DepartmentListManager;
import logic.models.roles.Professor;
import utils.logging.MasterLogger;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;

public class ProfessorsListEditor extends Template {
    private Professor operatingProfessor;
    private JButton goBackButton;
    private JButton addProfessorButton;
    private JTable professorsTable;
    private String[] columns;
    private String[][] data;
    private LinkedList<Professor> professorsList;
    private LinkedList<JButton> editButtonsList;

    public ProfessorsListEditor(MainFrame mainFrame, MainMenu mainMenu, Professor operatingProfessor) {
        super(mainFrame, mainMenu);
        this.operatingProfessor = operatingProfessor;
        columns = new String[] {"Name and Surname", "Academic Rank", "Administrative Role", "Office Number", "Email Address"};
        professorsList = DepartmentListManager.getProfessorsListBarDean(operatingProfessor);
        setTableData();
        drawPanel();
    }

    private void setTableData() {
        data = new String[professorsList.size()][];
        Professor departmentProfessor;
        for (int i = 0; i < professorsList.size(); i++) {
            departmentProfessor = professorsList.get(i);
            data[i] = new String[] {departmentProfessor.getFirstName() + " " + departmentProfessor.getLastName(),
                    departmentProfessor.getAcademicRankString(),
                    departmentProfessor.getAdministrativeRoleString(),
                    departmentProfessor.getOfficeNumber() + "",
                    departmentProfessor.getEmailAddress()};
        }
    }

    @Override
    protected void initializeComponents() {
        editButtonsList = new LinkedList<>();

        goBackButton = new JButton("Back");
        addProfessorButton = new JButton("Add");

        professorsTable = new JTable(data, columns);

        for (int i = 0; i < professorsList.size(); i++) {
            JButton button = new JButton("Edit");
            editButtonsList.add(button);
        }
    }

    @Override
    protected void alignComponents() {
        goBackButton.setBounds(140, 622, 80, 30);
        add(goBackButton);
        addProfessorButton.setBounds(236, 622, 80, 30);
        add(addProfessorButton);

        professorsTable.setRowHeight(30);
        JScrollPane scrollPane = new JScrollPane(professorsTable);
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
                MasterLogger.info("went back to professors list view", getClass());
                mainFrame.setCurrentPanel(new ProfessorsListManager(mainFrame, mainMenu, operatingProfessor));
            }
        });

        addProfessorButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                MasterLogger.info("dean opened the professor addition section", getClass());
                mainFrame.setCurrentPanel(new ProfessorAdderOfDean(mainFrame, mainMenu, operatingProfessor));
            }
        });

        JButton editButton;
        Professor editableProfessor;
        for (int i = 0; i < editButtonsList.size(); i++) {
            editButton = editButtonsList.get(i);
            editableProfessor = professorsList.get(i);
            editButton.addActionListener(new ProfessorEditHandler(mainFrame, mainMenu, operatingProfessor, editableProfessor));
        }
    }
}