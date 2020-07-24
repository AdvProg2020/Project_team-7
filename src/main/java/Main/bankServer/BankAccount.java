package Main.bankServer;

import java.util.ArrayList;

public class BankAccount {

    private String firstName;
    private String lastName;
    private String username;
    private String password;
    private String accountId;
    private double balance;
    private ArrayList<Receipt> transactionsToThisAccount = new ArrayList<>();
    private ArrayList<Receipt> transactionsFromThisAccount = new ArrayList<>();

    public BankAccount(String firstName, String lastName, String username, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.password = password;
        this.accountId = generateAccountId();
        this.balance = 1000000000;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public String generateAccountId() {
        if (Bank.getAllAccounts().isEmpty())
            return "100";
        else {
            int lastUsedId = Integer.parseInt(Bank.getAllAccounts().get(Bank.getAllAccounts().size() - 1).getAccountId());
            return Integer.toString(lastUsedId + 1);
        }
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getAccountId() {
        return accountId;
    }

    public double getBalance() {
        return balance;
    }

    public ArrayList<Receipt> getTransactionsFromThisAccount() {
        return transactionsFromThisAccount;
    }

    public ArrayList<Receipt> getTransactionsToThisAccount() {
        return transactionsToThisAccount;
    }
}
