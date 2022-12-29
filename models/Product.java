package models;

//Base class for all products the store will sell
public abstract class Product implements Comparable<Product>{
    private double price;
    private int stockQuantity;
    private int soldQuantity;


    public Product(double initPrice, int initQuantity) {
        price = initPrice;
        stockQuantity = initQuantity;
    }

    public int getStockQuantity() {
        return stockQuantity;
    }
    public double getPrice() {
        return price;
    }

    //Returns the total revenue (price * amount)
    public double sellUnits(int amount) {
        soldQuantity += amount;
        return price * amount;
    }

    public void increaseStockQuantity() {
        stockQuantity += 1;
    }

    public void decreaseStockQuantity() {
        stockQuantity -= 1;
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof Product)) {
            return false;
        }
        return toString().equals(((Product)obj).toString());
    }

    public int hashCode() {
        return toString().hashCode();
    }

    public int compareTo(Product p) {
        return p.soldQuantity - this.soldQuantity;
    }
}