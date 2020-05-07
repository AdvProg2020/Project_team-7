package Main.model.requests;

import Main.model.accounts.Account;

public class EditSellerPersonalInfo extends EditPersonalInfo {

    private String companyName;
    private String companyExtraInformation;

    public EditSellerPersonalInfo(Account account) {
        super(account);
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public void setCompanyExtraInformation(String companyExtraInformation) {
        this.companyExtraInformation = companyExtraInformation;
    }

    @Override
    public void accept() {
        super.accept();

    }
}
