package Main.model.exceptions;

import Main.model.accounts.Account;

public class InvalidInputException extends Exception {

    protected String errorMessage;

    public String getErrorMessage() {
        return errorMessage;
    }


    public static void validateUsernameUniqueness(String userName) throws InvalidInputException {
        if (Account.isThereUserWithUserName(userName)) {
            throw new InvalidInputException.duplicateUserNameException();
        }
    }

    public static void validateUserName(String userName) throws invalidUserNameException {
        if (!userName.matches("[A-Za-z_0-9.\\-]*")) {
            throw new InvalidInputException.invalidUserNameException();
        }
    }

    public static void validateNameTypeInfo(String nameTypeInfoTitle, String nameTypeInfo) throws InvalidInputException {
        if (!nameTypeInfo.matches("[A-Za-z_0-9.\\-][A-Za-z_0-9. \\-]*[A-Za-z_0-9.\\-]")) {
            throw new InvalidInputException.invalidNameTypeInfoException(nameTypeInfoTitle);
        }
    }

    public static void validateEmail(String email) throws InvalidInputException {
        if (!email.matches("([A-Za-z_0-9\\-]+\\.)*[A-Za-z_0-9\\-]+@.*\\..*")) {
            throw new InvalidInputException.invalidEmailException();
        }
    }

    public static void validatePhoneNumber(String phoneNumber) throws InvalidInputException {
        if (!phoneNumber.matches("09[0-9]{9}")) {
            throw new InvalidInputException.invalidPhoneNumberException();
        }
    }

    public static void validatePassWord(String passWord) throws InvalidInputException {
        if (!passWord.matches(".{8,}")) {
            throw new InvalidInputException.invalidPassWordException();
        }
    }


    public static class invalidEmailException extends InvalidInputException {
        public invalidEmailException() {
            this.errorMessage = "Email is invalid !\nEmail must be in format of [username@EmailMessenger.domain] ." +
                    "\nUsername can only contain alphabetical and numeric characters, '_', '-' and single '.' at middle .";
        }
    }

    public static class invalidNameTypeInfoException extends InvalidInputException {
        private String nameTypeInfoTitle;

        public invalidNameTypeInfoException(String nameTypeInfoTitle) {
            this.nameTypeInfoTitle = nameTypeInfoTitle;
            this.errorMessage = "Invalid character ! " + nameTypeInfoTitle + " can only contain English letters, numbers" +
                    ", spaces in middle, '_','.' and '-' .";
        }
    }

    public static class duplicateUserNameException extends InvalidInputException {
        public duplicateUserNameException() {
            this.errorMessage = "This user name already exists ! Try another one .";
        }
    }

    public static class invalidPassWordException extends InvalidInputException {
        public invalidPassWordException() {
            this.errorMessage = "PassWord must be at least 8 valid characters";
        }
    }

    public static class invalidPhoneNumberException extends InvalidInputException {
        public invalidPhoneNumberException() {
            this.errorMessage = "Phone number is invalid. It must be in format of 09XXXXXXXXX .";
        }
    }

    public static class invalidUserNameException extends InvalidInputException {
        public invalidUserNameException() {
            this.errorMessage = "Invalid character ! user name  can only contain English letters, numbers" +
                    ", '_','.' and '-' . make sure that it doesn't contain spaces !";
        }
    }
}



