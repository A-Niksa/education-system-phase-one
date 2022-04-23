package logic.menus.services.requests;

import logic.models.roles.Professor;
import logic.models.roles.Student;

public class RecommendationRequest extends Request {
    public RecommendationRequest(Student requestingStudent, Professor professor) {
        super(requestingStudent);
        requestType = RequestType.RECOMMENDATION;
        requestRecipient = professor;
    }

    public String getRecommendationText() { //welp
        String professorName = requestRecipient.getFirstName() + " " + requestRecipient.getLastName();
        String studentName = requestingStudent.getFirstName() + " " + requestingStudent.getLastName();

        String recommendationText = "I, " + professorName + ", hereby declare that " + studentName + " has been one of" +
                " my students and based on the level of excellence he has demonstrated in a plethora of different aspects" +
                " at the courses I have taught, I recommend him to be admitted to your academic program.";
        setResponseMessage(recommendationText);

        return convertToHTMLFormat(recommendationText);
    }

    private String convertToHTMLFormat(String message) {
        return "<html>" + message;
    }

}
