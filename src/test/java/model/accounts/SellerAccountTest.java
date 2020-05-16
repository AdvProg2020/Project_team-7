package model.accounts;

import Main.model.accounts.Account;
import Main.model.accounts.SellerAccount;
import Main.model.exceptions.AccountsException;
import org.junit.Assert;
import org.junit.Test;

public class SellerAccountTest {
    @Test(expected = AccountsException.class)
    public void invalidNameException() throws AccountsException {
        SellerAccount sellerAccount = new SellerAccount("userName", "firs*tName", "las#t Name",
                "sampleEmail@sample.sample", "09001112233", "password123", "companyName"
                ,"this company is great!", 100);
    }

    @Test(expected = AccountsException.class)
    public void duplicateUserNameException() throws AccountsException {
        SellerAccount sellerAccount = new SellerAccount("userName", "firstName", "last Name",
                "sampleEmail@sample.sample", "09001112233", "password123", "companyName",
                "this company is great!", 100);
        SellerAccount.addSeller(sellerAccount);
        SellerAccount managerAccount2 = new SellerAccount("userName", "firstName", "last Name",
                "sampleEmail@sample.sample", "09001112233", "password123", "companyName",
                "this company is great!",100);
    }

    @Test(expected = AccountsException.class)
    public void invalidPhoneNumberException() throws AccountsException {
        SellerAccount sellerAccount = new SellerAccount("userName", "firstName", "las#t Name",
                "sampleEmail@sample.sample", "090011112233", "password123", "companyName",
                "this company is great!",100);
    }

    @Test(expected = AccountsException.class)
    public void invalidPasswordException() throws AccountsException {
        SellerAccount sellerAccount = new SellerAccount("userName", "firstName", "las#t Name",
                "sampleEmail@sample.sample", "09001112233", "pass", "companyName"
                ,"this company is great!", 100);
    }

    @Test
    public void addSellerAndIsThereSellerTest() throws AccountsException {
        SellerAccount sellerAccount = new SellerAccount("username", "firstname", "lastname"
                , "example@exp.exp", "09000000000", "00000000", "companyName"
                ,"this company is great!", 100);
        SellerAccount.addSeller(sellerAccount);

        Assert.assertTrue(sellerAccount.isThereSellerWithUserName("username") && Account.isThereUserWithUserName("username"));
    }

    @Test
    public void viewMeTest() throws AccountsException {
        SellerAccount sellerAccount = new SellerAccount("userName", "firstName", "last Name",
                "sampleEmail@sample.sample", "09001112233", "password123", "companyName"
                ,"this company is great!", 100);

        Assert.assertEquals("Seller :\n\tfirst name : firstName\n\tlast name : last Name\n\tuser name : userName" +
                "\n\tcompany name : companyName\n\tcompany information : this company is great!\n\temail : " +
                "sampleEmail@sample.sample\n\tphone number : 09001112233\n",sellerAccount.viewMe());
    }

    @Test
    public void showSellerList() throws AccountsException {
        SellerAccount sellerAccount = new SellerAccount("userName", "firstName", "last Name",
                "sampleEmail@sample.sample", "09001112233", "password123", "companyName"
                ,"this company is great!", 100);
        SellerAccount.addSeller(sellerAccount);
        SellerAccount sellerAccount2 = new SellerAccount("userName2", "firstName2", "last Name2",
                "sampleEmail@sample.sample2", "09001112234", "password124", "companyName"
                ,"this company is great!", 100);
        SellerAccount.addSeller(sellerAccount2);

        Assert.assertEquals("Sellers :\n\tuser name : userName\tfull name : firstName last Name\n\tuser name : " +
                "userName2\tfull name : firstName2 last Name2\n",SellerAccount.showSellersList());
    }

    @Test
    public void getSellerWithNameTest() throws Exception {
        SellerAccount sellerAccount = new SellerAccount("userName", "firstName", "last Name",
                "sampleEmail@sample.sample", "09001112233", "password123", "companyName"
                ,"this company is great!", 100);
        SellerAccount.addSeller(sellerAccount);

        Assert.assertEquals(sellerAccount,SellerAccount.getSellerWithUserName("userName"));
    }

    @Test
    public void deleteSellerTest() throws AccountsException {
        SellerAccount sellerAccount = new SellerAccount("userName", "firstName", "last Name",
                "sampleEmail@sample.sample", "09001112233", "password123", "companyName"
                ,"this company is great!", 100);
        SellerAccount.addSeller(sellerAccount);
        sellerAccount.deleteSeller();

        Assert.assertFalse(SellerAccount.isThereUserWithUserName("userName") || Account.isThereUserWithUserName("userName"));
    }

    @Test
    public void viewCompanyInformationTest() throws AccountsException {
        SellerAccount sellerAccount = new SellerAccount("userName", "firstName", "last Name",
                "sampleEmail@sample.sample", "09001112233", "password123", "companyName"
                ,"this company is great!", 100);

        Assert.assertEquals("company name : companyName\n\tcompany information : this company is great!\n",sellerAccount.viewCompanyInformation());
    }

    @Test
    public void increaseBalanceByTest() throws AccountsException {
        SellerAccount sellerAccount = new SellerAccount("userName", "firstName", "last Name",
                "sampleEmail@sample.sample", "09001112233", "password123", "companyName"
                ,"this company is great!", 100);

        sellerAccount.increaseBalanceBy(20);
        Assert.assertEquals(120,sellerAccount.getBalance(),0);
    }

    @Test
    public void viewBalanceTest() throws AccountsException {
        SellerAccount sellerAccount = new SellerAccount("userName", "firstName", "last Name",
                "sampleEmail@sample.sample", "09001112233", "password123", "companyName"
                ,"this company is great!", 100);

        Assert.assertEquals("balance : 100.0\n",sellerAccount.viewBalance());
    }

    //TODO : write TestUnit for viewSellersOffs, addOff, showSellersProducts, viewSalesHistory, addLog, getLogWithID

}
