/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Console;

import Connection.ClientSend;
import Message.Message;
import java.io.File;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Marc
 */
public class RegisterTest {
    
    public RegisterTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {

    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
        File details = new File("user2.ser");
        File kv = new File("keystore");
        details.delete();
        kv.delete();
        

    }

    @Test
    public void testSomeMethod() {
        // TODO review the generated test code and remove the default call to fail.
        new Register("pass".toCharArray());
    }
}