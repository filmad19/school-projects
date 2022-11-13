package at.kaindorf.Theorie_Thread.ÜBUNGEN.Consumer_Producer_Cart.beans;

public class Product {
    private String name;
    private double price;
    private int quantity = 0;

    public Product(String line) {
        String[] tokens = line.split(";");
        this.name = tokens[0];
        this.price = Double.parseDouble(tokens[1].replace("€", "").replace(",", "."));
    }



    @Override
    public String toString() {
        return name + " - " + String.valueOf(price).replace(".", ",") + " €";
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void increaseQuantity() {
        this.quantity++;
    }

    public void decreaseQuantity() {
        this.quantity--;
    }
}
