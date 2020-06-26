package model.accounts;

import Main.model.accounts.Account;
import Main.model.exceptions.AccountsException;
import Main.model.accounts.ManagerAccount;
import org.junit.Assert;
import org.junit.Test;

public class ManagerAccountTest {

    @Test
    public void addManagerAndIsThereManagerTest () throws AccountsException {
        ManagerAccount managerAccount = new ManagerAccount("username","firstname","lastname"
                ,"example@exp.exp","09000000000","00000000",null);

        ManagerAccount.addManager(managerAccount);

        Assert.assertTrue(ManagerAccount.isThereManagerWithUserName("username")&&Account.isThereUserWithUserName("username"));
    }

    @Test
    public void viewMeTest () throws AccountsException {
        ManagerAccount managerAccount = new ManagerAccount("userName","firstName","last Name",
                "sampleEmail@sample.sample","09001112233","password123",null);
        ManagerAccount.addManager(managerAccount);

        Assert.assertEquals("Manager :\n\tfirst name : firstName\n\tlast name : last Name\n\tuser name : userName" +
                "\n\temail : sampleEmail@sample.sample\n\tphone number : 09001112233\n",managerAccount.viewMe());
    }

    @Test
    public void showManagerList() throws AccountsException {
        ManagerAccount managerAccount = new ManagerAccount("userName","firstName","last Name",
                "sampleEmail@sample.sample","09001112233","password123",null);
        ManagerAccount.addManager(managerAccount);
        ManagerAccount managerAccount2 = new ManagerAccount("userName2","firstName2","last Name2",
                "sampleEmail@sample.sample2","09001112234","password124",null);
        ManagerAccount.addManager(managerAccount2);

        Assert.assertEquals("Mangers :\n\tuser name : userName\tfull name : firstName last Name\n\tuser name : userName2\tfull name : firstName2 last Name2\n",ManagerAccount.showManagersList());
    }

    @Test
    public void getManagerWithNameTest() throws Exception {
        ManagerAccount managerAccount = new ManagerAccount("userName","firstName","last Name",
                "sampleEmail@sample.sample","09001112233","password123",null);
        ManagerAccount.addManager(managerAccount);

        Assert.assertEquals(managerAccount,ManagerAccount.getManagerWithUserName("userName"));
    }

    @Test
    public void deleteManagerTest() throws AccountsException {
        ManagerAccount managerAccount = new ManagerAccount("userName","firstName","last Name",
                "sampleEmail@sample.sample","09001112233","password123",null);
        ManagerAccount.addManager(managerAccount);
        ManagerAccount.deleteManager(managerAccount);

        Assert.assertFalse(ManagerAccount.isThereManagerWithUserName("userName"));
    }
}

