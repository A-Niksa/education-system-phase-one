package logic.menus.login;

public class LoggedInAccount {
    public boolean credentialsIsValid;
    public AccountType accountType;

    public LoggedInAccount(boolean credentialsIsValid, AccountType accountType) {
        this.credentialsIsValid = credentialsIsValid;
        this.accountType = accountType;
    }
}
