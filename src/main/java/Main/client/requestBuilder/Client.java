package Main.client.requestBuilder;

import Main.server.model.Product;
import javafx.collections.ObservableList;

import java.io.*;
import java.net.Socket;
import java.util.List;

public class Client {
    /**
     * when taking action in graphic controllers, relevant requestBuilder is called, (request is sent to client class
     * to build connection and returns the response), return value is gotten from the request builder and there in the
     * controller things get done
     */
    private Socket socket;
    private DataOutputStream dataOutputStream;
    private DataInputStream dataInputStream;
    private static Client clientInstance;

    private Client(String IP, String port) throws Exception {
        socket = new Socket(IP, Integer.parseInt(port));
        dataOutputStream = new DataOutputStream(new BufferedOutputStream(socket.getOutputStream()));
        dataInputStream = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
    }

    public static Client getClient(String IP, String port) throws Exception {
        if (clientInstance == null) {
            clientInstance = new Client(IP, port);
            //System.out.println(client.sendRequest("me/signUp/you"));
        }
        return clientInstance;
    }

    public String sendRequest(String request) {
        try {
            dataOutputStream.writeUTF(request);
            dataOutputStream.flush();
            System.out.println("client wrote " + request);
            String s = dataInputStream.readUTF();
            System.out.println("client read " + s);
            return s;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "failure";
    }

    public ObservableList sendRequestObject(String request) throws ClassNotFoundException {
        try {
            dataOutputStream.writeUTF(request);
            dataOutputStream.flush();
            System.out.println("client wrote " + request);
            //String s = dataInputStream.readUTF();
            ObjectInputStream ois = new ObjectInputStream(dataInputStream);
            System.out.println("client read " + "object");
            return ((ObservableList) ois.readObject());
            //return s;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void closeConnection() {
        try {
            dataOutputStream.writeUTF("0000#disconnect");
            dataOutputStream.flush();
            System.out.println("client wrote 0000#disconnect");
            String s = dataInputStream.readUTF();
            System.out.println("client read " + s);
            socket.close();
            dataInputStream.close();
            dataOutputStream.close();
            //TODO take action for token
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
