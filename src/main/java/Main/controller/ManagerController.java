package Main.controller;

import Main.model.Category;
import Main.model.Product;
import Main.model.accounts.Account;
import Main.model.accounts.BuyerAccount;
import Main.model.accounts.ManagerAccount;
import Main.model.accounts.SellerAccount;
import Main.model.discountAndOffTypeService.DiscountCode;
import Main.model.exceptions.DiscountAndOffTypeServiceException;
import Main.model.requests.EditCategory;
import Main.model.requests.EditDiscountCode;
import Main.model.requests.Request;

import java.util.ArrayList;

public class ManagerController {

    public String showUsersList() {
        return ManagerAccount.showManagersList() + SellerAccount.showSellersList() + BuyerAccount.showBuyersList();
    }

    public String viewUserWithUserName(String userName) {
        Account account = null;
        try {
            account = Account.getUserWithUserName(userName);
        } catch (Exception e) {
            return e.getMessage();
        }

        if (account instanceof ManagerAccount) {
            ManagerAccount managerAccount = (ManagerAccount) account;
            return managerAccount.viewMe();
        } else if (account instanceof SellerAccount) {
            SellerAccount sellerAccount = (SellerAccount) account;
            return sellerAccount.viewMe();
        } else {
            BuyerAccount buyerAccount = (BuyerAccount) account;
            return buyerAccount.viewMe();
        }
    }

    public void deleteUserWithUserName(String userName) throws Exception {
        Account account = Account.getUserWithUserName(userName);

        if (account instanceof ManagerAccount) {
            ManagerAccount managerAccount = (ManagerAccount) account;
            ManagerAccount.deleteManager(managerAccount);
        } else if (account instanceof SellerAccount) {
            SellerAccount sellerAccount = (SellerAccount) account;
            sellerAccount.deleteSeller();
        } else {
            BuyerAccount buyerAccount = (BuyerAccount) account;
            buyerAccount.deleteBuyer();
        }
    }

    public void createManagerProfile(ArrayList<String> managerInfo) throws Exception {
        GeneralController.validateInputAccountInfo(managerInfo, managerInfo.get(1));
        ManagerAccount managerAccount = new ManagerAccount(managerInfo.get(1), managerInfo.get(2), managerInfo.get(3),
                managerInfo.get(4), managerInfo.get(5), managerInfo.get(0));
        ManagerAccount.addManager(managerAccount);
    }

    public void removeProductWithId(String productId) throws Exception {
        Product product = Product.getProductWithId(productId);

        product.removeProduct();
    }

    public void createDiscountCode(ArrayList<String> discountInfo) throws Exception {
        validateInputDiscountInfo(discountInfo);

        ArrayList<BuyerAccount> buyersList = extractDiscountBuyersList(discountInfo);
        DiscountCode discountCode = new DiscountCode(discountInfo.get(1), discountInfo.get(2), discountInfo.get(3),
                discountInfo.get(4), discountInfo.get(5), buyersList);

        DiscountCode.addDiscountCode(discountCode);
        for (BuyerAccount buyerAccount : buyersList) {
            buyerAccount.addDiscountCode(discountCode);
        }
    }

    private void validateInputDiscountInfo(ArrayList<String> discountInfo) throws Exception {
        String discountCreationErrors = new String();

        try {
            DiscountAndOffTypeServiceException.validateInputDate(discountInfo.get(0));
        } catch (Exception e) {
            discountCreationErrors.concat(e.getMessage());
        }
        try {
            DiscountAndOffTypeServiceException.validateInputDate(discountInfo.get(1));
        } catch (Exception e) {
            discountCreationErrors.concat(e.getMessage());
        }
        if (discountCreationErrors.isEmpty()) {
            try {
                DiscountAndOffTypeServiceException.compareStartAndEndDate(discountInfo.get(0), discountInfo.get(1));
            } catch (Exception e) {
                discountCreationErrors.concat(e.getMessage());
            }
        }
        try {
            DiscountAndOffTypeServiceException.validateInputPercent(discountInfo.get(2));
        } catch (Exception e) {
            discountCreationErrors.concat(e.getMessage());
        }
        try {
            DiscountAndOffTypeServiceException.validateInputAmount(discountInfo.get(3));
        } catch (Exception e) {
            discountCreationErrors.concat(e.getMessage());
        }
        try {
            DiscountAndOffTypeServiceException.validateInputMaxNumOfUse(discountInfo.get(4));
        } catch (Exception e) {
            discountCreationErrors.concat(e.getMessage());
        }

        if (discountCreationErrors.isEmpty()) {
            throw new Exception("there where some errors in discount creation : \n" + discountCreationErrors);
        }
    }

    private ArrayList<BuyerAccount> extractDiscountBuyersList(ArrayList<String> discountInfo) throws Exception {
        ArrayList<BuyerAccount> buyersList = new ArrayList<BuyerAccount>();

        int discountInfoSize = discountInfo.size();
        for (int i = 6; i < discountInfoSize; i++) {
            BuyerAccount buyerAccount = BuyerAccount.getBuyerWithUserName(discountInfo.get(i));
            if (buyerAccount == null) {
                throw new Exception("There are some invalid user names in the given information !\n" +
                        "Please check user names and try again !\n");
            } else {
                buyersList.add(buyerAccount);
            }
        }
        return buyersList;
    }

    public String showAllDiscountCodes() {
        return DiscountCode.showAllDiscountCodes();
    }

    public String viewDiscountCodeWithCode(String code) {
        DiscountCode discountCode = null;
        try {
            discountCode = DiscountCode.getDiscountCodeWithCode(code);
        } catch (Exception e) {
            return e.getMessage();
        }

        return discountCode.viewMeAsManager();
    }

    public void removeDiscountCodeWithCode(String code) throws Exception {
        DiscountCode discountCode = DiscountCode.getDiscountCodeWithCode(code);

        discountCode.removeDiscountCode();
    }

    public String showAllRequests() {
        return Request.showAllRequests();
    }

    public String showRequestDetailsWithId(String requestId) {
        Request request = null;
        try {
            request = Request.getRequestWithId(requestId);
        } catch (Exception e) {
            return e.getMessage();
        }

        return request.showRequest();
    }

    public void acceptRequestWithId(String requestId) throws Exception {
        Request request = Request.getRequestWithId(requestId);

        request.accept();
    }

    public void declineRequestWithId(String requestId) throws Exception {
        Request request = Request.getRequestWithId(requestId);

        request.decline();
    }

    public void createCategory(String name, ArrayList<String> specialFeatures) throws Exception {
        validateInputCategoryInfo(name);
        Category category = new Category(name, specialFeatures);
        Category.addCategory(category);
    }

    private void validateInputCategoryInfo(String name) throws Exception {
        if (Category.isThereCategoryWithName(name)) {
            throw new Exception("there where some errors in category creation : \nthere is already a category with name :" +
                    " \'" + name + "\' !\n");
        }
    }

    public void removeCategoryWithId(String categoryId) throws Exception {
        Category category = Category.getCategoryWithName(categoryId);

        category.removeCategory();
    }

    public EditDiscountCode getDiscountCodeToEdit(String discountCode) throws Exception {
        return new EditDiscountCode(DiscountCode.getDiscountCodeWithCode(discountCode));
    }

    public void submitDiscountCodeEdits(EditDiscountCode editDiscountCode) throws Exception {
        validateInputEditDiscountInfo(editDiscountCode);
        editDiscountCode.acceptRequest();
    }

    private void validateInputEditDiscountInfo(EditDiscountCode editDiscountCode) throws Exception {
        String editDiscountCodeErrors = new String();

        try {
            DiscountAndOffTypeServiceException.validateInputDate(editDiscountCode.getStartDate());
        } catch (Exception e) {
            editDiscountCodeErrors.concat("start date is invalid :\n" + e.getMessage());
        }
        try {
            DiscountAndOffTypeServiceException.validateInputDate(editDiscountCode.getEndDate());
        } catch (Exception e) {
            editDiscountCodeErrors.concat("end date is invalid :\n" + e.getMessage());
        }
        if (editDiscountCodeErrors.isEmpty()) {
            try {
                DiscountAndOffTypeServiceException.compareStartAndEndDate(editDiscountCode.getStartDate(), editDiscountCode.getEndDate());
            } catch (Exception e) {
                editDiscountCodeErrors.concat(e.getMessage());
            }
        }
        try {
            DiscountAndOffTypeServiceException.validateInputPercent(editDiscountCode.getPercent());
        } catch (Exception e) {
            editDiscountCodeErrors.concat(e.getMessage());
        }

        try {
            DiscountAndOffTypeServiceException.validateInputAmount(editDiscountCode.getMaxAmount());
        } catch (Exception e) {
            editDiscountCodeErrors.concat(e.getMessage());
        }

        try {
            DiscountAndOffTypeServiceException.validateInputMaxNumOfUse(editDiscountCode.getMaxNumberOfUse());
        } catch (Exception e) {
            editDiscountCodeErrors.concat(e.getMessage());
        }
        try {
            validateEditDiscountUsersToBeAdded(editDiscountCode);
        } catch (Exception e) {
            editDiscountCodeErrors.concat(e.getMessage());
        }
        try {
            validateEditDiscountUsersToBeRemoved(editDiscountCode);
        } catch (Exception e) {
            validateEditDiscountUsersToBeRemoved(editDiscountCode);
        }

        if (editDiscountCodeErrors.isEmpty()) {
            throw new Exception("there where some errors in discount edit : \n" + editDiscountCodeErrors);
        }
    }

    private void validateEditDiscountUsersToBeAdded(EditDiscountCode editDiscountCode) throws Exception {
        String invalidUserNames = new String();
        for (String userNameToBeAdded : editDiscountCode.getUsersToBeAdded()) {
            try {
                BuyerAccount.getBuyerWithUserName(userNameToBeAdded);
            } catch (Exception e) {
                invalidUserNames.concat(e.getMessage());
            }
        }
        if (!invalidUserNames.isEmpty()) {
            throw new Exception("there where some errors in adding users : \n" + invalidUserNames);
        }
    }

    private void validateEditDiscountUsersToBeRemoved(EditDiscountCode editDiscountCode) throws Exception {
        String invalidUserNames = new String();
        for (String userNameToBeRemoved : editDiscountCode.getUsersToBeRemoved()) {
            try {
                BuyerAccount buyerAccount = BuyerAccount.getBuyerWithUserName(userNameToBeRemoved);
                if (!editDiscountCode.getDiscountCode().isThereBuyerWithReference(buyerAccount)) {
                    throw new Exception("There is no buyer with user name : " + userNameToBeRemoved + " in discount code's user list !\n");
                }
            } catch (Exception e) {
                invalidUserNames.concat(e.getMessage());
            }
        }
        if (!invalidUserNames.isEmpty()) {
            throw new Exception("there where some errors in removing users : \n" + invalidUserNames);
        }
    }

    public void acceptEditCategory(EditCategory editCategory) throws Exception {
        editCategory.acceptRequest();
    }
}
