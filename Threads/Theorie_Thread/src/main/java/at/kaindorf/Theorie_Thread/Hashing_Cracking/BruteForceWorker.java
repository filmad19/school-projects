package at.kaindorf.Theorie_Thread.Hashing_Cracking;

import com.google.common.io.BaseEncoding;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Callable;

public class BruteForceWorker implements Callable<String> {
    private static String charsUsedInString = "0123456789abcdefghijklmnopqrstuvwxyz";
    private static char[] charsUsed = charsUsedInString.toCharArray();

    private Person person;

    public BruteForceWorker(Person person) {
        this.person = person;
    }

    //Zeit, Person, name von Thread,
    @Override
    public String call() throws Exception {
        long startTime = System.currentTimeMillis();
        for(char a : charsUsed){
            for(char b : charsUsed){
                for(char c : charsUsed){
                    for(char d : charsUsed){
                        for(char e : charsUsed){
                            String password = "" + a + b + c + d + e;
                            String hash = person.getName() + password;
                            try {
                                MessageDigest messageDigest = MessageDigest.getInstance("MD5"); //wähle Algorithmus aus
                                byte[] passwordHash = messageDigest.digest(hash.getBytes()); //wandelt in Hash byte[] um
                                String str = BaseEncoding.base16().encode(passwordHash).toLowerCase();

                                if(str.equals(person.getHash())){
                                    long endTime = System.currentTimeMillis();
                                    System.out.println(person.getName() + ": " + password + "\t | " + (endTime-startTime)/1000 + " seconds");
                                    return password;
                                }
                            } catch (NoSuchAlgorithmException exception) {
                                exception.printStackTrace();
                            }
                            Thread.currentThread().setName("hallo");
                        }
                    }
                }
            }
        }

        throw new Exception("password not found");
    }
}
