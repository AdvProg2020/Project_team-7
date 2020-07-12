package Main.server.serverRequestProcessor;

import Main.server.model.accounts.Account;

import java.util.Date;

public class TokenInfo {
    private Account user;
    private Date startDate;

    public TokenInfo(Account user) {
        this.user = user;
        this.startDate = new Date();
    }
}
