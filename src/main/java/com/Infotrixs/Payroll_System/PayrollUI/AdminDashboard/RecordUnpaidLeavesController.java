/*
 * RecordUnpaidLeavesController class manages the UI for recording unpaid leaves for an employee.
 * It communicates with the server through a REST API to record the number of unpaid leaves associated with a specific employee.
 */

package com.Infotrixs.Payroll_System.PayrollUI.AdminDashboard;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.util.Duration;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

public class RecordUnpaidLeavesController {

    @FXML
    private TextField EmployeeIdTextField;

    @FXML
    private TextField newUnpaidLeavesField;

    @FXML
    private Label errorLabel;

    /*
     * Handles the action event triggered by the "Record Unpaid Leave" button.
     * Validates inputs, sends a request to the server to record unpaid leaves, and displays the result or error message.
     */
    @FXML
    private void handleRecordUnpaidLeave() {
        String employeeIdText = EmployeeIdTextField.getText().trim();
        String leavesText = newUnpaidLeavesField.getText().trim();

        if (employeeIdText.isEmpty() || leavesText.isEmpty()) {
            displayError(new Exception("Please fill in all required fields."));
            return;
        }

        if (!isNumeric(employeeIdText) || !isNumeric(leavesText)) {
            displayError(new Exception("Employee ID and Leaves must be numeric values."));
            return;
        }

        int employeeId = Integer.parseInt(employeeIdText);
        int leaves = Integer.parseInt(leavesText);

        handleRecordUnpaidLeaveApiCall(employeeId, leaves);
    }

    /*
     * Checks if a given string is a numeric value.
     */
    private boolean isNumeric(String str) {
        return str.matches("\\d+");
    }

    /*
     * Sends a request to the server to record unpaid leaves for a specific employee.
     * Displays the result or error message based on the server response.
     */
    private void handleRecordUnpaidLeaveApiCall(int employeeId, int leaves) {
        String baseUrl = "http://localhost:8080/admin";

        RestTemplate restTemplate = new RestTemplate();

        String recordUnpaidLeaveUrl = baseUrl + "/record-unpaid-leave/employee-id/" + employeeId + "/leaves/" + leaves;

        try {
            ResponseEntity<String> responseEntity = restTemplate.exchange(
                    recordUnpaidLeaveUrl,
                    HttpMethod.PUT,
                    null,
                    String.class
            );

            if (responseEntity.getStatusCode().is2xxSuccessful()) {
                displayError(new Exception(responseEntity.getBody()));
            } else {
                displayError(new Exception("Error: " + responseEntity.getBody()));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
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
