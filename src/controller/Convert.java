package controller;

import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;

import java.time.Instant;
import java.time.LocalTime;

/**
 * Lambda expression interface
 */
public interface Convert {

    /**
     * Gets values from DatePicker and ComboBox fields and converts them to an Instant value
     * @param date a DatePicker field that allows the user to input a LocalDate
     * @param time a ComboBox field that allows the user to input a LocalTime
     * @return an Instant value based on the values in the DatePicker and ComboBox fields
     */
    Instant toInstant(DatePicker date, ComboBox<LocalTime> time);

}
