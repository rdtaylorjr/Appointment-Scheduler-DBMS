package controller;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;

import model.Appointment;
import model.Customer;
import utils.DBQuery;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Controls the Customers screen
 */
public class CustomersController extends Controller {

    @FXML
    private TableView<Customer> table;

    @FXML
    private TableColumn<Customer, Integer> customerIDColumn;

    @FXML
    private TableColumn<Customer, String> nameColumn;

    @FXML
    private TableColumn<Customer, String> addressColumn;

    @FXML
    private TableColumn<Customer, String> postalCodeColumn;

    @FXML
    private TableColumn<Customer, String> phoneColumn;

    @FXML
    private TableColumn<Customer, String> divisionColumn;

    @FXML
    private TableColumn<Customer, String> countryColumn;

    @FXML
    private Button appointmentsButton;

    /**
     * Class constructor
     * @param data the complete set of data retrieved from the database by the DBQuery utility
     */
    public CustomersController(DBQuery data) {
        this.data = data;
    }

    /**
     * Initializes the controller class
     * @param url The location used to resolve relative paths for the root object, or null if the location is not known
     * @param rb The resources used to localize the root object, or null if the root object was not localized
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        table.setItems(data.getCustomers());
        table.refresh();
        errorLabel.setText("");
    }

    /**
     * Launches the Add Customer screen
     * @param event mouse input when the user clicks the Add button
     */
    @FXML
    private void addCustomer(MouseEvent event) {
        try {
            String fxml = "/view/addCustomer.fxml";
            AddCustomerController controller = new AddCustomerController(data);
            loadScreen(event, fxml, controller);
        }
        catch (Exception e) {
            System.out.println(e);
        }
    }

    /**
     * Launches the Modify Customer screen. Checks that the list of customers is not empty and that a customer is currently selected
     * @param event mouse input when the user clicks the Modify button
     */
    @FXML
    private void modifyCustomer(MouseEvent event) {
        try {
            selectedCustomer = table.getSelectionModel().getSelectedItem();
            if (data.getCustomers().isEmpty()) {
                errorLabel.setText("There are no customers to modify");
                return;
            }
            else if (selectedCustomer == null) {
                errorLabel.setText("Please select a customer");
                return;
            }

            String fxml = "/view/modifyCustomer.fxml";
            ModifyCustomerController controller = new ModifyCustomerController(data, selectedCustomer);
            loadScreen(event, fxml, controller);
        }
        catch (Exception e) {
            System.out.println(e);
        }
    }

    /**
     * Deletes a customer from the list of customers. Checks that the list is not empty, that a customer is currently selected, and that the customer has no appointments. Asks the user to confirm the deletion. Displays a message upon successful deletion
     * @param event mouse input when the user clicks the Delete button
     */
    @FXML
    private void deleteCustomer(MouseEvent event) {
        Customer selectedCustomer = table.getSelectionModel().getSelectedItem();
        if (data.getCustomers().isEmpty()) {
            errorLabel.setText("There are no customers to delete");
            return;
        }
        else if (selectedCustomer == null) {
            errorLabel.setText("Please select a customer");
            return;
        }
        for (Appointment a : data.getAppointments()) {
            if (a.getCustomerID() == selectedCustomer.getCustomerID()) {
                errorLabel.setText("Please delete the customer's appointments first");
                return;
            }
        }
        boolean confirm = confirmationWindow(" Customer " + Integer.toString(selectedCustomer.getCustomerID()));
        if (!confirm)
            return;

        data.deleteCustomer(selectedCustomer);
        table.setItems(data.getCustomers());
        table.refresh();
        errorLabel.setText("Customer " + selectedCustomer.getName() + " has been deleted");
    }

}
