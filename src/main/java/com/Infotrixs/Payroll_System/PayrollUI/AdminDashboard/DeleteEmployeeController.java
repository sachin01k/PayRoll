/*
 * DeleteEmployeeController class handles the deletion of an employee through the admin dashboard UI.
 * It communicates with the server using a REST API to perform the deletion operation.
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


public class DeleteEmployeeController {

    @FXML
    private TextField employeeIdTextField;

    @FXML
    private Label errorLabel;

    /*
     * Handles the deleteEmployee button action.
     * Validates the employee ID, makes a REST API call to delete the employee, and handles the response.
     */
    @FXML
    private void deleteEmployee() {
        // Validate if the employee ID field is filled
        String employeeIdText = employeeIdTextField.getText().trim();
        if (employeeIdText.isEmpty()) {
            displayError(new Exception("Please enter the employee ID."));
            return;
        }

        // Validate if the employee ID contains only numeric values
        try {
            int employeeId = Integer.parseInt(employeeIdText);

            // Make the API call to delete an employee
            String apiUrl = "http://localhost:8080/admin/delete-an-employee/employee-id/" + employeeId;

            RestTemplate restTemplate = new RestTemplate();
            ResponseEntity<?> responseEntity = restTemplate.exchange(apiUrl, HttpMethod.DELETE, null, String.class);

            // Handle the response
            if (responseEntity.getStatusCode().is2xxSuccessful()) {
                String response = (String) responseEntity.getBody();
                displayError(new Exception(response));
                // Handle the response as needed
            } else {
                String errorResponse = (String) responseEntity.getBody();
                displayError(new Exception(errorResponse));
                // Display error using the displayError function
                displayError(new Exception(errorResponse));
            }

        } catch (NumberFormatException e) {
            // Handle the case where the employee ID is not numeric
            displayError(new Exception("Please enter a valid numeric employee ID."));
        } catch (Exception e) {
            // Handle other exceptions
            System.out.println("An error occurred during employee deletion: " + e.getMessage());
            displayError(e);
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
        boolean hasError = !errorMessage.isEmpty();
        errorLabel.setVisible(hasError);
        errorLabel.setManaged(hasError);

        // Use the Timeline and KeyFrame for a smooth fade-out effect after the specified duration
        Duration duration = Duration.seconds(5); // Adjust the duration as needed
        KeyFrame keyFrame = new KeyFrame(duration, event -> {
            // Remove the error label text after the specified duration
            errorLabel.setText("");
        });

        Timeline timeline = new Timeline(keyFrame);
        timeline.play();
    }
}
