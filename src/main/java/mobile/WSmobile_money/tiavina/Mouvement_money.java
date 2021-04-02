package  mobile.WSmobile_money.tiavina;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import  mobile.WSmobile_money.utils.Function;
import  mobile.WSmobile_money.utils.Helper;

public class Mouvement_money {
     int id_mouvement ;
     int id_compte ;
     String type ;
     double montant ;
     String date_mouvement ;
     String status;

    public int getId_mouvement() {
        return id_mouvement;
    }

    public int getId_compte() {
        return id_compte;
    }

    public String getType() {
        return type;
    }

    public double getMontant() {
        return montant;
    }

    public String getDate_mouvement() {
        return date_mouvement;
    }

    public String getStatus() {
        return status;
    }

    public Mouvement_money() {
    }

    public Mouvement_money(int id_mouvement, int id_compte, String type, double montant, String date_mouvement, String status) {
        
        this.id_mouvement = id_mouvement;
        this.id_compte = id_compte;
        this.type = type;
        this.montant = montant;
        this.date_mouvement = date_mouvement;
        this.status = status;
    }
    
    public static void faireDepotMoney(int id_utilisateur,double montant) throws Exception{
         
        Connection c = Helper.getConnection();
        c.setAutoCommit(false);
        Statement s;
        
        try{
            LocalDateTime nw = LocalDateTime.now();
            String[] colonne = Function.getColonne("mouvement_money");
            
            String[] data={
                ""+id_utilisateur,
                "Depot",
                ""+montant,
                ""+nw,
                "En attente"
            };
            
            Function.insertWithConnection(c, colonne, data, "mouvement_money"); 
            
            c.commit();
        }
        catch(Exception e){
            c.rollback();
            e.printStackTrace();
            throw new Exception("erreur add depot");
        }
        finally{
            //s.close();
            c.close();
        }
    }
    
    
    public static Mouvement_money[] getMouvementEnAttente() throws Exception{
            
        List<Mouvement_money> retourn=new ArrayList<Mouvement_money>();
        Connection con = null;
        Statement st=null;
        
        try{
            Helper conect = new Helper();
            con = conect.getConnection();
            String req = "select * from mouvement_money where status='En attente'";
            st=con.createStatement();
            ResultSet res=st.executeQuery(req);
            while(res.next()){
                retourn.add(new Mouvement_money(res.getInt(1),res.getInt(2),res.getString(3),res.getDouble(4),res.getString(5),res.getString(6)));
            }
        }
        
        catch(Exception e){
            e.printStackTrace();
            throw new Exception("erreur get mouvementsEnAttente");
        }
        
        finally{
            st.close();
            con.close();
        }
        
        return retourn.toArray(new Mouvement_money[retourn.size()]);
    }
    
    public static void validerDepot(Mouvement_money mvt) throws Exception{
        try{
            Connection co = Helper.getConnection();
            
            Comptes compte = Comptes.findCompteFromColonne("id_utilisateur", ""+mvt.getId_compte());
            double solde = compte.getSolde() + mvt.getMontant();
            
            Function.UpdateWithConnection(co, "status", "Valide", "mouvement_money", "id_mouvement", ""+mvt.getId_mouvement());
            Function.UpdateWithConnection(co, "solde", ""+solde, "comptes", "id_utilisateur", ""+mvt.getId_compte());
        }
        catch(Exception e){
            e.printStackTrace();
            throw new Exception("Erreur valider solde");
        }
    }
     
      public static void refuserDepot(Mouvement_money mvt) throws Exception{
        try{
           Function.Update("status", "Rejete", "mouvement_money", "id_mouvement", ""+mvt.getId_mouvement());
        }
        catch(Exception e){
           e.printStackTrace();
           throw new Exception("Erreur valider solde");
        }
    }     
}
