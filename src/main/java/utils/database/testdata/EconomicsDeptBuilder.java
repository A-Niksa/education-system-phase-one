package utils.database.testdata;

import logic.models.abstractions.Course;
import logic.models.abstractions.Department;
import logic.models.abstractions.University;
import logic.models.roles.Dean;
import logic.models.roles.EducationDeputy;
import logic.models.roles.Professor;
import logic.models.roles.Student;
import utils.timing.TimeInDay;
import utils.timing.Weekday;
import utils.timing.WeeklyDate;

import java.time.LocalDateTime;
import java.util.LinkedList;

public class EconomicsDeptBuilder extends DepartmentBuilder {
    private EducationDeputy masoudHaghi;
    private Student sinaHabibi;
    private Course stochasticEquations;
    private Course gameTheory;

    protected EconomicsDeptBuilder(University university) {
        super(university);
        university.addDepartment(department);
    }

    @Override
    protected void buildDepartment() {
        department = new Department("Economics", dean);
    }

    @Override
    protected void buildProfessors() {
        masoudHaghi = new EducationDeputy("Masoud", "Haghi", "0242390221", "09125420984",
                "haghi@sharif.edu", "352341", "64592", 602,
                Professor.AcademicRank.ASSISTANT);
    }

    @Override
    protected void buildCourses() {
        LinkedList<WeeklyDate> stochasticEquationsDates = new LinkedList<>();
        stochasticEquationsDates.add(new WeeklyDate(Weekday.SATURDAY, new TimeInDay(9, 30, 0),
                new TimeInDay(11, 30, 0), "Stochastic Equations",
                "Masoud Haghi"));
        stochasticEquationsDates.add(new WeeklyDate(Weekday.WEDNESDAY, new TimeInDay(9, 30, 0),
                new TimeInDay(11, 30, 0), "Stochastic Equations",
                "Masoud Haghi"));
        stochasticEquations = new Course("Stochastic Equations",
                LocalDateTime.of(2022, 8, 23, 15, 0, 0), 3,
                Course.CourseLevel.PHD, "23400", masoudHaghi, stochasticEquationsDates);

        LinkedList<WeeklyDate> gameTheoryDates = new LinkedList<>();
        gameTheoryDates.add(new WeeklyDate(Weekday.SATURDAY, new TimeInDay(13, 30, 0),
                new TimeInDay(15, 30, 0), "Game Theory",
                "Soheil Shahbazi"));
        gameTheoryDates.add(new WeeklyDate(Weekday.MONDAY, new TimeInDay(13, 30, 0),
                new TimeInDay(15, 30, 0), "Game Theory",
                "Soheil Shahbazi"));
        gameTheory = new Course("Game Theory",
                LocalDateTime.of(2022, 8, 20, 12, 30, 0), 3,
                Course.CourseLevel.BACHELORS, "24952", dean, gameTheoryDates);
    }

    @Override
    protected void buildStudents() {
        sinaHabibi = new Student("Sina", "Habibi", "0150235232", "09239540234",
                "s.habibi@sharif.edu", "23590", "242030", dean, 2020,
                Student.AcademicStatus.CURRENTLY_STUDYING, Student.SoughtDegree.GRADUATE);
    }

    @Override
    protected void setDean() {
        dean = new Dean("Soheil", "Shahbazi", "0150239990", "09124212209",
                "shahbazi@sharif.edu", "294494", "79234", 601,
                Professor.AcademicRank.FULL);
    }

    @Override
    protected void setEducationDeputy() {
        department.setEducationDeputyForFirstTime(masoudHaghi);
    }

    @Override
    protected void mapStudentsToScores() {
//        stochasticEquations.mapStudentToScore(sinaHabibi, 18.75);
//        stochasticEquations.finalizeScore(sinaHabibi);
    }

    @Override
    protected void addProfessorsToDepartment() {}

    @Override
    protected void addStudentsToCourses() {
        stochasticEquations.addStudent(sinaHabibi);
    }

    @Override
    protected void addStudentsToDepartment() {
        department.addStudent(sinaHabibi);
    }

    @Override
    protected void addCoursesToDepartment() {
        department.addCourse(stochasticEquations);
        department.addCourse(gameTheory);
    }
}
