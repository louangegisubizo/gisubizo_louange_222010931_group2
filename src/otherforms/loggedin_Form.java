package otherforms;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class loggedin_Form extends JFrame {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private JLabel welcomeLabel;

    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/gisubizo_louange_222010931";
    private static final String JDBC_USER = "louange";
    private static final String JDBC_PASSWORD = "222010931";

    public loggedin_Form(String username) {
        setTitle("Logged In Form");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 200);
        setLocationRelativeTo(null);

        // Initialize components
        welcomeLabel = new JLabel("Welcome, " + username + "!");

        // Set up the layout
        setLayout(new FlowLayout(FlowLayout.CENTER, 20, 80));

        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 20));

        add(welcomeLabel);
    }

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                
                String loggedInUsername = "";

                // Create and display the logged-in form
                loggedin_Form loggedOnForm = new loggedin_Form(loggedInUsername);
                loggedOnForm.setVisible(true);
            }
        });
    }
}
