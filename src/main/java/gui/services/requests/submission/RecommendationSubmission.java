package gui.services.requests.submission;

import gui.MainFrame;
import gui.Template;
import gui.main.MainMenu;
import logic.menus.services.requests.RecommendationRequest;
import logic.models.roles.Professor;
import logic.models.roles.Student;
import logic.models.roles.User;
import utils.database.data.ProfessorsDB;
import utils.logging.MasterLogger;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;

public class RecommendationSubmission extends Template {
    private Student student;
    private JTextField recommendingProfessorID;
    private JButton submitRequest;
    private JTable professorsTable;
    private String[] columns;
    private String[][] data;
    private JSeparator separator;
    private JLabel currentRecommendationsPrompt;
    private LinkedList<JLabel> currentRecommendations;
    private RecommendationDisplayer recommendationDisplayer;

    public RecommendationSubmission(MainFrame mainFrame, MainMenu mainMenu, User user) {
        super(mainFrame, mainMenu);
        student = (Student) user;
        columns = new String[]{"Teaching ID", "Name and Surname", "Academic Rank", "Email Address"};
        setTableData();
        drawPanel();
    }

    private void setTableData() {
        LinkedList<Professor> professorsList = ProfessorsDB.getList();
        data = new String[professorsList.size()][];
        Professor professor;
        for (int i = 0; i < professorsList.size(); i++) {
            professor = professorsList.get(i);
            data[i] = new String[]{professor.getTeachingID(),
                    professor.getFirstName() + " " + professor.getLastName(),
                    professor.getAcademicRankString(),
                    professor.getEmailAddress()};
        }
    }

    @Override
    protected void initializeComponents() {
        recommendingProfessorID = new JTextField("Teaching ID of the Recommending Professor...");
        submitRequest = new JButton("Submit Request for a Recommendation Letter");
        professorsTable = new JTable(data, columns);
        separator = new JSeparator();
        currentRecommendationsPrompt = new JLabel("Current Recommendation Letters:");
        currentRecommendations = new LinkedList<>();
        recommendationDisplayer = new RecommendationDisplayer(this, currentRecommendations, student);
    }

    @Override
    protected void alignComponents() {
        professorsTable.setRowHeight(25);
        JScrollPane scrollPane = new JScrollPane(professorsTable);
        scrollPane.setBounds(50, 120, 650, 465);
        add(scrollPane);

        recommendingProfessorID.setBounds(50, 50, 330, 40);
        add(recommendingProfessorID);
        submitRequest.setBounds(388, 50, 310, 40);
        add(submitRequest);

        separator.setOrientation(SwingConstants.VERTICAL);
        separator.setBackground(Color.BLACK);
        separator.setForeground(Color.BLACK);
        separator.setBounds(710, 50, 15, 550);
        add(separator);

        currentRecommendationsPrompt.setBounds(730, 50, 250, 40);
        add(currentRecommendationsPrompt);
        recommendationDisplayer.displayRecommendations();
    }

    @Override
    protected void connectListeners() {
        submitRequest.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                String teachingID = recommendingProfessorID.getText();
                Professor professor = ProfessorsDB.getProfessorWithID(teachingID);

                if (professor == null) {
                    MasterLogger.error("no professor with an ID of " + teachingID + " exists", getClass());
                    JOptionPane.showMessageDialog(mainFrame, "No professor with an ID of " + teachingID + " exists.");
                    return;
                }

                RecommendationRequest request = new RecommendationRequest(student, professor);
                MasterLogger.error("recommendation letter request submitted", getClass());
                JOptionPane.showMessageDialog(mainFrame, "Recommendation request has been successfully sent to" +
                        " the selected professor.");
            }
        });
    }
}
