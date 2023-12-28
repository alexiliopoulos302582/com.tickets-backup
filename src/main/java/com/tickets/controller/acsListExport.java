package com.tickets.controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;

public class acsListExport {


    public static void exportacsList() {
        // JDBC connection parameters
        String url = "jdbc:mysql://localhost:3306/tickets";
        String user = "root";
        String password = "113718417";
        LocalDate currentDate = LocalDate.now();
        // SQL query
        String query = "SELECT 'id', 'acs_customer_id', 'acsafm', 'acs_address', 'acs_city', 'acs_client_name', 'acs_email', 'acs_phone_number' " +
                "UNION "+
                "SELECT * FROM tickets.acs_customer " +
                "INTO OUTFILE 'C:/ProgramData/MySQL/MySQL Server 8.0/Uploads/acs_list_"+ currentDate +".txt' " +
                "CHARACTER SET utf8 "+
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
