package com.vismaInternshipTest.MeetingInternshipTest;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


public class MeetingManualDataInput {

   // private static final String FILE_NAME = "meetings.json";

    // Main driver method
    public static void main(String[] args)
    {
        // Creating an employee object with it's attributes
        // set
        List<Employee> employee = getEmployees();
        ObjectMapper mapper = new ObjectMapper();
 
        // Try block to check for exceptions
        try {
 
            // Serializes emp object to a file employee.json
            mapper.writeValue(
                new File(
                    "D:/OPERA FORCED DOWNLOADS/Meeting-Internship-Test/Meeting-Internship-Test/src/main/resources/meetings.json"),
                employee);
 
            // Deserializes emp object in json string format
            String empJson
                = mapper.writeValueAsString(employee);
            System.out.println(
                "The employee object in json format:"
                + empJson);
            System.out.println(
                "Updating the dept of emp object");
 
            // Update deptName attribute of emp object
            ((Employee) employee).setDeptName("Devops");
            System.out.println(
                "Deserializing updated emp json ");
 
            // Reading from updated json and deserializes it
            // to emp object
            Employee updatedEmp = mapper.readValue(
                mapper.writeValueAsString(employee),
                Employee.class);
 
            // Print and display the updated employee object
            System.out.println("Updated emp object is "
                               + updatedEmp.toString());
        }
  
        // Catch block to handle exceptions
 
        // Catch block 1
        // Handling JsonGenerationException
        catch (JsonGenerationException e) {
 
            // Display the exception along with line number
            // using printStackTrace() method
            e.printStackTrace();
        }
 
        // Catch block 2
        // Handling JsonmappingException
        catch (JsonMappingException e) {
 
            // Display the exception along with line number
            // using printStackTrace() method
            e.printStackTrace();
        }
 
        // Catch block 3
        // handling generic I/O exceptions
        catch (IOException e) {
 
            // Display the exception along with line number
            // using printStackTrace() method
            e.printStackTrace();
        }
    }
 


    
    private static List<Employee> getEmployees() {
        // Creating objects of Employee class
        Employee emp1 = new Employee();
        emp1.setId("30233");
        emp1.setName("Bat");
        emp1.setDeptName("EssE");
        emp1.setRating(34);
        emp1.setSalary(1500.00);
    
        List<Employee> employees = new ArrayList<>();
        employees.add(emp1);
    
        return employees;
    }

}
// Class 2
// Helper class
class Employee {
 
    // Member variables of this class
    private String id;
    private String name;
    private String deptName;
    private double salary;
    private int rating;
 
    // Member methods of this class
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getDeptName() { return deptName; }
    public void setDeptName(String deptName)
    {
        // This keyword refers to current instance
        this.deptName = deptName;
    }
 
    public double getSalary() { return salary; }
    public void setSalary(double salary)
    {
        this.salary = salary;
    }
    public int getRating() { return rating; }
    public void setRating(int rating)
    {
        this.rating = rating;
    }
 
    @Override public String toString()
    {
        return "Employee [id=" + id + ", name=" + name
            + ", deptName=" + deptName + ", salary="
            + salary + ", rating=" + rating + "]";
    }
}