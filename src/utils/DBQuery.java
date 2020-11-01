package utils;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.*;
import java.sql.*;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

/**
 * Handles all queries to the database
 */
public class DBQuery {

    private ObservableList<Appointment> appointments = FXCollections.observableArrayList();
    private ObservableList<User> users = FXCollections.observableArrayList();
    private ObservableList<Contact> contacts = FXCollections.observableArrayList();
    private ObservableList<Customer> customers = FXCollections.observableArrayList();
    private ObservableList<Country> countries = FXCollections.observableArrayList();
    private ObservableList<Division> divisions = FXCollections.observableArrayList();
    private User currentUser;

    /**
     * Calls methods to read all relevant data from the database and store it in ObservableLists
     */
    public void readData() {
        readAppointments();
        readContacts();
        readUsers();
        readCustomers();
        readDivisions();
        readCountries();
    }

    /**
     * Gets the list of all appointments
     * @return the list of all appointments
     */
    public ObservableList<Appointment> getAppointments() {
        return appointments;
    }

    /**
     * Reads all relevant data from the appointments table in the database, stores each record in an Appointment object, and stores the Appointment objects in an ObservableList
     */
    private void readAppointments() {
        try {
            Statement statement = DBConnection.getConn().createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM appointments INNER JOIN contacts ON appointments.contact_id = contacts.contact_id INNER JOIN users ON appointments.user_id = users.user_id");
            while (resultSet.next()) {
                int appointmentID = Integer.parseInt(resultSet.getString("appointment_id").trim());
                String title = resultSet.getString("title").trim();
                String description = resultSet.getString("description").trim();
                String location = resultSet.getString("location").trim();
                String type = resultSet.getString("type").trim();
                Instant start = resultSet.getTimestamp("start").toLocalDateTime().toInstant(ZoneOffset.ofHours(0));
                Instant end = resultSet.getTimestamp("end").toLocalDateTime().toInstant(ZoneOffset.ofHours(0));
                String username = resultSet.getString("user_name").trim();
                String contact = resultSet.getString("contact_name").trim();
                int customerID = Integer.parseInt(resultSet.getString("customer_id").trim());
                int userID = Integer.parseInt(resultSet.getString("user_id").trim());
                int contactID = Integer.parseInt(resultSet.getString("contact_id").trim());
                appointments.add(new Appointment(appointmentID, title, description, location, type, start, end, username, contact, customerID, userID, contactID));
            }
        }
        catch (SQLException e) {
            System.out.println(e);
        }
        catch (NumberFormatException e) {
            System.out.println(e);
        }
    }

    /**
     * Adds a record to the appointments table in the database when a new Appointment object is created
     * @param appointment the new appointment to add to the database
     */
    public void addAppointment(Appointment appointment) {
        if (appointment != null)
            appointments.add(appointment);
        try {
            String sql = "INSERT INTO appointments (appointment_id, title, description, location, type, start, end, create_date, created_by, last_update, last_updated_by, customer_id, user_id, contact_id) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement preparedStatement = DBConnection.getConn().prepareStatement(sql);
            preparedStatement.setString(1, Integer.toString(appointment.getAppointmentID()));
            preparedStatement.setString(2, appointment.getTitle());
            preparedStatement.setString(3, appointment.getDescription());
            preparedStatement.setString(4, appointment.getLocation());
            preparedStatement.setString(5, appointment.getType());
            preparedStatement.setTimestamp(6, Timestamp.valueOf(LocalDateTime.ofInstant(appointment.getStart(), ZoneOffset.ofHours(0))));
            preparedStatement.setTimestamp(7, Timestamp.valueOf(LocalDateTime.ofInstant(appointment.getEnd(), ZoneOffset.ofHours(0))));
            preparedStatement.setTimestamp(8, Timestamp.valueOf(LocalDateTime.now()));
            preparedStatement.setString(9, getCurrentUser().getUsername());
            preparedStatement.setTimestamp(10, Timestamp.valueOf(LocalDateTime.now()));
            preparedStatement.setString(11, getCurrentUser().getUsername());
            preparedStatement.setString(12, Integer.toString(appointment.getCustomerID()));
            preparedStatement.setString(13, Integer.toString(appointment.getUserID()));
            preparedStatement.setString(14, Integer.toString(appointment.getContactID()));
            preparedStatement.executeUpdate();
        }
        catch (SQLException e) {
            System.out.println(e);
        }
    }

    /**
     * Updates a record in the appointments table in the database when an Appointment object is updated
     * @param appointment the appointment to be updated in the database
     */
    public void updateAppointment(Appointment appointment) {
        for (int i = 0; i < appointments.size(); i++) {
            if (appointments.get(i).getAppointmentID() == appointment.getAppointmentID()) {
                appointments.set(i, appointment);
                break;
            }
        }
        try {
            String sql = "UPDATE appointments SET title = ?, description = ?, location = ?, type = ?, start = ?, end = ?, last_update = ?, last_updated_by = ?, customer_id = ?, user_id = ?, contact_id = ? WHERE appointment_id = ?";
            PreparedStatement preparedStatement = DBConnection.getConn().prepareStatement(sql);
            preparedStatement.setString(1, appointment.getTitle());
            preparedStatement.setString(2, appointment.getDescription());
            preparedStatement.setString(3, appointment.getLocation());
            preparedStatement.setString(4, appointment.getType());
            preparedStatement.setTimestamp(5, Timestamp.valueOf(LocalDateTime.ofInstant(appointment.getStart(), ZoneOffset.ofHours(0))));
            preparedStatement.setTimestamp(6, Timestamp.valueOf(LocalDateTime.ofInstant(appointment.getEnd(), ZoneOffset.ofHours(0))));
            preparedStatement.setTimestamp(7, Timestamp.valueOf(LocalDateTime.now()));
            preparedStatement.setString(8, getCurrentUser().getUsername());
            preparedStatement.setString(9, Integer.toString(appointment.getCustomerID()));
            preparedStatement.setString(10, Integer.toString(appointment.getUserID()));
            preparedStatement.setString(11, Integer.toString(appointment.getContactID()));
            preparedStatement.setString(12, Integer.toString(appointment.getAppointmentID()));
            preparedStatement.executeUpdate();
        }
        catch (SQLException e) {
            System.out.println(e);
        }
    }

    /**
     * Deletes a record in the appointments table in the database when an Appointment object is deleted
     * @param appointment the appointment to be deleted in the database
     */
    public void deleteAppointment(Appointment appointment) {
        appointments.remove(appointment);
        try {
            String sql = "DELETE FROM appointments WHERE appointment_id = ? ";
            PreparedStatement preparedStatement = DBConnection.getConn().prepareStatement(sql);
            preparedStatement.setString(1, Integer.toString(appointment.getAppointmentID()));
            preparedStatement.executeUpdate();
        }
        catch (SQLException e) {
            System.out.println(e);
        }
    }

    /**
     * Gets the list of all users
     * @return the list of all users
     */
    public ObservableList<User> getUsers() {
        return users;
    }

    /**
     * Reads all relevant data from the users table in the database, stores each record in an User object, and stores the User objects in an ObservableList
     */
    public void readUsers() {
        try {
            Statement statement = DBConnection.getConn().createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM users");
            while (resultSet.next()) {
                int id = Integer.parseInt(resultSet.getString("user_id").trim());
                String name = resultSet.getString("user_name").trim();
                String password = resultSet.getString("password").trim();
                users.add(new User(id, name, password));
            }
        }
        catch (SQLException e) {
            System.out.println(e);
        }
        catch (NumberFormatException e) {
            System.out.println(e);
        }
    }

    /**
     * Gets the list of all contacts
     * @return the list of all contacts
     */
    public ObservableList<Contact> getContacts() {
        return contacts;
    }

    /**
     * Reads all relevant data from the contacts table in the database, stores each record in an Contact object, and stores the Contact objects in an ObservableList
     */
    public void readContacts() {
        try {
            Statement statement = DBConnection.getConn().createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM contacts");
            while (resultSet.next()) {
                int id = Integer.parseInt(resultSet.getString("contact_id").trim());
                String name = resultSet.getString("contact_name").trim();
                String email = resultSet.getString("email").trim();
                contacts.add(new Contact(id, name, email));
            }
        }
        catch (SQLException e) {
            System.out.println(e);
        }
        catch (NumberFormatException e) {
            System.out.println(e);
        }
    }

    /**
     * Gets the list of all customers
     * @return the list of all customers
     */
    public ObservableList<Customer> getCustomers() {
        return customers;
    }

    /**
     * Reads all relevant data from the customers table in the database, stores each record in an Customer object, and stores the Customer objects in an ObservableList
     */
    public void readCustomers() {
        try {
            Statement statement = DBConnection.getConn().createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM customers INNER JOIN first_level_divisions ON customers.division_id = first_level_divisions.division_id INNER JOIN countries ON first_level_divisions.country_id = countries.country_id");
            while (resultSet.next()) {
                int customerID = Integer.parseInt(resultSet.getString("customer_id").trim());
                String name = resultSet.getString("customer_name").trim();
                String address = resultSet.getString("address").trim();
                String postalCode = resultSet.getString("postal_code").trim();
                String phone = resultSet.getString("phone").trim();
                String division = resultSet.getString("division").trim();
                String country = resultSet.getString("country").trim();
                int divisionID = Integer.parseInt(resultSet.getString("division_id").trim());
                customers.add(new Customer(customerID, name, address, postalCode, phone, division, country, divisionID));
            }
        }
        catch (SQLException e) {
            System.out.println(e);
        }
        catch (NumberFormatException e) {
            System.out.println(e);
        }
    }

    /**
     * Adds a record to the customers table in the database when a new Customer object is created
     * @param customer the new customer to add to the database
     */
    public void addCustomer(Customer customer) {
        if (customer != null)
            customers.add(customer);
        try {
            String sql = "INSERT INTO customers (customer_id, customer_name, address, postal_code, phone, create_date, created_by, last_update, last_updated_by, division_id) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement preparedStatement = DBConnection.getConn().prepareStatement(sql);
            preparedStatement.setString(1, Integer.toString(customer.getCustomerID()));
            preparedStatement.setString(2, customer.getName());
            preparedStatement.setString(3, customer.getAddress());
            preparedStatement.setString(4, customer.getPostalCode());
            preparedStatement.setString(5, customer.getPhone());
            preparedStatement.setString(6, "2020-10-17 07:05:57"); // ADD DATE
            preparedStatement.setString(7, getCurrentUser().getUsername());
            preparedStatement.setString(8, "2020-10-17 07:05:57"); // ADD DATE
            preparedStatement.setString(9, getCurrentUser().getUsername());
            preparedStatement.setString(10, Integer.toString(customer.getDivisionID()));
            preparedStatement.executeUpdate();
        }
        catch (SQLException e) {
            System.out.println(e);
        }
    }

    /**
     * Updates a record in the customers table in the database when a Customer object is updated
     * @param customer the customer to be updated in the database
     */
    public void updateCustomer(Customer customer) {
        for (int i = 0; i < customers.size(); i++) {
            if (customers.get(i).getCustomerID() == customer.getCustomerID()) {
                customers.set(i, customer);
                break;
            }
        }
        try {
            String sql = "UPDATE customers SET customer_name = ?, address = ?, postal_code = ?, phone = ?, last_update = ?, last_updated_by = ?, division_id = ? WHERE customer_id = ?";
            PreparedStatement preparedStatement = DBConnection.getConn().prepareStatement(sql);
            preparedStatement.setString(1, customer.getName());
            preparedStatement.setString(2, customer.getAddress());
            preparedStatement.setString(3, customer.getPostalCode());
            preparedStatement.setString(4, customer.getPhone());
            preparedStatement.setString(5, "2020-10-17 07:05:57"); // ADD DATE
            preparedStatement.setString(6, getCurrentUser().getUsername());
            preparedStatement.setString(7, Integer.toString(customer.getDivisionID()));
            preparedStatement.setString(8, Integer.toString(customer.getCustomerID()));
            preparedStatement.executeUpdate();
        }
        catch (SQLException e) {
            System.out.println(e);
        }
    }

    /**
     * Deletes a record in the customers table in the database when a Customer object is deleted
     * @param customer the customer to be deleted in the database
     */
    public void deleteCustomer(Customer customer) {
        customers.remove(customer);
        try {
            String sql = "DELETE FROM customers WHERE customer_id = ? ";
            PreparedStatement preparedStatement = DBConnection.getConn().prepareStatement(sql);
            preparedStatement.setString(1, Integer.toString(customer.getCustomerID()));
            preparedStatement.executeUpdate();
        }
        catch (SQLException e) {
            System.out.println(e);
        }
    }

    /**
     * Gets the list of all divisions
     * @return the list of all divisions
     */
    public ObservableList<Division> getDivisions() {
        return divisions;
    }

    /**
     * Reads all relevant data from the divisions table in the database, stores each record in an Division object, and stores the Division objects in an ObservableList
     */
    public void readDivisions() {
        try {
            Statement statement = DBConnection.getConn().createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM first_level_divisions INNER JOIN countries ON first_level_divisions.country_id = countries.country_id");
            while (resultSet.next()) {
                int id = Integer.parseInt(resultSet.getString("division_id").trim());
                String name = resultSet.getString("division").trim();
                String country = resultSet.getString("country").trim();
                divisions.add(new Division(id, name, country));
            }
        }
        catch (SQLException e) {
            System.out.println(e);
        }
        catch (NumberFormatException e) {
            System.out.println(e);
        }
    }

    /**
     * Gets the list of all countries
     * @return the list of all countries
     */
    public ObservableList<Country> getCountries() {
        return countries;
    }

    /**
     * Reads all relevant data from the countries table in the database, stores each record in an Country object, and stores the Country objects in an ObservableList
     */
    public void readCountries() {
        try {
            Statement statement = DBConnection.getConn().createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM countries");
            while (resultSet.next()) {
                int id = Integer.parseInt(resultSet.getString("country_id").trim());
                String name = resultSet.getString("country").trim();
                countries.add(new Country(id, name));
            }
        }
        catch (SQLException e) {
            System.out.println(e);
        }
        catch (NumberFormatException e) {
            System.out.println(e);
        }
    }

    /**
     * Gets a User object representing the current user
     * @return a User object representing the current user
     */
    public User getCurrentUser() {
        return currentUser;
    }

    /**
     * Sets a User object representing the current user
     * @param currentUser a User object representing the current user
     */
    public void setCurrentUser(User currentUser) {
        this.currentUser = currentUser;
    }
}