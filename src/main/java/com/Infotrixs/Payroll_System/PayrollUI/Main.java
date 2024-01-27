/*
 * Main class serves as the entry point for the Payroll Application JavaFX application.
 * It extends the Application class and contains the start method to initialize the JavaFX stage.
 */
package com.Infotrixs.Payroll_System.PayrollUI;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    /*
     * The start method initializes the JavaFX stage by loading the LoginWindow.fxml file.
     * It sets the stage title, scene, and makes the stage resizable.
     * The method is automatically called when the JavaFX application is launched.
     *
     * @param primaryStage The primary stage for the JavaFX application.
     * @throws Exception Thrown if an error occurs during the initialization of the JavaFX stage.
     */
    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/LoginWindow.fxml"));
        Parent root = loader.load();

        primaryStage.setTitle("Payroll Application");
        primaryStage.setScene(new Scene(root, 1080, 720));

        // Set resizable to true
        primaryStage.setResizable(true);

        primaryStage.show();
    }

    /*
     * The main method serves as the entry point for the JavaFX application.
     * It calls the launch method to start the JavaFX application.
     *
     * @param args Command-line arguments passed to the application.
     */
    public static void main(String[] args) {
        launch(args);
    }

    /*
     * Method to start the JavaFX application from Spring Boot.
     * This method is used when launching the JavaFX application from another environment, such as Spring Boot.
     *
     * @param args Command-line arguments passed to the application.
     */
    public static void launchJavaFxApp(String[] args) {
        launch(args);
    }
}