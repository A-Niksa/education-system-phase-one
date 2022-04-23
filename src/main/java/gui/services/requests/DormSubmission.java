package gui.services.requests;

import gui.MainFrame;
import gui.Template;
import gui.main.MainMenu;
import logic.menus.services.requests.DormRequest;
import logic.models.roles.Student;
import logic.models.roles.User;
import utils.logging.MasterLogger;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DormSubmission extends Template {
    private Student student;
    private JLabel dormPrompt;
    private JButton submitRequest;
    private JSeparator separator;
    private JLabel resultText;

    public DormSubmission(MainFrame mainFrame, MainMenu mainMenu, User user) {
        super(mainFrame, mainMenu);
        student = (Student) user;
        drawPanel();
    }

    @Override
    protected void initializeComponents() {
        dormPrompt = new JLabel("Would you like to submit a request for receving a dormitory room?");
        submitRequest = new JButton("Submit Request for Dorm Room");
        separator = new JSeparator();
        resultText = new JLabel();
    }

    @Override
    protected void alignComponents() {
        dormPrompt.setBounds(70, 230, 500, 100);
        dormPrompt.setFont(new Font("", Font.BOLD, 13));
        add(dormPrompt);
        submitRequest.setBounds(140, 300, 250, 35);
        add(submitRequest);
        separator.setOrientation(SwingConstants.VERTICAL);
        separator.setBackground(Color.BLACK);
        separator.setForeground(Color.BLACK);
        separator.setBounds(528, 195, 15, 250);
        add(separator);
        resultText.setBounds(575, 193, 470, 200);
        resultText.setFont(new Font("", Font.BOLD, 14));
        add(resultText);
    }

    @Override
    protected void connectListeners() {
        submitRequest.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                resultText.setText("Your situation is being evaluated by our system...");

                DormRequest request = new DormRequest(student);
                MasterLogger.info("requested a dorm room", getClass());

                boolean willGetDorm = request.willGetDorm();
                String randomResult;
                if (willGetDorm) {
                    randomResult = "You have been successfully assigned a dormitory room.";
                    MasterLogger.info("student has been assigned a dorm room", getClass());
                } else {
                    randomResult = "Unfortunately, we cannot offer you a room at this time.";
                    MasterLogger.info("student was not able to get a dorm room", getClass());
                }

                Timer timer = new Timer(3200,
                        e -> resultText.setText(randomResult));
                timer.setRepeats(false); //welp
                timer.start();


                if (willGetDorm) {
                    MasterLogger.info("student has been assigned a dorm room", getClass());
                } else {
                    MasterLogger.info("student was not able to get a dorm room", getClass());
                }
            }
        });
    }
}
