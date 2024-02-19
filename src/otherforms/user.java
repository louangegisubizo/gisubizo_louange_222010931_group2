package otherforms;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.Vector;

public class user extends JFrame {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTextField nameField, passwordField, roleField;
    private JTable userTable;
    private DefaultTableModel tableModel;

    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/gisubizo_louange_222010931";
    private static final String JDBC_USER = "louange";
    private static final String JDBC_PASSWORD = "222010931";

    public user() {
        setTitle("User Management Application");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 400);
        setLocationRelativeTo(null);

        // Initialize components
        nameField = new JTextField();
        passwordField = new JTextField();
        roleField = new JTextField();

        JButton addButton = new JButton("Add User");
        JButton viewButton = new JButton("View Users");
        JButton updateButton = new JButton("Update User");
        JButton deleteButton = new JButton("Delete User");

        tableModel = new DefaultTableModel();
        userTable = new JTable(tableModel);

        // Set up the layout
        setLayout(null);

        nameField.setBounds(100, 20, 200, 20);
        passwordField.setBounds(100, 50, 200, 20);
        roleField.setBounds(100, 80, 200, 20);

        addButton.setBounds(100, 110, 120, 30);
        viewButton.setBounds(240, 110, 120, 30);
        updateButton.setBounds(100, 150, 120, 30);
        deleteButton.setBounds(240, 150, 120, 30);

        add(new JLabel("Name:")).setBounds(10, 20, 80, 20);
        add(nameField);
        add(new JLabel("password:")).setBounds(10, 50, 80, 20);
        add(passwordField);
        add(new JLabel("Role:")).setBounds(10, 80, 80, 20);
        add(roleField);
        add(addButton);
        add(viewButton);
        add(updateButton);
        add(deleteButton);

       /* add(new JScrollPane(userTable)).setBounds(350, 10, 220, 300);*/

        // Set up action listeners
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addUser();
            }
        });

        viewButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                viewUsers();
            }
        });

        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateUser();
            }
        });

        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteUser();
            }
        });
    }

    





    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD);
    }

    private void addUser() {
        try (Connection connection = getConnection()) {
            String name = nameField.getText().trim();
            String email = passwordField.getText().trim();
            String role = roleField.getText().trim();

            String insertQuery = "INSERT INTO user (name, password, role) VALUES (?, ?, ?)";
            try (PreparedStatement preparedStatement = connection.prepareStatement(insertQuery)) {
                preparedStatement.setString(1, name);
                preparedStatement.setString(2, email);
                preparedStatement.setString(3, role);

                int rowsInserted = preparedStatement.executeUpdate();
                if (rowsInserted > 0) {
                    JOptionPane.showMessageDialog(this, "User added successfully.");
                } else {
                    JOptionPane.showMessageDialog(this, "Failed to add user.");
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error connecting to the database.");
        }
    }

    private void viewUsers() {
        tableModel.setRowCount(0); // Clear existing data
        try (Connection connection = getConnection()) {
            String selectQuery = "SELECT * FROM user";
            try (Statement statement = connection.createStatement();
                 ResultSet resultSet = statement.executeQuery(selectQuery)) {

                while (resultSet.next()) {
                    String name = resultSet.getString("name");
                    String email = resultSet.getString("password");
                    String role = resultSet.getString("role");

                    Vector<Object> row = new Vector<>();
                    row.add(name);
                    row.add(email);
                    row.add(role);

                    tableModel.addRow(row);
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error connecting to the database.");
        }
    }

    private void updateUser() {
        try (Connection connection = getConnection()) {
            int selectedRow = userTable.getSelectedRow();
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(this, "Please select a user to update.");
                return;
            }

            String name = nameField.getText().trim();
            String password = passwordField.getText().trim();
            String role = roleField.getText().trim();

            String updateQuery = "UPDATE user SET name=?, password=?, role=? WHERE id=?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(updateQuery)) {
                preparedStatement.setString(1, name);
                preparedStatement.setString(2, password);
                preparedStatement.setString(3, role);

                int rowsUpdated = preparedStatement.executeUpdate();
                if (rowsUpdated > 0) {
                    JOptionPane.showMessageDialog(this, "User updated successfully.");
                } else {
                    JOptionPane.showMessageDialog(this, "Failed to update user.");
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error connecting to the database.");
        }
    }

    private void deleteUser() {
        try (Connection connection = getConnection()) {
            int selectedRow = userTable.getSelectedRow();
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(this, "Please select a user to delete.");
                return;
            }

            String deleteQuery = "DELETE FROM user WHERE id=?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(deleteQuery)) {
                int idToDelete = userTable.getSelectedRow() + 1; // Adding 1 as table rows start from 0
                preparedStatement.setInt(1, idToDelete);

                int rowsDeleted = preparedStatement.executeUpdate();
                if (rowsDeleted > 0) {
                    JOptionPane.showMessageDialog(this, "User deleted successfully.");
                } else {
                    JOptionPane.showMessageDialog(this, "Failed to delete user.");
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error connecting to the database.");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new user().setVisible(true);
            }
        });
    }
}
