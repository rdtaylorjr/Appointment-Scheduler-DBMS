package controller;

import model.Appointment;
import utils.DBQuery;

import java.net.URL;
import java.time.ZoneId;
import java.util.ResourceBundle;
import java.util.TimeZone;

/**
 * Controls the Modify Appointment screen
 */
public class ModifyAppointmentController extends Controller {

    /**
     * Class constructor
     * @param data the complete set of data retrieved from the database by the DBQuery utility
     * @param selectedAppointment the appointment selected by the user that is to be modified
     */
    public ModifyAppointmentController(DBQuery data, Appointment selectedAppointment) {
        this.data = data;
        this.selectedAppointment = selectedAppointment;
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
     * Prefills the form fields with the corresponding data from the selected appointment. Displays dates and times in the time zone set by the user's operating system
     */
    private void fillData() {
        id.setText(Integer.toString(selectedAppointment.getAppointmentID()));
        title.setText(selectedAppointment.getTitle());
        description.setText(selectedAppointment.getDescription());
        location.setValue(selectedAppointment.getLocation());
        type.setValue(selectedAppointment.getType());
        date.setValue(selectedAppointment.getStart().atZone(ZoneId.of(TimeZone.getDefault().getID())).toLocalDate());
        startTime.setValue(selectedAppointment.getStart().atZone(ZoneId.of(TimeZone.getDefault().getID())).toLocalTime());
        endTime.setValue(selectedAppointment.getEnd().atZone(ZoneId.of(TimeZone.getDefault().getID())).toLocalTime());
        customer.setValue(selectedAppointment.getCustomerID());
        user.setValue(selectedAppointment.getUsername());
        contact.setValue(selectedAppointment.getContact());
    }

}
