/*
 * UpdateEmployeeDetailsController class manages the functionality of updating employee details such as department,
 * employment level, and access. It communicates with the backend to perform these updates based on the selected function.
 */

package com.Infotrixs.Payroll_System.PayrollUI.AdminDashboard;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.util.Duration;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

public class UpdateEmployeeDetailsController {

    @FXML
    private TextField EmployeeIdTextField;

    @FXML
    private ComboBox<String> functionComboBox;

    @FXML
    private VBox updateDepartmentForm;

    @FXML
    private ComboBox<String> newDepartmentField;

    @FXML
    private VBox updateEmploymentLevelForm;

    @FXML
    private ComboBox<String> newEmploymentLevelComboBox;

    @FXML
    private VBox changeEmployeeAccessForm;

    @FXML
    private Label errorLabel;

    @FXML
    private Button performFunctionButton;

    /*
     * Initializes the controller. Sets up the ComboBox options and manages the visibility of UI components.
     */
    @FXML
    private void initialize() {
        functionComboBox.setItems(FXCollections.observableArrayList(
                "Update Department", "Update Employment Level", "Change Employee Access"));
        functionComboBox.setOnAction(this::handleFunctionSelection);

        updateDepartmentForm.setManaged(false);
        updateEmploymentLevelForm.setManaged(false);
        changeEmployeeAccessForm.setManaged(false);
        performFunctionButton.setManaged(false);
    }

    /*
     * Handles the selection of functions in the ComboBox by updating the UI accordingly.
     */
    @FXML
    private void handleFunctionSelection(ActionEvent event) {
        String selectedFunction = functionComboBox.getValue();

        updateEmploymentLevelForm.setVisible(false);
        updateDepartmentForm.setVisible(false);
        changeEmployeeAccessForm.setVisible(false);

        switch (selectedFunction) {
            case "Update Department":
                performFunctionButton.setText("Update Department");
                performFunctionButton.setManaged(true);
                updateDepartmentForm.setVisible(true);
                break;
            case "Update Employment Level":
                performFunctionButton.setText("Update Employment Level");
                performFunctionButton.setManaged(true);
                updateEmploymentLevelForm.setVisible(true);
                break;
            case "Change Employee Access":
                performFunctionButton.setText("Change Employee Access");
                performFunctionButton.setManaged(true);
                changeEmployeeAccessForm.setVisible(true);
                break;
        }

        switch (selectedFunction) {
            case "Update Department":
                updateDepartmentForm.setManaged(true);
                updateEmploymentLevelForm.setManaged(false);
                changeEmployeeAccessForm.setManaged(false);
                break;

            case "Update Employment Level":
                updateDepartmentForm.setManaged(false);
                updateEmploymentLevelForm.setManaged(true);
                changeEmployeeAccessForm.setManaged(false);
                break;

            case "Change Employee Access":
                changeEmployeeAccessForm.setManaged(true);
                updateDepartmentForm.setManaged(false);
                updateEmploymentLevelForm.setManaged(false);
                break;

            default:
                break;
        }
    }

    /*
     * Handles the action when the "Perform Function" button is clicked.
     * It validates the input and triggers the corresponding update action.
     */
    @FXML
    private void handlePerformFunctionButton(ActionEvent event) {
        String selectedFunction = functionComboBox.getValue();
        int employeeId = Integer.parseInt(EmployeeIdTextField.getText());
        String department = newDepartmentField.getValue();
        String employmentLevel = newEmploymentLevelComboBox.getValue();

        switch (selectedFunction) {
            case "Update Department":
                handleUpdateDepartment(employeeId, department);
                break;
            case "Update Employment Level":
                handleUpdateEmploymentLevel(employeeId, employmentLevel);
                break;
            case "Change Employee Access":
                handleChangeEmployeeAccess(employeeId);
                break;
        }
    }

    /*
     * Handles the API call to update the employee's department.
     */
    private void handleUpdateDepartment(int employeeId, String newDepartment) {
        String baseUrl = "http://localhost:8080/admin";
        RestTemplate restTemplate = new RestTemplate();
        String updateDepartmentUrl = baseUrl + "/update-department/employee-id/" + employeeId + "/new-department/" + newDepartment;

        try {
            ResponseEntity<String> responseEntity = restTemplate.exchange(
                    updateDepartmentUrl,
                    HttpMethod.PUT,
                    null,
                    String.class
            );

            if (responseEntity.getStatusCode().is2xxSuccessful()) {
                displayError(new Exception("Department updated successfully"));
                System.out.println(responseEntity.getBody());
            } else {
                displayError(new Exception("Failed to update department"));
                System.out.println(responseEntity.getBody());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /*
     * Handles the API call to update the employee's employment level.
     */
    private void handleUpdateEmploymentLevel(int employeeId, String newLevel) {
        String baseUrl = "http://localhost:8080/admin";
        RestTemplate restTemplate = new RestTemplate();
        String updateEmploymentLevelUrl = baseUrl + "/update-employment-level/employee-id/" + employeeId + "/new-level/" + newLevel;

        try {
            ResponseEntity<String> responseEntity = restTemplate.exchange(
                    updateEmploymentLevelUrl,
                    HttpMethod.PUT,
                    null,
                    String.class
            );

            if (responseEntity.getStatusCode().is2xxSuccessful()) {
                displayError(new Exception("Employment level updated successfully"));
                System.out.println(responseEntity.getBody());
            } else {
                displayError(new Exception("Failed to update employment level"));
                System.out.println(responseEntity.getBody());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /*
     * Handles the API call to change the employee's access.
     */
    private void handleChangeEmployeeAccess(int employeeId) {
        String baseUrl = "http://localhost:8080/admin";
        RestTemplate restTemplate = new RestTemplate();
        String changeEmployeeAccessUrl = baseUrl + "/change-employee-access/employee-id/" + employeeId;

        try {
            ResponseEntity<String> responseEntity = restTemplate.exchange(
                    changeEmployeeAccessUrl,
                    HttpMethod.PUT,
                    null,
                    String.class
            );

            if (responseEntity.getStatusCode().is2xxSuccessful()) {
                displayError(new Exception("Employee access changed successfully"));
                System.out.println(responseEntity.getBody());
            } else {
                displayError(new Exception("Failed to change employee access"));
                System.out.println(responseEntity.getBody());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /*
     * Displays an error message on the UI and clears it after a specified duration.
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