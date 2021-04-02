package  mobile.WSmobile_money.tiavina;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDateTime;

import  mobile.WSmobile_money.utils.Function;
import  mobile.WSmobile_money.utils.Helper;

/**
 *
 * @author Tiavina
 */
public class Utilisateur {

    int id_utilisateur;
    String token;
    String username;
    String password;

    public String getToken() {
        return token;
    }

    public int getId_utilisateur() {
        return id_utilisateur;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public void setId_utilisateur(int id_utilisateur) {
        this.id_utilisateur = id_utilisateur;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    
    public Utilisateur() {
    }

    public Utilisateur(int id_utilisateur, String token, String username, String password) {
        this.id_utilisateur = id_utilisateur;
        this.token = token;
        this.username = username;
        this.password = password;
    }
    
     public static Utilisateur findUtilisateur(String colonne,String value) throws Exception{
       
        Utilisateur retourn=null;
        Connection con =null;
        Statement st=null;
        try{
            con = Helper.getConnection();
            String req="select * from utilisateur where "+colonne+"='"+value+"'";
            st=con.createStatement();
             //System.out.print(req);
            ResultSet res=st.executeQuery(req);
            res.next();
            retourn=new Utilisateur(res.getInt(1),res.getString(2),res.getString(3),res.getString(4));
       }
       catch(Exception e){
           e.printStackTrace();
           throw new Exception("Erreur find Clients");
       }
       finally{
           con.close();
           st.close();
           if(retourn==null){
               throw new Exception("Clients introuvable");
           }
       }
       return retourn;
   }
     
    public static void genererToken(Utilisateur user) throws Exception{

        LocalDateTime nw = LocalDateTime.now();
        String token = user.getUsername()+nw;
        token = Cryptage.base64encode(token);
        String colonne = "token";
        Function.Update(colonne, token, "utilisateur", "id_utilisateur",""+user.getId_utilisateur());
    }
    
    public static void returnToken(Utilisateur user) throws Exception{

        LocalDateTime nw = LocalDateTime.now();
        String token = user.getUsername()+nw;
        token = Cryptage.base64encode(token);
        String colonne = "token";
        Function.Update(colonne, token, "utilisateur", "id_utilisateur",""+user.getId_utilisateur());
    }
     
    public static void inscription(String[] data) throws Exception{
        
        Connection co = Helper.getConnection();
        co.setAutoCommit(false); //tsy mi auto commit
        LocalDateTime now = LocalDateTime.now();
        
        try{       
            LocalDateTime nw = LocalDateTime.now();
            String token = data[4]+nw;
            
            String[] dataNewUser = {

                ""+Cryptage.base64encode(token),
                ""+data[4],
                ""+Cryptage.base64encode(data[5])
            };
                
            //Insere nouvel utilisateur inscrit et recupere son id_utilisateur(Foreign Key dans Comptes & Clients)
            Utilisateur newUser = new Utilisateur(0,dataNewUser[0],dataNewUser[1],dataNewUser[2]);        
            int id_newUser = insertNewUserReturnId(newUser);
            
            String[] colonne1=Function.getColonneWithConnection(co,"clients");
            String[] data1={
                ""+id_newUser,
                ""+data[0],//nom
                ""+data[1],//prenom
                ""+data[2],//datenaissance
                ""+data[3]//numero
            };
            Function.insertWithConnection(co,colonne1, data1,"clients");

            String[] colonne2=Function.getColonneWithConnection(co,"comptes");
            String[] data2={
                ""+id_newUser,
                "0",
                "0",
                "neant",
                "0",
                ""+now,
                ""+now
            };  
            Function.insertWithConnection(co,colonne2, data2, "comptes");
            
            co.commit(); //Commit tout les requetes de co en meme temps
        }
        catch(Exception e){
            
            co.rollback(); //Rollback tout les requetes de co en cas d'exception
            e.printStackTrace();
        }
        finally{
            co.close(); 
        }
    } 
    
    public static Clients login(String user,String pwds) throws Exception{
        
        String pwd=Cryptage.base64encode(pwds);
        Clients retourn=null;
        Utilisateur utilisateur=null;
        Connection con = null;
        Statement st=null;

        try{
            Helper conect = new Helper();
            con = conect.getConnection();
            String req = "select * from utilisateur where username='"+user+"' and password='"+pwd+"'";
            System.out.print(req); 
            st=con.createStatement();
            ResultSet res=st.executeQuery(req);
            res.next();
            utilisateur=new Utilisateur(res.getInt(1),res.getString(2),res.getString(3),res.getString(4));

            if(utilisateur==null) throw new Exception("User not found");
            
            else {
                retourn=Clients.findClient("id_utilisateur", ""+utilisateur.getId_utilisateur());
                Utilisateur.genererToken(utilisateur);
            }
        }
        catch(Exception e){
            e.printStackTrace();
            throw new Exception ("Erreur login");
        }
        finally{
            con.close();
            st.close();
        }
        return retourn;
    }
    
    public static int insertNewUserReturnId(Utilisateur newUser) throws Exception {
        
        Connection c = null;
        Statement st = null;
        ResultSet result = null;
        int idNewUser;
        
        try{
            c = Helper.getConnection();
            c.setAutoCommit(false);
            
            String req1 = String.format("Insert into Utilisateur(token, username, password) VALUES('%s','%s','%s')",
                        newUser.getToken(),newUser.getUsername(),newUser.getPassword());
            
            String req2 = String.format("Select max(id_utilisateur) as last_user_id from utilisateur");
            
            st = c.createStatement();
            st = c.createStatement();
            st.executeUpdate(req1);
            result = st.executeQuery(req2);
            
            result.next();
            idNewUser = result.getInt("last_user_id");
            
            c.commit();
            return idNewUser;           
        }
        catch(Exception e){
            
            c.rollback();
            e.printStackTrace();
            throw new Exception("Insert new User Exception");
        }
        finally{
            st.close();
            c.close();
        }
    }
}
