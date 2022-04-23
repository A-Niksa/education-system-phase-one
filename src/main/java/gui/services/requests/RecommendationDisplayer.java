package gui.services.requests;

import logic.menus.services.requests.RecommendationRequest;
import logic.menus.services.requests.Request;
import logic.models.roles.Student;
import utils.database.data.RequestsDB;

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
        LinkedList<Request> recommendationRequestsList = RequestsDB.getStudentsRecommendations(student);
        RecommendationRequest recommendationRequest;
        for (Request request : recommendationRequestsList) {
            if (request.requestWasSuccessful()) {
                recommendationRequest = specialCastToRecommendationRequest(request);
                JLabel recommendationText = new JLabel(recommendationRequest.getRecommendationText());
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

    private RecommendationRequest specialCastToRecommendationRequest(Request request) {
        RecommendationRequest recommendationRequest = new RecommendationRequest(request.getRequestingStudent(),
                request.getRequestRecipient());
        recommendationRequest.setRequestHasBeenRespondedTo(request.requestHasBeenRespondedTo());
        recommendationRequest.setRequestWasSuccessful(request.requestWasSuccessful());
        recommendationRequest.setRequestMessage(request.getRequestMessage());
        recommendationRequest.setResponseMessage(request.getResponseMessage());

        RequestsDB.removeFromDatabase(request);

        return recommendationRequest;
    }
}
