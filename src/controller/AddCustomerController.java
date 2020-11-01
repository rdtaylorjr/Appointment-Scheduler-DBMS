package controller;

import model.Customer;
import utils.DBQuery;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Controls the Add Customer screen
 */
public class AddCustomerController extends Controller {

    /**
     * Class constructor
     * @param data the complete set of data retrieved from the database by the DBQuery utility
     */
    public AddCustomerController(DBQuery data) {
        this.data = data;
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
     * Prefills the ID field with the Customer ID of the selected customer
     */
    private void fillData() {
        id.setText(Integer.toString(generateCustomerID()));
    }

    /**
     * Generates a unique Customer ID. Finds the largest existing Customer ID and adds 1
     * @return a unique Customer ID
     */
    private int generateCustomerID() {
        if (data.getCustomers().isEmpty())
            return 1;
        else {
            int i = 1;
            for (Customer a : data.getCustomers())
                if (a.getCustomerID() > i)
                    i = a.getCustomerID();
            return i + 1;
        }
    }

}
