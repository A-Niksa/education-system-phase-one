package gui.enrolment;

import gui.MainFrame;
import gui.main.MainMenu;
import logic.models.roles.Professor;
import utils.logging.MasterLogger;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CoursesListManager extends CoursesListView {
    private Professor operatingProfessor;
    private JButton openEditor;

    public CoursesListManager(MainFrame mainFrame, MainMenu mainMenu, Professor operatingProfessor) {
        super(mainFrame, mainMenu);
        this.operatingProfessor = operatingProfessor;
        setEditorButton();
        connectEditorListener();
    }

    private void setEditorButton() {
        openEditor = new JButton("Editor");
        openEditor.setBounds(140, 622, 80, 30);
        add(openEditor);
    }

    private void connectEditorListener() {
        openEditor.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                MasterLogger.info("deputy opened courses list editor", getClass());
                mainFrame.setCurrentPanel(new CoursesListEditor(mainFrame, mainMenu, operatingProfessor));
            }
        });
    }
}
