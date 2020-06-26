package controllerTest;

import Main.controller.GeneralController;
import Main.model.accounts.Account;
import Main.model.accounts.BuyerAccount;
import Main.model.accounts.ManagerAccount;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;

public class GeneralControllerTest {
    GeneralController generalController = new GeneralController();

    @Test
    public void createAccountTest1() {
        String type = "buyer";
        String username = "username";
        BuyerAccount buyerAccount = new BuyerAccount("username", "firstName",
                "lastName", "email@email.email", "09101111111", "password", 5000.5,null);
        BuyerAccount.addBuyer(buyerAccount);
        Assert.assertEquals("this userName is already taken.", generalController.createAccount(type, username));
    }

    @Test
    public void createAccountTest2() {
        String type = "manager";
        ManagerAccount managerAccount = new ManagerAccount("username", "firstName",
                "lastName", "email@email.email", "09101111111", "password",null);
        ManagerAccount.addManager(managerAccount);
        Assert.assertEquals("you can not create more than one manager account directly.",
                generalController.createAccount(type,"user"));
    }
    @Test
    public void createAccountTest3(){
        String type = "seller";
        Assert.assertEquals("your creating account request was sent to manager successfully.",
                generalController.createAccount(type,"ttd"));
    }
    @Test
    public void createAccountTest4() throws Exception {
        Account.getReservedUserNames().add("user4");
        Assert.assertEquals("this userName is already reserved.",
                generalController.createAccount("buyer","user4"));

    }
    @Test
    public void createAccountTest5(){
        Assert.assertEquals("account Created.",generalController.createAccount("buyer","user5"));
    }
}
