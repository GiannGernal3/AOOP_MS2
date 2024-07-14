/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package paysys;

import PaySys_Classes.Deductions;
import PaySys_Classes.Employee;
import PaySys_Classes.Salary;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.WeekFields;
import java.util.Date;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.Timer;
import java.sql.Time;

/**
 *
 * @author Giann Gernale
 */
public class PaySys_Homepage extends javax.swing.JFrame {
    Timer updateTimer;
    int DELAY = 100;
    private static final String DB_URL = "jdbc:mysql://localhost:3306/payrollsystem_db";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "user123";
    /**
     * Creates new form PaySys_Homepage
     */
    public PaySys_Homepage() {
        initComponents();
        scaleImage();
        updateTimer = new Timer(DELAY, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                Date currentTime = new Date();
                String formatTimeSTR = "hh:mm:ss a";
                DateFormat formatTime = new SimpleDateFormat(formatTimeSTR);
                String formattedTimeSTR = formatTime.format(currentTime);
                
                SimpleDateFormat sdf = new SimpleDateFormat("MMMM dd yyyy");
                String currentDate = sdf.format(currentTime);
                
                displayDate.setText(currentDate);
                displayClock.setText(formattedTimeSTR);
            }
        
        });
        updateTimer.start();
    }
    private void scaleImage(){
        ImageIcon icon = new ImageIcon("Banner.png");
        Image img = icon.getImage();
        Image imgScale = img.getScaledInstance(label.getWidth(), label.getHeight(), Image.SCALE_SMOOTH);
        ImageIcon scaledIcon = new ImageIcon(imgScale);
        label.setIcon(scaledIcon);

    }

    public void setUserName(String Username){
        usernametext.setText(Username);
    }
    public void setFirstName(String firstname){
        jTextField2.setText(firstname);
    }
    public void setLastName(String lastname){
        jTextField3.setText(lastname);
    }
    public void setEmployeeDetails(Employee employee) {
    
    jTextField2.setText(employee.getFirstName());
    jTextField3.setText(employee.getLastName());
    jTextField4.setText(employee.getBirthday());
    jTextField5.setText(employee.getAddress());
    jTextField6.setText(employee.getPhoneNum());
    jTextField7.setText(employee.getSSS());
    jTextField8.setText(employee.getPhilHealth());
    jTextField9.setText(employee.getTIN());
    jTextField10.setText(employee.getPagIbig());
    jTextField11.setText(employee.getPosition());
    jTextField12.setText(String.valueOf(employee.getGrossSemiMonthly()));
    jTextField13.setText(String.valueOf(employee.getBasicSal()));
    jLabel21.setText(String.valueOf(employee.getRiceSubsidy()));
    jLabel22.setText(String.valueOf(employee.getPhoneAllowance()));
    jLabel23.setText(String.valueOf(employee.getClothingAllowance()));
    jTextField14.setText(String.valueOf(employee.getHourlyRate()));
    int tempID = Integer.parseInt(employee.getEmployeeID());
    int EmployeeID = tempID + 10000;
    jLabel17.setText(String.valueOf(EmployeeID));
}
   
    public void disableButtons(){
        ITBTN.setVisible(false);
        HRBTN.setVisible(false);
        ACCBTN.setVisible(false);
        HRBTN.setVisible(false);
    }
    public void onlyITBTN(){
        HRBTN.setVisible(false);
        ACCBTN.setVisible(false);
        HRBTN.setVisible(false);
    }
    public void onlyHRBTN(){
        ITBTN.setVisible(false);
        ACCBTN.setVisible(false);
    }
    public void onlyACCBTN(){
        ITBTN.setVisible(false);
        HRBTN.setVisible(false);
        HRBTN.setVisible(false);
    }
    
    private Connection connect() {
        Connection connection = null;
        try {
            String url = "jdbc:mysql://localhost:3306/payrollsystem_db";
            String user = "root";
            String password = "user123";
            connection = DriverManager.getConnection(url, user, password);
            System.out.println("Connected to the database successfully.");
        } catch (SQLException e) {
            System.err.println("Connection failed: " + e.getMessage());
            e.printStackTrace();
        }
        return connection;
    }

    public void timeIn(int employeeID) {
        LocalDate currentDate = LocalDate.now();
        LocalTime currentTime = LocalTime.now();

        String checkSQL = "SELECT TimeInDate FROM `attendance` WHERE EmployeeID = ? AND Date = ? ORDER BY AttendanceID DESC LIMIT 1";
        String insertSQL = "INSERT INTO `attendance` (EmployeeID, Date, TimeInDate) VALUES (?, ?, ?)";

        try (Connection connection = connect(); PreparedStatement checkStatement = connection.prepareStatement(checkSQL); PreparedStatement insertStatement = connection.prepareStatement(insertSQL)) {

            // Check for recent "Time In" records
            checkStatement.setInt(1, employeeID);
            checkStatement.setDate(2, java.sql.Date.valueOf(currentDate));
            ResultSet resultSet = checkStatement.executeQuery();

            if (resultSet.next()) {
                Time lastTimeIn = resultSet.getTime("TimeInDate");
                LocalTime lastTimeInLocal = lastTimeIn.toLocalTime();
                Duration duration = Duration.between(lastTimeInLocal, currentTime);

                System.out.println("Last Time In: " + lastTimeInLocal);
                System.out.println("Duration in minutes: " + duration.toMinutes());

                if (duration.toMinutes() < 5) {
                    JOptionPane.showMessageDialog(null, "You just recently timed in. Please wait for 5 minutes before timing in again.", "Warning", JOptionPane.WARNING_MESSAGE);
                    return;
                }
            }

            // Insert new "Time In" record
            insertStatement.setInt(1, employeeID);
            insertStatement.setDate(2, java.sql.Date.valueOf(currentDate));
            insertStatement.setTime(3, java.sql.Time.valueOf(currentTime));

            int rowsAffected = insertStatement.executeUpdate();
            if (rowsAffected > 0) {
                JOptionPane.showMessageDialog(null, "Time In recorded successfully for Employee ID: " + employeeID, "Success", JOptionPane.INFORMATION_MESSAGE);
                System.out.println("Time In recorded for Employee ID: " + employeeID);
            } else {
                JOptionPane.showMessageDialog(null, "Failed to record Time In for Employee ID: " + employeeID, "Error", JOptionPane.ERROR_MESSAGE);
                System.err.println("Failed to insert Time In record for Employee ID: " + employeeID);
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "An error occurred while recording Time In: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }
    
    public void timeOut(int employeeID) {
        LocalTime currentTime = LocalTime.now();
        LocalDate currentDate = LocalDate.now();

        String selectSQL = "SELECT AttendanceID, TimeInDate FROM `attendance` WHERE EmployeeID = ? AND Date = ? AND TimeOutDate IS NULL";
        String updateSQL = "UPDATE `attendance` SET TimeOutDate = ?, HoursWorked = ?, OvertimeHours = ? WHERE AttendanceID = ?";

        try (Connection connection = connect(); PreparedStatement selectStatement = connection.prepareStatement(selectSQL); PreparedStatement updateStatement = connection.prepareStatement(updateSQL)) {

            selectStatement.setInt(1, employeeID);
            selectStatement.setDate(2, java.sql.Date.valueOf(currentDate));

            ResultSet resultSet = selectStatement.executeQuery();

            if (resultSet.next()) {
                int attendanceID = resultSet.getInt("AttendanceID");
                Time timeIn = resultSet.getTime("TimeInDate");

                LocalTime timeInLocal = timeIn.toLocalTime();
                Duration duration = Duration.between(timeInLocal, currentTime);

                if (duration.toHours() < 1) {
                    JOptionPane.showMessageDialog(null, "You just recently timed in. Please wait for at least 1 hour before timing out.", "Warning", JOptionPane.WARNING_MESSAGE);
                    return;
                }

                long hoursWorked = duration.toHours();
                long overtimeHours = Math.max(0, hoursWorked - 8);

                updateStatement.setTime(1, java.sql.Time.valueOf(currentTime));
                updateStatement.setTime(2, java.sql.Time.valueOf(LocalTime.of((int) hoursWorked, (int) (duration.toMinutes() % 60))));
                updateStatement.setTime(3, java.sql.Time.valueOf(LocalTime.of((int) overtimeHours, (int) ((duration.toMinutes() % 60) * (overtimeHours > 0 ? 1 : 0)))));
                updateStatement.setInt(4, attendanceID);

                updateStatement.executeUpdate();
                System.out.println("Time Out recorded for Employee ID: " + employeeID + ". Hours worked: " + hoursWorked + ", Overtime: " + overtimeHours);
            } else {
                JOptionPane.showMessageDialog(null, "No open attendance record found for Employee ID: " + employeeID, "Error", JOptionPane.ERROR_MESSAGE);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    private boolean showUserName(){
        usernametext.setVisible(true);
        return true;
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
        usernametext = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        jButton6 = new javax.swing.JButton();
        jLabel12 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jButton7 = new javax.swing.JButton();
        HRBTN = new javax.swing.JButton();
        ACCBTN = new javax.swing.JButton();
        ITBTN = new javax.swing.JButton();
        jLabel25 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        HomeTab = new javax.swing.JPanel();
        label = new javax.swing.JLabel();
        displayClock = new javax.swing.JLabel();
        displayDate = new javax.swing.JLabel();
        jButton9 = new javax.swing.JButton();
        jButton10 = new javax.swing.JButton();
        ProfileTab = new javax.swing.JPanel();
        jTextField2 = new javax.swing.JLabel();
        jTextField3 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jTextField4 = new javax.swing.JLabel();
        jTextField5 = new javax.swing.JLabel();
        jTextField6 = new javax.swing.JLabel();
        jTextField7 = new javax.swing.JLabel();
        jTextField8 = new javax.swing.JLabel();
        jTextField9 = new javax.swing.JLabel();
        jTextField10 = new javax.swing.JLabel();
        jTextField11 = new javax.swing.JLabel();
        jTextField12 = new javax.swing.JLabel();
        jTextField13 = new javax.swing.JLabel();
        jTextField14 = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        jLabel17 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        jLabel23 = new javax.swing.JLabel();
        jLabel24 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        AttendanceTab = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        jComboBox1 = new javax.swing.JComboBox<>();
        jButton8 = new javax.swing.JButton();
        jComboBox2 = new javax.swing.JComboBox<>();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(255, 255, 255));
        setResizable(false);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setPreferredSize(new java.awt.Dimension(1000, 1500));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        usernametext.setFont(new java.awt.Font("Segoe UI", 1, 20)); // NOI18N
        usernametext.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Collaterals/profile.png"))); // NOI18N
        usernametext.setText("Hi, Taylor");
        jPanel1.add(usernametext, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 20, -1, 40));

        jButton1.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Collaterals/house-window.png"))); // NOI18N
        jButton1.setText("Home");
        jButton1.setBorder(null);
        jButton1.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 110, 170, -1));

        jButton2.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Collaterals/user.png"))); // NOI18N
        jButton2.setText("Profile");
        jButton2.setBorder(null);
        jButton2.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton2, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 150, 170, -1));

        jButton3.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jButton3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Collaterals/calendar-lines.png"))); // NOI18N
        jButton3.setText("Record");
        jButton3.setBorder(null);
        jButton3.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton3, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 380, 170, -1));

        jButton4.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jButton4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Collaterals/calendar-day.png"))); // NOI18N
        jButton4.setText("Leave");
        jButton4.setBorder(null);
        jButton4.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton4, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 420, 170, -1));

        jButton5.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jButton5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Collaterals/calendar-clock.png"))); // NOI18N
        jButton5.setText("Overtime");
        jButton5.setToolTipText("");
        jButton5.setBorder(null);
        jButton5.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton5, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 460, 170, -1));

        jButton6.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jButton6.setForeground(new java.awt.Color(153, 0, 51));
        jButton6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Collaterals/exit.png"))); // NOI18N
        jButton6.setText("Log Out");
        jButton6.setBorder(null);
        jButton6.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton6, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 610, 170, -1));

        jLabel12.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(102, 102, 255));
        jLabel12.setText("Management");
        jPanel1.add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 190, -1, -1));

        jLabel14.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(102, 102, 255));
        jLabel14.setText("Payslip");
        jPanel1.add(jLabel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 500, -1, -1));

        jLabel15.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel15.setForeground(new java.awt.Color(102, 102, 255));
        jLabel15.setText("Overview");
        jPanel1.add(jLabel15, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 80, -1, -1));

        jLabel16.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel16.setForeground(new java.awt.Color(102, 102, 255));
        jLabel16.setText("Attendance");
        jPanel1.add(jLabel16, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 340, -1, -1));

        jButton7.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jButton7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Collaterals/wallet-arrow.png"))); // NOI18N
        jButton7.setText("Generate");
        jButton7.setBorder(null);
        jButton7.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jButton7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton7ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton7, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 540, 170, -1));

        HRBTN.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        HRBTN.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Collaterals/calendar-pen.png"))); // NOI18N
        HRBTN.setText("Request");
        HRBTN.setBorder(null);
        HRBTN.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        HRBTN.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                HRBTNActionPerformed(evt);
            }
        });
        jPanel1.add(HRBTN, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 260, 170, -1));

        ACCBTN.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        ACCBTN.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Collaterals/credit-card-buyer.png"))); // NOI18N
        ACCBTN.setText("Payslip");
        ACCBTN.setBorder(null);
        ACCBTN.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jPanel1.add(ACCBTN, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 300, 170, -1));

        ITBTN.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        ITBTN.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Collaterals/users.png"))); // NOI18N
        ITBTN.setText("Employee");
        ITBTN.setBorder(null);
        ITBTN.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ITBTN.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ITBTNActionPerformed(evt);
            }
        });
        jPanel1.add(ITBTN, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 220, 170, -1));

        jLabel25.setText("© 2024 MotorPH Co., All Rights Reserved. ");
        jPanel1.add(jLabel25, new org.netbeans.lib.awtextra.AbsoluteConstraints(770, 630, -1, 40));

        jScrollPane2.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane2.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

        HomeTab.setBackground(new java.awt.Color(255, 255, 255));
        HomeTab.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        HomeTab.add(label, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 20, 720, 250));

        displayClock.setFont(new java.awt.Font("Segoe UI", 1, 48)); // NOI18N
        displayClock.setForeground(new java.awt.Color(0, 0, 102));
        displayClock.setText("00:00:00 AA");
        HomeTab.add(displayClock, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 380, -1, 70));

        displayDate.setFont(new java.awt.Font("Segoe UI", 1, 36)); // NOI18N
        displayDate.setText("MM-dd-yyyy");
        HomeTab.add(displayDate, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 290, -1, -1));

        jButton9.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        jButton9.setForeground(new java.awt.Color(0, 102, 0));
        jButton9.setText("Time In");
        jButton9.setMaximumSize(new java.awt.Dimension(70, 22));
        jButton9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton9ActionPerformed(evt);
            }
        });
        HomeTab.add(jButton9, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 568, 110, 22));

        jButton10.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        jButton10.setForeground(new java.awt.Color(102, 0, 0));
        jButton10.setText("Time Out");
        jButton10.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton10ActionPerformed(evt);
            }
        });
        HomeTab.add(jButton10, new org.netbeans.lib.awtextra.AbsoluteConstraints(620, 570, 110, 22));

        jTabbedPane1.addTab("tab1", HomeTab);

        ProfileTab.setBackground(new java.awt.Color(255, 255, 255));

        jTextField2.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        jTextField2.setForeground(new java.awt.Color(0, 0, 102));
        jTextField2.setText("Username");

        jTextField3.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        jTextField3.setForeground(new java.awt.Color(0, 0, 102));
        jTextField3.setText("Username");

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(0, 0, 102));
        jLabel1.setText("Birthday:");

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(0, 0, 102));
        jLabel2.setText("Address:");

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(0, 0, 102));
        jLabel3.setText("Phone#:");

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(0, 0, 102));
        jLabel4.setText("SSS#:");

        jLabel5.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(0, 0, 102));
        jLabel5.setText("PhilHealth#:");

        jLabel6.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(0, 0, 102));
        jLabel6.setText("TIN#:");

        jLabel7.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(0, 0, 102));
        jLabel7.setText("PagIbig#:");

        jLabel8.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(0, 0, 102));
        jLabel8.setText("Position:");

        jLabel9.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(0, 0, 102));
        jLabel9.setText("Basic Salary:");

        jLabel10.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(0, 0, 102));
        jLabel10.setText("Gross Semi Monthly:");

        jLabel11.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(0, 0, 102));
        jLabel11.setText("Hourly Rate:");

        jTextField4.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jTextField4.setForeground(new java.awt.Color(0, 0, 102));
        jTextField4.setText("jLabel12");

        jTextField5.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jTextField5.setForeground(new java.awt.Color(0, 0, 102));
        jTextField5.setText("jLabel13");

        jTextField6.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jTextField6.setForeground(new java.awt.Color(0, 0, 102));
        jTextField6.setText("jLabel14");

        jTextField7.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jTextField7.setForeground(new java.awt.Color(0, 0, 102));
        jTextField7.setText("jLabel15");

        jTextField8.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jTextField8.setForeground(new java.awt.Color(0, 0, 102));
        jTextField8.setText("jLabel16");

        jTextField9.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jTextField9.setForeground(new java.awt.Color(0, 0, 102));
        jTextField9.setText("jLabel17");

        jTextField10.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jTextField10.setForeground(new java.awt.Color(0, 0, 102));
        jTextField10.setText("jLabel18");

        jTextField11.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jTextField11.setForeground(new java.awt.Color(0, 0, 102));
        jTextField11.setText("jLabel19");

        jTextField12.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jTextField12.setForeground(new java.awt.Color(0, 0, 102));
        jTextField12.setText("jLabel20");

        jTextField13.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jTextField13.setForeground(new java.awt.Color(0, 0, 102));
        jTextField13.setText("jLabel21");

        jTextField14.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jTextField14.setForeground(new java.awt.Color(0, 0, 102));
        jTextField14.setText("jLabel22");

        jLabel17.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel17.setForeground(new java.awt.Color(0, 0, 102));
        jLabel17.setText("jLabel17");

        jLabel18.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel18.setForeground(new java.awt.Color(0, 0, 102));
        jLabel18.setText("Rice Subsidy: ");

        jLabel19.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel19.setForeground(new java.awt.Color(0, 0, 102));
        jLabel19.setText("Phone Allowance: ");

        jLabel20.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel20.setForeground(new java.awt.Color(0, 0, 102));
        jLabel20.setText("Clothing Allowance: ");

        jLabel21.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel21.setForeground(new java.awt.Color(0, 0, 102));
        jLabel21.setText("jLabel21");

        jLabel22.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel22.setForeground(new java.awt.Color(0, 0, 102));
        jLabel22.setText("jLabel22");

        jLabel23.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel23.setForeground(new java.awt.Color(0, 0, 102));
        jLabel23.setText("jLabel23");

        jLabel24.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel24.setForeground(new java.awt.Color(0, 0, 102));
        jLabel24.setText("Employee ID:");

        jLabel13.setFont(new java.awt.Font("Segoe UI", 1, 48)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(0, 0, 102));
        jLabel13.setText("Employee Profile");

        javax.swing.GroupLayout ProfileTabLayout = new javax.swing.GroupLayout(ProfileTab);
        ProfileTab.setLayout(ProfileTabLayout);
        ProfileTabLayout.setHorizontalGroup(
            ProfileTabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ProfileTabLayout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 752, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 19, Short.MAX_VALUE))
            .addGroup(ProfileTabLayout.createSequentialGroup()
                .addGap(45, 45, 45)
                .addGroup(ProfileTabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(ProfileTabLayout.createSequentialGroup()
                        .addComponent(jLabel10)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jTextField12))
                    .addGroup(ProfileTabLayout.createSequentialGroup()
                        .addComponent(jLabel9)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jTextField13))
                    .addGroup(ProfileTabLayout.createSequentialGroup()
                        .addComponent(jLabel18)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel21))
                    .addGroup(ProfileTabLayout.createSequentialGroup()
                        .addComponent(jLabel19)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel22))
                    .addGroup(ProfileTabLayout.createSequentialGroup()
                        .addComponent(jLabel11)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jTextField14))
                    .addGroup(ProfileTabLayout.createSequentialGroup()
                        .addComponent(jLabel20)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel23))
                    .addGroup(ProfileTabLayout.createSequentialGroup()
                        .addComponent(jLabel8)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jTextField11))
                    .addGroup(ProfileTabLayout.createSequentialGroup()
                        .addComponent(jLabel7)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jTextField10))
                    .addGroup(ProfileTabLayout.createSequentialGroup()
                        .addComponent(jLabel5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jTextField8))
                    .addGroup(ProfileTabLayout.createSequentialGroup()
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jTextField7))
                    .addGroup(ProfileTabLayout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jTextField6))
                    .addGroup(ProfileTabLayout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jTextField5))
                    .addGroup(ProfileTabLayout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jTextField4))
                    .addGroup(ProfileTabLayout.createSequentialGroup()
                        .addComponent(jLabel6)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jTextField9))
                    .addGroup(ProfileTabLayout.createSequentialGroup()
                        .addComponent(jLabel24)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel17))
                    .addGroup(ProfileTabLayout.createSequentialGroup()
                        .addGap(2, 2, 2)
                        .addGroup(ProfileTabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel13)
                            .addGroup(ProfileTabLayout.createSequentialGroup()
                                .addComponent(jTextField2)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jTextField3)))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        ProfileTabLayout.setVerticalGroup(
            ProfileTabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ProfileTabLayout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addComponent(jLabel13)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(ProfileTabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField2)
                    .addComponent(jTextField3))
                .addGap(18, 18, 18)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(ProfileTabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel17)
                    .addComponent(jLabel24))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(ProfileTabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jTextField4))
                .addGap(18, 18, 18)
                .addGroup(ProfileTabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jTextField5))
                .addGap(18, 18, 18)
                .addGroup(ProfileTabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(jTextField6))
                .addGap(18, 18, 18)
                .addGroup(ProfileTabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(jTextField7))
                .addGap(20, 20, 20)
                .addGroup(ProfileTabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(jTextField8))
                .addGap(18, 18, 18)
                .addGroup(ProfileTabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(jTextField9))
                .addGap(18, 18, 18)
                .addGroup(ProfileTabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(jTextField10))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(ProfileTabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(jTextField11))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(ProfileTabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField12)
                    .addComponent(jLabel10))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(ProfileTabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField13)
                    .addComponent(jLabel9))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(ProfileTabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel18)
                    .addComponent(jLabel21))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(ProfileTabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel19)
                    .addComponent(jLabel22))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(ProfileTabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel20)
                    .addComponent(jLabel23))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(ProfileTabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel11)
                    .addComponent(jTextField14))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("tab2", ProfileTab);

        AttendanceTab.setBackground(new java.awt.Color(255, 255, 255));

        jTextArea1.setColumns(20);
        jTextArea1.setRows(5);
        jScrollPane1.setViewportView(jTextArea1);

        jComboBox1.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December" }));

        jButton8.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jButton8.setText("Print");
        jButton8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton8ActionPerformed(evt);
            }
        });

        jComboBox2.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jComboBox2.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "2023", "2024", "2025" }));

        javax.swing.GroupLayout AttendanceTabLayout = new javax.swing.GroupLayout(AttendanceTab);
        AttendanceTab.setLayout(AttendanceTabLayout);
        AttendanceTabLayout.setHorizontalGroup(
            AttendanceTabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(AttendanceTabLayout.createSequentialGroup()
                .addContainerGap(33, Short.MAX_VALUE)
                .addGroup(AttendanceTabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(AttendanceTabLayout.createSequentialGroup()
                        .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(43, 43, 43)
                        .addComponent(jComboBox2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton8))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 706, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(51, Short.MAX_VALUE))
        );
        AttendanceTabLayout.setVerticalGroup(
            AttendanceTabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(AttendanceTabLayout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(AttendanceTabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton8, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jComboBox2, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 544, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jTabbedPane1.addTab("tab3", AttendanceTab);

        jScrollPane2.setViewportView(jTabbedPane1);

        jPanel1.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, -30, 770, 660));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 670, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        jTabbedPane1.setSelectedIndex(0);
        usernametext.setVisible(true);
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
        jTabbedPane1.setSelectedIndex(1);
        usernametext.setVisible(false);
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        // TODO add your handling code here:
        PaySys_Leave LeaveApp = new PaySys_Leave();
        LeaveApp.setVisible(true);
        LeaveApp.usernametext.setText(jTextField2.getText());
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
        // TODO add your handling code here:
        PaySys_Login login = new PaySys_Login();
        login.setVisible(true);
        dispose();
    }//GEN-LAST:event_jButton6ActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        // TODO add your handling code here:
        PaySys_Overtime overtime = new PaySys_Overtime();
        overtime.setVisible(true);
        overtime.usernametext.setText(jTextField2.getText());
    }//GEN-LAST:event_jButton5ActionPerformed

    private void ITBTNActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ITBTNActionPerformed
        // TODO add your handling code here:
        IT_Management itbtn = new IT_Management();
        itbtn.setVisible(true);
    }//GEN-LAST:event_ITBTNActionPerformed

    private void jButton8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton8ActionPerformed
        // TODO add your handling code here:
        // TODO add your handling code here:
        String employeeid = jLabel17.getText();
        int EmployeeID = Integer.parseInt(employeeid);
        int employeeID = EmployeeID - 10000;
        Salary salary = new Salary();
        salary.setEmployeeID(EmployeeID);
        String Month = jComboBox1.getSelectedItem().toString();
        int month = 0;
        switch (Month) {
            case "January":
                month = 1;
                break;
            case "February":
                month = 2;
                break;
            case "March":
                month = 3;
                break;
            case "April":
                month = 4;
                break;
            case "May":
                month = 5;
                break;
            case "June":
                month = 6;
                break;
            case "July":
                month = 7;
                break;
            case "August":
                month = 8;
                break;
            case "September":
                month = 9;
                break;
            case "October":
                month = 10;
                break;
            case "November":
                month = 11;
                break;
            case "December":
                month = 12;
                break;
        }
        String Year = jComboBox2.getSelectedItem().toString();
        int year = Integer.parseInt(Year);

        // Initialize weekly hours worked
        int week1Hours = 0;
        int week2Hours = 0;
        int week3Hours = 0;
        int week4Hours = 0;
        int overtimeHours = 0;

        try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/payrollsystem_db", "root", "user123")) {
            String sql = "SELECT Date, HoursWorked, OvertimeHours FROM `attendance` WHERE EmployeeID = ? AND MONTH(Date) = ? AND YEAR(Date) = ?";
            try (PreparedStatement ps = con.prepareStatement(sql)) {
                ps.setInt(1, employeeID);
                ps.setInt(2, month);
                ps.setInt(3, year);

                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    LocalDate date = rs.getDate("Date").toLocalDate();
                    int hoursWorked = rs.getTime("HoursWorked").toLocalTime().toSecondOfDay() / 3600;
                    overtimeHours = rs.getTime("OvertimeHours").toLocalTime().toSecondOfDay() / 3600;

                    int weekOfMonth = date.get(WeekFields.of(Locale.getDefault()).weekOfMonth());
                    switch (weekOfMonth) {
                        case 1:
                            week1Hours += hoursWorked;
                            break;
                        case 2:
                            week2Hours += hoursWorked;
                            break;
                        case 3:
                            week3Hours += hoursWorked;
                            break;
                        case 4:
                            week4Hours += hoursWorked;
                            break;
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        int TotalWorkHours = week1Hours + week2Hours + week3Hours + week4Hours;

        if (TotalWorkHours == 0) {
            JOptionPane.showMessageDialog(this, "No attendance records for the selected month and year.", "No Records", JOptionPane.INFORMATION_MESSAGE);
            jTextArea1.setText("");
        } else {
            double basicIncome = Double.parseDouble(jTextField13.getText());
            double HourlyRate = Double.parseDouble(jTextField14.getText());
            double GrossIncome = TotalWorkHours * HourlyRate;

            Deductions deductions = new Deductions();
            double SSSContri = deductions.sssContribution(GrossIncome);
            double PhilHealthContri = deductions.philhealthContribution(GrossIncome);
            double PagIbigContri = deductions.pagibigContribution(GrossIncome);
            double Contributions = SSSContri + PhilHealthContri + PagIbigContri;

            double taxeableIncome = GrossIncome - Contributions;
            double WithHoldingTax = deductions.withholdingTax(taxeableIncome);
            double Deductions = WithHoldingTax + Contributions;
            double deductedIncome = GrossIncome - Deductions;

            double RiceSubsidy = Double.parseDouble(jLabel21.getText());
            double PhoneAllowance = Double.parseDouble(jLabel22.getText());
            double ClothingAllowance = Double.parseDouble(jLabel23.getText());
            double Allowances = RiceSubsidy + PhoneAllowance + ClothingAllowance;

            double NetPay = deductedIncome + Allowances;

            salary.setWeeklyWorkHours(week1Hours, week2Hours, week3Hours, week4Hours);
            salary.setOverTimeHours(overtimeHours);

            jTextArea1.setText("Employee ID: " + EmployeeID + "\n"
                    + "Full Name: " + jTextField2.getText() + " " + jTextField3.getText() + "\n"
                    + "Position: " + jTextField11.getText() + "\n\n"
                    + "Month: " + Month + "\n"
                    + "Year: " + year + "\n\n"
                    + "Work for Week 1: " + salary.week1HoursWorked + "\n"
                    + "Work for Week 2: " + salary.week2HoursWorked + "\n"
                    + "Work for Week 3: " + salary.week3HoursWorked + "\n"
                    + "Work for Week 4: " + salary.week4HoursWorked + "\n"
                    + "Total Hours worked: " + TotalWorkHours + "\n"
                    + "Hourly Rate: " + HourlyRate + "\n"
                    + "Gross Income: " + GrossIncome + "\n\n"
                    + "SSS Contribution: " + SSSContri + "\n"
                    + "PhilHealth Contribution: " + PhilHealthContri + "\n"
                    + "PagIbig Contribution: " + PagIbigContri + "\n"
                    + "WithHolding Tax: " + WithHoldingTax + "\n\n"
                    + "Rice Subsidy: " + RiceSubsidy + "\n"
                    + "Phone Allowance: " + PhoneAllowance + "\n"
                    + "Clothing Allowance: " + ClothingAllowance + "\n"
                    + "Total Deductions: " + Deductions + "\n\n"
                    + "Net Pay: " + NetPay);
        }
    }//GEN-LAST:event_jButton8ActionPerformed

    private void jButton7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton7ActionPerformed
        // TODO add your handling code here:
        
        jTabbedPane1.setSelectedIndex(2);
        showUserName();
    }//GEN-LAST:event_jButton7ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // TODO add your handling code here:
        PaySys_Attendance attendance = new PaySys_Attendance();

        // Assuming jLabel17 contains the employee ID, set jLabel3 with its text
        String employeeIDText = jLabel17.getText().trim();
        System.out.println("Setting jLabel3 text to: " + employeeIDText);

        // Initialize the PaySys_Attendance form with the employee ID
        attendance.initializeAttendanceForm(employeeIDText);

        // Make the PaySys_Attendance form visible
        attendance.setVisible(true);
    }//GEN-LAST:event_jButton3ActionPerformed

    private void HRBTNActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_HRBTNActionPerformed
        // TODO add your handling code here:
        HR_Management hrmanagement = new HR_Management();
        hrmanagement.setVisible(true);
    }//GEN-LAST:event_HRBTNActionPerformed

    private void jButton9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton9ActionPerformed
        // TODO add your handling code here:
        int tempID = Integer.parseInt(jLabel17.getText());
        int employeeID = tempID - 10000;
        timeIn(employeeID);
    }//GEN-LAST:event_jButton9ActionPerformed

    private void jButton10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton10ActionPerformed
        // TODO add your handling code here:
        int tempID = Integer.parseInt(jLabel17.getText());
        int employeeID = tempID - 10000;
        timeOut(employeeID);
    }//GEN-LAST:event_jButton10ActionPerformed

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
            java.util.logging.Logger.getLogger(PaySys_Homepage.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(PaySys_Homepage.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(PaySys_Homepage.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(PaySys_Homepage.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new PaySys_Homepage().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    public javax.swing.JButton ACCBTN;
    private javax.swing.JPanel AttendanceTab;
    public javax.swing.JButton HRBTN;
    private javax.swing.JPanel HomeTab;
    public javax.swing.JButton ITBTN;
    private javax.swing.JPanel ProfileTab;
    private javax.swing.JLabel displayClock;
    private javax.swing.JLabel displayDate;
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
    private javax.swing.JComboBox<String> jComboBox2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    public javax.swing.JLabel jLabel12;
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
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JLabel jTextField10;
    private javax.swing.JLabel jTextField11;
    private javax.swing.JLabel jTextField12;
    private javax.swing.JLabel jTextField13;
    private javax.swing.JLabel jTextField14;
    private javax.swing.JLabel jTextField2;
    private javax.swing.JLabel jTextField3;
    private javax.swing.JLabel jTextField4;
    private javax.swing.JLabel jTextField5;
    private javax.swing.JLabel jTextField6;
    private javax.swing.JLabel jTextField7;
    private javax.swing.JLabel jTextField8;
    private javax.swing.JLabel jTextField9;
    private javax.swing.JLabel label;
    private javax.swing.JLabel usernametext;
    // End of variables declaration//GEN-END:variables
}
