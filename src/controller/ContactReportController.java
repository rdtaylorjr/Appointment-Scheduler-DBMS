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
 * Controls the Contact Schedules Report screen
 */
public class ContactReportController extends Controller {

    @FXML
    private TableView<Appointment> costaTable;

    @FXML
    private TableColumn<Appointment, Integer> costaIDColumn;

    @FXML
    private TableColumn<Appointment, String> costaTitleColumn;

    @FXML
    private TableColumn<Appointment, String> costaDescriptionColumn;

    @FXML
    private TableColumn<Appointment, String> costaTypeColumn;

    @FXML
    private TableColumn<Appointment, String> costaStartColumn;

    @FXML
    private TableColumn<Appointment, String> costaEndColumn;

    @FXML
    private TableColumn<Appointment, Integer> costaCustomerColumn;

    @FXML
    private TableView<Appointment> garciaTable;

    @FXML
    private TableColumn<Appointment, Integer> garciaIDColumn;

    @FXML
    private TableColumn<Appointment, String> garciaTitleColumn;

    @FXML
    private TableColumn<Appointment, String> garciaDescriptionColumn;

    @FXML
    private TableColumn<Appointment, String> garciaTypeColumn;

    @FXML
    private TableColumn<Appointment, String> garciaStartColumn;

    @FXML
    private TableColumn<Appointment, String> garciaEndColumn;

    @FXML
    private TableColumn<Appointment, Integer> garciaCustomerColumn;

    @FXML
    private TableView<Appointment> leeTable;

    @FXML
    private TableColumn<Appointment, Integer> leeIDColumn;

    @FXML
    private TableColumn<Appointment, String> leeTitleColumn;

    @FXML
    private TableColumn<Appointment, String> leeDescriptionColumn;

    @FXML
    private TableColumn<Appointment, String> leeTypeColumn;

    @FXML
    private TableColumn<Appointment, String> leeStartColumn;

    @FXML
    private TableColumn<Appointment, String> leeEndColumn;

    @FXML
    private TableColumn<Appointment, Integer> leeCustomerColumn;

    /**
     * Class constructor
     * @param data the complete set of data retrieved from the database by the DBQuery utility
     */
    public ContactReportController(DBQuery data) {
        this.data = data;
    }

    /**
     * Initializes the controller class
     * @param url The location used to resolve relative paths for the root object, or null if the location is not known
     * @param rb The resources used to localize the root object, or null if the root object was not localized
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        generateCostaTable();
        generateGarciaTable();
        generateLeeTable();
    }

    /**
     * Generates the Costa table view. Filters the schedule of appointments to include only those with a Contact set to Anika Costa, populates the table view with that data, then refreshes the table view
     */
    private void generateCostaTable() {
        ObservableList<Appointment> filterAppointments = FXCollections.observableArrayList();
        for (Appointment a : data.getAppointments())
            if (a.getContact().equals("Anika Costa"))
                filterAppointments.add(a);
        costaTable.setItems(filterAppointments);
        costaTable.refresh();
    }

    /**
     * Generates the Garcia table view. Filters the schedule of appointments to include only those with a Contact set to Daniel Garcia, populates the table view with that data, then refreshes the table view
     */
    private void generateGarciaTable() {
        ObservableList<Appointment> filterAppointments = FXCollections.observableArrayList();
        for (Appointment a : data.getAppointments())
            if (a.getContact().equals("Daniel Garcia"))
                filterAppointments.add(a);
        garciaTable.setItems(filterAppointments);
        garciaTable.refresh();
    }

    /**
     * Generates the Lee table view. Filters the schedule of appointments to include only those with a Contact set to Li Lee, populates the table view with that data, then refreshes the table view
     */
    private void generateLeeTable() {
        ObservableList<Appointment> filterAppointments = FXCollections.observableArrayList();
        for (Appointment a : data.getAppointments())
            if (a.getContact().equals("Li Lee"))
                filterAppointments.add(a);
        leeTable.setItems(filterAppointments);
        leeTable.refresh();
    }

}