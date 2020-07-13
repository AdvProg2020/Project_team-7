package Main.client.requestBuilder;

import java.io.*;
import java.net.Socket;

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
            System.out.println("i wrote it.");
            return dataInputStream.readUTF();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "failure";
    }

    public void closeConnection(){
        try {
            dataOutputStream.writeUTF("0000/disconnect");
            dataOutputStream.flush();
            dataInputStream.readUTF();
            socket.close();
            dataInputStream.close();
            dataOutputStream.close();
            //TODO take action for token
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
