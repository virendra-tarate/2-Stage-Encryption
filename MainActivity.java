import java.util.Scanner;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import java.util.Base64;
import java.util.Date;
import java.text.SimpleDateFormat;

public class MainActivity {
    public static void main(String []array){
        Scanner sc = new Scanner(System.in);

        // Create a Date object
        Date currentDate = new Date();

        // Create a SimpleDateFormat object to format the date and time
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmssss");

        // Format the current date and time
        String RandomKey = dateFormat.format(currentDate);

        
        

        //Mobile no of User
        System.out.println("Enter Your Mobile No : ");
        String UserMobile = sc.nextLine();

        //User-Name of User
        System.out.println("\nEnter Your UserName (At Least 8 Character)");
        String UserName = sc.nextLine();

        //Message by user
        System.out.println("Enter A Message : ");
        String Message = sc.nextLine();

        if(Message.length() == 0 || Message == "" || Message == " "){
            System.out.println("Please Enter A Message !!");
            return;
        }

        if(UserName.length() <=8){
            System.out.println("Please Enter A Valid User Name !!");
            return;
        }

        if(UserMobile.length() != 10){
            System.out.println("Enter 10 digit Valid Mobile Number !!");
            return;
        }


        //generating a key from sender to Encrypte
        // First 8 Char from Username
        String Char8 = UserName.toLowerCase().substring(0, 8);
        String Mobi8 = UserMobile.substring(0, 8);
        
        String PrimeKey = Char8+Mobi8;
        System.out.println("\n\nDetails Are : \n\n");
        //Encrypte Message With Random Key
        System.out.println("Encrypted Message : "+encryptMessage(Message, RandomKey));
        String EncMessage = encryptMessage(Message, RandomKey);
        System.out.println("\nPlain Key : "+RandomKey);
        System.out.println("\nEncrypted key : "+encryptPrimeryKey(RandomKey, PrimeKey));
        String EncKey = encryptPrimeryKey(RandomKey, PrimeKey);
        System.out.println("\nKey After Descryption : "+decryptRandomKey(EncKey, PrimeKey));
        String OrignalRandomKey = decryptRandomKey(EncKey, PrimeKey);
        System.out.println("\nDecrypted Message : "+decryptMessage(EncMessage, RandomKey));
        



    }


    //Encrypte Message with Random Key 
    public static String encryptMessage(String value ,String RandomKey) {
        try {
            SecretKeySpec keySpec = new SecretKeySpec(RandomKey.getBytes(), "AES");
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, keySpec);
            byte[] encryptedValue = cipher.doFinal(value.getBytes());
            return Base64.getEncoder().encodeToString(encryptedValue);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    //Encrypte the Random Key With Primary Key
    public static String encryptPrimeryKey(String value , String PrimeKey) {
        try {
            SecretKeySpec keySpec = new SecretKeySpec(PrimeKey.getBytes(), "AES");
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, keySpec);
            byte[] encryptedValue = cipher.doFinal(value.getBytes());
            return Base64.getEncoder().encodeToString(encryptedValue);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    //Descripting Message

    //Descrypting Random Key using Primary Key
    public static String decryptRandomKey(String value, String PrimeKey) {
        try {
            SecretKeySpec keySpec = new SecretKeySpec(PrimeKey.getBytes(), "AES");
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE, keySpec);
            byte[] decodedValue = Base64.getDecoder().decode(value);
            byte[] decryptedValue = cipher.doFinal(decodedValue);
            return new String(decryptedValue);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    //After Descrypting key descrypte message

    public static String decryptMessage(String value, String OrignalRandomKey) {
        try {
            SecretKeySpec keySpec = new SecretKeySpec(OrignalRandomKey.getBytes(), "AES");
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE, keySpec);
            byte[] decodedValue = Base64.getDecoder().decode(value);
            byte[] decryptedValue = cipher.doFinal(decodedValue);
            return new String(decryptedValue);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }




}
