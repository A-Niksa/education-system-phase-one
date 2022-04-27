package logic.models.abstractions;

import java.util.LinkedList;

public class Transcript {
    private LinkedList<String> passedCoursesIDs; // includes course IDs

    public Transcript() {
        passedCoursesIDs = new LinkedList<>();
    }

    public void addCourse(Course course) {
        if (courseHasBeenPassed(course)) {
            removeCourse(course);
            passedCoursesIDs.add(course.getCourseID());
        } else {
            passedCoursesIDs.add(course.getCourseID());
        }
    }

    public void removeCourse(Course targetCourse) {
        String targetCourseID = targetCourse.getCourseID();
        String courseID;
        for (int i = 0; i < passedCoursesIDs.size(); i++) {
            courseID = passedCoursesIDs.get(i);
            if (courseID.equals(targetCourseID)) {
                passedCoursesIDs.remove(courseID);
                return;
            }
        }
    }

    private boolean courseHasBeenPassed(Course targetCourse) {
        String targetCourseID = targetCourse.getCourseID();
        for (String courseID : passedCoursesIDs) {
            if (courseID.equals(targetCourseID)) {
                return true;
            }
        }
        return false;
    }

    public LinkedList<String> getPassedCoursesIDs() {
        return passedCoursesIDs;
    }
}
