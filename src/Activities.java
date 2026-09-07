/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author ahmed
 */
public class Activities {
    
    private String name;
    private String location;
    private double cost;
    
    public Activities(String name, String location, double cost){
        this.name = name;
        this.location = location;
        this.cost = cost;
           
    }
    
    public String getName(){return name;}
    public String getLocation(){return location;}
    public double getCost(){return cost;}
    
    public void setName(String name){this.name = name;}
    public void setLocation(String location){this.location = location;}
    public void setCost(double cost){this.cost = cost;}
    
    @Override
    public String toString(){
        return ("Name:"+name+"  Location:"+location+"  Cost:"+cost);
    }
    
    
}
