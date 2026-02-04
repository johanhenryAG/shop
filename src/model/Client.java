/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;
import main.Payable;

/**
 *
 * @author johan
 */
public class Client extends Person implements Payable {
    private int memberId;
    private Amount balance;
    
    public static final int MEMBER_ID = 456;
    public static final double BALANCE = 50.00;
    
    public Client(String name, int memberId, double initialBalance) {
        super(name);
        this.memberId = memberId;
        this.balance = new Amount(initialBalance);
    }
    
    @Override
    public boolean pay(Amount amount) {
        double newBalance = balance.getValue() - amount.getValue();
        balance.setValue(newBalance);
        
        return newBalance >= 0;
    }
    
    public int getMemberId() {
        return memberId;
    }
    
    public void setMemberId(int memberId) {
        this.memberId = memberId;
    }
    
    public Amount getBalance() {
        return balance;
    }
    
    public void setBalance(Amount balance) {
        this.balance = balance;
    }
    
    @Override
    public String toString() {
        return "Client{" +
               "name='" + name + '\'' +
               ", memberId=" + memberId +
               ", balance=" + balance +
               '}';
    }
}
