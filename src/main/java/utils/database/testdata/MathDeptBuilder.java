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

public class MathDeptBuilder extends DepartmentBuilder {
    private EducationDeputy shahramKhazayi;
    private Professor siavashShahshahani;
    private Professor mohammadSafdari;
    private Student kamranKhoshkar;
    private Student alirezaVafa;
    private Student dibaDanaei;
    private Student hamidSimazadeh;
    private Student aliBeyrami;
    private Student hamedKhabazan;
    private Course complexAnalysis;
    private Course realAnalysis;

    public MathDeptBuilder(University university) {
        super(university);
        university.addDepartment(department);
    }

    @Override
    protected void buildDepartment() {
        department = new Department("Mathematics", dean);
    }

    @Override
    protected void buildProfessors() {
        shahramKhazayi = new EducationDeputy("Shahram", "Khazayi", "0152349021",
                "09192342209", "khazayi@sharif.edu", "451232", "43210",
                101, Professor.AcademicRank.ASSOCIATE);
        siavashShahshahani = new Professor("Siavash", "Shahshahani", "0150342183",
                "09129310242", "shahshah@sharif.edu", "123456", "12345",
                102, Professor.AcademicRank.FULL, Professor.AdministrativeRole.NORMAL);
        mohammadSafdari = new Professor("Mohammad", "Safdari", "0150235123",
                "09129510392", "safdari@sharif.edu", "419425", "20512",
                103, Professor.AcademicRank.ASSISTANT, Professor.AdministrativeRole.NORMAL);
    }

    @Override
    protected void buildCourses() {
        LinkedList<WeeklyDate> complexAnalysisDates = new LinkedList<>();
        complexAnalysisDates.add(new WeeklyDate(Weekday.SATURDAY, new TimeInDay(9, 30, 0),
                new TimeInDay(12, 30, 0), "Complex Analysis", "Siavash Shahshahani"));
        complexAnalysisDates.add(new WeeklyDate(Weekday.MONDAY, new TimeInDay(9, 30, 0),
                new TimeInDay(12, 30, 0), "Complex Analysis", "Siavash Shahshahani"));
        complexAnalysis = new Course("Complex Analysis",
                LocalDateTime.of(2022, 7, 22, 11, 0, 0),
                3, Course.CourseLevel.BACHELORS, "12341", siavashShahshahani,
                complexAnalysisDates);

        LinkedList<WeeklyDate> realAnalysisDates = new LinkedList<>();
        realAnalysisDates.add(new WeeklyDate(Weekday.SUNDAY, new TimeInDay(15, 0, 0),
                new TimeInDay(17, 0, 0), "Real Analysis", "Mohammad Safdari"));
        realAnalysisDates.add(new WeeklyDate(Weekday.TUESDAY, new TimeInDay(15, 0, 0),
                new TimeInDay(17, 0, 0), "Real Analysis", "Mohammad Safdari"));
        realAnalysis = new Course("Real Analysis",
                LocalDateTime.of(2022, 7, 22, 10, 30, 0), 2,
                Course.CourseLevel.GRADUATE, "32134", mohammadSafdari, realAnalysisDates);
    }

    @Override
    protected void buildStudents() {
        kamranKhoshkar = new Student("Kamran", "Khoshkar", "0150235290",
                "09320230290", "k.khoshkar@sharif.edu", "40201", "654321",
                mohammadSafdari, 2021, Student.AcademicStatus.CURRENTLY_STUDYING, Student.SoughtDegree.BACHELORS);
        alirezaVafa = new Student("Alireza", "Vafa", "0150235902", "09192340921",
                "a.vafa@sharif.edu", "54212", "123456", mohammadSafdari, 2020,
                Student.AcademicStatus.CURRENTLY_STUDYING, Student.SoughtDegree.BACHELORS);

        dibaDanaei = new Student("Diba", "Danaei", "0150235123", "09129525409",
                "d.danaei@sharif.edu", "54092", "456123", dean, 2019,
                Student.AcademicStatus.CURRENTLY_STUDYING, Student.SoughtDegree.GRADUATE);
        hamidSimazadeh = new Student("Hamid", "Simazadeh", "0150239024",
                "09129513209", "h.simazadeh@sharif.edu", "34902", "142152",
                siavashShahshahani, 2020, Student.AcademicStatus.CURRENTLY_STUDYING,
                Student.SoughtDegree.GRADUATE);

        aliBeyrami = new Student("Ali", "Beyrami", "0150235423", "09129233333",
                "a.beyrami@sharif.edu", "23523", "230512", siavashShahshahani, 2017,
                Student.AcademicStatus.CURRENTLY_STUDYING, Student.SoughtDegree.PHD);
        hamedKhabazan = new Student("Hamed", "Khabazan", "0152350293", "09129853209",
                "h.khabazan@sharif.edu", "51235", "205123", dean, 2012,
                Student.AcademicStatus.GRADUATED, Student.SoughtDegree.PHD);
    }

    @Override
    protected void setDean() {
        dean = new Dean("Hamidreza", "Fanaei", "0150235823", "09123412302",
                "fanaei@sharif.edu", "654321", "54321", 104,
                Professor.AcademicRank.FULL);
    }

    @Override
    protected void setEducationDeputy() {
        department.setEducationDeputyForFirstTime(shahramKhazayi);
    }

    @Override
    protected void mapStudentsToScores() {
//        realAnalysis.mapStudentToScore(kamranKhoshkar, 19.5);
//        realAnalysis.mapStudentToScore(alirezaVafa, 17.0);
//        realAnalysis.mapStudentToScore(dibaDanaei, 20.0);
//        realAnalysis.finalizeScore(kamranKhoshkar);
//        realAnalysis.finalizeScore(alirezaVafa);
//
//        complexAnalysis.mapStudentToScore(kamranKhoshkar, 12.0);
//        complexAnalysis.mapStudentToScore(dibaDanaei, 17.0);
//        complexAnalysis.mapStudentToScore(aliBeyrami, 19.0);
//        complexAnalysis.finalizeScore(dibaDanaei);
//        complexAnalysis.finalizeScore(aliBeyrami);
    }

    @Override
    protected void addProfessorsToDepartment() {
        department.addProfessor(siavashShahshahani);
        department.addProfessor(mohammadSafdari);
    }

    @Override
    protected void addStudentsToCourses() {
        realAnalysis.addStudent(kamranKhoshkar);
        complexAnalysis.addStudent(kamranKhoshkar);

        realAnalysis.addStudent(alirezaVafa);

        realAnalysis.addStudent(dibaDanaei);
        complexAnalysis.addStudent(dibaDanaei);

        realAnalysis.addStudent(hamidSimazadeh);

        complexAnalysis.addStudent(aliBeyrami);
    }

    @Override
    protected void addStudentsToDepartment() {
        department.addStudent(kamranKhoshkar);
        department.addStudent(alirezaVafa);
        department.addStudent(dibaDanaei);
        department.addStudent(hamidSimazadeh);
        department.addStudent(aliBeyrami);
        department.addStudent(hamedKhabazan);
    }

    @Override
    protected void addCoursesToDepartment() {
        department.addCourse(realAnalysis);
        department.addCourse(complexAnalysis);
    }
}
