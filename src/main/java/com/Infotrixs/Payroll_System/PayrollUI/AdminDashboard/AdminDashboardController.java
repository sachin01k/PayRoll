/*
 * AdminDashboardController class controls the functionality of the admin dashboard UI.
 * It handles button actions, navigation transitions, and loading different views based on user interactions.
 */
package com.Infotrixs.Payroll_System.PayrollUI.AdminDashboard;


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
import javafx.scene.control.ComboBox;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class AdminDashboardController implements Initializable {

    @FXML
    private AnchorPane rootPane;

    @FXML
    private Pane navigationPane;

    @FXML
    private AnchorPane content;

    @FXML
    private Button handleProfile,toggleNavigation,handleRecordDues,handleEditAdminDetails,handleRegisterNewEmployee, handleUpdateEmployeeDetails,handleRecordUnpaidLeaves,handleViewCurrentDueSalaries,handleViewPaymentRecords, handlePaymentSlip,handleShowStructure,handleDeleteEmployee,handleLogout;

    @FXML
    private ComboBox<String> adminActionsComboBox;

    @FXML
    private Pane adminActionFormPane;


    private boolean isNavigationPaneVisible = false;
    private boolean isAnimationRunning = false; // Added flag

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Initialize code if needed

        // Add highlighting effect to buttons
        addHighlightEffectToButton(handleProfile);
        addHighlightEffectToButton(handleEditAdminDetails);
        addHighlightEffectToButton(handleRegisterNewEmployee);
        addHighlightEffectToButton(handleUpdateEmployeeDetails);
        addHighlightEffectToButton(handleRecordUnpaidLeaves);
        addHighlightEffectToButton(handleRecordDues);
        addHighlightEffectToButton(handleViewCurrentDueSalaries);
        addHighlightEffectToButton(handlePaymentSlip);
        addHighlightEffectToButton(handleViewPaymentRecords);
        addHighlightEffectToButton(handleShowStructure);
        addHighlightEffectToButton(handleDeleteEmployee);
        addHighlightEffectToButton(handleLogout);
        addHighlightEffectToButton(toggleNavigation);


        // Add highlighting effect to other buttons as needed
    }

    /*
     * Adds highlighting effect to a given button on mouse hover.
     */
    private void addHighlightEffectToButton(Button button) {
        button.setOnMouseEntered(event -> button.setStyle("-fx-background-color: #6C60FC;")); // Change the color as desired
        button.setOnMouseExited(event -> button.setStyle("-fx-background-color: #5447EB;")); // Original color
    }

    /*
     * Toggles the visibility of the navigation pane with fade-in and fade-out animations.
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
     * Fades in the navigation pane with a slide-in effect.
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
     * Fades out the navigation pane with a slide-out effect.
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
     * Disables content and sets a semi-transparent background during navigation animations.
     */
    private void makeContentInactive() {
        content.setDisable(true);
        content.setStyle("-fx-background-color: #CFFCFB; -fx-opacity: 0.5;");
    }

    /*
     * Enables content after navigation animations and resets background color.
     */
    private void makeContentActive() {
        content.setDisable(false);
        content.setStyle("-fx-background-color: #CFFCFB;");
    }

    /*
     * Loads the Profile view when the "Profile" button is clicked.
     */
    public void handleProfile() {
        try {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AdminDashboard/Profile.fxml"));
            Parent ProfileRoot = loader.load();


            ProfileController profileController = loader.getController();



            content.getChildren().setAll(ProfileRoot);

            toggleNavigation();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleEditAdminDetails() {
        try {
            // Load the UpdateAdmin.fxml file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AdminDashboard/UpdateAdmin.fxml"));
            Parent updateAdminRoot = loader.load();

            // Access the controller of UpdateAdmin.fxml if needed
            UpdateAdminController updateAdminController = loader.getController();
            // Perform any initialization or communication with UpdateAdminController here

            // Replace the content of the content anchor pane with UpdateAdmin.fxml
            content.getChildren().setAll(updateAdminRoot);

            toggleNavigation();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    // Functionality 2: Register New Employees
    @FXML
    private void handleRegisterNewEmployee() {
        try {
            // Load the RegisterEmployee.fxml file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AdminDashboard/RegisterNewEmployee.fxml"));
            Parent registerEmployeeRoot = loader.load();

            // Access the controller of RegisterEmployee.fxml if needed
            RegisterNewEmployeeController registerEmployeeController = loader.getController();
            // Perform any initialization or communication with RegisterEmployeeController here

            // Replace the content of the content anchor pane with RegisterEmployee.fxml
            content.getChildren().setAll(registerEmployeeRoot);

            toggleNavigation();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Functionality 3: View and Update Job-Related Details of Employees
    @FXML
    private void handleUpdateEmployeeDetails() {
        // Example: Update Department
        try {
            // Load the UpdateAdmin.fxml file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AdminDashboard/UpdateEmployeeDetails.fxml"));
            Parent updateEmployeeDetailsRoot = loader.load();

            // Access the controller of UpdateAdmin.fxml if needed
            UpdateEmployeeDetailsController updateEmployeeDetailsController = loader.getController();
            // Perform any initialization or communication with UpdateAdminController here

            // Replace the content of the content anchor pane with UpdateAdmin.fxml
            content.getChildren().setAll(updateEmployeeDetailsRoot);

            toggleNavigation();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }



    // Functionality 5: Record Unpaid Leaves for Employees
    @FXML
    private void handleRecordUnpaidLeaves() {
        try {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AdminDashboard/RecordUnpaidLeaves.fxml"));
            Parent recordUnpaidLeavesRoot = loader.load();


            RecordUnpaidLeavesController recordUnpaidLeavesController = loader.getController();



            content.getChildren().setAll(recordUnpaidLeavesRoot);

            toggleNavigation();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Functionality 6: View Current Due Salary Details for All Employees
    @FXML
    private void handleViewCurrentDueSalaries() {
        try {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AdminDashboard/ViewCurrentDueSalaries.fxml"));
            Parent viewCurrentDueSalariesRoot = loader.load();


            ViewCurrentDueSalariesController viewCurrentDueSalariesController = loader.getController();



            content.getChildren().setAll(viewCurrentDueSalariesRoot);

            toggleNavigation();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Functionality 7: Make Payments on Current Due Salaries and Generate Pay Slips
    @FXML
    private void handlePaymentSlip() {
        try {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AdminDashboard/PaymentSlip.fxml"));
            Parent paymentSlipRoot = loader.load();


            PaymentSlipController paymentSlipController = loader.getController();



            content.getChildren().setAll(paymentSlipRoot);

            toggleNavigation();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Functionality 8: View Payment Records of All Employees
    @FXML
    private void handleViewPaymentRecords() {
        try {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AdminDashboard/ViewPaymentRecords.fxml"));
            Parent ViewpaymentRecordRoot = loader.load();


            ViewPaymentRecordsController viewPaymentRecordsController = loader.getController();



            content.getChildren().setAll(ViewpaymentRecordRoot);

            toggleNavigation();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @FXML
    private void handleShowStructure(){
        try {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AdminDashboard/SalaryStructure.fxml"));
            Parent SalaryStructureRoot = loader.load();


            SalaryStructureController salaryStructureController = loader.getController();



            content.getChildren().setAll(SalaryStructureRoot);

            toggleNavigation();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    // Functionality 9: Delete an Employee
    @FXML
    private void handleDeleteEmployee() {

    }

    // Functionality 10: Logout
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


    public void handleRecordDues(ActionEvent actionEvent) {
        try {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AdminDashboard/RecordUnpaidDues.fxml"));
            Parent RecordUnpaidDues = loader.load();


            RecordUnpaidDuesController recordUnpaidDuesController= loader.getController();



            content.getChildren().setAll(RecordUnpaidDues);

            toggleNavigation();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}