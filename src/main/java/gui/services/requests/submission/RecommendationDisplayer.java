package gui.services.requests.submission;

import logic.menus.services.requests.RecommendationRequest;
import logic.models.roles.Student;
import utils.database.data.RecommendationsDB;

import javax.swing.*;
import java.util.LinkedList;

public class RecommendationDisplayer {
    private static final int STARTING_X = 730;
    private static final int STARTING_Y = 92;

    private RecommendationSubmission panel;
    private LinkedList<JLabel> currentRecommendations;
    private Student student;

    public RecommendationDisplayer(RecommendationSubmission panel, LinkedList<JLabel> currentRecommendations, Student student) {
        this.panel = panel;
        this.currentRecommendations = currentRecommendations;
        this.student = student;
    }

    public void displayRecommendations() {
        updateRecommendations();
        alignRecommendations();
    }

    private void updateRecommendations() {
        LinkedList<RecommendationRequest> recommendationRequestsList = RecommendationsDB.getStudentsRecommendations(student);
        RecommendationRequest recommendationRequest;
        for (RecommendationRequest request : recommendationRequestsList) {
            if (request.requestWasSuccessful()) {
                JLabel recommendationText = new JLabel(request.getRecommendationText());
                currentRecommendations.add(recommendationText);
            }
        }
    }

    private void alignRecommendations() {
        int currentX = STARTING_X, currentY = STARTING_Y;
        int height = 145;
        for (JLabel recommendationTextLabel : currentRecommendations) {
            recommendationTextLabel.setBounds(currentX, currentY, 222, height);
            panel.add(recommendationTextLabel);
            currentY += height;
        }
    }
}
