/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author ahmed
 */
public class Restaurants {
    
    private String name;
    private String location;
    private double cpp;
    private String type;
    
    public Restaurants(String name, String location, double cpp, String type){
        this.name = name;
        this.location = location;
        this.cpp = cpp; ///cost per person
        this.type = type;
                
    }
    
    public String getName(){return name;}
    public String getLocation(){return location;}
    public double getCpp(){return cpp;}
    public String getType(){return type;}
    
    public void setName(String name){this.name = name;}
    public void setLocation(String location){this.location = location;}
    public void setCpp(double cpp){this.cpp = cpp;}
    public void setType(String type){this.type = type;}
    

    @Override
    public String toString(){
        return ("Name:"+name+"  Location:"+location+"  Cost per person:"+cpp+ "  cuisine type: "+type);
    }
    
}
