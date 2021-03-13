package com.siemt3.watchdog_server.condb;

import java.sql.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class App {
    public static void dbCommit(String test) throws SQLException {
        //TODO make sessions
        //TODO make prefab statement
        //TODO make proper deconstruct function
        String url = "jdbc:mysql://localhost:3306/SIEM?createDatabaseIfNotExist=true&serverTimezone=UTC";
        String user = "root";
        String password = "Asdfasdf6634";
        try {
            Connection myConn = DriverManager.getConnection(url, user, password);
            Statement myStatement = myConn.createStatement();
            long timestamp = current_time();
            //errors like alerts can and column names can be ignored here because intelij does not read the data source properly
            myStatement.executeUpdate("insert into alerts (event_id, event_type, event_name, priority, custom_data, date) values ('TEST', 'TEST','TEST', 1 , '" + test + "', FROM_UNIXTIME(" + timestamp + ") )");
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    public static long current_time(){
        return (long)(System.currentTimeMillis() / 1000);
    }
}
