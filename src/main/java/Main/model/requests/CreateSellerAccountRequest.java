package Main.model.requests;

import Main.model.accounts.Account;
import Main.model.accounts.SellerAccount;

public class CreateSellerAccountRequest extends Request {
    private SellerAccount sellerAccount;
    private final String name;

    public CreateSellerAccountRequest(SellerAccount sellerAccount, String name) {
        this.sellerAccount = sellerAccount;
        this.name = name;
        this.requestId = Integer.toString(Request.allRequests.size()*100+1);
    }

    public String showRequest() {
        String show = "Add New Seller Account Request:\n" +
                "Request ID: " + this.requestId + "\n" +
                sellerAccount.viewMe() + "\n";
        return show;
    }

    public void acceptRequest() {
        SellerAccount.addSeller(sellerAccount);
        Account.getReservedUserNames().remove(sellerAccount.getUserName());
    }

    public void declineRequest() {
        Account.getReservedUserNames().remove(sellerAccount.getUserName());
    }
}
