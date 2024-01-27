/*
 * ViewPaymentRecordsEmpController class is the controller for the window displaying payment records in the Employee Dashboard.
 * It fetches and displays the payment records of the logged-in employee by making an API call to the Spring Boot backend.
 * The displayed records include payslip ID, employee ID, employee name, department, employment level, designation,
 * base salary, house rent allowance, convenience allowance, provident fund (PF), insurance, in-hand salary, previous dues,
 * deduction for unpaid leaves, unpaid leaves, and payment status.
 */
package com.Infotrixs.Payroll_System.PayrollUI.EmployeeDashboard;

import com.Infotrixs.Payroll_System.DTOs.Outgoing.AllPaymentRecords;
import com.Infotrixs.Payroll_System.DTOs.Outgoing.PaySlipReplica;
import com.Infotrixs.Payroll_System.PayrollUI.LoginController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.List;

public class ViewPaymentRecordsEmpController {

    @FXML
    private TableColumn<PaySlipReplica, String> paySlipIdColumn;

    @FXML
    private TableColumn<PaySlipReplica, String> employeeIdColumn;

    @FXML
    private TableColumn<PaySlipReplica, String> employeeNameColumn;

    @FXML
    private TableColumn<PaySlipReplica, String> departmentColumn;

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

    private ObservableList<PaySlipReplica> paymentRecordObservableList;

    private static final String BASE_URL = "http://localhost:8080/employee";

    /*
     * This method is automatically called when the FXML file is loaded.
     * It initializes the payment records table by setting up the table columns and populating the table with data.
     */
    @FXML
    private void initialize() {
        // Initialize the table columns
        LoginController loginController = new LoginController();
        int employeeId = loginController.getempId(); // Replace with the actual admin ID

        initializeTableColumns();

        paymentRecordObservableList = FXCollections.observableArrayList();
        salaryTableView.setItems(paymentRecordObservableList);

        populatePaymentRecordTableView(employeeId);
    }

    /*
     * This method initializes the columns of the payment records table with appropriate cell value factories.
     */
    private void initializeTableColumns() {
        // Define columns
        paySlipIdColumn.setCellValueFactory(new PropertyValueFactory<>("paySlipId"));
        employeeIdColumn.setCellValueFactory(new PropertyValueFactory<>("employeeId"));
        employeeNameColumn.setCellValueFactory(new PropertyValueFactory<>("employeeName"));
        departmentColumn.setCellValueFactory(new PropertyValueFactory<>("department"));
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
    }

    /*
     * This method populates the payment records table view with data retrieved from the backend.
     */
    private void populatePaymentRecordTableView(int employeeId) {
        // Build the URL for the see payment records endpoint
        String seePaymentRecordsUrl = BASE_URL + "/see-payment-records/employee-id/" + employeeId;

        try {
            // Make the API call
            RestTemplate restTemplate = new RestTemplate();
            ResponseEntity<AllPaymentRecords> responseEntity = restTemplate.getForEntity(
                    seePaymentRecordsUrl,
                    AllPaymentRecords.class
            );

            // Handle the response as needed
            if (responseEntity.getStatusCode().is2xxSuccessful()) {
                AllPaymentRecords allPaymentRecords = responseEntity.getBody();
                List<PaySlipReplica> paymentRecords = allPaymentRecords.getPaymentRecords();

                paymentRecordObservableList.clear();
                paymentRecordObservableList.addAll(paymentRecords);

            } else {
                System.out.println("Failed to fetch payment records");
                System.out.println("Error: " + responseEntity.getBody());
            }
        } catch (Exception e) {
            // Handle exceptions
            e.printStackTrace();
        }
    }
}