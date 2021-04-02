package  mobile.WSmobile_money.tonny;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import  mobile.WSmobile_money.utils.Helper;

public class AppelInfo {
    
    private int id_appel;
    private int id_utilisateur;
    private String numero_entrant;
    private String numero_sortant;
    private double tarifSurSolde;
    private double tarifSurCredit;
    private double dureeAppel;
    private String date_appel;

    public double getTarifSurSolde() {
        return tarifSurSolde;
    }

    public void setTarifSurSolde(double tarifSurSolde) {
        this.tarifSurSolde = tarifSurSolde;
    }

    public double getTarifSurCredit() {
        return tarifSurCredit;
    }

    public void setTarifSurCredit(double tarifSurCredit) {
        this.tarifSurCredit = tarifSurCredit;
    }

    public double getDureeAppel() {
        return dureeAppel;
    }

    public void setDureeAppel(double dureeAppel) {
        this.dureeAppel = dureeAppel;
    }

    public int getId_appel() {
        return id_appel;
    }

    public void setId_appel(int id_appel) {
        this.id_appel = id_appel;
    }

    public int getId_utilisateur() {
        return id_utilisateur;
    }

    public void setId_utilisateur(int id_utilisateur) {
        this.id_utilisateur = id_utilisateur;
    }

    public String getNumero_entrant() {
        return numero_entrant;
    }

    public void setNumero_entrant(String numero_entrant) {
        this.numero_entrant = numero_entrant;
    }

    public String getNumero_sortant() {
        return numero_sortant;
    }

    public void setNumero_sortant(String numero_sortant) {
        this.numero_sortant = numero_sortant;
    }

    public String getDate_appel() {
        return date_appel;
    }

    public void setDate_appel(String date_appel) {
        this.date_appel = date_appel;
    }
    
    public AppelInfo(){};

    public AppelInfo(double tarifSurSolde, double tarifSurCredit, double dureeAppel) {
        this.tarifSurSolde = tarifSurSolde;
        this.tarifSurCredit = tarifSurCredit;
        this.dureeAppel = dureeAppel;
    }

    public AppelInfo(int id_appel, int id_utilisateur, String numero_entrant, String numero_sortant, double tarifSurSolde, double tarifSurCredit, double dureeAppel, String date_appel) {
        this.id_appel = id_appel;
        this.id_utilisateur = id_utilisateur;
        this.numero_entrant = numero_entrant;
        this.numero_sortant = numero_sortant;
        this.tarifSurSolde = tarifSurSolde;
        this.tarifSurCredit = tarifSurCredit;
        this.dureeAppel = dureeAppel;
        this.date_appel = date_appel;
    }
    
    public static AppelInfo[] getHistoriqueAppelEntrant(String numero) throws Exception{
        
        Connection c = null;
        Statement s = null;
        List<AppelInfo> list = new ArrayList<AppelInfo>();
        
        try{
            c = Helper.getConnection();
            c.setAutoCommit(true);
            s = c.createStatement();
            String requete = "SELECT * FROM Appels WHERE numero_entrant = '"+numero+"' ORDER BY Date_appel DESC Limit 5";
            System.out.println("Histo="+requete);
            ResultSet res = s.executeQuery(requete);
            
            while(res.next()){
                list.add(new AppelInfo(res.getInt(1),res.getInt(2),res.getString(3),res.getString(4),res.getDouble(5),res.getDouble(6),res.getDouble(7),res.getString(8)));
            }
            
            return list.toArray(new AppelInfo[list.size()]);
        }
        catch(Exception e){
            e.printStackTrace();
            throw new Exception("Get historique Entrant Exception");
        }
        finally{
            s.close();
            c.close();
        }
    }
    
    public static AppelInfo[] getHistoriqueAppelSortant(String numero) throws Exception{
        
        Connection c = null;
        Statement s = null;
        List<AppelInfo> list = new ArrayList<AppelInfo>();
        
        try{
            c = Helper.getConnection();
            c.setAutoCommit(true);
            s = c.createStatement();
            String requete = "SELECT * FROM Appels WHERE numero_sortant = '"+numero+"' ORDER BY Date_appel DESC Limit 5";
            System.out.println("Histo="+requete);
            ResultSet res = s.executeQuery(requete);
            
            while(res.next()){
                list.add(new AppelInfo(res.getInt(1),res.getInt(2),res.getString(3),res.getString(4),res.getDouble(5),res.getDouble(6),res.getDouble(7),res.getString(8)));
            }
            
            return list.toArray(new AppelInfo[list.size()]);
        }
        catch(Exception e){
            e.printStackTrace();
            throw new Exception("Get historique Entrant Exception");
        }
        finally{
            s.close();
            c.close();
        }
    }
}
