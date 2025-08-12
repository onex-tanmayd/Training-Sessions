package file;

import dto.User;

import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class FileReaderUtil {

    private static final String JDBC_URL = "jdbc:postgresql://localhost:5432/fileio_db";
    private static final String JDBC_USER = "postgres"; // change if needed
    private static final String JDBC_PASSWORD = "root"; // change if needed

    public static void printfile(String filePath) {
        List<User> users = readCsv(filePath);
        insertIntoDatabase(users);
    }

    private static List<User> readCsv(String filePath) {
        List<User> users = new ArrayList<>();
        try (var reader = new java.io.BufferedReader(new java.io.FileReader(filePath))) {
            reader.lines()
                    .skip(1) // skip header
                    .map(FileReaderUtil::getUserDetailList)
                    .map(FileReaderUtil::createdtofromdetail)
                    .forEach(users::add);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return users;
    }

    private static List<String> getUserDetailList(String userDetail) {
        return List.of(userDetail.split(","));
    }

    private static User createdtofromdetail(List<String> userDetailList) {
        return new User(
                Integer.parseInt(userDetailList.get(0)),
                userDetailList.get(1),
                userDetailList.get(2)
        );
    }

    private static void insertIntoDatabase(List<User> users) {
        String sql = "INSERT INTO users (id, name, email) VALUES (?, ?, ?)";

        try (Connection conn = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD);
             var stmt = conn.prepareStatement(sql)) {

            for (User user : users) {
                stmt.setInt(1, user.id());
                stmt.setString(2, user.name());
                stmt.setString(3, user.email());
                stmt.addBatch();
            }

            stmt.executeBatch();
            System.out.println("Users inserted successfully!");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Export table data to CSV
    public static void exportToCsv(String outputFilePath) {
        String query = "SELECT id, name, email FROM users";

        try (Connection conn = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD);
             Statement stmt = conn.createStatement();       // Make a query executor
             ResultSet rs = stmt.executeQuery(query);       // Run the SELECT and get rows

             FileWriter writer = new FileWriter(outputFilePath)) {

            // Write header
            writer.append("id,name,email\n");

            // Write rows
            while (rs.next()) {
                writer.append(rs.getInt("id") + ",")
                        .append(rs.getString("name") + ",")
                        .append(rs.getString("email") + "\n");
            }

            System.out.println("Data exported to " + outputFilePath);

        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
    }
}
