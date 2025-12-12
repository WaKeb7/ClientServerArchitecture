import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.Serial;
import java.util.Arrays;
import MySQL.ClientDatabase;


public class clientGUI extends JFrame {

    private ClientDatabase clientDatabase;

    @Serial
    private static final long serialVersionUID = -6167569334213042017L;

    // Set the size of the JFrame. JPanels will adapt to this size
    private final int WIDTH = 520;
    private final int HEIGHT = 600;

    private final LoginPanel loginPanel;

    public clientGUI() {
        // Construct the base JFrame first
        super("Java Swing Application");

        // Set the application title
        this.setTitle("Login Form");

        // Initial size of the frame: width, height
        this.setSize(WIDTH, HEIGHT);

        // Center the frame on the screen
        this.setLocationRelativeTo(null);

        // Shut down the entire application when the frame is closed
        // If you don't include this, the application will continue to
        // run in the background, and you'll have to kill it by pressing
        // the red square in Eclipse
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Set the layout manager and add items
        // 5, 5 is the border around the edges of the areas
        setLayout(new BorderLayout(5, 5));

        this.clientDatabase = new ClientDatabase();
        // Construct a JPanel for graphics
        loginPanel = new LoginPanel(clientDatabase);
        this.add(loginPanel, BorderLayout.CENTER);

        // Can make it so the user cannot resize the frame
        this.setResizable(false);
        // Show the frame on the screen
        this.setVisible(true);
    }

    public class LoginPanel extends JPanel {
        private ClientDatabase clientDatabase;

        @Serial
        private static final long serialVersionUID = -8776438726683578407L;
        private JLabel userLabel;
        private JTextField userField;
        private JLabel passwordLabel;
        private JPasswordField passwordField;
        private JButton loginButton;
        private JButton registerButton;
        private JButton forgotPassButton;
        private JButton logoutButton;

        public LoginPanel(ClientDatabase clientDatabase) {
            this.clientDatabase = clientDatabase;
            prepareButtonHandlers();
            setLayout(new FlowLayout());

            // Initialize components
            userLabel = new JLabel("Username:");
            userField = new JTextField(35); // Set preferred width
            passwordLabel = new JLabel("Password:");
            passwordField = new JPasswordField(35); // Set preferred width

            // Add components to the panel
            this.add(userLabel);
            this.add(userField);
            this.add(passwordLabel);
            this.add(passwordField);
            this.add(loginButton);
            this.add(registerButton);
            this.add(forgotPassButton);


        }

        private void prepareButtonHandlers() {
            loginButton = new JButton("Login");
            loginButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    performLogin();
                }
            });
            registerButton = new JButton("Register");
            registerButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    openRegistrationWindow();
                }
            });

            forgotPassButton = new JButton("Forgot Password?");
            forgotPassButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    forgotPassword();
                }
            });
            logoutButton = new JButton("Logout");
            logoutButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    performLogout();
                }
            });
        }

        private void performLogin() {
            String username = userField.getText();
            char[] password = passwordField.getPassword();

            // Implement logic to validate username and password
            //Description: Allows registered users to authenticate themselves and access the application.
            //Pre-conditions: User has an account.
            //Post-conditions: User has access to the application.
            //User story 1: User enters correct username and password.
            //1. System prompts for username and password.
            //2. User enters username n4me and password gmc47u0ph.
            //3. System checks if username is correct/stored in database -> it is.
            //4. System checks if password is correct/stored in database -> it is.
            //5. User is authenticated and is logged in.
            //User story 2: User enters correct username and incorrect password but enters correct
            //password before attempt limit is reached.
            //1. System prompts for username and password.
            //2. User enters username n4me and password pass.
            //3. System checks if username is correct/stored in database -> it is.
            //4. System checks if password is correct/stored in database -> it is not.
            //5. User is prompted to re-enter password (3 attempts).
            //6. User enters correct password (gmc47u0ph) before attempt limit is reached.
            //7. User is authenticated and is logged in.
            //User story 3: User enters correct username and incorrect password, failing to input correct
            //password before attempt limit is reached.
            //1. System prompts for username and password.
            //2. User enters username n4me and password pass.
            //3. System checks if username is correct/stored in database -> it is.
            //4. System checks if password is correct/stored in database -> it is not.
            //5. User is prompted to re-enter password (3 attempts).
            //6. User fails to enter the correct password 3 times.
            //7. User is not authenticated and is locked out.
            //8. User is prompted to request a new password to be emailed to the email address on
            //record
            // Convert the password to a String (consider security implications)
            String passwordString = new String(password);

            try {
                if (username.isEmpty() || passwordString.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Username or password cannot be empty.", "Login Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                boolean isAuthentic = authenticateUser(username, passwordString);

                // Clear the password array after use for security
                Arrays.fill(password, ' ');

                if (isAuthentic) {
                    showWelcomeMessage(username);
                    passwordField.setText("");
                } else {
                    // User authentication failed
                    JOptionPane.showMessageDialog(this, "Invalid username or password.", "Login Error", JOptionPane.ERROR_MESSAGE);
                    passwordField.setText("");
                    // Handle attempt counting and lockout logic here if needed
                }
            } catch (Exception e) {
                // Handle exceptions for login process
                JOptionPane.showMessageDialog(this, "An error occurred while trying to log in: " + e.getMessage(), "Login Error", JOptionPane.ERROR_MESSAGE);
            } finally {
                // Ensure password is cleared from memory
                Arrays.fill(password, ' ');
                passwordField.setText("");
            }

        }
        private void showWelcomeMessage(String username) {
            // Replace the current content with a welcome message
            removeAll();
            setLayout(new FlowLayout());
            JLabel welcomeLabel = new JLabel("Welcome, " + username + "!");
            add(welcomeLabel);
            add(logoutButton);
            revalidate();
            repaint();
        }

        private void openRegistrationWindow() {
            JFrame registrationFrame = new JFrame("Registration Form");
            registrationFrame.setBounds(200, 200, 400, 200);
            registrationFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            registrationFrame.getContentPane().setLayout(new GridLayout(4, 2));

            JLabel emailLabel = new JLabel("Email:");
            registrationFrame.getContentPane().add(emailLabel);

            JTextField emailField = new JTextField();
            registrationFrame.getContentPane().add(emailField);

            JLabel usernameLabel = new JLabel("Username:");
            registrationFrame.getContentPane().add(usernameLabel);

            JTextField newUsernameField = new JTextField();
            registrationFrame.getContentPane().add(newUsernameField);

            JLabel newPasswordLabel = new JLabel("Password:");
            registrationFrame.getContentPane().add(newPasswordLabel);

            JPasswordField newPasswordField = new JPasswordField();
            registrationFrame.getContentPane().add(newPasswordField);

            JButton registerButton = new JButton("Register");
            registerButton.addActionListener(e -> {
                // Implement logic to handle registration
                String email = emailField.getText();
                String newUsername = newUsernameField.getText();
                char[] newPassword = newPasswordField.getPassword();

                // Implement logic to handle the registration (e.g., store the user details)
                String passwordString = new String(newPassword);

                registerUser(newUsername, passwordString, email);
                // Clear the password field after processing
                Arrays.fill(newPassword, ' ');
                newPasswordField.setText("");

                // Close the registration window
                registrationFrame.dispose();
            });
            registrationFrame.getContentPane().add(registerButton);

            registrationFrame.setVisible(true);
        }
        private boolean registerUser(String username, String password, String emailaddress) {
            return clientDatabase.addUser(username, password, emailaddress);
        }
        private boolean authenticateUser(String username, String password) {
            // In a real application, ensure that passwords are hashed and use secure comparison
            return clientDatabase.authenticateUser(username, password); // method in ClientDatabase
        }

        private void forgotPassword() {
            // Implement logic to open the forgot password window
            // Description: Enables users to retrieve their passwords and regain access to their accounts.
            //Pre-conditions: User requests to retrieve their password
            //Post-conditions: Password is sent to email address on record.
            //User story 1: User fails to enter correct password in 3 attempts or requests to recover
            //password before 3 attempts are used, providing a registered username.
            //1. System prompts user for a username.
            //2. User enters username n4me.
            //3. System checks if n4me is in the database with an associated email and password ->
            //it is.
            //4. System emails the password to the email address on record.
            //User story 2: User fails to enter correct password in 3 attempts or requests to recover
            //password before 3 attempts are used, providing an unregistered username.
            //1. System prompts user for a username.
            //2. User enters username name.
            //3. System checks if name is in the database with an associated email and password ->
            //it isn’t.
            //4. System provides an invalid username error message to the user.
            //Next use case: User Login
        }
        private void performLogout() {
            // Show the login screen
            removeAll();
            setLayout(new FlowLayout());
            this.add(userLabel);
            this.add(userField);
            this.add(passwordLabel);
            this.add(passwordField);
            this.add(loginButton);
            this.add(registerButton);
            this.add(forgotPassButton);
            revalidate();
            repaint();
        }
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                new clientGUI();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}
