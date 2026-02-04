package main;

import java.util.ArrayList;
import model.Product;
import model.Sale;
import java.util.Scanner;
import model.Amount;

public class Shop {

    private Amount cash = new Amount(100.00);
    private ArrayList<Product> inventory;
    private ArrayList<Sale> sales;
    private Scanner scanner; // Scanner global para toda la clase

    final static double TAX_RATE = 1.04;

    public Shop() {
        inventory = new ArrayList<>();
        sales = new ArrayList<>();
        scanner = new Scanner(System.in); // Inicializar scanner una sola vez
    }

    public static void main(String[] args) {
        Shop shop = new Shop();

        shop.loadInventory();

        int opcion = 0;
        boolean exit = false;

        do {
            System.out.println("\n");
            System.out.println("===========================");
            System.out.println("Menu principal miTienda.com");
            System.out.println("===========================");
            System.out.println("1) Contar caja");
            System.out.println("2) Añadir producto");
            System.out.println("3) Añadir stock");
            System.out.println("4) Marcar producto proxima caducidad");
            System.out.println("5) Ver inventario");
            System.out.println("6) Venta");
            System.out.println("7) Ver ventas");
            System.out.println("8) Monto total de todas las ventas");
            System.out.println("9) Eliminar producto");
            System.out.println("10) Salir programa");
            System.out.print("Seleccione una opcion: ");
            
            opcion = shop.scanner.nextInt();
            shop.scanner.nextLine(); // Limpiar buffer

            switch (opcion) {
                case 1:
                    shop.showCash();
                    break;

                case 2:
                    shop.addProduct();
                    break;

                case 3:
                    shop.addStock();
                    break;

                case 4:
                    shop.setExpired();
                    break;

                case 5:
                    shop.showInventory();
                    break;

                case 6:
                    shop.sale();
                    break;

                case 7:
                    shop.showSales();
                    break;
                    
                case 8:
                    shop.showTotalSalesAmount();
                    break;
                    
                case 9:
                    shop.removeProduct();
                    break;

                case 10:
                    exit = true;
                    break;
            }
        } while (!exit);
        
        shop.scanner.close();
    }

    /**
     * load initial inventory to shop
     */
    public void loadInventory() {
        addProductToInventory(new Product("Manzana", 10.00, true, 10));
        addProductToInventory(new Product("Pera", 20.00, true, 20));
        addProductToInventory(new Product("Hamburguesa", 30.00, true, 30));
        addProductToInventory(new Product("Fresa", 5.00, true, 20));
    }

    /**
     * show current total cash
     */
    private void showCash() {
        System.out.println("Dinero actual: " + cash);
    }

    /**
     * add a new product to inventory getting data from console
     */
    public void addProduct() {
        System.out.print("Nombre: ");
        String name = scanner.nextLine(); 
        
        Product product = findProduct(name);
        if (product != null) {
            System.out.println("El producto ya existe en el sistema");
            return;
        } 
        System.out.print("Precio mayorista: ");
        double wholesalerPrice = scanner.nextDouble();
        System.out.print("Stock: ");
        int stock = scanner.nextInt();
        scanner.nextLine(); // Limpiar buffer
        
        addProductToInventory(new Product(name, wholesalerPrice, true, stock));
    }

    /**
     * add stock for a specific product
     */
    public void addStock() {
        System.out.print("Seleccione un nombre de producto: ");
        String name = scanner.nextLine();
        Product product = findProduct(name);

        if (product != null) {
            // ask for stock
            System.out.print("Seleccione la cantidad a añadir: ");
            int stock = scanner.nextInt();
            scanner.nextLine(); // Limpiar buffer
            // update stock product
            product.setStock(product.getStock() + stock);
            System.out.println("El stock del producto " + name + " ha sido actualizado a " + product.getStock());

        } else {
            System.out.println("No se ha encontrado el producto con nombre " + name);
        }
    }

    /**
     * set a product as expired
     */
    private void setExpired() {
        System.out.print("Seleccione un nombre de producto: ");
        String name = scanner.nextLine();

        Product product = findProduct(name);
        
        double porcentaje = 40.0;

        if (product != null) {
            double precioFinal = product.getPublicPrice().getValue() * (porcentaje/100);
            System.out.println("El precio del producto " + name + " ha sido actualizado a " + precioFinal);
            product.getPublicPrice().setValue(precioFinal);
        } else {
            System.out.println("Producto no encontrado");
        }
    }
    
    /**
     * remove a product from inventory
     */
    public void removeProduct() {
        System.out.print("Seleccione un nombre de producto a eliminar: ");
        String name = scanner.nextLine();
        
        Product product = findProduct(name);
        
        if (product != null) {
            inventory.remove(product);
            System.out.println("Producto " + name + " eliminado del inventario con éxito");
        } else {
            System.out.println("Error: Producto no encontrado en el inventario");
        }
    }

    /**
     * show all inventory
     */
    public void showInventory() {
        System.out.println("Contenido actual de la tienda:");
        for (Product product : inventory) {
            if (product != null) {
                System.out.println(product);
            }
        }
    }

    /**
     * make a sale of products to a client
     */
    public void sale() {
        // ask for client name
        System.out.println("Realizar venta, escribir nombre cliente");
        String client = scanner.nextLine();

        // sale product until input name is not 0
        double totalValue = 0.0;
        String name = "";
        
        ArrayList<Product> shoppingcart = new ArrayList<>();
        
        while (!name.equals("0")) {
            System.out.println("Introduce el nombre del producto, escribir 0 para terminar:");
            name = scanner.nextLine();

            if (name.equals("0")) {
                break;
            }
            Product product = findProduct(name);
            boolean productAvailable = false;

            if (product != null && product.isAvailable()) {
                productAvailable = true;
                totalValue += product.getPublicPrice().getValue();
                product.setStock(product.getStock() - 1);
                // if no more stock, set as not available to sale
                if (product.getStock() == 0) {
                    product.setAvailable(false);
                }
                shoppingcart.add(product);
                System.out.println("Producto añadido con éxito");
            }

            if (!productAvailable) {
                System.out.println("Producto no encontrado o sin stock");
            }
        }

        // show cost total
        totalValue *= TAX_RATE;
        Amount totalAmount = new Amount(totalValue);
        cash.setValue(cash.getValue() + totalValue);    
        System.out.println("Venta realizada con éxito, total: " + totalAmount);
        
        Sale newSale = new Sale(client, shoppingcart, totalAmount);
        sales.add(newSale);
    }

    /**
     * show all sales
     */
    private void showSales() {
        System.out.println("Lista de ventas:");
        for (Sale sale : sales) {
            if (sale != null) {
                System.out.println(sale);
            }
        }
    }
    
    /**
     * show total amount of all sales
     */
    public void showTotalSalesAmount() {
        double total = 0.0;
        for (Sale sale : sales) {
            if (sale != null) {
                total += sale.getAmount().getValue();
            }
        }
        System.out.println("Monto total de todas las ventas: " + new Amount(total));
    }
    
    /**
     * add a product to inventory (renamed to avoid confusion)
     *
     * @param product
     */
    public void addProductToInventory(Product product) {
        inventory.add(product);
    }

    /**
     * find product by name
     *
     * @param name
     * @return product found by name
     */
    public Product findProduct(String name) {
        for (Product product : inventory) {
            if (product != null && product.getName().equalsIgnoreCase(name)) {
                return product;
            }
        }
        return null;
    }
}
