/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sample.controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import java.util.Arrays;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.Event;
import javafx.geometry.Pos;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import sample.Main;

import sample.model.ChangeMakingCode;
/**
 * FXML Controller class
 *
 * @author liam
 */
public class ChangeMakingController implements Initializable {

    /**
     * Initializes the controller class.
     */
    
    @FXML
    private Button nextStep;
    
    @FXML
    private Button startToFinish;
    
    @FXML
    private GridPane coinsArray;
    
    @FXML
    private GridPane solutionArray;
    
    @FXML
    private VBox vbox;
    
    private Label[] labelsCoins = new Label[10];
    private Label[] labelsSolution = new Label[10];
    private Integer currentCoin = 0;
    private Integer currentSol = 0;
        
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ChangeMakingCode cmc = new ChangeMakingCode();
        cmc.changeMaking(90);
        System.out.println(java.util.Arrays.toString(cmc.getCoins()));
        System.out.println(java.util.Arrays.toString(cmc.getSolution()));
        //Integer[] sol = java.util.Arrays.copyOf(cmc.getSolution(), cmc.getSolution().length);
        for(int i = 0; i < cmc.getCoins().length; i++) {
            Label label = new Label();
            label.setText(cmc.getCoin(i).toString());
            label.prefWidthProperty().bind(coinsArray.widthProperty());
            label.setMinHeight(70.0);
            label.setAlignment(Pos.CENTER); 
            labelsCoins[i] = label;
            labelsCoins[i].setStyle("-fx-background-color: black, white ;" +
                          "-fx-background-insets: 0, 1 1 1 1 ;" + 
                          "-fx-font-weight: bold ;");
            coinsArray.add(label, i+2, 0);
        }
        for(int i = 0; i < cmc.getSolution().length; i++) {
            Label label = new Label();
            label.setText(cmc.getSolution(i).toString());
            labelsSolution[i] = label;
            labelsSolution[i].setStyle("-fx-background-color: black, white ;" +
                          "-fx-background-insets: 0, 1 1 1 1 ;" + 
                          "-fx-font-weight: bold ;");
            //solutionArray.add(label, i + 1, 0);
        }
        AnchorPane.setLeftAnchor(vbox, 10.0);
        //System.out.println(java.util.Arrays.toString(labelsCoins));
        //System.out.println(java.util.Arrays.toString(labelsSolution));
    }
    
    @FXML   
    private void handleNextStepAction(){
        if(currentCoin < labelsCoins.length) {
            labelsCoins[currentCoin].setStyle("-fx-background-color: grey;");
            Integer currentCoinElem = Integer.parseInt(labelsCoins[currentCoin].getText());
            Integer currentSolElem = Integer.parseInt(labelsSolution[currentSol].getText());
            if(Objects.equals(currentCoinElem, currentSolElem)) {
                labelsCoins[currentCoin].setStyle("-fx-background-color: green;");
                solutionArray.add(labelsSolution[currentSol], currentSol, 0);
                currentSol++;
            }
            else {
                labelsCoins[currentCoin].setStyle("-fx-background-color: red;");
            }
            currentCoin++;
        }
    }
    
    @FXML
    private void handleStartToFinishClick() {
        while(currentCoin < labelsCoins.length) {
            nextStep.fire();
        }
    }
    
}
