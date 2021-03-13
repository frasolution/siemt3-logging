package com.siemt3.watchdog_server.condb;

import java.sql.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class App {
    public static void main(String[] args) throws SQLException {
        String url = "jdbc:mysql://localhost:3306/SIEM?createDatabaseIfNotExist=true&serverTimezone=UTC";
        String user = "root";
        String password = "Asdfasdf6634";
        try {
            Connection myConn = DriverManager.getConnection(url, user, password);
            Statement myStatement = myConn.createStatement();
            long timestamp = current_time();
            myStatement.executeUpdate("insert into alerts (event_id, event_type, event_name, priority, custom_data,date) values ('TEST', 'TEST','TEST', 1 , 'TEST', FROM_UNIXTIME("+ timestamp +") )");
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    public static long current_time(){
        return (long)(System.currentTimeMillis() / 1000);
    }
}
