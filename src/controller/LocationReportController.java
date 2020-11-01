package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import model.Appointment;
import utils.DBQuery;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Controls the Appointments by Location Report screen
 */
public class LocationReportController extends Controller {

    @FXML
    private TableView<Appointment> chicagoTable;

    @FXML
    private TableColumn<Appointment, Integer> chicagoIDColumn;

    @FXML
    private TableColumn<Appointment, String> chicagoTitleColumn;

    @FXML
    private TableColumn<Appointment, String> chicagoDescriptionColumn;

    @FXML
    private TableColumn<Appointment, String> chicagoTypeColumn;

    @FXML
    private TableColumn<Appointment, String> chicagoStartColumn;

    @FXML
    private TableColumn<Appointment, String> chicagoEndColumn;

    @FXML
    private TableColumn<Appointment, Integer> chicagoCustomerColumn;

    @FXML
    private TableView<Appointment> tampaTable;

    @FXML
    private TableColumn<Appointment, Integer> tampaIDColumn;

    @FXML
    private TableColumn<Appointment, String> tampaTitleColumn;

    @FXML
    private TableColumn<Appointment, String> tampaDescriptionColumn;

    @FXML
    private TableColumn<Appointment, String> tampaTypeColumn;

    @FXML
    private TableColumn<Appointment, String> tampaStartColumn;

    @FXML
    private TableColumn<Appointment, String> tampaEndColumn;

    @FXML
    private TableColumn<Appointment, Integer> tampaCustomerColumn;

    @FXML
    private TableView<Appointment> bellinghamTable;

    @FXML
    private TableColumn<Appointment, Integer> bellinghamIDColumn;

    @FXML
    private TableColumn<Appointment, String> bellinghamTitleColumn;

    @FXML
    private TableColumn<Appointment, String> bellinghamDescriptionColumn;

    @FXML
    private TableColumn<Appointment, String> bellinghamTypeColumn;

    @FXML
    private TableColumn<Appointment, String> bellinghamStartColumn;

    @FXML
    private TableColumn<Appointment, String> bellinghamEndColumn;

    @FXML
    private TableColumn<Appointment, Integer> bellinghamCustomerColumn;

    /**
     * Class constructor
     * @param data the complete set of data retrieved from the database by the DBQuery utility
     */
    public LocationReportController(DBQuery data) {
        this.data = data;
    }

    /**
     * Initializes the controller class
     * @param url The location used to resolve relative paths for the root object, or null if the location is not known
     * @param rb The resources used to localize the root object, or null if the root object was not localized
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        generateChicagoTable();
        generateTampaTable();
        generateBellinghamTable();
    }

    /**
     * Generates the Chicago table view. Filters the schedule of appointments to include only those with a Location set to Chicago, populates the table view with that data, then refreshes the table view
     */
    private void generateChicagoTable() {
        ObservableList<Appointment> filterAppointments = FXCollections.observableArrayList();
        for (Appointment a : data.getAppointments())
            if (a.getLocation().equals("Chicago"))
                filterAppointments.add(a);
        chicagoTable.setItems(filterAppointments);
        chicagoTable.refresh();
    }

    /**
     * Generates the Tampa table view. Filters the schedule of appointments to include only those with a Location set to Tampa, populates the table view with that data, then refreshes the table view
     */
    private void generateTampaTable() {
        ObservableList<Appointment> filterAppointments = FXCollections.observableArrayList();
        for (Appointment a : data.getAppointments())
            if (a.getLocation().equals("Tampa"))
                filterAppointments.add(a);
        tampaTable.setItems(filterAppointments);
        tampaTable.refresh();
    }

    /**
     * Generates the Bellingham table view. Filters the schedule of appointments to include only those with a Location set to Bellingham, populates the table view with that data, then refreshes the table view
     */
    private void generateBellinghamTable() {
        ObservableList<Appointment> filterAppointments = FXCollections.observableArrayList();
        for (Appointment a : data.getAppointments())
            if (a.getLocation().equals("Bellingham"))
                filterAppointments.add(a);
        bellinghamTable.setItems(filterAppointments);
        bellinghamTable.refresh();
    }

}