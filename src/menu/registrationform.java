package menu;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class registrationform extends JFrame implements ActionListener {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTextField usernameField, passwordField;
    private JButton registerButton, loginButton;

    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/gisubizo_louange_222010931";
    private static final String JDBC_USER = "louange";
    private static final String JDBC_PASSWORD = "222010931";

    public registrationform() {
        setTitle("User Registration Form");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 200);
        setLocationRelativeTo(null);
        setLayout(null);

        // Initialize components
        usernameField = new JTextField();
        passwordField = new JPasswordField();

        registerButton = new JButton("Register");
        loginButton = new JButton("Go to Login");

        // Set bounds for each component
        usernameField.setBounds(150, 20, 200, 20);
        passwordField.setBounds(150, 60, 200, 20);
        registerButton.setBounds(50, 100, 120, 30);
        loginButton.setBounds(200, 100, 120, 30);

        // Add components to the frame
        addLabel("Username:", 30, 20);
        addLabel("Password:", 30, 60);
        add(usernameField);
        add(passwordField);
        add(registerButton);
        add(loginButton);

        // Add action listeners
        registerButton.addActionListener(this);
        loginButton.addActionListener(this);
    }

    private void addLabel(String text, int x, int y) {
        JLabel label = new JLabel(text);
        label.setBounds(x, y, 120, 20);
        add(label);
    }

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD);
    }

    private void registerUser() {
        try (Connection connection = getConnection()) {
            String username = usernameField.getText().trim();
            String password = passwordField.getText().trim();

            String insertQuery = "INSERT INTO user (name, password) VALUES (?, ?)";
            try (PreparedStatement preparedStatement = connection.prepareStatement(insertQuery)) {
                preparedStatement.setString(1, username);
                preparedStatement.setString(2, password);

                int rowsInserted = preparedStatement.executeUpdate();
                if (rowsInserted > 0) {
                    JOptionPane.showMessageDialog(this, "User registered successfully.");
                 
                } else {
                    JOptionPane.showMessageDialog(this, "Failed to register user.");
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error connecting to the database or invalid input.");
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == registerButton) {
            registerUser();
        } else if (e.getSource() == loginButton) {
          
            this.dispose();
         
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new registrationform().setVisible(true);
        });
    }
}
