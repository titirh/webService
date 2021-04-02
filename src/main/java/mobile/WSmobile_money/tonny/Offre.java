package  mobile.WSmobile_money.tonny;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import  mobile.WSmobile_money.tiavina.Comptes;
import  mobile.WSmobile_money.utils.Helper;

public class Offre 
{    
    int id_offre;
    String nom;
    double montant;
    String type;
    String valeur;
    String code;
    
    public int getId_offre() {
        return id_offre;
    }

    public void setId_offre(int id_offre) {
        this.id_offre = id_offre;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom_offre) {
        this.nom = nom_offre;
    }

    public double getMontant() {
        return montant;
    }

    public void setMontant(double montant) {
        this.montant = montant;
    }

    public String getType() {
        return type;
    }

    public void setType_offre(String type_offre) {
        this.type = type_offre;
    }

    public String getValeur() {
        return valeur;
    }

    public void setValeur(String valeur) {
        this.valeur = valeur;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Offre(){}
    
    public Offre(String nom_offre, double montant, String type_offre, String valeur, String code) {
        
        this.nom = nom_offre;
        this.montant = montant;
        this.type = type_offre;
        this.valeur = valeur;
        this.code = code;
    }
    
    public Offre(int id,String nom_offre, double montant, String type_offre, String valeur, String code) {
        
        this.id_offre =  id;
        this.nom = nom_offre;
        this.montant = montant;
        this.type = type_offre;
        this.valeur = valeur;
        this.code = code;
    }
    
    /// Traitement valeurs offres
    
    public static String getValeurEnPhrase(String valeur){
        double[] splitted = splitValeurOffre(valeur);
        return splittedEnPhrase(splitted);
    }
    
    public static double[] splitValeurOffre(String valeur){
        
        double[] reponse = new double[3];
        String[] splitted_str = valeur.split(";");
        for(int i = 0; i< splitted_str.length; i++){
            reponse[i] = Double.parseDouble(splitted_str[i]);
        }
        return reponse;
    }
    
    public static String splittedEnPhrase(double[] valeur){
        
        String appel = String.valueOf(valeur[0]);
        String mega = String.valueOf(valeur[1]);
        String sms = String.valueOf(valeur[2]);
        
        return String.format("%sar, %smo et %ssms",valeur[0],valeur[1],valeur[2]);
    } 
    
    public static String modifierValeurOffre(String valeurUnsplitted, double newValeur, String offreToEdit) {
        
        String reponse = new String();
        double[] splitted = splitValeurOffre(valeurUnsplitted);
        if(offreToEdit.equalsIgnoreCase("appel")){
            reponse = "" +newValeur+ ";" +splitted[1]+ ";" +splitted[2];
        }
        return reponse;
    }

    //--------------FONCTIONS--------------
    
    public static Offre[] getOffres() throws Exception {
        
        Connection c = null;
        Statement st = null;
        ResultSet res = null;
        Offre reponse= null;
        List<Offre> list = new ArrayList<Offre>();
        
        try{
            c = Helper.getConnection();
            c.setAutoCommit(true);
            
            String req ="select * from Offres";
            
            st = c.createStatement();
            res = st.executeQuery(req);
            
            while(res.next()){
                list.add(new Offre(res.getInt(1),res.getString(2),res.getDouble(3),res.getString(4),getValeurEnPhrase(res.getString("valeur")),res.getString(6)));
            }
        }
       
        catch(Exception e){
            e.printStackTrace();
            throw new Exception("get All Offres Exception");
        }
        
        
         return list.toArray(new Offre[list.size()]);
    }
    
    public static Offre getOffreById(int id_offre) throws Exception {
        
        Connection c = null;
        Statement st = null;
        ResultSet res = null;
        Offre reponse= null;
        
        try{
            c = Helper.getConnection();
            c.setAutoCommit(true);
            
            String req = String.format("SELECT * FROM Offres WHERE id_offre = '%s'",id_offre);
            
            st = c.createStatement();
            res = st.executeQuery(req);
            
            res.next();
            reponse = new Offre(id_offre,res.getString("nom"),res.getDouble("montant"),res.getString("type"),res.getString("valeur"),res.getString("code"));
            return reponse;
        }
        catch(Exception e){
            e.printStackTrace();
            throw new Exception("get Offre Exception");
        }
        finally{
            st.close();
            c.close();
        }
    }
    
    public static Offre getOffreEnPhraseById(int id_offre) throws Exception {
        
        Connection c = null;
        Statement st = null;
        ResultSet res = null;
        Offre reponse= null;
        
        try{
            c = Helper.getConnection();
            c.setAutoCommit(true);
            
            String req = String.format("SELECT * FROM Offres WHERE id_offre = '%s'",id_offre);
            
            st = c.createStatement();
            res = st.executeQuery(req);
            
            res.next();
            reponse = new Offre(id_offre,res.getString("nom"),res.getDouble("montant"),res.getString("type"),Offre.getValeurEnPhrase(res.getString("valeur")),res.getString("code"));
            return reponse;
        }
        catch(Exception e){
            e.printStackTrace();
            throw new Exception("get Offre Exception");
        }
        finally{
            st.close();
            c.close();
        }
    }
    
    public static void insertOffre(Offre offre) throws Exception {
        
        Connection c = null;
        Statement st = null;
        
        try{
            c = Helper.getConnection();
            c.setAutoCommit(true);
            
            String req = String.format("Insert into Offres(nom,montant,type,valeur,code) VALUES('%s',%s,'%s','%s','%s')",
                        offre.getNom(),offre.getMontant(),offre.getType(),offre.getValeur(),offre.getCode());
            
            st = c.createStatement();
            st.executeUpdate(req);
        }
        catch(Exception e){
            e.printStackTrace();
            throw new Exception("Insert Offre Exception");
        }
        finally{
            st.close();
            c.close();
        }
    }
    
    public static void deleteOffre(int id_offre) throws Exception {
        
        Connection c = null;
        Statement st = null;
        
        try{
            c = Helper.getConnection();
            c.setAutoCommit(true);
            
            String req = String.format("Delete from offres where id_offre = '%s'",id_offre);
            
            st = c.createStatement();
            st.executeUpdate(req);
        }
        catch(Exception e){
            e.printStackTrace();
            throw new Exception("DELETE Offre Exception");
        }
        finally{
            st.close();
            c.close();
        }
    }
    
    public static void updateOffre(Offre offre) throws Exception {
        
        Connection c = null;
        Statement st = null;
        
        try{
            c = Helper.getConnection();
            c.setAutoCommit(true);
            
            String req = String.format("Update Offres SET nom = '%s' , montant = %s , type = '%s' , valeur =  '%s'"
                        + " , code = '%s' WHERE id_offre = '%s'",offre.getNom(),offre.getMontant(),offre.getType(),
                        offre.getValeur(),offre.getCode(),offre.getId_offre());
            System.out.print(req);      
            
            st = c.createStatement();
            st.executeUpdate(req);
        }
        catch(Exception e){
            e.printStackTrace();
            throw new Exception("Update Offre Exception");
        }
        finally{
            st.close();
            c.close();
        }
    }
    
    //Type= money ou credit
    public static void acheterOffre(int id_utilisateur,int id_offre,String type) throws Exception {
        
        Connection c = null;
        Statement st = null;
        
        try{
            c = Helper.getConnection();
            c.setAutoCommit(false); //TRANSACTION
            
            Comptes compte = getCurrentSoldeCompte(c,id_utilisateur);
            Offre offreToBuy = getOffreById(id_offre);
            
            System.out.println("OFFRE TO BUY = "+offreToBuy.getValeur());
            
            st = c.createStatement(); 
            
            try{
                //Credit-- Offre++ [type = credit]
                //Solde-- Credit- Offre++ [type = credit]   
                String req1 = null;
                String req2 = null;
                
                //ACHAT PAR MOBILE MONEY
                if(type.equalsIgnoreCase("money") == true){    
                    
                    if(Comptes.verifierSoldeCompte(compte,offreToBuy.getMontant()) == false){
                        throw new Exception("Solde Insuffisant pur l'achat de cet offre");
                    }
                    
                    req1 = String.format("Insert into Mouvement_money(id_utilisateur,type,montant,date_mouvement,status)"
                            + " VALUES(%s,'Achat Offre',%s,now(),'Valide')",id_utilisateur,offreToBuy.getMontant());
                    
                    req2 = String.format("Update Comptes SET Solde = %s - %s WHERE id_utilisateur = %s",
                            compte.getSolde(),offreToBuy.getMontant(),id_utilisateur);
                    
                    st.executeUpdate(req1);
                    st.executeUpdate(req2);
                    
                    System.out.println(req1);
                    System.out.println(req2);
            
                    //Comptes.addCredit(compte.getId_utilisateur(), offreToBuy.getMontant());
                    compte = getCurrentSoldeCompte(c,id_utilisateur);
                }
                //ACHAT PAR CREDIT
                else{
                    
                    if(Comptes.verifierCreditCompte(compte,offreToBuy.getMontant()) == false){
                        throw new Exception("Credit Insuffisant pur l'achat de cet offre");
                    }
                    
                    req1 = String.format("Insert into Mouvement_%s(id_utilisateur,type,id_offre,montant,date_mouvement)"
                            + " VALUES(%s,'Achat',%s,%s,now())",type,id_utilisateur,offreToBuy.getId_offre(),offreToBuy.getMontant());
                    
                    req2 = String.format("Update Comptes SET Credit = %s - %s WHERE id_utilisateur = %s",
                            compte.getCredit(),offreToBuy.getMontant(),id_utilisateur);
                    
                    st.executeUpdate(req1);
                    st.executeUpdate(req2);
                }     
 
                compte = getCurrentSoldeCompte(c,id_utilisateur);
                //DATE EXPIRATION PROVISOIRE
                int duree = Integer.parseInt(offreToBuy.getType());
                LocalDateTime exp = LocalDateTime.now().plusDays(duree);
                //--------------------------
                String req3 = String.format("INSERT INTO Comptes VALUES (%s, %s, %s, '%s','%s','%s',now())"
                        ,id_utilisateur,compte.getCredit(),compte.getSolde(),offreToBuy.getNom(),offreToBuy.getValeur(),exp.toString());
                System.out.println("Requete transaction achat offre 1"+req3);  
                
                st.executeUpdate(req3);
                
                c.commit();
            }
            catch(Exception e){
                c.rollback();
                e.printStackTrace();
                throw new Exception("Echec Transaction Achat Offre");
            }
            finally{
                st.close();
                c.close();
            }
        }
        catch(Exception e){
            e.printStackTrace();
            throw new Exception("Exception Achat Offre");
        }
        finally{
            st.close();
            c.close();
        }
    }
    
    // ---------- Comptes
    
    public static Comptes getCurrentSoldeCompte(Connection co, int id_utilisateur) throws Exception{
        
        Statement s = co.createStatement();
        String requete = "SELECT * from comptes where id_utilisateur='"+id_utilisateur+"' order by date_solde desc limit 1";
        
        ResultSet res = s.executeQuery(requete);
        res.next();     
        Comptes retour = new Comptes(res.getInt(1),res.getDouble(2),res.getDouble(3),res.getString(4),res.getString(5),Timestamp.valueOf(res.getString("date_expiration")),Timestamp.valueOf(res.getString("date_solde")));
        
        s.close();
        return retour;
        
    }
}
