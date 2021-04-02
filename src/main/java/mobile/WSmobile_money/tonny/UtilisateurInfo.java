package  mobile.WSmobile_money.tonny;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import  mobile.WSmobile_money.tiavina.Clients;
import  mobile.WSmobile_money.tiavina.Utilisateur;
import  mobile.WSmobile_money.utils.Helper;

public class UtilisateurInfo {
    
    int id_utilisateur;
    String nom;
    String prenom;
    String numero;
    String token;

    public static UtilisateurInfo getUtilisateurInfoFromLogin(String user,String pass) throws Exception {
        
        Clients client = Utilisateur.login(user, pass);
        String clientToken = getIdUserToken(client.getId_utilisateur());
        UtilisateurInfo reponse = new UtilisateurInfo(client.getId_utilisateur(),client.getNom(),client.getPrenom(),client.getNumero(),clientToken);
    
        return reponse;
    }
    
    public static String getIdUserToken(int idU) throws Exception{
        
        Connection c = null;
        Statement st = null;
        ResultSet res = null;
        
        try{
            c = Helper.getConnection();
            c.setAutoCommit(true);
            
            String req ="Select token from utilisateur where id_utilisateur="+idU;
            st = c.createStatement();
            res = st.executeQuery(req);
            
            res.next();
            return res.getString("token");
        }
        catch(Exception e){
            e.printStackTrace();
            throw new Exception("get UI Token");
        }
    }
    
    public UtilisateurInfo(int id_utilisateur, String nom, String prenom, String numero, String token) {
        this.id_utilisateur = id_utilisateur;
        this.nom = nom;
        this.prenom = prenom;
        this.numero = numero;
        this.token = token;
    }
    public int getId_utilisateur() {
        return id_utilisateur;
    }

    public void setId_utilisateur(int id_utilisateur) {
        this.id_utilisateur = id_utilisateur;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
