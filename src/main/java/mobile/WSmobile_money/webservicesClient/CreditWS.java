package  mobile.WSmobile_money.webservicesClient;

import  mobile.WSmobile_money.tiavina.Comptes;
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
public class CreditWS {

    //@Path("credit/{montant}")
    @PostMapping(value = "credit/{montant}")
    public void crediterCompte(@PathVariable("montant") double montant,@RequestBody String json_id) throws Exception{
        
        Gson gson = new Gson();
        try{
            int id_utilisateur = gson.fromJson(json_id, int.class); 
            Comptes.addCredit(id_utilisateur,montant);
            
        }
        catch(Exception e){
            e.printStackTrace();
            throw new Exception("Echec depot money");
        }   
    }

}
