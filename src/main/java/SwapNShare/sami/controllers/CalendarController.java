package SwapNShare.sami.controllers;

import com.calendarfx.view.CalendarView;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.fxml.Initializable;
import SwapNShare.sami.models.Event;
import com.calendarfx.model.Entry;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.io.IOException;

public class CalendarController implements Initializable {

    @FXML
    private TextField idSearchField;

    @FXML
    private CalendarView idCalendar;

    @FXML
    void back(MouseEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AllEvent.fxml"));
            Parent root = loader.load();
            idCalendar.getScene().setRoot(root);
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

    // Private method to set appointments
    private void setevents(List<Event> events) {
        for (Event event : events) {
            // Convert Appointment to CalendarFX Entry

            Entry<?> entry = new Entry<>(event.getDescription_evenement());
            entry.setInterval(event.getDate_debut(), event.getDate_fin());

            // Set other properties of the entry as needed

            // Add the entry to the calendar
            idCalendar.getCalendarSources().get(0).getCalendars().get(0).addEntry(entry);
        }
    }

    // Public method to set appointments
    public void setEventsPublic(List<Event> events) {
        setevents(events);
    }

    @FXML
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

}
