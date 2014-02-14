
// This is a basic structure of the User class, there is still much to be done and al the methods to be implimented
package Console;

import Message.Message;
import Connection.*;
import Crypto.*;
import java.security.*;
import java.util.Scanner;



public class User {

//	private Message[] savedMessages;
        private SecureDetails s;
        public boolean loggedIn;
        private String realm;
        private ClientSend c;
        private byte[] publicKey;
        private String password;

	User() {
	
	}
            
        
        
        public void login(){
            
            realm = null;
            s = new SecureDetails();
            password = "1";
            Scanner in = new Scanner(System.in);
            KeyVault kv = new KeyVault();
            
            
            while (kv.checkPassword(password.toCharArray()) != true){
                
                System.out.println("Please enter your password: ");
                
                password = in.nextLine();
            
                if (kv.checkPassword(password.toCharArray()) == false){
                    System.out.println("Password Incorrect \n Press enter to try again");                
 
                }

            }
            
            System.out.println("Please Choose your realm 1 or 2: ");                
            realm = in.nextLine();            
            loggedIn = true;       
         
        }

        public void logout(){            
            loggedIn = false;            
        }
        
        public void readMessages(){
            
//            if (newMessages.length<=0){                
//                System.out.println("You have no new messages");                
//            }
//            else if(newMessages.length == 1){                
//                System.out.println("You have one new message");
//            }            
//            else{
//              System.out.println("You have " + newMessages.length + " new messages");
//            }            
//            System.out.println("You have " + s.getMessages().length + " saved messages");
//            
//            
            
        }
        
        
        public void recieveEmails() {
            
            ClientReceive.receive(s.getID());

            //return m;
        }
        
        public void createMessage() {
            c = new ClientSend();
            Message m = new Message();
            String recipitent = null;
            String contents = null;
            Scanner in = new Scanner(System.in);
            Encryption e = new Encryption();
            
            System.out.println("Please enter recpitent Id:");
            recipitent = in.nextLine();
            
            m.receiver = recipitent;
            PublicKey pk =  e.getKey(m.receiver);

            System.out.println("Please enter the message contents: ");
            contents = in.nextLine();          
    
            m.sender = e.encryptString(pk, s.getID());
            m.message = e.encryptString(pk, contents);
            ClientSend.sendMessage(m);

            
        } 
        
        public void deleteMessage(int x){
            
            
            // delete message from savedMessages[x] 
            
        }
        public static void main(String[] args) {      
            User u = new User();
            SecureDetails s = new SecureDetails();

            if (s.getRegistered() == true && u.loggedIn != true){
                u.login();
                Message[] m = u.recieveEmails();
                System.out.println("You have " + m.length + "new messages");
            }
            else if(s.getRegistered() == false){
                    Register r = new Register();
                    u.login();
                    u.createMessage();     
        }

    }

        
}
