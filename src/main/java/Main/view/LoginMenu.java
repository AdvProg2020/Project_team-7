package Main.view;

public class LoginMenu extends Menu {
    public LoginMenu(Menu parentMenu) {
        super("Login Menu",parentMenu);
        this.subMenus.put(1,login());

    }

    private Menu login() {
        return new Menu("Login",this){
            @Override
            public void show(){
                System.out.println(this.getName() + ":");
                System.out.println("Enter your username or Back to return:");
            }
            @Override
            public void execute(){
                String input = scanner.nextLine();
                if(input.equalsIgnoreCase("back"))
                    this.parentMenu.run();
                else{
                    System.out.println("Enter your password:");
                    String password = scanner.nextLine();
                    String messageFromLoginPage = generalController.login(input,password);
                    if(messageFromLoginPage.equals("Logged in successfully.")){
                        System.out.println(messageFromLoginPage);
                        this.parentMenu.parentMenu.run();
                    }else{
                        System.out.println(messageFromLoginPage);
                        this.run();
                    }
                }
            }
        };
    }
}
