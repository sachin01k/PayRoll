/*
 * SalaryStructureController class manages the functionality related to displaying the salary structure of an employee.
 * It communicates with the backend to retrieve and display the salary details based on the provided employee ID.
 */

package com.Infotrixs.Payroll_System.PayrollUI.AdminDashboard;

import com.Infotrixs.Payroll_System.DTOs.Outgoing.SalaryReplica;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.util.Duration;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import static org.springframework.jdbc.support.JdbcUtils.isNumeric;

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

    @FXML
    private Label errorLabel;

    @FXML
    private TextField employeeId;

    /*
     * This method is triggered when the user clicks the "Show Structure" button.
     * It validates the input, makes an API call to retrieve the salary structure, and updates the UI with the received data.
     */
    @FXML
    private void handleShowStructure() {
        String employeeIdText = employeeId.getText().trim();

        if (employeeIdText.isEmpty()) {
            displayError(new Exception("Enter Employee ID"));
            return;
        }

        if (!isNumeric(Integer.parseInt(employeeIdText))) {
            displayError(new Exception("Employee ID can only be numeric"));
            return;
        }

        try {
            int employeeId = Integer.parseInt(employeeIdText);

            String baseUrl = "http://localhost:8080/admin";
            String apiUrl = baseUrl + "/see-salary-structure-of-an-employee/employee-id/" + employeeId;

            RestTemplate restTemplate = new RestTemplate();

            ResponseEntity<SalaryReplica> responseEntity = restTemplate.exchange(
                    apiUrl,
                    HttpMethod.GET,
                    null,
                    SalaryReplica.class
            );

            if (responseEntity.getStatusCode().is2xxSuccessful()) {
                SalaryReplica salaryReplica = responseEntity.getBody();

                // Update UI with salary details
                salaryIdLabel.setText(String.valueOf(salaryReplica.getSalaryId()));
                employeeNameLabel.setText(salaryReplica.getEmployeeName());
                baseLabel.setText(String.valueOf(salaryReplica.getBase()));
                houseRestAllowLabel.setText(String.valueOf(salaryReplica.getHouseRentAllow()));
                convenienceAllowLabel.setText(String.valueOf(salaryReplica.getConvenienceAllow()));
                pfLabel.setText(String.valueOf(salaryReplica.getPF()));
                insuranceLabel.setText(String.valueOf(salaryReplica.getInsurance()));
                inHandLabel.setText(String.valueOf(salaryReplica.getInHand()));

                displayError(new Exception("Salary structure retrieved successfully!"));
            } else {
                displayError(new Exception(String.valueOf(responseEntity.getBody())));
            }

        } catch (NumberFormatException e) {
            displayError(new Exception("Invalid employee ID format"));
        } catch (Exception e) {
            displayError(new Exception("Failed to retrieve salary structure"));
            e.printStackTrace();
        }
    }

    /*
     * This method displays an error message on the UI.
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

        Duration duration = Duration.seconds(1); // Adjust the duration as needed
        KeyFrame keyFrame = new KeyFrame(duration, event -> {
            errorLabel.setText("");
        });

        Timeline timeline = new Timeline(keyFrame);
        timeline.play();
    }
}