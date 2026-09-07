/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */

/**
 *
 * @author ahmed
 */
import java.util.ArrayList;

public class travelPlanner {
    
    private String destination;
    private int days;
    private double budget;
    
    
    
    private ArrayList<Activities> activities;
    private ArrayList<Restaurants> restaurants;
    
    public travelPlanner (String destination, double budget, int days){
        this.destination = destination;
        this.budget = budget;
        this.days = days;
        activities = new ArrayList<>();
        restaurants = new ArrayList<>();
    }
    
    public void addActivity(Activities activity){
        activities.add(activity);
    }
    
    public void removeActivity(Activities activity){
        activities.remove(activity);
    }
    
    public ArrayList<Activities> getActivities(){
        return activities;
    }
    
    public void addRestaurant(Restaurants Restaurant){
        restaurants.add(Restaurant);
    }
    
    public void removeRestaurant(Restaurants Restaurant){
        restaurants.remove(Restaurant);
    }
    
    public ArrayList<Restaurants> getRestaurants(){
        return restaurants;
    }
    
    public String getDestination (){return destination;}
    public int getDays(){return days;}
    public double getBudget(){return budget;}
    
    public void setDestination(String destination){this.destination = destination;}
    public void setDays(int days){this.days = days;}
    public void setBudget(double budget){this.budget = budget;}
    
    
    public double totalCost(){
       double cost1 = 0;
       double cost2 = 0;
        for (Activities activity: activities){
            cost1 = cost1 + activity.getCost();
        }
        for (Restaurants restaurant: restaurants){
            cost2 = cost2 + restaurant.getCpp();
        }
        
        return cost1 + cost2;
    }
    
    public double remainingBudget(){
        
       return budget - totalCost();
    }
    

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
       
        
        UserInterface x = new UserInterface();
        
    }
    
}
