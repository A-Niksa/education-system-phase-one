import gui.MainFrame;
import logic.models.abstractions.Course;
import logic.models.abstractions.Department;
import logic.models.roles.Dean;
import logic.models.roles.EducationDeputy;
import logic.models.roles.Professor;
import logic.models.roles.Student;
import utils.database.tools.DatabaseWriter;
import utils.logging.LogIdentifier;
import utils.logging.MasterLogger;
import utils.timing.TimeInDay;
import utils.timing.Weekday;
import utils.timing.WeeklyDate;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.LinkedList;

public class Main {
    public static void main(String[] args) {
        MasterLogger.log("start of program", LogIdentifier.INFO, "psvm", "Main");
//        createTestData();
//        DatabaseWriter.updateDatabase();
        new MainFrame();
    }

    private static void createTestData() {
        Dean mathDean = new Dean("Hamidreza", "Fanaei", "0150347982", "09121329090",
                                "fana@sharif.edu", "12345678", "1329", 707,
                Professor.AcademicRank.FULL);
        Department mathDepartment = new Department("Mathematics", mathDean);
        EducationDeputy educationDeputy = new EducationDeputy("Ali", "Safdari", "004482133",
                "09129022310", "safdari@sharif.edu", "12904212", "3214", 302,
                Professor.AcademicRank.ASSOCIATE);
        mathDepartment.setEducationDeputy(educationDeputy);
        Professor shahshahani = new Professor("Siavash", "Shahshahani", "9999999999",
                "09120000000", "shahshah@sharif.edu", "12349876", "0101", 103,
                Professor.AcademicRank.FULL, Professor.AdministrativeRole.NORMAL);
        mathDepartment.addProfessor(shahshahani);
        LinkedList<WeeklyDate> complexAnalysisDates = new LinkedList<>();
        complexAnalysisDates.add(new WeeklyDate(Weekday.SATURDAY, new TimeInDay(9, 30, 0),
                new TimeInDay(12, 30, 0), "Complex Analysis", "Siavash Shahshahani"));
        complexAnalysisDates.add(new WeeklyDate(Weekday.MONDAY, new TimeInDay(9, 30, 0),
                new TimeInDay(12, 30, 0), "Complex Analysis", "Siavash Shahshahani"));
        Course complexAnalysis = new Course("Complex Analysis",
                LocalDateTime.of(2022, 7, 22, 9, 0, 0),
                3, Course.CourseLevel.BACHELORS, "12341", shahshahani,
                complexAnalysisDates);
        mathDepartment.addCourse(complexAnalysis);
        LinkedList<WeeklyDate> realAnalysisDates = new LinkedList<>();
        realAnalysisDates.add(new WeeklyDate(Weekday.SUNDAY, new TimeInDay(15, 0, 0),
                new TimeInDay(17, 0, 0), "Real Analysis", "Ali Safdari"));
        realAnalysisDates.add(new WeeklyDate(Weekday.TUESDAY, new TimeInDay(15, 0, 0),
                new TimeInDay(17, 0, 0), "Real Analysis", "Ali Safdari"));
        Course realAnalysis = new Course("Real Analysis",
                LocalDateTime.of(2022, 7, 23, 10, 30, 0), 2,
                Course.CourseLevel.GRADUATE, "32134", educationDeputy, realAnalysisDates);
        mathDepartment.addCourse(realAnalysis);
        LinkedList<WeeklyDate> partialDiffEquationsDates = new LinkedList<>();
        partialDiffEquationsDates.add(new WeeklyDate(Weekday.SATURDAY, new TimeInDay(10, 30, 0),
                new TimeInDay(12, 0, 0), "PDE", "Hamidreza Fanaei"));
        partialDiffEquationsDates.add(new WeeklyDate(Weekday.TUESDAY, new TimeInDay(11, 30, 0),
                new TimeInDay(13, 0, 0), "PDE", "Hamidreza Fanaei"));
        Course partialDiffEquations = new Course("PDE",
                LocalDateTime.of(2022, 8, 1, 8, 0, 0),
                                4, Course.CourseLevel.PHD, "42310", mathDean,
                                partialDiffEquationsDates);
        mathDepartment.addCourse(partialDiffEquations);
        Student student = new Student("Ali", "Alizadeh", "0150332134", "09199921032",
                "ali.alizadeh@sharif.edu", "1", "1", 4.0, educationDeputy,
                2021, Student.AcademicStatus.CURRENTLY_STUDYING, Student.SoughtDegree.BACHELORS);
        mathDepartment.addStudent(student);
        complexAnalysis.addStudent(student);
        complexAnalysis.mapStudentToScore(student, 20.0);
        complexAnalysis.finalizeScore(student);
        realAnalysis.addStudent(student);
        realAnalysis.mapStudentToScore(student, 18.5);
        realAnalysis.finalizeScore(student);
        partialDiffEquations.addStudent(student);
        partialDiffEquations.mapStudentToScore(student, 19.75);
    }
}