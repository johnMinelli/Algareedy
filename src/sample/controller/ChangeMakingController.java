/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sample.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import sample.model.ChangeMakingCode;

import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

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
    
    @FXML
    private TextField chooseChange;
    
    @FXML 
    private Label labelDescription;
    
    @FXML
    private Button chooseButton;
    
    private Label[] labelsCoins = new Label[10];
    private Label[] labelsSolution = new Label[10];
    private Integer currentCoin = 0;
    private Integer currentSol = 0;
    private Integer change = 90;
        
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        currentCoin = 0;
        currentSol = 0;
        ChangeMakingCode cmc = new ChangeMakingCode();
        cmc.changeMaking(change);
        //System.out.println(java.util.Arrays.toString(cmc.getCoins()));
        //System.out.println(java.util.Arrays.toString(cmc.getSolution()));
        chooseChange.setText("");
        labelDescription.setText("Ad ogni passo, se coins[i] <= resto si "
                    + "riempie la posizione j-esima dell'array contenente la soluzione"
                    + "(solution), cioè il numero minimo di monete da restituire. RESTO = " + 
                    change.toString() + ", i = " + currentCoin.toString() + ", j = " + currentSol.toString());
        //Integer[] sol = java.util.Arrays.copyOf(cmc.getSolution(), cmc.getSolution().length);
        for(int i = 0; i < cmc.getCoins().length; i++) {
            Label label = new Label();
            label.setText(cmc.getCoin(i).toString());
            label.prefWidthProperty().bind(coinsArray.widthProperty());
            label.setMinHeight(50.0);
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
            Integer currentCoinElem = Integer.parseInt(labelsCoins[currentCoin].getText());
            Integer currentSolElem = Integer.parseInt(labelsSolution[currentSol].getText());
            if(Objects.equals(currentCoinElem, currentSolElem)) {
                labelsCoins[currentCoin].setStyle("-fx-background-color: black, green ;" +
                          "-fx-background-insets: 0, 1 1 1 1 ;" + 
                          "-fx-font-weight: bold ;");
                labelsSolution[currentSol].setStyle("-fx-background-color: black, white ;" +
                          "-fx-background-insets: 0, 1 1 1 1 ;" + 
                          "-fx-font-weight: bold ;");
                labelsSolution[currentSol].setMinHeight(50.0);
                labelsSolution[currentSol].prefWidthProperty().bind(solutionArray.widthProperty());
                labelsSolution[currentSol].setAlignment(Pos.CENTER);
                solutionArray.add(labelsSolution[currentSol], currentSol + 2, 0);
                currentSol++;
            }
            else {
                labelsCoins[currentCoin].setStyle("-fx-background-color: black, red ;" +
                          "-fx-background-insets: 0, 1 1 1 1 ;" + 
                          "-fx-font-weight: bold ;");
            }
            currentCoin++;
            labelDescription.setText("Ad ogni passo, se coins[i] <= resto si "
                    + "riempie la posizione j-esima dell'array contenente la soluzione"
                    + "(solution), cioè il numero minimo di monete da restituire. RESTO = "
                    + change.toString() + ", i = " + currentCoin.toString() + ", j = " + 
                    currentSol.toString());
        }
        else {
            labelDescription.setText("Ad ogni passo, se coins[i] <= resto si "
                    + "riempie la posizione j-esima dell'array contenente la soluzione"
                    + "(solution), cioè il numero minimo di monete da restituire. RESTO = "
                    + change.toString() + ", i = " + currentCoin.toString() + ", j = " + currentSol.toString() + 
                    ". L'ALGORITMO HA TERMINATO LA SUA ESECUZIONE, PUOI SCEGLIERE UN NUOVO RESTO,"
                    + " TORNARE ALLA HOME OPPURE PASSARE ALLE DOMANDE DI AUTOAPPRENDIMENTO.");
        }
    }
    
    @FXML
    private void handleStartToFinishClick() {
        while(currentCoin < labelsCoins.length) {
            nextStep.fire();
        }
        labelDescription.setText("Ad ogni passo, se coins[i] <= resto si "
                    + "riempie la posizione j-esima dell'array contenente la soluzione"
                    + "(solution), cioè il numero minimo di monete da restituire. Resto = 90, "
                    + "i = " + currentCoin.toString() + ", j = " + currentSol.toString() + 
                    ". L'ALGORITMO HA TERMINATO LA SUA ESECUZIONE, PUOI SCEGLIERE UN NUOVO RESTO,"
                    + " TORNARE ALLA HOME OPPURE PASSARE ALLE DOMANDE DI AUTOAPPRENDIMENTO.");
    }
    
    private boolean isInt(String s) {
        try {
            int input = Integer.parseInt(s);
            return true;
        } catch (NumberFormatException e){
            return false;
        }
    }
    
    @FXML
    private void handleChangeButtonClicked() {
        String input = chooseChange.getText();
        if((this.isInt(input)) && (Integer.parseInt(input) <= 173)) {
            change = Integer.parseInt(input);
            this.initialize(null, null);
        }
        else {
            chooseChange.setText("INSERISCI UN INTERO <= 173");
        }
    }
}
