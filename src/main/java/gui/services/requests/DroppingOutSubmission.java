package gui.services.requests;

import gui.MainFrame;
import gui.Template;
import gui.main.MainMenu;
import logic.menus.services.requests.DroppingOutRequest;
import logic.models.roles.Student;
import logic.models.roles.User;
import utils.logging.MasterLogger;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DroppingOutSubmission extends Template {
    private Student student;
    private JLabel submissionPrompt;
    private JButton submitRequest;
    private JLabel submittedPrompt;

    public DroppingOutSubmission(MainFrame mainFrame, MainMenu mainMenu, User user) {
        super(mainFrame, mainMenu);
        student = (Student) user;
        drawPanel();
    }

    @Override
    protected void initializeComponents() {
        submissionPrompt = new JLabel("Do you want to send a request to the educational deputy for dropping out?");
        submitRequest = new JButton("Submit Request for Dropping Out");
        submittedPrompt = new JLabel();
    }

    @Override
    protected void alignComponents() {
        submissionPrompt.setBounds(240, 230, 500, 50);
        submissionPrompt.setFont(new Font("", Font.BOLD, 14));
        add(submissionPrompt);
        submitRequest.setBounds(340, 300, 300, 35);
        add(submitRequest);
        submittedPrompt.setBounds(290, 400, 500, 40);
        add(submittedPrompt);
    }

    @Override
    protected void connectListeners() {
        submitRequest.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                DroppingOutRequest request = new DroppingOutRequest(student);
                MasterLogger.info("submitted a drop-out request", getClass());
                submittedPrompt.setText("Your request was sent to the education deputy of your department.");
            }
        });
    }
}
