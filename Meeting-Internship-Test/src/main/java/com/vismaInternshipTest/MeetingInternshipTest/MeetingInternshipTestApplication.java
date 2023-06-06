package com.vismaInternshipTest.MeetingInternshipTest;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;

public class MeetingInternshipTestApplication {
    //Getting directory to the json file where data will be stored
    private static final String FILE_NAME = "D:/OPERA FORCED DOWNLOADS/Meeting-Internship-Test/Meeting-Internship-Test/src/main/resources/meetings.json";

    public static void main(String[] args) {
        //Start menu
        Scanner scanner = new Scanner(System.in);
        System.out.println("Welcome to the Meeting Management System!");

        boolean continueProgram = true;
        while (continueProgram) {
            System.out.println("Please choose an option:");
            System.out.println("1. Add new employee information");
            System.out.println("2. Delete an employee information");
            System.out.println("3. Add employee to existing employee");
            System.out.println("4. Remove an employee");
            System.out.println("5. Filter employees");
            System.out.println("6. Exit");
            System.out.print("Your choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline character
            //Choice for activating one of the functions
            switch (choice) {
                case 1:
                    addMeeting(scanner);
                    break;
                case 2:
                    deleteMeeting(scanner);
                    break;
                case 3:
                    addEmployee(scanner);
                    break;
                case 4:
                    removeEmployee(scanner);
                    break;
                case 5:
                    filterEmployees(scanner);
                    break;
                case 6:
                    continueProgram = false;
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
                    break;
            }
        }
//Goodbye message
        System.out.println("Have a nice day. Hope to see you soon.");
    }

    private static void filterEmployees(Scanner scanner) {
        List<Employee> employees = readEmployeesFromJson();
    
        if (employees.isEmpty()) {
            System.out.println("No employees found.");
            return;
        }
    //Choice for activating one of the filtering functions
        System.out.println("Filter options:");
        System.out.println("1. Filter by description");
        System.out.println("2. Filter by responsible person");
        System.out.println("3. Filter by category");
        System.out.println("4. Filter by type");
        System.out.println("5. Filter by date");
        System.out.println("6. Filter by number of employees");
        System.out.println("7. Cancel filtering");
        System.out.print("Your choice: ");
    
        int choice = scanner.nextInt();
        scanner.nextLine(); // Consume newline character
    

        //Function for different types of filtering
        List<Employee> filteredEmployees = new ArrayList<>();
        switch (choice) {
            case 1:
            //Finds all the employees datas that have description matching to user's input
                System.out.print("Enter description to filter by: ");
                String descriptionFilter = scanner.nextLine();
                for (Employee employee : employees) {
                    if (employee.getDescription().equalsIgnoreCase(descriptionFilter)) {
                        filteredEmployees.add(employee);
                    }
                }
                break;
            case 2:
            //Finds all the employees datas that have responsiblePerson matching to user's input
                System.out.print("Enter responsible person to filter by: ");
                String responsiblePersonFilter = scanner.nextLine();
                for (Employee employee : employees) {
                    if (employee.getResponsiblePerson().equalsIgnoreCase(responsiblePersonFilter)) {
                        filteredEmployees.add(employee);
                    }
                }
                break;
            case 3:
            //Finds all the employees datas that have categories matching to user's input
                System.out.print("Enter category to filter by: ");
                String categoryFilter = scanner.nextLine();
                for (Employee employee : employees) {
                    if (employee.getCategory().equalsIgnoreCase(categoryFilter)) {
                        filteredEmployees.add(employee);
                    }
                }
                break;
            case 4:
            ////Finds all the employees datas that have types matching to user's input
                System.out.print("Enter type to filter by: ");
                String typeFilter = scanner.nextLine();
                for (Employee employee : employees) {
                    if (employee.getType().equalsIgnoreCase(typeFilter)) {
                        filteredEmployees.add(employee);
                    }
                }
                break;
            case 5:
            //Finds all the employees datas that have set of time matching to user's input
                System.out.print("Enter start date to filter by (yyyy-mm-dd): ");
                String startDateFilter = scanner.nextLine();
                System.out.print("Enter end date to filter by (yyyy-mm-dd): ");
                String endDateFilter = scanner.nextLine();
                for (Employee employee : employees) {
                    if (employee.getStartDate().equals(startDateFilter) && employee.getEndDate().equals(endDateFilter)) {
                        filteredEmployees.add(employee);
                    }
                }
                break;
            case 6:
            //Finds all the meetings that has the minimum amount of given employees
                System.out.print("Enter the minimum number of employees: ");
                int minOccurrences = scanner.nextInt();
                scanner.nextLine(); // Consume newline character
    
                Map<String, Integer> employeeOccurrences = new HashMap<>();
                for (Employee employee : employees) {
                    String key = employee.getDescription() + "|" +
                                 employee.getResponsiblePerson();
    
                    int occurrences = employeeOccurrences.getOrDefault(key, 0);
                    employeeOccurrences.put(key, occurrences + 1);
                }
    
                for (Employee employee : employees) {
                    String key = employee.getDescription() + "|" +
                                 employee.getResponsiblePerson();
    
                    if (employeeOccurrences.get(key) >= minOccurrences &&
                        employee.getResponsiblePerson().equals(employee.getName())) {
                        if (!filteredEmployees.contains(employee)) {
                            filteredEmployees.add(employee);
                        }
                    }
                }
                break;
            case 7:
                System.out.println("Exiting...");
                break;
            default:
                System.out.println("Invalid choice. Please try again.");
                return;
        }
    
        if (filteredEmployees.isEmpty()) {
            System.out.println("No employees found matching the filter criteria.");
        } else {
            System.out.println("Filtered employees:");
            for (Employee employee : filteredEmployees) {
                System.out.println(employee);
            }
        }
    }

//Allows to add a meeting as employee, which will be later used to add on it to increase employee amount on the meeting 
    private static void addMeeting(Scanner scanner) {
        List<Employee> meeting = readEmployeesFromJson();
    
        List<Employee> newMeetings = getEmployees(scanner);
    
        for (Employee newMeeting : newMeetings) {
            boolean isDuplicate = false;
    
            for (Employee existingMeeting : meeting) {
                if (existingMeeting.getName().equals(newMeeting.getName()) &&
                    existingMeeting.getResponsiblePerson().equals(newMeeting.getResponsiblePerson()) &&
                    existingMeeting.getDescription().equals(newMeeting.getDescription()) &&
                    existingMeeting.getCategory().equals(newMeeting.getCategory()) &&
                    existingMeeting.getType().equals(newMeeting.getType())) {
                    if (existingMeeting.getStartDate().equals(newMeeting.getStartDate()) &&
                        existingMeeting.getEndDate().equals(newMeeting.getEndDate()) &&
                        existingMeeting.getResponsiblePerson().equals(newMeeting.getResponsiblePerson())) {
                        System.out.println("An employee with the same data already exists.");
                        isDuplicate = true;
                        break;
                    }
                }
            }
    
            if (!isDuplicate) {
                meeting.add(newMeeting);
                writeEmployeesToJson(meeting);
        System.out.println("Employee information added successfully!");
            }
        }
    
        
    }
    //removes an employee by the chosen ID
    private static void removeEmployee(Scanner scanner) {
        List<Employee> employees = readEmployeesFromJson();
    
        if (employees.isEmpty()) {
            System.out.println("No employees found.");
            return;
        }
    
        System.out.println("List of employees:");
        for (Employee employee : employees) {
            System.out.println(employee);
        }
    
        System.out.print("Enter the ID of the employee to remove: ");
        String employeeId = scanner.nextLine();
    
        Employee employeeToRemove = null;
        for (Employee employee : employees) {
            if (employee.getId().equals(employeeId)) {
                employeeToRemove = employee;
                break;
            }
        }
    
        if (employeeToRemove == null) {
            System.out.println("No employee found with the specified ID.");
            return;
        }
    
        System.out.println("Employee found:");
        System.out.println(employeeToRemove);
    
        System.out.print("Enter 'Y' to confirm removal or any other key to cancel: ");
        String confirmation = scanner.nextLine();
    
        if (confirmation.equalsIgnoreCase("Y")) {
            String employeeName = employeeToRemove.getName();
            String responsiblePerson = employeeToRemove.getResponsiblePerson();
    
            if (employeeName.equalsIgnoreCase(employeeToRemove.getName()) &&
                    responsiblePerson.equalsIgnoreCase(employeeToRemove.getResponsiblePerson())) {
                System.out.println("Name and responsible person match. Cannot remove this employee.");
                return;
            }
    
            employees.remove(employeeToRemove);
            writeEmployeesToJson(employees);
            System.out.println("Employee removed successfully!");
        } else {
            System.out.println("Removal canceled.");
        }
    }
    
    
    
    
    
    //Deletes everyone correspinding to the imputed representing person and his start date and end date
    private static void deleteMeeting(Scanner scanner) {
        List<Employee> meetings = readEmployeesFromJson();

        if (meetings.isEmpty()) {
            System.out.println("No employees found.");
            return;
        }

        System.out.println("Existing employees/meetings:");
        for (Employee meeting : meetings) {
            System.out.println(meeting);
        }

        System.out.print("Enter the responsible person of the employee/meeting to delete: ");
        String responsiblePersonToDelete = scanner.nextLine();

        System.out.print("Enter the start date of the employee/meeting to delete: ");
        String startDateToDelete = scanner.nextLine();

        System.out.print("Enter the start end of the employee/meeting to delete: ");
        String EndDateToDelete = scanner.nextLine();

        List<Employee> matchingMeeting = new ArrayList<>();
        for (Employee meeting : meetings) {
            if (meeting.getResponsiblePerson().equals(responsiblePersonToDelete) &&
            meeting.getStartDate().equals(startDateToDelete) &&
            meeting.getEndDate().equals(EndDateToDelete)) {
                matchingMeeting.add(meeting);
            }
        }

        if (matchingMeeting.isEmpty()) {
            System.out.println("No matching employees found.");
            return;
        }

        System.out.println("Matching employees:");
        for (Employee meeting : matchingMeeting) {
            System.out.println(meeting);
        }

        System.out.print("Enter 'Y' to confirm deletion or any other key to cancel: ");
        String confirmation = scanner.nextLine();

        if (confirmation.equalsIgnoreCase("Y")) {
            meetings.removeAll(matchingMeeting);
            writeEmployeesToJson(meetings);
            System.out.println("Employee information deleted successfully!");
        } else {
            System.out.println("Deletion canceled.");
        }
    }
//adds employee to the employee who has matching name and respresting person as it means its a meeting leader
//which allows the employee exist in a "meeting" without being himself as meeting leader
    private static void addEmployee(Scanner scanner) {
        List<Employee> employees = readEmployeesFromJson();
    
        if (employees.isEmpty()) {
            System.out.println("No employees found.");
            return;
        }
    
        System.out.println("Existing employees:");
        for (Employee employee : employees) {
            System.out.println(employee);
        }
    
        System.out.print("Enter the ID of the employee to add new employee information to: ");
        String employeeId = scanner.nextLine();
    
        Employee existingEmployee = null;
        for (Employee employee : employees) {
            if (employee.getId().equals(employeeId)) {
                existingEmployee = employee;
                break;
            }
        }
    
        if (existingEmployee == null) {
            System.out.println("No employee found with the specified ID.");
            return;
        }
    
        Employee newEmployee = new Employee();
    
        System.out.print("Enter new employee name: ");
        String employeeName = scanner.nextLine();
        newEmployee.setName(employeeName);
    
        // Copy data from existing employee
        newEmployee.setId(existingEmployee.getId());
        newEmployee.setResponsiblePerson(existingEmployee.getResponsiblePerson());
        newEmployee.setDescription(existingEmployee.getDescription());
        newEmployee.setCategory(existingEmployee.getCategory());
        newEmployee.setType(existingEmployee.getType());
        newEmployee.setStartDate(existingEmployee.getStartDate());
        newEmployee.setEndDate(existingEmployee.getEndDate());
    
        boolean isDuplicate = false;
    //checks if the data is the same to find out if the employee of that kind already exists
        for (Employee existingEmp : employees) {
            if (existingEmp.getName().equals(newEmployee.getName()) &&
                    existingEmp.getResponsiblePerson().equals(newEmployee.getResponsiblePerson()) &&
                    existingEmp.getDescription().equals(newEmployee.getDescription()) &&
                    existingEmp.getCategory().equals(newEmployee.getCategory()) &&
                    existingEmp.getType().equals(newEmployee.getType())) {
                if (existingEmp.getStartDate().equals(newEmployee.getStartDate()) &&
                        existingEmp.getEndDate().equals(newEmployee.getEndDate()) &&
                        existingEmp.getResponsiblePerson().equals(newEmployee.getResponsiblePerson())) {
                    System.out.println("An employee with the same data already exists.");
                    isDuplicate = true;
                    break;
                }
            }
        }
    
        if (!isDuplicate) {
            employees.add(newEmployee);
            writeEmployeesToJson(employees);
            System.out.println("New employee information added successfully!");
        }
    }
    
//Adds employee data into the .json file
    private static List<Employee> getEmployees(Scanner scanner) {
        List<Employee> employees = new ArrayList<>();
        boolean continueAddingLists = true;
    
        while (continueAddingLists) {
            System.out.println("Enter the number of employees in the list (0 to stop): ");
            String input = scanner.nextLine();
    
            try {
                int numEmployees = Integer.parseInt(input);
    
                if (numEmployees == 0) {
                    continueAddingLists = false;
                }
    
                for (int j = 0; j < numEmployees; j++) {
                    Employee emp = new Employee();
    
                    System.out.println("Enter employee name: ");
                    String employeeName = scanner.nextLine();
                    emp.setName(employeeName);
                    emp.setResponsiblePerson(employeeName);
    
                    System.out.println("Enter employee description: ");
                    String employeeDescription = scanner.nextLine();
                    emp.setDescription(employeeDescription);
    
                    System.out.println("Enter employee category (CodeMonkey / Hub / Short / TeamBuilding): ");
                    String employeeCategory = scanner.nextLine();
                    while (!isValidCategory(employeeCategory)) {
                        System.out.println("Invalid category. Please enter a valid category: ");
                        employeeCategory = scanner.nextLine();
                    }
                    emp.setCategory(employeeCategory);
    
                    System.out.println("Enter employee type (Live / InPerson): ");
                    String employeeType = scanner.nextLine();
                    while (!isValidType(employeeType)) {
                        System.out.println("Invalid type. Please enter a valid type: ");
                        employeeType = scanner.nextLine();
                    }
                    emp.setType(employeeType);
    
                    System.out.println("Enter employee start date (yyyy-mm-dd): ");
                    String employeeStartDate = scanner.nextLine();
                    while (!isValidDateFormat(employeeStartDate)) {
                        System.out.println("Invalid date format. Please enter a valid date (yyyy-mm-dd): ");
                        employeeStartDate = scanner.nextLine();
                    }
                    emp.setStartDate(employeeStartDate);
    
                    System.out.println("Enter employee start end (yyyy-mm-dd): ");
                    String employeeEndDate = scanner.nextLine();
                    while (!isValidDateFormat(employeeEndDate)) {
                        System.out.println("Invalid date format. Please enter a valid date (yyyy-mm-dd): ");
                        employeeEndDate = scanner.nextLine();
                    }
                    emp.setEndDate(employeeEndDate);
    
                    employees.add(emp);
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid number.");
            }
        }
    
        return employees;
    }
    
//Checks if user inputs correct form of dates
private static boolean isValidDateFormat(String date) {
    String pattern = "\\d{4}-\\d{2}-\\d{2}";
    return date.matches(pattern);
}
    //Checks if user inputs the right catergory
    private static boolean isValidCategory(String category) {
        // Define the valid categories
        String[] validCategories = { "CodeMonkey", "Hub", "Short", "TeamBuilding" };
        for (String validCategory : validCategories) {
            if (validCategory.equalsIgnoreCase(category)) {
                return true;
            }
        }
        return false;
    }
     //Checks if user inputs the right type
    private static boolean isValidType(String type) {
        // Define the valid types
        String[] validTypes = { "Live", "InPerson" };
        for (String validType : validTypes) {
            if (validType.equalsIgnoreCase(type)) {
                return true;
            }
        }
        return false;
    }
// reads employee data from a JSON file
    private static List<Employee> readEmployeesFromJson() {
        List<Employee> employees = new ArrayList<>();
        ObjectMapper mapper = new ObjectMapper();

        try (FileReader fileReader = new FileReader(new File(FILE_NAME))) {
            JsonNode jsonNode = mapper.readTree(fileReader);

            if (jsonNode.has("employees") && jsonNode.get("employees").isArray()) {
                ArrayNode employeesNode = (ArrayNode) jsonNode.get("employees");

                for (JsonNode employeeNode : employeesNode) {
                    Employee employee = mapper.convertValue(employeeNode, Employee.class);
                    employees.add(employee);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return employees;
    }
// writes employee data into the main fail for data usage
    private static void writeEmployeesToJson(List<Employee> employees) {
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode rootNode = mapper.createObjectNode();
        ArrayNode employeesNode = mapper.createArrayNode();

        for (Employee employee : employees) {
            JsonNode employeeNode = mapper.valueToTree(employee);
            employeesNode.add(employeeNode);
        }

        rootNode.set("employees", employeesNode);

        try (FileWriter fileWriter = new FileWriter(new File(FILE_NAME))) {
            mapper.writeValue(fileWriter, rootNode);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
//stores the variables for the Employee
class Employee {
    private String name;
    private String id;
    private String responsiblePerson;
    private String description;
    private String category;
    private String type;
    private String startDate;
    private String EndDate;

    public Employee() {
        // Generate a unique ID for the employee
        this.id = generateRandomId();
    }

    private String generateRandomId() {
        Random random = new Random();
        StringBuilder builder = new StringBuilder();

        // Generate a random 6-digit number
        int randomNumber = 100000 + random.nextInt(900000);
        builder.append(randomNumber);

        return "EMP" + builder.toString();
    }

    public void setId(String string) {
    }

    public String getName() {
        return name;
    }

    public String getId() {
        return id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getResponsiblePerson() {
        return responsiblePerson;
    }

    public void setResponsiblePerson(String responsiblePerson) {
        this.responsiblePerson = responsiblePerson;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return EndDate;
    }

    public void setEndDate(String EndDate) {
        this.EndDate = EndDate;
    }
//returns and sends the data in string form
    @Override
    public String toString() {
        return "Employee [id=" + id + ", name=" + name + ", responsiblePerson=" + responsiblePerson
                + ", description=" + description + ", category=" + category + ", type=" + type + ", startDate=" + startDate
                + ", EndDate=" + EndDate + "]";
    }
}