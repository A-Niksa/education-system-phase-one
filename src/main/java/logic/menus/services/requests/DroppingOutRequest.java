package logic.menus.services.requests;

import logic.models.abstractions.Department;
import logic.models.roles.Student;
import utils.database.data.DepartmentsDB;

public class DroppingOutRequest extends Request {
    public DroppingOutRequest(Student requestingStudent) {
        super(requestingStudent);
        requestType = RequestType.DROPPING_OUT;
        setRequestRecipient();
    }

    private void setRequestRecipient() {
        Department department = DepartmentsDB.getStudentsDepartment(requestingStudent);
        requestRecipient = department.getEducationDeputy();
    }
}
