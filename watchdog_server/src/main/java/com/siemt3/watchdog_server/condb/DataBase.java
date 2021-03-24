package com.siemt3.watchdog_server.condb;

import com.siemt3.watchdog_server.model.Alert;
import com.siemt3.watchdog_server.model.Threshold;

import java.sql.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

public class DataBase {

    public static ArrayList<Threshold> fetch(){
        String url = "jdbc:mysql://localhost:3306/SIEM?createDatabaseIfNotExist=true&serverTimezone=UTC";
        String user = "root";
        String password = "Asdfasdf6634";
        try{
            Connection myConn = DriverManager.getConnection(url, user, password);
            Statement myStatement = myConn.createStatement();
            String sqlStatement = "select * from thresholds";
            ResultSet rs = myStatement.executeQuery(sqlStatement);
            ArrayList <Threshold> al = new ArrayList<Threshold>();
            while(rs.next()){
                String name = rs.getString("name");
                int number = rs.getInt("number");
                al.add(new Threshold(name,number));
            }
            return al;
        }catch(Exception e){
            e.printStackTrace();
        }
        return new ArrayList<Threshold>(0);
    }

    public static void dbCommit(Alert alert) throws SQLException {
        //TODO better destructuring alert object
        String eventType = alert.getEventType();
        String eventName = alert.getEventName();
        long unix_time = alert.getUnix_time();
        int priority = alert.getPriority();
        String customData = alert.getCustomData();

        //TODO make sessions
        //TODO make proper deconstruct function
        //TODO load credentials from external file
        String url = "jdbc:mysql://localhost:3306/SIEM?createDatabaseIfNotExist=true&serverTimezone=UTC";
        String user = "root";
        String password = "Asdfasdf6634";
        try {
            Connection myConn = DriverManager.getConnection(url, user, password);
            Statement myStatement = myConn.createStatement();
            //TODO make prepared statement
            //errors like alerts can and column names can be ignored here because intelij does not read the data source properly
            myStatement.executeUpdate("insert into alerts (event_type, event_name, priority, custom_data, date) values ('"+eventType+"','"+eventName+"', "+priority+" , '" + customData + "', FROM_UNIXTIME(" + unix_time + ") )");
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    public static void dbCommit(String test) throws SQLException {
        //TODO make sessions
        //TODO make prepared statement
        //TODO make proper deconstruct function
        //TODO load credentials from external file
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
