package  mobile.WSmobile_money.tonny;

public class SoldeUtilisateur {
    
    private int id_utilisateur;
    private String numero;
    private double credit;
    private String valeur_offre;
    private String date_expiration;
    private String date_solde; 

    public int getId_utilisateur() {
        return id_utilisateur;
    }

    public void setId_utilisateur(int id_utilisateur) {
        this.id_utilisateur = id_utilisateur;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public double getCredit() {
        return credit;
    }

    public void setCredit(double credit) {
        this.credit = credit;
    }

    public String getValeur_offre() {
        return valeur_offre;
    }

    public void setValeur_offre(String valeur_offre) {
        this.valeur_offre = valeur_offre;
    }

    public String getDate_expiration() {
        return date_expiration;
    }

    public void setDate_expiration(String date_expiration) {
        this.date_expiration = date_expiration;
    }

    public String getDate_solde() {
        return date_solde;
    }

    public void setDate_solde(String date_solde) {
        this.date_solde = date_solde;
    }

    public SoldeUtilisateur(int id_utilisateur, String numero, double credit, String valeur_offre, String date_expiration, String date_solde) {
        this.id_utilisateur = id_utilisateur;
        this.numero = numero;
        this.credit = credit;
        this.valeur_offre = valeur_offre;
        this.date_expiration = date_expiration;
        this.date_solde = date_solde;
    }


    
    
}
