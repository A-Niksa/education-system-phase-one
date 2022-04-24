package gui.enrolment;

import gui.MainFrame;
import gui.Template;
import gui.main.MainMenu;
import logic.models.roles.Professor;
import logic.models.roles.User;
import utils.database.data.ProfessorsDB;

import javax.swing.*;
import java.util.LinkedList;

public class ProfessorsListView extends Template {
    private JTable professorsTable;
    private String[] columns;
    private String[][] data;

    public ProfessorsListView(MainFrame mainFrame, MainMenu mainMenu) {
        super(mainFrame, mainMenu);
        columns = new String[] {"Name and Surname", "Academic Rank", "Office Number", "Email Address"};
        setTableData();
        drawPanel();
    }

    private void setTableData() {
        LinkedList<Professor> professorsList = ProfessorsDB.getList();
        data = new String[professorsList.size()][];
        Professor professor;
        for (int i = 0; i < professorsList.size(); i++) {
            professor = professorsList.get(i);
            data[i] = new String[] {professor.getFirstName() + " " + professor.getLastName(),
                                    professor.getAcademicRankString(),
                                    professor.getOfficeNumber() + "",
                                    professor.getEmailAddress()};
        }
    }

    @Override
    protected void initializeComponents() {
        professorsTable = new JTable(data, columns);
    }

    @Override
    protected void alignComponents() {
        professorsTable.setRowHeight(25);
        JScrollPane scrollPane = new JScrollPane(professorsTable);
        scrollPane.setBounds(50, 50, 885, 530);
        add(scrollPane);
    }

    @Override
    protected void connectListeners() {}
}
