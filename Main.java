import java.util.Scanner;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import java.util.Base64;
import java.util.Date;
import java.util.Random;
import java.text.SimpleDateFormat;

class Main{
    private static final String USER_ID1 = "sa76YvpKqPNEXX0RURlME9uArLz2";
    private static final String USER_ID2 = "J5hPqRvzZbgwU7K8s1IoYcFt4nX2";

    public static void main(String[] args) {
        // Generating an Chat Room ID of 128 bits by using 2 User Id
        Main m = new Main();
        Scanner sc = new Scanner(System.in);
        Random random = new Random();
        // Create a Date object
        Date currentDate = new Date();

        // Create a SimpleDateFormat object to format the date and time
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyMMddHHmmss");

        //ChatroomId as our Primary Key
        String primaryKey = m.generateChatroom(USER_ID1, USER_ID2);
        
        

        // Format the current date and time with a 2 digt Random No
        //this is our Random Key i.e Secondary Key
        // 0 = 3 for message validation
        String secondaryKey = dateFormat.format(currentDate)+(random.nextInt(90) + 10)+"-0";

        System.out.println("Chatroom ID : "+primaryKey);
        System.out.println("Secondary Key : "+secondaryKey);

        System.out.println("Your Username : ");
        String sender = sc.nextLine();

        System.out.println("\nReciver Username : ");
        String reciver = sc.nextLine();

        System.out.println("Enter your Message : ");
        String message = sc.nextLine();

        String encMessage = m.encryptMessage(message, secondaryKey);
        String ensSenderReciver = m.encryptMessage(sender+"*"+reciver, secondaryKey);
        String encSecondaryKey = m.encryptMessage(secondaryKey, primaryKey);

        System.out.println("\nHow The Data Look on Server\n");
        System.out.println("sender*reciver : "+ensSenderReciver);
        System.out.println("message : "+encMessage);
        System.out.println("Key : "+encSecondaryKey);
        
        System.out.println("\nOrignal Values : \n");
        String myKey = decryptMessage(encSecondaryKey, primaryKey);
        System.out.println("Key : "+myKey);

        String user = decryptMessage(ensSenderReciver, myKey);
        String[] bothUser = user.split("\\*");

        System.out.println("Sender : "+bothUser[0]);
        System.out.println("Reciver : "+bothUser[1]);

        System.out.println("Message : "+decryptMessage(encMessage, myKey));



    }


    private String generateChatroom(String ID1, String ID2){
        String chatroomId = ID1.substring(0, 8) + ID2.substring(0, 8);
        return chatroomId;
    }

    //Method to encrypt the values
    private String encryptMessage(String value, String key){
        try {
            SecretKeySpec keySpec = new SecretKeySpec(key.getBytes(), "AES");
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, keySpec);
            byte[] encryptedValue = cipher.doFinal(value.getBytes());
            return Base64.getEncoder().encodeToString(encryptedValue);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    //method to decrypt the values
    private static String decryptMessage(String value, String key) {
        try {
            SecretKeySpec keySpec = new SecretKeySpec(key.getBytes(), "AES");
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