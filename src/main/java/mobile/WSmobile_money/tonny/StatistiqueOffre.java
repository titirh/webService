package  mobile.WSmobile_money.tonny;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import  mobile.WSmobile_money.utils.Helper;

public class StatistiqueOffre {
    
    private Offre offre;
    private double nombre;

    public Offre getOffre() {
        return offre;
    }

    public void setOffre(Offre offre) {
        this.offre = offre;
    }

    public double getNombre() {
        return nombre;
    }

    public void setNombre(double nombre) {
        this.nombre = nombre;
    }
    
    public StatistiqueOffre(){}

    public StatistiqueOffre(Offre offre, double nombre) {
        this.offre = offre;
        this.nombre = nombre;
    }
    
    public StatistiqueOffre[] getOffreFavoris() throws Exception {
        
        Connection c = null;
        Statement st = null;
        ResultSet res = null;
        List<StatistiqueOffre> list = new ArrayList<StatistiqueOffre>();
        
        try{
            c = Helper.getConnection();
            c.setAutoCommit(true);
            
            String req = String.format("SELECT * FROM OffresFavorisCredit");
            
            st = c.createStatement();
            res = st.executeQuery(req);
            
            while(res.next()){
                list.add(new StatistiqueOffre(new Offre(res.getInt("id_offre"),res.getString("nom"),res.getDouble("montant"),res.getString("type"),res.getString("valeur"),res.getString("code")),res.getDouble("occurence")));
            }
            return list.toArray(new StatistiqueOffre[list.size()]);
        }
        catch(Exception e){
            e.printStackTrace();
            throw new Exception("get Offre Favoris Exception");
        }
        finally{
            st.close();
            c.close();
        }
    }
}
