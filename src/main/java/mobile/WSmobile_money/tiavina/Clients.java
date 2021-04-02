package  mobile.WSmobile_money.tiavina;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Timestamp;

import  mobile.WSmobile_money.utils.Helper;


/**
 *
 * @author Tiavina
 */
public class Clients {
    private int id_utilisateur;
    String nom;
    String prenom;
    String date_naissance;
    String numero;
    String username;
    String password;

    public void setId_utilisateur(int id_utilisateur) {
        this.id_utilisateur = id_utilisateur;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public void setDate_naissance(String date_naissance) {
        this.date_naissance = date_naissance;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public int getId_utilisateur() {
        return id_utilisateur;
    }

    public String getNom() {
        return nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public String getDate_naissance() {
        return date_naissance;
    }

    public String getNumero() {
        return numero;
    }

    public Clients() {
    }
    
        public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Clients(int id_utilisateur, String nom, String prenom, String date_naissance, String numero) {
        this.id_utilisateur = id_utilisateur;
        this.nom = nom;
        this.prenom = prenom;
        this.date_naissance = date_naissance;
        this.numero = numero;
    }
    
    public Clients(String nom, String prenom, String date_naissance, String numero, String username, String password) {
        this.nom = nom;
        this.prenom = prenom;
        this.date_naissance = date_naissance;
        this.numero = numero;
        this.username = username;
        this.password = password;
    }
    
    public static Clients findClient(String colonne,String value) throws Exception{
       
        Clients retourn=null;
        Connection con =null;
        Statement st=null;
        
        try{
            con = Helper.getConnection();
            String req = "select * from clients where "+colonne+"='"+value+"'";
            st = con.createStatement();
            System.out.println(req);
            ResultSet res = st.executeQuery(req);
            
            res.next();     
            retourn = new Clients(res.getInt(1),res.getString(2),res.getString(3),res.getString(4),res.getString(5));
            con.close();
            st.close();
        }
        catch(Exception e){
           
           if(retourn == null){
               throw new Exception("Clients introuvable");
           }
           
           e.printStackTrace();
           throw new Exception("Erreur find Clients");
        }
        finally{
            con.close();
            st.close();
        }
        return retourn;
   }
  
}
