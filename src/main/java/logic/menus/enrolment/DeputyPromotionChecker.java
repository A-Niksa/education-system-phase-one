package logic.menus.enrolment;

import logic.models.abstractions.Department;
import logic.models.roles.Professor;
import utils.database.data.DepartmentsDB;

public class DeputyPromotionChecker {
    public static boolean departmentHasDeputy(Professor oneDepartmentProfessor) {
        Department department = DepartmentsDB.getProfessorsDepartment(oneDepartmentProfessor);
        return department.getEducationDeputy() != null;
    }
}
