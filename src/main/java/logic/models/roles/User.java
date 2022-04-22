package logic.models.roles;

import java.time.Duration;
import java.time.LocalDateTime;

abstract class User {
    protected String firstName;
    protected String lastName;
    protected String nationalID;
    protected String phoneNumber;
    protected String emailAddress;
    protected String password;
    protected LocalDateTime timeOfLastLogin;
    // TODO: add image

    protected User(String firstName, String lastName, String nationalID, String phoneNumber, String emailAddress,
                   String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.nationalID = nationalID;
        this.phoneNumber = phoneNumber;
        this.emailAddress = emailAddress;
        this.password = password;
    }

    public boolean hasBeenThreeHoursSinceLastLogin() {
        if (timeOfLastLogin != null) {
            Duration duration = Duration.between(timeOfLastLogin, LocalDateTime.now());
            Duration standardDifference = Duration.ofHours(3);
            return duration.compareTo(standardDifference) > 0; // is more than 3 hours
        } else {
            return false;
        }
    }

    public void setTimeOfLastLoginToNow() {
        timeOfLastLogin = LocalDateTime.now();
    }

    public LocalDateTime getTimeOfLastLogin() {
        return timeOfLastLogin;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getNationalID() {
        return nationalID;
    }

    protected void setNationalID(String nationalID) {
        this.nationalID = nationalID;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}