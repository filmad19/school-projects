package at.kaindorf.Theorie_Thread.ÜBUNGEN.Consumer_Producer_Cart.bl;

import at.kaindorf.Theorie_Thread.ÜBUNGEN.Consumer_Producer_Cart.beans.Product;

import java.util.List;
import java.util.Random;
import java.util.concurrent.Callable;

public class CartWorker implements Callable<Double> {
    private ShoppingCart shoppingCart;
    private List<Product> productList;
    private int number;
    private static long startInMillis = System.currentTimeMillis();


    public CartWorker(ShoppingCart shoppingCart, List<Product> productList, int number) {
        this.shoppingCart = shoppingCart;
        this.productList = productList;
        this.number = number;
    }

    @Override
    public Double call() throws Exception {
        Thread.currentThread().setName(String.valueOf(number));
        System.out.println(Thread.currentThread().getName() + " starts");
        double prices = 0;

        for (int i = 0; i < 10; i++) {
            Random random = new Random();

            Product currentProduct = productList.get(random.nextInt(productList.size()));

            if(random.nextInt(10) < 8){
//                HINEINLEGEN
                synchronized (shoppingCart){
                    shoppingCart.addProduct(currentProduct);
                    prices+=currentProduct.getPrice();
                    shoppingCart.notifyAll();
                    System.out.println(currentProduct.getQuantity() + " | " + Thread.currentThread().getName() + " put: " + currentProduct);
                }
            }else{
//                HERAUSNEHMEN
                synchronized (shoppingCart){
                    if(!shoppingCart.removeProduct(currentProduct)){
                        System.err.println(currentProduct.getQuantity() + " | " + Thread.currentThread().getName() + " is waiting for " + currentProduct);
                    }

                    while (!shoppingCart.removeProduct(currentProduct)){
                        if((System.currentTimeMillis()-startInMillis)/1000 > 12){
                            System.err.println(Thread.currentThread().getName() + " DEAD LOCK");
                            return prices;
                        }
                        shoppingCart.wait(1000);
                    }
                    prices-=currentProduct.getPrice();
                    System.out.println(currentProduct.getQuantity() + " | " + Thread.currentThread().getName() + " remove: " + currentProduct);
                }
            }
            
            Thread.sleep(random.nextInt(1000-1)+1);
        }
        return prices;
    }
}
