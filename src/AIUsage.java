/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.URI;
import java.io.IOException;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonArray;
import java.util.ArrayList;
/**
 *
 * @author ahmed
 */
public class AIUsage {
    
    String eCost = "TOTAL ESTIMATED COST:";
     
    private HttpClient client;
    
    public AIUsage(){
        
        client = HttpClient.newHttpClient();
        
    }
    
    public String createTrip(String prompt){
        
        String apiKey = System.getenv("GROQ_API_KEY");
        
        HttpRequest.Builder reqBuilder = HttpRequest.newBuilder();
        
        reqBuilder.uri(URI.create("https://api.groq.com/openai/v1/chat/completions"));
        
        reqBuilder.header("Content-Type", "application/json");
        
        reqBuilder.header("Authorization","Bearer "+apiKey);
        
        String jsonBody = "{\"model\": \"openai/gpt-oss-20b\",\"messages\": [{\"role\":"
                + "\"user\",\"content\":\""+prompt+"\"}]}";
        
        reqBuilder.POST(HttpRequest.BodyPublishers.ofString(jsonBody));
        
        HttpRequest request = reqBuilder.build();
        
        HttpResponse<String> response;
        
        try{
            response = client.send(request, HttpResponse.BodyHandlers.ofString());
        
            if (response.statusCode() == 200){
            
                JsonObject responseJson = JsonParser.parseString(response.body()).getAsJsonObject();
                JsonArray choices = responseJson.getAsJsonArray("choices");
                JsonObject firstChoice = choices.get(0).getAsJsonObject();
        
                JsonObject message = firstChoice.getAsJsonObject("message");
        
                String itinerary = message.get("content").getAsString();
        
       
                return itinerary;
                
            } else{return "Request Failed Error Code "+response.statusCode();}
        }
        catch(IOException error){System.out.println("Network error");}
        catch(InterruptedException error){ System.out.println("Request was interrupted");}
        
        return "unable to generate trip";
    }
    
    public double getEstimatedCost(String aiResponse){
        
       int position = aiResponse.indexOf(eCost);
       int length = position + eCost.length();
       
       String getCost = aiResponse.substring(length);
       
       getCost = getCost.trim();
       
       double finalCost = Double.parseDouble(getCost);
       
       System.out.println(finalCost);
       return finalCost;
    }
    
    public ArrayList<Activities> getActivities(String aiResponse){
        
        ArrayList<Activities> foundActivities = new ArrayList<>();
        
        String[] lines = aiResponse.split("\n");
        
        for(String line : lines){
            if(line.contains("ACTIVITY:")){
                String[] parts = line.split("\\|");
                
                String name = parts[0].replace("ACTIVITY:","");
                name = name.trim();
                
                String location = parts[1].replace("LOCATION:", "");
                location = location.trim();
                
                String costText = parts[2].replace("COST:", "");
                costText = costText.trim();
                
                double cost = Double.parseDouble(costText);
                
                Activities activity = new Activities(name, location, cost);

                foundActivities.add(activity);
            }
        }
        
        return foundActivities;
    }
    
    public ArrayList<Restaurants> getRestaurants(String aiResponse){
        
        ArrayList<Restaurants> foundRestaurants = new ArrayList<>();
        
        String[] sections = aiResponse.split("\n");
        
        for(String section : sections){
            if(section.contains("RESTAURANT:")){
                
                String[] region = section.split("\\|");
                
                String name = region[0].replace("RESTAURANT:", "");
                name = name.trim();
                
                String location = region[1].replace("LOCATION:", "");
                location = location.trim();
                
                String cppText = region[2].replace("COST:", "");
                cppText = cppText.trim();
                
                double cpp = Double.parseDouble(cppText);
                
                String type = region[3].replace("TYPE:", "");
                type = type.trim();
                
                Restaurants restaurant = new Restaurants(name, location, cpp, type);
                
                foundRestaurants.add(restaurant);
                
            }
        }
        return foundRestaurants;
    }
}
