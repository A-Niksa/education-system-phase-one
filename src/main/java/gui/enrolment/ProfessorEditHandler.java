package gui.enrolment;

import gui.MainFrame;
import gui.main.MainMenu;
import logic.models.roles.Professor;
import utils.logging.LogIdentifier;
import utils.logging.MasterLogger;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ProfessorEditHandler implements ActionListener {
    private MainFrame mainFrame;
    private MainMenu mainMenu;
    private Professor dean;
    private Professor correspondingProfessor;

    public ProfessorEditHandler(MainFrame mainFrame, MainMenu mainMenu, Professor dean, Professor correspondingProfessor) {
        this.mainFrame = mainFrame;
        this.mainMenu = mainMenu;
        this.dean = dean;
        this.correspondingProfessor = correspondingProfessor;
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        String professorName = correspondingProfessor.getFirstName() + " " + correspondingProfessor.getLastName();
        MasterLogger.log("opened professor editor for " + professorName, LogIdentifier.INFO,
                "actionPerformed","gui.enrolment.ProfessorEditHandler");
        mainFrame.setCurrentPanel(new ProfessorEditor(mainFrame, mainMenu, dean, correspondingProfessor));
    }
}
