/*
 * RecordUnpaidDuesController class manages the UI for recording unpaid dues for an employee.
 * It communicates with the server through a REST API to record the dues amount associated with a specific employee.
 */

package com.Infotrixs.Payroll_System.PayrollUI.AdminDashboard;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.util.Duration;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

public class RecordUnpaidDuesController {

    @FXML
    private TextField EmployeeIdTextField;

    @FXML
    private TextField DuesTextField;

    @FXML
    private Label errorLabel;

    /*
     * Handles the action event triggered by the "Record Dues" button.
     * Validates inputs, sends a request to the server to record unpaid dues, and displays the result or error message.
     */
    @FXML
    private void handleRecordDues(ActionEvent event) {
        if (!validateInputs()) {
            return;
        }

        int employeeId = Integer.parseInt(EmployeeIdTextField.getText());
        double duesAmount = Double.parseDouble(DuesTextField.getText());

        String apiUrl = "http://localhost:8080/admin/record-previous-due-of-an-employee/employee-id/" + employeeId + "/amount/" + duesAmount;

        try {
            RestTemplate restTemplate = new RestTemplate();
            ResponseEntity<?> responseEntity = restTemplate.exchange(apiUrl, HttpMethod.PUT, null, String.class);

            if (responseEntity.getStatusCode().is2xxSuccessful()) {
                String response = (String) responseEntity.getBody();
                displayError(new Exception(response));
            } else {
                String errorResponse = (String) responseEntity.getBody();
                displayError(new Exception(errorResponse));
            }

        } catch (Exception e) {
            displayError(new Exception("An error occurred during due recording: " + e.getMessage()));
        }
    }

    /*
     * Validates the inputs entered by the user in the Employee ID and Dues Amount text fields.
     * Checks for empty fields and ensures that numeric values are provided.
     */
    private boolean validateInputs() {
        String employeeIdText = EmployeeIdTextField.getText().trim();
        String duesAmountText = DuesTextField.getText().trim();

        // Check if fields are empty
        if (employeeIdText.isEmpty() || duesAmountText.isEmpty()) {
            displayError(new Exception("Please fill in all required fields."));
            return false;
        }

        // Validate if inputs are numeric
        if (!isNumeric(employeeIdText) || !isNumeric(duesAmountText)) {
            displayError(new Exception("Employee ID and Dues Amount must be numeric values."));
            return false;
        }

        return true;
    }

    /*
     * Checks if a given string is a numeric value.
     */
    private boolean isNumeric(String str) {
        // Check if a string contains only numeric characters
        return str.matches("\\d+(\\.\\d+)?");
    }

    /*
     * Displays an error message on the UI, with customizable styling and a fade-out effect after a specified duration.
     */
    private void displayError(Exception exception) {
        String errorMessage;
        if (exception != null) {
            errorMessage = exception.getMessage();
        } else {
            errorMessage = "";
        }

        errorLabel.setText(errorMessage);

        errorLabel.setStyle("-fx-text-fill: red; -fx-font-style: italic;");

        boolean hasError = errorMessage != null && !errorMessage.isEmpty();
        errorLabel.setVisible(hasError);
        errorLabel.setManaged(hasError);

        Duration duration = Duration.seconds(1);
        KeyFrame keyFrame = new KeyFrame(duration, event -> {
            errorLabel.setText("");
        });

        Timeline timeline = new Timeline(keyFrame);
        timeline.play();
    }
}
