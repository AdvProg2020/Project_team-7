package Main.server.serverRequestProcessor;

import Main.server.model.accounts.Account;
import org.apache.commons.lang3.time.DateUtils;

import java.util.Date;

public class TokenInfo {
    private Account user;
    private Date expirationDate;

    public TokenInfo(Account user) {
        this.user = user;
        expirationDate = new Date();
        this.expirationDate = DateUtils.addMinutes(expirationDate,Server.TOKEN_EXPIRATION_MINUTES);
    }

    public Account getUser() {
        return user;
    }

    public boolean hasTokenExpired(){
        Date dateNow = new Date();
        if(this.expirationDate.compareTo(dateNow)<0){
            return true;
        }

        return false;
    }
}
