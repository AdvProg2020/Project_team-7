package Main.server.serverRequestProcessor;

import Main.server.controller.GeneralController;
import Main.server.model.accounts.Account;
import Main.server.model.accounts.BuyerAccount;
import org.apache.commons.lang3.time.DateUtils;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketAddress;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;

public class Server {
    /**
     * check what the request starts with, guid to relevant request processor
     */
    private HashMap<String, TokenInfo> tokens = new HashMap<>();
    public static int TOKEN_EXPIRATION_MINUTES = 15;
    private ServerSocket serverSocket;
    private static Server serverInstance;
    private HashMap<SocketAddress, ArrayList<Date>> requests = new HashMap<>();
    private HashMap<SocketAddress, ArrayList<Date>> loginRequests = new HashMap<>();
    private final HashMap<Character, Character> KEY_MAP = new HashMap<>();
    private static final String KEY_STRING = "FH.xdjYSEL#TcMZIG4qKO9fW XPNnU23/vVm7i1gbRDesthyBaAr5:op8C6kQu0lz@Jw";

    private static String inputDateFormat = "yyyy/MM/dd HH:mm:ss";
    private static SimpleDateFormat simpleDateFormat = new SimpleDateFormat(inputDateFormat);

    private Server() {
        try {
            setKeyMap(KEY_STRING, KEY_MAP);
            serverSocket = new ServerSocket(0);
            System.out.println(serverSocket.getLocalPort());
        } catch (IOException e) {
            e.printStackTrace();
        }
        serverInstance = this;
    }

    private void setKeyMap(String keyString, HashMap<Character, Character> keyMap) {
        char[] key = keyString.toCharArray();
        char[] alphaNumericString = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789abcdefghijklmnopqrstuvwxyz#/: @.".toCharArray();
        for (int i = 0; i < key.length; i++) {
            char c = key[i];
            keyMap.put(c, alphaNumericString[i]);
        }
    }

    public static Server getServer() {
        if (serverInstance == null) {
            new Server();
        }
        return serverInstance;
    }

    public void start() {
        new Thread(() -> {
            while (true) {
                try {
                    Socket clientSocket = serverSocket.accept();
                    addConnectionLog(clientSocket);
                    new requestHandler(clientSocket).start();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private void addConnectionLog(Socket clientSocket) {
        SocketAddress socketAddress = clientSocket.getLocalSocketAddress();
        if (!requests.containsKey(socketAddress)) {
            ArrayList<Date> dates = new ArrayList<>();
            dates.add(new Date());
            requests.put(socketAddress, dates);
        } else {
            requests.get(socketAddress).add(new Date());
        }
    }

    private void addLoginRequestLog(Socket clientSocket) {
        SocketAddress socketAddress = clientSocket.getLocalSocketAddress();
        if (!loginRequests.containsKey(socketAddress)) {
            ArrayList<Date> dates = new ArrayList<>();
            dates.add(new Date());
            loginRequests.put(socketAddress, dates);
        } else {
            loginRequests.get(socketAddress).add(new Date());
        }
    }

    private class requestHandler extends Thread {
        private Socket clientSocket;
        private DataOutputStream dataOutputStream;
        private DataInputStream dataInputStream;
        private HashMap<String, ArrayList<String>> clientRequestsLog = new HashMap<>();
        private Date loginAllowedDate;
        private Date requestAllowedDate;

        public requestHandler(Socket clientSocket) {
            this.clientSocket = clientSocket;
            try {
                this.dataOutputStream = new DataOutputStream(new BufferedOutputStream(clientSocket.getOutputStream()));
                this.dataInputStream = new DataInputStream(new BufferedInputStream(clientSocket.getInputStream()));
            } catch (IOException e) {
                e.printStackTrace();
            }
            loginAllowedDate = new Date();
            requestAllowedDate = new Date();
        }

        @Override
        public void run() {
            try {
                handle();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        public void handle() throws Exception {
            String request = null;
            String response = null;
            String[] splitRequest = new String[0];
            try {
                request = dataInputStream.readUTF();
            } catch (Exception e) {
                clientSocket = serverSocket.accept();
                dataOutputStream = new DataOutputStream(new BufferedOutputStream(clientSocket.getOutputStream()));
                dataInputStream = new DataInputStream(new BufferedInputStream(clientSocket.getInputStream()));
                try {
                    request = dataInputStream.readUTF();
                } catch (Exception d) {
                    System.err.println(d.getMessage());
                    d.printStackTrace();
                }
            }
            if (!request.contains("timer")) {
                addConnectionLog(clientSocket);
            }

            splitRequest = request.split("#");
            String encryptedKey = splitRequest[splitRequest.length - 2].concat("#").concat(splitRequest[splitRequest.length - 1]);
            String decryptionKey = decryptMessage(encryptedKey, KEY_MAP);
            request = request.substring(0, request.length() - decryptionKey.length());
            System.out.println("encrypted key : " + encryptedKey);
            System.out.println("decryptionKey : " + decryptionKey);
            System.out.println("request :" + request);
            HashMap<Character, Character> keyMap = new HashMap<>();
            setKeyMap(decryptionKey, keyMap);
            request = decryptMessage(request, keyMap);
            System.out.println("server read " + request);

            splitRequest = request.split("#");

            //if(false){
            if (isReplayAttack(splitRequest)) {
                response = "tryAgain";
            } else if (!validateTooManyRequests(clientSocket)) {
                response = "tooManyRequests";
            } else if (splitRequest.length < 2) {
                response = "invalidRequest";
            } else if (splitRequest[1].equals("manager")) {
                response = ManagerRequestProcessor.process(splitRequest);
            } else if (splitRequest[1].equals("buyer")) {
                response = BuyerRequestProcessor.process(splitRequest);
            } else if (splitRequest[1].equals("seller")) {
                response = SellerRequestProcessor.process(splitRequest);
            } else if (splitRequest[1].equals("logout")) {
                response = logout(splitRequest, dataInputStream);
            } else if (splitRequest[1].equals("login")) {
                response = GeneralRequestProcessor.loginRequestProcessor(splitRequest);
                if (!response.startsWith("success") && !validateTooManyLoginRequests(clientSocket)) {
                    response = "tooManyRequests";
                }
            } else if (splitRequest[1].equals("signUp")) {
                response = GeneralRequestProcessor.signUpRequestProcessor(splitRequest);
            } else if (splitRequest[1].equalsIgnoreCase("signUpSeller")) {
                response = GeneralRequestProcessor.signUpSellerRequestProcessor(splitRequest);
            } else if (splitRequest[1].equalsIgnoreCase("signUpBuyer")) {
                response = GeneralRequestProcessor.signUpBuyerRequestProcessor(splitRequest);
            } else if (splitRequest[1].equalsIgnoreCase("signUpManager")) {
                response = GeneralRequestProcessor.signUpManagerRequestProcessor(splitRequest);
            } else if (splitRequest[1].equalsIgnoreCase("signUpSupporter")) {
                response = GeneralRequestProcessor.signUpSupporterRequestProcessor(splitRequest);
            } else if (splitRequest[1].equalsIgnoreCase("data")) {
                response = DataRequestProcessor.process(splitRequest);
            } else if (splitRequest[1].equals("disconnect")) {
                response = "disconnected";
            } else if (splitRequest[1].equals("buyerBalance")) {
                response = BuyerRequestProcessor.initializeBuyerPanelRequestProcessor(splitRequest);
            } else if (splitRequest[1].equals("buyerPersonalInfo")) {
                response = BuyerRequestProcessor.buyerPersonalInfoRequestProcessor(splitRequest);
            } else if (splitRequest[1].equals("managerPersonalInfo")) {
                response = ManagerRequestProcessor.managerPersonalInfoRequestProcessor(splitRequest);
            } else if (splitRequest[1].equals("editManagerPersonalInfo")) {
                response = ManagerRequestProcessor.editManagerPersonalInformationRequestProcessor(splitRequest);
            } else if (splitRequest[1].equals("initializeManageRequests")) {
                response = ManagerRequestProcessor.initializeManageRequestsRequestProcessor();
            } else if (splitRequest[1].equals("showRequestWithId")) {
                response = ManagerRequestProcessor.showRequestWithIdRequestProcessor(splitRequest);
            } else if (splitRequest[1].equals("acceptRequestWithId")) {
                response = ManagerRequestProcessor.acceptRequestWithIdRequestProcessor(splitRequest);
            } else if (splitRequest[1].equals("declineRequestWithId")) {
                response = ManagerRequestProcessor.declineRequestWithIdRequestProcessor(splitRequest);
            } else if (splitRequest[1].equals("initializeManageProducts")) {
                response = ManagerRequestProcessor.initializeManageProductsRequestProcessor();
            } else if (splitRequest[1].equals("showProductDigestWithId")) {
                response = ManagerRequestProcessor.showProductDigestWithIdRequestProcessor(splitRequest);
            } else if (splitRequest[1].equals("deleteProductWithId")) {
                response = ManagerRequestProcessor.deleteProductWithIdRequestProcessor(splitRequest);
            } else if (splitRequest[1].equals("initializeManageUsers")) {
                response = ManagerRequestProcessor.initializeManageUsersRequestProcessor();
            } else if (splitRequest[1].equals("userViewMeWithUserName")) {
                response = ManagerRequestProcessor.userViewMeWithUserNameRequestProcessor(splitRequest);
            } else if (splitRequest[1].equals("getMyUserName")) {
                response = ManagerRequestProcessor.getMyUserNameRequestProcessor(splitRequest);
            } else if (splitRequest[1].equals("deleteUserWithUserName")) {
                response = ManagerRequestProcessor.deleteUserWithUserNameRequestProcessor(splitRequest);
            } else if (splitRequest[1].equals("initializeManageCategories")) {
                response = ManagerRequestProcessor.initializeManageCategoriesRequestProcessor();
            } else if (splitRequest[1].equals("showCategoryInformation")) {
                response = ManagerRequestProcessor.showCategoryInformationRequestProcessor(splitRequest);
            } else if (splitRequest[1].equals("removeCategoryWithName")) {
                response = ManagerRequestProcessor.removeCategoryWithNameRequestProcessor(splitRequest);
            } else if (splitRequest[1].equals("editCategory")) {
                response = ManagerRequestProcessor.editCategoryRequestProcessor(splitRequest);
            } else if (splitRequest[1].equals("createCategory")) {
                response = ManagerRequestProcessor.createCategoryRequestProcessor(splitRequest);
            } else if (splitRequest[1].equals("initializeManageDiscounts")) {
                response = ManagerRequestProcessor.initializeManageDiscountsRequestProcessor();
            } else if (splitRequest[1].equals("viewDiscountAsManager")) {
                response = ManagerRequestProcessor.viewDiscountAsManagerRequestProcessor(splitRequest);
            } else if (splitRequest[1].equals("removeDiscountCode")) {
                response = ManagerRequestProcessor.removeDiscountCodeRequestProcessor(splitRequest);
            } else if (splitRequest[1].equals("getDiscountData")) {
                response = ManagerRequestProcessor.getDiscountDataRequestProcessor(splitRequest);
            } else if (splitRequest[1].equals("editDiscount")) {
                response = ManagerRequestProcessor.editDiscountRequestProcessor(splitRequest);
            } else if (splitRequest[1].equals("createDiscount")) {
                response = ManagerRequestProcessor.createDiscountRequestProcessor(splitRequest);
            } else if (splitRequest[1].equals("editBuyerPersonalInformation")) {
                response = BuyerRequestProcessor.editBuyerPersonalInformationRequestProcessor(splitRequest);
            } else if (splitRequest[1].equals("initializeMyOrders")) {
                response = BuyerRequestProcessor.initializeMyOrdersRequestProcessor(splitRequest);
            } else if (splitRequest[1].equals("getBuyLogInfo")) {
                response = BuyerRequestProcessor.getBuyLogInfoRequestProcessor(splitRequest);
            } else if (splitRequest[1].equals("initializeBuyerDiscounts")) {
                response = BuyerRequestProcessor.initializeBuyerDiscountsRequestProcessor(splitRequest);
            } else if (splitRequest[1].equals("showDiscountInfoAsBuyer")) {
                response = BuyerRequestProcessor.showDiscountInfoAsBuyerRequestProcessor(splitRequest);
            } else if (splitRequest[1].equals("addComment")) {
                response = SellerRequestProcessor.buildCommentResponse(splitRequest);
            } else if (splitRequest[1].equals("getSellerProductsList")) {
                response = SellerRequestProcessor.getSellerProductsList(splitRequest[0]);
            } else if (splitRequest[1].equals("addOff")) {
                response = SellerRequestProcessor.buildAddOffResponse(splitRequest);
            } else if (splitRequest[1].equals("addProduct")) {
                response = SellerRequestProcessor.buildAddProductResponse(splitRequest);
            } else if (splitRequest[1].equals("addSpecialFeatures")) {
                response = SellerRequestProcessor.buildAddSpecialFeaturesResponse(splitRequest);
            } else if (splitRequest[1].equals("getProductForProductEditPage")) {
                response = SellerRequestProcessor.getProductForProductEditPage(splitRequest);
            } else if (splitRequest[1].equals("editProduct")) {
                response = SellerRequestProcessor.buildEditProductResponse(splitRequest);
            } else if (splitRequest[1].equals("editSellerPersonalInformation")) {
                response = SellerRequestProcessor.buildEditPersonalInformationResponse(splitRequest);
            } else if (splitRequest[1].equals("editOff")) {
                response = SellerRequestProcessor.buildEditOffResponse(splitRequest);
            } else if (splitRequest[1].equals("getSellLogList")) {
                response = SellerRequestProcessor.getSellLogList(splitRequest);
            } else if (splitRequest[1].equals("getLogDetails")) {
                response = SellerRequestProcessor.getLogDetails(splitRequest);
            } else if (splitRequest[1].equals("getSellerOffList")) {
                response = SellerRequestProcessor.getSellerOffList(splitRequest);
            } else if (splitRequest[1].equals("getOffDetails")) {
                response = SellerRequestProcessor.getOffDetails(splitRequest);
            } else if (splitRequest[1].equals("getSellerPersonalInformation")) {
                response = SellerRequestProcessor.getSellerPersonalInformation(splitRequest);
            } else if (splitRequest[1].equals("getCompanyInformation")) {
                response = SellerRequestProcessor.getCompanyInformation(splitRequest);
            } else if (splitRequest[1].equals("getSellerBalance")) {
                response = SellerRequestProcessor.getSellerBalance(splitRequest);
            } else if (splitRequest[1].equals("getSellerCategories")) {
                response = SellerRequestProcessor.getSellerCategories();
            } else if (splitRequest[1].equals("getAllProductDataForSellerProductPage")) {
                response = SellerRequestProcessor.getAllProductDataForSellerProductPage(splitRequest);
            } else if (splitRequest[1].equals("removeProduct")) {
                response = SellerRequestProcessor.buildRemoveProductResponse(splitRequest);
            } else if (splitRequest[1].equals("getAllDataForProductPage")) {
                response = GeneralRequestProcessor.getAllDataForProductPage(splitRequest);
            } else if (splitRequest[1].equals("selectSeller")) {
                response = GeneralRequestProcessor.selectSeller(splitRequest);
            } else if (splitRequest[1].equals("rateProductPermission")) {
                response = GeneralRequestProcessor.buildRateProductPermissionResponse(splitRequest);
            } else if (splitRequest[1].equals("rateProduct")) {
                response = GeneralRequestProcessor.buildRateProductResponse(splitRequest);
            } else if (splitRequest[1].equals("initializeCartAndPrice")) {
                response = BuyerRequestProcessor.initializeCartAndPriceRequestProcessor(splitRequest);
            } else if (splitRequest[1].equals("compareProduct")) {
                response = GeneralRequestProcessor.buildCompareProductResponse(splitRequest);
            } else if (splitRequest[1].equals("getCartProducts")) {
                response = "do not write UTF";
                System.out.println("response = do not");
                ObjectOutputStream oos = new ObjectOutputStream(dataOutputStream);
                System.out.println("oos created");
                oos.writeObject(new ArrayList<>(BuyerRequestProcessor.getCartProductsRequestProcessor(splitRequest)));
                System.out.println("oos wrote");
            } else if (splitRequest[1].equals("increaseCartProduct")) {
                response = BuyerRequestProcessor.buildIncreaseCartProductResponse(splitRequest);
            } else if (splitRequest[1].equals("decreaseCartProduct")) {
                response = BuyerRequestProcessor.buildDecreaseCartProductResponse(splitRequest);
            } else if (splitRequest[1].equals("setReceiverInformation")) {
                response = BuyerRequestProcessor.setReceiverInformationRequestProcessor(splitRequest);
            } else if (splitRequest[1].equals("setPurchaseDiscount")) {
                response = BuyerRequestProcessor.setPurchaseDiscountRequestProcessor(splitRequest);
            } else if (splitRequest[1].equals("showPurchaseInfo")) {
                response = BuyerRequestProcessor.showPurchaseInfoRequestProcessor(splitRequest);
            } else if (splitRequest[1].equals("finalizeBankPayment")) {
                response = BuyerRequestProcessor.finalizeBankPaymentRequestProcessor(splitRequest);
            } else if (splitRequest[1].equals("finalizeWalletPayment")) {
                response = BuyerRequestProcessor.finalizeWalletPaymentRequestProcessor(splitRequest);
            } else if (splitRequest[1].equals("sendMessage")) {
                response = GeneralRequestProcessor.sendMessage(splitRequest);
            } else if (splitRequest[1].equals("financeSetup")) {
                response = GeneralRequestProcessor.setUpFinanceRequestProcessor(splitRequest);
            } else if (splitRequest[1].equals("initializeHelpCenter")) {
                response = BuyerRequestProcessor.initializeHelpCenterRequestProcessor(splitRequest);
            } else if (splitRequest[1].equals("openChatWith")) {
                response = GeneralRequestProcessor.openChatWithRequestProcessor(splitRequest);
            } else if (splitRequest[1].equals("initializeSupporterPanel")) {
                response = GeneralRequestProcessor.initializeSupporterPanelRequestProcessor(splitRequest);
            } else if (splitRequest[1].equals("setChatMessages")) {
                response = GeneralRequestProcessor.setChatMessagesRequestBuilder(splitRequest);
            } else if (splitRequest[1].equals("saveChatMessages")) {
                response = GeneralRequestProcessor.saveChatMessagesRequestBuilder(splitRequest);
            } else if (splitRequest[1].equals("addFileProduct")) {
                response = SellerRequestProcessor.buildAddProductResponse(splitRequest);
                receiveFile(splitRequest[2]);
            } else if (splitRequest[1].equals("chargeWallet")) {
                response = GeneralRequestProcessor.chargeWalletRequestProcessor(splitRequest);
            } else if (splitRequest[1].equals("withdrawFromWallet")) {
                response = GeneralRequestProcessor.withdrawFromWalletRequestProcessor(splitRequest);
            } else if (splitRequest[1].equals("downloadFiles")) {
                response = BuyerRequestProcessor.downloadFilesRequestProcessor(splitRequest);
                sendFiles(splitRequest);
            } else {
                response = "invalidRequest";
            }
            if (response.equals("do not write UTF")) {
                dataOutputStream.flush();
                System.out.println("oos flushed");
                System.out.println("server wrote " + response);
            }

            if (!response.equals("do not write UTF") && !response.equals("invalidRequest")) {
                dataOutputStream.writeUTF(response);
                dataOutputStream.flush();
                System.out.println("server wrote " + response);

                if (!response.equals("disconnected")) {
                    handle();
                } else {
                    System.out.println(GeneralController.writeDataAndGetObjectStringRecords());
                    removeToken(splitRequest[0]);
                    clientSocket.close();
                    dataOutputStream.close();
                    dataInputStream.close();
                }
            }
        }

        private void sendFiles(String[] splitRequest) {
            BuyerAccount buyerAccount = ((BuyerAccount) Server.getServer().getTokenInfo(splitRequest[0]).getUser());
            ArrayList<String> fileNames = new ArrayList<>(Arrays.asList(splitRequest[2].split("&")));
            int howManyFiles = fileNames.size();
            for (String fileName : fileNames) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            String name = fileName;
                            ServerSocket serverSocket1 = new ServerSocket(9999);
                            System.out.println("new server socket created");
                            Socket socket1 = serverSocket1.accept();
                            OutputStream outputStream = socket1.getOutputStream();
                            File file = new File("src/main/java/Main/server/fileResources/"+name);
                            byte[] mybytearray = new byte[(int) file.length()];
                            FileInputStream fileInputStream = new FileInputStream(file);
                            BufferedInputStream bufferedInputStream = new BufferedInputStream(fileInputStream);
                            bufferedInputStream.read(mybytearray, 0, mybytearray.length);
                            System.out.println("Sending " + file.getName() + "(" + mybytearray.length + " bytes)");
                            outputStream.write(mybytearray, 0, mybytearray.length);
                            outputStream.flush();
                            System.out.println("I AM THE SERVER I SENT THE FILE TO BUYER");
                            bufferedInputStream.close();
                            outputStream.close();
                            socket1.close();
                            System.out.println("everything closed");
                            dataOutputStream.writeUTF("success#file " + name + "uploaded to client");
                            dataOutputStream.flush();
                            System.out.println("i wrote success");
                        } catch (Exception e) {
                            e.printStackTrace();
                            System.err.println("ERROR IN RECEIVING FILE IN SERVER");
                        }
                    }
                }).start();
            }
        }

        private void receiveFile(String fileName) throws IOException {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        String name = fileName.split("___")[0];
                        ServerSocket serverSocket1 = new ServerSocket(9999);
                        System.out.println("new server socket created");
                        Socket socket1 = serverSocket1.accept();
                        InputStream inputStream = socket1.getInputStream();
                        byte[] mybytearray = new byte[6022386];
                        FileOutputStream fileOutputStream = new FileOutputStream("src/main/java/Main/server/fileResources/" + name);
                        BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(fileOutputStream);
                        System.out.println("I AM SERVER I AM READY TO READ THE FILE");
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
                        serverSocket1.close();
                        System.out.println("everything closed");
                        dataOutputStream.writeUTF("success#file " + name + "uploaded to server");
                        dataOutputStream.flush();
                        System.out.println("i wrote success");
                    } catch (Exception e) {
                        e.printStackTrace();
                        System.err.println("ERROR IN RECEIVING FILE IN SERVER");
                    }
                }
            }).start();
        }

        private boolean isReplayAttack(String[] splitRequest) {
            Date requestDate = null;
            try {
                requestDate = simpleDateFormat.parse(splitRequest[splitRequest.length - 2]);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            if (DateUtils.addMinutes(requestDate, 1).compareTo(new Date()) < 0) {
                return true;
            }
            String requestStarting = splitRequest[1].concat("#").concat((splitRequest.length > 2 ? splitRequest[2] : ""));
            if (clientRequestsLog.containsKey(requestStarting)) {
                if (clientRequestsLog.get(requestStarting).contains(splitRequest[splitRequest.length - 1])) {
                    return true;
                }
            } else {
                ArrayList<String> arrayList = new ArrayList<>();
                arrayList.add(splitRequest[splitRequest.length - 1]);
                clientRequestsLog.put(requestStarting, arrayList);
            }
            return false;
        }

        private boolean validateTooManyLoginRequests(Socket clientSocket) {
            if (loginAllowedDate.compareTo(new Date()) > 0) {
                return false;
            }
            SocketAddress socketAddress = clientSocket.getLocalSocketAddress();
            if (!loginRequests.containsKey(socketAddress)) {
                ArrayList<Date> dates = new ArrayList<>();
                dates.add(new Date());
                loginRequests.put(socketAddress, dates);
                return true;
            }
            ArrayList<Date> requestDates = loginRequests.get(socketAddress);
            if (requestDates.size() >= 3) {
                Date date = requestDates.get(requestDates.size() - 3);
                date = DateUtils.addSeconds(date, 5);
                if (date.compareTo(new Date()) > 0) {
                    loginAllowedDate = DateUtils.addSeconds(new Date(), 10);
                    requestDates.clear();
                    return false;
                } else {
                    return true;
                }
            }
            return true;
        }

        private boolean validateTooManyRequests(Socket clientSocket) {
            if (requestAllowedDate.compareTo(new Date()) > 0) {
                return false;
            }
            SocketAddress socketAddress = clientSocket.getLocalSocketAddress();
            ArrayList<Date> requestDates = requests.get(socketAddress);
            if (requestDates.size() >= 20) {
                Date date = requestDates.get(requestDates.size() - 20);
                date = DateUtils.addSeconds(date, 10);
                if (date.compareTo(new Date()) > 0) {
                    requestAllowedDate = DateUtils.addSeconds(new Date(), 20);
                    requestDates.clear();
                    return false;
                } else {
                    return true;
                }
            }
            return true;
        }
    }

    public TokenInfo getTokenInfo(String token) {
        for (String tokenString : tokens.keySet()) {
            if (tokenString.equals(token)) {
                return tokens.get(tokenString);
            }
        }
        return null;
    }

    public void removeToken(String token) {
        tokens.remove(token);
    }

    public String addToken(Account account) {
        String token = getRandomString(5);
        TokenInfo tokenInfo = new TokenInfo(account);
        tokens.put(token, tokenInfo);
        return token;
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

    public boolean validateToken(String token, Class<?> classType) {
        TokenInfo tokenInfo = tokens.get(token);
        if (tokenInfo == null)
            return false;
        if (tokenInfo.hasTokenExpired()) {
            removeToken(token);
            return false;
        }
        return tokenInfo.getUser().getClass() == classType || classType == Account.class;
    }

    private String logout(String[] splitRequest, DataInputStream dataInputStream) {
        tokens.remove(splitRequest[0]);
        return "success";
    }

    private boolean validateTooManyRequests(Socket clientSocket) {
        SocketAddress socketAddress = clientSocket.getLocalSocketAddress();
        ArrayList<Date> requestDates = requests.get(socketAddress);
        if (requestDates.size() >= 20) {
            Date date = requestDates.get(requestDates.size() - 20);
            date = DateUtils.addSeconds(date, 10);
            return date.compareTo(new Date()) <= 0;
        }
        return true;
    }

    public HashMap<String, TokenInfo> getTokens() {
        return tokens;
    }

    private String decryptMessage(String message, HashMap<Character, Character> keyMap) {
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
}
