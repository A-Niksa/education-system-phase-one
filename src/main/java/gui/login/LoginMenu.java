package gui.login;

import gui.MainFrame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginMenu extends JPanel {
    private MainFrame mainFrame;
    private JLabel welcomeMessage;
    private JLabel enterUsernameMessage;
    private JLabel enterPasswordMessage;
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private JLabel enterCaptchaMessage;
    private JLabel captchaImage;
    private JTextField captchaField;
    private JButton changeCaptchaButton;

    public LoginMenu(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
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
        welcomeMessage = new JLabel("Welcome to the Education System!");
        enterUsernameMessage = new JLabel("Please enter your username:");
        enterPasswordMessage = new JLabel("Please enter your password:");
        usernameField = new JTextField();
        passwordField = new JPasswordField();
        loginButton = new JButton("Login");
        enterCaptchaMessage = new JLabel("Please enter the number you see in the picture:");
        captchaImage = new JLabel(CaptchaLoader.getCaptchaImageIcon());
        captchaField = new JTextField();
        changeCaptchaButton = new JButton("Change Captcha");
    }

    private void alignComponents() {
        welcomeMessage.setBounds(360, 100, 300, 50);
        welcomeMessage.setFont(new Font("", Font.BOLD, 17));
        add(welcomeMessage);
        enterUsernameMessage.setBounds(350, 200, 300, 50);
        add(enterUsernameMessage);
        enterPasswordMessage.setBounds(350, 300, 300, 50);
        add(enterPasswordMessage);
        usernameField.setBounds(350, 240, 300, 40);
        add(usernameField);
        passwordField.setBounds(350, 340, 300, 40);
        add(passwordField);
        enterCaptchaMessage.setBounds(350, 400, 300, 50);
        add(enterCaptchaMessage);
        captchaImage.setBounds(350, 450, 130, 40);
        add(captchaImage);
        changeCaptchaButton.setBounds(490, 450, 130, 40);
        add(changeCaptchaButton);
        captchaField.setBounds(430, 500, 100, 30);
        add(captchaField);
        loginButton.setBounds(400, 600, 200, 40);
        add(loginButton);
    }

    private void connectListeners() {
        changeCaptchaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                captchaImage.setIcon(CaptchaLoader.getCaptchaImageIcon());
                repaint();
                revalidate();
            }
        });

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                String enteredUsername = usernameField.getText();
                String enteredPassword = passwordField.getText();
                String enteredCaptcha = captchaField.getText();

                if (!CaptchaLoader.captchaNumberIsValid(enteredCaptcha)) {
                    JOptionPane.showMessageDialog(mainFrame, "The entered captcha number is invalid.");
                    return;
                }

                // TODO
            }
        });
    }
}