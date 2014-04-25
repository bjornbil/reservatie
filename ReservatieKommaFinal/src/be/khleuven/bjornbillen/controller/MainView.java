/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package be.khleuven.bjornbillen.controller;

import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import static javafx.fxml.FXMLLoader.load;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 *
 * @author Bjorn
 */
public class MainView extends Application {
    
    private MainController myController;
    public static void main(String[] args){
        Application.launch(MainView.class, (java.lang.String[]) null);
    }
    
    @Override 
    public void start(Stage primaryStage) throws Exception {
        try {
    Stage dialogue = new Stage();
    Parent root = null;
    FXMLLoader loader = new FXMLLoader();
    root = (Parent) loader.load(getClass().getResource("MainFXML.fxml").openStream());
    myController = loader.getController();
     primaryStage.setTitle ("Reservatiesysteem");
     primaryStage.setScene (new Scene (root));
     primaryStage.show ();
        } catch (Exception e){
            Logger.getLogger(MainView.class.getName()).log(Level.SEVERE, null, e);
        }
    }
    
    @Override
    public void stop() throws Exception {
        super.stop();
     //   myController.destroy();
    }

}
