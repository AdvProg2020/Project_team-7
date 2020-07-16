package Main.client.consoleViewOld;

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
                System.out.println("Insert 'Add comment' or 'Back' to return:");
            }

            @Override
            public void execute() throws Exception {
                String input = scanner.nextLine().trim();
                if (input.equalsIgnoreCase("back"))
                    this.parentMenu.run();
                else if (input.equalsIgnoreCase("add comment")) {
                    System.out.println("Insert the title:");
                    String title = scanner.nextLine().trim();
                    System.out.println("Insert the content:");
                    String content = scanner.nextLine().trim();
                    try {
                        //TODO ALERT: watch out for the null arguments :D
                        generalController.addComment(title, content,null,null);
                        System.out.println("Your comment sent successfully.\n");
                        this.run();
                    } catch (Exception e){
                        System.out.println(e.getMessage() + "\n");
                        this.run();
                    }
                } else {
                    System.out.println("Invalid input!\n");
                    this.run();
                }
            }
        };
    }

    @Override
    public void run() throws Exception {
        System.out.println(generalController.showCommentsOfProduct() + "\n");
        this.show();
        this.execute();
    }
}
