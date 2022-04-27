package logic.menus.standing;

import logic.models.abstractions.Course;
import logic.models.abstractions.StudentStatus;

import java.util.LinkedList;

public class CourseStatsMaster {
    public static int getNumberOfPassingStudents(Course course) {
        LinkedList<StudentStatus> studentStatusesList = course.getStudentsStatusesList();
        Double score;
        int numberOfPassingStudents = 0;
        for (StudentStatus studentStatus : studentStatusesList) {
            score = studentStatus.getScore();

            if (score >= 10.0) {
                numberOfPassingStudents++;
            }
        }
        return numberOfPassingStudents;
    }

    private static int getTotalNumberOfStudents(Course course) {
        LinkedList<StudentStatus> studentStatusesList = course.getStudentsStatusesList();
        return studentStatusesList.size();
    }

    public static int getNumberOfFailingStudents(Course course) {
        int totalNumberOfStudents = getTotalNumberOfStudents(course);
        int numberOfPassingStudents = getNumberOfPassingStudents(course);
        int numberOfFailingStudents = totalNumberOfStudents - numberOfPassingStudents;
        return numberOfFailingStudents;
    }

    public static String getCoursesAverageScore(Course course) { // output is String to handle N/A cases
        int totalNumberOfStudents = getTotalNumberOfStudents(course);
        if (totalNumberOfStudents == 0) {
            return "N/A";
        }

        LinkedList<StudentStatus> studentStatusesList = course.getStudentsStatusesList();
        Double totalScore = 0.0;
        Double additionValue;
        for (StudentStatus studentStatus : studentStatusesList) {
            if (studentStatus.getScore() == null) {
                additionValue = 0.0;
            } else {
                additionValue = studentStatus.getScore();
            }
            totalScore += additionValue;
        }

        Double averageScore = totalScore / totalNumberOfStudents;
        return String.format("%.2f", averageScore);
    }

    public static String getCoursesAverageScoreWithoutFailingStudents(Course course) {
        int numberOfPassingStudents = getNumberOfPassingStudents(course);
        if (numberOfPassingStudents == 0) {
            return "N/A";
        }

        LinkedList<StudentStatus> studentStatusesList = course.getStudentsStatusesList();
        Double totalScore = 0.0;
        Double additionValue;
        for (StudentStatus studentStatus : studentStatusesList) {
            if (studentStatus.getScore() == null) {
                additionValue = 0.0;
            } else { // didn't use else if for clarity of the logic
                if (studentStatus.getScore() >= 10) {
                    additionValue = studentStatus.getScore();
                } else {
                    additionValue = 0.0;
                }
            }
            totalScore += additionValue;
        }

        Double averageScoreWithoutFailingStudents = totalScore / numberOfPassingStudents;
        return String.format("%.2f", averageScoreWithoutFailingStudents);
    }

    public static boolean allScoresAreFinalized(Course course) {
        LinkedList<StudentStatus> studentStatusesList = course.getStudentsStatusesList();
        for (StudentStatus studentStatus : studentStatusesList) {
            if (!studentStatus.scoreIsFinalized()) {
                return false;
            }
        }
        return true;
    }
}
