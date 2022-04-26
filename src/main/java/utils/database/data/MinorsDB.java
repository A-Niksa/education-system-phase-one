package utils.database.data;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import logic.menus.services.requests.MinorRequest;
import logic.menus.services.requests.Request;
import logic.models.roles.Professor;
import logic.models.roles.Student;

import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.LinkedList;

public class MinorsDB extends ModelDB {
    private static MinorsDB database;

    private LinkedList<MinorRequest> minorRequestsList;

    private MinorsDB() {
        minorRequestsList = new LinkedList<>();
        listType = new TypeToken<LinkedList<MinorRequest>>(){}.getType();
    }

    private static MinorsDB getInstance() {
        if (database == null) {
            database = new MinorsDB();
        }
        return database;
    }

    public static void addToDatabase(MinorRequest minor) {
        getInstance().addToDatabaseByInstance(minor);
    }

    private void addToDatabaseByInstance(MinorRequest minor) {
        minorRequestsList.add(minor);
    }

    public static void removeFromDatabase(MinorRequest minor) {
        getInstance().removeFromDatabaseByInstance(minor);
    }

    private void removeFromDatabaseByInstance(MinorRequest minor) {
        for (int i = 0; i < minorRequestsList.size(); i++) {
            if (minor == minorRequestsList.get(i)) {
                minorRequestsList.remove(i);
                return;
            }
        }
    }

    public static void setDatabase(LinkedList<MinorRequest> minorsList) {
        if (minorsList != null) {
            getInstance().setDatabaseByInstance(minorsList);
        }
    }

    private void setDatabaseByInstance(LinkedList<MinorRequest> minorsList) {
        this.minorRequestsList = minorsList;
    }

    public static Type getListType() {
        return getInstance().getListTypeByInstance();
    }

    public static LinkedList<MinorRequest> getList() {
        return getInstance().getListByInstance();
    }

    private LinkedList<MinorRequest> getListByInstance() {
        return minorRequestsList;
    }

    public static void addListToJson(FileWriter writer, Gson gson) {
        getInstance().addListToJsonByInstance(writer, gson);
    }

    @Override
    protected void addListToJsonByInstance(FileWriter writer, Gson gson) {
        try {
            writer.write(gson.toJson(minorRequestsList));
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static LinkedList<MinorRequest> getStudentsMinorRequests(Student targetStudent) {
        return getInstance().getStudentsMinorRequestsByInstance(targetStudent);
    }

    private LinkedList<MinorRequest> getStudentsMinorRequestsByInstance(Student targetStudent) {
        LinkedList<MinorRequest> minorRequests = new LinkedList<>();
        Student potentialStudent;
        String targetStudentID = targetStudent.getStudentID();
        for (MinorRequest minorRequest : minorRequestsList) {
            potentialStudent = minorRequest.getRequestingStudent();
            if (potentialStudent.getStudentID().equals(targetStudentID)) {
                minorRequests.add(minorRequest);
            }
        }
        return minorRequests;
    }

    public static LinkedList<Request> getMinorRequestsOfDeputy(Professor deputy) {
        return getInstance().getMinorsRequestsOfDeputyByInstance(deputy);
    }

    private LinkedList<Request> getMinorsRequestsOfDeputyByInstance(Professor deputy) {
        LinkedList<Request> minorRequestsOfDeputy = new LinkedList<>();
        String deputyID = deputy.getTeachingID();
        String originDepartmentName, targetDepartmentName;
        Professor originDepartmentDeputy, targetDepartmentDeputy;
        for (MinorRequest request : minorRequestsList) {
            originDepartmentName = request.getOriginDepartmentName();
            targetDepartmentName = request.getTargetDepartmentName();
            originDepartmentDeputy = DepartmentsDB.getDeputyWithDepartmentName(originDepartmentName);
            targetDepartmentDeputy = DepartmentsDB.getDeputyWithDepartmentName(targetDepartmentName);

            if (originDepartmentDeputy.getTeachingID().equals(deputyID) ||
                    targetDepartmentDeputy.getTeachingID().equals(deputyID)) {
                if (!request.requestHasBeenRespondedTo()) {
                    minorRequestsOfDeputy.add(request);
                }
            }
        }
        return minorRequestsOfDeputy;
    }

    public static void ensureStatusesAreUpToDate() {
        getInstance().ensureStatusesAreUpToDateByInstance();
    }

    private void ensureStatusesAreUpToDateByInstance() {
        boolean requestHasBeenRespondedTo;
        boolean requestHasBeenAccepted;
        for (MinorRequest request : minorRequestsList) {
            requestHasBeenRespondedTo = request.originDepartmentResponded() && request.targetDepartmentResponded();
            if (requestHasBeenRespondedTo) {
                request.setRequestHasBeenRespondedTo(true);
                requestHasBeenAccepted = request.originDepartmentAccepted() && request.targetDepartmentAccepted();
                if (requestHasBeenAccepted) {
                    request.setRequestWasSuccessful(true);
                    request.setStatus(MinorRequest.MinorStatus.ACCEPTED);
                } else {
                    // requestWasSuccessful() is false by default, so there's no need for setting it again
                    request.setStatus(MinorRequest.MinorStatus.REJECTED);
                }
            }
        }
    }
}
