/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.ResourceBundle;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import model.Player;


/**
 * FXML Controller class
 *
 * @author piguicorn
 */
public class TicTacToeViewController implements Initializable {
    
    ArrayList<Player> players = new ArrayList<Player>();
    
    // ¿quién está jugando ahora?
    int playing;
            
    // qué turno es
    int turn;
    
    // combinaciones ganadoras
    String[][] winnerCombinations = {
        {"00","10","20"},
        {"01","11","12"},
        {"02","12","22"},
        {"00","01","02"},
        {"10","11","12"},
        {"20","12","22"},
        {"00","11","22"},
        {"02","11","20"}
    };

    @FXML
    private Text square00;
    @FXML
    private Text square10;
    @FXML
    private Text square20;
    @FXML
    private Text square01;
    @FXML
    private Text square11;
    @FXML
    private Text square21;
    @FXML
    private Text square02;
    @FXML
    private Text square12;
    @FXML
    private Text square22;
    @FXML
    private Text playersTurn;
    
    @FXML
    private Button playBtn;
    


    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        newGame();
        
        playBtn.setOnMouseClicked((new EventHandler<MouseEvent>() { 
            public void handle(MouseEvent event) { 
               newGame();
            } 
        }));
    } 
    
    public void newGame() {
        System.out.println("New Game"); 
        
        // (re)inizializanzo variables
        playing = 0;
        turn = 0;
        
        
        // inicializando los jugadores
        players = new ArrayList<Player>();
        players.add(new Player("jugador 1", "o"));
        players.add(new Player("jugador 2", "x"));
        
        playersTurn.setText("Juega: " + players.get(playing).getName());
        
                
        // añadiendo los eventos a las casillas
        ArrayList<Text> squares = new ArrayList();
        squares.add(square00);
        squares.add(square10);
        squares.add(square20);
        squares.add(square01);
        squares.add(square11);
        squares.add(square21);
        squares.add(square02);
        squares.add(square12);
        squares.add(square22);

                
        for (Text square : squares) {
            
            // borra lo que hubiera 
            square.setText(null);
                                
            // y añade un evento de ratón para cuando
            // les jugadores pulsen
            square.setOnMouseClicked((new EventHandler<MouseEvent>() { 
                public void handle(MouseEvent event) {    
                    
                    if (playing == -1) {
                        return ;
                    }
                    
                    // ¿qué casilla es?
                    String id = ((Text) event.getSource()).getId();
                    String coords = id.substring(id.length() - 2);
                                          
                    // ¿está ya marcada?...
                    if (players.get(0).isSquareMarked(coords) || players.get(1).isSquareMarked(coords)) {
                        return ;
                    }
                    
                    // ...si no es así, márcala
                    players.get(playing).markSquare(coords);

                    square.setText(players.get(playing).getSymbol());

                    turn = turn + 1;
                    // comprueba si el jugador ha ganado
                    if (turn > 3 && didPlayerWon()) {
                        System.out.println("ganéee");
                        playersTurn.setText("☆¡ENHORABUENA!☆ Ha ganado el " + players.get(playing).getName());
                        playing = -1;
                        return ;
                    }
                    
                    changePlayer();
                    playersTurn.setText("Juega: " + players.get(playing).getName());

                   
                    System.out.println("marked");     

                } 
            })); 
        }
        
    }
    
    public void changePlayer() {            
        if (playing == 0) {
            playing = 1;
        } else {
            playing = 0;
        }
    }
    
    public boolean didPlayerWon () {
        
        boolean horizontal = false;
        boolean vertical = false;
        boolean diagonal = false;
        
        // un jugador gana cuando 3 de sus casillas
        // en marquedSquares forman parte de una 
        // de las combinaciones ganadoras
        for (int y = 0; y <= winnerCombinations.length; y++) {
            
            horizontal = checkCombination(y,"horizontal");
            vertical = checkCombination(y,"vertical");
            diagonal = checkCombination(y,"diagonal");
            
            if (horizontal || vertical || diagonal) {
                return true;
            }
        }
        
        return false;
    }
    
    public boolean checkCombination (int y, String combination) {
        String square;
        int counter = 0;
        
        // diagonal izda -> dcha ascendente
        if (combination == "diagonal" && 
            players.get(playing).isSquareMarked("02") &&
            players.get(playing).isSquareMarked("11") &&
            players.get(playing).isSquareMarked("20")) {
            
            return true;
        }
        
        for (int x = 0; x <= 3; x++) {
            
            square = x+""+y; // horizontal
            
            if (combination == "vertical") {
                square = y+""+x;
            }
            
            if (combination == "diagonal") {
                square = x+""+x;
            }
            
            if (players.get(playing).isSquareMarked(square)) {
                counter = counter + 1;
            }
        }
        
        return counter == 3;
    }
    
}
