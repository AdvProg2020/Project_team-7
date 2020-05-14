package Main.view;

import java.util.ArrayList;

public class UsersManagerMenu extends Menu {
    public UsersManagerMenu(Menu parentMenu) {
        super("Manage users", parentMenu);
        this.subMenus.put(1, view());
        this.subMenus.put(2, deleteUser());
        this.subMenus.put(3, createManagerProfile());

    }

    private Menu view() {
        return new Menu("View user", this) {
            @Override
            public void show() {
                System.out.println(this.getName() + ":");
                System.out.println("Enter a username or Back to return");
            }

            @Override
            public void execute() throws Exception {
                String input = scanner.nextLine();
                if (input.equalsIgnoreCase("back"))
                    this.parentMenu.run();
                else {
                    System.out.println(managerController.viewUserWithUserName(input));
                    this.run();
                }
            }
        };
    }

    private Menu deleteUser() {
        return new Menu("Delete user", this) {
            @Override
            public void show() {
                System.out.println(this.getName() + ":");
                System.out.println("Enter a username or Back to return");
            }

            @Override
            public void execute() throws Exception {
                String input = scanner.nextLine();
                if (input.equalsIgnoreCase("back"))
                    this.parentMenu.run();
                else {
                    try {
                        managerController.deleteUserWithUserName(input);
                        System.out.println("User deleted successfully");
                        this.run();
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                        this.run();
                    }
                }
            }
        };
    }

    private Menu createManagerProfile() {
        return new Menu("Create manager profile", this) {
            @Override
            public void show() {
                System.out.println(this.getName() + ":");
                System.out.println("Enter Create profile or Back to return:");
            }

            @Override
            public void execute() throws Exception {
                String input = scanner.nextLine();
                if (input.equalsIgnoreCase("back"))
                    this.parentMenu.run();
                else if (input.equalsIgnoreCase("create profile")) {
                    try {
                        ArrayList<String> managerInfo = new ArrayList<>();
                        getManagerInfo(managerInfo);
                        managerController.createManagerProfile(managerInfo);
                        System.out.println("Manager account created successfully");
                        this.run();
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                        this.run();
                    }
                }
            }
        };
    }

    public void getManagerInfo(ArrayList<String> managerInfo) {
        System.out.println("Password:");
        String password = scanner.nextLine();
        managerInfo.add(password);
        System.out.println("Username:");
        String username = scanner.nextLine();
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

    @Override
    public void run() throws Exception {
        System.out.println(managerController.showUsersList());
        this.show();
        this.execute();
    }
}
