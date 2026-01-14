/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author johan
 */
public class Amount {
    
    private double value;
    private String currencty = "euro";

    public Amount(double value) {
        this.value = value;
    }


    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public String getCurrencty() {
        return currencty;
    }

    public void setCurrencty(String currencty) {
        this.currencty = currencty;
    }  
}
