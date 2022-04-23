package gui.main;

import gui.MainFrame;
import gui.login.LoginMenu;
import logic.models.roles.User;
import utils.logging.LogIdentifier;
import utils.logging.MasterLogger;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static utils.timing.TimeManager.getTime;

public class MainMenu extends JPanel {
    protected MainFrame mainFrame;
    protected User user;
    protected JLabel lastLoginTime;
    protected JLabel profilePicture;
    protected JLabel nameLabel;
    protected JLabel emailAddressLabel;
    protected JButton logOutButton;

    public MainMenu(MainFrame mainFrame, User user) {
        this.mainFrame = mainFrame;
        this.user = user;
        configurePanel();
        initializeComponents();
        alignComponents();
        connectListeners();
    }

    protected void configurePanel() {
        setSize(new Dimension(mainFrame.FRAME_WIDTH, mainFrame.FRAME_HEIGHT));
        setLayout(null);
    }

    private void initializeComponents() {
        lastLoginTime = new JLabel("Last Login: " + user.getTimeOfLastLoginString());
        ImageIcon profilePictureIcon = new ImageIcon(user.getProfilePicture());
        profilePicture = new JLabel(profilePictureIcon);
        nameLabel = new JLabel(user.getFirstName() + " " + user.getLastName());
        emailAddressLabel = new JLabel(user.getEmailAddress());
        logOutButton = new JButton("Log Out");
    }

    private void alignComponents() {
        profilePicture.setBounds(15, 15, 60, 60);
        add(profilePicture);
        nameLabel.setBounds(100, 20, 400, 20);
        add(nameLabel);
        emailAddressLabel.setBounds(100, 45, 400, 20);
        add(emailAddressLabel);
        logOutButton.setBounds(15, 622, 80, 30);
        add(logOutButton);
        lastLoginTime.setBounds(760, 15, 250, 50);
        add(lastLoginTime);
    }

    private void connectListeners() {
        logOutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                MasterLogger.info("logged out", getClass());
                mainFrame.setCurrentPanel(new LoginMenu(mainFrame));
            }
        });
    }
}
