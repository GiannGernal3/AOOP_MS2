/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package PaySys_Classes;
import java.sql.Statement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.Scanner;
import java.sql.PreparedStatement;
/**
/**
 *
 * @author Giann Gernale
 */
public class Employee {
    private String EmployeeID, FirstName, LastName, Birthday, Address, PhoneNum, SSS, PhilHealth, TIN, PagIbig, Position;
    private double BasicSal,RiceSubsidy,PhoneAllowance,ClothingAllowance, GrossSemiMonthly, HourlyRate;
    
    public void setEmployeeID(String employeeID) {
        this.EmployeeID = employeeID;
    }

    public void setFirstName(String firstName) {
        this.FirstName = firstName;
    }

    public void setLastName(String lastName) {
        this.LastName = lastName;
    }

    public void setBirthday(String birthday) {
        this.Birthday = birthday;
    }

    public void setAddress(String address) {
        this.Address = address;
    }

    public void setPhoneNum(String phoneNum) {
        this.PhoneNum = phoneNum;
    }

    public void setSSS(String sss) {
        this.SSS = sss;
    }

    public void setPhilHealth(String philHealth) {
        this.PhilHealth = philHealth;
    }

    public void setTIN(String tin) {
        this.TIN = tin;
    }

    public void setPagIbig(String pagIbig) {
        this.PagIbig = pagIbig;
    }

    public void setPosition(String position) {
        this.Position = position;
    }

    public void setBasicSal(double basicSal) {
        this.BasicSal = basicSal;
    }
    public void setRiceSubsidy (double riceSubsidy){
        this.RiceSubsidy = riceSubsidy;
    }
    public void setPhoneAllowance (double phoneAllowance){
        this.PhoneAllowance = phoneAllowance;
    }
    public void setClothingAllowance (double clothingAllowance){
        this.ClothingAllowance = clothingAllowance;
    }
    public void setGrossSemiMonthly(double grossSemiMonthly) {
        this.GrossSemiMonthly = grossSemiMonthly;
    }

    public void setHourlyRate(double hourlyRate) {
        this.HourlyRate = hourlyRate;
    }
    public String getEmployeeID() {
        return EmployeeID;
    }

    public String getFirstName() {
        return FirstName;
    }

    public String getLastName() {
        return LastName;
    }

    public String getBirthday() {
        return Birthday;
    }

    public String getAddress() {
        return Address;
    }

    public String getPhoneNum() {
        return PhoneNum;
    }

    public String getSSS() {
        return SSS;
    }

    public String getPhilHealth() {
        return PhilHealth;
    }

    public String getTIN() {
        return TIN;
    }

    public String getPagIbig() {
        return PagIbig;
    }

    public String getPosition() {
        return Position;
    }

    public double getBasicSal() {
        return BasicSal;
    }
    public double getRiceSubsidy (){
        return RiceSubsidy;
    }
    public double getPhoneAllowance (){
        return PhoneAllowance;
    }
    public double getClothingAllowance (){
        return ClothingAllowance;
    }
    public double getGrossSemiMonthly() {
        return GrossSemiMonthly;
    }

    public double getHourlyRate() {
        return HourlyRate;
    }
}
