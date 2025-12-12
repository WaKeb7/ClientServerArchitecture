import MySQL.ClientDatabase;

public class userManager {

    private ClientDatabase clientDatabase;

    public userManager() {
        this.clientDatabase = new ClientDatabase();
    }

    public boolean registerUser(String username, String password, String email) {
        // Here, you would typically hash the password before storing
        // For simplicity, this example does not include password hashing
        return clientDatabase.addUser(username, password, email);
    }

    public boolean loginUser(String username, String password) {
        // Again, in a real scenario, you should compare the hashed password
        return clientDatabase.authenticateUser(username, password);
    }

    public boolean lockUserAccount(String username) {
        return clientDatabase.lockUser(username);
    }

    public String recoverPassword(String username) {
        // Typically, password recovery would involve more than just retrieving the email.
        // This might include generating a password reset token, sending an email, etc.
        // For simplicity, this method just returns the email associated with the username.
        return clientDatabase.getEmail(username);
    }
    public boolean logoutUser(String username) {
        // In a real application, this method would involve session management.
        // For our purposes, let's just assume it sets an 'is_logged_in' flag to false.
        return clientDatabase.updateLoginStatus(username, false);
    }

    // Additional user management methods can be added here...

}
