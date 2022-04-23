package logic.menus.services.requests;

import logic.models.roles.Professor;
import logic.models.roles.Student;
import utils.database.data.RequestsDB;

public class Request {
    public enum RequestType {
        DROPPING_OUT,
        CERTIFICATE,
        RECOMMENDATION,
        MINOR
    }

    private static int idEnumeration = 1;

    protected String requestID;
    protected Student requestingStudent;
    protected Professor requestRecipient;
    protected boolean requestHasBeenRespondedTo;
    protected boolean requestWasSuccessful;
    protected String requestMessage;
    protected String responseMessage;
    protected RequestType requestType;

    public Request(Student requestingStudent) {
        this.requestingStudent = requestingStudent;
        requestHasBeenRespondedTo = false;
        requestWasSuccessful = false;
        requestMessage = "";
        responseMessage = "";
        setRequestID();
        RequestsDB.addToDatabase(this);
    }

    private void setRequestID() {
        this.requestID = String.valueOf(idEnumeration);
        idEnumeration++;
    }

    public String getRequestID() {
        return requestID;
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

    public Professor getRequestRecipient() {
        return requestRecipient;
    }

    public RequestType getRequestType() {
        return requestType;
    }
}
