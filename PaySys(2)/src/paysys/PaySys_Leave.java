/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package paysys;
import paysys.PaySys_Login;
import PaySys_Classes.Leave;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.Timer;
import static paysys.PaySys_Login.username;
import java.sql.PreparedStatement;
/**
 *
 * @author Giann Gernale
 */
public class PaySys_Leave extends javax.swing.JFrame {
    int EmployeeID = Integer.parseInt(PaySys_Login.password);
    private String SCHEMA = "payrollsystem_db"; // Replace with your schema name
    private String TABLE = "`leaves`"; // Replace with your table name
    private String USER = "root"; // Replace with your database username
    private String PASSWORD = "user123"; // Replace with your database password
    DefaultTableModel model = new DefaultTableModel();
    private Timer refreshTimer;
    /**
     * Creates new form PaySys_Leave
     */
    public PaySys_Leave() {
        initComponents();
        model.setColumnIdentifiers(new String[]{"Employee ID", "Leave Type", "Start Date", "End Date", "Reason", "Leave Status"});
        try {
            displayLeaveDetails(jTable1);
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        refreshTimer = new Timer(5000, e -> refreshTableData()); // Refresh every 5 seconds
        refreshTimer.start();
        displayLeaveCredits(EmployeeID);
    }
    
    public void displayLeaveDetails(JTable jTable1) throws SQLException, ClassNotFoundException {
        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/" + SCHEMA, USER, PASSWORD);
        Statement statement = connection.createStatement();

        // Prepare a query to fetch all columns except leaveID
        String sql = "SELECT EmployeeID, TypeOfLeave, StartDate, EndDate, Reason, LeaveStatus FROM " + TABLE;
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
    private void displayTable(JTable jTable){
         jTable1.setModel(model);
    }
    
    private void setResizeTable(){
        jTable1.setAutoResizeMode(jTable1.AUTO_RESIZE_OFF);
    }
    
    private void refreshTableData() {
        try {
            model.setRowCount(0); // Clear existing data
            displayLeaveDetails(jTable1);
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
    
    private boolean isValidLeaveDates(Date startDate, Date endDate) {
        if (startDate.after(endDate)) {
            return false;
        }

        // Check if start date is before today
        Date today = new Date();
        return !startDate.before(today);
    }
    
    private void displayLeaveCredits(int employeeID) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet employeeRS = null;

        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/payrollsystem_db", "root", "user123");

            // Prepare a query to fetch all columns except leaveID
            String sql = "SELECT employeeID, sickCredits, emergencyCredits, vacationCredits FROM `leavecredits` WHERE employeeID = ?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, employeeID);

            employeeRS = preparedStatement.executeQuery();
            if (employeeRS.next()) {
                jLabel9.setText(String.valueOf(employeeRS.getInt("sickCredits")));
                jLabel10.setText(String.valueOf(employeeRS.getInt("emergencyCredits")));
                jLabel11.setText(String.valueOf(employeeRS.getInt("vacationCredits")));
            }
        } catch (SQLException ex) {
            Logger.getLogger(PaySys_Leave.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            // Close resources to prevent memory leaks
            if (employeeRS != null) {
                try {
                    employeeRS.close();
                } catch (SQLException ex) {
                    Logger.getLogger(PaySys_Leave.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            if (preparedStatement != null) {
                try {
                    preparedStatement.close();
                } catch (SQLException ex) {
                    Logger.getLogger(PaySys_Leave.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException ex) {
                    Logger.getLogger(PaySys_Leave.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
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
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        LeaveStart = new com.toedter.calendar.JDateChooser();
        LeaveEnd = new com.toedter.calendar.JDateChooser();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        LeaveReason = new javax.swing.JTextArea();
        jLabel3 = new javax.swing.JLabel();
        TypeOfLeave = new javax.swing.JComboBox<>();
        jLabel4 = new javax.swing.JLabel();
        usernametext = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel24 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Leave Application Menu");
        setResizable(false);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        jButton1.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jButton1.setForeground(new java.awt.Color(0, 102, 51));
        jButton1.setText("Apply");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jButton2.setForeground(new java.awt.Color(102, 0, 0));
        jButton2.setText("Close");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(0, 0, 102));
        jLabel1.setText("Start Date");

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(0, 0, 102));
        jLabel2.setText("End Date");

        LeaveReason.setColumns(20);
        LeaveReason.setRows(5);
        jScrollPane1.setViewportView(LeaveReason);

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(0, 0, 102));
        jLabel3.setText("Reason");

        TypeOfLeave.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        TypeOfLeave.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Vacation", "Sick", "Emergency" }));

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(0, 0, 102));
        jLabel4.setText("Leave Type");

        usernametext.setFont(new java.awt.Font("Segoe UI", 1, 36)); // NOI18N
        usernametext.setForeground(new java.awt.Color(0, 0, 102));
        usernametext.setText("UserName");

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        jScrollPane2.setViewportView(jTable1);

        jLabel5.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(0, 0, 102));
        jLabel5.setText("Leave Credits");

        jLabel6.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(0, 0, 102));
        jLabel6.setText("Sick Credits:");

        jLabel7.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(0, 0, 102));
        jLabel7.setText("Emergency Credits:");

        jLabel8.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(0, 0, 102));
        jLabel8.setText("Vacation Credits:");

        jLabel9.setForeground(new java.awt.Color(0, 0, 102));
        jLabel9.setText("jLabel9");

        jLabel10.setForeground(new java.awt.Color(0, 0, 102));
        jLabel10.setText("jLabel10");

        jLabel11.setForeground(new java.awt.Color(0, 0, 102));
        jLabel11.setText("jLabel11");

        jLabel24.setText("© 2024 MotorPH Co., All Rights Reserved. ");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel24))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(26, 26, 26)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(LeaveStart, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(LeaveEnd, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jScrollPane1)
                            .addComponent(TypeOfLeave, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(usernametext)
                                    .addComponent(jLabel1)
                                    .addComponent(jLabel2)
                                    .addComponent(jLabel3)
                                    .addComponent(jLabel4)
                                    .addComponent(jLabel5)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(jLabel6)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jLabel9))
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(jLabel7)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jLabel10))
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(jLabel8)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jLabel11))
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGap(0, 0, Short.MAX_VALUE)))
                        .addGap(144, 144, 144)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 530, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(24, 24, 24))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane2)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(usernametext)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel6)
                            .addComponent(jLabel9))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel7)
                            .addComponent(jLabel10))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel8)
                            .addComponent(jLabel11))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 21, Short.MAX_VALUE)
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(LeaveStart, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(LeaveEnd, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(TypeOfLeave, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jButton1)
                            .addComponent(jButton2))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel24)
                .addGap(16, 16, 16))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
        dispose();
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        Leave leave = new Leave();
        Date startDate = LeaveStart.getDate();
        Date endDate = LeaveEnd.getDate();
        String leaveStatus = "Pending";
        int leaveCredits = 5;

        // Check for invalid dates
        if (!isValidLeaveDates(startDate, endDate)) {
            JOptionPane.showMessageDialog(null, "Invalid leave dates. Please ensure the start date is before the end date and after today.", "Error", JOptionPane.ERROR_MESSAGE);
            return; // Exit the method if dates are invalid
        }

        try {
            leave.setLeaveDetails(EmployeeID, startDate, endDate, TypeOfLeave.getSelectedItem().toString(),
                    LeaveReason.getText(), leaveStatus, leaveCredits);
            leave.recordLeave(); // Call recordLeave method after setting details
            // Display success message (optional)
            JOptionPane.showMessageDialog(null, "Leave application submitted!", "Success", JOptionPane.INFORMATION_MESSAGE);
        } catch (SQLException | ClassNotFoundException | ParseException ex) {
            // Handle database errors and ParseException (optional)
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error submitting leave application: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
        
        jButton1.setEnabled(false);

        // Create a Timer to re-enable the button after set time
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
    }//GEN-LAST:event_jButton1ActionPerformed

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
            java.util.logging.Logger.getLogger(PaySys_Leave.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(PaySys_Leave.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(PaySys_Leave.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(PaySys_Leave.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new PaySys_Leave().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private com.toedter.calendar.JDateChooser LeaveEnd;
    private javax.swing.JTextArea LeaveReason;
    private com.toedter.calendar.JDateChooser LeaveStart;
    private javax.swing.JComboBox<String> TypeOfLeave;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable jTable1;
    public javax.swing.JLabel usernametext;
    // End of variables declaration//GEN-END:variables
}
