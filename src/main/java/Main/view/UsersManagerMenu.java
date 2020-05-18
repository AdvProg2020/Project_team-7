package Main.view;

import java.util.ArrayList;

public class UsersManagerMenu extends Menu {
    public UsersManagerMenu(Menu parentMenu) {
        super("Manage users", parentMenu);
        this.subMenus.put(1, view());
        this.subMenus.put(2, deleteUser());
        this.subMenus.put(3, createManagerProfile());
        this.subMenus.put(4, new SortingMenu(this));

    }

    private Menu view() {
        return new Menu("View user", this) {
            @Override
            public void show() {
                System.out.println(this.getName() + ":");
                System.out.println("Enter a username or 'Back' to return");
            }

            @Override
            public void execute() throws Exception {
                String input = scanner.nextLine();
                if (input.equalsIgnoreCase("back"))
                    this.parentMenu.run();
                else {
                    System.out.println(managerController.viewUserWithUserName(input) + "\n");
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
                System.out.println("Enter a username or 'Back' to return");
            }

            @Override
            public void execute() throws Exception {
                String input = scanner.nextLine().trim();
                if (input.equalsIgnoreCase("back"))
                    this.parentMenu.run();
                else {
                    try {
                        managerController.deleteUserWithUserName(input);
                        System.out.println("User deleted successfully.\n");
                        this.run();
                    } catch (Exception e) {
                        System.out.println(e.getMessage() + "\n");
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
                System.out.println("Enter 'Create' or 'Back' to return:");
            }

            @Override
            public void execute() throws Exception {
                String input = scanner.nextLine().trim();
                if (input.equalsIgnoreCase("back"))
                    this.parentMenu.run();
                else if (input.equalsIgnoreCase("create")) {
                    try {
                        ArrayList<String> managerInfo = new ArrayList<>();
                        System.out.println("Username:");
                        String username = scanner.nextLine().trim();
                        getManagerInfo(managerInfo);
                        managerController.createManagerProfile(managerInfo,username);
                        System.out.println("Manager account created successfully.\n");
                        this.run();
                    } catch (Exception e) {
                        System.out.println("hi" + e.getMessage() + "\n");
                        this.run();
                    }
                } else {
                    System.out.println("Invalid input!\n");
                    this.run();
                }
            }
        };
    }

    public void getManagerInfo(ArrayList<String> managerInfo) {
        System.out.println("Password:");
        String password = scanner.nextLine().trim();
        managerInfo.add(password);
        System.out.println("First name:");
        String firstName = scanner.nextLine().trim();
        managerInfo.add(firstName);
        System.out.println("Last name:");
        String lastName = scanner.nextLine().trim();
        managerInfo.add(lastName);
        System.out.println("Email:");
        String email = scanner.nextLine().trim();
        managerInfo.add(email);
        System.out.println("Phone number:");
        String phoneNumber = scanner.nextLine().trim();
        managerInfo.add(phoneNumber);
    }

    @Override
    public void run() throws Exception {
        System.out.println(managerController.showUsersList() + "\n");
        this.show();
        this.execute();
    }
}
