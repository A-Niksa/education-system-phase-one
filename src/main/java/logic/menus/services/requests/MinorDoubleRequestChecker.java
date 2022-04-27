package logic.menus.services.requests;

import logic.models.roles.Student;
import utils.database.data.MinorsDB;

import java.util.LinkedList;

public class MinorDoubleRequestChecker {
    public static boolean requestHasBeenPreviouslyAccepted(Student student, String targetDepartmentName) {
        LinkedList<MinorRequest> minorRequestsList = MinorsDB.getList();
        String requestingStudentID = student.getStudentID();
        String potentialStudentID;
        for (MinorRequest request : minorRequestsList) {
            potentialStudentID = request.getRequestingStudent().getStudentID();
            if (potentialStudentID.equals(requestingStudentID)) {
                if (request.getTargetDepartmentName().equals(targetDepartmentName) &&
                        request.requestWasSuccessful()) {
                    return true;
                }
            }
        }
        return false;
    }
}
