package SwapNShare.sami.controllers;

import SwapNShare.sami.models.Event;
import SwapNShare.sami.services.EventService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.control.MenuButton;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;

public class AllEvent implements Initializable {

    @FXML
    private GridPane eventsGrid;

    @FXML
    private MenuButton idCategories;

    @FXML
    private MenuButton idCategories1;

    @FXML
    private TextField idSearchField;

    private final EventService eventService = new EventService();

    private List<Event> events;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        EventService eventService = new EventService();
        events = eventService.getAll();

        int column = 0;
        int row = 1;
        try {
            for (Event event: events){
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/EventD.fxml"));
                Pane pane = fxmlLoader.load();
                EventD eventD = fxmlLoader.getController();
                eventD.setData(event);

                if (column == 3){
                    column = 0;
                    ++row;
                }
                eventsGrid.add(pane, column++, row);
                GridPane.setMargin(pane, new Insets(20));
            }
        }catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void switchToAdd(ActionEvent event) throws IOException{
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AddEvent.fxml"));
            Parent root = loader.load();
            eventsGrid.getScene().setRoot(root);
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

    @FXML
    void switchToCalendar(ActionEvent event) throws IOException{
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AllEventCalendar2.fxml"));
            Parent root = loader.load();

            CalendarController controller = loader.getController();

            // Fetch appointments for the desired time range
            LocalDate dateFocus = LocalDate.now(); // Or any other date you want to focus on
            List<Event> events = eventService.fetchEvent(); // Or fetchAppointmentsForWeek depending on your requirements

            controller.setEventsPublic(events);

            Stage stage = new Stage();
            stage.setTitle("Calendar View");
            stage.setScene(new Scene(root));

            stage.show();

            } catch (IOException e) {
                e.printStackTrace();
            }
    }

    public void initializeEventData(Event eventClicked) {
    }
}
