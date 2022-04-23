package logic.menus.services.requests;

import logic.models.abstractions.Department;
import logic.models.roles.Professor;
import logic.models.roles.Student;
import utils.database.data.DepartmentsDB;
import utils.database.data.MinorsDB;

public class MinorRequest extends Request {
    public enum MinorStatus {
        SUBMITTED,
        ACCEPTED,
        REJECTED
    }

    private String originalDepartmentName;
    private String targetDepartmentName;
    private boolean originalDepartmentAccepted;
    private boolean targetDepartmentAccepted;
    private Professor requestRecipientFromTargetDepartment;
    private MinorStatus status;

    public MinorRequest(Student requestingStudent, String targetDepartmentName) {
        super(requestingStudent);
        this.targetDepartmentName = targetDepartmentName;
        setOriginalDepartmentName();
        requestType = RequestType.MINOR;
        originalDepartmentAccepted = false;
        targetDepartmentAccepted = false;
        status = MinorStatus.SUBMITTED;
        setRequestRecipients();
        MinorsDB.addToDatabase(this);
    }

    public static boolean studentHasSufficientlyHighGPA(Student student) {
        double GPA = student.getTotalGPA();
        return GPA >= 17;
    }

    private void setOriginalDepartmentName() {
        Department department = DepartmentsDB.getStudentsDepartment(requestingStudent);
        originalDepartmentName = department.getDepartmentName();
    }

    private void setRequestRecipients() {
        setOriginalDepartmentRecipient();
        setTargetDepartmentRecipient();
    }

    private void setOriginalDepartmentRecipient() {
        Department department = DepartmentsDB.getStudentsDepartment(requestingStudent);
        requestRecipient = department.getEducationDeputy();
    }

    private void setTargetDepartmentRecipient() {
        Department department = DepartmentsDB.getDepartmentWithName(targetDepartmentName);
        requestRecipientFromTargetDepartment = department.getEducationDeputy();
    }

    public String getOriginalDepartmentName() {
        return originalDepartmentName;
    }

    public String getTargetDepartmentName() {
        return targetDepartmentName;
    }

    public boolean originalDepartmentAccepted() {
        return originalDepartmentAccepted;
    }

    public void setOriginalDepartmentAccepted(boolean originalDepartmentAccepted) {
        this.originalDepartmentAccepted = originalDepartmentAccepted;
    }

    public boolean targetDepartmentAccepted() {
        return targetDepartmentAccepted;
    }

    public void setTargetDepartmentAccepted(boolean targetDepartmentAccepted) {
        this.targetDepartmentAccepted = targetDepartmentAccepted;
    }

    public String getStatusString() {
        switch (status) {
            case SUBMITTED:
                return "Submitted";
            case ACCEPTED:
                return "Accepted";
            case REJECTED:
                return "Rejected";
        }
        return "";
    }

    public MinorStatus getStatus() {
        return status;
    }

    public void setStatus(MinorStatus status) {
        this.status = status;
    }
}
