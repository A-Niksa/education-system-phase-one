package utils.database.testdata;

import logic.models.abstractions.Course;
import logic.models.abstractions.Department;
import logic.models.abstractions.University;
import logic.models.roles.Dean;
import logic.models.roles.EducationDeputy;
import logic.models.roles.Professor;
import utils.timing.TimeInDay;
import utils.timing.Weekday;
import utils.timing.WeeklyDate;

import java.time.LocalDateTime;
import java.util.LinkedList;

public class AerospaceDeptBuilder extends DepartmentBuilder {
    private EducationDeputy naserTalebi;
    private Professor sobhanKhoshkam;
    private Course planeDesign;
    private Course aerodynamics;

    protected AerospaceDeptBuilder(University university) {
        super(university);
        university.addDepartment(department);
    }

    @Override
    protected void buildDepartment() {
        department = new Department("Aerospace Engineering", dean);
    }

    @Override
    protected void buildProfessors() {
        naserTalebi = new EducationDeputy("Naser", "Talebi", "0156239075", "09124235423",
                "talebi@sharif.edu", "341254", "95252", 301,
                Professor.AcademicRank.ASSOCIATE);
        sobhanKhoshkam = new Professor("Sobhan", "Khoshkam", "0150235124",
                "09125235235", "khoshkam@sharif.edu", "235841", "91256",
                302, Professor.AcademicRank.ASSISTANT, Professor.AdministrativeRole.NORMAL);
    }

    @Override
    protected void buildCourses() {
        LinkedList<WeeklyDate> planeDesignDates = new LinkedList<>();
        planeDesignDates.add(new WeeklyDate(Weekday.SUNDAY, new TimeInDay(8, 0, 0),
                new TimeInDay(9, 30, 0), "Plane Design",
                "Naser Talebi"));
        planeDesignDates.add(new WeeklyDate(Weekday.WEDNESDAY, new TimeInDay(8, 0, 0),
                new TimeInDay(9, 30, 0), "Plane Design",
                "Naser Talebi"));
        planeDesign = new Course("Plane Design",
                LocalDateTime.of(2022, 8, 20, 8, 30, 0), 4,
                Course.CourseLevel.GRADUATE, "28163", naserTalebi, planeDesignDates);

        LinkedList<WeeklyDate> aerodynamicsDates = new LinkedList<>();
        aerodynamicsDates.add(new WeeklyDate(Weekday.SATURDAY, new TimeInDay(8, 0, 0),
                new TimeInDay(10, 30, 0), "Aerodynamics",
                "Sobhan Khoshkam"));
        aerodynamicsDates.add(new WeeklyDate(Weekday.TUESDAY, new TimeInDay(8, 0, 0),
                new TimeInDay(10, 30, 0), "Aerodynamics",
                "Sobham Khoshkam"));
        aerodynamics = new Course("Aerodynamics",
                LocalDateTime.of(2022, 8, 30, 8, 30, 0), 3,
                Course.CourseLevel.PHD, "51262", sobhanKhoshkam, aerodynamicsDates);
    }

    @Override
    protected void buildStudents() {
    }

    @Override
    protected void setDean() {
        dean = new Dean("Hamid", "Takbiri", "0150235920", "09122130909",
                "takbiri@sharif.edu", "235313", "51296", 303,
                Professor.AcademicRank.FULL);
    }

    @Override
    protected void setEducationDeputy() {
        department.setEducationDeputyForFirstTime(naserTalebi);
    }

    @Override
    protected void mapStudentsToScores() {
    }

    @Override
    protected void addProfessorsToDepartment() {
        department.addProfessor(sobhanKhoshkam);
    }

    @Override
    protected void addStudentsToCourses() {
    }

    @Override
    protected void addStudentsToDepartment() {
    }

    @Override
    protected void addCoursesToDepartment() {
        department.addCourse(planeDesign);
        department.addCourse(aerodynamics);
    }
}
