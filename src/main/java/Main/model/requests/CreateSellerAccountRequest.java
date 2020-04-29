package Main.model.requests;

import Main.model.accounts.SellerAccount;

public class CreateSellerAccountRequest extends Request {
    private SellerAccount sellerAccount;
    private final String name;

    public CreateSellerAccountRequest(SellerAccount sellerAccount, String name) {
        this.sellerAccount = sellerAccount;
        this.name = name;
    }

    public String showRequest() {
        String show = "Add New Off Request:\n" +
                "Request ID: " + this.requestId + "\n" +
                sellerAccount.viewMe() + "\n";
        return show;
    }

    public void acceptRequest() {
        SellerAccount.addSeller(sellerAccount);
    }

    public void declineRequest() {
    }
}
