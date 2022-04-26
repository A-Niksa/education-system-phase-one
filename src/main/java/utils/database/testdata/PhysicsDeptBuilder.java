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

public class PhysicsDeptBuilder extends DepartmentBuilder {
    private EducationDeputy mahmoudBahmanabadi;
    private Professor rezaEjtehadi;
    private Student sabaSadeghi;
    private Course experimentalAstrophysics;
    private Course computationalPhysics;

    protected PhysicsDeptBuilder(University university) {
        super(university);
        university.addDepartment(department);
    }

    @Override
    protected void buildDepartment() {
        department = new Department("Physics", dean);
    }

    @Override
    protected void buildProfessors() {
        mahmoudBahmanabadi = new EducationDeputy("Mahmoud", "Bahmanabadi", "0150325123",
                "09123428509", "bahmanabadi@sharif.edu", "123456", "45123",
                201, Professor.AcademicRank.ASSOCIATE);
        rezaEjtehadi = new Professor("Reza", "Ejtehadi", "0152345090",
                "09124230923", "ejtehadi@sharif.edu", "987654", "34512",
                202, Professor.AcademicRank.ASSISTANT, Professor.AdministrativeRole.NORMAL);
    }

    @Override
    protected void buildCourses() {
        LinkedList<WeeklyDate> experimentalAstrophysicsDates = new LinkedList<>();
        experimentalAstrophysicsDates.add(new WeeklyDate(Weekday.SUNDAY, new TimeInDay(9, 30, 0),
                new TimeInDay(14, 30, 0), "Experimental Astrophysics",
                "Mahmoud Bahmanabadi"));
        experimentalAstrophysicsDates.add(new WeeklyDate(Weekday.TUESDAY, new TimeInDay(9, 30, 0),
                new TimeInDay(14, 30, 0), "Experimental Astrophysics",
                "Mahmoud Bahmanabadi"));
        experimentalAstrophysics = new Course("Experimental Astrophysics",
                LocalDateTime.of(2022, 7, 1, 8, 0, 0), 3,
                Course.CourseLevel.BACHELORS, "25960", mahmoudBahmanabadi, experimentalAstrophysicsDates);

        LinkedList<WeeklyDate> computationalPhysicsDates = new LinkedList<>();
        computationalPhysicsDates.add(new WeeklyDate(Weekday.SUNDAY, new TimeInDay(17, 30, 0),
                new TimeInDay(19, 30, 0), "Computational Physics",
                "Reza Ejtehadi"));
        computationalPhysicsDates.add(new WeeklyDate(Weekday.TUESDAY, new TimeInDay(17, 30, 0),
                new TimeInDay(19, 30, 0), "Computational Physics",
                "Reza Ejtehadi"));
        computationalPhysics = new Course("Computational Physics",
                LocalDateTime.of(2022, 8, 3, 15, 0, 0), 2,
                Course.CourseLevel.GRADUATE, "97641", rezaEjtehadi, computationalPhysicsDates);
    }

    @Override
    protected void buildStudents() {
        sabaSadeghi = new Student("Saba", "Sadeghi", "0150235390", "09129423249",
                "s.sadeghi@sharif.edu", "12894", "234212", rezaEjtehadi, 2019,
                Student.AcademicStatus.CURRENTLY_STUDYING, Student.SoughtDegree.BACHELORS);
    }

    @Override
    protected void setDean() {
        dean = new Dean("Sohrab", "Rahvar", "0159329921", "09122490390",
                "rahvar@sharif.edu", "245125", "14125", 203,
                Professor.AcademicRank.FULL);
    }

    @Override
    protected void setEducationDeputy() {
        department.setEducationDeputyForFirstTime(mahmoudBahmanabadi);
    }

    @Override
    protected void mapStudentsToScores() {
        experimentalAstrophysics.mapStudentToScore(sabaSadeghi, 17.0);
        computationalPhysics.mapStudentToScore(sabaSadeghi, 11.0);
        computationalPhysics.finalizeScore(sabaSadeghi);
    }

    @Override
    protected void addProfessorsToDepartment() {
        department.addProfessor(rezaEjtehadi);
    }

    @Override
    protected void addStudentsToCourses() {
        experimentalAstrophysics.addStudent(sabaSadeghi);
        computationalPhysics.addStudent(sabaSadeghi);
    }

    @Override
    protected void addStudentsToDepartment() {
        department.addStudent(sabaSadeghi);
    }

    @Override
    protected void addCoursesToDepartment() {
        department.addCourse(experimentalAstrophysics);
        department.addCourse(computationalPhysics);
    }
}
