package  mobile.WSmobile_money.webservicesClient;

import org.springframework.web.bind.annotation.RestController;

import mobile.WSmobile_money.tonny.Appel;
import mobile.WSmobile_money.tonny.AppelInfo;
import com.google.gson.Gson;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@CrossOrigin(origins="*",allowedHeaders="*")
@RestController
@RequestMapping

public class AppelClientWS {

    public AppelClientWS() {
    }
    
    @PostMapping(value="appel")
    public void simulerAppel(@RequestBody String json_appel) throws Exception{
        
        Gson gson = new Gson();
        try{
            System.out.println("Appel JSON RECU = "+json_appel);
            Appel appel = gson.fromJson(json_appel, Appel.class); 
            Appel.simulerAppel(appel);
        }
        catch(Exception e){
            e.printStackTrace();
            throw new Exception("Echec appel");
        }   
    }
    
    @GetMapping(path="/HistoriqueEntrant/{num}")
    public String getAppelEntrant(@PathVariable String json_numero) {
        
        Gson gson = new Gson();
        try{
            String numero = gson.fromJson(json_numero, String.class); 
            AppelInfo[] historiques = AppelInfo.getHistoriqueAppelEntrant(numero);
            return gson.toJson(historiques);
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }
    
    @GetMapping(path="appel/HistoriqueSortant/{num}")
    public String getAppelSortant(@PathVariable String json_numero) {
        
        Gson gson = new Gson();
        try{
            String numero = gson.fromJson(json_numero, String.class); 
            AppelInfo[] historiques = AppelInfo.getHistoriqueAppelSortant(numero);
            return gson.toJson(historiques);
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }
}
