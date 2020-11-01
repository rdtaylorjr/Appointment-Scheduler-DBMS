package main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import utils.*;

import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Appointment Management System<br />
 * C195 Software II (Fall 2020)<br />
 * Western Governors University
 *
 * @file Main.java
 * @author Russell Taylor
 * @date 10/21/2020
 */
public class Main extends Application {

    /**
     * Loads the database data into memory, then loads and displays the Log In screen
     * @param primaryStage the primary stage for the program's initial GUI screen
     */
    @Override
    public void start(Stage primaryStage) throws Exception {
        DBQuery data = new DBQuery();
        data.readData();
        ResourceBundle rb = ResourceBundle.getBundle("language_files/rb", Locale.getDefault());
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/login.fxml"));
        controller.LogInController controller = new controller.LogInController(data);
        loader.setController(controller);
        loader.setResources(rb);
        Parent root = loader.load();
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    /**
     * Opens the database connection, begins operation of the program, and closes the database connection
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        DBConnection.startConnection();
        launch(args);
        DBConnection.closeConnection();
    }
}
