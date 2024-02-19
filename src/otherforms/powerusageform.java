package otherforms;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class powerusageform extends JFrame {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private JTextField buildingIdField, timestampField, usageValueField;

    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/gisubizo_louange_222010931";
    private static final String JDBC_USER = "louange";
    private static final String JDBC_PASSWORD = "222010931";

    public powerusageform() {
        setTitle("Power Usage Management Application");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 400);
        setLocationRelativeTo(null);

        // Initialize components
        buildingIdField = new JTextField(20);
        timestampField = new JTextField(20);
        usageValueField = new JTextField(20);
        

        JLabel buildingLabel = new JLabel("Building ID:");
        JLabel timestampLabel = new JLabel("Timestamp (yyyy-MM-dd HH:mm:ss):");
        JLabel usageValueLabel = new JLabel("Usage Value:");

        JButton addButton = new JButton("Add Power Usage");
        JButton viewButton = new JButton("View Power Usage");
        JButton updateButton = new JButton("Update Power Usage");
        JButton deleteButton = new JButton("Delete Power Usage");

        // Set up the layout
        setLayout(null);
        buildingLabel.setBounds(30, 30, 120, 20);
        buildingIdField.setBounds(150, 30, 200, 20);

        timestampLabel.setBounds(30, 70, 200, 20);
        timestampField.setBounds(230, 70, 200, 20);

        usageValueLabel.setBounds(30, 110, 200, 20);
        usageValueField.setBounds(150, 110, 200, 20);


        buildingIdField.setBounds(150, 30, 200, 20);
        timestampField.setBounds(150, 70, 200, 20);
        usageValueField.setBounds(150, 110, 200, 20);

        addButton.setBounds(150, 150, 150, 30);
        viewButton.setBounds(310, 150, 150, 30);
        updateButton.setBounds(150, 190, 150, 30);
        deleteButton.setBounds(310, 190, 150, 30);
        
        add(buildingLabel);
        add(buildingIdField);
        add(timestampLabel);
        add(timestampField);
        add(usageValueLabel);
        add(usageValueField);
        add(buildingIdField);
        add(timestampField);
        add(usageValueField);
        add(addButton);
        add(viewButton);
        add(updateButton);
        add(deleteButton);

        // Set up action listeners
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addPowerUsage();
            }
        });

        viewButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                viewPowerUsage();
            }
        });

        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updatePowerUsage();
            }
        });

        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deletePowerUsage();
            }
        });
    }

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD);
    }

    private void addPowerUsage() {
        try (Connection connection = getConnection()) {
            int buildingId = Integer.parseInt(buildingIdField.getText().trim());
            String timestampString = timestampField.getText().trim();
            Date timestamp = new SimpleDateFormat("yyyy-MM-dd ").parse(timestampString);
            double usageValue = Double.parseDouble(usageValueField.getText().trim());

            String insertQuery = "INSERT INTO powerusage (buildingid, timestamp, usagevalue) VALUES (?, ?, ?)";
            try (PreparedStatement preparedStatement = connection.prepareStatement(insertQuery)) {
                preparedStatement.setInt(1, buildingId);
                preparedStatement.setTimestamp(2, new Timestamp(timestamp.getTime()));
                preparedStatement.setDouble(3, usageValue);

                int rowsInserted = preparedStatement.executeUpdate();
                if (rowsInserted > 0) {
                    JOptionPane.showMessageDialog(this, "Power usage added successfully.");
                } else {
                    JOptionPane.showMessageDialog(this, "Failed to add power usage.");
                }
            }
        } catch (SQLException | ParseException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error connecting to the database or invalid input.");
        }
    }

    private void viewPowerUsage() {
        try (Connection connection = getConnection()) {
            String selectQuery = "SELECT * FROM powerusage";
            try (Statement statement = connection.createStatement();
                 ResultSet resultSet = statement.executeQuery(selectQuery)) {

                StringBuilder powerUsageData = new StringBuilder("Power Usage Data:\n");

                while (resultSet.next()) {
                    int buildingId = resultSet.getInt("buildingid");
                    Timestamp timestamp = resultSet.getTimestamp("timestamp");
                    double usageValue = resultSet.getDouble("usagevalue");

                    powerUsageData.append("Building ID: ").append(buildingId)
                            .append(", Timestamp: ").append(timestamp)
                            .append(", Usage Value: ").append(usageValue).append("\n");
                }

                JTextArea textArea = new JTextArea(powerUsageData.toString());
                textArea.setEditable(false);
                JScrollPane scrollPane = new JScrollPane(textArea);
                scrollPane.setPreferredSize(new Dimension(400, 300));
                JOptionPane.showMessageDialog(this, scrollPane, "Power Usage Data", JOptionPane.PLAIN_MESSAGE);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error connecting to the database.");
        }
    }

    private void updatePowerUsage() {
        // Similar to addPowerUsage but with UPDATE SQL statement
        // Implement as needed
    }

    private void deletePowerUsage() {
        // Similar to addPowerUsage but with DELETE SQL statement
        // Implement as needed
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new powerusageform().setVisible(true);
            }
        });
    }
}
