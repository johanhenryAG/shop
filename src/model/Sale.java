package model;

import java.util.ArrayList;

public class Sale {
    private String client;
    private ArrayList<Product> products;
    private Amount amount;
    
    public Sale(String client, ArrayList<Product> products, Amount amount) {
        super();
        this.client = client;
        this.products = products;
        this.amount = amount;
    }
    
    public Sale(String client, Amount amount) {
        super();
        this.client = client;
        this.products = new ArrayList<>();
        this.amount = amount;
    }
    
    public String getClient() {
        return client;
    }
    
    public void setClient(String client) {
        this.client = client;
    }
    
    public ArrayList<Product> getProducts() {
        return products;
    }
    
    public void setProducts(ArrayList<Product> products) {
        this.products = products;
    }
    
    public Amount getAmount() {
        return amount;
    }
    
    public void setAmount(Amount amount) {
        this.amount = amount;
    }
    
    @Override
    public String toString() {
        String productosVendidos = "";
        for (Product p : products) {
            if (p != null) { 
                productosVendidos += p.getName() + " ";
            }
        }
        return "Sale{" + "client=" + client + ", products=" + productosVendidos.trim() + ", amount=" + amount + '}';
    }
}