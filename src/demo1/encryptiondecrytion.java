/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package demo1;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import static java.awt.SystemColor.window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
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
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;

/**
 *
 * @author user
 */
public class encryptiondecrytion {
    private static final String SIGNING_ALGORITHM= "SHA256withRSA";
    private static final String RSA = "RSA";
    private static Scanner sc;
    
    private static final String encryptionKey           = "ABCDEFGHIJKLMNOP";
    private static final String characterEncoding       = "UTF-8";
    private static final String cipherTransformation    = "AES/CBC/PKCS5PADDING";
    private static final String aesEncryptionAlgorithem = "AES";
    
    
    
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
     
        
        /*System.out.println("Enter the digital Signature:");
        String str = sc.nextLine();*/
      
        boolean b;
        b = Verify_Digital_Signature(
                input.getBytes(),
                signature, keyPair.getPublic());
        if(b){
            System.out.println("Digital Verification is True");
            JFrame jfm = new JFrame();
            jfm.setTitle("Android Lock Screen By Tony Stark");
            jfm.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            jfm.setSize(400, 550);
            jfm.setLocation(300,50);
            jfm.getContentPane().setBackground(Color.WHITE);
            AndroidLockScreenNew an= new AndroidLockScreenNew();
            jfm.setLayout(new BorderLayout());
            jfm.add(an,BorderLayout.CENTER);
            JLabel lb = new JLabel("Pattern Will Be Displayed Here");
            lb.setFont(new Font("Arial",lb.getFont().getStyle(),20));
            lb.setBorder(BorderFactory.createTitledBorder(null, "Click The Button", TitledBorder.CENTER, TitledBorder.ABOVE_TOP, new Font("Tahoma", 1, 24), new Color(204, 0, 0)));
            jfm.add(lb,BorderLayout.SOUTH);
            an.setOutputComponent(lb);
            JLabel lb1 = new JLabel(" Submit Button");
            lb1.setFont(new Font("Arial",lb.getFont().getStyle(),20));
            jfm.add(lb1,BorderLayout.SOUTH);
            an.setOutputComponent(lb1);
            jfm.setVisible(true);
        
            lb1.addMouseListener(new MouseListener(){
                @Override
                public void mouseClicked(MouseEvent e) {
                    String str="", str1="";
                    lb1.setText("   Submitted Succefully"); 
                    jfm.dispose();
                
                    Path fileName1 = Path.of("C:\\Users\\user\\Desktop\\NetBeansDoc\\Lock.txt");
                    try {
                        str = Files.readString(fileName1);
                    } catch (IOException ex) {
                        Logger.getLogger(encryptiondecrytion.class.getName()).log(Level.SEVERE, null, ex);
                    }
                
                    Path fileName2 = Path.of("C:\\Users\\user\\Desktop\\NetBeansDoc\\patterndecrypter.txt");
                    try {
                        str1 = Files.readString(fileName2);
                    } catch (IOException ex) {
                        Logger.getLogger(encryptiondecrytion.class.getName()).log(Level.SEVERE, null, ex);
                    }
// Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody

                    if(str.equals(str1)){
                    
                        Cipher decryptionCipher;
                        try {
                            decryptionCipher = Cipher.getInstance("RSA");
                            decryptionCipher.init(Cipher.DECRYPT_MODE,publicKey);
                            byte[] decryptedMessage;
                            decryptedMessage = decryptionCipher.doFinal(encryptedMessage);
                            String decryption = new String(decryptedMessage);
                            System.out.println("RSA decrypted message = "+decryption);
                
                            String decryptStr  = Adecrypt(decryption);
                            System.out.println("AES Decrypt String  : "+decryptStr);
                        } catch (NoSuchAlgorithmException ex) {
                            Logger.getLogger(encryptiondecrytion.class.getName()).log(Level.SEVERE, null, ex);
                        } catch (NoSuchPaddingException ex) {
                            Logger.getLogger(encryptiondecrytion.class.getName()).log(Level.SEVERE, null, ex);
                        } catch (IllegalBlockSizeException ex) {
                            Logger.getLogger(encryptiondecrytion.class.getName()).log(Level.SEVERE, null, ex);
                        } catch (BadPaddingException ex) {
                            Logger.getLogger(encryptiondecrytion.class.getName()).log(Level.SEVERE, null, ex);
                        } catch (InvalidKeyException ex) {
                            Logger.getLogger(encryptiondecrytion.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        System.exit(0);
                    }
                    else{
                        System.out.println("Wrong Pattern");
                        System.exit(0);
                    }
                
                 
                }

                @Override
                public void mousePressed(MouseEvent e) {
                    // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
                }

                @Override
                public void mouseReleased(MouseEvent e) {
                    lb1.setText(RSA); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
                }

                @Override
                public void mouseEntered(MouseEvent e) {
                // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
                }

                @Override
                public void mouseExited(MouseEvent e) {
                 // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
                }
            
            });
       
            
            
            
             
            
         
               
        }
        else{
            System.out.println("Wrong Signature");
            System.exit(0);
        }
       
    
   
    		
 

    }
    
}
