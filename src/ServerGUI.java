import MySQL.ServerDatabase;
import server.Server;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ServerGUI extends JFrame {

    private Server server;
    private ServerDatabase systemDatabase;

    private JLabel registeredUsersLabel;
    private JLabel loggedInUsersLabel;
    private JLabel connectedUsersLabel;
    private JTextArea loggedInUsersArea;
    private JTextArea lockedOutUsersArea;

    public ServerGUI(Server server, ServerDatabase systemDatabase) {
        this.server = server;
        this.systemDatabase = systemDatabase;

        createUI();
        updateStats();
    }

    private void createUI() {
        setTitle("Server Dashboard");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setLayout(new BorderLayout());
        JPanel panel = new JPanel(new GridLayout(5, 2));

        panel.add(new JLabel("Registered Users:"));
        registeredUsersLabel = new JLabel("0");
        panel.add(registeredUsersLabel);

        panel.add(new JLabel("Logged-in Users:"));
        loggedInUsersLabel = new JLabel("0");
        panel.add(loggedInUsersLabel);

        loggedInUsersArea = new JTextArea(5, 20);
        loggedInUsersArea.setEditable(false);
        panel.add(new JLabel("Logged-in Users:"));
        panel.add(new JScrollPane(loggedInUsersArea));

        lockedOutUsersArea = new JTextArea(5, 20);
        lockedOutUsersArea.setEditable(false);
        panel.add(new JLabel("Locked-out Users:"));
        panel.add(new JScrollPane(lockedOutUsersArea));

        panel.add(new JLabel("Connected Users:"));
        connectedUsersLabel = new JLabel("0");
        panel.add(connectedUsersLabel);

        JButton refreshButton = new JButton("Refresh");
        refreshButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateStats();
            }
        });
        panel.add(refreshButton);

        add(panel, BorderLayout.CENTER);
    }

    private void updateStats() {
        registeredUsersLabel.setText(String.valueOf(systemDatabase.getNumberOfRegisteredUsers()));
        loggedInUsersLabel.setText(String.valueOf(systemDatabase.getNumberOfLoggedInUsers()));
        String[] loggedInUsers = systemDatabase.getLoggedInUsers();
        loggedInUsersArea.setText(String.join("\n", loggedInUsers));

        String[] lockedOutUsers = systemDatabase.getLockedOutUsers();
        lockedOutUsersArea.setText(String.join("\n", lockedOutUsers));

        connectedUsersLabel.setText(String.valueOf(server.getNumberOfConnectedUsers()));
    }

    public static void main(String[] args) {
        // You would need to create and start your server and system database instances
        Server server = new Server();
        ServerDatabase systemDatabase = new ServerDatabase();

        // Start the server in a new thread
        //new Thread(() -> server.start(8000)).listen();

        // Initialize and show the server GUI
        EventQueue.invokeLater(() -> {
            ServerGUI gui = new ServerGUI(server, systemDatabase);
            gui.setVisible(true);
        });
    }
}
