/*
 * RegisterNewEmployeeController class handles the registration of new employees through the UI.
 * It communicates with the server using a REST API to add a new employee based on the provided information.
 */

package com.Infotrixs.Payroll_System.PayrollUI.AdminDashboard;

import com.Infotrixs.Payroll_System.DTOs.Incoming.NewEmployeeRequest;
import com.Infotrixs.Payroll_System.Enums.Department;
import com.Infotrixs.Payroll_System.Enums.EmploymentLevel;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.util.Duration;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

public class RegisterNewEmployeeController {

    @FXML
    private TextField nameField;

    @FXML
    private TextField emailField;

    @FXML
    private TextField phoneField;

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Label errorLabel;

    @FXML
    private ComboBox<String> departmentComboBox;

    @FXML
    private ComboBox<String> employmentLevelComboBox;

    @FXML
    private TextField bankAccNoField;

    @FXML
    private TextField ifscField;

    /*
     * Handles the registration of a new employee based on the provided information.
     * Sends a request to the server to add the new employee and displays the result or error message.
     */
    @FXML
    private void registerEmployee() {
        if (!validateInputFields()) {
            return;
        }

        // Extract information from input fields
        String name = nameField.getText();
        String username = usernameField.getText();
        String email = emailField.getText();
        String phone = phoneField.getText();
        String password = passwordField.getText();
        String department = departmentComboBox.getValue();
        String employmentLevel = employmentLevelComboBox.getValue();
        String bankAccNo = bankAccNoField.getText();
        String ifsc = ifscField.getText();

        // Create a NewEmployeeRequest object
        NewEmployeeRequest newEmployeeRequest = new NewEmployeeRequest();
        newEmployeeRequest.setName(name);
        newEmployeeRequest.setUsername(username);
        newEmployeeRequest.setEmail(email);
        newEmployeeRequest.setPhone(phone);
        newEmployeeRequest.setPassword(password);
        newEmployeeRequest.setDepartment(Department.valueOf(department));
        newEmployeeRequest.setEmploymentLevel(EmploymentLevel.valueOf(employmentLevel));
        newEmployeeRequest.setBankAccNo(bankAccNo);
        newEmployeeRequest.setIfsc(ifsc);

        // Set up REST API URL and headers
        String apiUrl = "http://localhost:8080/admin/add-new-employee";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<NewEmployeeRequest> requestEntity = new HttpEntity<>(newEmployeeRequest, headers);

        try {
            // Make the API call to register a new employee
            RestTemplate restTemplate = new RestTemplate();
            ResponseEntity<?> responseEntity = restTemplate.exchange(apiUrl, HttpMethod.POST, requestEntity, String.class);

            // Handle the response
            if (responseEntity.getStatusCode().is2xxSuccessful()) {
                String response = (String) responseEntity.getBody();
                displayError(new Exception(response));
            } else {
                String errorResponse = (String) responseEntity.getBody();
                displayError(new Exception(errorResponse));
            }

        } catch (Exception e) {
            displayError(new Exception(e.getMessage()));
        }
    }

    /*
     * Validates all input fields to ensure they are not empty and meet specified criteria.
     */
    private boolean validateInputFields() {
        // Check if any of the required fields are empty
        if (nameField.getText().isEmpty() ||
                emailField.getText().isEmpty() ||
                phoneField.getText().isEmpty() ||
                usernameField.getText().isEmpty() ||
                passwordField.getText().isEmpty() ||
                departmentComboBox.getValue() == null ||
                employmentLevelComboBox.getValue() == null ||
                bankAccNoField.getText().isEmpty() ||
                ifscField.getText().isEmpty()) {

            displayError(new Exception("Please fill in all required fields."));
            return false;
        }

        // Validate email format
        String email = emailField.getText();
        if (!isValidEmail(email)) {
            displayError(new Exception("Please enter a valid email address."));
            return false;
        }

        // Validate phone number format
        String phone = phoneField.getText();
        if (!isValidPhone(phone)) {
            displayError(new Exception("Please enter a valid phone number."));
            return false;
        }

        // Validate username length
        if (!isValidUsername(usernameField.getText())) {
            displayError(new Exception("Username must be between 4 and 10 characters."));
            return false;
        }

        // Validate password length
        if (!isValidPassword(passwordField.getText())) {
            displayError(new Exception("Password must be between 6 and 12 characters."));
            return false;
        }

        return true;
    }

    /*
     * Validates the format of an email address using a regular expression.
     */
    private boolean isValidEmail(String email) {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        return email.matches(emailRegex);
    }

    /*
     * Validates the format of a phone number using a regular expression.
     */
    private boolean isValidPhone(String phone) {
        String phoneRegex = "^\\d{10}$";
        return phone.matches(phoneRegex);
    }

    /*
     * Validates the length of a username.
     */
    private boolean isValidUsername(String username) {
        return username.length() >= 4 && username.length() <= 10;
    }

    /*
     * Validates the length of a password.
     */
    private boolean isValidPassword(String password) {
        return password.length() >= 6 && password.length() <= 12;
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
