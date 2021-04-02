package  mobile.WSmobile_money.tiavina;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import  mobile.WSmobile_money.utils.Helper;


public class Statistiques {
    String numero;
    double sommeMoney;
    double sommeCredit;

    public String getNumero() {
        return numero;
    }

    public double getSommeMoney() {
        return sommeMoney;
    }

    public double getSommeCredit() {
        return sommeCredit;
    }

    public Statistiques() {
    }

    public Statistiques(String numero, double sommeMoney, double sommeCredit) {
        this.numero = numero;
        this.sommeMoney = sommeMoney;
        this.sommeCredit = sommeCredit;
    }
    
    public static Statistiques[] getFavorisClients() throws Exception{
        
        Connection con =null;
        Statement st=null;
        List<Statistiques> retourn = new ArrayList<Statistiques>();
        
        try{
            con = Helper.getConnection();
            String req="select * from clientFavoris";
            System.out.println("reqclientFavoris = "+ req);
            st=con.createStatement();
            ResultSet res=st.executeQuery(req);
            while(res.next()){
                retourn.add(new Statistiques(res.getString(1),res.getDouble(2),res.getDouble(3)));
            }
        }
        catch(Exception e){
            e.printStackTrace();
            throw new Exception("erreur get Client Favoris");
        }
        return retourn.toArray(new Statistiques[retourn.size()]); 
    }
    
}
