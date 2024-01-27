/*
 * RegisterNewAdminController class is the controller for the Register New Admin window.
 * It handles the registration of new administrators by sending data to the Spring Boot backend.
 */
package com.Infotrixs.Payroll_System.PayrollUI;

import com.Infotrixs.Payroll_System.DTOs.Incoming.NewAdminRequest;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import org.springframework.http.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;

public class RegisterNewAdminController {

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
    private TextField authTokenField;

    @FXML
    private Button registerAdmin;

    @FXML
    private Label errorLabel; // Add a Label to show error messages

    // Add your logic for adding a new admin here
    private final Timeline errorLabelTimeline = new Timeline(
            new KeyFrame(Duration.seconds(1), event -> errorLabel.setVisible(false))
    );

    /*
     * This method is triggered when the "Register Admin" button is clicked.
     * It validates input fields, sends a POST request to the Spring Boot backend,
     * and handles the response or errors appropriately.
     */
    @FXML
    private void registerAdmin() {
        final String authToken = "AUTHORIZED_ADMIN0542"; // Replace this with your actual expected auth token

        String name = nameField.getText();
        String email = emailField.getText();
        String phone = phoneField.getText();
        String username = usernameField.getText();
        String password = passwordField.getText();
        String enteredAuthToken = authTokenField.getText();

        // Validate input fields
        if (name.isEmpty() || email.isEmpty() || phone.isEmpty() || username.isEmpty() || password.isEmpty() || enteredAuthToken.isEmpty()) {
            errorLabel.setText("Error: Please fill in all fields.");
            errorLabel.setVisible(true);
            errorLabelTimeline.playFromStart();
            return;  // Don't proceed if any field is empty
        }

        if (!enteredAuthToken.equals(authToken)) {
            errorLabel.setText("Error: Invalid Admin Authorization Token");
            errorLabel.setVisible(true);
            errorLabelTimeline.playFromStart();
            return;
        }

        if (!email.matches("\\b[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Z|a-z]{2,}\\b")) {
            errorLabel.setText("Error: Invalid email format.");
            errorLabel.setVisible(true);
            errorLabelTimeline.playFromStart();
            return;
        }

        if (!phone.matches("\\d{10}")) {
            errorLabel.setText("Error: Invalid phone number (10 digits required).");
            errorLabel.setVisible(true);
            errorLabelTimeline.playFromStart();
            return;
        }

        if (!username.matches("\\w{4,10}")) {
            errorLabel.setText("Error: Username must be 4 to 10 alphanumeric characters.");
            errorLabel.setVisible(true);
            errorLabelTimeline.playFromStart();
            return;
        }

        if (!password.matches(".{6,12}")) {
            errorLabel.setText("Error: Password must be 6 to 12 characters.");
            errorLabel.setVisible(true);
            errorLabelTimeline.playFromStart();
            return;
        }

        // Use RestTemplate to send a POST request to the Spring Boot backend
        String url = "http://localhost:8080/admin/add-new-admin"; // Change the URL accordingly
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        NewAdminRequest request = new NewAdminRequest();
        request.setName(name);
        request.setEmail(email);
        request.setPhone(phone);
        request.setUsername(username);
        request.setPassword(password);
        request.setAuthToken(enteredAuthToken);

        HttpEntity<NewAdminRequest> httpEntity = new HttpEntity<>(request, headers);
        RestTemplate restTemplate = new RestTemplate();

        try {
            ResponseEntity<String> responseEntity = restTemplate.postForEntity(url, httpEntity, String.class);

            if (responseEntity.getStatusCode().is2xxSuccessful()) {
                // Handle success, e.g., show a success message
                errorLabel.setText("Admin registered successfully.");
            } else {
                // Handle errors, e.g., show an error message
                errorLabel.setText("Error: " + responseEntity.getBody());
            }
        } catch (HttpClientErrorException e) {
            // Catch HttpClientErrorException specifically
            if (e.getStatusCode() == HttpStatus.UNAUTHORIZED) {
                errorLabel.setText("Error: Invalid Admin Authorization Token");
            } else {
                // Extract and display the error message from the server response
                String errorMessage = e.getResponseBodyAsString();
                errorLabel.setText("Error: " + errorMessage);
            }
        } catch (Exception e) {
            // Handle other exceptions, e.g., show an error message
            errorLabel.setText("Exception: " + e.getMessage());
        } finally {
            // Show the error label
            errorLabel.setVisible(true);

            // Schedule a task to hide the error label after 1 second
            errorLabelTimeline.playFromStart();
        }
    }
}