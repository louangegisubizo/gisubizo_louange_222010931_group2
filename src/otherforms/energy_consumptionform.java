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

public class energy_consumptionform extends JFrame implements ActionListener {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTextField sensorIdField, timestampField, consumptionValueField;
    private JButton addButton, viewButton, updateButton, deleteButton;

    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/gisubizo_louange_222010931";
    private static final String JDBC_USER = "louange";
    private static final String JDBC_PASSWORD = "222010931";

    public energy_consumptionform() {
        setTitle("Energy Consumption Form");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 300);
        setLocationRelativeTo(null);
        setLayout(null);

        // Initialize components
        sensorIdField = new JTextField();
        timestampField = new JTextField();
        consumptionValueField = new JTextField();

        addButton = new JButton("Add Consumption");
        viewButton = new JButton("View Consumption");
        updateButton = new JButton("Update Consumption");
        deleteButton = new JButton("Delete Consumption");

        // Set bounds for each component
        sensorIdField.setBounds(150, 20, 200, 20);
        timestampField.setBounds(150, 60, 200, 20);
        consumptionValueField.setBounds(150, 100, 200, 20);
        addButton.setBounds(50, 140, 150, 30);
        viewButton.setBounds(220, 140, 150, 30);
        updateButton.setBounds(50, 180, 150, 30);
        deleteButton.setBounds(220, 180, 150, 30);

        // Add components to the frame
        addLabel("Sensor ID:", 30, 20);
        addLabel("Timestamp:", 30, 60);
        addLabel("Consumption Value:", 30, 100);
        add(sensorIdField);
        add(timestampField);
        add(consumptionValueField);
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

    private void addConsumption() {
        try (Connection connection = getConnection()) {
            int sensorId = Integer.parseInt(sensorIdField.getText().trim());
            String timestamp = timestampField.getText().trim();
            double consumptionValue = Double.parseDouble(consumptionValueField.getText().trim());

            String insertQuery = "INSERT INTO energy_consumption (sensorID, timestamp, consumptionID) VALUES (?, ?, ?)";
            try (PreparedStatement preparedStatement = connection.prepareStatement(insertQuery)) {
                preparedStatement.setInt(1, sensorId);
                preparedStatement.setString(2, timestamp);
                preparedStatement.setDouble(3, consumptionValue);

                int rowsInserted = preparedStatement.executeUpdate();
                if (rowsInserted > 0) {
                    JOptionPane.showMessageDialog(this, "Energy consumption added successfully.");
                } else {
                    JOptionPane.showMessageDialog(this, "Failed to add energy consumption.");
                }
            }
        } catch (SQLException | NumberFormatException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error connecting to the database or invalid input.");
        }
    }

    private void viewConsumption() {
        try (Connection connection = getConnection()) {
            String selectQuery = "SELECT * FROM energy_consumption";
            try (PreparedStatement preparedStatement = connection.prepareStatement(selectQuery);
                 ResultSet resultSet = preparedStatement.executeQuery()) {

                StringBuilder consumptionData = new StringBuilder("Energy Consumption Data:\n");

                while (resultSet.next()) {
                    int sensorId = resultSet.getInt("sensorID");
                    String timestamp = resultSet.getString("timestamp");
                    double consumptionValue = resultSet.getDouble("consumptionValue");

                    consumptionData.append("Sensor ID: ").append(sensorId)
                            .append(", Timestamp: ").append(timestamp)
                            .append(", Consumption Value: ").append(consumptionValue).append("\n");
                }

                JTextArea textArea = new JTextArea(consumptionData.toString());
                textArea.setEditable(false);
                JScrollPane scrollPane = new JScrollPane(textArea);
                scrollPane.setPreferredSize(new Dimension(400, 300));
                JOptionPane.showMessageDialog(this, scrollPane, "Energy Consumption Data", JOptionPane.PLAIN_MESSAGE);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error connecting to the database.");
        }
    }

    // Implement updateConsumption and deleteConsumption methods similarly to addConsumption

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == addButton) {
            addConsumption();
        } else if (e.getSource() == viewButton) {
            viewConsumption();
        } else if (e.getSource() == updateButton) {
            // Implement updateConsumption method
        } else if (e.getSource() == deleteButton) {
            // Implement deleteConsumption method
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new energy_consumptionform().setVisible(true);
        });
    }
}
