package Main.view;

import Main.controller.GeneralController;

import java.util.ArrayList;

public class RegistrationMenu extends Menu {

    public RegistrationMenu(Menu parentMenu) {
        super("Create account", parentMenu);
        this.subMenus.put(1, registerManager());
        this.subMenus.put(2, registerBuyer());
        this.subMenus.put(3, registerSeller());

    }

    @Override
    public void show() {
        System.out.println(this.getName() + ":");
        System.out.println("Enter type of user [manager|buyer|seller] or Back to return");
    }

    @Override
    public void execute() {
        String input = scanner.nextLine();
        if(input.equalsIgnoreCase("back"))
            this.parentMenu.run();
        else {
            System.out.println("Enter your username");
            String username = scanner.nextLine();
            String messageFromCreateAccountMethod = generalController.createAccount(input,username);
            System.out.println(messageFromCreateAccountMethod);
            if(messageFromCreateAccountMethod.startsWith("this") || messageFromCreateAccountMethod.startsWith("you")){
                this.run();
            }else{
                GeneralController.selectedUsername = username;
                if (input.equalsIgnoreCase("manager"))
                    registerManager().run();
                else if (input.equalsIgnoreCase("buyer"))
                    registerBuyer().run();
                else if (input.equalsIgnoreCase("seller"))
                    registerSeller().run();
            }
            this.parentMenu.run();
        }
    }

    private Menu registerManager() {
        return new Menu("Register manager", this) {
            @Override
            public void show() {
                System.out.println(this.getName() + ":");
            }
            @Override
            public void execute() throws Exception {
                ArrayList<String> managerInfo = new ArrayList<>();
                getManagerInfo(managerInfo);
                generalController.getManagerInformation(managerInfo,GeneralController.selectedUsername);
            }
        };
    }

    public void getManagerInfo(ArrayList<String> managerInfo){
        System.out.println("Enter your personal information:\nPassword:");
        String password = scanner.nextLine();
        managerInfo.add(password);
        System.out.println("First name:");
        String firstName = scanner.nextLine();
        managerInfo.add(firstName);
        System.out.println("Last name:");
        String lastName = scanner.nextLine();
        managerInfo.add(lastName);
        System.out.println("Email:");
        String email = scanner.nextLine();
        managerInfo.add(email);
        System.out.println("Phone number:");
        String phoneNumber = scanner.nextLine();
        managerInfo.add(phoneNumber);
    }

    private Menu registerBuyer() {
        return new Menu("Register Buyer", this){
            @Override
            public void show(){
                System.out.println(this.getName() + ":");
            }
            @Override
            public void execute() throws Exception {
                ArrayList<String> buyerInfo = new ArrayList<>();
                getBuyerInfo(buyerInfo);
                generalController.getBuyerInformation(buyerInfo, GeneralController.selectedUsername);
            }
        };
    }

    public void getBuyerInfo(ArrayList<String> buyerInfo){
        System.out.println("Enter your personal information:\nPassword:");
        String password = scanner.nextLine();
        buyerInfo.add(password);
        System.out.println("First name:");
        String firstName = scanner.nextLine();
        buyerInfo.add(firstName);
        System.out.println("Last name:");
        String lastName = scanner.nextLine();
        buyerInfo.add(lastName);
        System.out.println("Email:");
        String email = scanner.nextLine();
        buyerInfo.add(email);
        System.out.println("Phone number:");
        String phoneNumber = scanner.nextLine();
        buyerInfo.add(phoneNumber);
        System.out.println("Balance:");
        String balance = scanner.nextLine();
        buyerInfo.add(balance);
    }

    private Menu registerSeller() {
        return new Menu("Register seller" , this){
            @Override
            public void show(){
                System.out.println(this.getName() + ":");
            }
            @Override
            public void execute() throws Exception {
                ArrayList<String> sellerInfo = new ArrayList<>();
                getSellerInfo(sellerInfo);
                generalController.getSellerInformation(sellerInfo, GeneralController.selectedUsername);
            }
        };
    }

    public void getSellerInfo(ArrayList<String> sellerInfo){
        System.out.println("Enter your personal information:\nPassword:");
        String password = scanner.nextLine();
        sellerInfo.add(password);
        System.out.println("First name:");
        String firstName = scanner.nextLine();
        sellerInfo.add(firstName);
        System.out.println("Last name:");
        String lastName = scanner.nextLine();
        sellerInfo.add(lastName);
        System.out.println("Email:");
        String email = scanner.nextLine();
        sellerInfo.add(email);
        System.out.println("Phone number:");
        String phoneNumber = scanner.nextLine();
        sellerInfo.add(phoneNumber);
        System.out.println("Company name:");
        String companyName = scanner.nextLine();
        sellerInfo.add(companyName);
        System.out.println("Company information:");
        String companyInfo = scanner.nextLine();
        sellerInfo.add(companyInfo);
    }
}

//TODO check validations
