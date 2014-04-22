/*
*The KeyVault is needed to store cryptographic 
*keys securely on the local system.
*/

package Crypto;

import Connection.Server;
import java.io.*;
import java.security.*;
import java.security.cert.CertificateException;
import java.security.spec.InvalidKeySpecException;

import java.security.spec.X509EncodedKeySpec;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.*;
/*
*KeyVault is responcible for storing and retreiving keys from the vault, it also provides a password check for user login. 
*/

public class KeyVault{
    //incase we decide on different system setup
    private static final String KEY_STORE_DIR = "";
    private static final String KEY_STORE_NAME = "keystore";
    private static final String KEY_STORE_TYPE = "JCEKS";

    public KeyVault(){
    }

    //PASSWORD IN REFERENCE IS TO OPEN THE KEYVAULT (LOCAL PASSWORD)
    public static boolean checkPassword(char[] localPassword){
        try{
            KeyStore ks  = KeyStore.getInstance(KEY_STORE_TYPE);
            ks.load(new FileInputStream(KEY_STORE_DIR + KEY_STORE_NAME), localPassword);
            return true;
        }catch(KeyStoreException | IOException | NoSuchAlgorithmException | CertificateException ex){
            return false;
        } 
    }

    public PublicKey bytesToKey(byte[] bytes){
        try{
            PublicKey publicKey = KeyFactory.getInstance("RSA").generatePublic(new X509EncodedKeySpec(bytes));
        return publicKey;
        }catch( NoSuchAlgorithmException | InvalidKeySpecException ex){
            throw new RuntimeException(ex);
        }
    }

    //Keystore methods	
    public static void createKeyStore() {
        try {
            KeyStore ks = KeyStore.getInstance(KEY_STORE_TYPE);  
            //KeyStore.ProtectionParameter passwordProtection = new KeyStore.PasswordProtection(User.getPassword());
            ks.load(null, Server.getPassword());
            if (checkIfKsExists() != true){
                try (FileOutputStream fos = new FileOutputStream(KEY_STORE_DIR + KEY_STORE_NAME)) {
                    ks.store(fos, Server.getPassword());
                }
            }
            else
                throw new RuntimeException("KEYSTORE ALREADY EXISTS");
        }catch(IOException | RuntimeException | KeyStoreException ex){
            throw new RuntimeException(ex);
        
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(KeyVault.class.getName()).log(Level.SEVERE, null, ex);
        } catch (CertificateException ex) {
            Logger.getLogger(KeyVault.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static boolean destroyKeyStore(){
        if(checkPassword(Server.getPassword())){
            File vault = new File(KEY_STORE_DIR + KEY_STORE_NAME);
            if(vault.delete()){
                return true;
            }
        }
        return false;
    }

    public static KeyStore loadKeyStore(){       
        try{
            KeyStore ks  = KeyStore.getInstance(KEY_STORE_TYPE);
            ks.load(new FileInputStream(KEY_STORE_DIR + KEY_STORE_NAME), Server.getPassword());
            return ks;
        }catch(IOException | KeyStoreException | NoSuchAlgorithmException | CertificateException ex){
            throw new RuntimeException(ex);
        }
    }

    public static boolean checkIfKsExists(){
        File f = new File(KEY_STORE_DIR + KEY_STORE_NAME);
        return f.exists() && !f.isDirectory();
    }

    //Setter methods
    public static void setRSAKeys(){
        try{
            KeyStore ks = loadKeyStore();
            //generate the rsaKeys
            KeyGen kg = new KeyGen();
            KeyPair rsaKeys = kg.generateRSAKeys();
            PublicKey pubKey = rsaKeys.getPublic();
            PrivateKey priKey = rsaKeys.getPrivate();

            KeyStore.ProtectionParameter passwordProtection = new KeyStore.PasswordProtection(Server.getPassword());

            KeyStore.PrivateKeyEntry rsaEntry = new KeyStore.PrivateKeyEntry(priKey, KeyGen.generateCertificate(pubKey, priKey));
            ks.setEntry("rsaKeys", rsaEntry, passwordProtection);

            FileOutputStream fos = new FileOutputStream(KEY_STORE_DIR + KEY_STORE_NAME);
            ks.store(fos, Server.getPassword());
            fos.close();
        }catch(IOException | KeyStoreException | NoSuchAlgorithmException | CertificateException ex){
            throw new RuntimeException(ex);
        }
    }

    public static void setAESKey(){
        try{
            KeyStore ks = loadKeyStore();
            //generate the aesKey
            KeyGen kg = new KeyGen();
            SecretKey aesKey = kg.generateAESKey();

            KeyStore.ProtectionParameter passwordProtection = new KeyStore.PasswordProtection(Server.getPassword());

            KeyStore.SecretKeyEntry aesKeyEntry = new KeyStore.SecretKeyEntry(aesKey);
            ks.setEntry("aesKey", aesKeyEntry, passwordProtection);

            FileOutputStream fos = new FileOutputStream(KEY_STORE_DIR + KEY_STORE_NAME);
            ks.store(fos, Server.getPassword());
            fos.close();
            
        }catch(IOException | KeyStoreException | NoSuchAlgorithmException | CertificateException ex){
            //logger.error("Cannot close connection");
        throw new RuntimeException(ex);
        }
    }

    //Getter methods
    public static KeyPair getRSAKeys(){
        try{
            KeyStore ks = loadKeyStore();
            KeyStore.ProtectionParameter passwordProtection = new KeyStore.PasswordProtection(Server.getPassword());
            KeyStore.PrivateKeyEntry rsaKeyEntry = (KeyStore.PrivateKeyEntry) ks.getEntry("rsaKeys", passwordProtection);
            PrivateKey priKey = rsaKeyEntry.getPrivateKey();
            java.security.cert.Certificate rsaCert = rsaKeyEntry.getCertificate();
            PublicKey pubKey = rsaCert.getPublicKey();
            KeyPair rsaKeys = new KeyPair(pubKey, priKey);
            return rsaKeys;
        }catch(KeyStoreException | NoSuchAlgorithmException | UnrecoverableEntryException ex){
            throw new RuntimeException(ex);
        }
    }


    public static Key getAESKey(){
        try{
            KeyStore ks = loadKeyStore();
            KeyStore.ProtectionParameter passwordProtection = new KeyStore.PasswordProtection(Server.getPassword());
            KeyStore.SecretKeyEntry aesKeyEntry = (KeyStore.SecretKeyEntry) ks.getEntry("aesKey", passwordProtection);
            Key aesKey = aesKeyEntry.getSecretKey();
            return aesKey;
        }catch(KeyStoreException | NoSuchAlgorithmException | UnrecoverableEntryException ex){
            throw new RuntimeException(ex);
        }
    }
}
