/*
 * ViewPaymentRecordsController class manages the UI for viewing payment records.
 * It communicates with the backend to fetch and display payment records.
 */

package com.Infotrixs.Payroll_System.PayrollUI.AdminDashboard;

import com.Infotrixs.Payroll_System.DTOs.Outgoing.AllPaymentRecords;
import com.Infotrixs.Payroll_System.DTOs.Outgoing.PaySlipReplica;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Duration;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.stream.Collectors;

public class ViewPaymentRecordsController {

    @FXML
    private TableColumn<PaySlipReplica, String> paySlipId;

    @FXML
    private TableColumn<PaySlipReplica, String> employeeId;
    @FXML
    private TableColumn<PaySlipReplica, String> employeeName;

    @FXML
    private TableColumn<PaySlipReplica, String> department;

    @FXML
    private TableColumn<PaySlipReplica, String> employmentLevelColumn;

    @FXML
    private TableColumn<PaySlipReplica, String> designationColumn;

    @FXML
    private TableColumn<PaySlipReplica, Double> baseColumn;

    @FXML
    private TableColumn<PaySlipReplica, Double> houseRestAllowColumn;

    @FXML
    private TableColumn<PaySlipReplica, Double> convenienceAllowColumn;

    @FXML
    private TableColumn<PaySlipReplica, Double> pfColumn;

    @FXML
    private TableColumn<PaySlipReplica, Double> insuranceColumn;

    @FXML
    private TableColumn<PaySlipReplica, Double> inHandColumn;

    @FXML
    private TableColumn<PaySlipReplica, Double> prevDueColumn;

    @FXML
    private TableColumn<PaySlipReplica, Double> deductionForUnpaidLeavesColumn;

    @FXML
    private TableColumn<PaySlipReplica, Integer> unpaidLeavesColumn;

    @FXML
    private TableColumn<PaySlipReplica, String> paymentStatusColumn;


    @FXML
    private TableView<PaySlipReplica> salaryTableView;

    @FXML
    private TextField searchTextField;


    @FXML
    private Label errorLabel;

    private ObservableList<PaySlipReplica> paymentRecordObservableList;

    /*
     * Initializes the controller. Sets up the TableView and TableColumn bindings.
     */
    public void initialize() {

        paySlipId.setCellValueFactory(new PropertyValueFactory<>("paySlipId"));
        employeeId.setCellValueFactory(new PropertyValueFactory<>("employeeId"));
        employeeName.setCellValueFactory(new PropertyValueFactory<>("employeeName"));
        department.setCellValueFactory(new PropertyValueFactory<>("department"));
        employmentLevelColumn.setCellValueFactory(new PropertyValueFactory<>("employmentLevel"));
        designationColumn.setCellValueFactory(new PropertyValueFactory<>("designation"));
        baseColumn.setCellValueFactory(new PropertyValueFactory<>("base"));
        houseRestAllowColumn.setCellValueFactory(new PropertyValueFactory<>("houseRestAllow"));
        convenienceAllowColumn.setCellValueFactory(new PropertyValueFactory<>("convenienceAllow"));
        pfColumn.setCellValueFactory(new PropertyValueFactory<>("pf"));
        insuranceColumn.setCellValueFactory(new PropertyValueFactory<>("insurance"));
        inHandColumn.setCellValueFactory(new PropertyValueFactory<>("inHand"));
        prevDueColumn.setCellValueFactory(new PropertyValueFactory<>("prevDue"));
        deductionForUnpaidLeavesColumn.setCellValueFactory(new PropertyValueFactory<>("deductionForUnpaidLeaves"));
        unpaidLeavesColumn.setCellValueFactory(new PropertyValueFactory<>("unpaidLeaves"));
        paymentStatusColumn.setCellValueFactory(new PropertyValueFactory<>("paymentStatus"));


        paymentRecordObservableList = FXCollections.observableArrayList();
        salaryTableView.setItems(paymentRecordObservableList);

        populatePaymentRecordTableView();
    }

    /*
     * Populates the TableView with payment records fetched from the backend.
     */
    private void populatePaymentRecordTableView() {
        String baseUrl = "http://localhost:8080/admin";

        RestTemplate restTemplate = new RestTemplate();

        String seePaymentRecordsUrl = baseUrl + "/see-payment-records";

        try {
            ResponseEntity<AllPaymentRecords> responseEntity = restTemplate.exchange(
                    seePaymentRecordsUrl,
                    HttpMethod.GET,
                    null,
                    AllPaymentRecords.class
            );

            if (responseEntity.getStatusCode().is2xxSuccessful()) {
                AllPaymentRecords allPaymentRecords = responseEntity.getBody();
                List<PaySlipReplica> paymentRecords = allPaymentRecords.getPaymentRecords();

                paymentRecordObservableList.clear();
                paymentRecordObservableList.addAll(paymentRecords);

            } else {
                displayError(new Exception("Failed to fetch payment records"));
                System.out.println("Error: " + responseEntity.getBody());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /*
     * Handles the action when the "Search" button is clicked. Filters the TableView based on Employee ID.
     */
    @FXML
    private void handleSearchButtonAction(ActionEvent event) {
        String searchText = searchTextField.getText().trim();

        if (searchText.isEmpty()) {
            displayError(new Exception("Enter Employee ID"));
            return;
        }


        List<PaySlipReplica> filteredRecords = paymentRecordObservableList.stream()
                .filter(record -> String.valueOf(record.getEmployeeId()).contains(searchText))
                .collect(Collectors.toList());

        salaryTableView.setItems(FXCollections.observableArrayList(filteredRecords));
    }


    /*
     * Handles the action when the "Reset" button is clicked. Clears the TableView and resets it to the initial state.
     */
    @FXML
    private void handleResetButtonAction(ActionEvent event) {
        searchTextField.clear();

        initialize();
        displayError(new Exception("Table Reset"));
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

