package org.example.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import org.example.models.Service;
import org.example.services.GestionService;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.function.Predicate;

public class Services implements Initializable {



    GestionService gs = new GestionService();
    public TextField idSearchField;

    public ImageView idSearch;

    public MenuButton idExchange;
    public GridPane serviceGrid;
    public Button idAdd;
    public MenuButton idCategories;



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        List<Service> list = new ArrayList<>();
        int columns=3, row=0;
        try {
            list = gs.Afficher();
            List<String> categories= gs.ListeCategories();
            idCategories.getItems().get(0).addEventHandler(
                    ActionEvent.ACTION, e->{
                        filtreByCategory("All");
                    }
            );

            idExchange.getItems().get(0).addEventHandler(
                    ActionEvent.ACTION, e->{
                        filterByExchangeChoice("Both");
                    }
            );
            idExchange.getItems().get(1).addEventHandler(
                    ActionEvent.ACTION, e->{
                        filterByExchangeChoice("Exchangeable");
                    }
            );
            idExchange.getItems().get(2).addEventHandler(
                    ActionEvent.ACTION, e->{
                        filterByExchangeChoice("Non exchangeable");
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
            for (int i=0; i<list.size(); i++){
                FXMLLoader fxmlLoader= new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/SingleService.fxml"));
                VBox box= fxmlLoader.load();
                SingleService singleService= fxmlLoader.getController();
                singleService.setData(list.get(i));
                if(columns== 3)
                {
                    columns = 0;
                    row++;
                }
                serviceGrid.add(box, ++columns, row);

            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        try {
            searchFilter();
            System.out.println("typing");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }


    public void addService(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AddService.fxml"));
            Parent root = loader.load();
            serviceGrid.getScene().setRoot(root);
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

    public void filtreByCategory(String category){
        int columns=3, row=0;
        if(!idCategories.getText().equals(category)){
            serviceGrid.getChildren().clear();
            idCategories.setText(category);
            List<Service> list = new ArrayList<>();
            try {
                if(!category.equals("All")){
                    list = gs.rechercherParCategory(gs.getCategoryByName(category));
                    for (int i=0; i<list.size(); i++){
                        FXMLLoader fxmlLoader= new FXMLLoader();
                        fxmlLoader.setLocation(getClass().getResource("/SingleService.fxml"));
                        VBox box= fxmlLoader.load();
                        SingleService singleService= fxmlLoader.getController();
                        singleService.setData(list.get(i));
                        if(columns== 3)
                        {
                            columns = 0;
                            row++;
                        }
                        serviceGrid.add(box, ++columns, row);

                    }
                }else {
                    list = gs.Afficher();
                    for (int i=0; i<list.size(); i++){
                        FXMLLoader fxmlLoader= new FXMLLoader();
                        fxmlLoader.setLocation(getClass().getResource("/SingleService.fxml"));
                        VBox box= fxmlLoader.load();
                        SingleService singleService= fxmlLoader.getController();
                        singleService.setData(list.get(i));
                        if(columns== 3)
                        {
                            columns = 0;
                            row++;
                        }
                        serviceGrid.add(box, ++columns, row);

                    }
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

    }

    public void filterByExchangeChoice(String exchange){
        int columns=3, row=0;
        if(!idExchange.getText().equals(exchange)){
            serviceGrid.getChildren().clear();
            idExchange.setText(exchange);
            List<Service> list = new ArrayList<>();
            try {
                if(!exchange.equals("Both")){
                    list = gs.filterByEchange(exchange);
                    for (int i=0; i<list.size(); i++){
                        FXMLLoader fxmlLoader= new FXMLLoader();
                        fxmlLoader.setLocation(getClass().getResource("/SingleService.fxml"));
                        VBox box= fxmlLoader.load();
                        SingleService singleService= fxmlLoader.getController();
                        singleService.setData(list.get(i));
                        if(columns== 3)
                        {
                            columns = 0;
                            row++;
                        }
                        serviceGrid.add(box, ++columns, row);

                    }
                }else {
                    list = gs.Afficher();
                    for (int i=0; i<list.size(); i++){
                        FXMLLoader fxmlLoader= new FXMLLoader();
                        fxmlLoader.setLocation(getClass().getResource("/SingleService.fxml"));
                        VBox box= fxmlLoader.load();
                        SingleService singleService= fxmlLoader.getController();
                        singleService.setData(list.get(i));
                        if(columns== 3)
                        {
                            columns = 0;
                            row++;
                        }
                        serviceGrid.add(box, ++columns, row);

                    }
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }



    private void searchFilter() throws SQLException {
        List<Service> list = gs.Afficher();


        ObservableList<Service> observableList = FXCollections.observableList(list);
        FilteredList<Service> filteredList= new FilteredList<>(observableList, e->true);
        idSearchField.setOnKeyReleased(e->{

            int columns=3, row=0;
            idSearchField.textProperty().addListener((observable, oldValue, newValue) -> {
                filteredList.setPredicate((Predicate<? super Service >) cust->{
                    if(newValue==null){
                        return true;
                    }else if(cust.getTitreService().toLowerCase().contains(newValue.toLowerCase())){
                        return true;
                    }else if(cust.getVille().toLowerCase().contains(newValue.toLowerCase())){
                        return true;
                    }
                    return false;
                });
            });

            /*final SortedList<Customer> customers = new SortedList<>(filterData);
            customers.comparatorProperty().bind(tblCustomer.comparatorProperty());*/
            System.out.println(filteredList);
            List<Service> filtered= new ArrayList<>();
            for (Service service : filteredList) {
                filtered.add(service);
            }
            System.out.println(filtered);
            serviceGrid.getChildren().clear();

            for (int i=0; i<filtered.size(); i++){
                System.out.println("n5dm");
                FXMLLoader fxmlLoader= new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/SingleService.fxml"));
                try {
                    VBox box = fxmlLoader.load();
                    SingleService singleService= fxmlLoader.getController();
                    singleService.setData(filtered.get(i));
                    System.out.println("wsolt");
                    if(columns== 3)
                    {
                        columns = 0;
                        row++;
                    }
                    serviceGrid.add(box, ++columns, row);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }



            }

            //ok let's check it
        });

    }
}
