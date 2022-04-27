package logic.menus.standing;

import logic.models.abstractions.Department;
import logic.models.abstractions.Transcript;
import logic.models.roles.Student;
import utils.database.data.DepartmentsDB;
import utils.database.data.StudentsDB;

import java.util.LinkedList;

public class TranscriptsMaster {
    public static Transcript getTranscriptWithStudentID(String targetStudentID) {
        LinkedList<Student> studentsList = StudentsDB.getList();
        String potentialStudentID;
        for (Student student : studentsList) {
            potentialStudentID = student.getStudentID();
            if (potentialStudentID.equals(targetStudentID)) {
                return student.getTranscript();
            }
        }
        return null;
    }

    public static Transcript getTranscriptWithStudentName(String targetStudentName) {
        LinkedList<Student> studentsList = StudentsDB.getList();
        String potentialStudentName;
        for (Student student : studentsList) {
            potentialStudentName = student.getFirstName() + " " + student.getLastName();
            if (potentialStudentName.equals(targetStudentName)) {
                return student.getTranscript();
            }
        }
        return null;
    }

    public static String[] getDepartmentsStudentsNames(String departmentName) {
        Department department = DepartmentsDB.getDepartmentWithName(departmentName);
        LinkedList<Student> studentsList = department.getListOfStudents();
        String[] studentsNames = new String[studentsList.size()];
        Student student;
        for (int i = 0; i < studentsList.size(); i++) {
            student = studentsList.get(i);
            studentsNames[i] = student.getFirstName() + " " + student.getLastName();
        }
        return studentsNames;
    }
}
