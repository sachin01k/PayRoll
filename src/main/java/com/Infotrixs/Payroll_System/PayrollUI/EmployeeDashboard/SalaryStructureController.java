/*
 * SalaryStructureController class is the controller for the Salary Structure window in the Employee Dashboard.
 * It fetches and displays the salary structure of the logged-in employee by making an API call to the Spring Boot backend.
 * The displayed salary details include the employee name, salary ID, base salary, house rent allowance, convenience allowance,
 * insurance, in-hand salary, and provident fund (PF) contribution.
 */
package com.Infotrixs.Payroll_System.PayrollUI.EmployeeDashboard;

import com.Infotrixs.Payroll_System.DTOs.Outgoing.SalaryReplica;
import com.Infotrixs.Payroll_System.PayrollUI.LoginController;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

public class SalaryStructureController {

    @FXML
    private Label employeeNameLabel;

    @FXML
    private Label salaryIdLabel;

    @FXML
    private Label baseLabel;

    @FXML
    private Label houseRestAllowLabel;

    @FXML
    private Label convenienceAllowLabel;

    @FXML
    private Label insuranceLabel;

    @FXML
    private Label inHandLabel;

    @FXML
    private Label pfLabel;

    /*
     * This method is automatically called when the FXML file is loaded.
     * It initializes the salary details by fetching them from the backend using the Employee ID obtained from LoginController.
     */
    public void initialize() {
        // Fetch admin details and set them to the labels
        LoginController loginController = new LoginController();
        int employeeId = loginController.getempId(); // Replace with the actual admin ID
        initData(employeeId);
    }

    /*
     * This method fetches employee salary details from the backend using the provided Employee ID.
     * It makes an API call and sets the retrieved details to the respective labels in the UI.
     */
    public void initData(int employeeId) {
        // Make the API call to see employee salary structure
        String apiUrl = "http://localhost:8080/employee/see-salary-structure/employee-id/" + employeeId;

        try {
            RestTemplate restTemplate = new RestTemplate();
            ResponseEntity<SalaryReplica> responseEntity = restTemplate.getForEntity(apiUrl, SalaryReplica.class);

            // Handle the response
            if (responseEntity.getStatusCode().is2xxSuccessful()) {
                // Salary details successfully retrieved
                SalaryReplica salaryReplica = responseEntity.getBody();

                // Set the details to the labels
                employeeNameLabel.setText(salaryReplica.getEmployeeName());
                salaryIdLabel.setText(String.valueOf(salaryReplica.getSalaryId()));
                baseLabel.setText(String.valueOf(salaryReplica.getBase()));
                houseRestAllowLabel.setText(String.valueOf(salaryReplica.getHouseRentAllow()));
                convenienceAllowLabel.setText(String.valueOf(salaryReplica.getConvenienceAllow()));
                insuranceLabel.setText(String.valueOf(salaryReplica.getInsurance()));
                inHandLabel.setText(String.valueOf(salaryReplica.getInHand()));
                pfLabel.setText(String.valueOf(salaryReplica.getPF()));

            } else {
                // Failed to retrieve salary details
                String errorResponse = responseEntity.getBody().toString();
                System.out.println("Failed to retrieve salary details. Error: " + errorResponse);

                // Handle the error response as needed
            }

        } catch (Exception e) {
            // Handle exceptions
            System.out.println("An error occurred while fetching salary details: " + e.getMessage());
        }
    }
}
