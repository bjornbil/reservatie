/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package be.khleuven.bjornbillen.controller;

//import be.khleuven.bjornbillen.entity.Reservatie;
import be.khleuven.bjornbillen.db.Database;
import be.khleuven.bjornbillen.db.ReservationSystem;
import be.khleuven.bjornbillen.db.ReservationSystemRemote;
import be.khleuven.bjornbillen.entity.Reservation;
import be.khleuven.bjornbillen.entity.Tafel;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
//import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
//import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;



/**
 *
 * @author Bjorn
 */
    

  
public class MainController implements Initializable {
    
    // TABEL DEFINIEREN
    
    @FXML
    TableView<Reservation> tableID;
    @FXML
    TableColumn<Reservation,Integer> iID;
    @FXML
    TableColumn<Reservation,String> iName;
    @FXML
    TableColumn<Reservation,String> iDate;
    @FXML
    TableColumn<Reservation,String> iStart;
    @FXML
    TableColumn<Reservation,String> iStop;
    @FXML
    TableColumn<Reservation,String> iTable;

    // FORM DEFINIEREN
    @FXML
    TextField nameInput;
    @FXML
    TextField dateInput;
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
    // VARIABELEN

    private int number;
    private int number2;
    private List<Integer> hours;
    private List<String> mins;
    private List<String> tafels;
    private List<Integer> places;
    private List<Reservation> reservaties;
    
    // TABEL DATA

    private ReservationSystemRemote remotedb;
   
    @Override
    public void initialize(URL url, ResourceBundle rb) {        
        Database.init();   
        iID.setCellValueFactory(new PropertyValueFactory<Reservation,Integer>("rID"));
        iName.setCellValueFactory(new PropertyValueFactory<Reservation,String>("rName"));
        iDate.setCellValueFactory(new PropertyValueFactory<Reservation,String>("rDate")); 
        iStart.setCellValueFactory(new PropertyValueFactory<Reservation,String>("rStartHour"));
        iStop.setCellValueFactory(new PropertyValueFactory<Reservation,String>("rStopHour")); 
        iTable.setCellValueFactory(new PropertyValueFactory<Reservation,String>("rTable"));
        hours = new ArrayList<Integer>();
        mins = new ArrayList<String>();
        tafels = new ArrayList<String>();
        places = new ArrayList<Integer>();
        tableInput.getItems().addAll(tafels);
        refresh();
        Collections.addAll(hours,12,13,14,15,16,17,18,19,20,21,22,23);
        Collections.addAll(mins,"00","15","30","45"); 
        Collections.addAll(places,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20);
        placesInject.getItems().clear();
        placesInject.getItems().addAll(places);
        startHoursInput.getItems().addAll(hours);
        startMinsInput.getItems().addAll(mins);
        stopHoursInput.getItems().addAll(hours);
        stopMinsInput.getItems().addAll(mins);
       
    }
    
    @FXML
    public void onAddReservation(){
        String beginuur = startHoursInput.getSelectionModel().getSelectedItem().toString() + ":" + startMinsInput.getSelectionModel().getSelectedItem().toString();
        String einduur = stopHoursInput.getSelectionModel().getSelectedItem().toString() + ":" + stopMinsInput.getSelectionModel().getSelectedItem().toString();
        Tafel tafel = Database.REMOTEDB.getTable(Integer.parseInt(tableInput.getSelectionModel().getSelectedItem().toString()));
        System.out.println(tafel.getTID());
        String datum = dateInput.getText();
        String naam = nameInput.getText();
        Reservation nieuweres = new Reservation(naam,datum,beginuur,einduur,tafel);
        Database.REMOTEDB.addReservation(nieuweres);
        maakFormLeeg();
        refresh();
    }
    
    @FXML
    public void onAddTable(){
        String tafelnaam = tableInject.getText();
        Integer aantalplaatsen = Integer.parseInt(placesInject.getSelectionModel().getSelectedItem().toString());
        Tafel t = new Tafel(tafelnaam, aantalplaatsen);
        Database.REMOTEDB.addTable(t);
        refresh();
        maakFormLeeg();
    }
    
    private void refresh(){
        if (!Database.REMOTEDB.getTables().isEmpty()){
            tafels.clear();
            tableInput.getItems().clear();
        for (Tafel t : Database.REMOTEDB.getTables()){
            tafels.add(t.getTID().toString());
        }
        }
        tableInput.getItems().addAll(tafels);
        if (!Database.REMOTEDB.getReservations().isEmpty()){
            tableID.getItems().clear();
            for (Reservation r : Database.REMOTEDB.getReservations()){
            tableID.getItems().add(r);
            }
        }
    }
      
    private void maakFormLeeg(){
        nameInput.clear();
        dateInput.clear();
        tableInject.clear();
    }
    

}
