/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;
import main.Logable;

/**
 *
 * @author johan
 */
public class Employee extends Person implements Logable {
    private int employeeId;
    private String password;
    
    public static final int EMPLOYEE_ID = 123;
    public static final String PASSWORD = "test";
    
    public Employee(String name, int employeeId, String password) {
        super(name);
        this.employeeId = employeeId;
        this.password = password;
    }
    
    @Override
    public boolean login(int user, String password) {
        return (user == EMPLOYEE_ID && password.equals(PASSWORD));
    }
    
    public int getEmployeeId() {
        return employeeId;
    }
    
    public void setEmployeeId(int employeeId) {
        this.employeeId = employeeId;
    }
    
    public String getPassword() {
        return password;
    }
    
    public void setPassword(String password) {
        this.password = password;
    }
    
    @Override
    public String toString() {
        return "Employee{" +
               "name='" + name + '\'' +
               ", employeeId=" + employeeId +
               '}';
    }
}
