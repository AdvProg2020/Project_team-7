package Main.view;

import java.util.ArrayList;

public class ManagerPanelMenu extends Menu {
    public ManagerPanelMenu(Menu parentMenu) {
        super("manager Panel",parentMenu);
        this.subMenus.put(1, new PersonalInfoMenu(this));
        this.subMenus.put(2, new UsersManagerMenu(this));
        this.subMenus.put(3, new AllProductsManagerMenu(this));
        this.subMenus.put(4, createDiscountCode());
        this.subMenus.put(5, new DiscountCodeManagerMenu(this));
        this.subMenus.put(6, new RequestManagerMenu(this));
    }

    private Menu createDiscountCode() {
        return new Menu("Create discount code",this){
            @Override
            public void show(){
                System.out.println(this.getName() + ":");
                System.out.println("Enter Continue to create discount code or Back to return:");
            }
            @Override
            public void execute() throws Exception {
                String input = scanner.nextLine();
                if(input.equalsIgnoreCase("back"))
                    this.parentMenu.run();
                else if(input.equalsIgnoreCase("continue")){
                    try{
                        ArrayList<String> discountInfo = new ArrayList<>();
                        getDiscountInfo(discountInfo);
                        ArrayList<String> buyersList = new ArrayList<>();
                        getBuyerIdList(buyersList);
                        managerController.createDiscountCode(buyersList,discountInfo);
                        System.out.println("Discount code created successfully.");
                        this.run();
                    }
                    catch(Exception e){
                        System.out.println(e.getMessage());
                        this.run();
                    }
                }
            }
        };
    }

    public void getDiscountInfo(ArrayList<String> discountInfo){
        System.out.println("Enter discount code information:\nStart date: (The input date  should be in " +
                "<yyyy/MM/dd HH:mm:ss>format)");
        String startDate = scanner.nextLine();
        discountInfo.add(startDate);
        System.out.println("End date:");
        String endDate = scanner.nextLine();
        discountInfo.add(endDate);
        System.out.println("Percent:");
        String percent = scanner.nextLine();
        discountInfo.add(percent);
        System.out.println("Maximum amount:");
        String maxAmount = scanner.nextLine();
        discountInfo.add(maxAmount);
        System.out.println("Maximum number of use:");
        String muxNumberOfUse = scanner.nextLine();
        discountInfo.add(muxNumberOfUse);
    }

    public void getBuyerIdList(ArrayList<String> buyersList){
        System.out.println("Number of buyer accounts receiving this discount code:");
        int numberOfBuyers = scanner.nextInt();
        System.out.println();
        for(int i=0; i<numberOfBuyers ; i++){
            String buyerId = scanner.nextLine();
            buyersList.add(buyerId);
        }
    }
}
