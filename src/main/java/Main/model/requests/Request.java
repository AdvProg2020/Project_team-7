package Main.model.requests;

import Main.controller.GeneralController;
import Main.model.IDGenerator;
import Main.model.Product;
import Main.model.sorting.RequestsSort;
import Main.model.sorting.UsersSort;
import com.gilecode.yagson.com.google.gson.stream.JsonReader;

import java.io.*;
import java.util.ArrayList;

import static java.util.Arrays.asList;

public abstract class Request {
    protected String requestId;
    private static StringBuilder lastUsedRequestID = new StringBuilder("@");
    protected static ArrayList<Request> allRequests = new ArrayList<Request>();

    public Request() {
        this.requestId = IDGenerator.getNewID(lastUsedRequestID);
    }

    public abstract String showRequest();

    public static Request getRequestWithId(String requestId) throws Exception {
        for (Request request : allRequests) {
            if (request.requestId.equals(requestId))
                return request;
        }
        throw new Exception("There is no request with given ID !\n");
    }

    public static String showAllRequests() {
        String requests = "";
        if (GeneralController.currentSort.equalsIgnoreCase("type"))
            allRequests.sort(new RequestsSort.requestsSortByType());
        else if (GeneralController.currentSort.equalsIgnoreCase("id"))
            allRequests.sort(new RequestsSort.requestsSortById());
        else if (!GeneralController.currentSort.equals(""))
            return "wrong sort type.";
        if (allRequests.isEmpty())
            return "There is no Requests to show.";
        for (Request request : allRequests) {
            requests = requests.concat(request.showRequest());
        }
        return requests;
    }

    public static void addRequest(Request request) {
        allRequests.add(request);
    }

    public abstract void acceptRequest() throws Exception;

    public abstract void declineRequest();

    public void accept() throws Exception {
        acceptRequest();
        allRequests.remove(this);
    }

    public void decline() {
        declineRequest();
        allRequests.remove(this);
    }

    public String getRequestId() {
        return requestId;
    }

    public String getType() {
        if (this instanceof EditProductRequest)
            return "Edit Product";
        else if (this instanceof EditOffRequest)
            return "Edit Off";
        else if (this instanceof DeleteProductRequest)
            return "Delete Product";
        else if (this instanceof CreateSellerAccountRequest)
            return "Create Seller";
        else if (this instanceof AddProductRequest)
            return "Add Product";
        else if (this instanceof AddOffRequest)
            return "Add Off";
        return null;
    }

    public static String readData() {
        try {
            GeneralController.jsonReader = new JsonReader(new FileReader(new File("src/main/JSON/requests.json")));
            Request[] allReq = GeneralController.yagsonMapper.fromJson(GeneralController.jsonReader, Request[].class);
            allRequests = (allReq == null) ? new ArrayList<>() : new ArrayList<>(asList(allReq));
            return "Read Requests Data Successfully.";
        } catch (FileNotFoundException e) {
            return "Problem loading data from requests.json";
        }
    }

    public static String writeData() {
        try {
            GeneralController.fileWriter = new FileWriter("src/main/JSON/requests.json");
            Request[] allReq = new Request[allRequests.size()];
            allReq = allRequests.toArray(allReq);
            GeneralController.yagsonMapper.toJson(allReq, Product[].class, GeneralController.fileWriter);
            GeneralController.fileWriter.close();
            return "Saved Requests Data Successfully.";
        } catch (IOException e) {
            return "Problem saving requests data.";
        }
    }
}
