/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Connection;

import Message.*;
import java.io.*;
import java.util.Stack;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.net.ssl.*;

/**
 *
 * @author Marc
 */
public class NodeHandler implements Runnable {

    private static boolean debug = true;
    private SSLSocket socket;
    private NodeList n;
    private ServerStorage s;

    NodeHandler(SSLServerSocket s) {
        try {
            this.socket = (SSLSocket) s.accept();
        } catch (IOException ex) {
            Logger.getLogger(NodeHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void run() {
        try {
            InputStream is = socket.getInputStream();
            ObjectInputStream ois = new ObjectInputStream(is);
            Stack in = (Stack) ois.readObject();
            Stack t = in;
            System.out.println("Stack recieved: " + in);
            Object o = t.peek();

            is.close();
            ois.close();

            if (o instanceof MessageServer) {
                s = new ServerStorage();

                s.updateMessageServerDetails(in);
            } else if (o instanceof IDServer) {
                s = new ServerStorage();
                s.updateIDSeverDetails(in);
            } else {
                n = new NodeList();
                n.updateList(in);
            }
            n = null;
            s = null;
        } catch (IOException | ClassNotFoundException ex) {
            Logger.getLogger(NodeHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
