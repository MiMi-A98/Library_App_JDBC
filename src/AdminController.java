import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class AdminController {
    boolean isLoggedIn = false;

    public void login(String name, String password) {

        if (Database.existsAdminCredentials(name, password)) {
            isLoggedIn = true;
            System.out.printf("Welcome %s !\n", name);
        } else {
            System.out.println("Credentials are incorrect!");
        }
    }

    public void logout() {
        isLoggedIn = false;
        System.out.println("\nYou have logged out of account Admin");
    }

    private boolean isAccountLoggedIn() {
        if (isLoggedIn) {
            return true;
        } else {
            System.out.println("\nYou are not logged in!");
            return false;
        }
    }

    public void addLibrarian(String name, String password, String address, String city, String phoneNumber) {
        if (isAccountLoggedIn()) {
            Database.createLibrarian(name, password, address, city, phoneNumber);
        }
    }

    public void viewLibrarians() {
        if (isAccountLoggedIn()) {
            System.out.println("\nLIBRARIAN lIST:");
            Database.readAllLibrarians();
        }
    }

    public void deleteLibrarian(String name) {
        if (isAccountLoggedIn()) {
            Database.deleteLibrarian(name);
        }
    }
}
