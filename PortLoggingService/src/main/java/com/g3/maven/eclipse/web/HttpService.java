/**
 * @author Nicolas Roth
 */

package com.g3.maven.eclipse.web;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import com.g3.maven.eclipse.model.PortScanLogRequest;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

public class HttpService {
	private final String BASE_URL = "http://localhost:8080";
	private final String AUTH_ENDPOINT = "/api/v1/auth";
	private final String PORT_ENDPOINT = "/api/v1/port";
	private String jwtToken;
	private AuthServiceStatus authStatus;
	
	
    public HttpService() {
		super();
		authStatus = AuthServiceStatus.UNAUTHORIZED;
		try {
			authorize();			
		} catch (Exception e) {
		}
	}
    
    private void authorize() {    	
    			
		try {
	    	authStatus = AuthServiceStatus.AUTHORIZING;
	    	
	    	HttpURLConnection con = getHttpConnection(AUTH_ENDPOINT);

	    	// create json payload and send to server
	        Gson gson = new Gson();        
	        Map<String, String> payload = new HashMap<String, String>();
	        // Further work remove code credentials
	        payload.put("username","AzureDiamond");
	        payload.put("password", "hunter2");
	        OutputStreamWriter wr = new OutputStreamWriter(con.getOutputStream());
	        wr.write(gson.toJson(payload));
	        wr.flush();
	        
	        String response = getResponse(con);
	        JsonObject jobj = gson.fromJson(response, JsonObject.class);
	       	
	    	this.jwtToken = jobj.get("jwt").getAsString();

	    	authStatus = AuthServiceStatus.AUTHORIZED;			
		} catch (Exception e) {
			authStatus = AuthServiceStatus.UNAUTHORIZED;
		}
    }
    
    public String sendPortLog(PortScanLogRequest payload) throws Exception {
    	if (authStatus != AuthServiceStatus.AUTHORIZED) {
    		authorize();
    	}
    	HttpURLConnection con = getHttpConnection(PORT_ENDPOINT);
    	con.setRequestProperty("Authorization", "Bearer " + this.jwtToken);

    	// create json payload and send to server
        Gson gson = new Gson();        
        OutputStreamWriter wr = new OutputStreamWriter(con.getOutputStream());
        wr.write(gson.toJson(payload));
        wr.flush();

        return getResponse(con);       	
    }
    
    private String getResponse(HttpURLConnection con) throws Exception {
    	String response = new String();
    	
        StringBuilder sb = new StringBuilder();  
        int HttpResult = con.getResponseCode(); 
        if (HttpResult == HttpURLConnection.HTTP_OK) {
            BufferedReader br = new BufferedReader(
                    new InputStreamReader(con.getInputStream(), "utf-8"));
            String line = null;  
            while ((line = br.readLine()) != null) {  
                sb.append(line + "\n");  
            }
            br.close();
            response = sb.toString();  
        } else {
            response = con.getResponseMessage();
        }
        
        return response; 
    	    	
    }
    
    private HttpURLConnection getHttpConnection(String endpoint) throws Exception {
        URL url = new URL(BASE_URL + endpoint);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();

        con.setDoOutput(true);
        con.setDoInput(true);
        con.setRequestProperty("Content-Type", "application/json");
        con.setRequestProperty("Accept", "application/json");
        con.setRequestMethod("POST");    
        
        return con;
    }
}
