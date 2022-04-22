package logic.models.abstractions;

import utils.database.data.CoursesDB;

import java.util.LinkedList;

public class Transcript {
    private LinkedList<String> passedCoursesIDs; // includes course IDs

    public Transcript() {
        passedCoursesIDs = new LinkedList<String>();
    }

    public void addCourse(Course course) {
        passedCoursesIDs.add(course.getCourseID());
    }

    public LinkedList<String> getPassedCoursesIDs() {
        return passedCoursesIDs;
    }
}
