package utils.database.data;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import logic.menus.services.requests.RecommendationRequest;
import logic.menus.services.requests.Request;
import logic.models.roles.Professor;
import logic.models.roles.Student;

import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.LinkedList;

public class RecommendationsDB extends ModelDB {
    private static RecommendationsDB database;

    private LinkedList<RecommendationRequest> recommendationRequestsList;

    private RecommendationsDB() {
        recommendationRequestsList = new LinkedList<>();
        listType = new TypeToken<LinkedList<RecommendationRequest>>(){}.getType();
    }

    private static RecommendationsDB getInstance() {
        if (database == null) {
            database = new RecommendationsDB();
        }
        return database;
    }

    public static void addToDatabase(RecommendationRequest recommendationRequest) {
        getInstance().addToDatabaseByInstance(recommendationRequest);
    }

    private void addToDatabaseByInstance(RecommendationRequest recommendationRequest) {
        recommendationRequestsList.add(recommendationRequest);
    }

    public static void removeFromDatabase(RecommendationRequest recommendationRequest) {
        getInstance().removeFromDatabaseByInstance(recommendationRequest);
    }

    private void removeFromDatabaseByInstance(RecommendationRequest recommendationRequest) {
        for (int i = 0; i < recommendationRequestsList.size(); i++) {
            if (recommendationRequest == recommendationRequestsList.get(i)) {
                recommendationRequestsList.remove(i);
                return;
            }
        }
    }

    public static void setDatabase(LinkedList<RecommendationRequest> recommendationsList) {
        if (recommendationsList != null) {
            getInstance().setDatabaseByInstance(recommendationsList);
        }
    }

    private void setDatabaseByInstance(LinkedList<RecommendationRequest> recommendationsList) {
        this.recommendationRequestsList = recommendationsList;
    }

    public static Type getListType() {
        return getInstance().getListTypeByInstance();
    }

    public static LinkedList<RecommendationRequest> getList() {
        return getInstance().getListByInstance();
    }

    private LinkedList<RecommendationRequest> getListByInstance() {
        return recommendationRequestsList;
    }

    public static void addListToJson(FileWriter writer, Gson gson) {
        getInstance().addListToJsonByInstance(writer, gson);
    }

    @Override
    protected void addListToJsonByInstance(FileWriter writer, Gson gson) {
        try {
            writer.write(gson.toJson(recommendationRequestsList));
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static LinkedList<RecommendationRequest> getStudentsRecommendations(Student targetStudent) {
        return getInstance().getStudentsRecommendationsByInstance(targetStudent);
    }

    private LinkedList<RecommendationRequest> getStudentsRecommendationsByInstance(Student targetStudent) {
        LinkedList<RecommendationRequest> recommendationRequests = new LinkedList<>();
        Student potentialStudent;
        String targetStudentID = targetStudent.getStudentID();
        for (RecommendationRequest request : recommendationRequestsList) {
            potentialStudent = request.getRequestingStudent();
            if (potentialStudent.getStudentID().equals(targetStudentID)) {
                recommendationRequests.add(request);
            }
        }
        return recommendationRequests;
    }

    public static LinkedList<Request> getRecommendationRequests(Professor targetProfessor) {
        return getInstance().getRecommendationRequestsByInstance(targetProfessor);
    }

    private LinkedList<Request> getRecommendationRequestsByInstance(Professor targetProfessor) {
        LinkedList<Request> recommendationRequestsToProfessor = new LinkedList<>();
        String targetProfessorID = targetProfessor.getTeachingID();
        String recipientProfessorID;
        for (Request request : recommendationRequestsList) {
            if (!request.requestHasBeenRespondedTo()) {
                recipientProfessorID = request.getRequestRecipient().getTeachingID();
                if (targetProfessorID.equals(recipientProfessorID)) {
                    recommendationRequestsToProfessor.add(request);
                }
            }
        }
        return recommendationRequestsToProfessor;
    }
}
