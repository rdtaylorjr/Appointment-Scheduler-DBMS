package controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

import model.User;
import utils.DBQuery;

import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

/**
 * Controls the Log In screen
 */
public class LogInController extends Controller {

    @FXML
    private Label loginLabel;

    @FXML
    private TextField username;

    @FXML
    private PasswordField password;

    private ResourceBundle rb;

    /**
     * Class constructor
     * @param data the complete set of data retrieved from the database by the DBQuery utility
     */
    public LogInController(DBQuery data) {
        this.data = data;
    }

    /**
     * Initializes the controller class
     * @param url The location used to resolve relative paths for the root object, or null if the location is not known
     * @param rb The resources used to localize the root object, or null if the root object was not localized
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.rb = rb;
        detectLocale();
    }

    /**
     * Detects the language and time zone set by the user's operating system. Displays all text on the Log In screen in either English or French. Displays the user's current time zone
     */
    private void detectLocale() {
        loginLabel.setText(rb.getString("login"));
        username.setPromptText(rb.getString("username"));
        password.setPromptText(rb.getString("password"));
        loginButton.setText(rb.getString("login"));
        errorLabel.setText(rb.getString("timezone") + " UTC " + ZoneOffset.systemDefault().getRules().getOffset(Instant.now()));
    }

    /**
     * Authenticates the login attempt against user login information stored in the database. If the attempt is successful, sets the current user, records the login attempt, and loads the Appointments screen. If the attempt is unsuccessful, displays an error message in the language set by the user's operating system, and records the login attempt
     * @param event mouse input when the user clicks the Log In button
     */
    @FXML
    private void authenticate(MouseEvent event) {
        for (User u : data.getUsers()) {
            if (u.getUsername().equals(username.getText().trim()) && u.getPassword().equals(password.getText().trim())) {
                data.setCurrentUser(u);
                recordLoginAttempt("Successful");
                loadAppointments(event);
                return;
            }
        }
        errorLabel.setText(rb.getString("error"));
        recordLoginAttempt("Unsuccessful");
    }

    /**
     * Records all user log-in attempts, date and time stamps, and whether each attempt was successful in a file named login_activity.txt. Appends each new record to the existing file and saves to the root folder of the program. Records dates and times in the time zone set by the user's operating system
     * @param successful a String indicating whether the login attempt was successful or unsuccessful
     */
    private void recordLoginAttempt(String successful) {
        try {
            FileWriter myWriter = new FileWriter("login_activity.txt", true);
            myWriter.write("Log in attempt by " + username.getText().trim() + " at " + DateTimeFormatter.ofPattern("HH:mm").format(LocalDateTime.now()) + " on " + DateTimeFormatter.ofPattern("MM-dd-yyyy").format(LocalDateTime.now()) + " was " + successful + "\n");
            myWriter.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

}