package gui;

import gui.login.LoginMenu;
import utils.database.tools.DatabaseIdentifier;
import utils.database.tools.DatabaseReader;
import utils.database.tools.DatabaseWriter;
import utils.logging.MasterLogger;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class MainFrame extends JFrame {
    public final int FRAME_WIDTH = 1000;
    public final int FRAME_HEIGHT = 700;

    private JPanel currentPanel;

    public MainFrame() {
        initializeObjects();
        LoginMenu loginMenu = new LoginMenu(this);
        setCurrentPanel(loginMenu);
        configureFrame();
        repaintFrame();
    }

    public void setCurrentPanel(JPanel currentPanel) {
        this.currentPanel = currentPanel;
        getContentPane().removeAll();
        getContentPane().add(this.currentPanel);
        repaintFrame();
    }

    private void initializeObjects() {
        DatabaseReader.constructFromDatabase(DatabaseIdentifier.UNIVERSITY);
        DatabaseReader.constructFromDatabase(DatabaseIdentifier.DEPARTMENTS);
        DatabaseReader.constructFromDatabase(DatabaseIdentifier.COURSES);
        DatabaseReader.constructFromDatabase(DatabaseIdentifier.STUDENTS);
        DatabaseReader.constructFromDatabase(DatabaseIdentifier.PROFESSORS);
        DatabaseReader.constructFromDatabase(DatabaseIdentifier.REQUESTS);
    }

    private void connectToDatabase() {
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                DatabaseWriter.updateDatabase();
                MasterLogger.info("end of program", getClass());
                e.getWindow().dispose();
            }
        });
    }

    private void configureFrame() {
        setSize(new Dimension(FRAME_WIDTH, FRAME_HEIGHT));
        setVisible(true);
        setResizable(false);
        setLocationRelativeTo(null);
        connectToDatabase();
    }

    private void repaintFrame() {
        repaint();
        revalidate();
    }
}
