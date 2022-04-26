package gui.login;

import gui.MainFrame;
import logic.models.roles.Professor;
import logic.models.roles.Student;
import logic.models.roles.User;
import utils.logging.MasterLogger;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PasswordChanger extends JPanel {
    private MainFrame mainFrame;
    private User user;
    private JTextField newPasswordField;
    private JButton changePassword;

    public PasswordChanger(MainFrame mainFrame, User user) {
        this.mainFrame = mainFrame;
        this.user = user;
        configurePanel();
        initializeComponents();
        alignComponents();
        connectListeners();
    }

    private void configurePanel() {
        setSize(new Dimension(mainFrame.FRAME_WIDTH, mainFrame.FRAME_HEIGHT));
        setLayout(null);
    }

    private void initializeComponents() {
        newPasswordField = new JTextField("New Password...");
        changePassword = new JButton("Change Password");
    }

    private void alignComponents() {
        newPasswordField.setBounds(350, 230, 300, 30);
        add(newPasswordField);
        changePassword.setBounds(350, 270, 300, 30);
        add(changePassword);
    }

    private void connectListeners() {
        changePassword.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                String newPassword = newPasswordField.getText();
                user.setPassword(newPassword);
                if (user instanceof Student) {
                    Student castUser = (Student) user;
                    castUser.updateInDatabase();
                } else { // Professor by design
                    Professor castUser = (Professor) user;
                    castUser.updateInDatabase();
                }
                user.setTimeOfLastLoginToNow();

                MasterLogger.info("user changed password", getClass());

                mainFrame.setCurrentPanel(new LoginMenu(mainFrame));
            }
        });
    }
}
