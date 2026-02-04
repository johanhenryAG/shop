/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main;
import model.Amount;

/**
 *
 * @author johan
 */
public interface Payable {
    /**
     * Realiza un pago restando la cantidad del saldo
     * @param amount cantidad a pagar
     * @return true si el pago es exitoso (saldo suficiente), false en caso contrario
     */
    boolean pay(Amount amount);
}