package com.tickets.controller;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;

public class ExportSerialNumbersList_txt {
    public static void exportserialnums_txt() {
        // JDBC connection parameters
        String url = "jdbc:mysql://localhost:3306/tickets";
        String user = "root";
        String password = "root";
        LocalDate currentDate = LocalDate.now();
        // SQL query
        String query = "SELECT 'id', 'device_model', 'merchant', 'serial_number'  " +
                "UNION " +
                "SELECT * FROM tickets.device " +
                "INTO OUTFILE 'C:/ProgramData/MySQL/MySQL Server 8.0/Uploads/serial_numbers_export_"+ currentDate +".txt' " +
                "FIELDS TERMINATED BY ';' ENCLOSED BY '\"' LINES TERMINATED BY '\n';";

        // Execute the query
        try (Connection connection = DriverManager.getConnection(url, user, password);
             Statement statement = connection.createStatement()) {

            statement.execute(query);

            System.out.println("Query executed successfully. Check the file for exported data.");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
