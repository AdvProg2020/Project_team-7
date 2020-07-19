package Main.server.serverRequestProcessor;

import Main.server.model.accounts.Account;
import org.apache.commons.lang3.time.DateUtils;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketAddress;
import java.util.ArrayList;
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
    private HashMap<SocketAddress, ArrayList<Date>> connectedClients = new HashMap<>();
    private HashMap<SocketAddress, ArrayList<Date>> loginRequests = new HashMap<>();

    private Server() {
        try {
            serverSocket = new ServerSocket(0);
            System.out.println(serverSocket.getLocalPort());
        } catch (IOException e) {
            e.printStackTrace();
        }
        serverInstance = this;
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
        if (!connectedClients.containsKey(socketAddress)) {
            ArrayList<Date> dates = new ArrayList<>();
            dates.add(new Date());
            connectedClients.put(socketAddress, dates);
        } else {
            connectedClients.get(socketAddress).add(new Date());
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

        public requestHandler(Socket clientSocket) {
            this.clientSocket = clientSocket;
            try {
                this.dataOutputStream = new DataOutputStream(new BufferedOutputStream(clientSocket.getOutputStream()));
                this.dataInputStream = new DataInputStream(new BufferedInputStream(clientSocket.getInputStream()));
            } catch (IOException e) {
                e.printStackTrace();
            }
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

            request = dataInputStream.readUTF();
            addConnectionLog(clientSocket);
            System.out.println("server read " + request);

            splitRequest = request.split("#");
            if (!validateTooManyRequests(clientSocket)) {
                response = "tooManyRequests";
            } else if (splitRequest.length < 2) {
                response = "invalidRequest";
            } else if (splitRequest[1].equals("manager")) {
                response = ManagerRequestProcessor.process(splitRequest);
            } else if (splitRequest[1].equals("buyer")) {
                response = BuyerRequestProcessor.process(splitRequest);
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
            } else if (splitRequest[2].equals("addSpecialFeatures")) {
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
                ObjectOutputStream oos = new ObjectOutputStream(dataOutputStream);
                oos.writeObject(new ArrayList<>(BuyerRequestProcessor.getCartProductsRequestProcessor(splitRequest)));
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
            } else if (splitRequest[1].equals("finalizePayment")) {
                response = BuyerRequestProcessor.finalizePaymentRequestProcessor(splitRequest);
            }

            if (!response.equals("do not write UTF"))
                dataOutputStream.writeUTF(response);
            dataOutputStream.flush();
            System.out.println("server wrote " + response);

            if (!response.equals("disconnected")) {
                handle();
            } else {
                removeToken(splitRequest[0]);
                clientSocket.close();
                dataOutputStream.close();
                dataInputStream.close();
            }
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
        String AlphaNumericString = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
                + "0123456789"
                + "abcdefghijklmnopqrstuvxyz";

        StringBuilder stringBuilder = new StringBuilder(stringLength);

        for (int i = 0; i < stringLength; i++) {

            int index = (int) (AlphaNumericString.length() * Math.random());
            stringBuilder.append(AlphaNumericString.charAt(index));
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
        ArrayList<Date> requestDates = connectedClients.get(socketAddress);
        if (requestDates.size() >= 20) {
            Date date = requestDates.get(requestDates.size() - 20);
            date = DateUtils.addSeconds(date, 10);
            return date.compareTo(new Date()) <= 0;
        }
        return true;
    }

    private boolean validateTooManyLoginRequests(Socket clientSocket) {
        SocketAddress socketAddress = clientSocket.getLocalSocketAddress();
        ArrayList<Date> requestDates = loginRequests.get(socketAddress);
        if (requestDates.size() >= 3) {
            Date date = requestDates.get(requestDates.size() - 3);
            date = DateUtils.addSeconds(date, 5);
            return date.compareTo(new Date()) <= 0;
        }
        return true;
    }

    public HashMap<String, TokenInfo> getTokens() {
        return tokens;
    }
}
