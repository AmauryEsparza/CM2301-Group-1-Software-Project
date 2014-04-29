/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Crypto;

import Console.User;
import java.security.Key;
import java.security.KeyPair;
import java.security.PublicKey;
import java.security.cert.Certificate;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author maxchandler
 */
public class KeyVaultTest {
    
    public KeyVaultTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
        User.setPassword("password".toCharArray());
        if(KeyVault.checkIfKsExists() == true)
            KeyVault.destroyKeyStore();
        if(KeyVault.checkIfTsExists() == true)
            KeyVault.destroyTrustStore();
    }
    
    @AfterClass
    public static void tearDownClass() {
        if(KeyVault.checkIfKsExists() == true)
            KeyVault.destroyKeyStore();
        if(KeyVault.checkIfTsExists() == true)
            KeyVault.destroyTrustStore();
    }
    
    @Before
    public void setUp() {
        KeyVault.createKeyStore();
    }
    
    @After
    public void tearDown() {
        KeyVault.destroyKeyStore();
        KeyVault.destroyTrustStore();
    }

    /**
     * Test of checkPassword method, of class KeyVault.
     */
    @Test
    public void testCheckPassword() {
        System.out.println("checkPassword");
        char[] localPassword = "password".toCharArray();
        boolean expResult = true;
        boolean result = KeyVault.checkPassword(localPassword);
        assertEquals(expResult, result);
    }
    
        /**
     * Test of checkPassword method, of class KeyVault.
     */
    @Test
    public void testCheckBadPassword() {
        System.out.println("checkBadPassword");
        char[] localPassword = "badPassword".toCharArray();
        boolean expResult = false;
        boolean result = KeyVault.checkPassword(localPassword);
        assertEquals(expResult, result);
    }

    /**
     * Test of bytesToKey method, of class KeyVault.
     */
    @Test
    public void testBytesToKey() {
        System.out.println("bytesToKey");
        KeyGen kg = new KeyGen();
        KeyPair kp = kg.generateRSAKeys();
        PublicKey pubKey = kp.getPublic();
        byte[] encoded = pubKey.getEncoded();
        PublicKey expResult = pubKey;
        PublicKey result = KeyVault.bytesToKey(encoded);
        assertEquals(expResult, result);
    }

    /**
     * Test of createKeyStore method, of class KeyVault.
     */
    @Test
    public void testCreateKeyStore() {
        System.out.println("createKeyStore");
        KeyVault.destroyKeyStore();
        KeyVault.createKeyStore();
    }

    /**
     * Test of destroyKeyStore method, of class KeyVault.
     */
    @Test
    public void testDestroyKeyStore() {
        System.out.println("destroyKeyStore");
        boolean expResult = true;
        boolean result = KeyVault.destroyKeyStore();
        assertEquals(expResult, result);
        KeyVault.createKeyStore();
    }

    /**
     * Test of loadKeyStore method, of class KeyVault.
     */
    @Test
    public void testLoadKeyStore() {
        System.out.println("loadKeyStore");
        KeyVault.loadKeyStore();
    }

    /**
     * Test of setRSAKeys method, of class KeyVault.
     */
    @Test
    public void testSetRSAKeys() {
        System.out.println("setRSAKeys");
        KeyVault.setRSAKeys();
    }

    /**
     * Test of setAESKey method, of class KeyVault.
     */
    @Test
    public void testSetAESKey() {
        System.out.println("setAESKey");
        KeyVault.setAESKey();
    }

    /**
     * Test of getRSAKeys method, of class KeyVault.
     */
    @Test
    public void testGetRSAKeys() {
        System.out.println("getRSAKeys");
        //NEEDS TO CREATE RSA KEYS THEN COMPARE TO THE ONES BROUGHT BACK!
        KeyVault.setRSAKeys();
        KeyPair result = KeyVault.getRSAKeys();
        System.out.println(result.getPublic() +"\n" + result.getPrivate());
        KeyPair dummy = new KeyPair(null, null);
        if(!result.getClass().equals(dummy.getClass())){
          fail("Not correct return type");
        }
    }

    /**
     * Test of getAESKey method, of class KeyVault.
     */
    @Test
    public void testGetAESKey() {
        System.out.println("getAESKey");
        KeyVault.setAESKey();
        Key k = KeyVault.getAESKey();
        System.out.println(k);
        Key result = KeyVault.getAESKey();
        assertEquals(k, result);
    }

    /**
     * Test of checkIfKsExists method, of class KeyVault.
     */
    @Test
    public void testCheckIfKsExists() {
        System.out.println("checkIfKsExists");
        boolean expResult = true;
        boolean result = KeyVault.checkIfKsExists();
        assertEquals(expResult, result);
    }

    /**
     * Test of createTrustStore method, of class KeyVault.
     */
    @Test
    public void testCreateTrustStore() {
        System.out.println("createTrustStore");
        KeyVault.destroyTrustStore();
        KeyVault.createTrustStore();
    }

    /**
     * Test of destroyTrustStore method, of class KeyVault.
     */
    @Test
    public void testDestroyTrustStore() {
        System.out.println("destroyTrustStore");
        KeyVault.destroyTrustStore();
        KeyVault.createTrustStore();
    }

    /**
     * Test of loadTrustStore method, of class KeyVault.
     */
    @Test
    public void testLoadTrustStore() {
        System.out.println("loadTrustStore");
        KeyVault.loadTrustStore();
    }

    /**
     * Test of addTrust method, of class KeyVault.
     */
    @Test
    public void testAddTrust() {
        System.out.println("addTrust");
        Certificate trustedCert = null ;
        KeyVault.addTrust(trustedCert);
    }

    /**
     * Test of checkIfTsExists method, of class KeyVault.
     */
    @Test
    public void testCheckIfTsExists() {
        System.out.println("checkIfTsExists");
        boolean expResult = true;
        boolean result = KeyVault.checkIfTsExists();
        assertEquals(expResult, result);
    }
    
}
