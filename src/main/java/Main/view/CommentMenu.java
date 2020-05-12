package Main.view;

public class CommentMenu extends Menu {
    public CommentMenu(Menu parentMenu) {
        super("Comments", parentMenu);
        this.subMenus.put(1, addComment());

    }

    private Menu addComment() {
        return new Menu("Add comment", this) {
            @Override
            public void show() {
                System.out.println(this.getName() + ":");
                System.out.println("Enter Add comment or Back to return");
            }

            @Override
            public void execute() throws Exception {
                String input = scanner.nextLine().trim();
                if (input.equalsIgnoreCase("back"))
                    this.parentMenu.run();
                else if (input.equalsIgnoreCase("add comment")) {
                    System.out.println("Enter the title:");
                    String title = scanner.nextLine();
                    System.out.println("Enter the content:");
                    String content = scanner.nextLine();
                    generalController.addComment(title, content);
                    System.out.println("Your comment sent successfully");
                    this.run();
                }

            }
        };
    }

    @Override
    public void run() throws Exception {
        System.out.println(generalController.showCommentsOfProduct());
        this.show();
        this.execute();
    }
}
