package logic.menus.login;

import logic.models.roles.User;

public class LoggedInAccount {
    public boolean credentialsIsValid;
    public AccountType accountType;
    public User user;

    public LoggedInAccount(boolean credentialsIsValid, AccountType accountType, User user) {
        this.credentialsIsValid = credentialsIsValid;
        this.accountType = accountType;
        this.user = user;
    }
}
