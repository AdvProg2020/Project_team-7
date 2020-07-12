package Main.server.serverRequestProcessor;

import Main.server.model.accounts.Account;

import java.util.ArrayList;
import java.util.Date;

public class TokenInfo {
    private ArrayList<Date> resentRequests = new ArrayList<>();
    private ArrayList<Date> resentLoginRequests = new ArrayList<>();
    private Account user;

    public TokenInfo(Account user) {
        this.user = user;
    }
}
