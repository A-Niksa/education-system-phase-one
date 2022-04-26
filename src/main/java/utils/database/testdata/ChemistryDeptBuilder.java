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

public class ChemistryDeptBuilder extends DepartmentBuilder {
    private EducationDeputy saeedEzabadi;
    private Course quantumChemistry;
    private Course chemicalReactors;

    protected ChemistryDeptBuilder(University university) {
        super(university);
        university.addDepartment(department);
    }

    @Override
    protected void buildDepartment() {
        department = new Department("Chemistry", dean);
    }

    @Override
    protected void buildProfessors() {
        saeedEzabadi = new EducationDeputy("Saeed", "Ezabadi", "0152359090", "09123428923",
                "ezabadi@sharif.edu", "235905", "77523", 401,
                Professor.AcademicRank.FULL);
    }

    @Override
    protected void buildCourses() {
        LinkedList<WeeklyDate> quantumChemistryDates = new LinkedList<>();
        quantumChemistryDates.add(new WeeklyDate(Weekday.SUNDAY, new TimeInDay(9, 30, 0),
                new TimeInDay(11, 30, 0), "Quantum Chemistry",
                "Arash Taba"));
        quantumChemistryDates.add(new WeeklyDate(Weekday.WEDNESDAY, new TimeInDay(9, 30, 0),
                new TimeInDay(11, 30, 0), "Quantum Chemistry",
                "Arash Taba"));
        quantumChemistry = new Course("Quantum Chemistry",
                LocalDateTime.of(2022, 9, 3, 10, 30, 0), 3,
                Course.CourseLevel.PHD, "25195", dean, quantumChemistryDates);

        LinkedList<WeeklyDate> chemicalReactorsDates = new LinkedList<>();
        chemicalReactorsDates.add(new WeeklyDate(Weekday.SATURDAY, new TimeInDay(9, 30, 0),
                new TimeInDay(11, 30, 0), "Chemical Reactors",
                "Saeed Ezabadi"));
        chemicalReactorsDates.add(new WeeklyDate(Weekday.MONDAY, new TimeInDay(9, 30, 0),
                new TimeInDay(11, 30, 0), "Chemical Reactors",
                "Saeed Ezabadi"));
        chemicalReactors = new Course("Chemical Reactors",
                LocalDateTime.of(2022, 9, 2, 9, 30, 0), 2,
                Course.CourseLevel.BACHELORS, "24974", saeedEzabadi, chemicalReactorsDates);
    }

    @Override
    protected void buildStudents() {}

    @Override
    protected void setDean() {
        dean = new Dean("Arash", "Taba", "0245129090", "09332380923",
                "taba@sharif.edu", "204552", "11245", 402,
                Professor.AcademicRank.ASSOCIATE);
    }

    @Override
    protected void setEducationDeputy() {
        department.setEducationDeputyForFirstTime(saeedEzabadi);
    }

    @Override
    protected void mapStudentsToScores() {}

    @Override
    protected void addProfessorsToDepartment() {}

    @Override
    protected void addStudentsToCourses() {}

    @Override
    protected void addStudentsToDepartment() {}

    @Override
    protected void addCoursesToDepartment() {
        department.addCourse(quantumChemistry);
        department.addCourse(chemicalReactors);
    }
}
