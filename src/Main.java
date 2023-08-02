


public class Main {
    static AdminController adminController = new AdminController();
    static LibrarianController librarianController = new LibrarianController();

    public static void main(String[] args) {
        adminController.login("admin", "admin98");
        adminController.addLibrarian("MiMi Acs", "abc", "asdsafd", "fasdasf", "3245");
        adminController.addLibrarian("cat", "ana", "asdsafd", "fasdasf", "3245");
        adminController.addLibrarian("mimi", "password", "asdsafd", "fasdasf", "3245");
        adminController.viewLibrarians();
        adminController.logout();


        librarianController.login("MiMi Acs", "abc");


       //librarianController.addBook("b45", "ads", "tmrw", 5);
//        librarianController.addBook("a8765", "cat", "ice", 6);
//        librarianController.addBook("k98", "dog", "ice tea", 8);
//        librarianController.addBook("l34", "mice", "sleep", 4);
//        librarianController.addBook("g23", "capybara", "generic isekai", 9);
//        librarianController.addBook("o90", "bunny", "fantasy", 7);
//
//        librarianController.addStudent("004", "maria", "0965748");
//        librarianController.addStudent("014", "mara", "0965748");
//        librarianController.addStudent("016", "ioana", "0965748");
//        librarianController.addStudent("023", "alexandra", "0965748");
//        librarianController.addStudent("156", "ion", "0965748");
//        librarianController.addStudent("234", "gheorghe", "0965748");
//
//        librarianController.viewStudents();
//        librarianController.viewBooks();

        librarianController.issueBook("tmrw", "ads","004");
        librarianController.issueBook("ice", "cat","234");
        librarianController.issueBook("sleep", "mice","234");
        librarianController.issueBook("generic isekai", "capybara","014");
        librarianController.issueBook("tmrw", "ads","016");




    }
}
