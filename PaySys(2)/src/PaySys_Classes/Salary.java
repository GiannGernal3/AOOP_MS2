/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package PaySys_Classes;

/**
 *
 * @author Giann Gernale
 */
public class Salary {
    public int employeeID, week1HoursWorked, week2HoursWorked, week3HoursWorked, week4HoursWorked, overtimeHours;
    
    public void setEmployeeID(int employeeID){
        this.employeeID = employeeID;
    }
    
    public void setWeeklyWorkHours(int week1HoursWorked, int week2HoursWorked, int week3HoursWorked, int week4HoursWorked){
        this.week1HoursWorked = week1HoursWorked;
        this.week2HoursWorked = week2HoursWorked;
        this.week3HoursWorked = week3HoursWorked;
        this.week4HoursWorked = week4HoursWorked;
        
    }
    public void setOverTimeHours (int overtimeHours){
        this.overtimeHours = overtimeHours;
    }
    
    
    
    
}
