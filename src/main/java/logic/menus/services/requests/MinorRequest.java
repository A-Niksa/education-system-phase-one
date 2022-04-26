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

    private String originDepartmentName;
    private String targetDepartmentName;
    private boolean originDepartmentResponded;
    private boolean originDepartmentAccepted;
    private boolean targetDepartmentAccepted;
    private boolean targetDepartmentResponded;
    private Professor requestRecipientFromTargetDepartment;
    private MinorStatus status;

    public MinorRequest(Student requestingStudent, String targetDepartmentName) {
        super(requestingStudent);
        this.targetDepartmentName = targetDepartmentName;
        setOriginalDepartmentName();
        requestType = RequestType.MINOR;
        originDepartmentResponded = false;
        originDepartmentAccepted = false;
        targetDepartmentAccepted = false;
        targetDepartmentResponded = false;
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
        originDepartmentName = department.getDepartmentName();
    }

    private void setRequestRecipients() {
        setOriginDepartmentRecipient();
        setTargetDepartmentRecipient();
    }

    private void setOriginDepartmentRecipient() {
        Department department = DepartmentsDB.getStudentsDepartment(requestingStudent);
        requestRecipient = department.getEducationDeputy();
    }

    private void setTargetDepartmentRecipient() {
        Department department = DepartmentsDB.getDepartmentWithName(targetDepartmentName);
        requestRecipientFromTargetDepartment = department.getEducationDeputy();
    }

    public String getOriginDepartmentName() {
        return originDepartmentName;
    }

    public String getTargetDepartmentName() {
        return targetDepartmentName;
    }

    public boolean originDepartmentAccepted() {
        return originDepartmentAccepted;
    }

    public void setOriginDepartmentAccepted(boolean originalDepartmentAccepted) {
        this.originDepartmentAccepted = originalDepartmentAccepted;
    }

    public boolean targetDepartmentAccepted() {
        return targetDepartmentAccepted;
    }

    public void setTargetDepartmentAccepted(boolean targetDepartmentAccepted) {
        this.targetDepartmentAccepted = targetDepartmentAccepted;
    }

    public boolean originDepartmentResponded() {
        return originDepartmentResponded;
    }

    public void setOriginDepartmentResponded(boolean originDepartmentResponded) {
        this.originDepartmentResponded = originDepartmentResponded;
    }

    public boolean targetDepartmentResponded() {
        return targetDepartmentResponded;
    }

    public void setTargetDepartmentResponded(boolean targetDepartmentResponded) {
        this.targetDepartmentResponded = targetDepartmentResponded;
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

    public boolean deputyIsFromOriginDepartment(Professor deputy) {
        String deputyID = deputy.getTeachingID();
        Professor originDepartmentsDeputy = DepartmentsDB.getDeputyWithDepartmentName(originDepartmentName);
        return deputyID.equals(originDepartmentsDeputy.getTeachingID());
    }

    public void updateInDatabase() {
        MinorsDB.removeFromDatabase(this);
        MinorsDB.addToDatabase(this);
    }
}
