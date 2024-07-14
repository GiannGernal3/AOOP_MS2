/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package paysys;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSetMetaData;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.Timer;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Giann Gernale
 */
public class IT_Management extends javax.swing.JFrame {
    DefaultTableModel model = new DefaultTableModel();
     DefaultTableModel model1 = new DefaultTableModel();
    private Timer refreshTimer;
    /**
     * Creates new form IT_Management
     */
    public IT_Management() {
        initComponents();
        model.setColumnIdentifiers(new String[] {"Employee ID", "Last Name", "First Name", "Birthday", "Address", "Phone Number", "SSS Number", "PhilHealth Number", "Tin Number", "PagIbig Number", "Status", "Position", "Immediate Supervisor", "Basic Salary", "Rice Subsidy", "Phone Allowance", "Clothing Allowance", "Gross Semi Monthly Rate", "Hourly Rate","Department ID"});
        model1.setColumnIdentifiers(new String[]{"Login ID","Username", "Password", "Position"});
        try {
            displayEmployeeDetails(jTable1);
            displayLoginDetails(jTable2);
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
         setResizeTable();
         addTextFieldListener();
        
    }
      private void insertEmployeeDetails() {
        // Get values from text fields
        String lastName = jTextField3.getText();
        String firstName = jTextField4.getText();
        Date birthday = jDateChooser1.getDate();
        String address = jTextField5.getText();
        String phone = jTextField6.getText();
        String sss = jTextField7.getText();
        String philHealth = jTextField8.getText();
        String tin = jTextField9.getText();
        String pagIbig = jTextField10.getText();
        String status = jTextField11.getText();
        String position = jTextField12.getText();
        String immediateSupervisor = jTextField13.getText();
        String department = String.valueOf(jComboBox1.getSelectedItem());
        int departmento = 0; 
        switch (department){
            case "Executive Management":
                departmento = 1;
                break;
            case "IT Department":
                departmento = 2;
                break;
            case "Human Resources Department":
                departmento = 3;
                break;
            case "Accounting Department":
                departmento = 4;
                break;
            case "Payroll Department":
                departmento = 5;
                break;
            case "Sales & Marketing Department":
                departmento = 6;
                break;
            case "Supply Chain and Logistics Department":
                departmento = 7;
                break;
            case "Customer Service Department":
                departmento = 8;
                break;
            default:
                System.err.println("Unknown department: " + department);
                break;
       }
        double basicSalary;
        double riceSubsidy;
        double phoneAllowance;
        double clothingAllowance;
        double grossSemiMonthly;
        double hourlyRate;

        try {
            basicSalary = Double.parseDouble(jTextField14.getText());
            riceSubsidy = Double.parseDouble(jTextField15.getText());
            phoneAllowance = Double.parseDouble(jTextField16.getText());
            clothingAllowance = Double.parseDouble(jTextField17.getText());
            grossSemiMonthly = Double.parseDouble(jTextField18.getText());
            hourlyRate = Double.parseDouble(jTextField19.getText());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Please enter valid numeric values for salary and allowances.", "Invalid Input", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Format the date
        String formattedBirthday = null;
        if (birthday != null) {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            formattedBirthday = dateFormat.format(birthday);
        } else {
            JOptionPane.showMessageDialog(this, "Please select a valid date of birth.", "Invalid Input", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
          int response = JOptionPane.showConfirmDialog(this, "Are you sure you want to apply these changes?", "Confirm", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
          if (response != JOptionPane.YES_OPTION) {
              return; // Exit the method if user cancels
          }

        // Database connection
        try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/payrollsystem_db", "root", "user123")) {

            // SQL Insert statement
            String sql = "INSERT INTO `employee` (LastName, FirstName, Birthday, Address, PhoneNumber, SSSNumber, PhilHealthNumber, TinNumber, PagIbigNumber, Status, Position, ImmediateSupervisor, BasicSalary, RiceSubsidy, PhoneAllowance, ClothingAllowance, GrossSemiMonthlyRate, HourlyRate, DepartmentID) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

            // Prepare statement
            try (PreparedStatement preparedStatement = con.prepareStatement(sql)) {
                preparedStatement.setString(1, lastName);
                preparedStatement.setString(2, firstName);
                preparedStatement.setString(3, formattedBirthday);
                preparedStatement.setString(4, address);
                preparedStatement.setString(5, phone);
                preparedStatement.setString(6, sss);
                preparedStatement.setString(7, philHealth);
                preparedStatement.setString(8, tin);
                preparedStatement.setString(9, pagIbig);
                preparedStatement.setString(10, status);
                preparedStatement.setString(11, position);
                preparedStatement.setString(12, immediateSupervisor);
                preparedStatement.setDouble(13, basicSalary);
                preparedStatement.setDouble(14, riceSubsidy);
                preparedStatement.setDouble(15, phoneAllowance);
                preparedStatement.setDouble(16, clothingAllowance);
                preparedStatement.setDouble(17, grossSemiMonthly);
                preparedStatement.setDouble(18, hourlyRate);
                preparedStatement.setInt(19, departmento);

                // Execute insert
                int rowsAffected = preparedStatement.executeUpdate();
                if (rowsAffected > 0) {
                    JOptionPane.showMessageDialog(this, "Employee details inserted successfully.");
                } else {
                    JOptionPane.showMessageDialog(this, "Failed to insert employee details.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Database error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
      
    private void insertLoginDetails() {
        // Get values from text fields
        String username = jTextField2.getText();
        String password = jTextField20.getText();
        String position = jTextField22.getText();
        
        int response = JOptionPane.showConfirmDialog(this, "Are you sure you want to apply these changes?", "Confirm", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
        if (response != JOptionPane.YES_OPTION) {
            return; // Exit the method if user cancels
        }

        // Database connection
        try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/payrollsystem_db", "root", "user123")) {

            // SQL Insert statement
            String sql = "INSERT INTO `login` (Username, Password, Position) VALUES (?, ?, ?)";

            // Prepare statement
            try (PreparedStatement preparedStatement = con.prepareStatement(sql)) {
                preparedStatement.setString(1, username);
                preparedStatement.setString(2, password);
                preparedStatement.setString(3, position);
                // Execute insert
                int rowsAffected = preparedStatement.executeUpdate();
                if (rowsAffected > 0) {
                    JOptionPane.showMessageDialog(this, "Employee login details inserted successfully.");
                } else {
                    JOptionPane.showMessageDialog(this, "Failed to insert employee login details.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Database error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void clearFields() { // Employee ID
        jTextField3.setText(""); // Last Name
        jTextField4.setText(""); // First Name
        jDateChooser1.setDate(null); // Birthday
        jTextField5.setText(""); // Address
        jTextField6.setText(""); // Phone Number
        jTextField7.setText(""); // SSS Number
        jTextField8.setText(""); // PhilHealth Number
        jTextField9.setText(""); // TIN Number
        jTextField10.setText(""); // PagIbig Number
        jTextField11.setText(""); // Status
        jTextField12.setText(""); // Position
        jTextField13.setText(""); // Immediate Supervisor
        jTextField14.setText(""); // Basic Salary
        jTextField15.setText(""); // Rice Subsidy
        jTextField16.setText(""); // Phone Allowance
        jTextField17.setText(""); // Clothing Allowance
        jTextField18.setText(""); // Gross Semi Monthly
        jTextField19.setText(""); // Hourly Rate
    }
    private void clearLoginFields() { // Employee ID
        jTextField2.setText(""); // Last Name
        jTextField20.setText(""); // Birthday
        jTextField22.setText("");// Hourly Rate
    }
    
    public void displayEmployeeDetails(JTable jTable1) throws SQLException, ClassNotFoundException {
        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/" + "payrollsystem_db", "root", "user123");
        Statement statement = connection.createStatement();

        // Prepare a query to fetch all columns except leaveID
        String sql = "SELECT EmployeeID, LastName, FirstName, Birthday, Address, PhoneNumber, SSSNumber, PhilHealthNumber, TinNumber, PagIbigNumber, Status, Position, ImmediateSupervisor, BasicSalary, RiceSubsidy, PhoneAllowance, ClothingAllowance, GrossSemiMonthlyRate, HourlyRate, DepartmentID FROM " + "`employee`";
        ResultSet resultSet = statement.executeQuery(sql);


        // Add rows from the result set
        while (resultSet.next()) {
            Object[] row = new Object[resultSet.getMetaData().getColumnCount()];
            for (int i = 1; i <= resultSet.getMetaData().getColumnCount(); i++) {
                row[i - 1] = resultSet.getObject(i);
            }
            model.addRow(row);
        }

        // Set the table model for the JTable
        jTable1.setModel(model);

        // Close the connection
        connection.close();
    }
    public void displayLoginDetails(JTable jTable1) throws SQLException, ClassNotFoundException {
        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/" + "payrollsystem_db", "root", "user123");
        Statement statement = connection.createStatement();

        // Prepare a query to fetch all columns except leaveID
        String sql = "SELECT LoginID, Username, Password, Position FROM " + "`login`";
        ResultSet resultSet = statement.executeQuery(sql);


        // Add rows from the result set
        while (resultSet.next()) {
            Object[] row = new Object[resultSet.getMetaData().getColumnCount()];
            for (int i = 1; i <= resultSet.getMetaData().getColumnCount(); i++) {
                row[i - 1] = resultSet.getObject(i);
            }
            model1.addRow(row);
        }

        // Set the table model for the JTable
        jTable2.setModel(model1);

        // Close the connection
        connection.close();
    }
    
    private void displayTable(JTable jTable){
         jTable1.setModel(model);
    }
    private void displayTable1(JTable jTable){
         jTable2.setModel(model1);
    }
    private void setResizeTable(){
        jTable1.setAutoResizeMode(jTable1.AUTO_RESIZE_OFF);
    }
    
    private void refreshTableData() {
        try {
            model.setRowCount(0); // Clear existing data
            displayEmployeeDetails(jTable1);
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
    
    private void setResizeTable1() {
        jTable2.setAutoResizeMode(jTable1.AUTO_RESIZE_OFF);
    }

    private void refreshTableData1() {
        try {
            model1.setRowCount(0); // Clear existing data
            displayLoginDetails(jTable2);
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
    
     private void searchEmployeeById(String employeeId) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            // Establish a connection to the database
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/" + "payrollsystem_db", "root", "user123");

            // Prepare the SQL query
            String sql = "SELECT * FROM `employee` WHERE EmployeeID = ?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, employeeId);

            // Execute the query
            resultSet = preparedStatement.executeQuery();

            // Get the result set metadata
            ResultSetMetaData metaData = resultSet.getMetaData();
            int columnCount = metaData.getColumnCount();

            // Clear the existing table model
            model.setRowCount(0);
            model.setColumnCount(0);

            // Add column names to the table model
            for (int i = 1; i <= columnCount; i++) {
                model.addColumn(metaData.getColumnName(i));
            }

            // Add rows to the table model
            while (resultSet.next()) {
                Object[] row = new Object[columnCount];
                for (int i = 1; i <= columnCount; i++) {
                    row[i - 1] = resultSet.getObject(i);
                }
                model.addRow(row);
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error fetching employee data: " + ex.getMessage(),
                    "Database Error", JOptionPane.ERROR_MESSAGE);
        } finally {
            // Close the resources
            try {
                if (resultSet != null) {
                    resultSet.close();
                }
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }
     private void searchLoginById(String employeeId) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            // Establish a connection to the database
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/" + "payrollsystem_db", "root", "user123");

            // Prepare the SQL query
            String sql = "SELECT * FROM `login` WHERE LoginID = ?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, employeeId);

            // Execute the query
            resultSet = preparedStatement.executeQuery();

            // Get the result set metadata
            ResultSetMetaData metaData = resultSet.getMetaData();
            int columnCount = metaData.getColumnCount();

            // Clear the existing table model
            model1.setRowCount(0);
            model1.setColumnCount(0);

            // Add column names to the table model
            for (int i = 1; i <= columnCount; i++) {
                model1.addColumn(metaData.getColumnName(i));
            }

            // Add rows to the table model
            while (resultSet.next()) {
                Object[] row = new Object[columnCount];
                for (int i = 1; i <= columnCount; i++) {
                    row[i - 1] = resultSet.getObject(i);
                }
                model1.addRow(row);
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error fetching employee data: " + ex.getMessage(),
                    "Database Error", JOptionPane.ERROR_MESSAGE);
        } finally {
            // Close the resources
            try {
                if (resultSet != null) {
                    resultSet.close();
                }
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }
     private void setWages() {
        try {
            double BasicSalary = Double.parseDouble(jTextField14.getText());
            double grossSemi, hourlyRate;

            grossSemi = BasicSalary / 2;
            hourlyRate = (grossSemi / 14) / 8;

            jTextField18.setText(String.valueOf(grossSemi));
            jTextField19.setText(String.valueOf(hourlyRate));
        } catch (NumberFormatException e) {
            jTextField18.setText("");
            jTextField19.setText("");
        }
    }

    private void addTextFieldListener() {
        jTextField14.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                setWages();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                setWages();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                setWages();
            }
        });
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jTextField21 = new javax.swing.JTextField();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        jTextField1 = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        jTextField3 = new javax.swing.JTextField();
        jTextField4 = new javax.swing.JTextField();
        jDateChooser1 = new com.toedter.calendar.JDateChooser();
        jTextField5 = new javax.swing.JTextField();
        jTextField6 = new javax.swing.JTextField();
        jTextField7 = new javax.swing.JTextField();
        jTextField8 = new javax.swing.JTextField();
        jTextField9 = new javax.swing.JTextField();
        jTextField10 = new javax.swing.JTextField();
        jTextField11 = new javax.swing.JTextField();
        jTextField12 = new javax.swing.JTextField();
        jTextField13 = new javax.swing.JTextField();
        jTextField14 = new javax.swing.JTextField();
        jTextField15 = new javax.swing.JTextField();
        jTextField16 = new javax.swing.JTextField();
        jTextField17 = new javax.swing.JTextField();
        jTextField18 = new javax.swing.JTextField();
        jTextField19 = new javax.swing.JTextField();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        jButton6 = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        jComboBox1 = new javax.swing.JComboBox<>();
        jLabel24 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable2 = new javax.swing.JTable();
        jLabel25 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        jLabel23 = new javax.swing.JLabel();
        jLabel26 = new javax.swing.JLabel();
        jLabel27 = new javax.swing.JLabel();
        jLabel28 = new javax.swing.JLabel();
        jTextField2 = new javax.swing.JTextField();
        jTextField20 = new javax.swing.JTextField();
        jTextField22 = new javax.swing.JTextField();
        jTextField23 = new javax.swing.JTextField();
        jButton4 = new javax.swing.JButton();
        jButton7 = new javax.swing.JButton();
        jButton8 = new javax.swing.JButton();
        jButton9 = new javax.swing.JButton();
        jButton10 = new javax.swing.JButton();

        jTextField21.setText("jTextField21");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);

        jTabbedPane1.setBackground(new java.awt.Color(255, 255, 255));

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        jPanel1.add(jTextField1, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 20, 180, 22));

        jButton1.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jButton1.setText("Search");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 20, -1, 22));

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jTable1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable1MouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(jTable1);

        jPanel1.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 60, 490, 480));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 20)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(0, 0, 102));
        jLabel1.setText("Employee Details");
        jPanel1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(530, 20, -1, -1));

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 20)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(0, 0, 102));
        jLabel2.setText("Employee List");
        jLabel2.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        jPanel1.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 7, -1, 40));

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(0, 0, 102));
        jLabel3.setText("Department:");
        jPanel1.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(530, 520, -1, 20));

        jLabel5.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(0, 0, 102));
        jLabel5.setText("Last Name");
        jPanel1.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(530, 60, -1, -1));

        jLabel6.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(0, 0, 102));
        jLabel6.setText("First Name");
        jPanel1.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(530, 110, -1, -1));

        jLabel7.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(0, 0, 102));
        jLabel7.setText("Birthday");
        jPanel1.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(530, 160, -1, -1));

        jLabel8.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(0, 0, 102));
        jLabel8.setText("Address");
        jPanel1.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(530, 210, -1, -1));

        jLabel9.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(0, 0, 102));
        jLabel9.setText("Phone Number");
        jPanel1.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(530, 260, -1, -1));

        jLabel10.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(0, 0, 102));
        jLabel10.setText("SSS Number");
        jPanel1.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(530, 310, -1, -1));

        jLabel11.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(0, 0, 102));
        jLabel11.setText("PhilHealth Number");
        jPanel1.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(530, 360, -1, -1));

        jLabel12.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(0, 0, 102));
        jLabel12.setText("TIN");
        jPanel1.add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(530, 410, -1, -1));

        jLabel13.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(0, 0, 102));
        jLabel13.setText("Rice Subsidy");
        jPanel1.add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(760, 260, -1, -1));

        jLabel14.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(0, 0, 102));
        jLabel14.setText("Immediate Supervisor");
        jPanel1.add(jLabel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(760, 160, -1, -1));

        jLabel15.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel15.setForeground(new java.awt.Color(0, 0, 102));
        jLabel15.setText("Basic Salary");
        jPanel1.add(jLabel15, new org.netbeans.lib.awtextra.AbsoluteConstraints(760, 210, -1, -1));

        jLabel16.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel16.setForeground(new java.awt.Color(0, 0, 102));
        jLabel16.setText("Position");
        jPanel1.add(jLabel16, new org.netbeans.lib.awtextra.AbsoluteConstraints(760, 110, -1, -1));

        jLabel17.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel17.setForeground(new java.awt.Color(0, 0, 102));
        jLabel17.setText("Status");
        jPanel1.add(jLabel17, new org.netbeans.lib.awtextra.AbsoluteConstraints(760, 60, -1, -1));

        jLabel18.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel18.setForeground(new java.awt.Color(0, 0, 102));
        jLabel18.setText("Phone Allowance");
        jPanel1.add(jLabel18, new org.netbeans.lib.awtextra.AbsoluteConstraints(760, 310, -1, -1));

        jLabel19.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel19.setForeground(new java.awt.Color(0, 0, 102));
        jLabel19.setText("Clothing Allowance");
        jPanel1.add(jLabel19, new org.netbeans.lib.awtextra.AbsoluteConstraints(760, 360, -1, -1));

        jLabel20.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel20.setForeground(new java.awt.Color(0, 0, 102));
        jLabel20.setText("Gross Semi Monthly");
        jPanel1.add(jLabel20, new org.netbeans.lib.awtextra.AbsoluteConstraints(760, 410, -1, -1));

        jLabel21.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel21.setForeground(new java.awt.Color(0, 0, 102));
        jLabel21.setText("Hourly Rate");
        jPanel1.add(jLabel21, new org.netbeans.lib.awtextra.AbsoluteConstraints(760, 460, -1, -1));
        jPanel1.add(jTextField3, new org.netbeans.lib.awtextra.AbsoluteConstraints(530, 80, 200, -1));
        jPanel1.add(jTextField4, new org.netbeans.lib.awtextra.AbsoluteConstraints(530, 130, 200, -1));
        jPanel1.add(jDateChooser1, new org.netbeans.lib.awtextra.AbsoluteConstraints(530, 180, 200, -1));
        jPanel1.add(jTextField5, new org.netbeans.lib.awtextra.AbsoluteConstraints(530, 230, 200, -1));
        jPanel1.add(jTextField6, new org.netbeans.lib.awtextra.AbsoluteConstraints(530, 280, 200, -1));
        jPanel1.add(jTextField7, new org.netbeans.lib.awtextra.AbsoluteConstraints(530, 330, 200, -1));
        jPanel1.add(jTextField8, new org.netbeans.lib.awtextra.AbsoluteConstraints(530, 380, 200, -1));
        jPanel1.add(jTextField9, new org.netbeans.lib.awtextra.AbsoluteConstraints(530, 430, 200, -1));
        jPanel1.add(jTextField10, new org.netbeans.lib.awtextra.AbsoluteConstraints(530, 480, 200, -1));
        jPanel1.add(jTextField11, new org.netbeans.lib.awtextra.AbsoluteConstraints(760, 80, 200, -1));
        jPanel1.add(jTextField12, new org.netbeans.lib.awtextra.AbsoluteConstraints(760, 130, 200, -1));
        jPanel1.add(jTextField13, new org.netbeans.lib.awtextra.AbsoluteConstraints(760, 180, 200, -1));
        jPanel1.add(jTextField14, new org.netbeans.lib.awtextra.AbsoluteConstraints(760, 230, 200, -1));
        jPanel1.add(jTextField15, new org.netbeans.lib.awtextra.AbsoluteConstraints(760, 280, 200, -1));
        jPanel1.add(jTextField16, new org.netbeans.lib.awtextra.AbsoluteConstraints(760, 330, 200, -1));
        jPanel1.add(jTextField17, new org.netbeans.lib.awtextra.AbsoluteConstraints(760, 380, 200, -1));
        jPanel1.add(jTextField18, new org.netbeans.lib.awtextra.AbsoluteConstraints(760, 430, 200, -1));
        jPanel1.add(jTextField19, new org.netbeans.lib.awtextra.AbsoluteConstraints(760, 480, 200, -1));

        jButton2.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jButton2.setForeground(new java.awt.Color(0, 102, 0));
        jButton2.setText("Apply");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton2, new org.netbeans.lib.awtextra.AbsoluteConstraints(780, 560, 90, 22));

        jButton3.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jButton3.setForeground(new java.awt.Color(102, 0, 0));
        jButton3.setText("Clear");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton3, new org.netbeans.lib.awtextra.AbsoluteConstraints(890, 560, 90, 22));

        jButton5.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jButton5.setForeground(new java.awt.Color(102, 0, 0));
        jButton5.setText("Close");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton5, new org.netbeans.lib.awtextra.AbsoluteConstraints(420, 560, 90, 22));

        jButton6.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jButton6.setForeground(new java.awt.Color(0, 102, 51));
        jButton6.setText("Refresh Table");
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton6, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 560, -1, 22));

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(0, 0, 102));
        jLabel4.setText("Pag-Ibig Number");
        jPanel1.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(530, 460, -1, -1));

        jComboBox1.setForeground(new java.awt.Color(0, 0, 102));
        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Executive Management", "IT Department", "Human Resources Department", "Accounting Department", "Payroll Department", "Sales & Marketing Department", "Supply Chain and Logistics Department", "Customer Service Department" }));
        jPanel1.add(jComboBox1, new org.netbeans.lib.awtextra.AbsoluteConstraints(610, 520, 350, -1));

        jLabel24.setText("© 2024 MotorPH Co., All Rights Reserved. ");
        jPanel1.add(jLabel24, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 550, -1, 40));

        jTabbedPane1.addTab("Employee Creation", jPanel1);

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jTable2.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane2.setViewportView(jTable2);

        jPanel2.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(21, 59, 480, 480));

        jLabel25.setText("© 2024 MotorPH Co., All Rights Reserved. ");
        jPanel2.add(jLabel25, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 550, -1, 40));

        jLabel22.setFont(new java.awt.Font("Segoe UI", 1, 20)); // NOI18N
        jLabel22.setForeground(new java.awt.Color(0, 0, 102));
        jLabel22.setText("Login Accounts List");
        jLabel22.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        jPanel2.add(jLabel22, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 7, -1, 40));

        jLabel23.setFont(new java.awt.Font("Segoe UI", 1, 20)); // NOI18N
        jLabel23.setForeground(new java.awt.Color(0, 0, 102));
        jLabel23.setText("Login Details");
        jPanel2.add(jLabel23, new org.netbeans.lib.awtextra.AbsoluteConstraints(690, 120, -1, -1));

        jLabel26.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel26.setForeground(new java.awt.Color(0, 0, 102));
        jLabel26.setText("Username");
        jPanel2.add(jLabel26, new org.netbeans.lib.awtextra.AbsoluteConstraints(650, 180, -1, -1));

        jLabel27.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel27.setForeground(new java.awt.Color(0, 0, 102));
        jLabel27.setText("Password");
        jPanel2.add(jLabel27, new org.netbeans.lib.awtextra.AbsoluteConstraints(650, 240, -1, -1));

        jLabel28.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel28.setForeground(new java.awt.Color(0, 0, 102));
        jLabel28.setText("Position");
        jPanel2.add(jLabel28, new org.netbeans.lib.awtextra.AbsoluteConstraints(650, 300, -1, -1));
        jPanel2.add(jTextField2, new org.netbeans.lib.awtextra.AbsoluteConstraints(650, 200, 210, -1));
        jPanel2.add(jTextField20, new org.netbeans.lib.awtextra.AbsoluteConstraints(650, 260, 210, -1));
        jPanel2.add(jTextField22, new org.netbeans.lib.awtextra.AbsoluteConstraints(650, 320, 210, -1));
        jPanel2.add(jTextField23, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 20, 180, -1));

        jButton4.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jButton4.setText("Search");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });
        jPanel2.add(jButton4, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 20, -1, -1));

        jButton7.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jButton7.setForeground(new java.awt.Color(102, 0, 0));
        jButton7.setText("Close");
        jButton7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton7ActionPerformed(evt);
            }
        });
        jPanel2.add(jButton7, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 560, 100, -1));

        jButton8.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jButton8.setForeground(new java.awt.Color(0, 102, 0));
        jButton8.setText("Refresh Table");
        jButton8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton8ActionPerformed(evt);
            }
        });
        jPanel2.add(jButton8, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 560, -1, -1));

        jButton9.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jButton9.setForeground(new java.awt.Color(0, 102, 51));
        jButton9.setText("Apply");
        jButton9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton9ActionPerformed(evt);
            }
        });
        jPanel2.add(jButton9, new org.netbeans.lib.awtextra.AbsoluteConstraints(650, 390, 100, -1));

        jButton10.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jButton10.setForeground(new java.awt.Color(102, 0, 0));
        jButton10.setText("Clear");
        jButton10.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton10ActionPerformed(evt);
            }
        });
        jPanel2.add(jButton10, new org.netbeans.lib.awtextra.AbsoluteConstraints(760, 390, 100, -1));

        jTabbedPane1.addTab("Account Creation", jPanel2);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane1)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 636, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
        insertEmployeeDetails();
        
        Timer timer = new Timer(180000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                jButton1.setEnabled(true);
            }
        });

        // Set the timer to execute only once
        timer.setRepeats(false);

        // Start the timer
        timer.start();
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // TODO add your handling code here:
        clearFields();
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        // TODO add your handling code here:
        dispose();
    }//GEN-LAST:event_jButton5ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        String employeeID = jTextField1.getText();
        searchEmployeeById(employeeID);
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
        // TODO add your handling code here:
        if (refreshTimer != null && refreshTimer.isRunning()) {
            refreshTimer.stop();
        }

        refreshTimer = new Timer(500, e -> {
            refreshTableData();
            refreshTimer.stop(); // Stop the timer after one refresh
        });
        refreshTimer.setRepeats(false); // Ensure the timer only runs once
        refreshTimer.start();
    }//GEN-LAST:event_jButton6ActionPerformed

    private void jTable1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable1MouseClicked
        // TODO add your handling code here:
        
    }//GEN-LAST:event_jTable1MouseClicked

    private void jButton7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton7ActionPerformed
        // TODO add your handling code here:
        dispose();
    }//GEN-LAST:event_jButton7ActionPerformed

    private void jButton10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton10ActionPerformed
        // TODO add your handling code here:
        clearLoginFields();
    }//GEN-LAST:event_jButton10ActionPerformed

    private void jButton9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton9ActionPerformed
        // TODO add your handling code here:
        insertLoginDetails();
        Timer timer = new Timer(180000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                jButton1.setEnabled(true);
            }
        });

        // Set the timer to execute only once
        timer.setRepeats(false);

        // Start the timer
        timer.start();
    }//GEN-LAST:event_jButton9ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        // TODO add your handling code here:
        String employeeID = jTextField23.getText();
        searchLoginById(employeeID);
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jButton8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton8ActionPerformed
        // TODO add your handling code here:
         if (refreshTimer != null && refreshTimer.isRunning()) {
            refreshTimer.stop();
        }

        refreshTimer = new Timer(500, e -> {
            refreshTableData1();
            refreshTimer.stop(); // Stop the timer after one refresh
        });
        refreshTimer.setRepeats(false); // Ensure the timer only runs once
        refreshTimer.start();
    }//GEN-LAST:event_jButton8ActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Windows".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(IT_Management.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(IT_Management.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(IT_Management.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(IT_Management.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new IT_Management().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton10;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton7;
    private javax.swing.JButton jButton8;
    private javax.swing.JButton jButton9;
    private javax.swing.JComboBox<String> jComboBox1;
    private com.toedter.calendar.JDateChooser jDateChooser1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JTable jTable2;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField10;
    private javax.swing.JTextField jTextField11;
    private javax.swing.JTextField jTextField12;
    private javax.swing.JTextField jTextField13;
    private javax.swing.JTextField jTextField14;
    private javax.swing.JTextField jTextField15;
    private javax.swing.JTextField jTextField16;
    private javax.swing.JTextField jTextField17;
    private javax.swing.JTextField jTextField18;
    private javax.swing.JTextField jTextField19;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextField jTextField20;
    private javax.swing.JTextField jTextField21;
    private javax.swing.JTextField jTextField22;
    private javax.swing.JTextField jTextField23;
    private javax.swing.JTextField jTextField3;
    private javax.swing.JTextField jTextField4;
    private javax.swing.JTextField jTextField5;
    private javax.swing.JTextField jTextField6;
    private javax.swing.JTextField jTextField7;
    private javax.swing.JTextField jTextField8;
    private javax.swing.JTextField jTextField9;
    // End of variables declaration//GEN-END:variables
}
