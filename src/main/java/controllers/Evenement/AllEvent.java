package controllers.Evenement;

import services.ServicesEvenement.EventService;
import models.Evenement.Event;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.function.Predicate;

public class AllEvent implements Initializable {

    @FXML
    private GridPane eventsGrid;

    @FXML
    private MenuButton idCategories;

    @FXML
    private MenuButton idDates;

    @FXML
    private TextField idSearchField;

    EventService es = new EventService();

    private final EventService eventService = new EventService();

    private List<Event> events;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        EventService eventService = new EventService();
        events = eventService.getAll();

        int column = 0;
        int row = 1;
        try {
            events = es.getAll();
            List<String> categories= es.ListeCategories();
            idCategories.getItems().get(0).addEventHandler(
                    ActionEvent.ACTION, e->{
                        filtreByCategory("All");
                    }
            );

            for (String cat: categories) {
                MenuItem menuItem = new MenuItem(cat);
                idCategories.getItems().add(menuItem);
                MenuItem menuItem1 = idCategories.getItems().get(idCategories.getItems().size() - 1);
                menuItem1.addEventHandler(ActionEvent.ACTION, e->{
                    filtreByCategory(cat);
                });

            }

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
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        try {
            searchFilter();
            System.out.println("typing");
        } catch (SQLException e) {
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

    private void searchFilter() throws SQLException {
        List<Event> list = es.getAll();


        ObservableList<Event> observableList = FXCollections.observableList(list);
        FilteredList<Event> filteredList= new FilteredList<>(observableList, e->true);
        idSearchField.setOnKeyReleased(e->{

            int columns=3, row=0;
            idSearchField.textProperty().addListener((observable, oldValue, newValue) -> {
                filteredList.setPredicate((Predicate<? super Event >) cust->{
                    if(newValue==null){
                        return true;
                    }else if(cust.getTitre_evenement().toLowerCase().contains(newValue.toLowerCase())){
                        return true;
                    }
                    return false;
                });
            });


            System.out.println(filteredList);
            List<Event> filtered= new ArrayList<>();
            for (Event event : filteredList) {
                filtered.add(event);
            }
            System.out.println(filtered);
            eventsGrid.getChildren().clear();

            for (int i=0; i<filtered.size(); i++){
                System.out.println("n5dm");
                FXMLLoader fxmlLoader= new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/EventD.fxml"));
                try {
                    Pane pane = fxmlLoader.load();
                    EventD eventD= fxmlLoader.getController();
                    eventD.setData(filtered.get(i));
                    System.out.println("wsolt");
                    if(columns == 3)
                    {
                        columns = 0;
                        row++;
                    }
                    eventsGrid.add(pane, ++columns, row);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }


            }
        });

    }

    public void filtreByCategory(String category){
        int columns=3, row=0;
        if(!idCategories.getText().equals(category)){
            eventsGrid.getChildren().clear();
            idCategories.setText(category);
            List<Event> list = new ArrayList<>();
            try {
                if(!category.equals("All")){
                    list = es.rechercherParCategory(es.getCategoryByName(category));
                }
                else {
                    list = es.getAll();
                }
//                if(idDates.getText().equals("Both") || idDates.getText().equals("Filter By Exchange")){
//                    idDates.setText("Both");
//                }
//                if (idDates.getText().equals("Exchangeable")){
//                    idDates.setText("Exchangeable");
//                    list = list.stream().filter(s->s.isChoixEchange()==1).toList();
//
//                }
//                if (idDates.getText().equals("Non exchangeable")){
//                    idDates.setText("Non exchangeable");
//                    list = list.stream().filter(s->s.isChoixEchange()==0).toList();
//
//                }
                for (int i=0; i<list.size(); i++){
                    FXMLLoader fxmlLoader= new FXMLLoader();
                    fxmlLoader.setLocation(getClass().getResource("/EventD.fxml"));
                    VBox box= fxmlLoader.load();
                    EventD eventD= fxmlLoader.getController();
                    eventD.setData(list.get(i));
                    if(columns== 3)
                    {
                        columns = 0;
                        row++;
                    }
                    eventsGrid.add(box, ++columns, row);

                }

            } catch (SQLException e) {
                throw new RuntimeException(e);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }


    }

    public void initializeEventData(Event eventClicked) {
    }
}
