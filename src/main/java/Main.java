import gui.MainFrame;
import logic.models.abstractions.Course;
import logic.models.abstractions.Department;
import logic.models.roles.Dean;
import logic.models.roles.EducationDeputy;
import logic.models.roles.Professor;
import logic.models.roles.Student;
import utils.database.tools.DatabaseWriter;

import java.time.LocalDate;

public class Main {
    public static void main(String[] args) {
        createTestData();
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
        Course course = new Course("Complex Analysis", LocalDate.of(2022, 07, 22),
                shahshahani);
        Student student = new Student("Ali", "Alizadeh", "0150332134", "09199921032",
                "ali.alizadeh@sharif.edu", "3231PLWS", 4.0, educationDeputy, 2021,
                Student.AcademicStatus.CURRENTLY_STUDYING);
        mathDepartment.addStudent(student);
        course.addStudent(student);
    }
}