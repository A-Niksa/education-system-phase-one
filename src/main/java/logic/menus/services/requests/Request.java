package logic.menus.services.requests;

import logic.models.roles.Student;
import utils.database.data.RequestsDB;

public class Request {
    protected Student requestingStudent;
    protected boolean requestHasBeenRespondedTo;
    protected boolean requestWasSuccessful;
    protected String requestMessage;
    protected String responseMessage;

    public Request(Student requestingStudent) {
        this.requestingStudent = requestingStudent;
        requestHasBeenRespondedTo = false;
        requestWasSuccessful = false;
        requestMessage = "";
        responseMessage = "";
        RequestsDB.addToDatabase(this);
    }

    public Student getRequestingStudent() {
        return requestingStudent;
    }

    public void setRequestingStudent(Student requestingStudent) {
        this.requestingStudent = requestingStudent;
    }

    public boolean requestHasBeenRespondedTo() {
        return requestHasBeenRespondedTo;
    }

    public void setRequestHasBeenRespondedTo(boolean requestHasBeenRespondedTo) {
        this.requestHasBeenRespondedTo = requestHasBeenRespondedTo;
    }

    public boolean requestWasSuccessful() {
        return requestWasSuccessful;
    }

    public void setRequestWasSuccessful(boolean requestWasSuccessful) {
        this.requestWasSuccessful = requestWasSuccessful;
    }

    public String getRequestMessage() {
        return requestMessage;
    }

    public void setRequestMessage(String requestMessage) {
        this.requestMessage = requestMessage;
    }

    public String getResponseMessage() {
        return responseMessage;
    }

    public void setResponseMessage(String responseMessage) {
        this.responseMessage = responseMessage;
    }
}
