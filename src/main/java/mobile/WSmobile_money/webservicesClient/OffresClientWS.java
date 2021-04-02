package  mobile.WSmobile_money.webservicesClient;

import  mobile.WSmobile_money.tonny.Offre;
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
public class OffresClientWS {

    public OffresClientWS() {
    }
  
    
    @PostMapping(value ="acheterOffre/{type_paiement}/{id_offre}")
    public void acheterOffre(@PathVariable("type_paiement") String type_paiement,@PathVariable("id_offre") int id,@RequestBody String compteJSON) throws Exception {
        
        Gson gson = new Gson();
        try{
            int id_utilisateur = gson.fromJson(compteJSON, int.class); 
            Offre.acheterOffre(id_utilisateur,id,type_paiement);
        }
        catch(Exception e){
            e.printStackTrace();
            throw new Exception("Echec ajout d'offre");
        }  
    }
}
