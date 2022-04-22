package gui;

import gui.main.MainMenu;
import utils.logging.MasterLogger;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public abstract class Template extends JPanel {
    protected MainFrame mainFrame;
    protected MainMenu mainMenu;
    protected JButton mainMenuButton;

    public Template(MainFrame mainFrame, MainMenu mainMenu) {
        this.mainFrame = mainFrame;
        this.mainMenu = mainMenu;
        configurePanel();
        initializeTemplateComponents();
        alignTemplateComponents();
        connectTemplateListeners();
    }

    protected void drawPanel() {
        initializeComponents();
        alignComponents();
        connectListeners();
    }

    private void configurePanel() {
        setSize(new Dimension(mainFrame.FRAME_WIDTH, mainFrame.FRAME_HEIGHT));
        setLayout(null);
    }

    private void initializeTemplateComponents() {
        mainMenuButton = new JButton("Main Menu");
    }

    protected abstract void initializeComponents();

    private void alignTemplateComponents() {
        mainMenuButton.setBounds(15, 622, 110, 30);
        add(mainMenuButton);
    }

    protected abstract void alignComponents();

    private void connectTemplateListeners() {
        mainMenuButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                MasterLogger.info("returned to the main menu", getClass());
                mainFrame.setCurrentPanel(mainMenu);
            }
        });
    }

    protected abstract void connectListeners();
}
