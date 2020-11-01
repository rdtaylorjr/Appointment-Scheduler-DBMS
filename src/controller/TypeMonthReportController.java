package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import model.Appointment;
import model.Count;
import utils.DBQuery;

import java.net.URL;
import java.time.ZoneId;
import java.time.format.TextStyle;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Controls the Appointments by Type & Month Report screen
 */
public class TypeMonthReportController extends Controller {

    @FXML
    private TableView<Count> typeTable;

    @FXML
    private TableColumn<Count, String> typeColumn;

    @FXML
    private TableColumn<Count, Integer> typeAppointmentsColumn;

    @FXML
    private TableView<Count> monthTable;

    @FXML
    private TableColumn<Count, String> monthColumn;

    @FXML
    private TableColumn<Count, Integer> monthAppointmentsColumn;

    /**
     * Class constructor
     * @param data the complete set of data retrieved from the database by the DBQuery utility
     */
    public TypeMonthReportController(DBQuery data) {
        this.data = data;
    }

    /**
     * Initializes the controller class
     * @param url The location used to resolve relative paths for the root object, or null if the location is not known
     * @param rb The resources used to localize the root object, or null if the root object was not localized
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        generateTypeTable();
        generateMonthTable();
    }

    /**
     * Generates the Type table view. Calculates the number of appointments for each type, populates the table view with that data, then refreshes the table view
     */
    private void generateTypeTable() {
        ObservableList<Count> counts = FXCollections.observableArrayList();
        ObservableList<Count> view = FXCollections.observableArrayList();
        Count planning = new Count("Planning Session", 0);
        Count debriefing = new Count("De-Briefing", 0);
        counts.addAll(planning, debriefing);

        for (Appointment a : data.getAppointments())
            for (Count c : counts)
                if (c.getType().equals(a.getType()))
                    c.setCount(c.getCount() + 1);
        for (Count c : counts)
            if (c.getCount() > 0)
                view.add(c);

        typeTable.setItems(view);
        typeTable.refresh();
    }

    /**
     * Generates the Month table view. Calculates the number of appointments for each month, populates the table view with that data, then refreshes the table view
     */
    private void generateMonthTable() {
        ObservableList<Count> counts = FXCollections.observableArrayList();
        ObservableList<Count> view = FXCollections.observableArrayList();
        Count jan = new Count(0, "January");
        Count feb = new Count(0, "February");
        Count mar = new Count(0, "March");
        Count apr = new Count(0, "April");
        Count may = new Count(0, "May");
        Count jun = new Count(0, "June");
        Count jul = new Count(0, "July");
        Count aug = new Count(0, "August");
        Count sep = new Count(0, "September");
        Count oct = new Count(0, "October");
        Count nov = new Count(0, "November");
        Count dec = new Count(0, "December");
        counts.addAll(jan,feb,mar,apr,may,jun,jul,aug,sep,oct,nov,dec);

        for (Appointment a : data.getAppointments())
            for (Count c : counts)
                if (c.getMonth().equals(a.getStart().atZone(ZoneId.systemDefault()).toLocalDate().getMonth().getDisplayName(TextStyle.FULL, Locale.ENGLISH)))
                    c.setCount(c.getCount() + 1);
        for (Count c : counts)
            if (c.getCount() > 0)
                view.add(c);

        monthTable.setItems(view);
        monthTable.refresh();
    }

}