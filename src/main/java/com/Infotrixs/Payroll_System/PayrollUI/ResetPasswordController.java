/*
 * ResetPasswordController class is the controller for the Reset Password window.
 * It handles the user's request for an OTP and password reset, communicates with the Spring Boot backend,
 * and manages the visibility of UI elements based on the user's actions.
 */
package com.Infotrixs.Payroll_System.PayrollUI;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.control.Label;
import javafx.util.Duration;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

public class ResetPasswordController {

    @FXML
    private ComboBox<String> userTypeComboBox;

    @FXML
    private TextField userId;

    @FXML
    private TextField otpField;

    @FXML
    private TextField newPasswordField;

    @FXML
    private Label errorLabel;

    @FXML
    private Button requestOTPButton;

    @FXML
    private Button resetPassword;

    private final String BASE_URL = "http://localhost:8080/";

    @FXML
    private HBox P;

    // Add a Timeline for controlling the visibility of the errorLabel
    private Timeline errorLabelTimeline;

    @FXML
    public void initialize() {
        // Initialize the Timeline
        errorLabelTimeline = new Timeline(new KeyFrame(
                Duration.seconds(0.8), // Adjust the duration as needed
                this::hideErrorLabel));

        // Set auto-reverse to false, so it doesn't show again after hiding
        errorLabelTimeline.setAutoReverse(false);
    }

    /*
     * This method hides the errorLabel after the specified duration.
     */
    private void hideErrorLabel(ActionEvent event) {
        // Hide the errorLabel
        errorLabel.setVisible(false);
    }

    /*
     * This method handles the user's request for an OTP by making an API call to the Spring Boot backend.
     * It checks for input validity and updates UI elements accordingly.
     */
    @FXML
    public void requestOTP() {
        String userType = userTypeComboBox.getValue();
        String ID = userId.getText();

        if (userType == null || userType.isEmpty()) {
            displayError(new IllegalArgumentException("Please select a user type."));
            return;
        }

        // Check if user ID is empty
        if (userId.getText().isEmpty()) {
            displayError(new IllegalArgumentException("Please enter User ID."));
            return;
        }

        String endpoint = userType.toLowerCase() + "/password-reset-request/" + userType.toLowerCase() + "-id/" + ID;
        try {
            ResponseEntity<String> response = makeApiCall(endpoint);

            if (response.getStatusCode() == HttpStatus.OK) {
                // API call successful, show OTP fields
                otpField.setVisible(true);
                newPasswordField.setVisible(true);
                resetPassword.setVisible(true);
                P.setVisible(true);
                displayError(null); // Clear error label
            } else {
                // API call failed, show error message
                displayError(new RuntimeException("Error: " + response.getBody()));
            }
        } catch (Exception e) {
            displayError(e);
        }
    }

    /*
     * This method handles the user's password reset request by making an API call to the Spring Boot backend.
     * It validates input fields, performs the API call, and displays the appropriate messages based on the response.
     */
    @FXML
    public void resetPassword() {
        // Get the selected user type from ComboBox
        String userType = userTypeComboBox.getValue();

        String validationError = validateFields();
        if (validationError != null) {
            displayError(new IllegalArgumentException(validationError));
            return;
        }

        // Make API call for password reset
        String endpoint = userType.toLowerCase() + "/reset-password/" + userType.toLowerCase() + "-id/" + userId.getText() +
                "/otp/P-" + otpField.getText() + "/new-password/" + newPasswordField.getText();

        // Make the API call with an empty request body
        try {
            ResponseEntity<String> response = makeApiCallNP(endpoint, null);

            // Handle the response based on HTTP status
            if (response.getStatusCode() == HttpStatus.OK) {
                // API call successful, show success message
                displayError(null); // Clear error label
            } else {
                // API call failed, show error message
                displayError(new RuntimeException("Error: " + response.getBody()));
            }
        } catch (Exception e) {
            displayError(e);
        }
    }

    /*
     * This method validates the input fields (OTP and new password) and returns an error message if validation fails.
     */
    private String validateFields() {
        // Validate OTP
        if (otpField.getText().isEmpty()) {
            return "OTP cannot be empty.";
        }

        // Validate new password
        String newPassword = newPasswordField.getText();
        if (newPassword.isEmpty()) {
            return "New password cannot be empty.";
        }

        // Check if the password is at least 6 characters long
        if (newPassword.length() < 6) {
            return "Password must be at least 6 characters long.";
        }

        // All fields are valid
        return null; // No error message
    }

    /*
     * This method makes an API call to the Spring Boot backend using RestTemplate.
     * It handles GET requests and returns the response entity.
     */
    private ResponseEntity<String> makeApiCall(String endpoint) {
        // Create a RestTemplate
        RestTemplate restTemplate = new RestTemplate();

        // Make the API call and return the response
        return restTemplate.getForEntity(BASE_URL + endpoint, String.class);
    }

    /*
     * This method makes an API call to the Spring Boot backend using RestTemplate.
     * It handles PUT requests and returns the response entity.
     */
    private ResponseEntity<String> makeApiCallNP(String endpoint, String requestBody) {
        // Create a RestTemplate
        RestTemplate restTemplate = new RestTemplate();

        // Make the API call and return the response
        return restTemplate.exchange(BASE_URL + endpoint, HttpMethod.PUT, null, String.class);
    }

    /*
     * This method displays error messages on the UI.
     * It takes an exception as a parameter, extracts the error message, and sets it to the errorLabel.
     * It also handles the styling and visibility of the errorLabel.
     */
    private void displayError(Exception exception) {
        // Set the error message to the label
        String errorMessage;
        if (exception != null) {
            errorMessage = exception.getMessage();
        } else {
            errorMessage = "";
        }

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
