package  mobile.WSmobile_money.utils;

import java.sql.Connection;
import java.sql.DriverManager;

public class Helper {
    
    public static Connection getConnection()throws Exception {
        try
        {
            Class.forName("org.postgresql.Driver");
            String url="jdbc:postgresql://localhost:5432/mobile_money";
            String user="postgres";
            String password="123456"; 
            Connection c=DriverManager.getConnection(url,user,password);
            //c.setAutoCommit(false);
            //System.out.println("Connecte");
            return c;
        }
        
        catch(Exception e)
        {
            e.printStackTrace();
            return null;
        }
    }
}