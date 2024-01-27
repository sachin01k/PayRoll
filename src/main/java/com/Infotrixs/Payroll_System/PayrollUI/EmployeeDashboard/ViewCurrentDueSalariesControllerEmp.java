/*
 * ViewCurrentDueSalariesControllerEmp class is the controller for the window displaying current due salary details in the Employee Dashboard.
 * It fetches and displays the due salary details of the logged-in employee by making an API call to the Spring Boot backend.
 * The displayed details include the employee name, salary ID, employee ID, base salary, house rent allowance, convenience allowance,
 * insurance, in-hand salary, previous dues, deduction for unpaid leaves, unpaid leaves, net pay, payment status, department,
 * employment level, designation, and provident fund (PF) contribution.
 */
package com.Infotrixs.Payroll_System.PayrollUI.EmployeeDashboard;

import com.Infotrixs.Payroll_System.DTOs.Outgoing.DueSalaryDetails;
import com.Infotrixs.Payroll_System.PayrollUI.LoginController;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

public class ViewCurrentDueSalariesControllerEmp {

    @FXML
    private Label employeeNameLabel;

    @FXML
    private Label salaryIdLabel;

    @FXML
    private Label employeeIdLabel;

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
    private Label previousDuesLabel;

    @FXML
    private Label deductionForUnpaidLeavesLabel;

    @FXML
    private Label unpaidLeavesLabel;

    @FXML
    private Label netPayLabel;

    @FXML
    private Label paymentStatusLabel;

    @FXML
    private Label departmentLabel;

    @FXML
    private Label employmentLevelLabel;

    @FXML
    private Label designationLabel;

    @FXML
    private Label pfLabel;

    /*
     * This method is automatically called when the FXML file is loaded.
     * It initializes the due salary details by fetching them from the backend using the Employee ID obtained from LoginController.
     */
    public void initialize() {
        // Fetch admin details and set them to the labels
        LoginController loginController = new LoginController();
        int employeeId = loginController.getempId(); // Replace with the actual admin ID
        initData(employeeId);
    }

    /*
     * This method fetches employee due salary details from the backend using the provided Employee ID.
     * It makes an API call and sets the retrieved details to the respective labels in the UI.
     */
    public void initData(int employeeId) {
        // Make the API call to see due salary details
        String apiUrl = "http://localhost:8080/employee/see-due-salary-details/employee-id/" + employeeId;

        try {
            RestTemplate restTemplate = new RestTemplate();
            ResponseEntity<DueSalaryDetails> responseEntity = restTemplate.getForEntity(apiUrl, DueSalaryDetails.class);

            // Handle the response
            if (responseEntity.getStatusCode().is2xxSuccessful()) {
                // Due salary details successfully retrieved
                DueSalaryDetails dueSalaryDetails = responseEntity.getBody();

                // Set the details to the labels
                employeeNameLabel.setText(dueSalaryDetails.getEmployeeName());
                salaryIdLabel.setText(String.valueOf(dueSalaryDetails.getSalaryId()));
                employeeIdLabel.setText(String.valueOf(dueSalaryDetails.getEmployeeId()));
                baseLabel.setText(String.valueOf(dueSalaryDetails.getBase()));
                houseRestAllowLabel.setText(String.valueOf(dueSalaryDetails.getHouseRentAllow()));
                convenienceAllowLabel.setText(String.valueOf(dueSalaryDetails.getConvenienceAllow()));
                insuranceLabel.setText(String.valueOf(dueSalaryDetails.getInsurance()));
                inHandLabel.setText(String.valueOf(dueSalaryDetails.getInHand()));
                previousDuesLabel.setText(String.valueOf(dueSalaryDetails.getPreviousDues()));
                deductionForUnpaidLeavesLabel.setText(String.valueOf(dueSalaryDetails.getDeductionForUnpaidLeaves()));
                unpaidLeavesLabel.setText(String.valueOf(dueSalaryDetails.getUnpaidLeaves()));
                netPayLabel.setText(String.valueOf(dueSalaryDetails.getNetPay()));
                paymentStatusLabel.setText(dueSalaryDetails.getPaymentStatus());
                departmentLabel.setText(dueSalaryDetails.getDepartment());
                employmentLevelLabel.setText(dueSalaryDetails.getEmploymentLevel());
                designationLabel.setText(dueSalaryDetails.getDesignation());
                pfLabel.setText(String.valueOf(dueSalaryDetails.getPF()));

            } else {
                // Failed to retrieve due salary details
                String errorResponse = responseEntity.getBody().toString();
                System.out.println("Failed to retrieve due salary details. Error: " + errorResponse);

                // Handle the error response as needed
            }

        } catch (Exception e) {
            // Handle exceptions
            System.out.println("An error occurred while fetching due salary details: " + e.getMessage());
        }
    }
}