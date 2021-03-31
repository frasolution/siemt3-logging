package com.siemt3.watchdog_server.condb;

import com.siemt3.watchdog_server.model.Alert;
import com.siemt3.watchdog_server.model.Threshold;

import java.sql.*;
import java.util.ArrayList;

import static com.siemt3.watchdog_server.CredentialsFile.URL;
import static com.siemt3.watchdog_server.CredentialsFile.USER;
import static com.siemt3.watchdog_server.CredentialsFile.PASSWORD;


public class DataBase {

    public static ArrayList<Threshold> fetch(){

        try{
            Connection myConn = DriverManager.getConnection(URL, USER, PASSWORD);
            Statement myStatement = myConn.createStatement();
            String sqlStatement = "select * from thresholds";
            ResultSet rs = myStatement.executeQuery(sqlStatement);
            ArrayList <Threshold> al = new ArrayList<Threshold>();
            while(rs.next()){
                String name = rs.getString("name");
                String type = rs.getString("type");
                int number = rs.getInt("number");

                al.add(new Threshold(name, type, number));
            }
            return al;
        }catch(Exception e){
            e.printStackTrace();
        }
        return new ArrayList<Threshold>(0);
    }

    public static void dbCommit(Alert alert) throws SQLException {
        String eventType = alert.getEventType();
        String eventName = alert.getEventName();
        long unix_time = alert.getUnix_time();
        int priority = alert.getPriority();
        String customData = alert.getCustomData();

        //TODO make sessions

        try {
            Connection myConn = DriverManager.getConnection(URL, USER, PASSWORD);
            String ps = "insert into alerts (event_type, event_name, priority, custom_data, date) values ( ? , ? , ? , ?, FROM_UNIXTIME(?) )";
            PreparedStatement myPreparedStatement = myConn.prepareStatement(ps);
            myPreparedStatement.setString(1, eventType);
            myPreparedStatement.setString(2, eventName);
            myPreparedStatement.setInt(3, priority);
            myPreparedStatement.setString(4, customData);
            myPreparedStatement.setLong(5, unix_time);
            myPreparedStatement.executeUpdate();

        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    public static long current_time(){
        return (long)(System.currentTimeMillis() / 1000);
    }
}
