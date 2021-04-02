package  mobile.WSmobile_money.webservicesClient;

import  mobile.WSmobile_money.tiavina.Mouvement_money;
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
public class MoneyWS {

    public MoneyWS() {
    }


    @GetMapping("money")
    public String getJson() {
        //TODO return proper representation object
        throw new UnsupportedOperationException();
    }
    
    @PostMapping(path ="money/depot/{montant}")
    public void faireDepot(@PathVariable("montant") double montant, @RequestBody String json_id) throws Exception{
        
        Gson gson = new Gson();
        try{
            int id_utilisateur = gson.fromJson(json_id, int.class); 
            Mouvement_money.faireDepotMoney(id_utilisateur,montant);
            
        }
        catch(Exception e){
            e.printStackTrace();
            throw new Exception("Echec depot money");
        }   
    }

}
