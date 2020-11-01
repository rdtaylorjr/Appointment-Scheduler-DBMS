package controller;

import model.Customer;
import utils.DBQuery;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Controls the Modify Customer screen
 */
public class ModifyCustomerController extends Controller {

    /**
     * Class constructor
     * @param data the complete set of data retrieved from the database by the DBQuery utility
     * @param selectedCustomer the customer selected by the user that is to be modified
     */
    public ModifyCustomerController(DBQuery data, Customer selectedCustomer) {
        this.data = data;
        this.selectedCustomer = selectedCustomer;
    }

    /**
     * Initializes the controller class
     * @param url The location used to resolve relative paths for the root object, or null if the location is not known
     * @param rb The resources used to localize the root object, or null if the root object was not localized
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        fillCustomerOptions();
        fillData();
        errorLabel.setText("");
    }

    /**
     * Prefills the form fields with the corresponding data from the selected customer
     */
    private void fillData() {
        id.setText(Integer.toString(selectedCustomer.getCustomerID()));
        name.setText(selectedCustomer.getName());
        address.setText(selectedCustomer.getAddress());
        postalCode.setText(selectedCustomer.getPostalCode());
        phone.setText(selectedCustomer.getPhone());
        division.setValue(selectedCustomer.getDivision());
        country.setValue(selectedCustomer.getCountry());
    }

}
