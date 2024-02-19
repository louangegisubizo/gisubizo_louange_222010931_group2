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

public class alertform extends JFrame implements ActionListener {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTextField buildingIdField, sensorIdField, alertTypeField, timestampField;
    private JButton addButton, viewButton, updateButton, deleteButton;

    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/gisubizo_louange_222010931";
    private static final String JDBC_USER = "louange";
    private static final String JDBC_PASSWORD = "222010931";

    public alertform() {
        setTitle("Alert Form");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 300);
        setLocationRelativeTo(null);
        setLayout(null);

        // Initialize components
        buildingIdField = new JTextField();
        sensorIdField = new JTextField();
        alertTypeField = new JTextField();
        timestampField = new JTextField();

        addButton = new JButton("Add Alert");
        viewButton = new JButton("View Alerts");
        updateButton = new JButton("Update Alert");
        deleteButton = new JButton("Delete Alert");

        // Set bounds for each component
        buildingIdField.setBounds(150, 20, 200, 20);
        sensorIdField.setBounds(150, 60, 200, 20);
        alertTypeField.setBounds(150, 100, 200, 20);
        timestampField.setBounds(150, 140, 200, 20);
        addButton.setBounds(50, 180, 150, 30);
        viewButton.setBounds(220, 180, 150, 30);
        updateButton.setBounds(50, 220, 150, 30);
        deleteButton.setBounds(220, 220, 150, 30);

        // Add components to the frame
        addLabel("Building ID:", 30, 20);
        addLabel("Sensor ID:", 30, 60);
        addLabel("Alert Type:", 30, 100);
        addLabel("Timestamp:", 30, 140);
        add(buildingIdField);
        add(sensorIdField);
        add(alertTypeField);
        add(timestampField);
        add(addButton);
        add(viewButton);
        add(updateButton);
        add(deleteButton);

        // Add action listeners
        addButton.addActionListener(this);
        viewButton.addActionListener(this);
        updateButton.addActionListener(this);
        deleteButton.addActionListener(this);
    }

    private void addLabel(String text, int x, int y) {
        JLabel label = new JLabel(text);
        label.setBounds(x, y, 120, 20);
        add(label);
    }

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD);
    }

    private void addAlert() {
        try (Connection connection = getConnection()) {
            int buildingId = Integer.parseInt(buildingIdField.getText().trim());
            int sensorId = Integer.parseInt(sensorIdField.getText().trim());
            String alertType = alertTypeField.getText().trim();
            String timestamp = timestampField.getText().trim();

            String insertQuery = "INSERT INTO Alert (buildingid, sensorid, alerttype, timestamp) VALUES (?, ?, ?, ?)";
            try (PreparedStatement preparedStatement = connection.prepareStatement(insertQuery)) {
                preparedStatement.setInt(1, buildingId);
                preparedStatement.setInt(2, sensorId);
                preparedStatement.setString(3, alertType);
                preparedStatement.setString(4, timestamp);

                int rowsInserted = preparedStatement.executeUpdate();
                if (rowsInserted > 0) {
                    JOptionPane.showMessageDialog(this, "Alert added successfully.");
                } else {
                    JOptionPane.showMessageDialog(this, "Failed to add alert.");
                }
            }
        } catch (SQLException | NumberFormatException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error connecting to the database or invalid input.");
        }
    }

    private void viewAlerts() {
        try (Connection connection = getConnection()) {
            String selectQuery = "SELECT * FROM Alert";
            try (PreparedStatement preparedStatement = connection.prepareStatement(selectQuery);
                 ResultSet resultSet = preparedStatement.executeQuery()) {

                StringBuilder alertData = new StringBuilder("Alert Data:\n");

                while (resultSet.next()) {
                    int buildingId = resultSet.getInt("buildingid");
                    int sensorId = resultSet.getInt("sensorid");
                    String alertType = resultSet.getString("alerttype");
                    String timestamp = resultSet.getString("timestamp");

                    alertData.append("Building ID: ").append(buildingId)
                            .append(", Sensor ID: ").append(sensorId)
                            .append(", Alert Type: ").append(alertType)
                            .append(", Timestamp: ").append(timestamp).append("\n");
                }

                JTextArea textArea = new JTextArea(alertData.toString());
                textArea.setEditable(false);
                JScrollPane scrollPane = new JScrollPane(textArea);
                scrollPane.setPreferredSize(new Dimension(400, 300));
                JOptionPane.showMessageDialog(this, scrollPane, "Alert Data", JOptionPane.PLAIN_MESSAGE);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error connecting to the database.");
        }
    }

    // Implement updateAlert and deleteAlert methods similarly to addAlert

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == addButton) {
            addAlert();
        } else if (e.getSource() == viewButton) {
            viewAlerts();
        } else if (e.getSource() == updateButton) {
            // Implement updateAlert method
        } else if (e.getSource() == deleteButton) {
            // Implement deleteAlert method
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new alertform().setVisible(true);
        });
    }
}
