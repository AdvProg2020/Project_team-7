package Main.client.requestBuilder;

import java.io.*;
import java.net.Socket;
import java.util.HashMap;
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
    private final HashMap<Character, Character> KEY_MAP = new HashMap<>();
    private static final String KEY_STRING = "FHxdjYSEL#TcMZIG4qKO9fWXPNnU23vVm7i1gbRDesthyBaAr5op8C6kQu0lzJw";

    private Client(String IP, String port) throws Exception {
        socket = new Socket(IP, Integer.parseInt(port));
        dataOutputStream = new DataOutputStream(new BufferedOutputStream(socket.getOutputStream()));
        dataInputStream = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
        setKeyMap(KEY_STRING,KEY_MAP);
    }

    private void setKeyMap(String keyString, HashMap<Character,Character> keyMap) {
        char[] key = keyString.toCharArray();
        char[] alphaNumericString = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789abcdefghijklmnopqrstuvwxyz#".toCharArray();
        for (int i = 0; i < alphaNumericString.length; i++) {
            char c = alphaNumericString[i];
            keyMap.put(c, key[i]);
        }
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
            String randomKey = getRandomKey();
            System.out.println(randomKey);
            System.out.println(encryptMessage(randomKey,KEY_MAP));
            HashMap<Character,Character> keyMap = new HashMap<>();
            setKeyMap(randomKey,keyMap);
            request = encryptMessage(request,keyMap);
            request = request .concat("#").concat(encryptMessage(randomKey,KEY_MAP));
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

    public List sendRequestObject(String request) throws ClassNotFoundException {
        try {
            dataOutputStream.writeUTF(request);
            dataOutputStream.flush();
            System.out.println("client wrote " + request);
            //String s = dataInputStream.readUTF();
            ObjectInputStream ois = new ObjectInputStream(dataInputStream);
            System.out.println("client read " + "object");
            return ((List) ois.readObject());
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

    private String encryptMessage(String message, HashMap<Character,Character> keyMap) {
        char[] messageChars = message.toCharArray();
        StringBuilder m = new StringBuilder(message);
        for (int i = 0; i < messageChars.length; i++) {
            char c = messageChars[i];
            m.setCharAt(i,keyMap.get(c));
        }
        return m.toString();
    }

    private static String getRandomKey() {
        StringBuilder alphaNumericString = new StringBuilder("ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789abcdefghijklmnopqrstuvwxyz#");

        for (int i = 0; i < 50; i++) {
            int index1 = (int) (alphaNumericString.length() * Math.random());
            int index2 = (int) (alphaNumericString.length() * Math.random());
            char temp = alphaNumericString.charAt(index1);
            alphaNumericString.setCharAt(index1, alphaNumericString.charAt(index2));
            alphaNumericString.setCharAt(index2, temp);
        }

        return alphaNumericString.toString();
    }
}
