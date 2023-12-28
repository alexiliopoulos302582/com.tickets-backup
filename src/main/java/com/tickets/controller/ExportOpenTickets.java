package com.tickets.controller;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;

public class ExportOpenTickets {
    public static void exportOpenTickets() {
        // JDBC connection parameters
        String url = "jdbc:mysql://localhost:3306/tickets";
        String user = "root";
        String password = "root";
        LocalDate currentDate = LocalDate.now();
        // SQL query
        String query = "SELECT 'id', 'AFM', 'assigned_to', 'category', 'city', 'customer_id', 'department', 'device_model', 'email', 'help_topic', " +
                "'issue', 'phone_number', 'priority', 'response', 'serial_number', 'solution', 'solution_type', 'subject', 'ticket_source', 'ticket_state', 'user_name', 'creation_date','address','client_name' "+
                "UNION " +
                "SELECT * FROM tickets.ticket WHERE ticket_state = 1 " +
                "INTO OUTFILE 'C:/ProgramData/MySQL/MySQL Server 8.0/Uploads/open_export_"+ currentDate +".csv' " +
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
