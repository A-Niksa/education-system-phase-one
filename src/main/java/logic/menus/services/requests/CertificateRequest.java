package logic.menus.services.requests;

import logic.models.abstractions.Department;
import logic.models.roles.Student;
import utils.database.data.DepartmentsDB;
import utils.timing.TimeManager;

public class CertificateRequest extends Request {
    public CertificateRequest(Student requestingStudent) {
        super(requestingStudent);
    }

    public String getCertificateText() {
        String nameAndSurname = requestingStudent.getFirstName() + " " + requestingStudent.getLastName();
        String studentID = requestingStudent.getStudentID();
        Department studentsDepartment = DepartmentsDB.getStudentsDepartment(requestingStudent);
        String majorName = studentsDepartment.getDepartmentName();
        String currentDate = TimeManager.getDate();

        String certificateText = "It is hereby certified that " + nameAndSurname + " (student ID: " + studentID +
                ") is currently a student of Sharif University of Technology and is majoring in " + majorName + "." +
                "\nDate of Certification: " + currentDate;

        return convertToHTMLFormat(certificateText);
    }

    private String convertToHTMLFormat(String message) {
        return "<html>" + message.replaceAll("\n", "<br>");
    }
}
