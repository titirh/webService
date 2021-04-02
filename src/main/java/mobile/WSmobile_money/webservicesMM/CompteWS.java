package  mobile.WSmobile_money.webservicesMM;


import  mobile.WSmobile_money.tiavina.Comptes;
import com.google.gson.Gson;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


//@Path("compte")
@CrossOrigin(origins="*",allowedHeaders="*")
@RestController
@RequestMapping
public class CompteWS{

    public CompteWS(){
    }
    
    @GetMapping(path="compte")
    public void get() throws Exception {
    
    }
    
    @GetMapping(path="compte/{id_utilisateur}")
    public String getCompteById(@PathVariable("id_utilisateur") int id) throws Exception {

        String datas=null;
            try{
            Gson gs = new Gson();
            Comptes compte = Comptes.findCompteFromColonne("id_utilisateur", ""+id);
            datas = gs.toJson(compte);
        }
        catch(Exception e){
            e.printStackTrace();
            throw new Exception("getJsonCompteById Exception");
        }  
            
        return datas;
    }
    @GetMapping(path="compte/monCompte/{id_utilisateur}")
    public String getMonCompte(@PathVariable("id_utilisateur") int id) throws Exception {

        String datas=null;
        
        try{
            Gson gs = new Gson();
            Comptes[] compte = Comptes.getDataMonCompte(id);
            datas = gs.toJson(compte);
            System.out.print("mcompte"+String.valueOf(datas));
        }
        catch(Exception e){
            e.printStackTrace();
            throw new Exception("getMOnCmpte Exception");
        }  
            
        return datas;
    }
}
