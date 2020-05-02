package Main.model.requests;

import java.util.ArrayList;

public abstract class Request {
    protected String requestId;
    protected static ArrayList<Request> allRequests = new ArrayList<Request>();

    public abstract String showRequest();

    public Request getRequestWithId(String requestId){
        for (Request request : allRequests) {
            if (request.requestId.equals(requestId))
                return request;
        }
        return null;//TODO exception for not found request id
    }

    public static String showAllRequests(){
        if (allRequests.isEmpty())
            return "There is no Requests to show.";
        for (Request request : allRequests) {
            return request.showRequest();
        }
        return null;
    }

//    public String showRequestPossibleFields(){
//        return null;
//    }

    public void addRequest(Request request){
        allRequests.add(request);
    }

    public abstract void acceptRequest();

    public abstract void declineRequest();

    public void accept(){
        acceptRequest();
        allRequests.remove(this);
    }

    public void decline(){
        declineRequest();
        allRequests.remove(this);
    }

    public String getRequestId() {
        return requestId;
    }

    public String getType(){
        if(this instanceof EditProductRequest)
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
