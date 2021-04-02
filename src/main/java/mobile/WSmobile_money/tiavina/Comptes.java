/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mobile.WSmobile_money.tiavina;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import  mobile.WSmobile_money.tonny.Offre;
import  mobile.WSmobile_money.utils.Function;
import  mobile.WSmobile_money.utils.Helper;

/**
 *
 * @author Tiavina
 */
public class Comptes {
    int id_utilisateur;
    double credit;
    double solde;
    String type_offre;
    String valeur_offre;
    Timestamp date_expiration;
    Timestamp date_solde;

    public int getId_utilisateur() {
        return id_utilisateur;
    }

    public double getCredit() {
        return credit;
    }

    public double getSolde() {
        return solde;
    }
    
    public Comptes() {
    }

    public String getType_offre() {
        return type_offre;
    }

    public void setType_offre(String type_offre) {
        this.type_offre = type_offre;
    }

    public String getValeur_offre() {
        return valeur_offre;
    }

    public void setValeur_offre(String valeur_offre) {
        this.valeur_offre = valeur_offre;
    }

    public Timestamp getDate_expiration() {
        return date_expiration;
    }

    public void setDate_expiration(Timestamp date_expiration) {
        this.date_expiration = date_expiration;
    }

    public Timestamp getDate_solde() {
        return date_solde;
    }

    public void setDate_solde(Timestamp date_solde) {
        this.date_solde = date_solde;
    }

    public Comptes(int id_utilisateur, double credit, double solde, String type_offre, String valeur_offre, Timestamp date_expiration,Timestamp date_solde) {
        this.id_utilisateur = id_utilisateur;
        this.credit = credit;
        this.solde = solde;
        this.type_offre = type_offre;
        this.valeur_offre = valeur_offre;
        this.date_expiration = date_expiration;
        this.date_solde = date_solde;
    }
    
    public static boolean verifierSoldeCompte(Comptes compte,double montant){
        
        if(compte.getSolde()<montant){
            return false;
        }
        return true;
    }
    
     public static boolean verifierCreditCompte(Comptes compte,double montant){
        
        if(compte.getCredit()<montant){
            return false;
        }
        return true;
    }
     
    public static Comptes getLastSoldeCreditUser(Connection c, int id) throws SQLException, Exception{
        
        Statement s = null;
        Comptes compte;
        
        try{
            s = c.createStatement();
            String requete = "SELECT * FROM comptes WHERE id_utilisateur = "+id+" ORDER BY date_solde DESC LIMIT 1";
            ResultSet res = s.executeQuery(requete);
            res.next();
            compte = new Comptes(res.getInt(1),res.getDouble("credit"),res.getDouble("solde"),"Aucun offre"," ",Timestamp.valueOf(res.getString("date_expiration")),Timestamp.valueOf(res.getString("date_solde")));
            return compte;
        }
        catch(Exception e){
            e.printStackTrace();
            throw new Exception("getLastSoldeCreditUser Exception");
        }
        finally{
            s.close();
        }
    } 
     
    public static boolean verifierSoldeFromId(Connection c,int id, double montant) throws SQLException, Exception{
        
        try{
            Comptes lastSolde = getLastSoldeCreditUser(c,id);
            System.out.println("lastSolde= "+lastSolde.getSolde());
            System.out.println("montant= "+montant);
            if(lastSolde.getSolde() > montant) return true;
            else return false;
        }
        catch(Exception e){
            throw new Exception("verifierSoldeFromId Exception");
        }
    }
    
    public static boolean verifierCreditFromId(Connection c,int id, double montant) throws SQLException, Exception{
        
        try{
            Comptes lastCredit = getLastSoldeCreditUser(c,id);

            if(lastCredit.getCredit() > montant) return true;
            else return false;
        }
        catch(Exception e){
            e.printStackTrace();
            throw new Exception("Exception verifierCreditFromId");
        }
    }
    
    public static void addCredit(int id_utilisateur,double montant) throws Exception{
            
        System.gc();
        Connection co = null;
        
        try{
            co = Helper.getConnection();
            co.setAutoCommit(false);

            System.out.println(id_utilisateur+" x "+montant);
            if(Comptes.verifierSoldeFromId(co, id_utilisateur, montant) == true){

                LocalDateTime nw=LocalDateTime.now();
                String[] colonne=Function.getColonne("mouvement_credit");
                String[] colonneNoId = Arrays.copyOfRange(colonne,0,(colonne.length));

                String[] data={
                    ""+id_utilisateur,
                    "creditation",
                    "999",
                    ""+montant,
                    ""+nw
                };
            
                try{
                    Function.insertWithConnection(co,colonneNoId, data, "mouvement_credit");
                    //double solde=compte.getSolde() - montant;
                    Function.UpdateWithConnection(co, "solde", "solde - "+montant, "comptes", "id_utilisateur", ""+id_utilisateur);
                    //double credit = compte.getCredit() + montant;
                    Function.UpdateWithConnection(co, "credit", "credit + "+montant, "comptes", "id_utilisateur", ""+id_utilisateur);

                    co.commit();
                }
                catch(Exception e){

                    co.rollback();
                    e.printStackTrace();
                    throw new Exception("TransactionAddCreditFail");
                }
                finally{
                    co.close();
                }
            }
            else{
                throw new Exception("depot insuffisant");
            }
        }
        catch(Exception e){
            e.printStackTrace();
            throw new Exception("Exception 1");
        }
    }
    
    public static Comptes findCompteById(int id) throws Exception{
       
        Comptes retour=null;
        Connection con =null;
        Statement st=null;
       
        try{
            con = Helper.getConnection();
            String req = "select * from comptes where id_utilisateur ="+id;
            st = con.createStatement();
            ResultSet res = st.executeQuery(req);
            res.next();
            retour = new Comptes(res.getInt(1),res.getDouble(2),res.getDouble(3),res.getString(4),res.getString(5),Timestamp.valueOf(res.getString("date_expiration")),Timestamp.valueOf(res.getString("date_solde")));
        }
        catch(Exception e){
           
            if(retour == null){
                throw new Exception("Compte introuvable");
            }
           
            e.printStackTrace();
            throw new Exception("Erreur find Comptes from colonne");
        }
        finally{
            con.close();
            st.close();
        }
        return retour;
    }  

    public static Comptes findCompteFromColonne(String colonne,String value) throws Exception{
       
        Comptes retour=null;
        Connection con =null;
        Statement st=null;
       
        try{
            con = Helper.getConnection();
            String req="select * from comptes where "+colonne+"='"+value+"'";
            st=con.createStatement();
             //System.out.print(req);
            ResultSet res=st.executeQuery(req);
            res.next();
            retour = new Comptes(res.getInt(1),res.getDouble(2),res.getDouble(3),res.getString(4),res.getString(5),Timestamp.valueOf(res.getString("date_expiration")),Timestamp.valueOf(res.getString("date_solde")));
        }
        catch(Exception e){
           
           if(retour == null){
               throw new Exception("Compte introuvable");
           }
           
           e.printStackTrace();
           throw new Exception("Erreur find Comptes from colonne");
        }
        finally{
           con.close();
           st.close();
        }
        return retour;
    } 
    
    public static Comptes[] getDataMonCompte(int id_user) throws Exception{
       
        Connection con =null;
        Statement st=null;
        List<Comptes> list = new ArrayList<Comptes>();
       
        try{
            con = Helper.getConnection();
            String req="select * from dataCompteValide where id_utilisateur ="+id_user;
            System.out.println("reqMonCompte = "+ req);
            st=con.createStatement();
             //System.out.print(req);
            ResultSet res=st.executeQuery(req);
            while(res.next()){
                list.add(new Comptes(res.getInt(1),res.getDouble(2),res.getDouble(3),res.getString(4),Offre.getValeurEnPhrase(res.getString("valeur_offre")),Timestamp.valueOf(res.getString("date_expiration")),Timestamp.valueOf(res.getString("date_solde"))));
            }
            
            if(list.size() == 0){
                list.add(getLastSoldeCreditUser(con,id_user));
            }
            
            return list.toArray(new Comptes[list.size()]);
        }
        catch(Exception e){

           
           e.printStackTrace();
           throw new Exception("Erreur get Data Compte");
        }
        finally{
           con.close();
           st.close();
        }
    } 
}

    /*
    public static Comptes[] getSumCredit() throws Exception{
            
        List<Comptes> retour =new ArrayList<Comptes>();
        Connection con = null;
        Statement st=null;
        
        try{
            Helper conect = new Helper();
            con = conect.getConnection();
            String req = "select * from Sumcredit order by credit desc ";
            st=con.createStatement();
            ResultSet res=st.executeQuery(req);
            System.out.print(req); 
            while(res.next()){
                retour.add(new Comptes(res.getInt(1),res.getDouble(2),res.getDouble(3),res.getString(4),res.getDouble(5),Timestamp.valueOf(res.getString("date_expiration")),Timestamp.valueOf(res.getString("date_solde"))));
            }  
        }
        catch(Exception e){
            throw new Exception ("Errueur getSumCredit--->function.");
        }
        finally{
            con.close();
            st.close();
        }
        
        return retour.toArray(new Comptes[retour.size()]);
    }
    
    public static Comptes[] getSumSolde() throws Exception{
        
        List<Comptes> retour =new ArrayList<Comptes>();
        Connection con = null;
        Statement st=null;
        try{
            Helper conect = new Helper();
            con = conect.getConnection();
            String req = "select * from SumDepot order by solde desc ";
            st=con.createStatement();
            ResultSet res=st.executeQuery(req);
            System.out.print(req); 
            while(res.next()){
                retour.add(new Comptes(res.getInt(1),res.getDouble(2),res.getDouble(3),res.getString(4),res.getDouble(5),Timestamp.valueOf(res.getString("date_expiration")),Timestamp.valueOf(res.getString("date_solde"))));
            }  
        }
        catch(Exception e){
            throw new Exception ("Errueur getSumSolde--->function.");
        }
        finally{
            con.close();
            st.close();
        }
        
        return retour.toArray(new Comptes[retour.size()]);
    }
    
    public static Comptes[] getSumTotal() throws Exception{
        List<Comptes> retourn =new ArrayList<Comptes>();
        Connection con = null;
        Statement st=null;
        try{
            Helper conect = new Helper();
            con = conect.getConnection();
            String req = "select sumtotal. *,(credit+solde)as total from sumtotal order by total desc ";
            st=con.createStatement();
            ResultSet res=st.executeQuery(req);
            System.out.print(req); 
            while(res.next()){
                retourn.add(new Comptes(res.getInt(1),res.getDouble(2),res.getDouble(3),res.getString(4),res.getDouble(5),Timestamp.valueOf(res.getString("date_expiration")),Timestamp.valueOf(res.getString("date_solde"))));
            }  
        }
        catch(Exception e){
            throw new Exception ("Errueur getSumTotal--->function.");
        }
        finally{
            con.close();
            st.close();
        }
        return retourn.toArray(new Comptes[retourn.size()]);
    }

    */
