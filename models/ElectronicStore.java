package models;

import java.util.Arrays;
import java.util.HashMap;

public class ElectronicStore {
    public final int MAX_PRODUCTS = 10; //Maximum number of products the store can have
    private int curProducts;
    private String name;
    private Product[] stock; //Array to hold all products
    private double revenue;
    private HashMap<Product, Integer> currentCart; //Hashmap to hold the products in the cart
    private int numOfSales;
    private double sale;
    private double currentCartRevenue;

    public ElectronicStore(String initName) {
        curProducts = 0;
        name = initName;
        stock = new Product[MAX_PRODUCTS];
        revenue = 0.0;
        currentCart = new HashMap<>();
        numOfSales = 0;
        sale = 0.0;
        currentCartRevenue = 0.0;
    }

    public String getName() {
        return name;
    }
    public double getRevenue() { return revenue; }
    public HashMap<Product, Integer> getCurrentCart() { return currentCart; }
    public int getNumOfSales() { return numOfSales; }
    public double getSale() { return sale; }
    public double getCurrentCartRevenue() {
        return currentCartRevenue;
    }

    //This method will return the available stock, first loop to determine the size of the availableStock Array,
    //then loop again to add available products.
    public Product[] getAvailableStock() {
        int count = 0;
        for (Product p: stock) {
            if (p != null && p.getStockQuantity() > 0)
                count++;
        }
        Product[] availableStock = new Product[count];
        int index = 0;
        for (Product p: stock) {
            if (p != null && p.getStockQuantity() > 0) {
                availableStock[index] = p;
                index++;
            }
        }
        return availableStock;
    }

    //Adds a product and returns true if there is space in the array
    //Returns false otherwise
    public boolean addProduct(Product newProduct) {
        if (curProducts < MAX_PRODUCTS) {
            stock[curProducts] = newProduct;
            curProducts++;
            return true;
        }
        return false;
    }

    // This method will make a copy of stock, then sort it based on its soldQuantity,
    // and returns first 3 products in the sorted array.
    public Product[] popularProduct() {
        Product[] curItems = new Product[curProducts];
        for (int i = 0; i < curProducts; i++) {
            curItems[i] = stock[i];
        }
        Arrays.sort(curItems);
        return new Product[]{curItems[0], curItems[1], curItems[2]};
    }

    //This method will add the product to the Hashmap cart if its stockQuantity > 0,
    //and update the product quantity in the cart,
    //update the current cart revenue,
    //decrease the product stock quantity.
    public boolean addToCart(Product p) {
        if (p.getStockQuantity() <= 0) {
            return false;
        }
        else {
            if (currentCart.containsKey(p)) {
                currentCart.put(p, currentCart.get(p)+1);
            } else {
                currentCart.put(p, 1);
            }
            currentCartRevenue += p.getPrice();
            p.decreaseStockQuantity();
            return true;
        }
    }

    //This method will remove the product that the index selected in the cart,
    //first get the product based on its index, then update its quantity in the cart,
    //if quantity = 0, remove it from the cart,
    //update the current cart revenue,
    //increase the product stock quantity.
    public void removeFromCart(int index) {
        Product p = (Product) currentCart.keySet().toArray()[index];
        if (currentCart.get(p) > 1) {
            currentCart.put(p, currentCart.get(p)-1);
        } else {
            currentCart.remove(p);
        }
        currentCartRevenue -= p.getPrice();
        p.increaseStockQuantity();
    }

    //This method will sell all products current in the cart,
    //loop the current cart to compute the revenue,
    //then update the number of sales, sale, currentCartRevenue and clear the current cart.
    public void sellCart() {
        for (Product p: currentCart.keySet()) {
            revenue += p.sellUnits(currentCart.get(p));
        }
        numOfSales += 1;
        sale = revenue / numOfSales;
        currentCartRevenue = 0;
        currentCart.clear();
    }

    public static ElectronicStore createStore() {
        ElectronicStore store1 = new ElectronicStore("Watts Up Electronics");
        Desktop d1 = new Desktop(100, 10, 3.0, 16, false, 250, "Compact");
        Desktop d2 = new Desktop(200, 10, 4.0, 32, true, 500, "Server");
        Laptop l1 = new Laptop(150, 10, 2.5, 16, true, 250, 15);
        Laptop l2 = new Laptop(250, 10, 3.5, 24, true, 500, 16);
        Fridge f1 = new Fridge(500, 10, 250, "White", "Sub Zero", false);
        Fridge f2 = new Fridge(750, 10, 125, "Stainless Steel", "Sub Zero", true);
        ToasterOven t1 = new ToasterOven(25, 10, 50, "Black", "Danby", false);
        ToasterOven t2 = new ToasterOven(75, 10, 50, "Silver", "Toasty", true);
        store1.addProduct(d1);
        store1.addProduct(d2);
        store1.addProduct(l1);
        store1.addProduct(l2);
        store1.addProduct(f1);
        store1.addProduct(f2);
        store1.addProduct(t1);
        store1.addProduct(t2);
        return store1;
    }
} 