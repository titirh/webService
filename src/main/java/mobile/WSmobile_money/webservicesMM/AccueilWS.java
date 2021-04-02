package  mobile.WSmobile_money.webservicesMM;

import  mobile.WSmobile_money.tiavina.Clients;
import  mobile.WSmobile_money.tiavina.Utilisateur;
import  mobile.WSmobile_money.tonny.UtilisateurInfo;
import com.google.gson.Gson;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;


@CrossOrigin(origins="*",allowedHeaders="*")
@RestController
@RequestMapping

public class AccueilWS{
   
    public AccueilWS() {
    }
    
    @PostMapping(path ="accueil/inscription")
    @CrossOrigin 
    public String inscriptionWs(@RequestBody String data) throws Exception {
        
        System.out.println("New user recu= "+data);
        try{
            Gson gs = new Gson();
            Clients newUser = gs.fromJson(data, Clients.class);

            String[] str_datas={
                 ""+newUser.getNom(),
                 ""+newUser.getPrenom(),
                 ""+newUser.getDate_naissance(),
                 ""+newUser.getNumero(),
                 ""+newUser.getUsername(),
                 ""+newUser.getPassword()
            };
            Utilisateur.inscription(str_datas);
            UtilisateurInfo ui = UtilisateurInfo.getUtilisateurInfoFromLogin(str_datas[4],str_datas[5]);
            String reponse = gs.toJson(ui);
            
            return reponse;
        }
        catch(Exception e){
            e.printStackTrace();
            throw new Exception("Inscription Erreur");
        }  
    }
    
    @PostMapping(path ="accueil/login")
    @CrossOrigin 
    public String login(@RequestBody String data) throws Exception {
        
    	System.out.println("RECU="+data);
        String reponse = null;
        Gson gs = new Gson();
        String token = null;
        
        try{
            Utilisateur util = gs.fromJson(data, Utilisateur.class);
            UtilisateurInfo ui = UtilisateurInfo.getUtilisateurInfoFromLogin(util.getUsername(),util.getPassword());
            reponse = gs.toJson(ui);
        	System.out.println("REPONSE="+reponse);
            return reponse;
        }
        catch(Exception e){
            e.printStackTrace();
            return null;
            //return Response.status(Response.Status.FORBIDDEN).build();
        }  
    }
}
