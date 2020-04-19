package Main.model.requests;

import Main.model.accounts.SellerAccount;

public class CreateSellerAccountRequest extends Request {
    private SellerAccount sellerAccount;
    private final String name;

    public CreateSellerAccountRequest(SellerAccount sellerAccount, String name) {
        this.sellerAccount = sellerAccount;
        this.name = name;
    }
}
