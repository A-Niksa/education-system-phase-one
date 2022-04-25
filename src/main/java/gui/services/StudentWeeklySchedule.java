package gui.services;

import gui.MainFrame;
import gui.Template;
import gui.main.MainMenu;
import logic.menus.services.StudentScheduleLoader;
import logic.models.roles.Student;
import logic.models.roles.User;
import utils.timing.Weekday;
import utils.timing.WeeklyDate;

import javax.swing.*;
import java.awt.*;
import java.util.LinkedList;

public class StudentWeeklySchedule extends Template {
    private Student student;
    private JPanel saturdayPanel;
    private JTable saturdayTable;
    private String[][] saturdayData;
    private JPanel sundayPanel;
    private JTable sundayTable;
    private String[][] sundayData;
    private JPanel mondayPanel;
    private JTable mondayTable;
    private String[][] mondayData;
    private JPanel tuesdayPanel;
    private JTable tuesdayTable;
    private String[][] tuesdayData;
    private JPanel wednesdayPanel;
    private JTable wednesdayTable;
    private String[][] wednesdayData;
    private JPanel thursdayPanel;
    private JTable thursdayTable;
    private String[][] thursdayData;
    private JPanel fridayPanel;
    private JTable fridayTable;
    private String[][] fridayData;
    private LinkedList<JPanel> weekdayPanels;
    private LinkedList<JTable> weekdayTables;
    private String[] weekdays;
    private String[] columns;
    private JTabbedPane tabbedPane;

    public StudentWeeklySchedule(MainFrame mainFrame, MainMenu mainMenu, User user) {
        super(mainFrame, mainMenu);
        student = (Student) user;
        weekdays = new String[] {"Saturday", "Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday"};
        columns = new String[] {"Course Name", "Instructor's Name", "Starts From", "Ends At"};
        drawPanel();
    }

    private String[][] getTableData(Weekday weekday) {
        LinkedList<WeeklyDate> coursesInfoOfStudent = StudentScheduleLoader.getStudentsCourseInformationPerDay(student, weekday);
        String[][] data = new String[coursesInfoOfStudent.size()][];
        WeeklyDate courseInfo;
        for (int i = 0; i < coursesInfoOfStudent.size(); i++) {
            courseInfo = coursesInfoOfStudent.get(i);
            data[i] = new String[] {courseInfo.getCourseName(),
                                    courseInfo.getProfessorName(),
                                    courseInfo.getStartTimeString(),
                                    courseInfo.getEndTimeString()};
        }
        return data;
    }

    @Override
    protected void initializeComponents() {
        weekdayPanels = new LinkedList<>();
        weekdayTables = new LinkedList<>();

        saturdayPanel = new JPanel();
        weekdayPanels.add(saturdayPanel);
        saturdayTable = new JTable(getTableData(Weekday.SATURDAY), columns);
        weekdayTables.add(saturdayTable);
        sundayPanel = new JPanel();
        weekdayPanels.add(sundayPanel);
        sundayTable = new JTable(getTableData(Weekday.SUNDAY), columns);
        weekdayTables.add(sundayTable);
        mondayPanel = new JPanel();
        weekdayPanels.add(mondayPanel);
        mondayTable = new JTable(getTableData(Weekday.MONDAY), columns);
        weekdayTables.add(mondayTable);
        tuesdayPanel = new JPanel();
        weekdayPanels.add(tuesdayPanel);
        tuesdayTable = new JTable(getTableData(Weekday.TUESDAY), columns);
        weekdayTables.add(tuesdayTable);
        wednesdayPanel = new JPanel();
        weekdayPanels.add(wednesdayPanel);
        wednesdayTable = new JTable(getTableData(Weekday.WEDNESDAY), columns);
        weekdayTables.add(wednesdayTable);
        thursdayPanel = new JPanel();
        weekdayPanels.add(thursdayPanel);
        thursdayTable = new JTable(getTableData(Weekday.THURSDAY), columns);
        weekdayTables.add(thursdayTable);
        fridayPanel = new JPanel();
        weekdayPanels.add(fridayPanel);
        fridayTable = new JTable(getTableData(Weekday.FRIDAY), columns);
        weekdayTables.add(fridayTable);
        tabbedPane = new JTabbedPane();
    }

    private void alignTable(JTable table, JPanel panel) {
        table.setRowHeight(25);
        JScrollPane scrollPane = new JScrollPane(table);
        panel.setLayout(new BorderLayout(0, 0));
        panel.add(scrollPane, BorderLayout.CENTER);
    }

    @Override
    protected void alignComponents() {
        tabbedPane.setBounds(50, 50, 885, 530);
        String headerName;
        for (int i = 0; i < weekdayPanels.size(); i++) {
            headerName = weekdays[i];
            tabbedPane.add(headerName, weekdayPanels.get(i));
            alignTable(weekdayTables.get(i), weekdayPanels.get(i));
        }
        add(tabbedPane);
    }

    @Override
    protected void connectListeners() {}
}