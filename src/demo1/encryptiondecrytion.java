/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package demo1;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.security.InvalidKeyException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.Signature;
import java.util.Base64;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.*;  
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.*; 

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.Signature;
import java.util.Scanner;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.Signature;
import java.util.Arrays;
import java.util.Scanner;
import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.border.TitledBorder;


import javax.crypto.Cipher;  
import javax.crypto.SecretKey;  
import javax.crypto.SecretKeyFactory;  
import javax.crypto.spec.IvParameterSpec;  
import javax.crypto.spec.PBEKeySpec;  
import javax.crypto.spec.SecretKeySpec;  
import java.nio.charset.StandardCharsets;  
import java.security.InvalidAlgorithmParameterException;  
import java.security.InvalidKeyException;  
import java.security.NoSuchAlgorithmException;  
import java.security.spec.InvalidKeySpecException;  
import java.security.spec.KeySpec;  
import java.util.Base64;  
import javax.crypto.BadPaddingException;  
import javax.crypto.IllegalBlockSizeException;  
import javax.crypto.NoSuchPaddingException;  
  

import java.util.Base64;
import java.util.Scanner;
import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 *
 * @author user
 */
public class encryptiondecrytion {
    private static final String SIGNING_ALGORITHM= "SHA256withRSA";
    private static final String RSA = "RSA";
    private static Scanner sc;
    
    public static byte[] Create_Digital_Signature(byte[] input,PrivateKey Key)throws Exception
    {
        Signature signature = Signature.getInstance(SIGNING_ALGORITHM);
        signature.initSign(Key);
        signature.update(input);
        return signature.sign();
    }
    
    public static KeyPair Generate_RSA_KeyPair()throws Exception
    {
        SecureRandom secureRandom = new SecureRandom();
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance(RSA);
        keyPairGenerator.initialize( 2048,secureRandom);
        return keyPairGenerator.generateKeyPair();
    }
  
    public static boolean Verify_Digital_Signature(byte[] input,byte[] signatureToVerify,PublicKey key)throws Exception
    {
        Signature signature = Signature.getInstance(SIGNING_ALGORITHM);
        signature.initVerify(key);
        signature.update(input);
        return signature.verify(signatureToVerify);
    }
    private static final String encryptionKey           = "ABCDEFGHIJKLMNOP";
    private static final String characterEncoding       = "UTF-8";
    private static final String cipherTransformation    = "AES/CBC/PKCS5PADDING";
    private static final String aesEncryptionAlgorithem = "AES";
    
    public static String Aencrypt(String plainText) {
        String encryptedText = "";
        try {
            Cipher cipher   = Cipher.getInstance(cipherTransformation);
            byte[] key      = encryptionKey.getBytes(characterEncoding);
            SecretKeySpec secretKey = new SecretKeySpec(key, aesEncryptionAlgorithem);
            IvParameterSpec ivparameterspec = new IvParameterSpec(key);
            cipher.init(Cipher.ENCRYPT_MODE, secretKey, ivparameterspec);
            byte[] cipherText = cipher.doFinal(plainText.getBytes("UTF8"));
            Base64.Encoder encoder = Base64.getEncoder();
            encryptedText = encoder.encodeToString(cipherText);

        } catch (Exception E) {
             System.err.println("Encrypt Exception : "+E.getMessage());
        }
        return encryptedText;
    }
    public static String Adecrypt(String encryptedText) {
        String decryptedText = "";
        try {
            Cipher cipher = Cipher.getInstance(cipherTransformation);
            byte[] key = encryptionKey.getBytes(characterEncoding);
            SecretKeySpec secretKey = new SecretKeySpec(key, aesEncryptionAlgorithem);
            IvParameterSpec ivparameterspec = new IvParameterSpec(key);
            cipher.init(Cipher.DECRYPT_MODE, secretKey, ivparameterspec);
            Base64.Decoder decoder = Base64.getDecoder();
            byte[] cipherText = decoder.decode(encryptedText.getBytes("UTF8"));
            decryptedText = new String(cipher.doFinal(cipherText), "UTF-8");

        } catch (Exception E) {
            System.err.println("decrypt Exception : "+E.getMessage());
        }
        return decryptedText;
    }
    public static void main(String[] args) throws Exception  {
        String input= "This IS A"+ " Final Year Project";
        KeyPair keyPair = Generate_RSA_KeyPair();
        
        PublicKey publicKey = keyPair.getPublic();

        String publicKeyString = Base64.getEncoder().encodeToString(publicKey.getEncoded());

    	/*System.out.println("public key = "+ publicKeyString);*/
        
        PrivateKey privateKey = keyPair.getPrivate();

        String privateKeyString = Base64.getEncoder().encodeToString(privateKey.getEncoded());

    	/*System.out.println("private key = "+ privateKeyString);*/

  
        // Function Call
        byte[] signature = Create_Digital_Signature( input.getBytes(),keyPair.getPrivate());
        
        String k="";
        for(int i=0;i<signature.length; i++){
            k+=signature[i];
        }
        /*System.out.println("Signature Value:"+k);*/
  
        Path path = Paths.get("C:\\Users\\user\\Desktop\\NetBeansDoc\\Digital.txt");
        try{
            Files.writeString(path, k, StandardCharsets.UTF_8);
        }
        catch(IOException ex){
            System.out.print("INvalid Path");
        }
        
     
        
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter String User Wanted To Encrypt: ");
        String plainString = sc.nextLine();
        String encyptStr   = Aencrypt(plainString);
        System.out.println("AES Encrypt String  : "+encyptStr);
        
        
        Cipher encryptionCipher = Cipher.getInstance("RSA");
        encryptionCipher.init(Cipher.ENCRYPT_MODE,privateKey);
        String message = encyptStr;
    	byte[] encryptedMessage =
    	encryptionCipher.doFinal(message.getBytes());
    	String encryption = Base64.getEncoder().encodeToString(encryptedMessage);
                
               
               
    	System.out.println("RSA encrypted String = "+encryption);
                
                
       /* System.out.println("Verification: "+ Verify_Digital_Signature(input.getBytes(),signature, keyPair.getPublic()));*/
     
        
        System.out.println("Enter the digital Signature:");
        String str = sc.nextLine();
      
        
        if( str.equals(k)){
            
        /*JFrame jfm1 = new JFrame();
        jfm1.setTitle("Android Lock Screen By Tony Stark");
        jfm1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jfm1.setSize(400, 550);
        jfm1.setLocation(300,50);
        jfm1.getContentPane().setBackground(Color.WHITE);
        AndroidLockScreenNew an1= new AndroidLockScreenNew();
        jfm1.setLayout(new BorderLayout());
        jfm1.add(an1,BorderLayout.CENTER);
        JLabel lb1 = new JLabel("Pattern Will Be Displayed Here");
        lb1.setFont(new Font("Arial",lb1.getFont().getStyle(),20));
        lb1.setBorder(BorderFactory.createTitledBorder(null, "Output", TitledBorder.CENTER, TitledBorder.ABOVE_TOP, new Font("Tahoma", 1, 24), new Color(204, 0, 0)));
        jfm1.add(lb1,BorderLayout.SOUTH);
        an1.setOutputComponent(lb1);
        jfm1.setVisible(true);
        
        Path fileName
            = Path.of("C:\\Users\\user\\Desktop\\NetBeansDoc\\Lock.txt");
        String l = Files.readString(fileName);
        
        System.out.println(l);
        
        Path fileName1
            = Path.of("C:\\Users\\user\\Desktop\\NetBeansDoc\\patterndecrypter.txt");
        String m = Files.readString(fileName1);
        
        System.out.println(m);
        File myObj = new File("C:\\Users\\user\\Desktop\\NetBeansDoc\\patterndecrypter.txt"); 
        myObj.delete();
        
        if(l.equals(m)){*/
            
            
            
             
            
          Cipher decryptionCipher = Cipher.getInstance("RSA");
    	  decryptionCipher.init(Cipher.DECRYPT_MODE,publicKey);
    	  byte[] decryptedMessage;
          decryptedMessage = decryptionCipher.doFinal(encryptedMessage);
    	  String decryption = new String(decryptedMessage);
    	  System.out.println("RSA decrypted message = "+decryption);
                
          String decryptStr  = Adecrypt(decryption);
          System.out.println("AES Decrypt String  : "+decryptStr);
                
        /*}
        else{
                System.out.println("Wrong Pattern");
        }*/
            
        }
        else{
            System.out.println("Wrong Signature");
        }
       
    
   
    		
 

}
    
}
