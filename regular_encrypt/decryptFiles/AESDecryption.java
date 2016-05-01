import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

/*
Decrypts a file and places it all under the same key
*/

//args[0] ~ name of file to be decrypted
//args[1] ~ name of file where decrypted text will be placed

//built on top of the following:
//http://www.quickprogrammingtips.com/java/how-to-encrypt-and-decrypt-data-in-java-using-aes-algorithm.html
 
public class AESDecryption {
 
    public static void main(String[] args) throws Exception {
				SecretKey secKey = getSecretEncryptionKey();
				PrintWriter writer = new PrintWriter(args[1], "UTF-8");				


				Scanner sc2 = null;
				try {
				    sc2 = new Scanner(new File(args[0]));
				} catch (FileNotFoundException e) {
				    e.printStackTrace();  
				}
				while (sc2.hasNextLine()) {
				        Scanner s2 = new Scanner(sc2.nextLine());
				    while (s2.hasNext()) {
				        String s = s2.next();
								//System.out.println(decryptText(hexToBytes(s.toCharArray()), secKey));
								writer.print(decryptText(hexToBytes(s.toCharArray()), secKey)+" ");
				    }
				}

				writer.close();
        String plainText = "World";

        byte[] cipherText = encryptText(plainText, secKey);
        String decryptedText = decryptText(cipherText, secKey);
    }
 
    public static SecretKey getSecretEncryptionKey() throws Exception{
				String strkey = "my secret key123";//16 bytes
        SecretKey secKey = new SecretKeySpec(strkey.getBytes("UTF-8"), "AES");
        return secKey;
    }
    
    /**
     * Encrypts plainText in AES using the secret key
     */
    public static byte[] encryptText(String plainText,SecretKey secKey) throws Exception{
		// AES defaults to AES/ECB/PKCS5Padding in Java 7
        Cipher aesCipher = Cipher.getInstance("AES");
        aesCipher.init(Cipher.ENCRYPT_MODE, secKey);
        byte[] byteCipherText = aesCipher.doFinal(plainText.getBytes());
        return byteCipherText;
    }
    
    /**
     * Decrypts encrypted byte array using the key used for encryption.
     */
    public static String decryptText(byte[] byteCipherText, SecretKey secKey) throws Exception {
		// AES defaults to AES/ECB/PKCS5Padding in Java 7
        Cipher aesCipher = Cipher.getInstance("AES");
        aesCipher.init(Cipher.DECRYPT_MODE, secKey);
        byte[] bytePlainText = aesCipher.doFinal(byteCipherText);
        return new String(bytePlainText);
    }
    
    /**
     * Convert a binary byte array into readable hex form
     */
    private static String  bytesToHex(byte[] hash) {
        return DatatypeConverter.printHexBinary(hash);
    }

		//http://www.java2s.com/Code/Java/Development-Class/ConverthexToBytes.htm
		public static byte[] hexToBytes(char[] hex) {
		  int length = hex.length / 2;
		  byte[] raw = new byte[length];
		  for (int i = 0; i < length; i++) {
		    int high = Character.digit(hex[i * 2], 16);
		    int low = Character.digit(hex[i * 2 + 1], 16);
		    int value = (high << 4) | low;
		    if (value > 127)
		      value -= 256;
		    raw[i] = (byte) value;
		  }
		  return raw;
  }
}
