package Main.model.exceptions;

import Main.model.accounts.Account;

public class invalidInputException extends Exception {

    protected String errorMessage;

    public String getErrorMessage() {
        return errorMessage;
    }


    public static void validateUsernameUniqueness(String userName) throws invalidInputException {
        if (Account.isThereUserWithUserName(userName)) {
            throw new invalidInputException.duplicateUserNameException();
        }
    }

    public static void validateNameTypeInfo(String nameTypeInfoTitle, String nameTypeInfo) throws invalidInputException {
        if (!nameTypeInfo.matches("[A-Za-z_0-9.\\-][A-Za-z_0-9. \\-]*[A-Za-z_0-9.\\-]")) {
            throw new invalidInputException.invalidNameTypeInfoException(nameTypeInfoTitle);
        }
    }

    public static void validateEmail(String email) throws invalidInputException {
        if (!email.matches("([A-Za-z_0-9\\-]+\\.)*[A-Za-z_0-9\\-]+@.*\\..*")) {
            throw new invalidInputException.invalidEmailException();
        }
    }

    public static void validatePhoneNumber(String phoneNumber) throws invalidInputException {
        if (!phoneNumber.matches("09[0-9]{9}")) {
            throw new invalidInputException.invalidPhoneNumberException();
        }
    }

    public static void validatePassWord(String passWord) throws invalidInputException {
        if (!passWord.matches(".{8,}")) {
            throw new invalidInputException.invalidPassWordException();
        }
    }


    public static class invalidEmailException extends invalidInputException {
        public invalidEmailException() {
            this.errorMessage = "Email is invalid !\nEmail must be in format of [username@EmailMessenger.domain] ." +
                    "\nUsername can only contain alphabetical and numeric characters, '_', '-' and single '.' at middle .";
        }
    }

    public static class invalidNameTypeInfoException extends invalidInputException {
        private String nameTypeInfoTitle;

        public invalidNameTypeInfoException(String nameTypeInfoTitle) {
            this.nameTypeInfoTitle = nameTypeInfoTitle;
            this.errorMessage = "Invalid character ! " + nameTypeInfoTitle + " can only contain English letters, numbers" +
                    ", spaces in middle, '_','.' and '-' .";
        }
    }

    public static class duplicateUserNameException extends invalidInputException {
        public duplicateUserNameException() {
            this.errorMessage = "This user name already exists ! Try another one .";
        }
    }

    public static class invalidPassWordException extends invalidInputException {
        public invalidPassWordException() {
            this.errorMessage = "PassWord must be at least 8 valid characters";
        }
    }

    public static class invalidPhoneNumberException extends invalidInputException {
        public invalidPhoneNumberException() {
            this.errorMessage = "Phone number is invalid. It must be in format of 09XXXXXXXXX .";
        }
    }
}



