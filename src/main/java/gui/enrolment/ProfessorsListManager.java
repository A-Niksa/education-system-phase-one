package gui.enrolment;

import gui.MainFrame;
import gui.main.MainMenu;
import logic.models.roles.Professor;
import utils.logging.MasterLogger;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ProfessorsListManager extends ProfessorsListView {
    private Professor professor;
    private JButton openEditor;

    public ProfessorsListManager(MainFrame mainFrame, MainMenu mainMenu, Professor professor) {
        super(mainFrame, mainMenu);
        this.professor = professor;
        drawPanel();
        setEditorButton();
        connectEditor();
    }

    private void setEditorButton() {
        openEditor = new JButton("Editor");
        openEditor.setBounds(140, 622, 80, 30);
        add(openEditor);
    }

    private void connectEditor() {
        openEditor.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                MasterLogger.info("dean opened professors list editor", getClass());
                mainFrame.setCurrentPanel(new ProfessorsListEditor(mainFrame, mainMenu, professor));
            }
        });
    }
}
