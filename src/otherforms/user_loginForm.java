package otherforms;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class user_loginForm implements ActionListener {
     JTextField usernameField;
     JPasswordField passwordField;

     static final String JDBC_URL = "jdbc:mysql://localhost:3306/gisubizo_louange_222010931";
     String USERNAME = "louange";
     String PASSWORD = "222010931";

    public void createAndShowGUI() {
        JFrame frame = new JFrame("login form");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel(null);

        JLabel usernameLabel = new JLabel("first_name:");
        JLabel passwordLabel = new JLabel("Password:");

        usernameLabel.setBounds(50, 50, 80, 30);
        passwordLabel.setBounds(50, 100, 80, 30);

        usernameField = new JTextField();
        passwordField = new JPasswordField();

        usernameField.setBounds(140, 50, 200, 30);
        passwordField.setBounds(140, 100, 200, 30);

        JButton loginButton = new JButton("Login");
        JButton resetButton = new JButton("Reset");

        loginButton.setBounds(100, 180, 80, 30);
        resetButton.setBounds(200, 180, 80, 30);

        loginButton.addActionListener(this);
        resetButton.addActionListener(this);

        panel.add(usernameLabel);
        panel.add(usernameField);
        panel.add(passwordLabel);
        panel.add(passwordField);
        panel.add(loginButton);
        panel.add(resetButton);

        frame.getContentPane().add(panel);
        frame.setSize(400, 300);
        frame.setVisible(true);
    }

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
    }

    private boolean authenticateUser(String username, String password) {
        try (Connection connection = getConnection()) {
            String query = "SELECT * FROM user WHERE name = ? AND password = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setString(1, username);
                preparedStatement.setString(2, password);

                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    return resultSet.next(); // If there is a matching user, authentication is successful
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    private void openLoggedInForm(String first_name) {
       loggedin_Form loggedInForm = new loggedin_Form("");
        loggedInForm.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("Login")) {
            String username = usernameField.getText();
            String password = new String(passwordField.getPassword());
            if (authenticateUser(username, password)) {
                JOptionPane.showMessageDialog(null, "Login successful");
                openLoggedInForm(username); // Open the logged-in form
                JFrame loginFrame = (JFrame) SwingUtilities.getRoot((Component) e.getSource());
                loginFrame.dispose(); // Close the login form
            } else {
                JOptionPane.showMessageDialog(null, "Invalid username or password");
            }
        } else if (e.getActionCommand().equals("Reset")) {
            usernameField.setText("");
            passwordField.setText("");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new user_loginForm().createAndShowGUI();
            }
        });
    }
}