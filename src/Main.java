


public class Main {
    static AdminController adminController = new AdminController();
    static LibrarianController librarianController = new LibrarianController();

    public static void main(String[] args) {

        initializeData();

        librarianController.login("MiMi Acs", "abc");

        librarianController.viewStudents();
        librarianController.viewBooks();

        librarianController.issueBook("tmrw", "ads","004");
        librarianController.issueBook("ice", "cat","234");
        librarianController.issueBook("sleep", "mice","234");
        librarianController.issueBook("generic isekai", "capybara","014");
        librarianController.issueBook("tmrw", "ads","016");

    }

    public static void initializeData() {
        adminController.login("admin", "admin98");
        adminController.addLibrarian("MiMi Acs", "abc", "asdsafd", "fasdasf", "3245");
        adminController.addLibrarian("cat", "ana", "asdsafd", "fasdasf", "3245");
        adminController.addLibrarian("mimi", "password", "asdsafd", "fasdasf", "3245");
        adminController.viewLibrarians();
        adminController.logout();
    }
}
