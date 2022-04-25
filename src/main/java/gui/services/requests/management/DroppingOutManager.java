package gui.services.requests.management;

import gui.MainFrame;
import gui.main.MainMenu;
import logic.menus.services.requests.RecommendationRequest;
import logic.menus.services.requests.Request;
import logic.models.abstractions.Department;
import logic.models.roles.Professor;
import logic.models.roles.Student;
import utils.database.data.DepartmentsDB;
import utils.database.data.RequestsDB;
import utils.logging.MasterLogger;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DroppingOutManager extends RequestManager {
    public DroppingOutManager(MainFrame mainFrame, MainMenu mainMenu, Professor operatingProfessor) {
        super(mainFrame, mainMenu, operatingProfessor);
        columns = new String[] {"Student ID", "Student Name"};
        drawInteractivePanel();
    }

    @Override
    protected void setRequestsList() {
        Department department = DepartmentsDB.getProfessorsDepartment(operatingProfessor);
        requestsList = RequestsDB.getDroppingOutRequests(department);
    }

    @Override
    protected void setRequestsTableData() {
        data = new String[requestsList.size()][];
        Request request;
        Student requestingStudent;
        for (int i = 0; i < requestsList.size(); i++) {
            request = requestsList.get(i);
            requestingStudent = request.getRequestingStudent();
            data[i] = new String[] {requestingStudent.getStudentID(),
                    requestingStudent.getFirstName() + " " + requestingStudent.getLastName()};
        }
    }

    @Override
    protected void setApproveListener(int index) {
        JButton approveButton = approveButtonsList.get(index);
        approveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                Request request = requestsList.get(index);
                Student requestingStudent = request.getRequestingStudent();
                request.setRequestHasBeenRespondedTo(true);
                request.setRequestWasSuccessful(true);
                requestingStudent.setAcademicStatus(Student.AcademicStatus.DROPPED_OUT);
                request.updateInDatabase();
                requestingStudent.updateInDatabase();
                MasterLogger.info("dropping out request (requesting student ID: " +
                        requestingStudent.getStudentID() + ") has been accepted by the operating deputy (ID: " +
                        operatingProfessor.getTeachingID() + ")", getClass());
            }
        });
    }

    @Override
    protected void setDeclineListener(int index) {
        JButton declineButton = declineButtonsList.get(index);
        declineButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                Request request = requestsList.get(index);
                Student requestingStudent = request.getRequestingStudent();
                request.setRequestHasBeenRespondedTo(true);
                request.setRequestWasSuccessful(false);
                request.updateInDatabase();
                MasterLogger.info("dropping request (requesting student ID: " +
                        requestingStudent.getStudentID() + ") has been declined by the operating deputy (ID: " +
                        operatingProfessor.getTeachingID() + ")", getClass());
            }
        });
    }
}
