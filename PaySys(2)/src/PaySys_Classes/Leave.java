    /*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package PaySys_Classes;
import java.sql.Statement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.sql.SQLException;
/**
 *
 * @author Giann Gernale
 */
public class Leave {
    private int employeeID;
    private Date leaveStart;
    private Date leaveEnd;
    private String leaveType;
    private String leaveStatus;
    private String leaveReason;
    private int leaveCredits; // Assuming emergency leave credits are stored in a separate table

    // Setter methods for all attributes

    public void setLeaveDetails(int employeeID, Date leaveStart, Date leaveEnd, String leaveType, 
                             String leaveReason, String leaveStatus, int leaveCredits) throws ParseException {
    this.employeeID = employeeID;
    this.leaveType = leaveType;
    this.leaveReason = leaveReason;
    this.leaveStatus = leaveStatus;
    this.leaveCredits = leaveCredits;
    this.leaveStart = leaveStart;
    this.leaveEnd = leaveEnd;
}

    // Getter methods for all attributes (optional)

    public int getEmployeeID() {
        return employeeID;
    }

    public Date getLeaveStart() {
        return leaveStart;
    }

    public Date getLeaveEnd() {
        return leaveEnd;
    }

    public String getLeaveType() {
        return leaveType;
    }
    
    public String getLeaveReason() {
        return leaveReason;
    }

    public String getLeaveStatus() {
        return leaveStatus;
    }

    public int getSickLeaveCredits() {
        return leaveCredits;
    }

    // Method to record leave (assuming logic similar to CSV recording)
    public void recordLeave() throws SQLException, ClassNotFoundException {
        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/payrollsystem_db", "root", "user123");
        PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO `leaves` (EmployeeID, TypeOfLeave, StartDate, endDate, reason, leaveStatus, credits) VALUES (?, ?, ?, ?, ?, ?, ?)");
        // Set prepared statement parameters (check for null values if necessary)
        preparedStatement.setInt(1, this.employeeID); // Handle null check
        preparedStatement.setString(2, this.leaveType != null ? this.leaveType : "");
        preparedStatement.setTimestamp(3, new java.sql.Timestamp(this.leaveStart.getTime()));
        preparedStatement.setTimestamp(4, new java.sql.Timestamp(this.leaveEnd.getTime()));
        preparedStatement.setString(5, this.leaveReason != null ? this.leaveReason : "");
        preparedStatement.setString(6, leaveStatus);
        preparedStatement.setInt(7, this.leaveCredits);

        try {
            preparedStatement.executeUpdate(); // Execute the update query
            // Display success message (optional)
        } catch (SQLException ex) {
            // Handle SQL errors
            ex.printStackTrace();
        } finally {
            // Close connection (assuming it's not handled elsewhere)
            connection.close();
        }
    }
}
