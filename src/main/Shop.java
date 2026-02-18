package main;

import java.util.ArrayList;
import java.util.Scanner;
import model.Product;
import model.Sale;
import model.Amount;
import model.Employee;
import model.Client;

public class Shop {

    private Amount cash = new Amount(100.00);
    private ArrayList<Product> inventory;
    private ArrayList<Sale> sales;
    private Scanner scanner;

    final static double TAX_RATE = 1.04;

    public Shop() {
        inventory = new ArrayList<>();
        sales = new ArrayList<>();
        scanner = new Scanner(System.in); 
    }
    
    public boolean initSession() {
        Employee employee = new Employee("test", Employee.EMPLOYEE_ID, Employee.PASSWORD);
        boolean loggedIn = false;

        while (!loggedIn) {
            System.out.print("Introduzca numero de empleado: ");
            int userId = scanner.nextInt();
            scanner.nextLine(); 

            System.out.print("Introduzca Contraseña: ");
            String password = scanner.nextLine();

        if (employee.login(userId, password)) {
            System.out.println("Bienvenido, " + employee.getName() + "!");
            loggedIn = true;
        } else {
            System.out.println("El número de empleado o contraseña son incorrectos");
            }
        }
        return false; 
    }

    public static void main(String[] args) {
        Shop shop = new Shop();

        shop.loadInventory();
        shop.initSession();
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

    public void loadInventory() {
        addProductToInventory(new Product("Manzana", 10.00, true, 10));
        addProductToInventory(new Product("Pera", 20.00, true, 20));
        addProductToInventory(new Product("Hamburguesa", 30.00, true, 30));
        addProductToInventory(new Product("Fresa", 5.00, true, 20));
    }

    private void showCash() {
        System.out.println("Dinero actual: " + cash);
    }

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

    public void addStock() {
        System.out.print("Seleccione un nombre de producto: ");
        String name = scanner.nextLine();
        Product product = findProduct(name);

        if (product != null) {
            // ask for stock
            System.out.print("Seleccione la cantidad a añadir: ");
            int stock = scanner.nextInt();
            scanner.nextLine();
            // update stock product
            product.setStock(product.getStock() + stock);
            System.out.println("El stock del producto " + name + " ha sido actualizado a " + product.getStock());

        } else {
            System.out.println("No se ha encontrado el producto con nombre " + name);
        }
    }
    
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
    
    public void removeProduct() {
        System.out.print("Seleccione un nombre de producto a eliminar: ");
        String name = scanner.nextLine();
        
        Product product = findProduct(name);
        
        if (product != null) {
            inventory.remove(product);
            System.out.println("Producto " + name + " eliminado del inventario con éxito");
        } else {
            System.out.println("El producto no se encuentra en el inventario");
        }
    }

    public void showInventory() {
        System.out.println("Contenido actual de la tienda:");
        for (Product product : inventory) {
            if (product != null) {
                System.out.println(product);
            }
        }
    }

    public void sale() {
        System.out.println("Realizar venta, escribir nombre cliente");
        String clientn = scanner.nextLine();

        Client client = new Client(clientn, Client.MEMBER_ID, Client.BALANCE);

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

        totalValue *= TAX_RATE;
        Amount totalAmount = new Amount(totalValue);
        cash.setValue(cash.getValue() + totalValue);    
        System.out.println("Venta realizada con éxito, total: " + totalAmount);
        
        if (client.pay(totalAmount)) {
            System.out.println("Pago realizado con éxito. Saldo restante: " + client.getBalance());
        } else {
            double deuda = Math.abs(client.getBalance().getValue());
            System.out.println("Saldo insuficiente. El cliente debe: " + new Amount(deuda));
        }
        
        Sale newSale = new Sale(client, shoppingcart, totalAmount);
        sales.add(newSale);
    }

    private void showSales() {
        System.out.println("Lista de ventas:");
        for (Sale sale : sales) {
            if (sale != null) {
                System.out.println(sale);
            }
        }
    }
    
    public void showTotalSalesAmount() {
        double total = 0.0;
        for (Sale sale : sales) {
            if (sale != null) {
                total += sale.getAmount().getValue();
            }
        }
        System.out.println("Monto total de todas las ventas: " + new Amount(total));
    }

    public void addProductToInventory(Product product) {
        inventory.add(product);
    }

    public Product findProduct(String name) {
        for (Product product : inventory) {
            if (product != null && product.getName().equalsIgnoreCase(name)) {
                return product;
            }
        }
        return null;
    }
}
