/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package  mobile.WSmobile_money.utils;

import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author P12A-53-TIAVINA
 */
public class Function {
    
    public static String[] getColonne(String table) throws Exception
        {
             List<String> col=new ArrayList<String>();
            Connection con = null;
            Statement st=null;
            try{
            Helper conect = new Helper();
            con = conect.getConnection();
            String req = "SELECT COLUMN_NAME from INFORMATION_SCHEMA.Columns WHERE Table_name='" + table + "' ";
            st=con.createStatement();
            ResultSet res=st.executeQuery(req);
            System.out.print(req); 
            while(res.next()){
               
                col.add(res.getString(1));
                  
            }  
            }
            catch(Exception e){
            throw new Exception ("Errueur get colonne--->function.");
            }
            finally{
                con.close();
                st.close();
            }
            String[] retourn=new String[col.size()-1];
            String[] cols=col.toArray(new String[col.size()]);
            
            for(int i=0;i<retourn.length;i++){
                retourn[i]=cols[i+1];
            }
            
            return retourn;
        }
     
     public static Timestamp setDaty(String date)
    {
        String[] espace=date.split(" ");
        String[] dar=espace[0].split("-");
        int y=Integer.parseInt(dar[0])-1900;
        int v=Integer.parseInt(dar[1])-1;
        int d=Integer.parseInt(dar[2]);
        String[] lera=espace[1].split(":");
        int h=Integer.parseInt(lera[0]);
        int m=Integer.parseInt(lera[1]);
        int s=Integer.parseInt(lera[2]);
        return new Timestamp(y,v,d,h,m,s,0);
    }
     
    public static void Update(String colonne,String donne,String table,String condition,String equals) throws Exception{
     
        String req0 = "update "+table+" set ";
        String virj = ",";
        String col = " ";
        String cot = "'";
        String egal = "=";

        if(colonne.contains(".") == true) col = col +colonne+egal+donne;
        else col=col +colonne+egal+cot+donne+cot;

        String conditions=" where "+condition+" = '"+equals+"';";
        String retourn=req0 + col+conditions;
        System.out.print(retourn);

        Connection con = null;
        Statement st = null;
        
        try{
            con = Helper.getConnection();
            st = con.createStatement();
            st.executeUpdate(retourn);
        }
        catch(Exception e){
            throw new Exception("erreur insert with connection function");
        }  
        finally{      
            st.close();
            con.close();
        }     
    }
     
    public static void UpdateWithConnection(Connection con,String colonne,String donne,String table,String condition,String equals) throws Exception{
     
        String req0 = "update "+table+" set ";
        String virj = ",";
        String col = " ";
        String cot = "'";
        String egal = "=";

        if(donne.contains("+") == true || donne.contains("-") == true) col = col +colonne+egal+donne;
        else col=col +colonne+egal+cot+donne+cot;

        String conditions=" where "+condition+" = '"+equals+"';";
        String requete = req0 + col+conditions;
        System.out.println("RQ UPDATE W/ CONNECTION= "+requete);

        Statement st=null;
        try{
            st=con.createStatement();
            st.executeUpdate(requete);
        }
        catch(Exception e){
            throw new Exception("erreur update w/co function");
        }  
        finally{
            st.close();
        }     
    }
      
    public static boolean testDouble(String args){
        try{
            Double.parseDouble(args);
        }
        catch(NumberFormatException ex){
            return false;
        }
        return true;
    }
     
     public static void UpdateAll(String[] colonne,String[] donne,String table,String condition,String equals) throws Exception{
        
        String req0="update "+table+" set ";
        String virj=",";
        String col=" ";
        String cot=" '";
        String egal="=";
        for(int i=0;i<colonne.length;i++){
            if(i==colonne.length-1){
                 if(Function.testDouble(donne[i])==true) col=col + colonne[i]+egal+donne[i];
                 else col=col + colonne[i]+egal+cot+donne[i]+cot;
            }
            else{
                 if(Function.testDouble(donne[i])==true) col=col + colonne[i]+egal+donne[i]+virj;   
                 else  col=col + colonne[i]+egal+cot+donne[i]+cot+virj;

            }
        }
        String conditions=" where "+condition+"='"+equals+"';";
        String retourn=req0 + col+conditions;
        System.out.print(retourn);
        Connection con =null;

        Statement st=null;
        try{
            con = Helper.getConnection();

            st=con.createStatement();
            st.executeUpdate(retourn);
        }
        catch(Exception e){
           throw new Exception("erreur Update ALL");
        }  
        finally{
            con.close();
            st.close();
        }
    }
     
     public static void inserer(String[] colonne,String[] donne,String table) throws Exception {
            
        String req0 = "insert into ";
        String req1 = " (";
        String req2 = ") ";
        String req3 = "values";

        String virj = ",";
        String col = " ";
        int k = colonne.length;
        for (int i = 0; i < k; i++) {
            if (i == k - 1)
            {
                col = col + colonne[i];
            }
            else
            {
                col = col + colonne[i] + virj;
            }
        }
        String value = " ";
        int t = donne.length;
        for (int j = 0; j < t; j++) {
            if (j == t - 1)
            {
                value = value + "'" +donne[j] +"'";
            }
            else {
                value = value + "'" +donne[j] +"'"+virj;
            }  
        }
        
        String repColonne = req0 + table + req1 + col + req2 + req3 + req1 + value + req2 + ";";
        Connection con =null;
        Statement st=null;
        
        try{
            con = Helper.getConnection();
            System.out.println(repColonne);
            st=con.createStatement();
            st.executeUpdate(repColonne);
        }
        catch(Exception e){
            throw new Exception("erreur insert function");
        }  
        finally{
            con.close();
            st.close();
        }
    }
     
     public static void insertWithConnection(Connection co,String[] colonne,String[] data,String table) throws Exception {
         
        String req0 = "insert into ";
        String req1 = " (";
        String req2 = ") ";
        String req3 = "values";

        String virj = ",";
        String col = " ";
        int k = colonne.length;
        for (int i = 0; i < k; i++) {
            if (i == k - 1)
            {
                col = col + colonne[i];
            }
            else
            {
                col = col + colonne[i] + virj;
            }
        }
        String value = " ";
        int taille = data.length;
        for (int j = 0; j < taille; j++) {
            if (j == taille - 1)
            {
                if(data[j].contains("now()") == true) value = value + data[j];
                else value = value + "'" +data[j] +"'";
            }
            else {
                if(data[j].contains("now()") == true) value = value + data[j] + virj;
                else value = value + "'" +data[j] +"'"+virj;
            }  
        }
        
        String requeteFinale = req0 + table + req1 + col + req2 + req3 + req1 + value + req2 + ";";
        Statement st=null;
        
        try{
            st = co.createStatement();
            System.out.println("ReqFinale = "+requeteFinale);
            st.executeUpdate(requeteFinale);
        }
        catch(Exception e){
            e.printStackTrace();
            throw new Exception("Erreur insert Avec Connection function");
        }  
        finally{
            //Close statement seulement
            st.close();
        }
    }
    
    public static String[] getColonneWithConnection(Connection con,String table) throws Exception
    {
        List<String> col=new ArrayList<String>();
        Statement st=null;

        try{
            Helper conect = new Helper();
            con = conect.getConnection();
            String req = "SELECT COLUMN_NAME from INFORMATION_SCHEMA.Columns WHERE Table_name='" + table + "' ";
            st=con.createStatement();
            ResultSet res=st.executeQuery(req);
            System.out.print(req); 
            while(res.next()){
                col.add(res.getString(1));
            }  
        }
        catch(Exception e){
            throw new Exception ("Errueur get colonne w/ connection --->function.");
        }
        finally{
            con.close();
            st.close();
        }

        String[] retourn = col.toArray(new String[col.size()]);

        return retourn;
    }
     
}
