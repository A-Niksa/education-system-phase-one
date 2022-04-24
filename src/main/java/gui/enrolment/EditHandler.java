package gui.enrolment;

import gui.MainFrame;
import gui.main.MainMenu;
import gui.standing.TemporaryStanding;
import logic.models.abstractions.Department;
import logic.models.abstractions.StudentStatus;
import logic.models.roles.Professor;
import utils.database.data.DepartmentsDB;
import utils.database.data.ProfessorsDB;
import utils.logging.LogIdentifier;
import utils.logging.MasterLogger;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;

public class EditHandler implements ActionListener {
    private MainFrame mainFrame;
    private MainMenu mainMenu;
    private Professor dean;
    private Professor correspondingProfessor;

    public EditHandler(MainFrame mainFrame, MainMenu mainMenu, Professor dean, Professor correspondingProfessor) {
        this.mainFrame = mainFrame;
        this.mainMenu = mainMenu;
        this.dean = dean;
        this.correspondingProfessor = correspondingProfessor;
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        String professorName = correspondingProfessor.getFirstName() + " " + correspondingProfessor.getLastName();
        MasterLogger.log("opened professor editor for " + professorName, LogIdentifier.INFO,
                "actionPerformed","gui.enrolment.EditHandler");
        mainFrame.setCurrentPanel(new ProfessorEditor(mainFrame, mainMenu, dean, correspondingProfessor));
    }
}
