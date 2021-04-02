package  mobile.WSmobile_money.webservicesMM;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import  mobile.WSmobile_money.tiavina.Statistiques;
import com.google.gson.Gson;

@CrossOrigin(origins="*",allowedHeaders="*")
@RestController
@RequestMapping
public class StatistiquesWS{

    public StatistiquesWS() {
    }
    
    public void get() throws Exception {}

    @GetMapping(path="statistiques/clientsfavoris")
    public String getClientFavoris() throws Exception {
        
        String datas = null;
        try{
            Gson gs = new Gson();
            Statistiques[] statistique = Statistiques.getFavorisClients();
            datas = gs.toJson(statistique);
        }
        catch(Exception e){
            e.printStackTrace();
            throw new Exception("getClientFavoris Exception");
        }      
        return datas;
    }
}
