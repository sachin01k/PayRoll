/*
 * LoginController class manages the login functionality for both admins and employees.
 * It communicates with the backend to perform authentication and opens the corresponding dashboards.
 */

package com.Infotrixs.Payroll_System.PayrollUI;

import com.Infotrixs.Payroll_System.DTOs.Outgoing.AdminLoginDetails;
import com.Infotrixs.Payroll_System.DTOs.Outgoing.EmployeeLoginDetails;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.control.Label;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.util.Duration;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import java.io.IOException;

public class LoginController {
    private static int Id;

    public int getId()
    {
        return Id;
    }
    private static String adminName;

    private static int emplId;

    public int getempId()
    {
        return emplId;
    }


    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private ComboBox<String> roleComboBox;

    @FXML
    private Label errorLabel;




    @FXML
    public void initialize() {

    }

    /*
     * Handles the event when the usernameField is clicked.
     */
    @FXML
    private void handleUsernameFieldClick(MouseEvent event) {
        if (!usernameField.isFocused()) {
            usernameField.requestFocus();
        }
    }
    /*
     * Handles the event when the passwordField is clicked.
     */
    @FXML
    private void handlePasswordFieldClick(MouseEvent event) {
        if (!passwordField.isFocused()) {
            passwordField.requestFocus();
        }
    }
    /*
     * Handles the event when the login button is clicked. Performs user authentication and opens the respective dashboard.
     */
    @FXML
    private void loginButtonClicked() {
        String enteredUsername = usernameField.getText();
        String enteredPassword = passwordField.getText();
        String selectedRole = roleComboBox.getValue();

        // Check for empty username
        if (enteredUsername.isEmpty()) {
            displayError("Username is required.");
            return;
        }

        // Check for empty password
        if (enteredPassword.isEmpty()) {
            displayError("Password is required.");
            return;
        }

        // Create a RestTemplate instance to send HTTP requests
        RestTemplate restTemplate = new RestTemplate();

        // URL for your Spring Boot login API
        String apiUrl = "http://localhost:8080";  // Replace with your actual API URL

        // Create the request payload based on the selected role
        String loginUrl;
        if ("Admin".equals(selectedRole)) {
            loginUrl = apiUrl + "/admin-login";
        } else if ("Employee".equals(selectedRole)) {
            loginUrl = apiUrl + "/employee-login";
        } else {
            // Handle the case where no role is selected
            displayError("Please select a role.");
            return;
        }

        // Create the HTTP headers with basic authentication credentials
        HttpHeaders headers = new HttpHeaders();
        headers.setBasicAuth(enteredUsername, enteredPassword);

        // Create the login request with headers
        HttpEntity<String> requestEntity = new HttpEntity<>(headers);

        try {
            // Make the request with authentication credentials
            ResponseEntity<AdminLoginDetails> responseEntity = restTemplate.exchange(loginUrl, HttpMethod.GET, requestEntity, AdminLoginDetails.class);
            ResponseEntity<EmployeeLoginDetails> responseEntityEmp = restTemplate.exchange(loginUrl, HttpMethod.GET, requestEntity, EmployeeLoginDetails.class);

            // Handle the response
            if (responseEntity.getStatusCode().is2xxSuccessful()) {
                // Login successful

                // Store the values from the API response
                Id = responseEntity.getBody().getAdminId();
                adminName = responseEntity.getBody().getAdminName();

                emplId=responseEntityEmp.getBody().getEmployeeId();

                // You can use these values as needed in your application
                System.out.println("Admin ID: " + Id);
                System.out.println("Admin Name: " + adminName);
                System.out.println("Employee ID: " + emplId);

                if ("Admin".equals(selectedRole)) {
                    openAdminDashboard();
                } else if ("Employee".equals(selectedRole)) {
                    openEmployeeDashboard();
                }

                System.out.println("Logged in!");
            } else {
                // Login failed
                displayError("Login failed. Error: " + responseEntity.getBody());
            }
        } catch (HttpClientErrorException.Unauthorized unauthorizedException) {
            // Handle unauthorized access (e.g., show an error message to the user)
            displayError("Unauthorized access. Please check your credentials.");
        } catch (Exception e) {
            // Handle other exceptions
            displayError("An error occurred: " + e.getMessage());
        }
    }


    /*
     * Opens the Admin Dashboard.
     */
    private void openAdminDashboard() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AdminDashboard/AdminDashboard.fxml"));
            Parent root = loader.load();

            Stage loginStage = (Stage) usernameField.getScene().getWindow();  // Get the login window's stage
            loginStage.close();

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Admin Dashboard");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /*
     * Opens the Employee Dashboard.
     */
    private void openEmployeeDashboard() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/EmployeeDashboard/EmployeeDashboard.fxml"));
            Parent root = loader.load();

            Stage loginStage = (Stage) usernameField.getScene().getWindow();  // Get the login window's stage
            loginStage.close();

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Employee Dashboard");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /*
     * Displays an error message on the UI and clears it after a specified duration.
     */
    private void displayError(String errorMessage) {
        // Set the error message to the label
        errorLabel.setText(errorMessage);

        // You can customize the style of the error label if needed
        errorLabel.setStyle("-fx-text-fill: red; -fx-font-style: italic;");

        // Set the visibility and managed properties of the label based on the presence of an error message
        boolean hasError = errorMessage != null && !errorMessage.isEmpty();
        errorLabel.setVisible(hasError);
        errorLabel.setManaged(hasError);

        // Use the Timeline and KeyFrame for a smooth fade-out effect after the specified duration
        Duration duration = Duration.seconds(1);
        KeyFrame keyFrame = new KeyFrame(duration, event -> {
            // Remove the error label text after the specified duration
            errorLabel.setText("");
        });

        Timeline timeline = new Timeline(keyFrame);
        timeline.play();
    }

    /*
     * Handles the event when the "Register New Admin" button is clicked.
     */
    public void RegisterNewAdminClicked(ActionEvent actionEvent) {
        try {
            // Load the FXML file for the "Add New Admin" window
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/RegisterNewAdminWindow.fxml"));
            Parent root = loader.load();

            // Create a new stage
            Stage addAdminStage = new Stage();
            addAdminStage.initModality(Modality.APPLICATION_MODAL);
            addAdminStage.setTitle("Register New Admin");

            // Set the scene with the loaded FXML content
            addAdminStage.setScene(new Scene(root));

            // Show the stage
            addAdminStage.showAndWait(); // This makes the window modal

        } catch (Exception e) {
            e.printStackTrace(); // Handle the exception according to your needs
        }
    }

    /*
     * Handles the event when the "Reset Password" button is clicked.
     */
    @FXML
    private void resetPasswordClicked()
    {
        try {
            // Load the FXML file for the "Add New Admin" window
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ResetPasswordWindow.fxml"));
            Parent root = loader.load();

            // Create a new stage
            Stage addAdminStage = new Stage();
            addAdminStage.initModality(Modality.APPLICATION_MODAL);
            addAdminStage.setTitle("Reset Password");

            // Set the scene with the loaded FXML content
            addAdminStage.setScene(new Scene(root));

            // Show the stage
            addAdminStage.showAndWait(); // This makes the window modal

        } catch (Exception e) {
            e.printStackTrace(); // Handle the exception according to your needs
        }
    }

}