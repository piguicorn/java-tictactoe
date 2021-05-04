/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.util.ArrayList;

/**
 *
 * @author piguicorn
 */
public class Player {
    // atributos
    private String name;
    private String symbol;
    private ArrayList<String> marquedSquares = new ArrayList<String>();
    
    // constructor
    public Player(String name, String symbol) {
        this.name = name;
        this.symbol = symbol;
    }
    
    // métodos
    public String getName() {
        return this.name;
    }
    
        
    public String getSymbol() {
        return this.symbol;
    }
    
    public boolean isSquareMarked(String square) {
        return marquedSquares.contains(square);
    }
    
    public void markSquare(String square) {
        marquedSquares.add(square);
    }
}
