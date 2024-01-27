/*
 * ProfileController class manages the UI for displaying the profile details of an admin.
 * It communicates with the server through a REST API to fetch and display admin details such as ID, name, email, phone, and username.
 */

package com.Infotrixs.Payroll_System.PayrollUI.AdminDashboard;

import com.Infotrixs.Payroll_System.DTOs.Outgoing.AdminDetails;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import com.Infotrixs.Payroll_System.PayrollUI.LoginController;

public class ProfileController {

    @FXML
    private Label adminIdLabel;

    @FXML
    private Label nameLabel;

    @FXML
    private Label emailLabel;

    @FXML
    private Label phoneLabel;

    @FXML
    private Label usernameLabel;

    private final RestTemplate restTemplate = new RestTemplate();

    /*
     * Initializes the controller, fetching admin details using the LoginController's admin ID and setting them to the UI labels.
     */
    public void initialize() {
        LoginController loginController = new LoginController();
        int adminId = loginController.getId(); // Replace with the actual admin ID
        initData(adminId);
    }

    /*
     * Fetches admin details from the server based on the provided admin ID and sets them to the UI labels.
     */
    public void initData(int adminId) {
        // Replace with your actual API URL
        String apiUrl = "http://localhost:8080/admin";
        String seeAccountDetailsUrl = apiUrl + "/see-account-details/admin-id/" + adminId;

        try {
            // Make the request to see account details
            ResponseEntity<AdminDetails> responseEntity = restTemplate.getForEntity(seeAccountDetailsUrl, AdminDetails.class);

            // Handle the response
            if (responseEntity.getStatusCode().is2xxSuccessful()) {
                // Admin details successfully retrieved
                AdminDetails adminDetails = responseEntity.getBody();

                // Set the details to the labels
                adminIdLabel.setText(String.valueOf(adminDetails.getAdminId()));
                nameLabel.setText(adminDetails.getName());
                emailLabel.setText(adminDetails.getEmail());
                phoneLabel.setText(adminDetails.getPhone());
                usernameLabel.setText(adminDetails.getUsername());
            } else {
                // Failed to retrieve admin details
                System.out.println("Failed to retrieve admin details. Error: " + responseEntity.getBody());
            }
        } catch (Exception e) {
            // Handle exceptions
            System.out.println("An error occurred while fetching admin details: " + e.getMessage());
        }
    }
}


