package  mobile.WSmobile_money.utils;

import java.util.ArrayList;
import java.util.Optional;

public class JsonRequest {
	  private Integer status;
	   	Object data;
	    String message;

	    public Integer getStatus() {
	        return status;
	    }

	    public Object getData() {
	        return data;
	    }

	    public String getMessage() {
	        return message;
	    }
	    
	  

	    public void setStatus(Integer status) {
	        this.status = status;
	    }

	    public void setData(Object data) {
	        this.data = data;
	    }

	    public void setMessage(String message) {
	        this.message = message;
	    }  
	    

	    public JsonRequest() {
	    }

	    
	    
	    public JsonRequest(Integer status, Object data, String message) {
	        this.status = status;
	        this.data= data;
	        this.message = message;
	    }
}
