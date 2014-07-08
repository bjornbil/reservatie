/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package be.khleuven.bjornbillen.controller;

import be.khleuven.bjornbillen.db.Database;
import be.khleuven.bjornbillen.entity.Reservation;
import be.khleuven.bjornbillen.entity.Tafel;
import java.net.URL;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.layout.VBoxBuilder;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Callback;
import javafx.util.Duration;
import org.thehecklers.dialogfx.DialogFX;

/**
 *
 * @author Bejarn
 */
public class MainController implements Initializable {

    @FXML
    Parent root;
    @FXML
    MenuBar menubar;
    @FXML
    Menu file;
    @FXML
    Menu view;
    @FXML
    MenuItem close;
    @FXML
    MenuItem onlytoday;
    @FXML
    MenuItem showall;
    @FXML
    MenuItem searchname;
    @FXML
    MenuItem searchtable;
    @FXML
    Button delete;
    @FXML
    TextField nameInput;
    @FXML
    ComboBox dayInput;
    @FXML
    ComboBox monthInput;
    @FXML
    ComboBox yearInput;
    @FXML
    ComboBox startHoursInput;
    @FXML
    ComboBox startMinsInput;
    @FXML
    ComboBox stopHoursInput;
    @FXML
    ComboBox stopMinsInput;
    @FXML
    ComboBox tableInput;
    @FXML
    Button submit;
    @FXML
    Button submit2;
    @FXML
    ComboBox placesInject;
    @FXML
    TextField tableInject;
    @FXML
    TableView<Reservation> reservationView;
    @FXML
    TableColumn<Reservation, Integer> rID;
    @FXML
    TableColumn<Reservation, String> rName;
    @FXML
    TableColumn<Reservation, String> rDate;
    @FXML
    TableColumn<Reservation, String> rStart;
    @FXML
    TableColumn<Reservation, String> rStop;
    @FXML
    TableColumn<Reservation, String> rTable;
    @FXML
    TableColumn<Reservation, Boolean> rDelete;
    @FXML
    TableColumn<Reservation, Boolean> rEdit;
    @FXML
    TableView<Tafel> tableView;
    @FXML
    TableColumn<Tafel, Integer> tID;
    @FXML
    TableColumn<Tafel, String> tDescription;
    @FXML
    TableColumn<Tafel, Integer> tPlaatsen;
    @FXML
    TableColumn<Tafel, Boolean> tDelete;
    @FXML
    TableColumn<Tafel, Boolean> tEdit;
    @FXML
    Label clock;
    // VARIABELEN
    private List<Integer> hours;
    private List<String> mins;
    private List<String> tafels;
    private List<Integer> places;
    private List<Integer> days;
    private List<Integer> months;
    private List<Integer> years;
    private List<String> descr;
    private boolean lastDeleted = false;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Database.init();
        final Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(1), new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                final Calendar cal = Calendar.getInstance();
                clock.setText(cal.getTime().toLocaleString());
            }
        }));
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
        descr = new ArrayList<String>();
        hours = new ArrayList<Integer>();
        mins = new ArrayList<String>();
        tafels = new ArrayList<String>();
        places = new ArrayList<Integer>();
        days = new ArrayList<Integer>();
        months = new ArrayList<Integer>();
        years = new ArrayList<Integer>();
        tID.setCellValueFactory(new PropertyValueFactory<Tafel, Integer>("tID"));
        tDescription.setCellValueFactory(new PropertyValueFactory<Tafel, String>("tDescription"));
        tPlaatsen.setCellValueFactory(new PropertyValueFactory<Tafel, Integer>("tPlaces"));
        rID.setCellValueFactory(new PropertyValueFactory<Reservation, Integer>("rID"));
        rName.setCellValueFactory(new PropertyValueFactory<Reservation, String>("rName"));
        rDate.setCellValueFactory(new PropertyValueFactory<Reservation, String>("rDate"));

        rDate.setSortType(TableColumn.SortType.ASCENDING);
        rStart.setCellValueFactory(new PropertyValueFactory<Reservation, String>("rStartHour"));
        rStart.setSortType(TableColumn.SortType.ASCENDING);
        rStop.setCellValueFactory(new PropertyValueFactory<Reservation, String>("rStopHour"));
        rStop.setSortType(TableColumn.SortType.ASCENDING);
        rTable.setCellValueFactory(new PropertyValueFactory<Reservation, String>("rTable"));
        rEdit.setCellValueFactory(
                new Callback<TableColumn.CellDataFeatures<Reservation, Boolean>, ObservableValue<Boolean>>() {
            @Override
            public ObservableValue<Boolean> call(TableColumn.CellDataFeatures<Reservation, Boolean> p) {
                return new SimpleBooleanProperty(p.getValue() != null);
            }
        });
        rEdit.setCellFactory(
                new Callback<TableColumn<Reservation, Boolean>, TableCell<Reservation, Boolean>>() {
            @Override
            public TableCell<Reservation, Boolean> call(TableColumn<Reservation, Boolean> p) {
                return new UpdateReservationCell();
            }
        });
        rDelete.setCellValueFactory(
                new Callback<TableColumn.CellDataFeatures<Reservation, Boolean>, ObservableValue<Boolean>>() {
            @Override
            public ObservableValue<Boolean> call(TableColumn.CellDataFeatures<Reservation, Boolean> p) {
                return new SimpleBooleanProperty(p.getValue() != null);
            }
        });
        rDelete.setCellFactory(
                new Callback<TableColumn<Reservation, Boolean>, TableCell<Reservation, Boolean>>() {
            @Override
            public TableCell<Reservation, Boolean> call(TableColumn<Reservation, Boolean> p) {
                return new DeleteReservationCell();
            }
        });
        tEdit.setCellValueFactory(
                new Callback<TableColumn.CellDataFeatures<Tafel, Boolean>, ObservableValue<Boolean>>() {
            @Override
            public ObservableValue<Boolean> call(TableColumn.CellDataFeatures<Tafel, Boolean> p) {
                return new SimpleBooleanProperty(p.getValue() != null);
            }
        });
        tEdit.setCellFactory(
                new Callback<TableColumn<Tafel, Boolean>, TableCell<Tafel, Boolean>>() {
            @Override
            public TableCell<Tafel, Boolean> call(TableColumn<Tafel, Boolean> p) {
                return new UpdateTableCell();
            }
        });
        tDelete.setCellValueFactory(
                new Callback<TableColumn.CellDataFeatures<Tafel, Boolean>, ObservableValue<Boolean>>() {
            @Override
            public ObservableValue<Boolean> call(TableColumn.CellDataFeatures<Tafel, Boolean> p) {
                return new SimpleBooleanProperty(p.getValue() != null);
            }
        });
        tDelete.setCellFactory(
                new Callback<TableColumn<Tafel, Boolean>, TableCell<Tafel, Boolean>>() {
            @Override
            public TableCell<Tafel, Boolean> call(TableColumn<Tafel, Boolean> p) {
                return new DeleteTableCell();
            }
        });
        refresh();
        Collections.addAll(days, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31);
        Collections.addAll(months, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12);
        Collections.addAll(years, 2014, 2015, 2016, 2017, 2018, 2019, 2020);
        Collections.addAll(hours, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23);
        Collections.addAll(mins, "00", "15", "30", "45");
        Collections.addAll(places, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20);
        placesInject.getItems().clear();
        placesInject.getItems().addAll(places);
        startHoursInput.getItems().addAll(hours);
        startHoursInput.getSelectionModel().select(0);
        startMinsInput.getItems().addAll(mins);
        startMinsInput.getSelectionModel().select(0);
        stopHoursInput.getItems().addAll(hours);
        stopHoursInput.getSelectionModel().select(0);
        stopMinsInput.getItems().addAll(mins);
        stopMinsInput.getSelectionModel().select(0);
        int dayid = Calendar.getInstance().get(Calendar.DATE) - 1;
        dayInput.getItems().clear();
        dayInput.getItems().addAll(days);
        dayInput.getSelectionModel().select(dayid);
        yearInput.getItems().clear();
        yearInput.getItems().addAll(years);
        yearInput.getSelectionModel().select(0);
        monthInput.getItems().clear();
        monthInput.getItems().addAll(months);
        int monthid = Calendar.getInstance().get(Calendar.MONTH);
        monthInput.getSelectionModel().select(monthid);
        reservationView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        reservationView.getSortOrder().add(rDate);
        reservationView.getSortOrder().add(rStart);
        reservationView.getSortOrder().add(rStop);

    }

    @FXML
    public void close() {
        Platform.exit();
    }

    @FXML
    public void showOnlyToday() {
        if (!Database.REMOTEDB.getReservations().isEmpty() || lastDeleted) {
            reservationView.getItems().clear();
            reservationView.getSortOrder().clear();
            Calendar date = Calendar.getInstance();
            for (Reservation r : Database.REMOTEDB.getReservations()) {
                String currentdate = date.get(Calendar.YEAR) + "/" + (date.get(Calendar.MONTH) + 1) + "/" + date.get(Calendar.DATE);
                if (r.getRDate().equals(currentdate)) {
                    reservationView.getItems().add(r);
                }
            }
            reservationView.getSortOrder().add(rDate);
            reservationView.getSortOrder().add(rStart);
            reservationView.getSortOrder().add(rStop);
        }
    }

    @FXML
    public void showAll() {
        refresh();
    }

    @FXML
    public void showByName() {
        
                    final Stage dialogStage = new Stage();
                    GridPane grd_pan = new GridPane();
                    grd_pan.setStyle("-fx-border-width:5px; -fx-border-color:black; -fx-border-radius:5px;");
                    grd_pan.setAlignment(Pos.CENTER);
                    grd_pan.setHgap(10);
                    grd_pan.setVgap(10);//pading
                    Scene scene = new Scene(grd_pan, 300, 120);
                    dialogStage.setScene(scene);
                    dialogStage.setTitle("FILTER OP NAAM");
                    dialogStage.initStyle(StageStyle.UNDECORATED);

                    Label lab_alert = new Label("Gelieve een naam in te geven");
                    final TextField text = new TextField();
                    grd_pan.add(lab_alert, 0, 0);
                    grd_pan.add(text, 0, 2);
                    Button btn_ok = new Button("ZOEK");
                   
                    btn_ok.setOnAction(new EventHandler<ActionEvent>() {
                        @Override
                        public void handle(ActionEvent arg0) {
                            reservationView.getItems().clear();
                            reservationView.getSortOrder().clear();
                            if (text != null){
                                for (Reservation r : Database.REMOTEDB.getReservations()){
                                    if (r.getRName().equalsIgnoreCase(text.getText())){
                                        reservationView.getItems().add(r);
                                    }
                                }
                            }
                            reservationView.getSortOrder().add(rDate);
                            reservationView.getSortOrder().add(rStart);
                            reservationView.getSortOrder().add(rStop);
                            dialogStage.hide();

                        }
                    });
                    Button btn_ann = new Button("Annuleer");
                    btn_ann.setOnAction(new EventHandler<ActionEvent>() {
                        @Override
                        public void handle(ActionEvent arg0) {
                            dialogStage.hide();
                            refresh();
                            unsetLastDeleted();
                        }
                    });
                    HBox box = new HBox(20);
                    Label label = new Label();
                    label.setPrefSize(110, 20);
                    box.getChildren().addAll(label, btn_ann, btn_ok);
                    grd_pan.add(box, 0, 4);
                    dialogStage.show();
    }

    @FXML
    public void showByTable() {
        final Stage dialogStage = new Stage();
                    GridPane grd_pan = new GridPane();
                    grd_pan.setStyle("-fx-border-width:5px; -fx-border-color:black; -fx-border-radius:5px;");
                    grd_pan.setAlignment(Pos.CENTER);
                    grd_pan.setHgap(10);
                    grd_pan.setVgap(10);//pading
                    Scene scene = new Scene(grd_pan, 300, 120);
                    dialogStage.setScene(scene);
                    dialogStage.setTitle("FILTER OP TAFEL");
                    dialogStage.initStyle(StageStyle.UNDECORATED);
                    Label lab_alert = new Label("Gelieve een tafel te selecteren");
                    final ComboBox combo = new ComboBox();
                    combo.getItems().clear();
                    combo.getItems().addAll(descr);
                    combo.getSelectionModel().select(0);
                    grd_pan.add(lab_alert, 0, 0);
                    grd_pan.add(combo, 0, 2);
                    Button btn_ok = new Button("ZOEK");
                   
                    btn_ok.setOnAction(new EventHandler<ActionEvent>() {
                        @Override
                        public void handle(ActionEvent arg0) {
                            reservationView.getItems().clear();
                            reservationView.getSortOrder().clear();
                            for (Reservation r : Database.REMOTEDB.getReservations()){ 
                                    if (r.getTable().getTID() == getTableIdFromDescription(combo.getSelectionModel().getSelectedItem().toString())){
                                        reservationView.getItems().add(r);
                                    }
                                }
                            reservationView.getSortOrder().add(rDate);
                            reservationView.getSortOrder().add(rStart);
                            reservationView.getSortOrder().add(rStop);
                            dialogStage.hide();

                        }
                    });
                    Button btn_ann = new Button("Annuleer");
                    btn_ann.setOnAction(new EventHandler<ActionEvent>() {
                        @Override
                        public void handle(ActionEvent arg0) {
                            dialogStage.hide();
                            refresh();
                            unsetLastDeleted();
                        }
                    });
                    HBox box = new HBox(20);
                    Label label = new Label();
                    label.setPrefSize(110, 20);
                    box.getChildren().addAll(label, btn_ann, btn_ok);
                    grd_pan.add(box, 0, 4);
                    dialogStage.show();
    }

    public List<String> getDescr() {
        return descr;
    }

    public List<String> getTafels() {
        return tafels;
    }

    public List<String> getMins() {
        return mins;
    }

    public void setLastDeleted() {
        lastDeleted = true;
    }

    public void unsetLastDeleted() {
        lastDeleted = false;
    }

    private Integer getTableIdFromDescription(String descr) {
        int id = 0;
        for (Tafel t : Database.REMOTEDB.getTables()) {
            if (t.getTDescription().equals(descr)) {
                id = t.getTID();
            }
        }
        return id;
    }

    public void refresh() {
        if (!Database.REMOTEDB.getTables().isEmpty() || lastDeleted) {
            tafels.clear();
            descr.clear();
            tableInput.getItems().clear();
            for (Tafel t : Database.REMOTEDB.getTables()) {
                tafels.add(Integer.toString(t.getTID()));
                descr.add(t.getTDescription());
            }
            tableInput.getSelectionModel().select(0);
        }
        tableInput.getItems().addAll(descr);
        if (!Database.REMOTEDB.getReservations().isEmpty() || lastDeleted) {
            reservationView.getItems().clear();
            reservationView.getSortOrder().clear();
            for (Reservation r : Database.REMOTEDB.getReservations()) {
                reservationView.getItems().add(r);
            }
            reservationView.getSortOrder().add(rDate);
            reservationView.getSortOrder().add(rStart);
            reservationView.getSortOrder().add(rStop);
        }
        if (!Database.REMOTEDB.getTables().isEmpty() || lastDeleted) {
            tableView.getItems().clear();

            for (Tafel t : Database.REMOTEDB.getTables()) {
                tableView.getItems().add(t);
            }

        }
    }

    protected void maakFormLeeg() {
        nameInput.clear();
        tableInject.clear();
        int dayid = Calendar.getInstance().get(Calendar.DATE) - 1;
        dayInput.getSelectionModel().select(dayid);
        int monthid = Calendar.getInstance().get(Calendar.MONTH);
        monthInput.getSelectionModel().select(monthid);
        tableInput.getSelectionModel().select(0);
    }

    @FXML
    public void onAddTable() {
        String tafelnaam = tableInject.getText();
        Integer aantalplaatsen = Integer.parseInt(placesInject.getSelectionModel().getSelectedItem().toString());
        final Tafel t = new Tafel(tafelnaam, aantalplaatsen);
        boolean ok = true;
        for (Tafel tafel : Database.REMOTEDB.getTables()) {
            if (tafel.getTDescription().equals(tafelnaam)) {
                ok = false;
            }
        }
        if (ok) {
            Database.REMOTEDB.addTable(t);
        } else {
            DialogFX dialog = new DialogFX(DialogFX.Type.ERROR);
            dialog.setTitleText("ERROR");
            dialog.setMessage("Deze tafelnaam bestaat al");
            dialog.showDialog();
        }
        maakFormLeeg();
        refresh();
    }

    @FXML
    public void onAddReservation() {
        String beginuur = startHoursInput.getSelectionModel().getSelectedItem().toString() + ":" + startMinsInput.getSelectionModel().getSelectedItem().toString();
        String einduur = stopHoursInput.getSelectionModel().getSelectedItem().toString() + ":" + stopMinsInput.getSelectionModel().getSelectedItem().toString();
        Integer tableid = getTableIdFromDescription(tableInput.getSelectionModel().getSelectedItem().toString());
        Tafel tafel = Database.REMOTEDB.getTable(tableid);
        int jaar = Integer.parseInt(yearInput.getSelectionModel().getSelectedItem().toString()) - 1900;
        int maand = Integer.parseInt(monthInput.getSelectionModel().getSelectedItem().toString()) - 1;
        int dag = Integer.parseInt(dayInput.getSelectionModel().getSelectedItem().toString());
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date(jaar, maand, dag));
        String naam = nameInput.getText();
        if (naam.isEmpty() || jaar == 0 || maand == 0 || dag == 0) {
            DialogFX dialog = new DialogFX(DialogFX.Type.ERROR);
            dialog.setTitleText("ERROR");
            dialog.setMessage("Gelieve een geldige naam en datum op te geven");
            dialog.showDialog();
        } else {
            Reservation nieuweres = new Reservation(naam, cal, beginuur, einduur, tafel);
            if (checkReservation(nieuweres)) {
                Database.REMOTEDB.addReservation(nieuweres);
            } else {
                DialogFX dialog = new DialogFX(DialogFX.Type.ERROR);
                dialog.setTitleText("ERROR");
                dialog.setMessage("Dubbele (of ongeldige) reservatie!");
                dialog.showDialog();
            }
            maakFormLeeg();
            refresh();
        }
    }

    private boolean checkReservation(Reservation reservation) {
        boolean result = true;
        Integer startuur = Integer.parseInt(reservation.getRStartHour().split(":")[0]);
        Integer startmin = Integer.parseInt(reservation.getRStartHour().split(":")[1]);
        Integer stopuur = Integer.parseInt(reservation.getRStopHour().split(":")[0]);
        Integer stopmin = Integer.parseInt(reservation.getRStopHour().split(":")[1]);
        for (Reservation r : Database.REMOTEDB.getReservations()) {
            if (r.getRDate().equals(reservation.getRDate())) {
                if (r.getTable().equals(reservation.getTable())) {
                    Integer startuurtemp = Integer.parseInt(r.getRStartHour().split(":")[0]);
                    Integer startmintemp = Integer.parseInt(r.getRStartHour().split(":")[1]);
                    Integer stopuurtemp = Integer.parseInt(r.getRStopHour().split(":")[0]);
                    Integer stopmintemp = Integer.parseInt(r.getRStopHour().split(":")[1]);
                    if (startuur >= startuurtemp && startuur < stopuurtemp) {
                        result = false;
                    }
                    if (startuur <= startuurtemp && (stopuur > startuurtemp || (stopuur == startuurtemp && stopmin > stopmintemp))) {
                        result = false;
                    }
                    if (startuur == stopuurtemp && startmin < stopmintemp) {
                        result = false;
                    }
                }
            }
        }
        return result;
    }

    class DeleteReservationCell extends TableCell<Reservation, Boolean> {

        final Button deleteButton = new Button("");
        final Image image = new Image(getClass().getResourceAsStream("delete.jpg"));

        DeleteReservationCell() {

            //Action when the button is pressed
            deleteButton.setGraphic(new ImageView(image));
            deleteButton.setStyle("-fx-background-color: transparent;");
            deleteButton.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent t) {
                    // get Selected Item
                    final int index = DeleteReservationCell.this.getIndex();

                    //remove selected item from the table list


                    final Stage dialogStage = new Stage();
                    GridPane grd_pan = new GridPane();
                    grd_pan.setAlignment(Pos.CENTER);
                    grd_pan.setHgap(10);
                    grd_pan.setVgap(10);//pading
                    Scene scene = new Scene(grd_pan, 350, 100);
                    dialogStage.setScene(scene);
                    dialogStage.setTitle("BEVESTIGEN");
                    dialogStage.initModality(Modality.WINDOW_MODAL);

                    Label lab_alert = new Label("Ben je zeker dat je deze reservatie wil verwijderen?");
                    grd_pan.add(lab_alert, 0, 0);

                    Button btn_ok = new Button("OK");
                    btn_ok.setOnAction(new EventHandler<ActionEvent>() {
                        @Override
                        public void handle(ActionEvent arg0) {
                            Database.REMOTEDB.deleteReservation(getTableView().getItems().get(index).getRID());
                            if (Database.REMOTEDB.getReservations().isEmpty()) {
                                setLastDeleted();
                            }
                            refresh();
                            unsetLastDeleted();
                            dialogStage.hide();

                        }
                    });
                    Button btn_ann = new Button("Annuleer");
                    btn_ann.setOnAction(new EventHandler<ActionEvent>() {
                        @Override
                        public void handle(ActionEvent arg0) {
                            dialogStage.hide();
                            refresh();
                            unsetLastDeleted();
                        }
                    });
                    HBox box = new HBox(20);
                    Label label = new Label();
                    label.setPrefSize(110, 20);
                    box.getChildren().addAll(label, btn_ann, btn_ok);
                    grd_pan.add(box, 0, 3);
                    dialogStage.show();


                }
            });

        }

        //Display button if the row is not empty
        @Override
        protected void updateItem(Boolean t, boolean empty) {
            super.updateItem(t, empty);
            if (!empty) {
                setGraphic(deleteButton);

            }
        }
    }

    class UpdateReservationCell extends TableCell<Reservation, Boolean> {

        final Button updateButton = new Button("");
        final Image image = new Image(getClass().getResourceAsStream("edit.png"));

        UpdateReservationCell() {

            //Action when the button is pressed
            updateButton.setGraphic(new ImageView(image));
            updateButton.setStyle("-fx-background-color: transparent;");
            updateButton.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent t) {
                    // Oude variabelen ophalen
                    int index = UpdateReservationCell.this.getIndex();

                    String naamold = getTableView().getItems().get(index).getRName();
                    String[] datumold = getTableView().getItems().get(index).getRDate().split("/");
                    Integer dagold = Integer.parseInt(datumold[2]);
                    Integer maandold = Integer.parseInt(datumold[1]);
                    Integer jaarold = Integer.parseInt(datumold[0]);
                    String[] startold = getTableView().getItems().get(index).getRStartHour().split(":");
                    Integer startuurold = Integer.parseInt(startold[0]);
                    Integer startminold = Integer.parseInt(startold[1]);
                    String[] stopold = getTableView().getItems().get(index).getRStopHour().split(":");
                    Integer stopuurold = Integer.parseInt(stopold[0]);
                    Integer stopminold = Integer.parseInt(stopold[1]);
                    Tafel tafel = getTableView().getItems().get(index).getTable();
                    // Nieuwe elementen toevoegen aan updateform
                    submit.setText("Update");
                    submit.setStyle("-fx-background-color: green;");
                    startHoursInput.getSelectionModel().select(startuurold - 12);
                    if (startminold == 0) {
                        startMinsInput.getSelectionModel().select(0);
                    } else if (startminold == 15) {
                        startMinsInput.getSelectionModel().select(1);
                    } else if (startminold == 30) {
                        startMinsInput.getSelectionModel().select(2);
                    } else if (startminold == 45) {
                        startMinsInput.getSelectionModel().select(3);
                    }
                    stopHoursInput.getSelectionModel().select(stopuurold - 12);
                    if (stopminold == 0) {
                        stopMinsInput.getSelectionModel().select(0);
                    } else if (stopminold == 15) {
                        stopMinsInput.getSelectionModel().select(1);
                    } else if (stopminold == 30) {
                        stopMinsInput.getSelectionModel().select(2);
                    } else if (stopminold == 45) {
                        stopMinsInput.getSelectionModel().select(3);
                    }
                    dayInput.getSelectionModel().select(dagold - 1);
                    yearInput.getSelectionModel().select(jaarold - 2014);
                    monthInput.getSelectionModel().select(maandold - 1);
                    int tafelindex = -1;
                    for (int i = 0; i < getDescr().size(); i++) {
                        if (getDescr().get(i).equals(tafel.getTDescription())) {
                            tafelindex = i;
                        }
                    }
                    tableInput.getSelectionModel().select(tafelindex);
                    nameInput.setText(naamold);
                    submit.setOnAction(new EventHandler<ActionEvent>() {
                        @Override
                        public void handle(ActionEvent t) {
                            Integer newdag, newmaand, newjaar;
                            newdag = (Integer) dayInput.getSelectionModel().getSelectedItem();
                            newmaand = (Integer) monthInput.getSelectionModel().getSelectedItem() - 1;
                            newjaar = (Integer) yearInput.getSelectionModel().getSelectedItem() - 1900;
                            Tafel newtafel = Database.REMOTEDB.getTable(getTableIdFromDescription(tableInput.getSelectionModel().getSelectedItem().toString()));
                            Calendar calend = Calendar.getInstance();
                            calend.setTime(new Date(newjaar, newmaand, newdag));
                            int index = UpdateReservationCell.this.getIndex();
                            Integer id = reservationView.getItems().get(index).getRID();
                            String beginuurnew = startHoursInput.getSelectionModel().getSelectedItem().toString() + ":" + startMinsInput.getSelectionModel().getSelectedItem().toString();
                            String einduurnew = stopHoursInput.getSelectionModel().getSelectedItem().toString() + ":" + stopMinsInput.getSelectionModel().getSelectedItem().toString();
                            Reservation newres = Database.REMOTEDB.getReservation(id);
                            newres.setRName(nameInput.getText().toString());
                            newres.setRDate(calend);
                            newres.setStartHour(beginuurnew);
                            newres.setStopHour(einduurnew);
                            newres.setTable(newtafel);
                            Database.REMOTEDB.editReservation(newres);
                            DialogFX dialog = new DialogFX(DialogFX.Type.ACCEPT);
                            dialog.setTitleText("Succes");
                            dialog.setMessage("Reservatie succesvol gewijzigd");
                            dialog.showDialog();
                            submit.setText("Toevoegen");
                            submit.setStyle("");
                            submit.setOnAction(new EventHandler<ActionEvent>() {
                                @Override
                                public void handle(ActionEvent t) {
                                    onAddReservation();
                                }
                            });
                            refresh();
                        }
                    });
                }
            });

        }

        //Display button if the row is not empty
        @Override
        protected void updateItem(Boolean t, boolean empty) {
            super.updateItem(t, empty);
            if (!empty) {
                setGraphic(updateButton);

            }
        }
    }

    class DeleteTableCell extends TableCell<Tafel, Boolean> {

        final Button deleteButton = new Button("");
        final Image image = new Image(getClass().getResourceAsStream("delete.jpg"));

        DeleteTableCell() {

            //Action when the button is pressed
            deleteButton.setGraphic(new ImageView(image));
            deleteButton.setStyle("-fx-background-color: transparent;");
            deleteButton.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent t) {
                    // get Selected Item
                    final int index = DeleteTableCell.this.getIndex();

                    //remove selected item from the table list

                    final Stage dialogStage = new Stage();
                    GridPane grd_pan = new GridPane();
                    grd_pan.setAlignment(Pos.CENTER);
                    grd_pan.setHgap(10);
                    grd_pan.setVgap(10);//pading
                    Scene scene = new Scene(grd_pan, 350, 100);
                    dialogStage.setScene(scene);
                    dialogStage.setTitle("BEVESTIGEN");
                    dialogStage.initModality(Modality.WINDOW_MODAL);

                    Label lab_alert = new Label("Ben je zeker dat je deze tafel wil verwijderen?");
                    Label lab_alert2 = new Label("Alle reservaties van deze tafel zullen worden verwijderd");
                    grd_pan.add(lab_alert, 0, 0);
                    grd_pan.add(lab_alert2, 0, 1);
                    Button btn_ok = new Button("OK");
                    btn_ok.setOnAction(new EventHandler<ActionEvent>() {
                        @Override
                        public void handle(ActionEvent arg0) {
                            for (Reservation r : Database.REMOTEDB.getReservations()) {
                                if (r.getTable().getTID() == getTableView().getItems().get(index).getTID()) {
                                    Database.REMOTEDB.deleteReservation(r.getRID());
                                }
                            }
                            Database.REMOTEDB.deleteTable(getTableView().getItems().get(index).getTID());
                            if (Database.REMOTEDB.getTables().isEmpty() || Database.REMOTEDB.getReservations().isEmpty()) {
                                setLastDeleted();
                            }
                            refresh();
                            unsetLastDeleted();
                            dialogStage.hide();

                        }
                    });
                    Button btn_ann = new Button("Annuleer");
                    btn_ann.setOnAction(new EventHandler<ActionEvent>() {
                        @Override
                        public void handle(ActionEvent arg0) {
                            dialogStage.hide();
                            refresh();
                            unsetLastDeleted();
                        }
                    });
                    HBox box = new HBox(20);
                    Label label = new Label();
                    label.setPrefSize(110, 20);
                    box.getChildren().addAll(label, btn_ann, btn_ok);
                    grd_pan.add(box, 0, 4);
                    dialogStage.show();
                }
            });

        }

        //Display button if the row is not empty
        @Override
        protected void updateItem(Boolean t, boolean empty) {
            super.updateItem(t, empty);
            if (!empty) {
                setGraphic(deleteButton);

            }
        }
    }

    class UpdateTableCell extends TableCell<Tafel, Boolean> {

        final Button updateButton = new Button("");
        final Image image = new Image(getClass().getResourceAsStream("edit.png"));

        UpdateTableCell() {

            //Action when the button is pressed
            updateButton.setGraphic(new ImageView(image));
            updateButton.setStyle("-fx-background-color: transparent;");
            updateButton.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent t) {
                    // Oude variabelen ophalen
                    int index = UpdateTableCell.this.getIndex();

                    String naamold = getTableView().getItems().get(index).getTDescription();
                    Integer plaatsenold = getTableView().getItems().get(index).getTPlaces();
                    placesInject.getSelectionModel().select(plaatsenold - 1);
                    tableInject.setText(naamold);
                    // Nieuwe elementen toevoegen aan updateform
                    submit2.setText("Update");
                    submit2.setStyle("-fx-background-color: green;");


                    submit2.setOnAction(new EventHandler<ActionEvent>() {
                        @Override
                        public void handle(ActionEvent t) {
                            String newdescr = tableInject.getText();
                            Integer newplaces = (Integer) placesInject.getSelectionModel().getSelectedItem();
                            int index = UpdateTableCell.this.getIndex();
                            Integer id = getTableView().getItems().get(index).getTID();
                            Tafel newtafel = Database.REMOTEDB.getTable(id);
                            newtafel.setTDescription(newdescr);
                            newtafel.setTPlaces(newplaces);
                            Database.REMOTEDB.editTable(newtafel);
                            DialogFX dialog = new DialogFX(DialogFX.Type.ACCEPT);
                            dialog.setTitleText("Succes");
                            dialog.setMessage("Reservatie succesvol gewijzigd");
                            dialog.showDialog();
                            submit2.setText("Toevoegen");
                            submit2.setStyle("");
                            submit2.setOnAction(new EventHandler<ActionEvent>() {
                                @Override
                                public void handle(ActionEvent t) {
                                    onAddTable();
                                }
                            });
                            refresh();
                        }
                    });
                }
            });

        }

        //Display button if the row is not empty
        @Override
        protected void updateItem(Boolean t, boolean empty) {
            super.updateItem(t, empty);
            if (!empty) {
                setGraphic(updateButton);

            }
        }
    }
}
