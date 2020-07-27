package Main.client.requestBuilder;

import Main.client.graphicView.scenes.ManagerPanel.ManagerPanelController;

import java.io.*;
import java.net.Socket;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
    private static final String KEY_STRING = "FH.xdjYSEL#TcMZIG4qKO9fW XPNnU23/vVm7i1gbRDesthyBaAr5:op8C6kQu0lz@Jw";
    protected static DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
    private String IP;
    private String port;


    private Client(String IP, String port) throws Exception {
        this.IP = IP;
        this.port = port;
        socket = new Socket(IP, Integer.parseInt(port));
        dataOutputStream = new DataOutputStream(new BufferedOutputStream(socket.getOutputStream()));
        dataInputStream = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
        setKeyMap(KEY_STRING, KEY_MAP);
    }

    private void renewConnections() throws IOException {
        System.out.println("waiting to renew connection");
        socket = new Socket(IP, 9999);
        System.out.println("socket renewed");
        dataOutputStream = new DataOutputStream(new BufferedOutputStream(socket.getOutputStream()));
        dataInputStream = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
    }

    private void setKeyMap(String keyString, HashMap<Character, Character> keyMap) {
        char[] key = keyString.toCharArray();
        char[] alphaNumericString = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789abcdefghijklmnopqrstuvwxyz#/: @.".toCharArray();
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
            System.out.println("original request was: " + request);
            String originalRequest = new String(request);
            String randomKey = getRandomKey();
            if (randomKey.charAt(randomKey.length() - 1) == 'K') {
                sendRequest(originalRequest);
            } else {
                HashMap<Character, Character> keyMap = new HashMap<>();
                setKeyMap(randomKey, keyMap);
                String dateNow = dateFormat.format(new Date());
                request = request.concat("#").concat(dateNow).concat("#").concat(getRandomString(5));
                request = encryptMessage(request, keyMap);
                System.out.println("key : " + randomKey);
                String encryptedKey = encryptMessage(randomKey, KEY_MAP);
                System.out.println("encrypted key : " + encryptedKey);
                request = request.concat("#").concat(encryptedKey);
                System.out.println("request : " + request);
                dataOutputStream.writeUTF(request);
                dataOutputStream.flush();
                System.out.println("client wrote " + request);
                String s = dataInputStream.readUTF();
                if (s.equals("tryAgain")) {
                    sendRequest(originalRequest);
                }
                if (s.startsWith("downloading"))
                    receiveFile(s.split("#")[1]);
                System.out.println("client read " + s);
                return s;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "failure";
    }

    private void receiveFile(String names) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    String name = names.split("&")[0];
                    Socket socket1 = new Socket(IP, 9999);
                    InputStream inputStream = socket1.getInputStream();
                    byte[] mybytearray = new byte[6022386];
                    FileOutputStream fileOutputStream = new FileOutputStream(new File("src/main/java/Main/client/buyersFiles/" + name));
                    BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(fileOutputStream);
                    System.out.println("I AM BUYER I AM READY TO READ THE FILE");
                    int bytesRead = inputStream.read(mybytearray, 0, mybytearray.length);
                    System.out.println("NOW I READ ITS LENGTH");
                    int current = bytesRead;
                    do {
                        bytesRead = inputStream.read(mybytearray, current, (mybytearray.length - current));
                        System.out.println("DO WHILE IS IN PROGRESS");
                        if (bytesRead >= 0)
                            current += bytesRead;
                    } while (bytesRead > -1);
                    System.out.println("FINISHED LOADING FILE");
                    bufferedOutputStream.write(mybytearray, 0, current);
                    bufferedOutputStream.flush();
                    System.out.println("File " + name + " downloaded (" + current + " bytes read)");
                    fileOutputStream.close();
                    bufferedOutputStream.close();
                    socket1.close();
                    System.out.println("everything closed");
                    sendRequest("success#"+name+"#downloaded");
                    System.out.println("i wrote success");
                    ManagerPanelController.alertInfo("File downloaded successfully!");
                } catch (Exception e) {
                    e.printStackTrace();
                    System.err.println("ERROR IN RECEIVING FILE IN CLIENT");
                    ManagerPanelController.alertError("error in downloading file");
                }
            }
        }).start();
    }

    public String sendRequestFile(String request, File file) {
        try {
            String originalRequest = request;
            String randomKey = getRandomKey();
            if (randomKey.charAt(randomKey.length() - 1) == 'K') {
                sendRequest(originalRequest);
            } else {
                HashMap<Character, Character> keyMap = new HashMap<>();
                setKeyMap(randomKey, keyMap);
                String dateNow = dateFormat.format(new Date());
                request = request.concat("#").concat(dateNow).concat("#").concat(getRandomString(5));
                request = encryptMessage(request, keyMap);
                String encryptedKey = encryptMessage(randomKey, KEY_MAP);
                request = request.concat("#").concat(encryptedKey);

                dataOutputStream.writeUTF(request);
                dataOutputStream.flush();
                System.out.println("client wrote " + request);
                String s = dataInputStream.readUTF();
                System.out.println("client read " + s);
                if (s.startsWith("success")) {
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                Socket socket1 = new Socket(IP, 9999);
                                OutputStream outputStream = socket1.getOutputStream();
                                byte[] mybytearray = new byte[(int) file.length()];
                                FileInputStream fileInputStream = new FileInputStream(file);
                                BufferedInputStream bufferedInputStream = new BufferedInputStream(fileInputStream);
                                bufferedInputStream.read(mybytearray, 0, mybytearray.length);
                                System.out.println("Sending " + file.getName() + "(" + mybytearray.length + " bytes)");
                                outputStream.write(mybytearray, 0, mybytearray.length);
                                outputStream.flush();
                                System.out.println("I AM THE SELLER I SENT THE FILE");
                                bufferedInputStream.close();
                                outputStream.close();
                                socket1.close();
                                System.out.println("everything closed");
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }).start();
                    String ss = dataInputStream.readUTF();
                    System.out.println("client read after uploading file: " + s);
                    return ss;
                } else {
                    return "error#error uploading the file";
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "unreachable";
    }

    public List sendRequestObject(String request) throws ClassNotFoundException {
        String originalRequest = request;
        System.out.println("original was: ");
        try {
            String randomKey = getRandomKey();
            if (randomKey.charAt(randomKey.length() - 1) == 'K') {
                sendRequest(originalRequest);
            } else {
                HashMap<Character, Character> keyMap = new HashMap<>();
                setKeyMap(randomKey, keyMap);
                String dateNow = dateFormat.format(new Date());
                request = request.concat("#").concat(dateNow).concat("#").concat(getRandomString(5));
                request = encryptMessage(request, keyMap);
                request = request.concat("#").concat(encryptMessage(randomKey, KEY_MAP));

                dataOutputStream.writeUTF(request);
                dataOutputStream.flush();
                System.out.println("client wrote " + request);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        ObjectInputStream ois = null;
        try {
            ois = new ObjectInputStream(dataInputStream);
            System.out.println("client read " + "object");
            List list = ((List) ois.readObject());
            dataInputStream.readUTF();
            return list;
            //return s;
        } catch (IOException e) {
            e.printStackTrace();
            //return null;
            //sendRequestObject(originalRequest);
        }
        return new ArrayList();
    }

    public void closeConnection() {
        try {
            GeneralRequestBuilder.buildDisconnectionRequest();
            socket.close();
            dataInputStream.close();
            dataOutputStream.close();
            //TODO take action for token
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String encryptMessage(String message, HashMap<Character, Character> keyMap) {
        char[] messageChars = message.toCharArray();
        StringBuilder m = new StringBuilder(message);
        for (int i = 0; i < messageChars.length; i++) {
            char c = messageChars[i];
            try {
                m.setCharAt(i, keyMap.get(c));
            } catch (Exception e) {
            }
        }
        return m.toString();
    }

    private static String getRandomKey() {
        StringBuilder alphaNumericString = new StringBuilder("ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789abcdefghijklmnopqrstuvwxyz#/: @.");

        for (int i = 0; i < 50; i++) {
            int index1 = (int) (alphaNumericString.length() * Math.random());
            int index2 = (int) (alphaNumericString.length() * Math.random());
            char temp = alphaNumericString.charAt(index1);
            alphaNumericString.setCharAt(index1, alphaNumericString.charAt(index2));
            alphaNumericString.setCharAt(index2, temp);
        }

        return alphaNumericString.toString();
    }

    private String getRandomString(int stringLength) {
        String alphaNumericString = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
                + "0123456789"
                + "abcdefghijklmnopqrstuvwxyz";

        StringBuilder stringBuilder = new StringBuilder(stringLength);

        for (int i = 0; i < stringLength; i++) {

            int index = (int) (alphaNumericString.length() * Math.random());
            stringBuilder.append(alphaNumericString.charAt(index));
        }

        return stringBuilder.toString();
    }


}
