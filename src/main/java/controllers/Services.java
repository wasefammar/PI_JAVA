package controllers;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import models.Service;
import services.GestionService;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.function.Predicate;

public class Services implements Initializable {


    public ScrollPane idScrollPane;
    public VBox idVbox;
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
        Pagination pagination = new Pagination();
        //int columns =3, row = 0;
        try {
            list = gs.Afficher();
            pagination.setPageCount((int) Math.ceil((double) list.size() /3));

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
            /*for (int i=0; i<list.size(); i++){
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

            }*/

            List<Service> finalList = list;
            pagination.setPageFactory(pageIndex -> {
                final int[] columns = {3};
                final int[] row = { 0 };
                int startIndex = pageIndex * 3;
                int endIndex = Math.min(startIndex + 3, finalList.size());

                GridPane services = new GridPane();
                services.setHgap(30);
                services.setVgap(10);


                for (int i = startIndex; i < endIndex; i++) {
                    FXMLLoader fxmlLoader = new FXMLLoader();
                    fxmlLoader.setLocation(getClass().getResource("/SingleService.fxml"));
                    VBox box = null;
                    try {
                        box = fxmlLoader.load();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    SingleService singleService = fxmlLoader.getController();
                    try {
                        singleService.setData(finalList.get(i));
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }

                    VBox finalBox1 = box;
                    Platform.runLater(() -> { // Run UI updates on the JavaFX thread
                        if(columns[0] == 3)
                        {
                            columns[0] = 0;
                            row[0]++;
                        }
                        services.add(finalBox1, ++columns[0], row[0]);

                    });
                }

                return services; // Pagination control doesn't need a Node for the page
            });
             //serviceGrid.getChildren().add(pagination);
             idScrollPane.setContent(pagination);



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
        Pagination pagination = new Pagination();
        if(!idCategories.getText().equals(category)){
            serviceGrid.getChildren().clear();
            idCategories.setText(category);
            List<Service> list = new ArrayList<>();
            try {
                if(!category.equals("All")){
                    list = gs.rechercherParCategory(gs.getCategoryByName(category));
                }
                else {
                    list = gs.Afficher();
                }
                if(idExchange.getText().equals("Both") || idExchange.getText().equals("Filter By Exchange")){
                    idExchange.setText("Both");
                }
                if (idExchange.getText().equals("Exchangeable")){
                    idExchange.setText("Exchangeable");
                    list = list.stream().filter(s->s.isChoixEchange()==1).toList();

                }
                if (idExchange.getText().equals("Non exchangeable")){
                    idExchange.setText("Non exchangeable");
                    list = list.stream().filter(s->s.isChoixEchange()==0).toList();

                }
                pagination.setPageCount((int) Math.ceil((double) list.size() /3));
/*                for (int i=0; i<list.size(); i++){
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

                }*/
                List<Service> finalList = list;
                pagination.setPageFactory(pageIndex -> {
                    final int[] columns = {3};
                    final int[] row = { 0 };
                    int startIndex = pageIndex * 3;
                    int endIndex = Math.min(startIndex + 3, finalList.size());

                    GridPane services = new GridPane();
                    services.setHgap(30);
                    services.setVgap(10);


                    for (int i = startIndex; i < endIndex; i++) {
                        FXMLLoader fxmlLoader = new FXMLLoader();
                        fxmlLoader.setLocation(getClass().getResource("/SingleService.fxml"));
                        VBox box = null;
                        try {
                            box = fxmlLoader.load();
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                        SingleService singleService = fxmlLoader.getController();
                        try {
                            singleService.setData(finalList.get(i));
                        } catch (SQLException e) {
                            throw new RuntimeException(e);
                        }

                        VBox finalBox1 = box;
                        Platform.runLater(() -> { // Run UI updates on the JavaFX thread
                            if(columns[0] == 3)
                            {
                                columns[0] = 0;
                                row[0]++;
                            }
                            services.add(finalBox1, ++columns[0], row[0]);

                        });
                    }

                    return services; // Pagination control doesn't need a Node for the page
                });
                //serviceGrid.getChildren().add(pagination);
                idScrollPane.setContent(pagination);

            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }


    }

    public void filterByExchangeChoice(String exchange){
        Pagination pagination = new Pagination();
        if(!idExchange.getText().equals(exchange)){
            serviceGrid.getChildren().clear();
            idExchange.setText(exchange);
            List<Service> list = new ArrayList<>();
            try {
                if(!exchange.equals("Both")){
                    list = gs.filterByEchange(exchange);
                }else {
                    list = gs.Afficher();
                }
                if(idCategories.getText().equals("All") || idCategories.getText().equals("Filter By Category")){
                    idCategories.setText("All");
                }
                else {
                    list = list.stream().filter(s-> {
                        try {
                            return s.getIdCategorie()==gs.getCategoryByName(idCategories.getText());
                        } catch (SQLException e) {
                            throw new RuntimeException(e);
                        }
                    }).toList();

                }
                pagination.setPageCount((int) Math.ceil((double) list.size() /3));

                /*for (int i=0; i<list.size(); i++){
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

                }*/
                List<Service> finalList = list;
                pagination.setPageFactory(pageIndex -> {
                    final int[] columns = {3};
                    final int[] row = { 0 };
                    int startIndex = pageIndex * 3;
                    int endIndex = Math.min(startIndex + 3, finalList.size());

                    GridPane services = new GridPane();
                    services.setHgap(30);
                    services.setVgap(10);


                    for (int i = startIndex; i < endIndex; i++) {
                        FXMLLoader fxmlLoader = new FXMLLoader();
                        fxmlLoader.setLocation(getClass().getResource("/SingleService.fxml"));
                        VBox box = null;
                        try {
                            box = fxmlLoader.load();
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                        SingleService singleService = fxmlLoader.getController();
                        try {
                            singleService.setData(finalList.get(i));
                        } catch (SQLException e) {
                            throw new RuntimeException(e);
                        }

                        VBox finalBox1 = box;
                        Platform.runLater(() -> { // Run UI updates on the JavaFX thread
                            if(columns[0] == 3)
                            {
                                columns[0] = 0;
                                row[0]++;
                            }
                            services.add(finalBox1, ++columns[0], row[0]);

                        });
                    }

                    return services; // Pagination control doesn't need a Node for the page
                });
                //serviceGrid.getChildren().add(pagination);
                idScrollPane.setContent(pagination);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }



    private void searchFilter() throws SQLException {
        List<Service> list = gs.Afficher();

        Pagination pagination = new Pagination();

        ObservableList<Service> observableList = FXCollections.observableList(list);
        FilteredList<Service> filteredList= new FilteredList<>(observableList, e->true);
        idSearchField.setOnKeyReleased(e->{

            pagination.setPageCount((int) Math.ceil((double) list.size() /3));
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


            System.out.println(filteredList);
            List<Service> filtered= new ArrayList<>();
            for (Service service : filteredList) {
                filtered.add(service);
            }
            System.out.println(filtered);
            serviceGrid.getChildren().clear();

           /* for (int i=0; i<filtered.size(); i++){
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



            }*/
            List<Service> finalList = filtered;
            pagination.setPageFactory(pageIndex -> {
                final int[] columns = {3};
                final int[] row = { 0 };
                int startIndex = pageIndex * 3;
                int endIndex = Math.min(startIndex + 3, finalList.size());

                GridPane services = new GridPane();
                services.setHgap(30);
                services.setVgap(10);


                for (int i = startIndex; i < endIndex; i++) {
                    FXMLLoader fxmlLoader = new FXMLLoader();
                    fxmlLoader.setLocation(getClass().getResource("/SingleService.fxml"));
                    VBox box = null;
                    try {
                        box = fxmlLoader.load();
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }

                    SingleService singleService = fxmlLoader.getController();
                    try {
                        singleService.setData(finalList.get(i));
                    } catch (SQLException exx) {
                        throw new RuntimeException(exx);
                    }

                    VBox finalBox1 = box;
                    Platform.runLater(() -> { // Run UI updates on the JavaFX thread
                        if(columns[0] == 3)
                        {
                            columns[0] = 0;
                            row[0]++;
                        }
                        services.add(finalBox1, ++columns[0], row[0]);

                    });
                }

                return services; // Pagination control doesn't need a Node for the page
            });
            //serviceGrid.getChildren().add(pagination);
            idScrollPane.setContent(pagination);

            //ok let's check it
        });

    }
}
