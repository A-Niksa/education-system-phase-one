package gui.services.requests.management;

import gui.MainFrame;
import gui.Template;
import gui.main.MainMenu;
import logic.menus.services.requests.Request;
import logic.models.roles.Professor;
import logic.models.roles.Student;

import javax.swing.*;
import java.util.LinkedList;

public abstract class RequestManager extends Template {
    Professor operatingProfessor;
    Student requestingStudent;
    protected JTable requestsTable;
    protected String[] columns;
    protected String[][] data;
    protected LinkedList<Request> requestsList;
    protected LinkedList<JButton> approveButtonsList;
    protected LinkedList<JButton> declineButtonsList;

    public RequestManager(MainFrame mainFrame, MainMenu mainMenu, Professor operatingProfessor) {
        super(mainFrame, mainMenu);
        this.operatingProfessor = operatingProfessor;
    }

    protected abstract void setRequestsList();

    protected abstract void setRequestsTableData();

    protected void setTable() {
        setRequestsList();
        setRequestsTableData();
    }

    protected void drawInteractivePanel() {
        setTable();
        drawPanel();
    }

    @Override
    protected void initializeComponents() {
        approveButtonsList = new LinkedList<>();
        declineButtonsList = new LinkedList<>();

        requestsTable = new JTable(data, columns);

        for (int i = 0; i < requestsList.size(); i++) {
            JButton approveButton = new JButton("Approve");
            approveButtonsList.add(approveButton);
            JButton declineButton = new JButton("Decline");
            declineButtonsList.add(declineButton);
        }
    }

    @Override
    protected void alignComponents() {
        requestsTable.setRowHeight(30);
        JScrollPane scrollPane = new JScrollPane(requestsTable);
        scrollPane.setBounds(50, 50, 688, 530);
        add(scrollPane);

        int currentX = 760, currentY = 72;
        int buttonHeight = 27;
        JButton approveButton, declineButton;
        for (int i = 0; i < requestsList.size(); i++) {
            approveButton = approveButtonsList.get(i);
            declineButton = declineButtonsList.get(i);
            approveButton.setBounds(currentX, currentY, 100, buttonHeight);
            declineButton.setBounds(currentX + 105, currentY, 100, buttonHeight);
            add(approveButton);
            add(declineButton);
            currentY += buttonHeight + 3;
        }
    }

    @Override
    protected void connectListeners() {
        for (int i = 0; i < requestsList.size(); i++) {
            setApproveListener(i);
            setDeclineListener(i);
        }
    }

    protected abstract void setApproveListener(int index);

    protected abstract void setDeclineListener(int index);
}
