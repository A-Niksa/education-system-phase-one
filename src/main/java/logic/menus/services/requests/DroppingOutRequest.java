package logic.menus.services.requests;

import logic.models.roles.Student;

public class DroppingOutRequest extends Request {
    public DroppingOutRequest(Student requestingStudent) {
        super(requestingStudent);
    }
}
