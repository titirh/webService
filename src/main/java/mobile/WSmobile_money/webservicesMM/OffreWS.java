package  mobile.WSmobile_money.webservicesMM;

import java.util.ArrayList;

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

import  mobile.WSmobile_money.tonny.Offre;
import com.google.gson.Gson;

@CrossOrigin(origins="*",allowedHeaders="*")
@RestController
@RequestMapping

public class OffreWS{
   
    @GetMapping(path="offre")
    public String getJsonOffres() throws Exception {
      
    	String datas = null;
        try{
            Gson gs = new Gson();
            Offre[] all = Offre.getOffres();
            datas = gs.toJson(all);
        }
        catch(Exception e){
            e.printStackTrace();
            throw new Exception("getJsonOffres Exception");
        }  
        
        return datas;
    }
    
    @GetMapping(path="offre/{id}")
    public String getJsonOffreById(@PathVariable("id") int id) throws Exception {
      
    	String datas = null;
        try{
            Gson gs = new Gson();
            Offre all = Offre.getOffreEnPhraseById(id);
            Offre[] table = { all };
            datas = gs.toJson(table);
        }
        catch(Exception e){
            e.printStackTrace();
            throw new Exception("getJsonOffreById Exception");
        }  
        
        return datas;
    }
    
    @PostMapping(path="offre")
    public void addOffre(@RequestBody String newOffre) throws Exception {
      
    	 Gson gson = new Gson();
         try{
             Offre nOffre = gson.fromJson(newOffre, Offre.class); 
             Offre.insertOffre(nOffre);
         }
         catch(Exception e){
             e.printStackTrace();
             throw new Exception("Echec ajout d'offre");
         }  
    }
    
    @PutMapping(path="offre")
    public void updateOffre(String updatedOffre) throws Exception {
      
        Gson gson = new Gson();
        try{
            Offre nOffre = gson.fromJson(updatedOffre, Offre.class); 
            Offre.updateOffre(nOffre);
        }
        catch(Exception e){
            e.printStackTrace();
            throw new Exception("Echec mise a jour offre");
        }  
    }
    
    @DeleteMapping(path="offre/{id}")
    public void deleteOffre(@PathVariable("id") int id) throws Exception {
      
        try{
            Offre.deleteOffre(id);
        }
        catch(Exception e){
            e.printStackTrace();
            throw new Exception("Echec suppression d'offre");
        }  
    }
}
