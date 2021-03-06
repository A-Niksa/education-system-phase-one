package gui.enrolment;

import gui.MainFrame;
import gui.Template;
import gui.main.MainMenu;
import logic.menus.enrolment.DeputyPromotionChecker;
import logic.models.abstractions.Department;
import logic.models.roles.Professor;
import utils.database.data.DepartmentsDB;
import utils.database.data.ProfessorsDB;
import utils.logging.MasterLogger;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ProfessorEditor extends Template {
    private Professor dean;
    private Professor professor;
    private JButton goBackButton;
    private JLabel professorName;
    private JComboBox<String> newRank;
    private JButton changeRank;
    private JTextField newOfficeNumber;
    private JButton changeOfficeNumber;
    private JButton demoteFromDeputy;
    private JButton promoteToDeputy;
    private JButton removeProfessor;

    public ProfessorEditor(MainFrame mainFrame, MainMenu mainMenu, Professor dean, Professor professor) {
        super(mainFrame, mainMenu);
        this.dean = dean;
        this.professor = professor;
        drawPanel();
    }

    @Override
    protected void initializeComponents() {
        goBackButton = new JButton("Back");
        professorName = new JLabel(professor.getFirstName() + " " + professor.getLastName(),
                SwingConstants.CENTER);
        newRank = new JComboBox<>(new String[]{"Assistant Professor", "Associate Professor", "Full Professor"});
        changeRank = new JButton("Change");
        newOfficeNumber = new JTextField("New Office Number...");
        changeOfficeNumber = new JButton("Change");
        demoteFromDeputy = new JButton("Demote from Deputy");
        promoteToDeputy = new JButton("Promote to Deputy");
        removeProfessor = new JButton("Remove Professor");
    }

    @Override
    protected void alignComponents() {
        goBackButton.setBounds(140, 622, 80, 30);
        add(goBackButton);

        professorName.setBounds(405, 150, 200, 50);
        professorName.setFont(new Font("", Font.BOLD, 16));
        add(professorName);

        newRank.setBounds(300, 240, 250, 30);
        add(newRank);
        changeRank.setBounds(565, 240, 150, 30);
        add(changeRank);

        newOfficeNumber.setBounds(300, 285, 250, 30);
        add(newOfficeNumber);
        changeOfficeNumber.setBounds(565, 285, 150, 30);
        add(changeOfficeNumber);

        demoteFromDeputy.setBounds(300, 330, 415, 30);
        add(demoteFromDeputy);
        promoteToDeputy.setBounds(300, 375, 415, 30);
        add(promoteToDeputy);
        removeProfessor.setBounds(300, 420, 415, 30);
        add(removeProfessor);
    }

    @Override
    protected void connectListeners() {
        goBackButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                MasterLogger.info("went back to professors list editor", getClass());
                mainFrame.setCurrentPanel(new ProfessorsListEditor(mainFrame, mainMenu, dean));
            }
        });

        changeRank.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                String selectedRank = (String) newRank.getSelectedItem();
                if (selectedRank.equals("Assistant Professor")) {
                    professor.setAcademicRank(Professor.AcademicRank.ASSISTANT);
                } else if (selectedRank.equals("Associate Professor")) {
                    professor.setAcademicRank(Professor.AcademicRank.ASSOCIATE);
                } else { // "Full Professor" by design
                    professor.setAcademicRank(Professor.AcademicRank.FULL);
                }
                professor.updateInDatabase();
                MasterLogger.info(professorName.getText() + "'s rank set to " + selectedRank, getClass());
            }
        });

        changeOfficeNumber.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                int newOfficeNumberInteger = Integer.parseInt(newOfficeNumber.getText());
                professor.setOfficeNumber(newOfficeNumberInteger);
                professor.updateInDatabase();
                MasterLogger.info(professorName.getText() + "'s room set to " + newOfficeNumberInteger, getClass());
            }
        });

        demoteFromDeputy.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if (professor.getAdministrativeRole() != Professor.AdministrativeRole.EDUCATION_DEPUTY) {
                    JOptionPane.showMessageDialog(mainFrame, "The selected professor is not a deputy.");
                    MasterLogger.error("professor selected for demotion to a normal professor is not a deputy",
                            getClass());
                    return;
                }

                professor.setAdministrativeRole(Professor.AdministrativeRole.NORMAL);
                Department department = DepartmentsDB.getProfessorsDepartment(professor);
                department.setEducationDeputy(null);
                MasterLogger.info("professor demoted to normal professor", getClass());
            }
        });

        promoteToDeputy.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if (DeputyPromotionChecker.departmentHasDeputy(professor)) {
                    JOptionPane.showMessageDialog(mainFrame, "This department already has a deputy. Demote the" +
                            " other deputy before adding a new one.");
                    MasterLogger.error("cannot add a new deputy; department already has one", getClass());
                    return;
                }

                Department department = DepartmentsDB.getProfessorsDepartment(professor);
                department.setEducationDeputy(professor);
            }
        });

        removeProfessor.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                Department department = DepartmentsDB.getProfessorsDepartment(professor);
                if (professor.getAdministrativeRole() == Professor.AdministrativeRole.EDUCATION_DEPUTY) {
                    department.setEducationDeputy(null);
                }

                department.removeProfessor(professor);
                ProfessorsDB.removeFromDatabase(professor);
                MasterLogger.info("removed the selected professor", getClass());
            }
        });
    }
}
