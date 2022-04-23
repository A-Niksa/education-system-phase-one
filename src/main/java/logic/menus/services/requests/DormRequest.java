package logic.menus.services.requests;

import logic.models.roles.Student;

import java.util.Random;

public class DormRequest extends Request {
    private Random random;

    public DormRequest(Student requestingStudent) {
        super(requestingStudent);
        requestType = RequestType.DORM;
        random = new Random();
    }

    public boolean willGetDorm() {
        return random.nextBoolean();
    }
}
