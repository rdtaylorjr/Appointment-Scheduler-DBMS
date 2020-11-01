package controller;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;

import model.Appointment;
import utils.DBQuery;

import java.net.URL;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
import java.util.TimeZone;

/**
 * Controls the Appointments screen
 */
public class AppointmentsController extends Controller {

    @FXML
    private TableView<Appointment> table;

    @FXML
    private TableColumn<Appointment, Integer> appointmentIDColumn;

    @FXML
    private TableColumn<Appointment, String> titleColumn;

    @FXML
    private TableColumn<Appointment, String> descriptionColumn;

    @FXML
    private TableColumn<Appointment, String> locationColumn;

    @FXML
    private TableColumn<Appointment, Integer> contactColumn;

    @FXML
    private TableColumn<Appointment, String> typeColumn;

    @FXML
    private TableColumn<Appointment, LocalDateTime> startColumn;

    @FXML
    private TableColumn<Appointment, LocalDateTime> endColumn;

    @FXML
    private TableColumn<Appointment, Integer> customerIDColumn;

    @FXML
    private RadioButton weekRadio;

    @FXML
    private RadioButton monthRadio;

    @FXML
    private RadioButton allRadio;

    @FXML
    private Button customersButton;

    private boolean alert;

    /**
     * Class constructor
     * @param data the complete set of data retrieved from the database by the DBQuery utility
     */
    public AppointmentsController(DBQuery data, boolean alert) {
        this.data = data;
        this.alert = alert;
    }

    /**
     * Initializes the controller class
     * @param url The location used to resolve relative paths for the root object, or null if the location is not known
     * @param rb The resources used to localize the root object, or null if the root object was not localized
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        generateTable(data.getAppointments());
        upcomingAppointment();
    }

    /**
     * Displays a message when there is an appointment associated with the current user that starts within 15 minutes of the user's login. Otherwise displays a message indicating there are no upcoming appointments
     */
    private void upcomingAppointment() {
        if (alert == true) {
            for (Appointment a : data.getAppointments()) {
                if (data.getCurrentUser().getID() == a.getUserID() && a.getStart().isAfter(Instant.now()) && a.getStart().isBefore(Instant.now().plus(Duration.ofMinutes(15)))) {
                    errorLabel.setText("Appointment " + Integer.toString(a.getAppointmentID()) + " on " + DateTimeFormatter.ofPattern("MM-dd-yyyy").format(a.getStart().atZone(ZoneId.of(TimeZone.getDefault().getID()))) + " at " + DateTimeFormatter.ofPattern("HH:mm").format(a.getStart().atZone(ZoneId.of(TimeZone.getDefault().getID()))) + " starts in less than 15 minutes");
                    return;
                }
            }
            errorLabel.setText("There are no upcoming appointments");
        }
    }

    /**
     * Generates the table view and populates it with data from the view ObservableList, refreshes the table view, and clears any error messages
     * @param view an ObservableList containing the data to be displayed in the table
     */
    private void generateTable(ObservableList<Appointment> view) {
        table.setItems(view);
        table.refresh();
        errorLabel.setText("");
    }

    /**
     * Displays all appointments in the table view
     * @param event mouse input when the user selects the All radio button
     */
    @FXML
    private void viewAll(MouseEvent event) {
        generateTable(data.getAppointments());
    }

    /**
     * Filters the appointments to displays only appointments in the current month in the table view
     * @param event mouse input when the user selects the Month radio button
     */
    @FXML
    private void viewMonth(MouseEvent event) {
        ObservableList<Appointment> filterAppointments = FXCollections.observableArrayList();
        for (Appointment a : data.getAppointments())
            if (a.getStart().atZone(ZoneId.systemDefault()).toLocalDate().getMonthValue() == LocalDate.now().getMonthValue())
                filterAppointments.add(a);
        generateTable(filterAppointments);
    }

    /**
     * Filters the appointments to display only appointments in the upcoming 7 days in the table view
     * @param event mouse input when the user selects the Month radio button
     */
    @FXML
    private void viewWeek(MouseEvent event) {
        ObservableList<Appointment> filterAppointments = FXCollections.observableArrayList();
        for (Appointment a : data.getAppointments())
            if (!a.getStart().isBefore(Instant.now()) && a.getStart().isBefore(Instant.now().plus(Duration.ofDays(7))))
                filterAppointments.add(a);
        generateTable(filterAppointments);
    }

    /**
     * Launches the Add Appointment screen
     * @param event mouse input when the user clicks the Add button
     */
    @FXML
    private void addAppointment(MouseEvent event) {
        try {
            String fxml = "/view/addAppointment.fxml";
            AddAppointmentController controller = new AddAppointmentController(data);
            loadScreen(event, fxml, controller);
        }
        catch (Exception e) {
            System.out.println(e);
        }
    }

    /**
     * Launches the Modify Appointment screen. Checks that the schedule of appointments is not empty and that an appointment is currently selected
     * @param event mouse input when the user clicks the Modify button
     */
    @FXML
    private void modifyAppointment(MouseEvent event) {
        try {
            selectedAppointment = table.getSelectionModel().getSelectedItem();
            if (data.getAppointments().isEmpty()) {
                errorLabel.setText("There are no appointments to modify");
                return;
            }
            else if (selectedAppointment == null) {
                errorLabel.setText("Please select an appointment");
                return;
            }

            String fxml = "/view/modifyAppointment.fxml";
            ModifyAppointmentController controller = new ModifyAppointmentController(data, selectedAppointment);
            loadScreen(event, fxml, controller);
        }
        catch (Exception e) {
            System.out.println(e);
        }
    }

    /**
     * Deletes an appointment from the schedule. Checks that the schedule is not empty and that an appointment is currently selected. Asks the user to confirm the deletion. Displays a message upon successful deletion
     * @param event mouse input when the user clicks the Delete button
     */
    @FXML
    private void deleteAppointment(MouseEvent event) {
        Appointment selectedAppointment = table.getSelectionModel().getSelectedItem();
        if (data.getAppointments().isEmpty()) {
            errorLabel.setText("There are no appointments to delete");
            return;
        }
        else if (selectedAppointment == null) {
            errorLabel.setText("Please select an appointment");
            return;
        }
        boolean confirm = confirmationWindow(" Appointment " + Integer.toString(selectedAppointment.getAppointmentID()));
        if (!confirm)
            return;

        data.deleteAppointment(selectedAppointment);
        table.setItems(data.getAppointments());
        table.refresh();
        errorLabel.setText("Appointment " + Integer.toString(selectedAppointment.getAppointmentID()) + " " + selectedAppointment.getType() + " has been cancelled");
    }

    /**
     * Exits the program
     * @param event mouse input when the user clicks the Log Out button
     */
    @FXML
    private void logout(MouseEvent event) {
        Platform.exit();
    }

}
