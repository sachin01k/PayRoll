/*
 * EmployeeProfileController class is the controller for the Employee Profile window.
 * It fetches and displays employee details by making an API call to the Spring Boot backend.
 * The employee details include information such as employee ID, name, email, phone number, username,
 * department, employment level, designation, bank account number, IFSC code, and account access status.
 */
package com.Infotrixs.Payroll_System.PayrollUI.EmployeeDashboard;

import com.Infotrixs.Payroll_System.DTOs.Outgoing.EmployeeDetails;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import com.Infotrixs.Payroll_System.PayrollUI.LoginController;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

public class EmployeeProfileController {

    @FXML
    private Label employeeIdLabel;

    @FXML
    private Label nameLabel;

    @FXML
    private Label emailLabel;

    @FXML
    private Label phoneLabel;

    @FXML
    private Label usernameLabel;

    @FXML
    private Label departmentLabel;

    @FXML
    private Label EmploymentLevelLabel;

    @FXML
    private Label DesignationLabel;

    @FXML
    private Label bankAccountNumberLabel;

    @FXML
    private Label ifscLabel;

    @FXML
    private Label accountAccessLabel;

    /*
     * This method is automatically called when the FXML file is loaded.
     * It initializes the employee details by fetching them from the backend using the Employee ID obtained from LoginController.
     */
    public void initialize() {
        // Fetch admin details and set them to the labels
        LoginController loginController = new LoginController();
        int employeeId = loginController.getempId(); // Replace with the actual admin ID
        initData(employeeId);
    }

    /*
     * This method fetches employee details from the backend using the provided Employee ID.
     * It makes an API call and sets the retrieved details to the respective labels in the UI.
     */
    public void initData(int employeeId) {
        // Make the API call to see employee account details
        String apiUrl = "http://localhost:8080/employee/see-account-details/employee-id/" + employeeId;

        try {
            RestTemplate restTemplate = new RestTemplate();
            ResponseEntity<EmployeeDetails> responseEntity = restTemplate.getForEntity(apiUrl, EmployeeDetails.class);

            // Handle the response
            if (responseEntity.getStatusCode().is2xxSuccessful()) {
                // Employee details successfully retrieved
                EmployeeDetails employeeDetails = responseEntity.getBody();

                employeeIdLabel.setText(String.valueOf(employeeDetails.getEmpId()));
                nameLabel.setText(employeeDetails.getName());
                emailLabel.setText(employeeDetails.getEmail());
                phoneLabel.setText(employeeDetails.getPhone());
                usernameLabel.setText(employeeDetails.getUsername());
                departmentLabel.setText(employeeDetails.getDepartment());
                EmploymentLevelLabel.setText(employeeDetails.getEmploymentLevel());
                DesignationLabel.setText(employeeDetails.getDesignation());
                bankAccountNumberLabel.setText(employeeDetails.getBankAccNo());
                ifscLabel.setText(employeeDetails.getIfsc());
                accountAccessLabel.setText(employeeDetails.getAccountAccess());

            } else {
                // Failed to retrieve employee details
                String errorResponse = responseEntity.getBody().toString();
                System.out.println("Failed to retrieve employee details. Error: " + errorResponse);

                // Handle the error response as needed
            }

        } catch (Exception e) {
            // Handle exceptions
            System.out.println("An error occurred while fetching employee details: " + e.getMessage());
        }
    }

}
