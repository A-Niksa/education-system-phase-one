package gui.addition;

import gui.MainFrame;
import gui.enrolment.ProfessorsListEditor;
import gui.main.MainMenu;
import logic.models.roles.Professor;
import utils.logging.MasterLogger;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ProfessorAdderOfDean extends ProfessorAdder {
    private JButton goBackButton;

    public ProfessorAdderOfDean(MainFrame mainFrame, MainMenu mainMenu, Professor operatingProfessor) {
        super(mainFrame, mainMenu, operatingProfessor);
        goBackButton = new JButton("Back");
        goBackButton.setBounds(140, 622, 80, 30);
        add(goBackButton);
        connectBackListener();
    }

    private void connectBackListener() {
        goBackButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                MasterLogger.info("went back to professors list editor", getClass());
                mainFrame.setCurrentPanel(new ProfessorsListEditor(mainFrame, mainMenu, operatingProfessor));
            }
        });
    }
}
