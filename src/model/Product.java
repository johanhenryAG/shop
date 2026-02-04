package model;

public class Product {
    private int id;
    private String name;
    private Amount publicPrice;
    private Amount wholesalerPrice;
    private boolean available;
    private int stock;
    private static int totalProducts;
    static double EXPIRATION_RATE = 0.60;
    
    public Product(String name, double wholesalerPrice, boolean available, int stock) {
        super();
        this.id = totalProducts + 1;
        this.name = name;
        this.wholesalerPrice = new Amount(wholesalerPrice);
        this.publicPrice = new Amount(wholesalerPrice * 2);
        this.available = available;
        this.stock = stock;
        totalProducts++;
    }
    
    public int getId() {
        return id;
    }
    
    public void setId(int id) {
        this.id = id;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public Amount getPublicPrice() {
        return publicPrice;
    }
    
    public void setPublicPrice(Amount publicPrice) {
        this.publicPrice = publicPrice;
    }
    
    public Amount getWholesalerPrice() {
        return wholesalerPrice;
    }
    
    public void setWholesalerPrice(Amount wholesalerPrice) {
        this.wholesalerPrice = wholesalerPrice;
    }
    
    public boolean isAvailable() {
        return available;
    }
    
    public void setAvailable(boolean available) {
        this.available = available;
    }
    
    public int getStock() {
        return stock;
    }
    
    public void setStock(int stock) {
        this.stock = stock;
    }
    
    public static int getTotalProducts() {
        return totalProducts;
    }
    
    public static void setTotalProducts(int totalProducts) {
        Product.totalProducts = totalProducts;
    }
    
    public void expire() {
        EXPIRATION_RATE = 0.2;
        publicPrice.setValue(publicPrice.getValue() * EXPIRATION_RATE);
    }
    
    @Override 
    public String toString() {
        return String.format(
            "ID: %d | Nombre: %s | Precio Público: %s | Mayorista: %s | Stock: %d | Disponible: %s",
            id, name, publicPrice, wholesalerPrice.getValue(), stock,
            available ? "Sí" : "No"
        );
    }
}