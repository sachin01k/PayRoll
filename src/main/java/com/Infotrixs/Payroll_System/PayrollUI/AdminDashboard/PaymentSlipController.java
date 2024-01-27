/*
 * PaymentSlipController class manages the UI and logic for generating and displaying payment slips.
 * It communicates with the server through a REST API to generate payslips for employees.
 */

package com.Infotrixs.Payroll_System.PayrollUI.AdminDashboard;

import com.Infotrixs.Payroll_System.DTOs.Outgoing.PaySlipReplica;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.util.Duration;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

public class PaymentSlipController {

    @FXML
    private Label paySlipIdLabel;

    @FXML
    private Label employeeIdLabel;

    @FXML
    private Label employeeNameLabel;

    @FXML
    private Label departmentLabel;

    @FXML
    private Label employmentLevelLabel;

    @FXML
    private Label designationLabel;

    @FXML
    private Label baseLabel;

    @FXML
    private Label houseRestAllowLabel;

    @FXML
    private Label convenienceAllowLabel;

    @FXML
    private Label pfLabel;

    @FXML
    private Label insuranceLabel;

    @FXML
    private Label inHandLabel;

    @FXML
    private Label prevDueLabel;

    @FXML
    private Label deductionForUnpaidLeavesLabel;

    @FXML
    private Label unpaidLeavesLabel;

    @FXML
    private Label paymentStatusLabel;

    @FXML
    private TextField employeeId;

    @FXML
    private Label errorLabel;

    /*
     * Setters for various attributes of a payment slip, updating corresponding UI labels with the provided values.
     * These methods are responsible for displaying employee details, salary components, and payment status on the UI.
     */

    public void setEmployeeId(String employeeId) {
        employeeIdLabel.setText(employeeId);
    }

    public void setEmployeeName(String employeeName) {
        employeeNameLabel.setText(employeeName);
    }

    public void setDepartment(String department) {
        departmentLabel.setText(department);
    }

    public void setEmploymentLevel(String employmentLevel) {
        employmentLevelLabel.setText(employmentLevel);
    }

    public void setDesignation(String designation) {
        designationLabel.setText(designation);
    }

    public void setBase(String base) {
        baseLabel.setText(base);
    }

    public void setHouseRestAllow(String houseRestAllow) {
        houseRestAllowLabel.setText(houseRestAllow);
    }

    public void setConvenienceAllow(String convenienceAllow) {
        convenienceAllowLabel.setText(convenienceAllow);
    }

    public void setPf(String pf) {
        pfLabel.setText(pf);
    }

    public void setInsurance(String insurance) {
        insuranceLabel.setText(insurance);
    }

    public void setInHand(String inHand) {
        inHandLabel.setText(inHand);
    }

    public void setPrevDue(String prevDue) {
        prevDueLabel.setText(prevDue);
    }

    public void setDeductionForUnpaidLeaves(String deductionForUnpaidLeaves) {
        deductionForUnpaidLeavesLabel.setText(deductionForUnpaidLeaves);
    }

    public void setUnpaidLeaves(String unpaidLeaves) {
        unpaidLeavesLabel.setText(unpaidLeaves);
    }

    public void setPaymentStatus(String paymentStatus) {
        paymentStatusLabel.setText(paymentStatus);
    }

    /*
     * Handles the "Generate" button action.
     * Validates input, makes a REST API call to generate a pay slip, and updates the UI with the result.
     */
    @FXML
    private void handleGenerateButtonAction() {
        String employeeIdText = employeeId.getText().trim();

        if (!employeeIdText.isEmpty()) {
            try {
                int employeeId = Integer.parseInt(employeeIdText);

                // Set the base URL of your API
                String baseUrl = "http://localhost:8080/admin";

                // Build the URL for the pay-salary-and-generate-pay-slip endpoint
                String apiUrl = baseUrl + "/pay-salary-and-generate-pay-slip-of-an-employee/employee-id/" + employeeId;

                // Create a RestTemplate instance
                RestTemplate restTemplate = new RestTemplate();

                // Make the API call
                ResponseEntity<PaySlipReplica> responseEntity = restTemplate.exchange(
                        apiUrl,
                        HttpMethod.POST,
                        null,
                        PaySlipReplica.class
                );

                if (responseEntity.getStatusCode().is2xxSuccessful()) {
                    PaySlipReplica paySlipReplica = responseEntity.getBody();

                    // Update UI labels with the payslip details

                    paySlipIdLabel.setText(String.valueOf(paySlipReplica.getPaySlipId()));
                    employeeIdLabel.setText(String.valueOf(paySlipReplica.getEmployeeId()));
                    employeeNameLabel.setText(paySlipReplica.getEmployeeName());
                    departmentLabel.setText(paySlipReplica.getDepartment());
                    employmentLevelLabel.setText(paySlipReplica.getEmploymentLevel());
                    designationLabel.setText(paySlipReplica.getDesignation());
                    baseLabel.setText(String.valueOf(paySlipReplica.getBase()));
                    houseRestAllowLabel.setText(String.valueOf(paySlipReplica.getHouseRestAllow()));
                    convenienceAllowLabel.setText(String.valueOf(paySlipReplica.getConvenienceAllow()));
                    pfLabel.setText(String.valueOf(paySlipReplica.getPf()));
                    insuranceLabel.setText(String.valueOf(paySlipReplica.getInsurance()));
                    inHandLabel.setText(String.valueOf(paySlipReplica.getInHand()));
                    prevDueLabel.setText(String.valueOf(paySlipReplica.getPrevDue()));
                    deductionForUnpaidLeavesLabel.setText(String.valueOf(paySlipReplica.getDeductionForUnpaidLeaves()));
                    unpaidLeavesLabel.setText(String.valueOf(paySlipReplica.getUnpaidLeaves()));
                    paymentStatusLabel.setText(paySlipReplica.getPaymentStatus());

                    displayError(new Exception("Payslip generated successfully!"));
                } else {
                    System.out.println("Failed to generate payslip");
                    displayError(new Exception("Error: " + responseEntity.getBody()));
                }
            } catch (NumberFormatException e) {
                displayError(new Exception("Invalid employee ID format"));
            } catch (Exception e) {
                displayError(new Exception("Failed to generate payslip"));
                e.printStackTrace();
            }
        } else {
            displayError(new Exception("Please enter an employee ID"));
        }
    }

    /*
     * Displays an error message on the UI label with optional customization of style.
     */
    private void displayError(Exception exception) {
        // Set the error message to the label
        String errorMessage = (exception != null) ? exception.getMessage() : "";


        errorLabel.setText(errorMessage);

        // Customize the style of the error label if needed
        errorLabel.setStyle("-fx-text-fill: red; -fx-font-style: italic;");

        // Set the visibility and managed properties of the label based on the presence of an error message
        boolean hasError = errorMessage != null && !errorMessage.isEmpty();
        errorLabel.setVisible(hasError);
        errorLabel.setManaged(hasError);

        // Use the Timeline and KeyFrame for a smooth fade-out effect after the specified duration
        Duration duration = Duration.seconds(1); // Adjust the duration as needed
        KeyFrame keyFrame = new KeyFrame(duration, event -> {
            // Remove the error label text after the specified duration
            errorLabel.setText("");
        });

        Timeline timeline = new Timeline(keyFrame);
        timeline.play();
    }



}
