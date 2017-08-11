package pers.pdfstuff.pdfmerger.commons.core;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.BindException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.List;
import java.util.logging.Logger;

public class ApplicationInstanceManager {

    private static final Logger logger = LogsCenter.getLogger(ApplicationInstanceManager.class);
    private static ApplicationInstanceListener subListener;

    private static final int SINGLE_INSTANCE_NETWORK_SOCKET = 31026;
    private static ServerSocket socket = null;
    
    public static ServerSocket getServerSocket() {
        return socket;
    }
    
    public static void closeSocket() throws IOException {
        if (socket != null && !socket.isClosed()) {
            logger.info("Closing server socket");
            socket.close();
        }
    }
    
    public static boolean registerInstance(List<String> rawArgs) {
        final String FILE_NAME;
        FILE_NAME = assignFileName(rawArgs);
        
        boolean returnValueOnError = true;
        try {
            socket = new ServerSocket();
            socket.setReuseAddress(true);
            socket.setSoTimeout(0);
            socket.bind(new InetSocketAddress(InetAddress.getLocalHost(), SINGLE_INSTANCE_NETWORK_SOCKET), 10);
            logger.info("Listening for application instances on socket " + SINGLE_INSTANCE_NETWORK_SOCKET);
            InstanceListenerThread listener = new InstanceListenerThread(socket);
            listener.start();
        } catch (BindException e) {
            /**
             * Could not open a SERVER socket => not first instance. Send
             * message containing command line arguments to existing server
             * socket.
             * 
             */
            e.printStackTrace();
            return sendMessageToFirstInstance(FILE_NAME, returnValueOnError);
        } catch (UnknownHostException e) {
            logger.severe(e.getMessage());
            return returnValueOnError;
        } catch (IOException e) {
            e.printStackTrace();
            return sendMessageToFirstInstance(FILE_NAME, returnValueOnError);
        }
        return true;

    }

    private static String assignFileName(List<String> rawArgs) {
        final String FILE_NAME;
        if (rawArgs != null && !rawArgs.isEmpty()) {
            FILE_NAME = rawArgs.get(0);
        } else {
            FILE_NAME = "";
        }
        return FILE_NAME;
    }

    /**
     * Writes a message to the socket opened by first instance and exits
     * 
     * @param FILE_NAME
     *            This is the message
     * @param returnValueOnError
     * @return whether message sending was successful
     */
    private static boolean sendMessageToFirstInstance(final String FILE_NAME, boolean returnValueOnError) {
        Socket clientSocket;
        try {
            clientSocket = new Socket(InetAddress.getLocalHost(), SINGLE_INSTANCE_NETWORK_SOCKET);
            OutputStream out = clientSocket.getOutputStream();
            out.write(FILE_NAME.getBytes());
            out.close();
            clientSocket.close();
            logger.info("Successfully notified first instance.");
            return false;
        } catch (IOException e2) {
            // TODO Auto-generated catch block
            e2.printStackTrace();
            logger.severe("Error connecting to local port for single instance notification");
            return returnValueOnError;
        }
    }

    //Calls the new instance event handler in ApplicationInstanceListener
    private static void fireNewInstance(String message) {
        if (subListener != null) {
            subListener.newInstanceCreated(message);
        }

    }

    /**
     * Sets the sublistener. Allows for modifying the multiple instance event
     * handling.
     * 
     * @param listener
     *            Holds method to be overriden
     */
    public static void setApplicationInstanceListener(ApplicationInstanceListener listener) {
        subListener = listener;
    }

    /**
     * ================== INNER CLASS ===========================
     */

    private static class InstanceListenerThread extends Thread {

        public InstanceListenerThread(ServerSocket socket) {
            super(new InstanceListenerRunnable(socket));
        }
    }

    /**
     * The runnable will listen for messages from new instances and respond
     * accordingly
     * 
     * @author Naren
     *
     */
    private static class InstanceListenerRunnable implements Runnable {
        final ServerSocket socket;

        public InstanceListenerRunnable(ServerSocket socket) {
            this.socket = socket;
        }

        @Override
        public void run() {
            while (!socket.isClosed()) {
                try {
                    Socket client = socket.accept();
                    BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));
                    String message = in.readLine();
                    if (message != null) {
                        logger.info("Message present, handling message...");
                        fireNewInstance(message);
                    }
                    in.close();
                    client.close();
                } catch (IOException e) {
                    try {
                        socket.close();
                    } catch (IOException e1) {
                        // TODO Auto-generated catch block
                        e1.printStackTrace();
                    }
                }
            }
        }
    }
}
