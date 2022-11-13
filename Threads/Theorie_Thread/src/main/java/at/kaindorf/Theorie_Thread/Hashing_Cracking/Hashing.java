package at.kaindorf.Theorie_Thread.Hashing_Cracking;

import com.google.common.io.BaseEncoding;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Hashing {
    public static void main(String[] args) {
        String password = "MySecret123!!!";
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("MD5"); //wähle Algorithmus aus
            byte[] passwordHash = messageDigest.digest(password.getBytes()); //wandelt in Hash byte[] um

            String passwordHexString = BaseEncoding.base16().encode(passwordHash).toLowerCase(); //wandelt Hash byte[] in Hex um
            System.out.println(passwordHexString);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

}
