package controller;

import model.Appointment;
import utils.DBQuery;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Controls the Add Appointment screen
 */
public class AddAppointmentController extends Controller {

    /**
     * Class constructor
     * @param data the complete set of data retrieved from the database by the DBQuery utility
     */
    public AddAppointmentController(DBQuery data) {
        this.data = data;
    }

    /**
     * Initializes the controller class
     * @param url The location used to resolve relative paths for the root object, or null if the location is not known
     * @param rb The resources used to localize the root object, or null if the root object was not localized
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        fillAppointmentOptions();
        fillData();
        errorLabel.setText("");
    }

    /**
     * Prefills the ID field with the Appointment ID of the selected appointment and the User field with the Username of the current user
     */
    private void fillData() {
        id.setText(Integer.toString(generateAppointmentID()));
        user.setValue(data.getCurrentUser().getUsername());
    }

    /**
     * Generates a unique Appointment ID. Finds the largest existing Appointment ID and adds 1
     * @return a unique Appointment ID
     */
    private int generateAppointmentID() {
        if (data.getAppointments().isEmpty())
            return 1;
        else {
            int i = 1;
            for (Appointment a : data.getAppointments())
                if (a.getAppointmentID() > i)
                    i = a.getAppointmentID();
            return i + 1;
        }
    }

}
