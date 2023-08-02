public class LibrarianController {

    boolean isLoggedIn = false;

    public void login(String name, String password) {

        if (Database.existsLibrarianCredentials(name, password)) {
            isLoggedIn = true;
            System.out.printf("Welcome %s !\n", name);
        } else {
            System.out.println("Credentials are incorrect!");
        }
    }

    public void logout() {
        isLoggedIn = false;
        System.out.println("\nYou have logged out!");
    }

    private boolean isAccountLoggedIn() {
        if (isLoggedIn) {
            return true;
        } else {
            System.out.println("\nYou are not logged in!");
            return false;
        }
    }

    public void addBook(String ID, String author, String title, int quantity) {
        if (isAccountLoggedIn()) {
            Database.createBook(ID, author, title, quantity);
        }
    }

    public void addStudent(String studentId, String name, String phoneNumber) {
        if (isAccountLoggedIn()) {
            Database.createStudent(studentId, name, phoneNumber);
        }
    }

    public void viewBooks() {
        if (isAccountLoggedIn()) {
            Database.readAllAvailableBooks();
        }
    }

    public void issueBook(String bookTitle, String author, String studentId) {
        if (isAccountLoggedIn()) {
            if (Database.existsBook(bookTitle, author) && Database.existsStudent(studentId)) {
                Database.createIssuedBook(bookTitle, author, studentId);
                Database.updateBookIssued(bookTitle, author);
            } else {
                System.out.println("Book or student not found!");
            }
        }
    }

    public void viewIssuedBooks() {
        if (isAccountLoggedIn()) {
            Database.readAllIssuedBooks();
        }
    }

    public void returnBook(String bookId, String studentName) {
        if (isAccountLoggedIn()) {
            Database.deleteIssuedBook(bookId, studentName);
            Database.updateBookReturned(bookId);
        }
    }

    public void viewStudents() {
        if (isAccountLoggedIn()) {
            Database.readAllStudents();
        }
    }
}
