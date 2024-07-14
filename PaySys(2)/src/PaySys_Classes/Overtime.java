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
public class Overtime {

    private int employeeID;
    private Date overtimeDate;
    private String overtimeStatus;
    private int overtimeHours; // Assuming emergency leave credits are stored in a separate table
    // Setter methods for all attributes
    public void setOvertimeDetails(int employeeID, Date overtimeDate, String overtimeStatus, int overtimeHours) throws ParseException {
        this.employeeID = employeeID;
        this.overtimeDate = overtimeDate;
        this.overtimeStatus = overtimeStatus;
        this.overtimeHours = overtimeHours;
    }
    
     public int getEmployeeID() {
        return employeeID;
    }

    public Date getOvertimeDate() {
        return overtimeDate;
    }

    public String getOvertimeStatus() {
        return overtimeStatus;
    }

    public int getOvertimeHours() {
        return overtimeHours;
    }
    
    public void recordOvertime() throws SQLException, ClassNotFoundException {
        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/payrollsystem_db", "root", "user123");
        PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO `overtime` (EmployeeID, OverTimeStatus, OvertimeDate, OvertimeHours) VALUES (?, ?, ?, ?)");
        // Set prepared statement parameters (check for null values if necessary)
        preparedStatement.setInt(1, this.employeeID); // Handle null check
        preparedStatement.setString(2, this.overtimeStatus);
        preparedStatement.setTimestamp(3, new java.sql.Timestamp(this.overtimeDate.getTime()));
        preparedStatement.setInt(4, this.overtimeHours);

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
