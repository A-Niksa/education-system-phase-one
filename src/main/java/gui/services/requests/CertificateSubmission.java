package gui.services.requests;

import gui.MainFrame;
import gui.Template;
import gui.main.MainMenu;
import logic.menus.services.requests.CertificateRequest;
import logic.models.roles.Student;
import logic.models.roles.User;
import utils.logging.MasterLogger;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CertificateSubmission extends Template {
    private Student student;
    private JLabel generatingPrompt;
    private JButton generateButton;
    private JSeparator separator;
    private JLabel yourCertificatePrompt;
    private JLabel certificateText;

    public CertificateSubmission(MainFrame mainFrame, MainMenu mainMenu, User user) {
        super(mainFrame, mainMenu);
        student = (Student) user;
        drawPanel();
    }

    @Override
    protected void initializeComponents() {
        generatingPrompt = new JLabel("Would you like to get an enrolment certificate?");
        generateButton = new JButton("Generate an Enrolment Certificate");
        separator = new JSeparator();
        yourCertificatePrompt = new JLabel();
        certificateText = new JLabel();
    }

    @Override
    protected void alignComponents() {
        generatingPrompt.setBounds(90, 230, 500, 100);
        generatingPrompt.setFont(new Font("", Font.BOLD, 13));
        add(generatingPrompt);
        generateButton.setBounds(110, 300, 250, 35);
        add(generateButton);
        separator.setOrientation(SwingConstants.VERTICAL);
        separator.setBackground(Color.BLACK);
        separator.setForeground(Color.BLACK);
        separator.setBounds(450, 195, 15, 250);
        add(separator);
        yourCertificatePrompt.setBounds(475, 195, 100, 30);
        add(yourCertificatePrompt);
        certificateText.setBounds(475, 215, 470, 200);
        certificateText.setFont(new Font("", Font.BOLD, 14));
        add(certificateText);
    }

    @Override
    protected void connectListeners() {
        generateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                CertificateRequest request = new CertificateRequest(student);
                yourCertificatePrompt.setText("Your Certificate:");
                certificateText.setText(request.getCertificateText());
                MasterLogger.info("received an enrolment certificate", getClass());
            }
        });
    }
}
