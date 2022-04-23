package gui.services.requests;

import gui.MainFrame;
import gui.Template;
import gui.main.MainMenu;
import logic.menus.services.requests.MinorRequest;
import logic.menus.services.requests.Request;
import logic.models.abstractions.Department;
import logic.models.roles.Professor;
import logic.models.roles.Student;
import logic.models.roles.User;
import utils.database.data.DepartmentsDB;
import utils.database.data.MinorsDB;
import utils.database.data.ProfessorsDB;
import utils.database.data.RequestsDB;
import utils.logging.MasterLogger;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;

public class MinorSubmission extends Template {
    private Student student;
    private JTable requestsTable;
    private JScrollPane scrollPane;
    private String[] columns;
    private String[][] data;
    private String[] departmentNames;
    private JComboBox<String> departmentSelector;
    private JButton submitRequest;

    public MinorSubmission(MainFrame mainFrame, MainMenu mainMenu, User user) {
        super(mainFrame, mainMenu);
        student = (Student) user;
        columns = new String[] {"Origin Department", "Target Department", "Status"};
        setTableData();
        setDepartmentNames();
        drawPanel();
    }

    private void setDepartmentNames() {
        LinkedList<Department> departmentsList = DepartmentsDB.getList();
        departmentNames = new String[departmentsList.size()];
        Department department;
        for (int i = 0; i < departmentsList.size(); i++) {
            department = departmentsList.get(i);
            departmentNames[i] = department.getDepartmentName();
        }
    }

    private void setTableData() {
        LinkedList<MinorRequest> minorRequestsList = MinorsDB.getStudentsMinorRequests(student);
        data = new String[minorRequestsList.size()][];
        MinorRequest request;
        for (int i = 0; i < minorRequestsList.size(); i++) {
            request = minorRequestsList.get(i);
            data[i] = new String[] {request.getOriginalDepartmentName(),
                                request.getTargetDepartmentName(),
                                request.getStatusString()};
        }
    }

    @Override
    protected void initializeComponents() {
        requestsTable = new JTable(data, columns);
        departmentSelector = new JComboBox<>(departmentNames);
        submitRequest = new JButton("Submit Request for Minor");
    }

    @Override
    protected void alignComponents() {
        requestsTable.setRowHeight(25);
        scrollPane = new JScrollPane(requestsTable);
        scrollPane.setBounds(50, 120, 885, 400);
        add(scrollPane);

        departmentSelector.setBounds(50, 50, 630, 40);
        add(departmentSelector);

        submitRequest.setBounds(690, 50, 240, 40);
        add(submitRequest);
    }

    @Override
    protected void connectListeners() {
        submitRequest.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if (!MinorRequest.studentHasSufficientlyHighGPA(student)) {
                    MasterLogger.error("student's GPA is not high enough (lower than 17)", getClass());
                    JOptionPane.showMessageDialog(mainFrame, "Your GPA has to be 17 or higher to be eligible for" +
                            " a minor request. Your GPA is not sufficiently high.");
                    return;
                }

                String targetDepartmentName = (String) departmentSelector.getSelectedItem();
                if (targetDepartmentName.equals(student.getDepartmentName())) {
                    MasterLogger.error("cannot request minor for the current department", getClass());
                    JOptionPane.showMessageDialog(mainFrame, "You cannot minor at your current department.");
                    return;
                }

                MinorRequest request = new MinorRequest(student, targetDepartmentName);
                MasterLogger.info("submitted a minor request to minor at " + targetDepartmentName, getClass());

                setTableData();
                DefaultTableModel tableModel = new DefaultTableModel(data, columns);
                requestsTable.setModel(tableModel);
            }
        });
    }
}
