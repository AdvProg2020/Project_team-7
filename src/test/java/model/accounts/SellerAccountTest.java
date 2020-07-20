package model.accounts;

import Main.server.model.accounts.Account;
import Main.server.model.accounts.SellerAccount;
import Main.server.model.exceptions.AccountsException;
import org.junit.Assert;
import org.junit.Test;

public class SellerAccountTest {
    @Test
    public void addSellerAndIsThereSellerTest() throws AccountsException {
        SellerAccount sellerAccount = new SellerAccount("username", "firstname", "lastname"
                , "example@exp.exp", "09000000000", "00000000", "companyName"
                ,"this company is great!", 100,null);
        SellerAccount.addSeller(sellerAccount);

        Assert.assertTrue(sellerAccount.isThereSellerWithUserName("username") && Account.isThereUserWithUserName("username"));
    }

    @Test
    public void viewMeTest() throws AccountsException {
        SellerAccount sellerAccount = new SellerAccount("userName", "firstName", "last Name",
                "sampleEmail@sample.sample", "09001112233", "password123", "companyName"
                ,"this company is great!", 100,null);

        Assert.assertEquals("Seller :\n\tfirst name : firstName\n\tlast name : last Name\n\tuser name : userName" +
                "\n\tcompany name : companyName\n\tcompany information : this company is great!\n\temail : " +
                "sampleEmail@sample.sample\n\tphone number : 09001112233\n",sellerAccount.viewMe());
    }

    @Test
    public void showSellerList() throws AccountsException {
        SellerAccount sellerAccount = new SellerAccount("userName", "firstName", "last Name",
                "sampleEmail@sample.sample", "09001112233", "password123", "companyName"
                ,"this company is great!", 100,null);
        SellerAccount.addSeller(sellerAccount);
        SellerAccount sellerAccount2 = new SellerAccount("userName2", "firstName2", "last Name2",
                "sampleEmail@sample.sample2", "09001112234", "password124", "companyName"
                ,"this company is great!", 100,null);
        SellerAccount.addSeller(sellerAccount2);

        Assert.assertEquals("Sellers :\n\tuser name : userName\tfull name : firstName last Name\n\tuser name : " +
                "userName2\tfull name : firstName2 last Name2\n",SellerAccount.showSellersList());
    }

    @Test
    public void getSellerWithNameTest() throws Exception {
        SellerAccount sellerAccount = new SellerAccount("userName", "firstName", "last Name",
                "sampleEmail@sample.sample", "09001112233", "password123", "companyName"
                ,"this company is great!", 100,null);
        SellerAccount.addSeller(sellerAccount);

        Assert.assertEquals(sellerAccount,SellerAccount.getSellerWithUserName("userName"));
    }

    @Test
    public void deleteSellerTest() throws AccountsException {
        SellerAccount sellerAccount = new SellerAccount("userName", "firstName", "last Name",
                "sampleEmail@sample.sample", "09001112233", "password123", "companyName"
                ,"this company is great!", 100,null);
        SellerAccount.addSeller(sellerAccount);
        sellerAccount.deleteSeller();

        Assert.assertFalse(SellerAccount.isThereUserWithUserName("userName") || Account.isThereUserWithUserName("userName"));
    }

    @Test
    public void viewCompanyInformationTest() throws AccountsException {
        SellerAccount sellerAccount = new SellerAccount("userName", "firstName", "last Name",
                "sampleEmail@sample.sample", "09001112233", "password123", "companyName"
                ,"this company is great!", 100,null);

        Assert.assertEquals("company name : companyName\n\tcompany information : this company is great!\n",sellerAccount.viewCompanyInformation());
    }

    @Test
    public void increaseBalanceByTest() throws AccountsException {
        SellerAccount sellerAccount = new SellerAccount("userName", "firstName", "last Name",
                "sampleEmail@sample.sample", "09001112233", "password123", "companyName"
                ,"this company is great!", 100,null);

        sellerAccount.increaseBalanceBy(20);
        Assert.assertEquals(120,sellerAccount.getWalletBalance(),0);
    }

    @Test
    public void viewBalanceTest() throws AccountsException {
        SellerAccount sellerAccount = new SellerAccount("userName", "firstName", "last Name",
                "sampleEmail@sample.sample", "09001112233", "password123", "companyName"
                ,"this company is great!", 100,null);

        Assert.assertEquals("balance : 100.0\n",sellerAccount.viewBalance());
    }

    //TODO : write TestUnit for viewSellersOffs, addOff, showSellersProducts, viewSalesHistory, addLog, getLogWithID

}
