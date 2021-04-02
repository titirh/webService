package  mobile.WSmobile_money.webservicesMM;

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
public class TransactionsWS {

    public TransactionsWS() {
    }

    @GetMapping(path="transactions")
    public String getMouvementEnAttente() throws Exception {
        String datas = null;
        try{
            Gson gs = new Gson();
            Mouvement_money[] all = Mouvement_money.getMouvementEnAttente();
            datas = gs.toJson(all);
        }
        catch(Exception e){
            e.printStackTrace();
            throw new Exception("getJsonOffres Exception");
        }  
        return datas;
    }
    
    @PostMapping(value = "transactions/validate")
    public void validationMouvement(@RequestBody String data) throws Exception {
        Gson gs = new Gson();  
        try{
            Mouvement_money mvt = gs.fromJson(data, Mouvement_money.class);
            Mouvement_money.validerDepot(mvt);
        }
        catch(Exception e){
            e.printStackTrace();
        }  
    }
    
    @PostMapping(value = "transactions/reject")
    public void rejectedMouvement(@RequestBody String data) throws Exception {
        Gson gs = new Gson();  
        try{
            Mouvement_money mvt = gs.fromJson(data, Mouvement_money.class);
            Mouvement_money.refuserDepot(mvt);
        }
        catch(Exception e){
            e.printStackTrace();
        }  
    }

    @PostMapping(value = "transactions/addMoney")
    public void addMoney(@RequestBody String data) throws Exception {
        Gson gs = new Gson();  
        try{
            Mouvement_money mvt = gs.fromJson(data, Mouvement_money.class);
            Mouvement_money.refuserDepot(mvt);
        }
        catch(Exception e){
            e.printStackTrace();
        }  
    }
}
