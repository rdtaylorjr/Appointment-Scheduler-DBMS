package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import model.*;
import utils.DBQuery;

import java.time.*;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.TimeZone;

/**
 * An abstract class that contains instance variables and methods used by multiple screen controllers
 */
public abstract class Controller implements Initializable {

    @FXML
    protected TextField id;

    @FXML
    protected TextField title;

    @FXML
    protected TextField description;

    @FXML
    protected ComboBox<String> location;

    @FXML
    protected ComboBox<String> type;

    @FXML
    protected DatePicker date;

    @FXML
    protected ComboBox<LocalTime> startTime;

    @FXML
    protected ComboBox<LocalTime> endTime;

    @FXML
    protected ComboBox<Integer> customer;

    @FXML
    protected ComboBox<String> user;

    @FXML
    protected ComboBox<String> contact;

    @FXML
    protected TextField name;

    @FXML
    protected TextField address;

    @FXML
    protected TextField postalCode;

    @FXML
    protected TextField phone;

    @FXML
    protected ComboBox<String> division;

    @FXML
    protected ComboBox<String> country;

    @FXML
    protected Button loginButton;

    @FXML
    protected Button addButton;

    @FXML
    protected Button modifyButton;

    @FXML
    protected Button deleteButton;

    @FXML
    protected Button reportsButton;

    @FXML
    protected Button logoutButton;

    @FXML
    protected Button cancelButton;

    @FXML
    protected Button addSaveButton;

    @FXML
    protected Button modifySaveButton;

    @FXML
    protected Label errorLabel;

    @FXML
    protected ToggleGroup radio;

    @FXML
    protected RadioButton typeMonthRadio;

    @FXML
    protected RadioButton contactRadio;

    @FXML
    protected RadioButton locationRadio;

    protected DBQuery data;
    protected Appointment selectedAppointment;
    protected Customer selectedCustomer;

    /**
     * Loads the Appointments screen. Passes the address of the fxml file and a new instance of the controller class to the loadScreen() method. Sets a boolean value indicating whether this is the first time the screen is accessed after logging in
     * @param event mouse input when the user clicks the Log In button or Appointments button
     */
    @FXML
    protected void loadAppointments(MouseEvent event) {
        try {
            boolean alert = false;
            if (event.getSource() == loginButton)
                alert = true;
            String fxml = "/view/appointments.fxml";
            AppointmentsController controller = new AppointmentsController(data, alert);
            loadScreen(event, fxml, controller);
        }
        catch (Exception e) {
            System.out.println(e);
        }
    }

    /**
     * Loads the Customers screen. Passes the address of the fxml file and a new instance of the controller class to the loadScreen() method
     * @param event mouse input when the user clicks the Customers button
     */
    @FXML
    protected void loadCustomers(MouseEvent event) {
        try {
            String fxml = "/view/customers.fxml";
            CustomersController controller = new CustomersController(data);
            loadScreen(event, fxml, controller);
        }
        catch (Exception e) {
            System.out.println(e);
        }
    }

    /**
     * Loads the TypeMonthReport screen. Passes the address of the fxml file and a new instance of the controller class to the loadScreen() method
     * @param event mouse input when the user clicks the Customers button
     */
    @FXML
    protected void loadReports(MouseEvent event) {
        try {
            String fxml = "/view/typeMonthReport.fxml";
            TypeMonthReportController controller = new TypeMonthReportController(data);
            loadScreen(event, fxml, controller);
        }
        catch (Exception e) {
            System.out.println(e);
        }
    }

    /**
     * Loads the ContactReport screen. Passes the address of the fxml file and a new instance of the controller class to the loadScreen() method
     * @param event mouse input when the user clicks the Customers button
     */
    @FXML
    protected void loadContactReport(MouseEvent event) {
        try {
            String fxml = "/view/contactReport.fxml";
            ContactReportController controller = new ContactReportController(data);
            loadScreen(event, fxml, controller);
        }
        catch (Exception e) {
            System.out.println(e);
        }
    }

    /**
     * Loads the LocationReport screen. Passes the address of the fxml file and a new instance of the controller class to the loadScreen() method
     * @param event mouse input when the user clicks the Customers button
     */
    @FXML
    protected void loadLocationReport(MouseEvent event) {
        try {
            String fxml = "/view/locationReport.fxml";
            LocationReportController controller = new LocationReportController(data);
            loadScreen(event, fxml, controller);
        }
        catch (Exception e) {
            System.out.println(e);
        }
    }

    /**
     * Loads a new screen. Receives details about which screen to load from the classes above
     * @param event mouse input when the user clicks a button
     * @param fxml the address of the fxml document
     * @param controller a new instance of the controller class
     */
    protected void loadScreen(MouseEvent event, String fxml, Controller controller) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(fxml));
        ResourceBundle rb = ResourceBundle.getBundle("language_files/rb");
        loader.setController(controller);
        loader.setResources(rb);
        Parent root = loader.load();
        Scene scene = new Scene(root);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    /**
     * Prefills the available options in the ComboBoxes on the Add Appointment and Modify Appointment screens
     * <p>Lambda Expression (Line 236): This lambda expression improves the code by avoiding repetition of the verbose syntax for converting an hour from an integer to a LocalTime. That syntax renders the built-in getItems().addAll() method for filling ComboBoxes unuseable, since it would be needed for every parameter. Additionally, because lambda expression works for both start times and end times, further reducing code repetition.</p>
     */
    protected void fillAppointmentOptions() {
        location.getItems().addAll("Chicago", "Tampa", "Bellingham");
        type.getItems().addAll("Planning Session", "De-Briefing");

        Fill comboBox = (field, h1, h2, h3, h4, h5, h6, h7) -> {
            ObservableList<Integer> hours = FXCollections.observableArrayList();
            hours.addAll(h1, h2, h3, h4, h5, h6, h7);
            for (int h : hours)
                field.getItems().add(LocalDateTime.of(LocalDate.now(),LocalTime.of(h,0)).toInstant(ZoneOffset.ofHours(0)).atZone(ZoneId.of(TimeZone.getDefault().getID())).toLocalTime());
        };
        comboBox.addAll(startTime, 11, 12, 13, 14, 15, 16, 2);
        comboBox.addAll(endTime, 12, 13, 14, 15, 16, 17, 3);

        for (Customer c : data.getCustomers())
            customer.getItems().add(c.getCustomerID());
        for (User u : data.getUsers())
            user.getItems().add(u.getUsername());
        for (Contact c : data.getContacts())
            contact.getItems().add(c.getName());
    }

    /**
     * Prefills the available options in the ComboBoxes on the Add Customer and Modify Customer screens
     */
    protected void fillCustomerOptions() {
        for (Division d : data.getDivisions())
            division.getItems().add(d.getName());
        for (Country c : data.getCountries())
            country.getItems().add(c.getName());
    }

    /**
     * Automatically sets the start time value to 1 hour before the selected end time
     * @param event an action event when the user makes a selection from the endTime ComboBox
     */
    @FXML
    protected void updateStartTime(ActionEvent event) {
        startTime.setValue(endTime.getValue().minusHours(1));
    }

    /**
     * Automatically sets the end time value to 1 hour after the selected start time
     * @param event an action event when the user makes a selection from the startTime ComboBox
     */
    @FXML
    protected void updateEndTime(ActionEvent event) {
        endTime.setValue(startTime.getValue().plusHours(1));
    }

    /**
     * Automatically updates the Division ComboBox values to only those associated with the selected Country
     * @param event an action event when the user makes a selection from the Country ComboBox
     */
    @FXML
    protected void updateDivision(ActionEvent event) {
        ObservableList<String> updateOptions = FXCollections.observableArrayList();
        for (Division d : data.getDivisions())
            if (country.getValue().equals(d.getCountry()))
                updateOptions.add(d.getName());
        division.setItems(updateOptions);
    }

    /**
     * Automatically sets the Country value to the country associated with the selected First Level Division
     * @param event an action event when the user makes a selection from the Division ComboBox
     */
    @FXML
    protected void updateCountry(ActionEvent event) {
        for (Division d : data.getDivisions())
            if (d.getName().equals(division.getValue()))
                country.setValue(d.getCountry());
    }

    /**
     * Checks user input for each of the Add Appointment or Modify Appointment screen fields, displays an error message if any of the fields is empty, if the selected date and time are in the past or are outside of business hours (8am-10pm EST), or if a customer has overlapping appointments, then calls a DBQuery method to save the input data to the database, and returns to the appointments screen
     * <p>Lambda Expression (Line 341): This lambda expression reduces code repetition and allows the verbose syntax needed to convert the values of the date and time fields to Instant values to be written only once</p>
     * @param event mouse input when the user clicks the Save button
     */
    @FXML
    protected void saveAppointment(MouseEvent event) {
        if (title.getText().trim().isEmpty()) {
            errorLabel.setText("Please enter a Title");
            return;
        }
        if (description.getText().trim().isEmpty()) {
            errorLabel.setText("Please enter a Description");
            return;
        }
        if (location.getSelectionModel().isEmpty()) {
            errorLabel.setText("Please select a Location");
            return;
        }
        if (type.getSelectionModel().isEmpty()) {
            errorLabel.setText("Please select a Type");
            return;
        }
        if (date.getValue() == null) {
            errorLabel.setText("Please enter a Date");
            return;
        }
        if (startTime.getValue() == null) {
            errorLabel.setText("Please enter a Start Time");
            return;
        }
        if (endTime.getValue() == null) {
            errorLabel.setText("Please enter an End Time");
            return;
        }

        Convert dateTime = (date, time) -> ZonedDateTime.of(date.getValue(), time.getValue(), ZoneId.of(TimeZone.getDefault().getID())).toInstant();
        Instant start = dateTime.toInstant(date, startTime);
        Instant end = dateTime.toInstant(date, endTime);

        if (start.isBefore(Instant.now())) {
            errorLabel.setText("Please select a future Date and Time");
            return;
        }
        if (start.atZone(ZoneId.of("America/New_York")).toLocalTime().isBefore(LocalTime.of(8,0)) || end.atZone(ZoneId.of("America/New_York")).toLocalTime().isAfter(LocalTime.of(22,0))) {
            errorLabel.setText("Select a time during business hours");
            return;
        }

        if (customer.getSelectionModel().isEmpty()) {
            errorLabel.setText("Please select a Customer ID");
            return;
        }
        for (Appointment a : data.getAppointments()) {
            if (customer.getValue() == a.getCustomerID() && start.equals(a.getStart())) {
                errorLabel.setText("Overlapping appointment");
                return;
            }
        }
        if (user.getSelectionModel().isEmpty()) {
            errorLabel.setText("Please select a User");
            return;
        }
        if (contact.getSelectionModel().isEmpty()) {
            errorLabel.setText("Please select a Contact");
            return;
        }

        int userID = 0, contactID = 0;
        for (User u : data.getUsers())
            if (u.getUsername().equals(user.getValue()))
                userID = u.getID();
        for (Contact c : data.getContacts())
            if (c.getName().equals(contact.getValue()))
                contactID = c.getID();

        Appointment appointment = new Appointment(Integer.parseInt(id.getText()), title.getText().trim(), description.getText().trim(), location.getValue(), type.getValue(), start, end, user.getValue(), contact.getValue(), customer.getValue(), userID, contactID);

        if (event.getSource() == addSaveButton)
            data.addAppointment(appointment);
        if (event.getSource() == modifySaveButton)
            data.updateAppointment(appointment);

        loadAppointments(event);
    }

    /**
     * Checks user input for each of the Add Customer or Modify Customer screen fields, displays an error message if any of the fields is empty, calls a DBQuery method to save the input data to the database, and returns to the appointments screen
     * @param event mouse input when the user clicks the Save button
     */
    @FXML
    protected void saveCustomer(MouseEvent event) {
        if (name.getText().trim().isEmpty()) {
            errorLabel.setText("Please enter a Name");
            return;
        }
        if (address.getText().trim().isEmpty()) {
            errorLabel.setText("Please enter an Address");
            return;
        }
        if (postalCode.getText().trim().isEmpty()) {
            errorLabel.setText("Please enter a Postal Code");
            return;
        }
        if (phone.getText().trim().isEmpty()) {
            errorLabel.setText("Please enter a Phone Number");
            return;
        }
        if (division.getSelectionModel().isEmpty()) {
            errorLabel.setText("Please select a Division");
            return;
        }
        if (country.getSelectionModel().isEmpty()) {
            errorLabel.setText("Please select a Country");
            return;
        }

        int divisionID = 0;
        for (Division d : data.getDivisions())
            if (d.getName().equals(division.getValue()))
                divisionID = d.getID();

        Customer customer = new Customer(Integer.parseInt(id.getText().trim()), name.getText().trim(), address.getText().trim(), postalCode.getText().trim(), phone.getText().trim(), division.getValue(), country.getValue(), divisionID);

        if (event.getSource() == addSaveButton)
            data.addCustomer(customer);
        if (event.getSource() == modifySaveButton)
            data.updateCustomer(customer);

        loadCustomers(event);
    }

    /**
     * Displays a confirmation prompt before deleting an item
     * @param id the ID of the appointment or customer to be deleted
     * @return a boolean value indicating whether the user wants to proceed
     */
    protected boolean confirmationWindow(String id) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Delete");
        alert.setContentText("Are you sure you want to delete" + id + "?");
        alert.setHeaderText(null);
        alert.setGraphic(null);

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            return true;
        }
        else {
            return false;
        }
    }

    /**
     * Clears any error messages
     * @param event mouse input when the user clicks away from the item that caused the message
     */
    @FXML
    protected void clearError(MouseEvent event) {
        errorLabel.setText("");
    }

}