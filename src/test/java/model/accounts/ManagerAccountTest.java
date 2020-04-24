package model.accounts;

import Main.model.accounts.Account;
import Main.model.exceptions.invalidInputException;
import Main.model.accounts.ManagerAccount;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;

public class ManagerAccountTest {

    @Test(expected = invalidInputException.class)
    public void invalidNameException() throws invalidInputException {
        ManagerAccount managerAccount = new ManagerAccount("userName","firs*tName","las#t Name",
                "sampleEmail@sample.sample","09001112233","password123");
    }

    @Test(expected = invalidInputException.class)
    public void duplicateUserNameException() throws invalidInputException {
        ManagerAccount managerAccount = new ManagerAccount("userName","firstName","last Name",
                "sampleEmail@sample.sample","09001112233","password123");
        ManagerAccount.addManager(managerAccount);
        ManagerAccount managerAccount2 = new ManagerAccount("userName","firstName","last Name",
                "sampleEmail@sample.sample","09001112233","password123");
    }

    @Test(expected = invalidInputException.class)
    public void invalidPhoneNumberException() throws invalidInputException {
        ManagerAccount managerAccount = new ManagerAccount("userName","firstName","las#t Name",
                "sampleEmail@sample.sample","090011112233","password123");
    }

    @Test(expected = invalidInputException.class)
    public void invalidPasswordException() throws invalidInputException {
        ManagerAccount managerAccount = new ManagerAccount("userName","firstName","las#t Name",
                "sampleEmail@sample.sample","09001112233","pass");
    }

    @Test
    public void addManagerAndIsThereManagerTest () throws invalidInputException {
        ManagerAccount managerAccount = new ManagerAccount("username","firstname","lastname"
                ,"example@exp.exp","09000000000","00000000");

        ManagerAccount.addManager(managerAccount);

        Assert.assertTrue(ManagerAccount.isThereManagerWithUserName("username")&&Account.isThereUserWithUserName("username"));
    }

    @Test
    public void viewMeTest () throws invalidInputException {
        ManagerAccount managerAccount = new ManagerAccount("userName","firstName","last Name",
                "sampleEmail@sample.sample","09001112233","password123");
        ManagerAccount.addManager(managerAccount);

        Assert.assertEquals(managerAccount.viewMe(),"Manager :\n\tfirst name : firstName\n\tlast name : last Name\n\tuser name : userName" +
                "\n\temail : sampleEmail@sample.sample\n\tphone number : 09001112233\n");
    }

    @Test
    public void showManagerList() throws invalidInputException {
        ManagerAccount managerAccount = new ManagerAccount("userName","firstName","last Name",
                "sampleEmail@sample.sample","09001112233","password123");
        ManagerAccount.addManager(managerAccount);
        ManagerAccount managerAccount2 = new ManagerAccount("userName2","firstName2","last Name2",
                "sampleEmail@sample.sample2","09001112234","password124");
        ManagerAccount.addManager(managerAccount2);

        Assert.assertEquals(ManagerAccount.showManagersList(),"Mangers :\n\tuser name : userName\tfull name : firstName last Name\n\tuser name : userName2\tfull name : firstName2 last Name2\n");
    }

    @Test
    public void getManagerWithNameTest() throws invalidInputException {
        ManagerAccount managerAccount = new ManagerAccount("userName","firstName","last Name",
                "sampleEmail@sample.sample","09001112233","password123");
        ManagerAccount.addManager(managerAccount);

        Assert.assertEquals(ManagerAccount.getManagerWithUserName("userName"),managerAccount);
    }

    @Test
    public void deleteManagerTest() throws invalidInputException {
        ManagerAccount managerAccount = new ManagerAccount("userName","firstName","last Name",
                "sampleEmail@sample.sample","09001112233","password123");
        ManagerAccount.addManager(managerAccount);
        ManagerAccount.deleteManager(managerAccount);

        Assert.assertFalse(ManagerAccount.isThereManagerWithUserName("userName"));
    }
}

