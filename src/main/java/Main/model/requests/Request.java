package Main.model.requests;

import java.util.ArrayList;

public abstract class Request {
    protected String requestId;
    protected static ArrayList<Request> allRequests = new ArrayList<Request>();

    public abstract String showRequest();

    public static Request getRequestWithId(String requestId) throws Exception {
        for (Request request : allRequests) {
            if (request.requestId.equals(requestId))
                return request;
        }
        throw new Exception("There is no request with given ID !\n");
    }

    public static String showAllRequests() {
        if (allRequests.isEmpty())
            return "There is no Requests to show.";
        for (Request request : allRequests) {
            return request.showRequest();
        }
        return null;
    }

    public static void addRequest(Request request) {
        allRequests.add(request);
    }

    public abstract void acceptRequest() throws Exception;

    public abstract void declineRequest();

    public void accept() {
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
}
