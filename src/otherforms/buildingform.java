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

public class buildingform extends JFrame implements ActionListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JLabel BuildingIDLabel, NameLabel, AddressLabel, OwnerLabel; 
    private JTextField buildingIdField, nameField, addressField, ownerField;
    private JButton addButton, viewButton, updateButton, deleteButton;

    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/gisubizo_louange_222010931";
    private static final String JDBC_USER = "louange";
    private static final String JDBC_PASSWORD = "222010931";

    public buildingform() {
        setTitle("Building Management Form");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 300);
        setLocationRelativeTo(null);
        setLayout(null);
        
     // Initialize components
        buildingIdField = new JTextField();
        nameField = new JTextField();
        addressField = new JTextField();
        ownerField = new JTextField();
        
        BuildingIDLabel = new JLabel("buildingid:");
        NameLabel = new JLabel("name:");
        AddressLabel = new JLabel("address:");
        OwnerLabel = new JLabel("owner:");
        addButton = new JButton("Add Building");
        viewButton = new JButton("View Buildings");
        updateButton = new JButton("Update Building");
        deleteButton = new JButton("Delete Building");

        // Set bounds for each component
        buildingIdField.setBounds(120, 20, 200, 20);
        BuildingIDLabel.setBounds(30,20,80,20);
        nameField.setBounds(120, 60, 200, 20);
        NameLabel.setBounds(30,60,80,20);
        addressField.setBounds(120, 120, 200, 20);
        AddressLabel.setBounds(30,120,80,20);
        ownerField.setBounds(120, 160, 200, 20);
        OwnerLabel.setBounds(30,160,80,20);
        
        addButton.setBounds(50, 180, 120, 30);
        viewButton.setBounds(200, 180, 120, 30);
        updateButton.setBounds(50, 220, 120, 30);
        deleteButton.setBounds(200, 220, 120, 30);

        // Add components to the frame
        add(BuildingIDLabel);
        add(buildingIdField);
        add(NameLabel);
        add(nameField);
        add(AddressLabel);
        add(addressField);
        add(OwnerLabel);
        add(ownerField);
        add(addButton);
        add(viewButton);
        add(updateButton);
        add(deleteButton);

        // Add action listeners
        addButton.addActionListener(this);
        viewButton.addActionListener(this);
        updateButton.addActionListener(this);
        deleteButton.addActionListener(this);
        setVisible(true);
    }
    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD);
    }

    private void addBuilding() {
        try (Connection connection = getConnection()) {
            int buildingId = Integer.parseInt(buildingIdField.getText().trim());
            String name = nameField.getText().trim();
            String address = addressField.getText().trim();
            String owner = ownerField.getText().trim();

            String insertQuery = "INSERT INTO building (buildingid, name, address, owner) VALUES (?, ?, ?, ?)";
            try (PreparedStatement preparedStatement = connection.prepareStatement(insertQuery)) {
                preparedStatement.setInt(1, buildingId);
                preparedStatement.setString(2, name);
                preparedStatement.setString(3, address);
                preparedStatement.setString(4, owner);

                int rowsInserted = preparedStatement.executeUpdate();
                if (rowsInserted > 0) {
                    JOptionPane.showMessageDialog(this, "Building added successfully.");
                } else {
                    JOptionPane.showMessageDialog(this, "Failed to add building.");
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error connecting to the database or invalid input.");
        }
    }

    private void viewBuildings() {
        try (Connection connection = getConnection()) {
            String selectQuery = "SELECT * FROM building";
            try (PreparedStatement preparedStatement = connection.prepareStatement(selectQuery);
                 ResultSet resultSet = preparedStatement.executeQuery()) {

                StringBuilder buildingData = new StringBuilder("Building Data:\n");

                while (resultSet.next()) {
                    int buildingId = resultSet.getInt("buildingid");
                    String name = resultSet.getString("name");
                    String address = resultSet.getString("address");
                    String owner = resultSet.getString("owner");

                    buildingData.append("Building ID: ").append(buildingId)
                            .append(", Name: ").append(name)
                            .append(", Address: ").append(address)
                            .append(", Owner: ").append(owner).append("\n");
                }

                JTextArea textArea = new JTextArea(buildingData.toString());
                textArea.setEditable(false);
                JScrollPane scrollPane = new JScrollPane(textArea);
                scrollPane.setPreferredSize(new Dimension(400, 300));
                JOptionPane.showMessageDialog(this, scrollPane, "Building Data", JOptionPane.PLAIN_MESSAGE);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error connecting to the database.");
        }
    }

    // Implement updateBuilding and deleteBuilding methods similarly to addBuilding

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == addButton) {
            addBuilding();
        } else if (e.getSource() == viewButton) {
            viewBuildings();
        } else if (e.getSource() == updateButton) {
            // Implement updateBuilding method
        } else if (e.getSource() == deleteButton) {
            // Implement deleteBuilding method
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new buildingform().setVisible(true);
        });
    }
}
