package Main.model.requests;

import java.util.ArrayList;

public abstract class Request {
    protected String requestId;
    protected static ArrayList<Request> allRequests = new ArrayList<Request>();

    public String showRequest(){
        return null;
    }

    public Request getRequestWithId(String requestId){
        return null;
    }

    public static String showAllRequests(){
        return null;
    }

    public String showRequestPossibleFields(){
        return null;
    }

    public void addRequest(Request request){

    }

    public void acceptRequest(){

    }

    public void declineRequest(){

    }
}
