package model.accounts;

import Main.model.exceptions.invalidInputException;
import Main.model.accounts.ManagerAccount;
import org.junit.Test;

public class ManagerAccountTest {

    @Test(expected = invalidInputException.class)
    public void invalidNameException() throws invalidInputException {
        ManagerAccount managerAccount = new ManagerAccount("userName","firs*tName","las#t Name",
                "sampleEmail@sample.sample","09001112233","password123");
    }

    @Test(expected = invalidInputException.class)
    public void duplicateUserNameException() throws invalidInputException {
        ManagerAccount managerAccount = new ManagerAccount("userName","firstName","las#t Name",
                "sampleEmail@sample.sample","09001112233","password123");
        ManagerAccount managerAccount2 = new ManagerAccount("userName","firstName","las#t Name",
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
}
