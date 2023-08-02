import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;
import java.util.Date;
import java.util.Properties;

public class Database {
    private Database() {
    }

    public static Connection getConnection() {
        Connection connection = null;

        try {
            FileInputStream file = new FileInputStream("db.properties");

            Properties prop = new Properties();
            prop.load(file);

            try {
                connection = DriverManager.getConnection(prop.getProperty("url"), prop.getProperty("username"), prop.getProperty("password"));
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return connection;
    }

    static boolean existsAdminCredentials(String name, String password) {
        boolean credentialsFound = false;
        try {
            Connection connection = Database.getConnection();
            PreparedStatement statement = connection.prepareStatement("select user_name , password from admin_credentials where user_name=? and password=?");
            statement.setString(1, name);
            statement.setString(2, password);
            ResultSet resultSet = statement.executeQuery();

            credentialsFound = resultSet.next();

            resultSet.close();
            statement.close();
            connection.close();
        } catch (Exception e) {
            System.out.println(e);
        }
        return credentialsFound;
    }

    static boolean existsLibrarianCredentials(String name, String password) {
        boolean credentialsFound = false;
        try {
            Connection connection = Database.getConnection();
            PreparedStatement statement = connection.prepareStatement("select name , password from librarians where name=? and password=?");
            statement.setString(1, name);
            statement.setString(2, password);
            ResultSet resultSet = statement.executeQuery();

            credentialsFound = resultSet.next();

            resultSet.close();
            statement.close();
            connection.close();
        } catch (Exception e) {
            System.out.println(e);
        }
        return credentialsFound;
    }

    static void createLibrarian(String name, String password, String address, String city, String phoneNumber) {

        try {
            Connection connection = Database.getConnection();
            PreparedStatement statement = connection.prepareStatement("insert into librarians (name, password, adress, city, phone_number) values (?,?,?,?,?)");
            statement.setString(1, name);
            statement.setString(2, password);
            statement.setString(3, address);
            statement.setString(4, city);
            statement.setString(5, phoneNumber);

            statement.executeUpdate();
            statement.close();
            connection.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }

    static void readAllLibrarians() {
        try {
            Connection connection = Database.getConnection();
            PreparedStatement statement = connection.prepareStatement("select * from librarians");
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                String name = resultSet.getString("name");
                String password = resultSet.getString("password");
                String address = resultSet.getString("adress");
                String city = resultSet.getString("city");
                String phoneNumber = resultSet.getString("phone_number");

                System.out.println("Name: " + name + " // " + "Password: " + password + " // " + "Address: " + address + " // " +
                        "City: " + city + " // " + "Phone number: " + phoneNumber);
            }
            statement.close();
            resultSet.close();
            connection.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    static void deleteLibrarian(String name) {
        try {
            Connection connection = Database.getConnection();
            PreparedStatement statement = connection.prepareStatement("delete from librarians where name = ?");
            statement.setString(1, name);
            statement.executeUpdate();

            statement.close();
            connection.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    static void createBook(String ID, String author, String title, int quantity) {
        try {
            Connection connection = Database.getConnection();
            PreparedStatement statement = connection.prepareStatement("insert into books (book_id, author_name, title, available_quantity) values (?, ?, ?, ?)");

            statement.setString(1, ID);
            statement.setString(2, author);
            statement.setString(3, title);
            statement.setInt(4, quantity);
            statement.executeUpdate();

            statement.close();
            connection.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    static boolean existsBook(String bookTitle, String authorName) {
        boolean bookExists = false;
        try {
            Connection connection = Database.getConnection();
            PreparedStatement statement = connection.prepareStatement("select * from books where title = ? and author_name = ?");
            statement.setString(1, bookTitle);
            statement.setString(2, authorName);
            ResultSet resultSet = statement.executeQuery();

            bookExists = resultSet.next();

            statement.close();
            resultSet.close();
            connection.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        if (bookExists == false) {
            System.out.println("Book not found!");
        }
        return bookExists;
    }

    static void readAllAvailableBooks() {
        try {
            Connection connection = Database.getConnection();
            PreparedStatement statement = connection.prepareStatement("select * from books");
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                String id = resultSet.getString("book_id");
                String author = resultSet.getString("author_name");
                String title = resultSet.getString("title");
                int availableQuantity = resultSet.getInt("available_quantity");
                int issuedQuantity = resultSet.getInt("issued_quantity");
                Date addedDate = resultSet.getDate("added_date");

                System.out.println("Book index: " + id + " // " + "Author: " + author + " // " + "Title: " + title + " // " + "Available quantity: " + availableQuantity + " // "
                        + "Issued quantity: " + issuedQuantity + " // " + "Added date: " + addedDate);
            }
            statement.close();
            resultSet.close();
            connection.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    static void updateBookIssued(String bookTitle, String authorName) {
        try {
            Connection connection = Database.getConnection();
            PreparedStatement statement = connection.prepareStatement("""
                    update books
                    set available_quantity = available_quantity -1, issued_quantity = issued_quantity +1
                    where title = ? and author_name = ?
                    """);
            statement.setString(1, bookTitle);
            statement.setString(2, authorName);
            statement.executeUpdate();

            statement.close();
            connection.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    static void updateBookReturned(String bookId) {
        try {
            Connection connection = Database.getConnection();
            PreparedStatement statement = connection.prepareStatement("""
                    update books
                    set available_quantity = available_quantity +1, issued_quantity = issued_quantity -1
                    where book_id = ?
                    """);
            statement.setString(1, bookId);
            statement.executeUpdate();

            statement.close();
            connection.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    static void createStudent(String studentId, String name, String phoneNumber) {
        try {
            Connection connection = Database.getConnection();
            PreparedStatement statement = connection.prepareStatement("insert into students (id, name, phone_number) values (?, ?, ?)");

            statement.setString(1, studentId);
            statement.setString(2, name);
            statement.setString(3, phoneNumber);
            statement.executeUpdate();

            statement.close();
            connection.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    static boolean existsStudent(String studentId) {
        boolean studentExists = false;
        try {
            Connection connection = Database.getConnection();
            PreparedStatement statement = connection.prepareStatement("select * from students where id = ?");
            statement.setString(1, studentId);
            ResultSet resultSet = statement.executeQuery();

            studentExists = resultSet.next();

            statement.close();
            resultSet.close();
            connection.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        if (studentExists == false) {
            System.out.println("Student not found!");
        }
        return studentExists;
    }

    static void readAllStudents() {
        try {
            Connection connection = Database.getConnection();
            PreparedStatement statement = connection.prepareStatement("select * from students");
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                String id = resultSet.getString("id");
                String name = resultSet.getString("name");
                String phoneNumber = resultSet.getString("phone_number");

                System.out.println("Student id: " + id + "//" + "Name: " + name + "//" + "Phone number: " + phoneNumber);
            }
            statement.close();
            resultSet.close();
            connection.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    static void createIssuedBook(String bookTitle, String author, String studentId) {
        try {
            Connection connection = Database.getConnection();
            PreparedStatement statement = connection.prepareStatement("""
                    insert into issued_books (book_id, student_name)
                    select books.book_id, students.name
                    from books, students
                    where books.title = ? and books.author_name = ? and students.id = ?;
                    """);
            statement.setString(1, bookTitle);
            statement.setString(2, author);
            statement.setString(3, studentId);
            statement.executeUpdate();

            statement.close();
            connection.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    static void readAllIssuedBooks() {
        try {
            Connection connection = Database.getConnection();
            PreparedStatement statement = connection.prepareStatement("select * from issued_books");
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                String bookId = resultSet.getString("book_id");
                String studentName = resultSet.getString("student_name");
                Date issuedDate = resultSet.getDate("issued_date");

                System.out.println("Book index: " + bookId + " // " +  "Student name: " + studentName + " // "
                        + "Issued date: " + issuedDate);
            }
            resultSet.close();
            statement.close();
            connection.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    static void deleteIssuedBook(String bookId, String studentName) {
        try {
            Connection connection = Database.getConnection();
            PreparedStatement statement = connection.prepareStatement("delete from issued_books where book_id = ? and student_name = ?");
            statement.setString(1, bookId);
            statement.setString(2, studentName);
            statement.executeUpdate();

            statement.close();
            connection.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }


}
