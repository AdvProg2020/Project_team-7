package Main.bankServer;

import org.apache.commons.lang3.time.DateUtils;

import java.util.Date;

public class BankTokenInfo {
    private String username;
    private String password;
    private Date expirationDate;

    public BankTokenInfo(String username, String password) {
        this.username = username;
        this.password = password;
        expirationDate = new Date();
        this.expirationDate = DateUtils.addMinutes(expirationDate, Bank.TOKEN_EXPIRATION_MINUTES);
    }

    public boolean hasTokenExpired(){
        Date dateNow = new Date();
        return this.expirationDate.compareTo(dateNow) < 0;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}
