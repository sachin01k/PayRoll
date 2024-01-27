/*
 * UpdateAdminController class manages the functionality of updating admin details like email, phone, and username.
 * It communicates with the backend to perform these updates based on the selected function.
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
import org.springframework.http.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import com.Infotrixs.Payroll_System.PayrollUI.LoginController;

public class UpdateAdminController {

    @FXML
    private ComboBox<String> functionComboBox;

    @FXML
    private VBox updateEmailForm;

    @FXML
    private VBox updatePhoneForm;

    @FXML
    private VBox updateUsernameForm;

    @FXML
    private Button performFunctionButton;

    @FXML
    private Label adminId;

    @FXML
    private TextField newEmailTextField;

    @FXML
    private TextField newPhoneTextField;

    @FXML
    private TextField newUsernameTextField;

    @FXML
    private Label errorLabel;

    private String adminID;

    /*
     * Initializes the controller. Sets the admin ID, populates the functionComboBox,
     * and sets up the visibility of update forms and buttons.
     */
    public void initialize() {
        LoginController loginController = new LoginController();
        adminId.setText(String.valueOf(loginController.getId()));
        adminID = String.valueOf(loginController.getId());

        functionComboBox.setItems(FXCollections.observableArrayList(
                "Update Email", "Update Phone", "Update Username"
        ));

        functionComboBox.setOnAction(this::handleFunctionSelection);

        updateEmailForm.setManaged(false);
        updatePhoneForm.setManaged(false);
        updateUsernameForm.setManaged(false);
        performFunctionButton.setManaged(false);
    }

    /*
     * Handles the selection of functions in the ComboBox by updating the UI accordingly.
     */
    @FXML
    private void handleFunctionSelection(ActionEvent event) {
        String selectedFunction = functionComboBox.getValue();

        updateEmailForm.setVisible(false);
        updatePhoneForm.setVisible(false);
        updateUsernameForm.setVisible(false);

        switch (selectedFunction) {
            case "Update Email":
                performFunctionButton.setText("Update Email");
                performFunctionButton.setManaged(true);
                break;
            case "Update Phone":
                performFunctionButton.setText("Update Phone");
                performFunctionButton.setManaged(true);
                break;
            case "Update Username":
                performFunctionButton.setText("Update Username");
                performFunctionButton.setManaged(true);
                break;
        }

        switch (selectedFunction) {
            case "Update Email":
                updateEmailForm.setManaged(true);
                updatePhoneForm.setManaged(false);
                updateUsernameForm.setManaged(false);
                updateEmailForm.setVisible(true);
                break;
            case "Update Phone":
                updateEmailForm.setManaged(false);
                updatePhoneForm.setManaged(true);
                updateUsernameForm.setManaged(false);
                updatePhoneForm.setVisible(true);
                break;
            case "Update Username":
                updateEmailForm.setManaged(false);
                updatePhoneForm.setManaged(false);
                updateUsernameForm.setManaged(true);
                updateUsernameForm.setVisible(true);
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
        String newEmail = newEmailTextField.getText().trim();
        String newPhone = newPhoneTextField.getText().trim();
        String newUsername = newUsernameTextField.getText().trim();

        switch (selectedFunction) {
            case "Update Email":
                if (!isValidEmail(newEmail)) {
                    displayError(new Exception("Invalid email"));
                    return;
                }
                handleUpdateEmailAction(adminID, newEmail);
                break;
            case "Update Phone":
                if (!isValidPhoneNumber(newPhone)) {
                    displayError(new Exception("Phone number should be of length 10"));
                    return;
                }
                handleUpdatePhoneAction(adminID, newPhone);
                break;
            case "Update Username":
                if (!isValidUsername(newUsername)) {
                    displayError(new Exception("Username should be 4 - 10 characters long"));
                    return;
                }
                handleUpdateUsernameAction(adminID, newUsername);
                break;
            default:
                break;
        }
    }

    /*
     * Validates the email format using a regular expression.
     */
    private boolean isValidEmail(String email) {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        return email.matches(emailRegex);
    }

    /*
     * Validates the phone number format (10 digits).
     */
    private boolean isValidPhoneNumber(String phone) {
        String phoneRegex = "^\\d{10}$";
        return phone.matches(phoneRegex);
    }

    /*
     * Validates the username length (4 - 10 characters).
     */
    private boolean isValidUsername(String username) {
        return username.length() >= 4 && username.length() <= 10;
    }

    /*
     * Handles the API call to update the admin's email.
     */
    private void handleUpdateEmailAction(String adminId, String newEmail) {
        try {
            RestTemplate restTemplate = new RestTemplate();
            String apiUrl = "http://localhost:8080/admin";
            String updateEmailUrl = apiUrl + "/update-email/admin-id/" + adminId + "/new-email/" + newEmail;
            HttpEntity<String> requestEntity = new HttpEntity<>(new HttpHeaders());
            ResponseEntity<String> responseEntity = restTemplate.exchange(
                    updateEmailUrl,
                    HttpMethod.PUT,
                    requestEntity,
                    String.class
            );

            if (responseEntity.getStatusCode().is2xxSuccessful()) {
                displayError(new Exception(responseEntity.getBody()));
            } else {
                displayError(new Exception(responseEntity.getBody()));
            }
        } catch (HttpClientErrorException.Unauthorized unauthorizedException) {
            displayError(new Exception("Unauthorized access. Please check your credentials."));
        } catch (Exception e) {
            displayError(new Exception(e.getMessage()));
        }
    }

    /*
     * Handles the API call to update the admin's phone number.
     */
    private void handleUpdatePhoneAction(String adminId, String newPhone) {
        RestTemplate restTemplate = new RestTemplate();
        String apiUrl = "http://localhost:8080/admin";
        String updatePhoneUrl = apiUrl + "/update-phone/admin-id/" + adminId + "/new-phone/" + newPhone;

        try {
            ResponseEntity<String> responseEntity = restTemplate.exchange(
                    updatePhoneUrl,
                    HttpMethod.PUT,
                    null,
                    String.class
            );

            if (responseEntity.getStatusCode().is2xxSuccessful()) {
                String responseMessage = responseEntity.getBody();
                displayError(new Exception(responseMessage));
            } else {
                String errorMessage = responseEntity.getBody();
                displayError(new Exception(errorMessage));
            }
        } catch (Exception e) {
            displayError(new Exception(e.getMessage()));
        }
    }

    /*
     * Handles the API call to update the admin's username.
     */
    private void handleUpdateUsernameAction(String adminId, String newUsername) {
        RestTemplate restTemplate = new RestTemplate();
        String apiUrl = "http://localhost:8080/admin";
        String updateUsernameUrl = apiUrl + "/update-username/admin-id/" + adminId + "/new-username/" + newUsername;

        try {
            ResponseEntity<String> responseEntity = restTemplate.exchange(
                    updateUsernameUrl,
                    HttpMethod.PUT,
                    null,
                    String.class
            );

            if (responseEntity.getStatusCode().is2xxSuccessful()) {
                String responseMessage = responseEntity.getBody();
                displayError(new Exception(responseMessage));
            } else {
                String errorMessage = responseEntity.getBody();
                displayError(new Exception(errorMessage));
            }
        } catch (Exception e) {
            displayError(new Exception(e.getMessage()));
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
