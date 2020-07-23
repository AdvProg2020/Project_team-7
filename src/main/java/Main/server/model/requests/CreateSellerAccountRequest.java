package Main.server.model.requests;

import Main.server.model.accounts.Account;
import Main.server.model.accounts.SellerAccount;

public class CreateSellerAccountRequest extends Request {
    private SellerAccount sellerAccount;
    private final String name;

    public CreateSellerAccountRequest(SellerAccount sellerAccount, String name) {
        super();
        this.sellerAccount = sellerAccount;
        this.name = name;
    }

    public String showRequest() {
        return "Add New Seller Account Request:\n" +
                "\tRequest ID: " + this.requestId + "\n" +
                sellerAccount.viewMe() + "\n";
    }

    public void acceptRequest() {
        SellerAccount.addSeller(sellerAccount);
        Account.getReservedUserNames().remove(sellerAccount.getUserName());
    }

    public void declineRequest() {
        allRequests.remove(this);
        Account.getReservedUserNames().remove(sellerAccount.getUserName());
    }

    public SellerAccount getSellerAccount() {
        return sellerAccount;
    }
}
