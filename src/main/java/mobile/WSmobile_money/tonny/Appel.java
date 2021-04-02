package  mobile.WSmobile_money.tonny;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import mobile.WSmobile_money.utils.Function;
import  mobile.WSmobile_money.utils.Helper;

public class Appel {
    
    private int id_appel;
    private String numero_entrant;
    private String numero_sortant;
    private double dureeAppel; //en secondes
    public static final int tarifMemeOperateur = 1;
    public static final int tarifDifferentOperateur = 3;
    public static final int tarifAppelCredit = 4;

    public int getId_appel() {
        return id_appel;
    }

    public void setId_appel(int id_appel) {
        this.id_appel = id_appel;
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

    public double getDuree() {
        return dureeAppel;
    }

    public void setDuree(double duree) {
        this.dureeAppel = duree;
    }

    public Appel(int id_appel, String numero_entrant, String numero_sortant, double duree) {
        this.id_appel = id_appel;
        this.numero_entrant = numero_entrant;
        this.numero_sortant = numero_sortant;
        this.dureeAppel = duree;
    }
    
    public static AppelInfo simulerAppel(Appel appel) throws Exception{
        
        Connection c = null;
        Statement s = null;
        int tarif = 0;
        AppelInfo reponse = new AppelInfo(); //tarifSolde tarifCredit dureeAppel 
        System.out.println("DUREE1= "+appel.getDuree());
        try{
            c = Helper.getConnection();
            c.setAutoCommit(false);
        
            //Verifier operateur
            if(isMemeOperateur(appel.getNumero_entrant(),appel.getNumero_sortant()) == true) tarif = tarifMemeOperateur;
            else tarif = tarifDifferentOperateur;

            //Verifier solde compte numeroentrant
            SoldeUtilisateur[] soldesAppel = getSoldeAppelNumero(c,appel.getNumero_entrant());
            double soldeAppelUser = splitGetSoldeTotalAppel(soldesAppel);
                //System.out.println("Solde appel 0="+soldesAppel[0].getValeur_offre()); 
                //System.out.println("Solde appel date0="+soldesAppel[0].getDate_expiration()); 
            double creditAppelUser = (double)soldesAppel[0].getCredit();

            //Calculer Tarif 
            double tarifSurSolde = tarif * appel.getDuree();
            double tarifRestant = soldeAppelUser - tarifSurSolde; //100-120 = -20

            if(tarifRestant >= 0){
                System.out.println("Solde appel suffisant="+tarifRestant); 
                updateOffreAppel(c,soldesAppel,tarifSurSolde);
                
                reponse.setTarifSurSolde(tarifSurSolde);
                reponse.setTarifSurCredit(0);
                reponse.setDureeAppel(appel.getDuree());
            }
            else{
                System.out.println("Solde appel insuffisant="+tarifRestant); 
                updateOffreAppel(c,soldesAppel,(tarifSurSolde + tarifRestant));
                
                double tarifSurCredit = Math.abs(tarifRestant * tarifAppelCredit);
                //Credit suffisant
                if(creditAppelUser >= tarifSurCredit){ 
                    Function.UpdateWithConnection(c, "credit", "credit - "+tarifSurCredit, "comptes", "id_utilisateur", ""+soldesAppel[0].getId_utilisateur());
                    
                    reponse.setTarifSurSolde(tarifSurSolde + tarifRestant);
                    reponse.setTarifSurCredit(tarifSurCredit);
                    reponse.setDureeAppel(appel.getDuree());
                }
                //Credit insuffisant
                else{
                    Function.UpdateWithConnection(c, "credit", "0", "comptes", "id_utilisateur", ""+soldesAppel[0].getId_utilisateur());
                    
                    reponse.setTarifSurSolde(tarifSurSolde + tarifRestant);
                    reponse.setTarifSurCredit(creditAppelUser);
                    reponse.setDureeAppel((tarifSurSolde + tarifRestant + (creditAppelUser/tarifAppelCredit)) / tarif);
                }
            }
            //INSERT APPEL DANS DB
            String[] cols = {"id_utilisateur","numero_entrant","numero_sortant","tarif_solde","tarif_credit","duree","date_appel"};
            String[] datas = {
                ""+soldesAppel[0].getId_utilisateur(),
                ""+appel.getNumero_entrant(),
                ""+appel.getNumero_sortant(),
                ""+reponse.getTarifSurSolde(),
                ""+reponse.getTarifSurCredit(),
                ""+reponse.getDureeAppel(),
                "now()"
            };
            Function.insertWithConnection(c, cols, datas, "appels");
            
            c.commit();
            return reponse;
        }
        catch(Exception e){
            c.rollback();
            e.printStackTrace();
            throw new Exception("calculer tarif appel exception");
        }
    }
    
    private static void updateOffreAppel(Connection c, SoldeUtilisateur[] soldesAppel, double tarifSurSolde) {
        
        try{
            Statement s =c.createStatement();
            double reste = tarifSurSolde;
            int i = 0;
            while(i < soldesAppel.length){
                double[] valeurs = Offre.splitValeurOffre(soldesAppel[i].getValeur_offre());
                
                if(reste == 0) break;
                
                else if(reste < valeurs[0]){
                    double valeurs_restant = valeurs[0] - reste;
                    String newValeurOffre = Offre.modifierValeurOffre(soldesAppel[i].getValeur_offre(), valeurs_restant,"appel");
                    Function.UpdateWithConnection(c, "valeur_offre", newValeurOffre, "Comptes", "date_solde", soldesAppel[i].getDate_solde());
                    
                    break;
                }
                
                else if(reste >= valeurs[0]){ //appel
                    double valeur_restant = 0;
                    String newValeurOffre = Offre.modifierValeurOffre(soldesAppel[i].getValeur_offre(), valeur_restant,"appel");
                    Function.UpdateWithConnection(c, "valeur_offre", newValeurOffre, "Comptes", "date_solde", soldesAppel[i].getDate_solde());
                    
                    reste = reste - valeurs[0];
                }          
                i++;
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public static double splitGetSoldeTotalAppel(SoldeUtilisateur[] sa){
        
        double somme =0;
        int i = 0;
        while(i < sa.length){
            double[] valeurs = Offre.splitValeurOffre(sa[i].getValeur_offre());
            somme = somme + valeurs[0]; //appel;internet;sms
            i++;
        }
        
        return somme;
    }
    
    public static SoldeUtilisateur[] getSoldeAppelNumero(Connection c,String num)throws Exception{
        
        List<SoldeUtilisateur> list = new ArrayList(); 
        Statement s = c.createStatement();
        
        try{        
            String requete = String.format("SELECT * from OffreAppelClient WHERE numero = '%s' ORDER BY date_expiration ASC",
                                num);
            System.out.println(requete);
            ResultSet res = s.executeQuery(requete);
            while(res.next()){
                
                System.out.println("solde 1 "+res.getString("valeur_offre"));
                list.add(new SoldeUtilisateur(res.getInt("id_utilisateur"),res.getString("numero"),res.getDouble("credit"),res.getString("valeur_offre"),res.getString("date_expiration"),res.getString("date_solde")));
            }
            
            return list.toArray(new SoldeUtilisateur[list.size()]);
        }
        catch(Exception e){
            e.printStackTrace();
        }
        finally{
            s.close();
        }
        
        return list.toArray(new SoldeUtilisateur[list.size()]);
    }
    
    public static boolean isMemeOperateur(String num1,String num2){
        
        num1 = num1.substring(0, 3);
        num2 = num2.substring(0, 3);
        return num1.equals(num2);
    }
    
    
    
}
