package at.kaindorf.Theorie_Thread.ÜBUNGEN.Consumer_Producer_Cart.bl;

import at.kaindorf.Theorie_Thread.ÜBUNGEN.Consumer_Producer_Cart.beans.Product;

import java.util.ArrayList;
import java.util.List;

public class ShoppingCart {
    private List<Product> cart = new ArrayList<>();

    public void addProduct(Product product){
        if(cart.contains(product)){
            cart.get(cart.indexOf(product)).increaseQuantity();
        }else{
            cart.add(product);
        }
    }

    public Boolean removeProduct(Product product) {
        if(cart.contains(product)){
            if(cart.get(cart.indexOf(product)).getQuantity() > 1) {
                cart.get(cart.indexOf(product)).decreaseQuantity();
            } else {
                cart.remove(product);
            }
            return true;
        }else{
            return false;
        }
    }
}
