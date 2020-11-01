Title: Appointment Management System

Purpose: This program provides an easy-to-use graphical user interface for managing customer and appointment data within the organization's database. The program allows the user to add, modify, and delete customers and appointments. It notifies the user of upcoming appointments, generates various reports based on added appointments, displays all dates and times in the local timezone set by the user's operating system, and displays the login screen in either English or French based on the user's operating system settings. This functionality is designed to streamline the organization's internal processes in order to strengthen its ongoing relationships with its customers.

Author: Russell Taylor

Contact Information: rtay343@wgu.edu

Application version: 1.02

Date: 10/21/2020

IDE version: IntelliJ IDEA 2020.2.3

JDK version: Java SE 11.0.8

JavaFX version: JavaFX-SDK-11.0.2

Directions for How to Run the Program:
Log In Screen
Before accessing the customer and appointment data, the user must enter a valid username and password
This program does not provide functionality for adding or modifying user information
The current usernames and passwords available are test/test and admin/admin
All login attempts are recorded in the file login-activity.txt

Appointments Screen
Upon logging in, the program will load the appointments screen
A notification indicates whether any appointments associated with the current user will start within 15 minutes of login
The main table view displays all appointments in the database
Appointments in the table can be filtered to display only appointments in the current month or only appointments in the next 7 days using the Month and Week radio buttons at the top of the screen
The Add button will load the Add Appointment screen
The Modify button will load the Modify Appointment screen. An appointment must be selected in the table view
The Delete button will delete the selected appointment. An appointment must be selected in the table view
The Customers button will load the Customers screen
The Reports button will load the Reports screen
The Log Out button will close the connection to the database and close the program

Add Appointments Screen
The appointment ID is auto-generated and cannot be changed
All fields must be completed
Appointments are 1 hour long and can only be scheduled within the organization's business hours (8am-10pm EST)
Customers may not have overlapping appointments

Modify Appointments Screen
All information for the selected appointment is pre-filled
All of the same validation checks apply as when adding an appointment

Customers Screen
The main table view displays all customers in the database
The Add button will load the Add Customer screen
The Modify button will load the Modify Customer screen. A customer must be selected in the table view
The Delete button will delete the currently selected customer. A customer must be selected in the table view
A customer cannot be deleted if that customer is associated with any appointments. All of the customers appointments must first be deleted.
The Appointments button will return to the Appointments screen

Add Customer Screen
The customer ID is auto-generated and cannot be changed
All fields must be completed

Modify Customer Screen
All information for the selected customer is pre-filled
All of the same validation checks apply as when adding a customer

Reports Screen
Three automatically generated reports are available
The Appointments by Type & Month report displays the total number of appointments by Type and by Month
The Contact Schedules report filters and displays appointments by Contact
The Location report filters and displays appointments by Location

Description of the Additional Report: The additional report included in the program filters and displays the appointments by location. The report includes appointment ID, title, type, description, start date and time, end date and time, and customer ID for each appointment.