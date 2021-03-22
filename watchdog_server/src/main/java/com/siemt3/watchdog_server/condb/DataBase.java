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

    public static HashMap<String, ArrayList<Threshold>> fetch(){
        String url = "jdbc:mysql://localhost:3306/SIEM?createDatabaseIfNotExist=true&serverTimezone=UTC";
        String user = "root";
        String password = "Asdfasdf6634";
        try{
            Connection myConn = DriverManager.getConnection(url, user, password);
            Statement myStatement = myConn.createStatement();
            String sqlStatement = "select * from thresholds";
            ResultSet rs = myStatement.executeQuery(sqlStatement);
            ArrayList <Threshold> test = new ArrayList<Threshold>();
            ArrayList <Threshold> apache2 = new ArrayList<Threshold>();
            ArrayList <Threshold> port = new ArrayList<Threshold>();
            ArrayList <Threshold> ssh = new ArrayList<Threshold>();
            while(rs.next()){
                String name = rs.getString("name");
                String type = rs.getString("type");
                int count = rs.getInt("count");
                int time = rs.getInt("time");

                switch(type){
                    case "test":
                        test.add(new Threshold(name,count,time));
                        break;
                    case "apache2":
                        apache2.add(new Threshold(name,count,time));
                        break;
                    case "port":
                        port.add(new Threshold(name,count,time));
                        break;
                    case "ssh":
                        ssh.add(new Threshold(name,count,time));
                        break;
                    default:
                        break;
                }
            }
            HashMap<String, ArrayList<Threshold>> hm = new HashMap<String, ArrayList<Threshold>>();
            hm.put("test", test);
            hm.put("apache2", apache2);
            hm.put("port", port);
            hm.put("ssh", ssh);
            return hm;
        }catch(Exception e){
            e.printStackTrace();
        }
        return new HashMap<String, ArrayList<Threshold>>();
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
