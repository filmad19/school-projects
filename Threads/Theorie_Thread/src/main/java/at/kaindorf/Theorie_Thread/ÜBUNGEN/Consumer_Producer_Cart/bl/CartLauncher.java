package at.kaindorf.Theorie_Thread.ÜBUNGEN.Consumer_Producer_Cart.bl;

import at.kaindorf.Theorie_Thread.ÜBUNGEN.Consumer_Producer_Cart.beans.Product;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.concurrent.*;

public class CartLauncher {
    private List<Product> productList;
    private ShoppingCart shoppingCart = new ShoppingCart();

    private static List<Product> readProducts() throws IOException {
        Path path = Paths.get("src/main/java/at/kaindorf/Theorie_Thread/ÜBUNGEN/Consumer_Producer_Cart/products_shoppingCard.csv");
        return Files.readAllLines(path)
                .stream()
                .skip(1)
                .map(Product::new)
                .toList();
    }

    private void runWorkers(){
        try {
            productList = readProducts();
        } catch (IOException e) {
            e.printStackTrace();
        }

        ExecutorService pool = Executors.newFixedThreadPool(5);
        CompletionService<Double> service = new ExecutorCompletionService<>(pool);

        for (int i = 0; i < 5; i++) {
            service.submit(new CartWorker(shoppingCart, productList, i));
        }

        pool.shutdown();

        double sumOfCart = 0;

        for (int i = 0; i < 5; i++) {
            try {
                sumOfCart+=service.take().get();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }
        System.out.println("Total sum of shopping cart: " + String.format("%.2f", sumOfCart).replace(".", ",") + "€");

    }

    public static void main(String[] args) {
        new CartLauncher().runWorkers();
    }
}
