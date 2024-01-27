/*
 * EmployeeDashboardController class is the controller for the Employee Dashboard window.
 * It manages the UI elements, animations, and navigation functionalities.
 */
package com.Infotrixs.Payroll_System.PayrollUI.EmployeeDashboard;

import javafx.animation.FadeTransition;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class EmployeeDashboardController implements Initializable {

    @FXML
    private AnchorPane rootPane;

    @FXML
    private Pane navigationPane;

    @FXML
    private AnchorPane content;

    @FXML
    private Button toggleNavigation, handleProfile, handleShowStructure, handleViewCurrentDueSalaries, handleViewPaymentRecords, handleLogout;

    private boolean isNavigationPaneVisible = false;
    private boolean isAnimationRunning = false;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Add highlighting effect to buttons
        addHighlightEffectToButton(handleProfile);
        addHighlightEffectToButton(handleShowStructure);
        addHighlightEffectToButton(handleViewCurrentDueSalaries);
        addHighlightEffectToButton(handleViewPaymentRecords);
        addHighlightEffectToButton(handleLogout);
        addHighlightEffectToButton(toggleNavigation);
    }

    /*
     * Adds highlighting effect to the specified button on mouse hover and exit.
     */
    private void addHighlightEffectToButton(Button button) {
        button.setOnMouseEntered(event -> button.setStyle("-fx-background-color: #5447EB;")); // Change the color as desired
        button.setOnMouseExited(event -> button.setStyle("-fx-background-color: #6C60FC;")); // Original color
    }

    /*
     * Toggles the visibility of the navigationPane with a fade-in/out animation.
     */
    @FXML
    private void toggleNavigation() {
        if (isAnimationRunning) {
            return; // Do nothing if the animation is already running
        }

        isAnimationRunning = true;

        if (isNavigationPaneVisible) {
            fadeOutNavigationPane();
            makeContentActive();
        } else {
            fadeInNavigationPane();
            makeContentInactive();
        }

        isNavigationPaneVisible = !isNavigationPaneVisible;
    }

    /*
     * Fades in the navigationPane with a specified duration and translation animation.
     */
    private void fadeInNavigationPane() {
        FadeTransition fadeTransition = new FadeTransition(Duration.seconds(0.5), navigationPane);
        fadeTransition.setFromValue(0);
        fadeTransition.setToValue(1);
        fadeTransition.setOnFinished(event -> isAnimationRunning = false); // Animation finished callback
        fadeTransition.play();

        TranslateTransition translateTransition = new TranslateTransition(Duration.seconds(0.5), navigationPane);
        translateTransition.setByX(250);
        translateTransition.setOnFinished(event -> isAnimationRunning = false); // Animation finished callback
        translateTransition.play();
    }

    /*
     * Fades out the navigationPane with a specified duration and translation animation.
     */
    private void fadeOutNavigationPane() {
        FadeTransition fadeTransition = new FadeTransition(Duration.seconds(0.5), navigationPane);
        fadeTransition.setFromValue(1);
        fadeTransition.setToValue(0);
        fadeTransition.setOnFinished(event -> isAnimationRunning = false); // Animation finished callback
        fadeTransition.play();

        TranslateTransition translateTransition = new TranslateTransition(Duration.seconds(0.5), navigationPane);
        translateTransition.setByX(-250);
        translateTransition.setOnFinished(event -> isAnimationRunning = false); // Animation finished callback
        translateTransition.play();
    }

    /*
     * Disables the content and sets its background color to indicate inactivity.
     */
    private void makeContentInactive() {
        content.setDisable(true);
        content.setStyle("-fx-background-color: #CFFCFB; -fx-opacity: 0.5;");
    }

    /*
     * Enables the content and resets its background color to the original.
     */
    private void makeContentActive() {
        content.setDisable(false);
        content.setStyle("-fx-background-color: #CFFCFB;");
    }

    /*
     * Handles the "Profile" button click, loads EmployeeProfile.fxml into the content AnchorPane, and toggles navigation.
     */
    @FXML
    private void handleProfile() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/EmployeeDashboard/EmployeeProfile.fxml"));
            Parent profileRoot = loader.load();
            EmployeeProfileController employeeProfileController = loader.getController();
            content.getChildren().setAll(profileRoot);
            toggleNavigation();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /*
     * Handles the "Show Structure" button click, loads SalaryStructureEmp.fxml into the content AnchorPane, and toggles navigation.
     */
    @FXML
    private void handleShowStructure() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/EmployeeDashboard/SalaryStructureEmp.fxml"));
            Parent salaryStructureRoot = loader.load();
            SalaryStructureController salaryStructureController = loader.getController();
            content.getChildren().setAll(salaryStructureRoot);
            toggleNavigation();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /*
     * Handles the "View Current Due Salaries" button click, loads ViewCurrentDueSalaryEmp.fxml into the content AnchorPane, and toggles navigation.
     */
    @FXML
    private void handleViewCurrentDueSalaries() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/EmployeeDashboard/ViewCurrentDueSalaryEmp.fxml"));
            Parent viewCurrentDueSalariesRoot = loader.load();
            ViewCurrentDueSalariesControllerEmp viewCurrentDueSalariesController = loader.getController();
            content.getChildren().setAll(viewCurrentDueSalariesRoot);
            toggleNavigation();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /*
     * Handles the "View Payment Records" button click, loads ViewPaymentRecordsEmp.fxml into the content AnchorPane, and toggles navigation.
     */
    @FXML
    private void handleViewPaymentRecords() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/EmployeeDashboard/ViewPaymentRecordsEmp.fxml"));
            Parent viewPaymentRecordsRoot = loader.load();
            ViewPaymentRecordsEmpController viewPaymentRecordsEmpController = loader.getController();
            content.getChildren().setAll(viewPaymentRecordsRoot);
            toggleNavigation();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /*
     * Handles the "Logout" button click by showing a confirmation dialog.
     * If confirmed, logs out and loads the LoginWindow.fxml, closing the current window.
     */
    @FXML
    private void handleLogout(ActionEvent event) {
        // Example: Logout
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Logout");
        alert.setHeaderText(null);
        alert.setContentText("Are you sure you want to logout?");
        alert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                // Code to handle logout (replace with your logic)
                System.out.println("Logging out...");

                // Load the LoginWindow.fxml after successful logout
                loadLoginWindow((Stage) ((Node) event.getSource()).getScene().getWindow());
            }
        });
    }

    /*
     * Loads the LoginWindow.fxml, creating a new Stage and closing the current Stage.
     */
    private void loadLoginWindow(Stage currentStage) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/LoginWindow.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setTitle("Login");
            stage.setScene(new Scene(root));
            stage.show();

            // Close the current window
            currentStage.close();
        } catch (IOException e) {
            e.printStackTrace();
            // Handle exception as needed
        }
    }
}
