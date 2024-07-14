/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package paysys;

import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.Timer;
import javax.swing.table.DefaultTableModel;
import java.sql.Timestamp;
import java.sql.Time;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 *
 * @author Giann Gernale
 */
public class HR_Management extends javax.swing.JFrame {
    DefaultTableModel model = new DefaultTableModel();
    DefaultTableModel model1 = new DefaultTableModel();
    DefaultTableModel model2 = new DefaultTableModel();
    private Timer refreshTimer;
    private int selectedRow = -1;
    private int selectedRow1 = -1;
    
    /**
     * Creates new form HR_Management
     */
    public HR_Management() {
        initComponents();
        model.setColumnIdentifiers(new String[] {"Employee ID", "Last Name", "First Name", "Birthday", "Address", "Phone Number", "SSS Number", "PhilHealth Number", "Tin Number", "PagIbig Number", "Status", "Position", "Immediate Supervisor", "Basic Salary", "Rice Subsidy", "Phone Allowance", "Clothing Allowance", "Gross Semi Monthly Rate", "Hourly Rate", "Department ID"});
        model1.setColumnIdentifiers(new String[] {"Leave ID","Employee ID", "Type of Leave", "Start Date", "End Date", "Reason", "Leave Status", "Leave Duration"});
        model2.setColumnIdentifiers(new String[] {"OvertimeID","Employee ID", "Overtime Status", "Overtime Date", "Overtime Hours"});
        try {
            displayEmployeeDetails(jTable1);
            displayLeaveRequest(jTable2);
            displayOvertimeRequest(jTable3);
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
         setResizeTable();
         disableTextFields();
    }

    private void clearFields() {
        jTextField2.setText(""); // Employee ID
        jTextField3.setText(""); // Last Name
        jTextField4.setText(""); // First Name
        jTextField20.setText(""); // Birthday
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
    public void displayLeaveRequest(JTable jTable2) throws SQLException, ClassNotFoundException {
        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/" + "payrollsystem_db", "root", "user123");
        Statement statement = connection.createStatement();

        // Prepare a query to fetch all columns except leaveID
        String sql = "SELECT LeaveID, EmployeeID, TypeOfLeave, StartDate, EndDate, Reason, LeaveStatus, LeaveDuration FROM " + "`leaves`";
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
    public void displayOvertimeRequest(JTable jTable3) throws SQLException, ClassNotFoundException {
        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/" + "payrollsystem_db", "root", "user123");
        Statement statement = connection.createStatement();

        // Prepare a query to fetch all columns except leaveID
        String sql = "SELECT OvertimeID, EmployeeID, OverTimeStatus, OvertimeDate, OvertimeHours FROM " + "`overtime`";
        ResultSet resultSet = statement.executeQuery(sql);


        // Add rows from the result set
        while (resultSet.next()) {
            Object[] row = new Object[resultSet.getMetaData().getColumnCount()];
            for (int i = 1; i <= resultSet.getMetaData().getColumnCount(); i++) {
                row[i - 1] = resultSet.getObject(i);
            }
            model2.addRow(row);
        }

        // Set the table model for the JTable
        jTable3.setModel(model2);

        // Close the connection
        connection.close();
    }
    private void displayTable(JTable jTable, JTable jTable2){
         jTable1.setModel(model);
         jTable2.setModel(model1);
    }
    
    private void setResizeTable(){
        jTable1.setAutoResizeMode(jTable1.AUTO_RESIZE_OFF);
    }
    
    private void refreshTableData() {
        try {
            model.setRowCount(0); // Clear existing data
            model1.setRowCount(0);
            model2.setRowCount(0);
            displayEmployeeDetails(jTable1);
            displayLeaveRequest(jTable2);
            displayOvertimeRequest(jTable3);
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
    private void applyEmployeeDetails() {
    // Get values from text fields
    int selectedRow = jTable1.getSelectedRow();
    if (selectedRow == -1) {
        JOptionPane.showMessageDialog(this, "No row selected", "Error", JOptionPane.ERROR_MESSAGE);
        return;
    }

    // Show confirmation dialog
    int response = JOptionPane.showConfirmDialog(this, "Are you sure you want to apply these changes?", "Confirm", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
    if (response != JOptionPane.YES_OPTION) {
        return; // Exit the method if user cancels
    }

    String employeeID = jTextField2.getText();
    String lastName = jTextField3.getText();
    String firstName = jTextField4.getText();
    String birthday = jTextField20.getText();
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
    double basicSalary = Double.parseDouble(jTextField14.getText());
    double riceSubsidy = Double.parseDouble(jTextField15.getText());
    double phoneAllowance = Double.parseDouble(jTextField16.getText());
    double clothingAllowance = Double.parseDouble(jTextField17.getText());
    double grossSemiMonthly = Double.parseDouble(jTextField18.getText());
    double hourlyRate = Double.parseDouble(jTextField19.getText());

    // Database connection
    try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/payrollsystem_db", "root", "user123")) {
        // SQL Update statement
        String sql = "UPDATE `Employee` SET LastName=?, FirstName=?, Birthday=?, Address=?, PhoneNumber=?, SSSNumber=?, PhilHealthNumber=?, TinNumber=?, PagIbigNumber=?, Status=?, Position=?, ImmediateSupervisor=?, BasicSalary=?, RiceSubsidy=?, PhoneAllowance=?, ClothingAllowance=?, GrossSemiMonthlyRate=?, HourlyRate=?, DepartmentID = ? WHERE EmployeeID=?";

        // Prepare statement
        try (PreparedStatement preparedStatement = con.prepareStatement(sql)) {
            preparedStatement.setString(1, lastName);
            preparedStatement.setString(2, firstName);
            preparedStatement.setString(3, birthday);
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
            preparedStatement.setString(20, employeeID);
            

            // Execute update
            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                JOptionPane.showMessageDialog(this, "Employee details updated successfully.");

                // Update table model
                jTable1.setValueAt(employeeID, selectedRow, 0);
                jTable1.setValueAt(lastName, selectedRow, 1);
                jTable1.setValueAt(firstName, selectedRow, 2);
                jTable1.setValueAt(birthday, selectedRow, 3);
                jTable1.setValueAt(address, selectedRow, 4);
                jTable1.setValueAt(phone, selectedRow, 5);
                jTable1.setValueAt(sss, selectedRow, 6);
                jTable1.setValueAt(philHealth, selectedRow, 7);
                jTable1.setValueAt(tin, selectedRow, 8);
                jTable1.setValueAt(pagIbig, selectedRow, 9);
                jTable1.setValueAt(status, selectedRow, 10);
                jTable1.setValueAt(position, selectedRow, 11);
                jTable1.setValueAt(immediateSupervisor, selectedRow, 12);
                jTable1.setValueAt(basicSalary, selectedRow, 13);
                jTable1.setValueAt(riceSubsidy, selectedRow, 14);
                jTable1.setValueAt(phoneAllowance, selectedRow, 15);
                jTable1.setValueAt(clothingAllowance, selectedRow, 16);
                jTable1.setValueAt(grossSemiMonthly, selectedRow, 17);
                jTable1.setValueAt(hourlyRate, selectedRow, 18);
                jTable1.setValueAt(departmento, selectedRow, 19);
            } else {
                JOptionPane.showMessageDialog(this, "Failed to update employee details.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    } catch (SQLException e) {
        e.printStackTrace();
        JOptionPane.showMessageDialog(this, "Database error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
    }
}
    private void disableTextFields(){
        jTextField2.setEnabled(false); // Employee ID
        jTextField3.setEnabled(false); // Last Name
        jTextField4.setEnabled(false); // First Name
        jTextField20.setEnabled(false); // Birthday
        jTextField5.setEnabled(false); // Address
        jTextField6.setEnabled(false); // Phone Number
        jTextField7.setEnabled(false); // SSS Number
        jTextField8.setEnabled(false); // PhilHealth Number
        jTextField9.setEnabled(false); // TIN Number
        jTextField10.setEnabled(false); // PagIbig Number
        jTextField11.setEnabled(false); // Status
        jTextField12.setEnabled(false); // Position
        jTextField13.setEnabled(false); // Immediate Supervisor
        jTextField14.setEnabled(false); // Basic Salary
        jTextField15.setEnabled(false); // Rice Subsidy
        jTextField16.setEnabled(false); // Phone Allowance
        jTextField17.setEnabled(false); // Clothing Allowance
        jTextField18.setEnabled(false); // Gross Semi Monthly
        jTextField19.setEnabled(false);
    }
    private void enableTextFields(){
        jTextField2.setEnabled(true); // Employee ID
        jTextField3.setEnabled(true); // Last Name
        jTextField4.setEnabled(true); // First Name
        jTextField20.setEnabled(true); // Birthday
        jTextField5.setEnabled(true); // Address
        jTextField6.setEnabled(true); // Phone Number
        jTextField7.setEnabled(true); // SSS Number
        jTextField8.setEnabled(true); // PhilHealth Number
        jTextField9.setEnabled(true); // TIN Number
        jTextField10.setEnabled(true); // PagIbig Number
        jTextField11.setEnabled(true); // Status
        jTextField12.setEnabled(true); // Position
        jTextField13.setEnabled(true); // Immediate Supervisor
        jTextField14.setEnabled(true); // Basic Salary
        jTextField15.setEnabled(true); // Rice Subsidy
        jTextField16.setEnabled(true); // Phone Allowance
        jTextField17.setEnabled(true); // Clothing Allowance
        jTextField18.setEnabled(true); // Gross Semi Monthly
        jTextField19.setEnabled(true);
    }
    private void deleteEmployee() {
        // Get the selected row index
        int selectedRow = jTable1.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "No row selected", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Show confirmation dialog
        int response = JOptionPane.showConfirmDialog(this, "Are you sure you want to delete this record?", "Confirm", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
        if (response != JOptionPane.YES_OPTION) {
            return; // Exit the method if user cancels
        }

        // Get the employeeID from the selected row
        String employeeID = jTable1.getValueAt(selectedRow, 0).toString();

        // Database connection
        try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/payrollsystem_db", "root", "user123")) {
            // SQL DELETE statement
            String sql = "DELETE FROM `employee` WHERE EmployeeID = ?";

            // Prepare statement
            try (PreparedStatement preparedStatement = con.prepareStatement(sql)) {
                preparedStatement.setString(1, employeeID);

                // Execute delete
                int rowsAffected = preparedStatement.executeUpdate();
                if (rowsAffected > 0) {
                    JOptionPane.showMessageDialog(this, "Employee record deleted successfully.");

                    // Remove row from table model
                    DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
                    model.removeRow(selectedRow);
                } else {
                    JOptionPane.showMessageDialog(this, "Failed to delete employee record.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Database error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        jButton2 = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jTextField2 = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jTextField3 = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jTextField4 = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jTextField5 = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        jTextField6 = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        jTextField7 = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        jTextField8 = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        jTextField9 = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jTextField10 = new javax.swing.JTextField();
        jTextField19 = new javax.swing.JTextField();
        jLabel21 = new javax.swing.JLabel();
        jTextField18 = new javax.swing.JTextField();
        jLabel20 = new javax.swing.JLabel();
        jTextField17 = new javax.swing.JTextField();
        jLabel19 = new javax.swing.JLabel();
        jTextField16 = new javax.swing.JTextField();
        jLabel18 = new javax.swing.JLabel();
        jTextField15 = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        jTextField14 = new javax.swing.JTextField();
        jLabel15 = new javax.swing.JLabel();
        jTextField13 = new javax.swing.JTextField();
        jLabel14 = new javax.swing.JLabel();
        jTextField12 = new javax.swing.JTextField();
        jLabel16 = new javax.swing.JLabel();
        jTextField11 = new javax.swing.JTextField();
        jLabel17 = new javax.swing.JLabel();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jTextField1 = new javax.swing.JTextField();
        jButton5 = new javax.swing.JButton();
        jTextField20 = new javax.swing.JTextField();
        jButton6 = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();
        jLabel23 = new javax.swing.JLabel();
        jComboBox1 = new javax.swing.JComboBox<>();
        jPanel3 = new javax.swing.JPanel();
        jLabel22 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable2 = new javax.swing.JTable();
        jButton8 = new javax.swing.JButton();
        jButton9 = new javax.swing.JButton();
        jTextField21 = new javax.swing.JTextField();
        jButton10 = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTable3 = new javax.swing.JTable();
        jLabel25 = new javax.swing.JLabel();
        jTextField22 = new javax.swing.JTextField();
        jButton11 = new javax.swing.JButton();
        jButton12 = new javax.swing.JButton();
        jButton13 = new javax.swing.JButton();
        jButton7 = new javax.swing.JButton();
        jLabel24 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jTabbedPane1.setBackground(new java.awt.Color(255, 255, 255));
        jTabbedPane1.setMinimumSize(new java.awt.Dimension(1000, 602));

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

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

        jPanel2.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 50, 490, 510));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 20)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(0, 51, 102));
        jLabel1.setText("Employee List");
        jPanel2.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 10, -1, -1));

        jButton2.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jButton2.setForeground(new java.awt.Color(102, 0, 51));
        jButton2.setText("Delete");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        jPanel2.add(jButton2, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 580, 90, -1));

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(0, 0, 102));
        jLabel2.setText("Employee Details");
        jPanel2.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(540, 20, -1, -1));

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(0, 0, 102));
        jLabel4.setText("Employee Number");
        jPanel2.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(540, 60, -1, -1));
        jPanel2.add(jTextField2, new org.netbeans.lib.awtextra.AbsoluteConstraints(540, 80, 200, -1));

        jLabel5.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(0, 0, 102));
        jLabel5.setText("Last Name");
        jPanel2.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(540, 110, -1, -1));
        jPanel2.add(jTextField3, new org.netbeans.lib.awtextra.AbsoluteConstraints(540, 130, 200, -1));

        jLabel6.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(0, 0, 102));
        jLabel6.setText("First Name");
        jPanel2.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(540, 160, -1, -1));
        jPanel2.add(jTextField4, new org.netbeans.lib.awtextra.AbsoluteConstraints(540, 180, 200, -1));

        jLabel7.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(0, 0, 102));
        jLabel7.setText("Birthday");
        jPanel2.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(540, 210, -1, -1));

        jLabel8.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(0, 0, 102));
        jLabel8.setText("Address");
        jPanel2.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(540, 260, -1, -1));
        jPanel2.add(jTextField5, new org.netbeans.lib.awtextra.AbsoluteConstraints(540, 280, 200, -1));

        jLabel9.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(0, 0, 102));
        jLabel9.setText("Phone Number");
        jPanel2.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(540, 310, -1, -1));
        jPanel2.add(jTextField6, new org.netbeans.lib.awtextra.AbsoluteConstraints(540, 330, 200, -1));

        jLabel10.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(0, 0, 102));
        jLabel10.setText("SSS Number");
        jPanel2.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(540, 360, -1, -1));
        jPanel2.add(jTextField7, new org.netbeans.lib.awtextra.AbsoluteConstraints(540, 380, 200, -1));

        jLabel11.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(0, 0, 102));
        jLabel11.setText("PhilHealth Number");
        jPanel2.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(540, 410, -1, -1));
        jPanel2.add(jTextField8, new org.netbeans.lib.awtextra.AbsoluteConstraints(540, 430, 200, -1));

        jLabel12.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(0, 0, 102));
        jLabel12.setText("TIN");
        jPanel2.add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(540, 460, -1, -1));
        jPanel2.add(jTextField9, new org.netbeans.lib.awtextra.AbsoluteConstraints(540, 480, 200, -1));

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(0, 0, 102));
        jLabel3.setText("Department");
        jPanel2.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(770, 510, -1, -1));
        jPanel2.add(jTextField10, new org.netbeans.lib.awtextra.AbsoluteConstraints(540, 530, 200, -1));
        jPanel2.add(jTextField19, new org.netbeans.lib.awtextra.AbsoluteConstraints(770, 480, 200, -1));

        jLabel21.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel21.setForeground(new java.awt.Color(0, 0, 102));
        jLabel21.setText("Hourly Rate");
        jPanel2.add(jLabel21, new org.netbeans.lib.awtextra.AbsoluteConstraints(770, 460, -1, -1));
        jPanel2.add(jTextField18, new org.netbeans.lib.awtextra.AbsoluteConstraints(770, 430, 200, -1));

        jLabel20.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel20.setForeground(new java.awt.Color(0, 0, 102));
        jLabel20.setText("Gross Semi Monthly");
        jPanel2.add(jLabel20, new org.netbeans.lib.awtextra.AbsoluteConstraints(770, 410, -1, -1));
        jPanel2.add(jTextField17, new org.netbeans.lib.awtextra.AbsoluteConstraints(770, 380, 200, -1));

        jLabel19.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel19.setForeground(new java.awt.Color(0, 0, 102));
        jLabel19.setText("Clothing Allowance");
        jPanel2.add(jLabel19, new org.netbeans.lib.awtextra.AbsoluteConstraints(770, 360, -1, -1));
        jPanel2.add(jTextField16, new org.netbeans.lib.awtextra.AbsoluteConstraints(770, 330, 200, -1));

        jLabel18.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel18.setForeground(new java.awt.Color(0, 0, 102));
        jLabel18.setText("Phone Allowance");
        jPanel2.add(jLabel18, new org.netbeans.lib.awtextra.AbsoluteConstraints(770, 310, -1, -1));
        jPanel2.add(jTextField15, new org.netbeans.lib.awtextra.AbsoluteConstraints(770, 280, 200, -1));

        jLabel13.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(0, 0, 102));
        jLabel13.setText("Rice Subsidy");
        jPanel2.add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(770, 260, -1, -1));
        jPanel2.add(jTextField14, new org.netbeans.lib.awtextra.AbsoluteConstraints(770, 230, 200, -1));

        jLabel15.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel15.setForeground(new java.awt.Color(0, 0, 102));
        jLabel15.setText("Basic Salary");
        jPanel2.add(jLabel15, new org.netbeans.lib.awtextra.AbsoluteConstraints(770, 210, -1, -1));
        jPanel2.add(jTextField13, new org.netbeans.lib.awtextra.AbsoluteConstraints(770, 180, 200, -1));

        jLabel14.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(0, 0, 102));
        jLabel14.setText("Immediate Supervisor");
        jPanel2.add(jLabel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(770, 160, -1, -1));
        jPanel2.add(jTextField12, new org.netbeans.lib.awtextra.AbsoluteConstraints(770, 130, 200, -1));

        jLabel16.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel16.setForeground(new java.awt.Color(0, 0, 102));
        jLabel16.setText("Position");
        jPanel2.add(jLabel16, new org.netbeans.lib.awtextra.AbsoluteConstraints(770, 110, -1, -1));
        jPanel2.add(jTextField11, new org.netbeans.lib.awtextra.AbsoluteConstraints(770, 80, 200, -1));

        jLabel17.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel17.setForeground(new java.awt.Color(0, 0, 102));
        jLabel17.setText("Status");
        jPanel2.add(jLabel17, new org.netbeans.lib.awtextra.AbsoluteConstraints(770, 60, -1, -1));

        jButton3.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jButton3.setForeground(new java.awt.Color(0, 102, 0));
        jButton3.setText("Apply");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });
        jPanel2.add(jButton3, new org.netbeans.lib.awtextra.AbsoluteConstraints(770, 580, 90, -1));

        jButton4.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jButton4.setForeground(new java.awt.Color(102, 0, 0));
        jButton4.setText("Clear");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });
        jPanel2.add(jButton4, new org.netbeans.lib.awtextra.AbsoluteConstraints(880, 580, 90, 22));

        jTextField1.setForeground(new java.awt.Color(204, 204, 204));
        jTextField1.setText("Search Employee Number");
        jTextField1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTextField1MouseClicked(evt);
            }
        });
        jPanel2.add(jTextField1, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 20, 240, -1));

        jButton5.setText("Search");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });
        jPanel2.add(jButton5, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 20, 80, -1));
        jPanel2.add(jTextField20, new org.netbeans.lib.awtextra.AbsoluteConstraints(540, 230, 200, -1));

        jButton6.setText("Refresh Table");
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });
        jPanel2.add(jButton6, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 580, -1, -1));

        jButton1.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jButton1.setForeground(new java.awt.Color(0, 102, 0));
        jButton1.setText("Edit");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jPanel2.add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 580, 90, -1));

        jLabel23.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel23.setForeground(new java.awt.Color(0, 0, 102));
        jLabel23.setText("Pag-Ibig Number");
        jPanel2.add(jLabel23, new org.netbeans.lib.awtextra.AbsoluteConstraints(540, 510, -1, -1));

        jComboBox1.setForeground(new java.awt.Color(0, 0, 102));
        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Executive Management", "IT Department", "Human Resources Department", "Accounting Department", "Payroll Department", "Sales & Marketing Department", "Supply Chain and Logistics Department", "Customer Service Department" }));
        jPanel2.add(jComboBox1, new org.netbeans.lib.awtextra.AbsoluteConstraints(770, 530, 200, -1));

        jTabbedPane1.addTab("Employee Management", jPanel2);

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));
        jPanel3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel22.setFont(new java.awt.Font("Segoe UI", 1, 20)); // NOI18N
        jLabel22.setForeground(new java.awt.Color(0, 0, 102));
        jLabel22.setText("Leave Request");
        jPanel3.add(jLabel22, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 0, -1, 60));

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
        jTable2.setGridColor(new java.awt.Color(255, 255, 255));
        jTable2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable2MouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(jTable2);

        jPanel3.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 50, 610, 520));

        jButton8.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jButton8.setForeground(new java.awt.Color(0, 102, 0));
        jButton8.setText("Approve");
        jButton8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton8ActionPerformed(evt);
            }
        });
        jPanel3.add(jButton8, new org.netbeans.lib.awtextra.AbsoluteConstraints(600, 580, 90, -1));

        jButton9.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jButton9.setForeground(new java.awt.Color(102, 0, 0));
        jButton9.setText("Decline");
        jButton9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton9ActionPerformed(evt);
            }
        });
        jPanel3.add(jButton9, new org.netbeans.lib.awtextra.AbsoluteConstraints(710, 580, 90, -1));

        jTextField21.setForeground(new java.awt.Color(204, 204, 204));
        jTextField21.setText("Search Employee Number");
        jTextField21.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTextField21MouseClicked(evt);
            }
        });
        jPanel3.add(jTextField21, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 20, 230, 22));

        jButton10.setText("Search");
        jButton10.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton10ActionPerformed(evt);
            }
        });
        jPanel3.add(jButton10, new org.netbeans.lib.awtextra.AbsoluteConstraints(730, 20, -1, -1));

        jTabbedPane1.addTab("Leave Request", jPanel3);

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));
        jPanel4.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jTable3.setModel(new javax.swing.table.DefaultTableModel(
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
        jTable3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable3MouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(jTable3);

        jPanel4.add(jScrollPane3, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 50, 480, 520));

        jLabel25.setFont(new java.awt.Font("Segoe UI", 1, 20)); // NOI18N
        jLabel25.setForeground(new java.awt.Color(0, 0, 102));
        jLabel25.setText("Overtime Request");
        jPanel4.add(jLabel25, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 10, -1, 40));

        jTextField22.setForeground(new java.awt.Color(204, 204, 204));
        jTextField22.setText("Search Employee Number");
        jTextField22.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTextField22MouseClicked(evt);
            }
        });
        jPanel4.add(jTextField22, new org.netbeans.lib.awtextra.AbsoluteConstraints(480, 20, 170, 22));

        jButton11.setText("Search");
        jButton11.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton11ActionPerformed(evt);
            }
        });
        jPanel4.add(jButton11, new org.netbeans.lib.awtextra.AbsoluteConstraints(660, 20, -1, -1));

        jButton12.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jButton12.setForeground(new java.awt.Color(0, 102, 0));
        jButton12.setText("Approve");
        jButton12.setMaximumSize(new java.awt.Dimension(78, 27));
        jButton12.setMinimumSize(new java.awt.Dimension(78, 27));
        jButton12.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton12ActionPerformed(evt);
            }
        });
        jPanel4.add(jButton12, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 580, -1, -1));

        jButton13.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jButton13.setForeground(new java.awt.Color(102, 0, 0));
        jButton13.setText("Decline");
        jButton13.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton13ActionPerformed(evt);
            }
        });
        jPanel4.add(jButton13, new org.netbeans.lib.awtextra.AbsoluteConstraints(660, 580, -1, -1));

        jTabbedPane1.addTab("Overtime Request", jPanel4);

        jPanel1.add(jTabbedPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1000, 650));

        jButton7.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jButton7.setForeground(new java.awt.Color(102, 0, 0));
        jButton7.setText("Close");
        jButton7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton7ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton7, new org.netbeans.lib.awtextra.AbsoluteConstraints(850, 660, 140, 30));

        jLabel24.setText("© 2024 MotorPH Co., All Rights Reserved. ");
        jPanel1.add(jLabel24, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 670, -1, -1));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 703, Short.MAX_VALUE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // TODO add your handling code here:
        applyEmployeeDetails();
        
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
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        // TODO add your handling code here:
        clearFields();
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        // TODO add your handling code here:
        String employeeID = jTextField1.getText();
        searchEmployeeById(employeeID);
    }//GEN-LAST:event_jButton5ActionPerformed

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
        int selectedRow = jTable1.getSelectedRow();
        if (selectedRow != -1) {
            jTextField2.setText(jTable1.getValueAt(selectedRow, 0).toString());
            jTextField3.setText(jTable1.getValueAt(selectedRow, 1).toString());
            jTextField4.setText(jTable1.getValueAt(selectedRow, 2).toString());
            jTextField20.setText(jTable1.getValueAt(selectedRow, 3).toString());
            jTextField5.setText(jTable1.getValueAt(selectedRow, 4).toString());
            jTextField6.setText(jTable1.getValueAt(selectedRow, 5).toString());
            jTextField7.setText(jTable1.getValueAt(selectedRow, 6).toString());
            jTextField8.setText(jTable1.getValueAt(selectedRow, 7).toString());
            jTextField9.setText(jTable1.getValueAt(selectedRow, 8).toString());
            jTextField10.setText(jTable1.getValueAt(selectedRow, 9).toString());
            jTextField11.setText(jTable1.getValueAt(selectedRow, 10).toString());
            jTextField12.setText(jTable1.getValueAt(selectedRow, 11).toString());
            jTextField13.setText(jTable1.getValueAt(selectedRow, 12).toString());
            jTextField14.setText(jTable1.getValueAt(selectedRow, 13).toString());
            jTextField15.setText(jTable1.getValueAt(selectedRow, 14).toString());
            jTextField16.setText(jTable1.getValueAt(selectedRow, 15).toString());
            jTextField17.setText(jTable1.getValueAt(selectedRow, 16).toString());
            jTextField18.setText(jTable1.getValueAt(selectedRow, 17).toString());
            jTextField19.setText(jTable1.getValueAt(selectedRow, 18).toString());
        }
    }//GEN-LAST:event_jTable1MouseClicked

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        enableTextFields();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
        deleteEmployee();
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton7ActionPerformed
        // TODO add your handling code here:
        dispose();
    }//GEN-LAST:event_jButton7ActionPerformed

    private void jTable2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable2MouseClicked
        // TODO add your handling code here:
         selectedRow = jTable2.getSelectedRow();
    }//GEN-LAST:event_jTable2MouseClicked

    private void jButton8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton8ActionPerformed
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(null, "Please select a row first.", "Information", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        String leaveID = jTable2.getValueAt(selectedRow, 0).toString();
        String employeeID = jTable2.getValueAt(selectedRow, 1).toString();
        int LeaveID = Integer.parseInt(leaveID);
        int EmployeeID = Integer.parseInt(employeeID);
        String status = jTable2.getValueAt(selectedRow, 6).toString(); // Assuming status is in the 6th column
        Timestamp startDate = Timestamp.valueOf(jTable2.getValueAt(selectedRow, 3).toString());
        Timestamp endDate = Timestamp.valueOf(jTable2.getValueAt(selectedRow, 4).toString());

        if (!"Pending".equalsIgnoreCase(status)) {
            JOptionPane.showMessageDialog(null, "Only pending requests can be modified.", "Information", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        int Credits = 0;
        String typeOfLeave = jTable2.getValueAt(selectedRow, 2).toString();

        try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/payrollsystem_db", "root", "user123")) {
            String sql = "SELECT sickCredits, emergencyCredits, vacationCredits FROM leavecredits WHERE employeeID = ?";

            try (PreparedStatement stmt = con.prepareStatement(sql)) {
                stmt.setInt(1, EmployeeID);
                ResultSet rs = stmt.executeQuery();

                if (rs.next()) {
                    int sickCredits = rs.getInt("sickCredits");
                    int emergencyCredits = rs.getInt("emergencyCredits");
                    int vacationCredits = rs.getInt("vacationCredits");

                    switch (typeOfLeave) {
                        case "Sick":
                            Credits = sickCredits;
                            break;
                        case "Emergency":
                            Credits = emergencyCredits;
                            break;
                        case "Vacation":
                            Credits = vacationCredits;
                            break;
                        default:
                            JOptionPane.showMessageDialog(null, "Leave type mismatch!");
                            return;
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Employee not found in leave credits table.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
            }

            // Calculate the difference in days
            long timeDiff = endDate.getTime() - startDate.getTime();
            long daysDiff = TimeUnit.DAYS.convert(timeDiff, TimeUnit.MILLISECONDS);
            int DaysDiff = (int) daysDiff + 1; // Include the end date

            // Check if there are enough credits
            if (Credits == 0) {
                JOptionPane.showMessageDialog(null, "No more credits!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            } else if (Credits < DaysDiff) {
                JOptionPane.showMessageDialog(null, "Not enough credits!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Calculate new credits
            int newCredits = Credits - DaysDiff;

            // Update credits in the leavecredits table
            switch (typeOfLeave) {
                case "Sick":
                    sql = "UPDATE leavecredits SET sickCredits = ? WHERE employeeID = ?";
                    break;
                case "Emergency":
                    sql = "UPDATE leavecredits SET emergencyCredits = ? WHERE employeeID = ?";
                    break;
                case "Vacation":
                    sql = "UPDATE leavecredits SET vacationCredits = ? WHERE employeeID = ?";
                    break;
            }

            try (PreparedStatement preparedStatement = con.prepareStatement(sql)) {
                preparedStatement.setInt(1, newCredits);
                preparedStatement.setInt(2, EmployeeID);
                preparedStatement.executeUpdate();
            }

            // Update the status in the leave table
            sql = "UPDATE `leaves` SET LeaveStatus = ? WHERE LeaveID = ?";
            try (PreparedStatement preparedStatement = con.prepareStatement(sql)) {
                preparedStatement.setString(1, "Approved");
                preparedStatement.setInt(2, LeaveID);
                preparedStatement.executeUpdate();
            }
            sql = "UPDATE `leaves` SET LeaveDuration = ? WHERE LeaveID = ?";
            try (PreparedStatement preparedStatement = con.prepareStatement(sql)) {
                preparedStatement.setInt(1, DaysDiff);
                preparedStatement.setInt(2, LeaveID);
                preparedStatement.executeUpdate();
            }

            // Refresh the table to show updated status and credits
            refreshTableData();

            JOptionPane.showMessageDialog(null, "Leave request approved!", "Success", JOptionPane.INFORMATION_MESSAGE);
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Database error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_jButton8ActionPerformed

    private void jButton9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton9ActionPerformed
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(null, "Please select a row first.", "Information", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        String leaveID = jTable2.getValueAt(selectedRow, 0).toString();
        int LeaveID = Integer.parseInt(leaveID);
        String status = jTable2.getValueAt(selectedRow, 6).toString(); // Assuming status is in the 6th column

        if (!"Pending".equalsIgnoreCase(status)) {
            JOptionPane.showMessageDialog(null, "Only pending requests can be modified.", "Information", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/payrollsystem_db", "root", "user123")) {
            // Update the status in the database
            String sql = "UPDATE `leaves` SET LeaveStatus = ? WHERE LeaveID = ?";
            try (PreparedStatement preparedStatement = con.prepareStatement(sql)) {
                preparedStatement.setString(1, "Declined");
                preparedStatement.setInt(2, LeaveID);
                preparedStatement.executeUpdate();
            }

            // Refresh the table to show updated status and credits
            refreshTableData();
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Database error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_jButton9ActionPerformed

    private void jTextField1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTextField1MouseClicked
        // TODO add your handling code here:
        jTextField1.setText("");
    }//GEN-LAST:event_jTextField1MouseClicked

    private void jTextField21MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTextField21MouseClicked
        // TODO add your handling code here:
        jTextField21.setText("");
    }//GEN-LAST:event_jTextField21MouseClicked

    private void jButton10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton10ActionPerformed
        // TODO add your handling code here:
        String employeeId = jTextField21.getText();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            // Establish a connection to the database
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/" + "payrollsystem_db", "root", "user123");

            // Prepare the SQL query
            String sql = "SELECT * FROM `leaves` WHERE EmployeeID = ?";
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
    }//GEN-LAST:event_jButton10ActionPerformed

    private void jTextField22MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTextField22MouseClicked
        // TODO add your handling code here:
        jTextField22.setText("");
    }//GEN-LAST:event_jTextField22MouseClicked

    private void jButton11ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton11ActionPerformed
        // TODO add your handling code here:
        String employeeId = jTextField22.getText();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            // Establish a connection to the database
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/" + "payrollsystem_db", "root", "user123");

            // Prepare the SQL query
            String sql = "SELECT * FROM `overtime` WHERE EmployeeID = ?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, employeeId);

            // Execute the query
            resultSet = preparedStatement.executeQuery();

            // Get the result set metadata
            ResultSetMetaData metaData = resultSet.getMetaData();
            int columnCount = metaData.getColumnCount();

            // Clear the existing table model
            model2.setRowCount(0);
            model2.setColumnCount(0);

            // Add column names to the table model
            for (int i = 1; i <= columnCount; i++) {
                model2.addColumn(metaData.getColumnName(i));
            }

            // Add rows to the table model
            while (resultSet.next()) {
                Object[] row = new Object[columnCount];
                for (int i = 1; i <= columnCount; i++) {
                    row[i - 1] = resultSet.getObject(i);
                }
                model2.addRow(row);
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
    }//GEN-LAST:event_jButton11ActionPerformed

    private void jTable3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable3MouseClicked
        // TODO add your handling code here:
        selectedRow1 = jTable3.getSelectedRow();
    }//GEN-LAST:event_jTable3MouseClicked

    private void jButton12ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton12ActionPerformed
        // TODO add your handling code here:
        if (selectedRow1 == -1) {
            JOptionPane.showMessageDialog(null, "Please select a row first.", "Information", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        String overtimeID = jTable3.getValueAt(selectedRow1, 0).toString();
        String employeeID = jTable3.getValueAt(selectedRow1, 1).toString();
        int OvertimeID = Integer.parseInt(overtimeID);
        int EmployeeID = Integer.parseInt(employeeID);
        String status = jTable3.getValueAt(selectedRow1, 2).toString(); // Assuming status is in the 6th column

        if (!"Pending".equalsIgnoreCase(status)) {
            JOptionPane.showMessageDialog(null, "Only pending requests can be modified.", "Information", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/payrollsystem_db", "root", "user123")) {
            // Update the status in the database
            String sql = "UPDATE `overtime` SET OverTimeStatus = ? WHERE OvertimeID = ?";
            try (PreparedStatement preparedStatement = con.prepareStatement(sql)) {
                preparedStatement.setString(1, "Approved");
                preparedStatement.setInt(2, OvertimeID);
                preparedStatement.executeUpdate();
            }
            // Refresh the table to show updated status and credits
            refreshTableData();
            
            JOptionPane.showMessageDialog(null, "Overtime request approved!", "Success", JOptionPane.INFORMATION_MESSAGE);
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Database error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_jButton12ActionPerformed

    private void jButton13ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton13ActionPerformed
        // TODO add your handling code here:
        if (selectedRow1 == -1) {
            JOptionPane.showMessageDialog(null, "Please select a row first.", "Information", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        String overtimeID = jTable3.getValueAt(selectedRow1, 0).toString();
        String employeeID = jTable3.getValueAt(selectedRow1, 1).toString();
        int OvertimeID = Integer.parseInt(overtimeID);
        int EmployeeID = Integer.parseInt(employeeID);
        String status = jTable3.getValueAt(selectedRow1, 2).toString(); // Assuming status is in the 6th column

        if (!"Pending".equalsIgnoreCase(status)) {
            JOptionPane.showMessageDialog(null, "Only pending requests can be modified.", "Information", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/payrollsystem_db", "root", "user123")) {
            // Update the status in the database
            String sql = "UPDATE `overtime` SET OverTimeStatus = ? WHERE OvertimeID = ?";
            try (PreparedStatement preparedStatement = con.prepareStatement(sql)) {
                preparedStatement.setString(1, "Declined");
                preparedStatement.setInt(2, OvertimeID);
                preparedStatement.executeUpdate();
            }
            // Refresh the table to show updated status and credits
            refreshTableData();
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Database error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_jButton13ActionPerformed

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
            java.util.logging.Logger.getLogger(HR_Management.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(HR_Management.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(HR_Management.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(HR_Management.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new HR_Management().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton10;
    private javax.swing.JButton jButton11;
    private javax.swing.JButton jButton12;
    private javax.swing.JButton jButton13;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton7;
    private javax.swing.JButton jButton8;
    private javax.swing.JButton jButton9;
    private javax.swing.JComboBox<String> jComboBox1;
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
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JTable jTable2;
    private javax.swing.JTable jTable3;
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
    private javax.swing.JTextField jTextField3;
    private javax.swing.JTextField jTextField4;
    private javax.swing.JTextField jTextField5;
    private javax.swing.JTextField jTextField6;
    private javax.swing.JTextField jTextField7;
    private javax.swing.JTextField jTextField8;
    private javax.swing.JTextField jTextField9;
    // End of variables declaration//GEN-END:variables
}
