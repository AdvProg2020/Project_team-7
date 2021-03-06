package model.accounts;

import Main.server.model.accounts.Account;
import Main.server.model.accounts.BuyerAccount;
import Main.server.model.exceptions.AccountsException;
import org.junit.Assert;
import org.junit.Test;

public class BuyerAccountTest {

    @Test(expected = AccountsException.class)
    public void invalidNameException() throws AccountsException {
        AccountsException.validateNameTypeInfo("name", "name*m");
    }

    @Test(expected = AccountsException.class)
    public void duplicateUserNameException() throws AccountsException {
        BuyerAccount buyerAccount = new BuyerAccount("userName", "firstName", "last Name",
                "sampleEmail@sample.sample", "09001112233", "password123", 100,null,null);
        BuyerAccount.addBuyer(buyerAccount);
        AccountsException.validateUsernameUniqueness("userName");
    }

    @Test(expected = AccountsException.class)
    public void invalidPhoneNumberException() throws AccountsException {
        AccountsException.validatePhoneNumber("09999999");
    }

    @Test(expected = AccountsException.class)
    public void invalidPasswordException() throws AccountsException {
        AccountsException.validatePassWord("fgsd");
    }

    @Test
    public void addBuyerAndIsThereBuyerTest() throws AccountsException {
        BuyerAccount buyerAccount = new BuyerAccount("username", "firstname", "lastname"
                , "example@exp.exp", "09000000000", "00000000", 100,null,null);

        BuyerAccount.addBuyer(buyerAccount);

        Assert.assertTrue(BuyerAccount.isThereBuyerWithUserName("username") && Account.isThereUserWithUserName("username"));
    }

    @Test
    public void viewMeTest() throws AccountsException {
        BuyerAccount buyerAccount = new BuyerAccount("userName", "firstName", "last Name",
                "sampleEmail@sample.sample", "09001112233", "password123", 100,null,null);
        BuyerAccount.addBuyer(buyerAccount);

        Assert.assertEquals("Buyer :\n\tfirst name : firstName\n\tlast name : last Name\n\tuser name : userName" +
                "\n\temail : sampleEmail@sample.sample\n\tphone number : 09001112233\n", buyerAccount.viewMe());
    }

    @Test
    public void showBuyerList() throws AccountsException {
        BuyerAccount buyerAccount = new BuyerAccount("userName", "firstName", "last Name",
                "sampleEmail@sample.sample", "09001112233", "password123", 100,null,null);
        BuyerAccount.addBuyer(buyerAccount);
        BuyerAccount buyerAccount2 = new BuyerAccount("userName2", "firstName2", "last Name2",
                "sampleEmail@sample.sample2", "09001112234", "password124", 100,null,null);
        BuyerAccount.addBuyer(buyerAccount2);

        Assert.assertEquals("Buyers :\n\tuser name : userName\tfull name : firstName last Name\n\tuser name : " +
                "userName2\tfull name : firstName2 last Name2\n", BuyerAccount.showBuyersList());
    }

    @Test
    public void getBuyerWithNameTest() throws Exception {
        BuyerAccount buyerAccount = new BuyerAccount("userName", "firstName", "last Name",
                "sampleEmail@sample.sample", "09001112233", "password123", 100,null,null);
        BuyerAccount.addBuyer(buyerAccount);

        Assert.assertEquals(buyerAccount, BuyerAccount.getBuyerWithUserName("userName"));
    }

    @Test
    public void deleteBuyerTest() throws Exception {
        BuyerAccount buyerAccount = new BuyerAccount("userName", "firstName", "last Name",
                "sampleEmail@sample.sample", "09001112233", "password123", 100,null,null);
        BuyerAccount.addBuyer(buyerAccount);
        buyerAccount.deleteBuyer();

        Assert.assertFalse(BuyerAccount.isThereBuyerWithUserName("userName"));
    }

    @Test
    public void decreaseBalanceByTest() throws Exception {
        BuyerAccount buyerAccount = new BuyerAccount("userName", "firstName", "last Name",
                "sampleEmail@sample.sample", "09001112233", "password123", 100,null,null);

        buyerAccount.decreaseBalanceBy(20);
        Assert.assertEquals(80, buyerAccount.getWalletBalance(), 0);
    }

    //TODO : write test for : addCartsProductsToBoughtProducts, viewOrders, emptyCart, addLog, removeDiscountCode, addDiscountCod, getLogWithId, hasBuyerBoughtProduct

    @Test
    public void viewBalanceTest() throws AccountsException {
        BuyerAccount buyerAccount = new BuyerAccount("userName", "firstName", "last Name",
                "sampleEmail@sample.sample", "09001112233", "password123", 100,null,null);

        Assert.assertEquals("balance : 100.0\n", buyerAccount.viewBalance());
    }
}
