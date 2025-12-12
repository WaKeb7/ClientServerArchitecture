package Test;

import MySQL.ClientDatabase;

public class ClientDatabaseTest {

    public static void main(String[] args) {
        // Create instance of ClientDatabase to test
        ClientDatabase clientDB = new ClientDatabase();

        // Test: Add a user
        boolean addUserResult = clientDB.addUser("testuser3", "password123", "testuser@example.com");
        System.out.println("Add User Result: " + (addUserResult ? "Passed" : "Failed"));

        // Test: Authenticate user
        boolean authResult = clientDB.authenticateUser("testuser3", "password123");
        System.out.println("Authentication Result: " + (authResult ? "Passed" : "Failed"));

        // Test: Lock user
//        boolean lockResult = clientDB.lockUser("testuser2");
//        System.out.println("Lock User Result: " + (lockResult ? "Passed" : "Failed"));

        // Test: Get email of user
        String email = clientDB.getEmail("testuser3");
        System.out.println("Get Email Result: " + (email != null ? "Passed" : "Failed"));
        if (email != null) {
            System.out.println("Email for 'testuser3': " + email);
        }

        // Clean up (Delete the test user)
        // Note: You would need to implement a deleteUser method in ClientDatabase for this to work
        // boolean deleteResult = clientDB.deleteUser("testuser");
        // System.out.println("Delete User Result: " + (deleteResult ? "Passed" : "Failed"));
    }
}
