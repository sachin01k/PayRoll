/*
 * ViewCurrentDueSalariesController class manages the UI for viewing and exporting current due salaries.
 * It communicates with the backend to fetch and display due salary details.
 */
package com.Infotrixs.Payroll_System.PayrollUI.AdminDashboard;

import com.Infotrixs.Payroll_System.DTOs.Outgoing.AllDueSalaries;
import com.Infotrixs.Payroll_System.DTOs.Outgoing.DueSalaryDetails;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.jdbc.support.JdbcUtils.isNumeric;


public class ViewCurrentDueSalariesController {

    @FXML
    private TableView<Salary> salaryTableView;

    @FXML
    private TableColumn<Salary, Integer> salaryIdColumn;

    @FXML
    private TableColumn<Salary, Integer> employeeIdColumn;

    @FXML
    private TableColumn<Salary, String> employeeNameColumn;

    @FXML
    private TableColumn<Salary, Double> baseColumn;

    @FXML
    private TableColumn<Salary, Double> houseRentAllowColumn;

    @FXML
    private TableColumn<Salary, Double> convenienceAllowColumn;

    @FXML
    private TableColumn<Salary, Double> insuranceColumn;

    @FXML
    private TableColumn<Salary, Double> inHandColumn;

    @FXML
    private TableColumn<Salary, Double> previousDuesColumn;

    @FXML
    private TableColumn<Salary, Double> deductionForUnpaidLeavesColumn;

    @FXML
    private TableColumn<Salary, Integer> unpaidLeavesColumn;

    @FXML
    private TableColumn<Salary, Double> netPayColumn;

    @FXML
    private TableColumn<Salary, String> paymentStatusColumn;

    @FXML
    private TableColumn<Salary, String> departmentColumn;

    @FXML
    private TableColumn<Salary, String> employmentLevelColumn;

    @FXML
    private TableColumn<Salary, String> designationColumn;

    @FXML
    private TableColumn<Salary, Double> pfColumn;


    @FXML
    private TextField searchTextField;

    @FXML
    private Label errorLabel;

    private ObservableList<Salary> salaryObservableList;



    /*
     * Initializes the controller. Sets up the TableView and TableColumn bindings.
     */
    public void initialize() {
        // ... (TableColumn bindings)

        salaryIdColumn.setCellValueFactory(new PropertyValueFactory<>("salaryId"));
        employeeIdColumn.setCellValueFactory(new PropertyValueFactory<>("employeeId"));
        employeeNameColumn.setCellValueFactory(new PropertyValueFactory<>("employeeName"));
        baseColumn.setCellValueFactory(new PropertyValueFactory<>("base"));
        houseRentAllowColumn.setCellValueFactory(new PropertyValueFactory<>("houseRentAllow"));
        convenienceAllowColumn.setCellValueFactory(new PropertyValueFactory<>("convenienceAllow"));
        insuranceColumn.setCellValueFactory(new PropertyValueFactory<>("insurance"));
        inHandColumn.setCellValueFactory(new PropertyValueFactory<>("inHand"));
        previousDuesColumn.setCellValueFactory(new PropertyValueFactory<>("previousDues"));
        deductionForUnpaidLeavesColumn.setCellValueFactory(new PropertyValueFactory<>("deductionForUnpaidLeaves"));
        unpaidLeavesColumn.setCellValueFactory(new PropertyValueFactory<>("unpaidLeaves"));
        netPayColumn.setCellValueFactory(new PropertyValueFactory<>("netPay"));
        paymentStatusColumn.setCellValueFactory(new PropertyValueFactory<>("paymentStatus"));
        departmentColumn.setCellValueFactory(new PropertyValueFactory<>("department"));
        employmentLevelColumn.setCellValueFactory(new PropertyValueFactory<>("employmentLevel"));
        designationColumn.setCellValueFactory(new PropertyValueFactory<>("designation"));
        pfColumn.setCellValueFactory(new PropertyValueFactory<>("pf"));

        salaryObservableList = FXCollections.observableArrayList();
        salaryTableView.setItems(salaryObservableList);

        populateSalaryTableView();



    }

    /*
     * Populates the TableView with due salary details fetched from the backend.
     */
    private void populateSalaryTableView() {
        String baseUrl = "http://localhost:8080/admin";

        RestTemplate restTemplate = new RestTemplate();

        String seeDueSalariesUrl = baseUrl + "/see-due-salaries";

        try {
            ResponseEntity<AllDueSalaries> responseEntity = restTemplate.exchange(
                    seeDueSalariesUrl,
                    HttpMethod.GET,
                    null,
                    AllDueSalaries.class
            );

            // Handle the response as needed
            if (responseEntity.getStatusCode().is2xxSuccessful()) {
                AllDueSalaries allDueSalaries = responseEntity.getBody();
                List<DueSalaryDetails> dueSalaries = allDueSalaries.getDueSalaries();

                List<Salary> salaries = convertDueSalariesToSalaries(dueSalaries);

                salaryObservableList.clear();
                salaryObservableList.addAll(salaries);

            } else {
                displayError(new Exception("Failed to fetch due salaries"));
                System.out.print(responseEntity.getBody());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /*
     * Converts a list of DueSalaryDetails to a list of Salary objects.
     */
    private List<Salary> convertDueSalariesToSalaries(List<DueSalaryDetails> dueSalaries) {
        List<Salary> salaries = new ArrayList<>();

        for (DueSalaryDetails dueSalaryDetails : dueSalaries) {
            Salary salary = new Salary();

            salary.setSalaryId(dueSalaryDetails.getSalaryId());
            salary.setEmployeeId(dueSalaryDetails.getEmployeeId());
            salary.setEmployeeName(dueSalaryDetails.getEmployeeName());
            salary.setBase(dueSalaryDetails.getBase());
            salary.setHouseRentAllow(dueSalaryDetails.getHouseRentAllow());
            salary.setConvenienceAllow(dueSalaryDetails.getConvenienceAllow());
            salary.setInsurance(dueSalaryDetails.getInsurance());
            salary.setInHand(dueSalaryDetails.getInHand());
            salary.setPreviousDues(dueSalaryDetails.getPreviousDues());
            salary.setDeductionForUnpaidLeaves(dueSalaryDetails.getDeductionForUnpaidLeaves());
            salary.setUnpaidLeaves(dueSalaryDetails.getUnpaidLeaves());
            salary.setNetPay(dueSalaryDetails.getNetPay());
            salary.setPaymentStatus(dueSalaryDetails.getPaymentStatus());
            salary.setDepartment(dueSalaryDetails.getDepartment());
            salary.setEmploymentLevel(dueSalaryDetails.getEmploymentLevel());
            salary.setDesignation(dueSalaryDetails.getDesignation());
            salary.setPf(dueSalaryDetails.getPF());

            salaries.add(salary);
        }

        return salaries;
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

        if (!isNumeric(Integer.parseInt(searchText))) {
            displayError(new Exception("Employee ID can only be numeric"));
            return;
        }

        if (!searchText.isEmpty()) {
            ObservableList<Salary> filteredData = salaryTableView.getItems().filtered(
                    salary -> String.valueOf(salary.getEmployeeId()).contains(searchText)
            );


            salaryTableView.setItems(filteredData);
        }
    }


    /*
     * Handles the action when the "Reset" button is clicked. Clears the TableView and resets it to the initial state.
     */
    @FXML
    private void handleResetButtonAction(ActionEvent event) {

        salaryObservableList.clear();

        displayError(new Exception("Table Reset"));
        initialize();
    }

    /*
     * Handles the action when the "Export to CSV" button is clicked. Exports TableView data to a CSV file.
     */
    @FXML
    private void handleExportToCSVButtonAction(ActionEvent event) {
        try {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Save CSV File");
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("CSV Files", "*.csv"));
            File file = fileChooser.showSaveDialog(new Stage());

            if (file != null) {
                exportToCSV(file);
                displayError(new Exception("Data exported to CSV successfully."));
            }
        } catch (IOException e) {
            displayError(new Exception("Error exporting data to CSV."));
            e.printStackTrace();
        }
    }

    /*
     * Exports the data in the TableView to a CSV file.
     */
    private void exportToCSV(File file) throws IOException {
        try (FileWriter writer = new FileWriter(file)) {
            // Write CSV header
            writer.append("Salary ID,Employee ID,Employee Name,Base,House Rent Allow,Convenience Allow,Insurance,In Hand,Previous Dues,Deduction for Unpaid Leaves,Unpaid Leaves,Net Pay,Payment Status,Department,Employment Level,Designation,PF\n");

            // Write CSV data
            for (Salary salary : salaryObservableList) {
                writer.append(String.format("%d,%d,%s,%.2f,%.2f,%.2f,%.2f,%.2f,%.2f,%.2f,%d,%.2f,%s,%s,%s,%s,%.2f\n",
                        salary.getSalaryId(), salary.getEmployeeId(), salary.getEmployeeName(),
                        salary.getBase(), salary.getHouseRentAllow(), salary.getConvenienceAllow(),
                        salary.getInsurance(), salary.getInHand(), salary.getPreviousDues(),
                        salary.getDeductionForUnpaidLeaves(), salary.getUnpaidLeaves(), salary.getNetPay(),
                        salary.getPaymentStatus(), salary.getDepartment(), salary.getEmploymentLevel(),
                        salary.getDesignation(), salary.getPf()));
            }
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

