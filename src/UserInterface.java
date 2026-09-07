
import java.awt.GridLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.awt.BorderLayout;
import java.util.ArrayList;
import javax.swing.JList;
import javax.swing.DefaultListModel;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author ahmed
 */
public class UserInterface {
    
    private JFrame frame;
    private String aiResponse = "";
    private travelPlanner myTrip;
    
    
    public UserInterface(){
        frame = new JFrame("Travel Planner");
        frame.setSize(800,600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        
        JPanel panel = new JPanel(new BorderLayout());
        
        JPanel topPanel = new JPanel(new BorderLayout());
        JPanel centerPanel = new JPanel(new BorderLayout());
        JPanel bottomPanel = new JPanel();
        
        panel.add(topPanel, BorderLayout.NORTH);
        panel.add(centerPanel, BorderLayout.CENTER);
        panel.add(bottomPanel, BorderLayout.SOUTH);
        
        JPanel inputPanel = new JPanel(new GridLayout(3,2,10,10));
        JPanel checkPanel = new JPanel(new GridLayout(2,3,10,5));
        
        JLabel destinationLabel = new JLabel("Destination:");
        inputPanel.add(destinationLabel);
        
        frame.add(panel);
        
        
        JTextField destinationText = new JTextField(15);
        inputPanel.add(destinationText);
        
        JLabel budgetLabel = new JLabel("Budget:");
        inputPanel.add(budgetLabel);
        
        JTextField budgetText = new JTextField(15);
        inputPanel.add(budgetText);
        
        JLabel daysLabel = new JLabel("Days:");
        inputPanel.add(daysLabel);
        
        JTextField daysText = new JTextField(15);
        inputPanel.add(daysText);
        
        JLabel interestsLabel = new JLabel("Interests");
        
        
        JCheckBox food = new JCheckBox("Food");
        JCheckBox sightSeeing = new JCheckBox("SightSeeing");
        JCheckBox outDoors = new JCheckBox("Outdoors");
        JCheckBox shopping = new JCheckBox("Shopping");
        JCheckBox roadTrip = new JCheckBox("RoadTrip");
        
        checkPanel.add(food);
        checkPanel.add(sightSeeing);
        checkPanel.add(outDoors);
        checkPanel.add(shopping);
        checkPanel.add(roadTrip);
        
        topPanel.add(interestsLabel, BorderLayout.NORTH);
        topPanel.add(inputPanel, BorderLayout.CENTER);
        topPanel.add(checkPanel, BorderLayout.SOUTH);
        
        
        
        JTextArea results = new JTextArea();
        JScrollPane scroll = new JScrollPane(results);
        results.setLineWrap(true);
        results.setWrapStyleWord(true);
        
        JPanel listPanel = new JPanel(new GridLayout(2, 1, 5, 5));
        
        DefaultListModel<Activities> activityModel = new DefaultListModel<>();
        JList<Activities> activityList = new JList<>(activityModel);
        
        JScrollPane activityScroll = new JScrollPane(activityList);
        
        DefaultListModel<Restaurants> restaurantModel = new DefaultListModel<>();
        JList<Restaurants> restaurantList = new JList<>(restaurantModel);
        
        JScrollPane restaurantScroll = new JScrollPane(restaurantList);
        
        listPanel.add(activityScroll);
        listPanel.add(restaurantScroll);
        
        centerPanel.add(scroll, BorderLayout.CENTER);
       centerPanel.add(listPanel, BorderLayout.EAST);
        
        JButton createTrip = new JButton("Create Trip");
        bottomPanel.add(createTrip);
        
        createTrip.addActionListener(e -> {
            
            String interests = "";
            
            if (food.isSelected() == true){
               interests = interests + "food, ";
            }
            
            if (sightSeeing.isSelected() == true){
               interests = interests + "Sightseeing, ";
            }
            
            if (outDoors.isSelected() == true){
               interests = interests + "outdoors, ";
            }
            
            if (shopping.isSelected() == true){
               interests = interests + "shopping, ";
            }
            
            if (roadTrip.isSelected() == true){
               interests = interests + "RoadTrip, ";
            }
        
            try{
                String destination = destinationText.getText();
                double budget = Double.parseDouble(budgetText.getText());
                int days = Integer.parseInt(daysText.getText());
                
                myTrip = new travelPlanner(destination, budget, days);
            
                
                
                String prompt = ("Create a " + days + " days travel plan for " + destination +
        ". The Budget is " + budget + " and the interests are " + interests + ". Format"
        + " the response as clean plain text only. Do not use Markdown, tables,"
        + " # headings, or ** bold text. Use simple headings"
        + " and bullet points. Also at the end give me the TOTAL ESTIMATED COST:"
        + " followed by only the number no currency or any other words just the number. Moreover "
        + "in front of every label where it says which day it is I want you to write the "
        + "estimated cost of that day as well which all add up to the total estimated cost. "

        + "For every activity in the itinerary, include a separate line exactly in this format: "
        + "ACTIVITY: name | LOCATION: location | COST: number. "

        + "For every restaurant in the itinerary, include a separate line exactly in this format: "
        + "RESTAURANT: name | LOCATION: location | COST: number | TYPE: cuisine. "

        + "The COST must contain only a number with no currency symbol or words.");
                
              
                
                AIUsage ai = new AIUsage();
                aiResponse = ai.createTrip(prompt);
                
                ArrayList<Activities> generatedActivities = ai.getActivities(aiResponse);
                ArrayList<Restaurants> generatedRestaurants = ai.getRestaurants(aiResponse);
                
                activityModel.clear();
                
                for(Activities activity : generatedActivities){
                    myTrip.addActivity(activity);
                    activityModel.addElement(activity);
                }
                
                restaurantModel.clear();
                
                for(Restaurants restaurant : generatedRestaurants){
                    myTrip.addRestaurant(restaurant);
                    restaurantModel.addElement(restaurant);
                }
                
                double estimatedCost = myTrip.totalCost();
                
                double remainingBudget = myTrip.remainingBudget();
                
                results.setText(aiResponse +"\n\nRemaining Budget: $"+remainingBudget);
                
                
                
                
            }
            catch(NumberFormatException error){
                JOptionPane.showMessageDialog(frame, "Invalid input");
            }
           
           
        });
        
        JButton saveTrip = new JButton("Save");
        bottomPanel.add(saveTrip);
        saveTrip.addActionListener(e ->{
            
            try{
                
                FileWriter writer = new FileWriter("savedTrip.txt");
                writer.write(aiResponse);
                writer.close();
                
                JOptionPane.showMessageDialog(frame, "Trip Saved!");
                
            }catch(IOException error){
                JOptionPane.showMessageDialog(frame,"could not save trip");
                
            }
            
            
        });
        
        JButton loadTrip = new JButton("Load Trip");
        bottomPanel.add(loadTrip);
        
        loadTrip.addActionListener(e->{
            
            try{
                
                aiResponse = Files.readString(Path.of("savedTrip.txt"));
                results.setText(aiResponse);
                
            }catch(IOException error){
                JOptionPane.showMessageDialog(frame, "Could not Load Trip");
            
            }
            
        });
        
        JButton removeActivity = new JButton("Remove Activity");
        bottomPanel.add(removeActivity);
        
        removeActivity.addActionListener(e -> {

            Activities selectedActivity = activityList.getSelectedValue();

            if (selectedActivity != null) {

                myTrip.removeActivity(selectedActivity);
                activityModel.removeElement(selectedActivity);

                double newTotal = myTrip.totalCost();
                double newRemaining = myTrip.remainingBudget();

                results.setText(aiResponse
                    + "\n\nUpdated Total Cost: $" + newTotal
                    + "\nRemaining Budget: $" + newRemaining);
            }
        });
        
        JButton removeRestaurant = new JButton("Remove Restaurant");
        bottomPanel.add(removeRestaurant);

        removeRestaurant.addActionListener(e -> {

            Restaurants selectedRestaurant = restaurantList.getSelectedValue();

            if (selectedRestaurant != null) {

                myTrip.removeRestaurant(selectedRestaurant);
                restaurantModel.removeElement(selectedRestaurant);

                double newTotal = myTrip.totalCost();
                double newRemaining = myTrip.remainingBudget();

                results.setText(aiResponse
                    + "\n\nUpdated Total Cost: $" + newTotal
                    + "\nRemaining Budget: $" + newRemaining);
            }
        });
       
        frame.setVisible(true);
    }
}
