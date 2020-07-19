package Main.server.controller;

import Main.server.model.Category;
import Main.server.model.Product;
import Main.server.model.accounts.Account;
import Main.server.model.accounts.BuyerAccount;
import Main.server.model.accounts.ManagerAccount;
import Main.server.model.accounts.SellerAccount;
import Main.server.model.discountAndOffTypeService.DiscountCode;
import Main.server.model.exceptions.AccountsException;
import Main.server.model.exceptions.DiscountAndOffTypeServiceException;
import Main.server.model.requests.EditCategory;
import Main.server.model.requests.EditDiscountCode;
import Main.server.model.requests.Request;

import java.util.ArrayList;

public class ManagerController {

    public String showUsersList() {
        return ManagerAccount.showManagersList() + SellerAccount.showSellersList() + BuyerAccount.showBuyersList();
    }

    public ArrayList<String> usersListForGraphic() {
        ArrayList<String> users = new ArrayList<>();
        users.addAll(ManagerAccount.allManagersForGraphic());
        users.addAll(SellerAccount.allSellersForGraphic());
        users.addAll(BuyerAccount.allBuyersForGraphic());
        return users;
    }

    public String viewUserWithUserName(String userName) {
        Account account;
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

    public void createManagerProfile(ArrayList<String> managerInfo, String userName) throws Exception {
        GeneralController.validateInputAccountInfo(managerInfo, userName);
        ManagerAccount managerAccount = new ManagerAccount(userName, managerInfo.get(1), managerInfo.get(2),
                managerInfo.get(3), managerInfo.get(4), managerInfo.get(0), null);
        ManagerAccount.addManager(managerAccount);
    }

    public void removeProductWithId(String productId) throws Exception {
        Product product = Product.getProductWithId(productId);

        product.removeProduct();
    }

    public String createDiscountCode(ArrayList<String> buyersUserNamesList, ArrayList<String> discountInfo) throws Exception {
        validateInputDiscountInfo(buyersUserNamesList, discountInfo);

        ArrayList<BuyerAccount> buyersList = extractDiscountBuyersList(buyersUserNamesList);
        DiscountCode discountCode = new DiscountCode(discountInfo.get(0), discountInfo.get(1), discountInfo.get(2),
                discountInfo.get(3), discountInfo.get(4), buyersList);

        discountCode.addDiscountCode();
        for (BuyerAccount buyerAccount : buyersList) {
            buyerAccount.addDiscountCode(discountCode);
        }
        return "Created Discount Successfully!";
    }

    private void validateInputDiscountInfo(ArrayList<String> buyersUserNamesList, ArrayList<String> discountInfo) throws Exception {
        StringBuilder discountCreationErrors = new StringBuilder();

        try {
            DiscountAndOffTypeServiceException.validateInputDate(discountInfo.get(0));
        } catch (Exception e) {
            discountCreationErrors.append("start date is invalid :\n").append(e.getMessage());
        }
        try {
            DiscountAndOffTypeServiceException.validateInputDate(discountInfo.get(1));
        } catch (Exception e) {
            discountCreationErrors.append("end date is invalid :\n").append(e.getMessage());
        }
        if (discountCreationErrors.length() == 0) {
            try {
                DiscountAndOffTypeServiceException.compareStartAndEndDate(discountInfo.get(0), discountInfo.get(1));
            } catch (Exception e) {
                discountCreationErrors.append(e.getMessage());
            }
        }
        try {
            DiscountAndOffTypeServiceException.validateInputPercent(discountInfo.get(2));
        } catch (Exception e) {
            discountCreationErrors.append(e.getMessage());
        }
        try {
            DiscountAndOffTypeServiceException.validateInputAmount(discountInfo.get(3));
        } catch (Exception e) {
            discountCreationErrors.append(e.getMessage());
        }
        try {
            DiscountAndOffTypeServiceException.validateInputMaxNumOfUse(discountInfo.get(4));
        } catch (Exception e) {
            discountCreationErrors.append(e.getMessage());
        }
        try {
            validateDiscountBuyersToBeSet(buyersUserNamesList);
        } catch (Exception e) {
            discountCreationErrors.append(e.getMessage());
        }

        if (discountCreationErrors.length() != 0) {
            throw new Exception("there where some errors in discount creation : \n" + discountCreationErrors);
        }
    }

    private void validateDiscountBuyersToBeSet(ArrayList<String> buyerUserNamesList) throws Exception {
        StringBuilder discountBuyersToBeSetErrors = new StringBuilder();

        for (String username : buyerUserNamesList) {
            try {
                BuyerAccount buyerAccount = BuyerAccount.getBuyerWithUserName(username);
            } catch (Exception e) {
                discountBuyersToBeSetErrors.append(e.getMessage());
            }
        }

        if (discountBuyersToBeSetErrors.length() != 0) {
            throw new Exception("there were some errors in setting buyers : \n" + discountBuyersToBeSetErrors);
        }
    }

    private ArrayList<BuyerAccount> extractDiscountBuyersList(ArrayList<String> buyerUserNamesList) throws Exception {
        ArrayList<BuyerAccount> buyersList = new ArrayList<>();
        for (String username : buyerUserNamesList) {
            buyersList.add(BuyerAccount.getBuyerWithUserName(username));
        }
        return buyersList;
    }

    public String showAllDiscountCodes() {
        return DiscountCode.showAllDiscountCodes();
    }

    public String viewDiscountCodeWithCode(String code) {
        DiscountCode discountCode;
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
        Request request;
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

    public String createCategory(String name, ArrayList<String> specialFeatures, String imagePath) throws Exception {
        validateInputCategoryInfo(name);
        Category category = new Category(name, specialFeatures, imagePath);
        Category.addCategory(category);
        return "Created category successfully!";
    }

    private void validateInputCategoryInfo(String name) throws Exception {
        StringBuilder inputCategoryInfoErrors = new StringBuilder();

        if (Category.isThereCategoryWithName(name)) {
            inputCategoryInfoErrors.append("there is already a category with name :" + " '").append(name).append("' !\n");
        }
        try {
            AccountsException.validateNameTypeInfo("category name", name);
        } catch (AccountsException e) {
            inputCategoryInfoErrors.append(e.getErrorMessage());
        }

        if (inputCategoryInfoErrors.length() != 0) {
            throw new Exception("there were some errors in category name : \n" + inputCategoryInfoErrors);
        }
    }

    public void removeCategoryWithName(String categoryName) throws Exception {
        Category category = Category.getCategoryWithName(categoryName);

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
        StringBuilder editDiscountCodeErrors = new StringBuilder();

        try {
            DiscountAndOffTypeServiceException.validateInputDate(editDiscountCode.getStartDate());
        } catch (Exception e) {
            editDiscountCodeErrors.append("start date is invalid :\n").append(e.getMessage());
        }
        try {
            DiscountAndOffTypeServiceException.validateInputDate(editDiscountCode.getEndDate());
        } catch (Exception e) {
            editDiscountCodeErrors.append("end date is invalid :\n").append(e.getMessage());
        }
        if (editDiscountCodeErrors.length() == 0) {
            try {
                DiscountAndOffTypeServiceException.compareStartAndEndDate(editDiscountCode.getStartDate(), editDiscountCode.getEndDate());
            } catch (Exception e) {
                editDiscountCodeErrors.append(e.getMessage());
            }
        }
        try {
            DiscountAndOffTypeServiceException.validateInputPercent(editDiscountCode.getPercent());
        } catch (Exception e) {
            editDiscountCodeErrors.append(e.getMessage());
        }

        try {
            DiscountAndOffTypeServiceException.validateInputAmount(editDiscountCode.getMaxAmount());
        } catch (Exception e) {
            editDiscountCodeErrors.append(e.getMessage());
        }

        try {
            DiscountAndOffTypeServiceException.validateInputMaxNumOfUse(editDiscountCode.getMaxNumberOfUse());
        } catch (Exception e) {
            editDiscountCodeErrors.append(e.getMessage());
        }
        try {
            validateEditDiscountUsersToBeAdded(editDiscountCode);
        } catch (Exception e) {
            editDiscountCodeErrors.append(e.getMessage());
        }
        try {
            validateEditDiscountUsersToBeRemoved(editDiscountCode);
        } catch (Exception e) {
            validateEditDiscountUsersToBeRemoved(editDiscountCode);
        }

        if (editDiscountCodeErrors.length() != 0) {
            throw new Exception("there where some errors in discount edit : \n" + editDiscountCodeErrors);
        }
    }

    private void validateEditDiscountUsersToBeAdded(EditDiscountCode editDiscountCode) throws Exception {
        StringBuilder invalidUserNames = new StringBuilder();
        for (String userNameToBeAdded : editDiscountCode.getUsersToBeAdded()) {
            try {
                BuyerAccount.getBuyerWithUserName(userNameToBeAdded);
            } catch (Exception e) {
                invalidUserNames.append(e.getMessage());
            }
        }
        if (invalidUserNames.length() != 0) {
            throw new Exception("there where some errors in adding users : \n" + invalidUserNames);
        }
    }

    private void validateEditDiscountUsersToBeRemoved(EditDiscountCode editDiscountCode) throws Exception {
        StringBuilder invalidUserNames = new StringBuilder();
        for (String userNameToBeRemoved : editDiscountCode.getUsersToBeRemoved()) {
            try {
                BuyerAccount buyerAccount = BuyerAccount.getBuyerWithUserName(userNameToBeRemoved);
                if (!editDiscountCode.getDiscountCode().isThereBuyerWithReference(buyerAccount)) {
                    throw new Exception("There is no buyer with user name : " + userNameToBeRemoved + " in discount code's user list !\n");
                }
            } catch (Exception e) {
                invalidUserNames.append(e.getMessage());
            }
        }
        if (invalidUserNames.length() != 0) {
            throw new Exception("there where some errors in removing users : \n" + invalidUserNames);
        }
    }

    public EditCategory getCategoryToEdit(String categoryName) throws Exception {
        return new EditCategory(Category.getCategoryWithName(categoryName));
    }

    public void submitCategoryEdits(EditCategory editCategory) throws Exception {
        validateInputEditCategoryInfo(editCategory);
        editCategory.acceptRequest();
    }

    private void validateInputEditCategoryInfo(EditCategory editCategory) throws Exception {
        StringBuilder editCategoryErrors = new StringBuilder();

        try {
            if (!editCategory.getCategory().getName().equals(editCategory.getName())) {
                validateInputCategoryInfo(editCategory.getName());
            }
        } catch (Exception e) {
            editCategoryErrors.append(e.getMessage());
        }
        try {
            validateCategoryProductsToBeAdded(editCategory);
        } catch (Exception e) {
            editCategoryErrors.append(e.getMessage());
        }
        try {
            validateEditCategoryProductsToBeRemoved(editCategory);
        } catch (Exception e) {
            editCategoryErrors.append(e.getMessage());
        }

        try {
            validateEditCategorySpecialFeaturesToBeAdded(editCategory);
        } catch (Exception e) {
            editCategoryErrors.append(e.getMessage());
        }

        try {
            validateEditCategorySpecialFeaturesToBeRemoved(editCategory);
        } catch (Exception e) {
            editCategoryErrors.append(e.getMessage());
        }

        if (editCategoryErrors.length() != 0) {
            throw new Exception("there where some errors in category edit : \n" + editCategoryErrors);
        }
    }


    private void validateCategoryProductsToBeAdded(EditCategory editCategory) throws Exception {
        StringBuilder invalidProductIDs = new StringBuilder();
        for (String productID : editCategory.getProductsToBeAdded()) {
            try {
                Product.getProductWithId(productID);
            } catch (Exception e) {
                invalidProductIDs.append(e.getMessage());
            }
        }
        if (invalidProductIDs.length() != 0) {
            throw new Exception("there where some errors in adding products : \n" + invalidProductIDs);
        }
    }

    private void validateEditCategoryProductsToBeRemoved(EditCategory editCategory) throws Exception {
        StringBuilder invalidProductIDs = new StringBuilder();
        for (String productID : editCategory.getProductsToBeRemoved()) {
            try {
                Product product = Product.getProductWithId(productID);
                if (!editCategory.getCategory().isThereProductWithReference(product)) {
                    throw new Exception("There is no product with this ID in this category !\n");
                }
            } catch (Exception e) {
                invalidProductIDs.append(e.getMessage());
            }
        }
        if (invalidProductIDs.length() != 0) {
            throw new Exception("there where some errors in removing products : \n" + invalidProductIDs);
        }
    }

    private void validateEditCategorySpecialFeaturesToBeAdded(EditCategory editCategory) throws Exception {
        StringBuilder specialFeaturesToBeAddedErrors = new StringBuilder();

        Category category = editCategory.getCategory();
        for (String specialFeaturesToBeAdded : editCategory.getSpecialFeaturesToBeAdded()) {
            if (category.isThereSpecialFeature(specialFeaturesToBeAdded)) {
                specialFeaturesToBeAddedErrors.append("There is already a special feature with title \"").append(specialFeaturesToBeAdded).append("\" in this category !\n");
            }
        }
        if (specialFeaturesToBeAddedErrors.length() != 0) {
            throw new Exception("there were some errors in adding special features : \n" + specialFeaturesToBeAddedErrors);
        }
    }

    private void validateEditCategorySpecialFeaturesToBeRemoved(EditCategory editCategory) throws Exception {
        StringBuilder specialFeaturesToBeRemovedErrors = new StringBuilder();

        Category category = editCategory.getCategory();
        for (String specialFeatureToBeRemoved : editCategory.getSpecialFeaturesToBeRemoved()) {
            if (!category.isThereSpecialFeature(specialFeatureToBeRemoved)) {
                specialFeaturesToBeRemovedErrors.append("There is no special feature with title : ").append(specialFeatureToBeRemoved).append(" in this category!\n");
            }
        }
        if (specialFeaturesToBeRemovedErrors.length() != 0) {
            throw new Exception("there were some errors in removing special features : \n" + specialFeaturesToBeRemovedErrors);
        }
    }
}
