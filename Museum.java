/*
   Class Name:       Museum.java
   Authors:          Jerry & William
   Creation Date:    2020-6-5
   Course:           ICS4U1-02
   Purpose:          This class represents a museum and manages all its operations.         
*/

import java.util.ArrayList;
import java.util.*;

public class Museum
{
   private static final int VISITOR_BASE_ID = 100000;       // ID of the first visitor added
   private static final int ARTIFACT_BASE_ID = 10000;       // ID of the first artifact added
   private static final int EXHIBIT_BASE_ID = 1000;         // ID of the museum lobby (first exhibit added)
   
   private Date openingDate;                                // opening date of the museum
   private Date currentDate;                                // current date in museum simulation
   private double maxDisplaySpace;                          // total amount of display space available
   private double maxStorageSpace;                          // total amount of storage space available
   private int numCurrentVisitors;                          // current number of visitors inside museum
   private int daysOpenCount;                               // number of days the museum has been open
   private ArrayList <Exhibit> allExhibits;                 // all exhibits in museum stored in an ArrayList
   private ArrayList <Artifact> allArtifacts;               // all artifacts in museum stored in an ArrayList
   private ArrayList <Visitor> allVisitors;                 // all visitors currently in museum stored in an ArrayList
   private Bank bank;                                       // manages the financial aspects of the museum
   private int numVisitorsAdded;                            // total number of visitors added
   private int numExhibitsAdded;                            // total number of exhibits added
   private int numArtifactsAdded;                           // total number of artifacts added
   
   // Constructors
   
   // default constructor if no parameters are entered
   public Museum()
   {
      openingDate = null;
      currentDate = null;
      maxDisplaySpace = 0;
      maxStorageSpace = 0;
      numCurrentVisitors = 0;
      daysOpenCount = 0;
      allExhibits = null;
      allArtifacts = null;
      allVisitors = null;
      bank = null;
      numVisitorsAdded = 0;
      numExhibitsAdded = 0;
      numArtifactsAdded = 0;
   }
   
   // constructor for creating a pre-existing museum, given all fields
   public Museum(Date date1, Date date2, double maxDisplaySpace, double maxStorageSpace, int numCurrentVisitors, int daysOpenCount, ArrayList allExhibits, ArrayList allArtifacts, ArrayList allVisitors, Bank bank)
   {
      Date openingDate = date1;
      Date currentDate = date2;
      this.openingDate = openingDate;
      this.currentDate = currentDate;
      this.maxDisplaySpace = maxDisplaySpace;
      this.maxStorageSpace = maxStorageSpace;
      this.numCurrentVisitors = numCurrentVisitors;
      this.daysOpenCount = daysOpenCount;
      this.allExhibits = allExhibits;
      this.allArtifacts = allArtifacts;
      this.allVisitors = allVisitors;
      this.bank = bank;
      numVisitorsAdded = allVisitors.size();
      numExhibitsAdded = allExhibits.size();
      numArtifactsAdded = allArtifacts.size();
   }
   
   // constructor for creating a brand new museum, only given opening date, max display space, and max storage space
   public Museum(String date, double maxDisplaySpace, double maxStorageSpace)
   {
      Date openingDate = new Date(date);
      this.openingDate = openingDate;
      this.currentDate = openingDate;
      numCurrentVisitors = 0;
      daysOpenCount = 0;
      this.maxDisplaySpace = maxDisplaySpace;
      this.maxStorageSpace = maxStorageSpace;
      allExhibits = new ArrayList();
      allArtifacts = new ArrayList();
      allVisitors = new ArrayList();
      
      ArrayList <Double> dailyRevenue = new ArrayList<Double>();
      ArrayList <Date> dates = new ArrayList<Date>();
      dailyRevenue.trimToSize();
      dates.trimToSize();
      dailyRevenue.add(0.0);
      dates.add(openingDate);
      bank = new Bank(0,dailyRevenue, dates, currentDate);
      
      numVisitorsAdded = 0;
      numExhibitsAdded = 0;
      numArtifactsAdded = 0;
      this.addExhibit("Lobby", "Area to store all new visitors, or visitors not currently at an existing artifact.");
   }
   
   // Accessors
   
   public Date getOpeningDate() {
      return openingDate;
   }
   
   public Date getCurrentDate() {
      return currentDate;
   }
   
   public double getMaxDisplaySpace() {
      return maxDisplaySpace;
   }
   
   public double getMaxStorageSpace() {
      return maxStorageSpace;
   }
        
   public int getNumCurrentVisitors() {
      return numCurrentVisitors;
   }
   
   public int getDaysOpenCount() {
      return daysOpenCount;
   }
   
   public ArrayList <Exhibit> getAllExhibits() {
      return allExhibits;
   }
  
   public ArrayList <Artifact> getAllArtifacts() {
      return allArtifacts;
   }
   
   public ArrayList <Visitor> getAllVisitors() {
      return allVisitors;
   }
        
   public Bank getBank() {
      return bank;
   }
   
   public Exhibit[] getAllExhibitsArray() {
      return allExhibits.toArray(new Exhibit[allExhibits.size()]);
   }

   public Artifact[] getAllArtifactsArray() {
      return allArtifacts.toArray(new Artifact[allArtifacts.size()]);
   }

   public Visitor[] getAllVisitorsArray() {
      return allVisitors.toArray(new Visitor[allVisitors.size()]);
   }
     
   // Mutators
      
   public void setOpeningDate(String date) {
      Date setDate = new Date(date);
      openingDate = setDate;
   }
   
   public void setCurrentDate(String date) {
      Date setDate = new Date(date);
      currentDate = setDate;
   }
     
   public void setMaxDisplaySpace(double space) {
      maxDisplaySpace = space;
   }
   
   public void setMaxStorageSpace(double space) {
      maxStorageSpace = space;
   }
   
   public void setNumCurrentVisitors(int num) {
      numCurrentVisitors = num;
   }
   
   public void setDaysOpenCount(int num) {
      daysOpenCount = num;
   }
   
   public void setAllExhibits(ArrayList allExhibits) {
      this.allExhibits = allExhibits;
   }
   
   public void setAllArtifacts(ArrayList allArtifacts) {
      this.allArtifacts = allArtifacts;
   }
   
   public void setAllVisitors(ArrayList allVisitors) {
      this.allVisitors = allVisitors;
   }
   
   // takes in Bank's fields
   public void setBank(double lifeTimeRevenue, ArrayList <Double> dailyRevenue, ArrayList <Date> dailyRevenueDates, Date currentDate) {
      Bank newBank = new Bank (lifeTimeRevenue, dailyRevenue, dailyRevenueDates, currentDate);
      bank = newBank;
   }
   
   // takes in Bank object
   public void setBank(Bank bank) {
      this.bank = bank;
   } 
   
   // Instance Methods
   
   /* 
   *  Adds a pre-existing visitor to the museum
   *  String firstName - first name of visitor being added
   *  String lastName - last name of visitor being added
   *  int age - age of visitor
   *  Exhibit currentExhibit - the exhibit the visitor is currently in
   *  Artifact currentArtifact - the artifact the visitor is currently in
   */
   public void addVisitor(String firstName, String lastName, int age, Exhibit currentExhibit, Artifact currentArtifact)
   {
      try
      {
         // Check if visitor age is valid               
         if (age >= Child.MIN_AGE)
         {
            Visitor newVisitor;
            
            // Create child/adult/senior objects according to age, uses inheritance from abstract Visitor
            if (age >= Child.MIN_AGE && age <= Child.MAX_AGE)
            {
               newVisitor = new Child (VISITOR_BASE_ID + numVisitorsAdded, firstName, lastName, age, currentExhibit, currentArtifact);
               bank.addRevenue(Child.ENTRANCE_FEE);
            }
            else if (age >= Adult.MIN_AGE && age <= Adult.MAX_AGE)
            {
               newVisitor = new Adult (VISITOR_BASE_ID + numVisitorsAdded, firstName, lastName, age, currentExhibit, currentArtifact);
               bank.addRevenue(Adult.ENTRANCE_FEE);
            }
            else
            {
               newVisitor = new Senior (VISITOR_BASE_ID + numVisitorsAdded, firstName, lastName, age, currentExhibit, currentArtifact);
               bank.addRevenue(Senior.ENTRANCE_FEE);
            }
                     
            // Update museum, exhibit, and artifact info    
            numVisitorsAdded++;
            numCurrentVisitors++;
            allVisitors.add(newVisitor);
            currentExhibit.addVisitor(newVisitor);
            currentArtifact.addVisitor(newVisitor);
            
            System.out.println("Visitor has been successfully added.");
         }
         else
            System.out.println("Age is not valid");
      }
      catch(Exception e)
      {
         System.out.println("Error adding visitor");
      }
   }

   /* 
   *  Adds a brand new visitor to the museum, placed in lobby
   *  String firstName - first name of visitor being added
   *  String lastName - last name of visitor being added
   *  int age - age of visitor
   */
   public void addVisitor(String firstName, String lastName, int age)
   {
      try
      {               
         if (age >= Child.MIN_AGE)
         {
            Visitor newVisitor;
            Exhibit lobby = allExhibits.get(findExhibitIndexByName("Lobby"));
                    
            if (age >= Child.MIN_AGE && age <= Child.MAX_AGE)
            {
               newVisitor = new Child (VISITOR_BASE_ID + numVisitorsAdded, firstName, lastName, age, lobby, null);
               bank.addRevenue(Child.ENTRANCE_FEE);
            }
            else if (age >= Adult.MIN_AGE && age <= Adult.MAX_AGE)
            {
               newVisitor = new Adult (VISITOR_BASE_ID + numVisitorsAdded, firstName, lastName, age, lobby, null);
               bank.addRevenue(Adult.ENTRANCE_FEE);
            }
            else
            {
               newVisitor = new Senior (VISITOR_BASE_ID + numVisitorsAdded, firstName, lastName, age, lobby, null);
               bank.addRevenue(Senior.ENTRANCE_FEE);
            }
                         
            numVisitorsAdded++;
            numCurrentVisitors++;
            allVisitors.add(newVisitor);
            lobby.addVisitor(newVisitor);
            
            System.out.println("Visitor has been successfully added");
         }
         else
            System.out.println("Age is not valid");
      }
      catch(Exception e)
      {
         System.out.println("Error adding visitor");
      }
   }   
   
   /* 
   *  Removes a specific visitor from the museum, given their ID 
   *  int givenId - ID of visitor to be removed
   */
   public void removeVisitor(int givenId)
   {
      try
      {
         int foundIndex = findVisitorIndexById(givenId);
         Visitor foundVisitor = allVisitors.get(foundIndex);
         
         if (foundIndex == -1)
            System.out.println("Visitor with ID " + givenId + " cannot be found");
         else
         {
            allVisitors.remove(foundIndex);    
            (foundVisitor.getCurrentArtifact()).removeVisitor(foundVisitor);
            (foundVisitor.getCurrentExhibit()).removeVisitor(foundVisitor);
            numCurrentVisitors--; 
            System.out.println("Visitor has been successfully removed");
         }
      }
      catch (Exception e)
      {
         System.out.println("Error removing visitor");
      }
   }
   
   /*
   *  Removes a specific visitor from the museum, given their full name
   *  String givenName - full name of visitor to be removed
   */
   public void removeVisitor(String givenName)
   {
      try
      {
         int foundIndex = findVisitorIndexByName(givenName);
         Visitor foundVisitor = allVisitors.get(foundIndex);
      
         if (foundIndex == -1)
            System.out.println("Visitor with name " + givenName + " cannot be found");
         else
         {
            allVisitors.remove(foundIndex);
            (foundVisitor.getCurrentArtifact()).removeVisitor(foundVisitor);
            (foundVisitor.getCurrentExhibit()).removeVisitor(foundVisitor);    
            numCurrentVisitors--; 
            System.out.println("Visitor has been successfully removed");
         }   
      }
      catch (Exception e)
      {
         System.out.println("Error removing visitor");
      }
   }
   
   /*
   *  Adds a brand new exhibit to the museum, given its name and description
   *  String name - name of exhibit
   *  String description - description of exhibit
   */
   
   public void addExhibit(String name, String description)
   {
      try
      {
         Exhibit newExhibit = new Exhibit(name, EXHIBIT_BASE_ID + numExhibitsAdded, description);
         numExhibitsAdded ++;
         allExhibits.add(newExhibit);
         System.out.println("Exhibit has been successfully added");
      }
      catch(Exception e)
      {
         System.out.println("Error adding exhibit");
      }
   }
   
   /*
   *  Removes an exhibit from the museum, given its ID
   *  String exhibitId - ID of exhibit
   */
   
   public void removeExhibit(int exhibitId)
   {
      try
      {
         int foundIndex = findExhibitIndexById(exhibitId);
         Exhibit foundExhibit = allExhibits.get(foundIndex);
         
         if (foundIndex == -1)
            System.out.println("Exhibit with ID " + exhibitId + " cannot be found");
         else
         {  
            // remove all artifacts from Exhibit (thus movig visitors to lobby)
            ArrayList <Artifact> displacedArtifacts = foundExhibit.getArtifactList();
            displacedArtifacts.trimToSize();
            for (int i=0;i < displacedArtifacts.size(); i++)
            {
               this.removeArtifact(displacedArtifacts.get(i).getId());
            }
            allExhibits.remove(foundIndex);
            
            System.out.println("Exhibit has been successfully removed");     
         }
      }
      catch (Exception e)
      {
         System.out.println("Error removing exhibit");
      }
   }
   
   /*
   *  Removes exhibit from museum, given its name
   *  String exhibitName - name of exhibit
   */
   
   public void removeExhibit(String exhibitName)
   {
      int foundIndex = findExhibitIndexByName(exhibitName);
      Exhibit foundExhibit = allExhibits.get(foundIndex);
      
      if (foundIndex == -1)
      {
         System.out.println("Exhibit called " + exhibitName + " cannot be found");
      }
      else
      {  
         // remove all artifacts from Exhibit (thus movig visitors to lobby)
         ArrayList <Artifact> displacedArtifacts = foundExhibit.getArtifactList();
         displacedArtifacts.trimToSize();
         for (int i=0;i < displacedArtifacts.size(); i++)
         {
            this.removeArtifact(displacedArtifacts.get(i).getId());
         }
         allExhibits.remove(foundIndex);
            
         System.out.println("Exhibit has been successfully removed");     
      }   
   }
   
   /*
   *  Adds a new artifact to the museum if there is enough space available
   *  String name - name of artifact
   *  String description - description of artifact
   *  double value - value of artifact
   *  double floorSpace - floor space required for artifact
   *  String dateMadeString - String representation of artifact's date made
   *  String datePurchasedString - String representation of artifact's date purchased
   *  int exhibitId - ID of the exhibit the artifact belongs to
   *  boolean onDisplay - indicates whether or not the artifact is on display
   */
   
   public void addArtifact(String name, String description, double value, double floorSpace, String dateMadeString, String datePurchasedString, int exhibitId, boolean onDisplay)
   {
      try
      {
         Date dateMade = new Date(dateMadeString);
         Date datePurchased = new Date(datePurchasedString);
         int foundIndex = findExhibitIndexById(exhibitId);
         
         if (foundIndex != -1)
         {
            Artifact newArtifact = new Artifact(name, ARTIFACT_BASE_ID + numArtifactsAdded, description, value, floorSpace, dateMade, datePurchased, allExhibits.get(foundIndex), onDisplay);
            
            if((onDisplay == true && floorSpace + displaySpaceUsed() <= maxDisplaySpace) || (onDisplay == false && floorSpace + storageSpaceUsed() <= maxStorageSpace))
            {
               allArtifacts.add(newArtifact);
               (allExhibits.get(foundIndex)).addArtifact(newArtifact);
               numArtifactsAdded++;
               System.out.println("Artifact has been successfully added");
            }
            else
            {
               System.out.println("Artifact cannot be added");
            }
         }
         else
            System.out.println("Artifact with ID " + exhibitId + " cannot be found");
      }
      catch(Exception e)
      {
         System.out.println("Error adding artifact");
      }
   }
   
   /*
   *  Removes an artifact from the museum
   *  int artifactId - ID of artifact to be removed
   */
   
   public void removeArtifact(int artifactId)
   {
      int foundIndex = findArtifactIndexById(artifactId);
      Artifact foundArtifact = allArtifacts.get(foundIndex);
      
      if (foundIndex == -1)
         System.out.println("Artifact with ID " + artifactId + " cannot be found");
      else
      {
         // move all visitors at artifact to lobby        
         ArrayList<Visitor> displacedVisitors = foundArtifact.getCurrentVisitors();
         displacedVisitors.trimToSize();
         for (int i=0; i < displacedVisitors.size();i++)
         {
            this.moveToLobby(displacedVisitors.get(i));  //moves visitor to lobby
         }
         
         // remove artifact from arraylist
         allArtifacts.remove(foundIndex);
         
         // remove artifact from exhibit
         foundArtifact.getExhibitLocation().removeArtifact(foundArtifact);
         
         System.out.println("Artifact has been successfully removed");
      }
   }
   
   /*
   *  Removes an artifact from the museum
   *  String artifactName - name of artifact to be removed
   */
   
   public void removeArtifact(String artifactName)
   {
      int foundIndex = findArtifactIndexByName(artifactName);
      Artifact foundArtifact = allArtifacts.get(foundIndex);
      
      if (foundIndex == -1)
         System.out.println("Artifact called " + artifactName + " cannot be found");
      else
      {
         // move all visitors at artifact to lobby         
         ArrayList<Visitor> displacedVisitors = foundArtifact.getCurrentVisitors();
         displacedVisitors.trimToSize();
         for (int i=0; i < displacedVisitors.size();i++)
         {
            this.moveToLobby(displacedVisitors.get(i));  //moves visitor to lobby
         }
         
         // remove artifact from arraylist
         allArtifacts.remove(foundIndex);
         
         // remove artifact from exhibit
         foundArtifact.getExhibitLocation().removeArtifact(foundArtifact);
         
         System.out.println("Artifact has been successfully removed");
      }
   }
   
   /*
   *  Moves a specific visitor to another artifact
   *  int visitorId - ID of visitor to be moved
   *  int toArtifactId - ID of artifact destination
   *  returns boolean indicating if visitor is successfully moved
   */
   
   public boolean moveVisitor(int visitorId, int toArtifactId)
   {
      try
      {
         // locate visitor and artifact ID
         int visitorFoundIndex = findVisitorIndexById(visitorId);
         int artifactFoundIndex = findArtifactIndexById(toArtifactId);
         
         // boolean indicates if the visitor is successfully moved (different artifact is given)
         boolean moveSuccessful = false;
         
         // variables to store objects
         Visitor movingVisitor;
         Artifact toArtifact;
         Exhibit toExhibit;
         Exhibit originalExhibit = allVisitors.get(visitorFoundIndex).getCurrentExhibit();
         
         // check if IDs given are valid
         
         if (visitorFoundIndex != -1 && artifactFoundIndex != -1)
         {
            movingVisitor = allVisitors.get(visitorFoundIndex);
            toArtifact = allArtifacts.get(artifactFoundIndex);
            
            // move visitor to another object (updates visitor info)
            moveSuccessful = movingVisitor.moveVisitor(toArtifact);
            
            // if visitor is successfully moved
            if (moveSuccessful)
            {
               // remove visitor from their initial artifact
               // add visitor to their new artifact
               
               (movingVisitor.getCurrentArtifact()).removeVisitor(movingVisitor);
               toArtifact.addVisitor(movingVisitor);
                
               // if artifact is located in another exhibit
               if (toArtifact.getExhibitLocation().equals(originalExhibit) == false)
               {
                  toExhibit = toArtifact.getExhibitLocation();
                  
                  // remove visitor from their initial exhibit
                  // add visitor to their new exhibit
                  
                  (movingVisitor.getCurrentExhibit()).removeVisitor(movingVisitor);
                  toExhibit.addVisitor(movingVisitor);
               }
            }
            
            return true;
         }
         else
            return false;
      }
      catch(Exception e)
      { 
         return false;
      }
   }
   
   /*
   *  Moves a specific visitor to another artifact
   *  String visitorName - name of visitor to be moved
   *  String toArtifactName - name of artifact destination
   *  returns boolean indicating if visitor is successfully moved
   */
   public boolean moveVisitor(String visitorName, String toArtifactName)
   {
      try
      {
         // locate visitor and artifact ID
         int visitorFoundIndex = findVisitorIndexByName(visitorName);
         int artifactFoundIndex = findArtifactIndexByName(toArtifactName);
         
         // boolean indicates if the visitor is successfully moved (different artifact is given)
         boolean moveSuccessful = false;
         
         // variables to store objects
         Visitor movingVisitor;
         Artifact toArtifact;
         Exhibit toExhibit;
         Exhibit originalExhibit = allVisitors.get(visitorFoundIndex).getCurrentExhibit();
         
         // check if IDs given are valid
         
         if (visitorFoundIndex != -1 && artifactFoundIndex != -1)
         {
            movingVisitor = allVisitors.get(visitorFoundIndex);
            toArtifact = allArtifacts.get(artifactFoundIndex);
            
            // move visitor to another object (updates visitor info)
            moveSuccessful = movingVisitor.moveVisitor(toArtifact);
            
            // if visitor is successfully moved
            if (moveSuccessful)
            {
               // remove visitor from their initial artifact
               // add visitor to their new artifact
               
               (movingVisitor.getCurrentArtifact()).removeVisitor(movingVisitor);
               toArtifact.addVisitor(movingVisitor);
                  
               // if artifact is located in another exhibit
               if (toArtifact.getExhibitLocation().equals(originalExhibit) == false)
               {
                  toExhibit = toArtifact.getExhibitLocation();
                  
                  // remove visitor from their initial exhibit
                  // add visitor to their new exhibit
                  
                  (movingVisitor.getCurrentExhibit()).removeVisitor(movingVisitor);
                  toExhibit.addVisitor(movingVisitor);
               }
               
               return true;
            }
            
            return false;
         }
         else
            return false;
      }
      catch(Exception e)
      { 
         return false;
      }
   }
   
   /*
   *  Moves a specific visitor to the lobby
   *  returns boolean indicating if visitor is successfully moved
   */
   public boolean moveToLobby(Visitor visitor)
   {
      return (visitor.moveToLobby(allExhibits.get(findExhibitIndexByName("Lobby"))));
   }
   

   // Closes the museum for the day
   public void closeForTheDay()
   {
      // add the current date to Bank's date array
      (bank.getDailyRevenueDates()).add(currentDate);
          
      // update days open and current date for Museum
      // set tomorrow's initial daily revenue as $0 for Bank
      daysOpenCount++;
      currentDate.progressDate();
      (bank.getDailyRevenue()).add(0.0);
          
      // clear visitor array in Museum
      allVisitors.clear();
          
      // clear currentVisitors in all Artifacts and Exhibits
      for (int i = 0; i < allExhibits.size(); i ++)
      {
         (allExhibits.get(i)).clearVisitors();
      }
          
      for (int i = 0; i < allArtifacts.size(); i ++)
      {
         (allArtifacts.get(i)).clearVisitors();
      }
          
   }
   
   // Returns the number of total exhibits in the museum
   public int numTotalExhibits()
   {
      allExhibits.trimToSize();
      return allExhibits.size() - 1;
   }
   
   // Returns the number of total artifacts in the museum
   public int numTotalArtifacts()
   {
      allArtifacts.trimToSize();
      return allArtifacts.size();
   }
   
   // Returns the number of total visitors in the museum
   public int numTotalVisitors()
   {
      allVisitors.trimToSize();
      return allVisitors.size();
   }
   
   // Prints identifier of all visitors in museum
   public void printAllVisitors()
   {
      allVisitors.trimToSize();
      for(int i = 0; i < allVisitors.size(); i ++)
      {
         System.out.println((allVisitors.get(i)));
      }
   }
   
   /*
   *  Prints a specific visitor's identifier and location, given their ID
   *  givenId - ID of visitor being printed
   */
   public void printSpecificVisitor(int givenId)
   {
      int foundIndex = findVisitorIndexById(givenId);
      
      if (foundIndex == -1)
         System.out.println("Visitor with ID " + givenId + " cannot be found.");
      else
      {
         System.out.println((allVisitors.get(foundIndex)));
         System.out.println("Current location: \n" + allVisitors.get(foundIndex).getCurrentArtifact().identifierToString());
      }
   }
   
   /*
   *  Prints a specific visitor's identifier and location, given their full name
   *  givenName - full name of visitor being printed
   */
   public void printSpecificVisitor(String givenName)
   {
      int foundIndex = findVisitorIndexByName(givenName);
      
      if (foundIndex == -1)
         System.out.println("Visitor with ID " + givenName + " cannot be found");
      else
      {
         System.out.println((allVisitors.get(foundIndex)));
         System.out.println("Current location: \n" + allVisitors.get(foundIndex).getCurrentArtifact().identifierToString());
      }
   }
   
   /*
   *  Print all the artifacts a visitor has visited, given their ID
   *  givneId - ID of visitor
   */
   public void printVisitedArtifacts(int givenId)
   {
      int foundIndex = findVisitorIndexById(givenId);
      Visitor foundVisitor;
      ArrayList <Artifact> artifactsVisited;
      
      if (foundIndex == -1)
         System.out.println("Visitor with ID " + givenId + " cannot be found");
      else
      {
         foundVisitor = allVisitors.get(foundIndex);
         artifactsVisited = foundVisitor.getVisitedArtifacts();
         
         for (int i = 0; i < artifactsVisited.size(); i ++)
         {
            System.out.print((artifactsVisited.get(i)).identifierToString());
         }
      }
   }
   
   /*
   *  Print all the artifacts a visitor has visited, given their name
   *  givneName - name of visitor
   */
   public void printVisitedArtifacts(String givenName)
   {
      int foundIndex = findVisitorIndexByName(givenName);
      Visitor foundVisitor;
      ArrayList <Artifact> artifactsVisited;
      
      if (foundIndex == -1)
         System.out.println("Visitor with name " + givenName + " cannot be found");
      else
      {
         foundVisitor = allVisitors.get(foundIndex);
         artifactsVisited = foundVisitor.getVisitedArtifacts();
         
         for (int i = 0; i < artifactsVisited.size(); i ++)
         {
            System.out.print((artifactsVisited.get(i)).identifierToString());
         }
      }   
   }
   
   // Returns the number of adults in the museum
   public int numAdults()
   {
      int adultCount = 0;
      
      allVisitors.trimToSize();
      for (int i = 0; i < allVisitors.size(); i ++)
      {
         if (allVisitors.get(i) instanceof Adult)
            adultCount++;
      }
      
      return adultCount;
   }
   
   // Returns the number of children in the museum
   public int numChildren()
   {
      int childCount = 0;
      
      allVisitors.trimToSize();
      for (int i = 0; i < allVisitors.size(); i ++)
      {
         if (allVisitors.get(i) instanceof Child)
            childCount++;
      }
      
      return childCount;
   }
   
   // Returns the number of seniors in the museum
   public int numSeniors()
   {
      int seniorCount = 0;
      
      allVisitors.trimToSize();
      for (int i = 0; i < allVisitors.size(); i ++)
      {
         if (allVisitors.get(i) instanceof Senior)
            seniorCount++;
      }
      
      return seniorCount;
   }
   
   // Returns the average age of all current visitors in the museum
   public double averageAge()
   {
      int ageSum = 0;
      int visitorCount = 0;
      double average;
      
      allVisitors.trimToSize();
      for (int i = 0; i < allVisitors.size(); i ++)
      {
         ageSum += (allVisitors.get(i)).getAge();
         visitorCount++;
      }
      
      average = (double) ageSum / visitorCount;
      return average;
   }
   
   // Prints the identifiers of all adults in the museum
   public void printAllAdults()
   {
      boolean atLeastOne = false;
      
      allVisitors.trimToSize();
      for (int i = 0; i < allVisitors.size(); i ++)
      {
         if (allVisitors.get(i) instanceof Adult)
         {
            System.out.println((allVisitors.get(i)).identifierToString());
            atLeastOne = true;
         }
      }
      
      if (!atLeastOne)
         System.out.println("There are no adult visitors currently in the museum");
   }
   
   // Prints the identifiers of all children in the museum
   public void printAllChildren()
   {
      boolean atLeastOne = false;
      
      allVisitors.trimToSize();
      for (int i = 0; i < allVisitors.size(); i ++)
      {
         if (allVisitors.get(i) instanceof Child)
         {
            System.out.println((allVisitors.get(i)).identifierToString());
            atLeastOne = true;
         }
      }
      
      if (!atLeastOne)
         System.out.println("There are no child visitors currently in the museum");
   }
   
   // Prints the identifiers of all seniors in the museum
   public void printAllSeniors()
   {
      boolean atLeastOne = false;
      
      allVisitors.trimToSize();
      for (int i = 0; i < allVisitors.size(); i ++)
      {
         if (allVisitors.get(i) instanceof Senior)
         {
            System.out.println((allVisitors.get(i)).identifierToString());
            atLeastOne = true;
         }
      }
      
      if (!atLeastOne)
         System.out.println("There are no senior visitors currently in the museum");
   }
   
   // Prints current visitors from lowest to highest age, sorted using selection sort
   public void printVisitorsAscendingAge()
   {
      int min;
      int minIndex;
   
      allVisitors.trimToSize();
      for (int i = 0; i < allVisitors.size(); i ++)
      {
         min = (allVisitors.get(i)).getAge(); 
         minIndex = i;
       
         for (int j = i + 1; j < allVisitors.size(); j ++)
         {
            if ((allVisitors.get(j)).getAge() < min)
            {
               min = (allVisitors.get(j)).getAge();
               minIndex = j;
            }
         }
       
         Collections.swap(allVisitors,i,minIndex);
         System.out.println((allVisitors.get(i)).identifierToString());
      }
   }
   
   // Prints current visitors from highest to lowest age, sorted using selection sort
   public void printVisitorsDescendingAge()
   {
      int max;
      int maxIndex;
   
      allVisitors.trimToSize();
      for (int i = 0; i < allVisitors.size(); i ++)
      {
         max = (allVisitors.get(i)).getAge(); 
         maxIndex = i;
       
         for (int j = i + 1; j < allVisitors.size(); j ++)
         {
            if ((allVisitors.get(j)).getAge() > max)
            {
               max = (allVisitors.get(j)).getAge();
               maxIndex = j;
            }
         }
       
         Collections.swap(allVisitors,i,maxIndex);
         System.out.println((allVisitors.get(i)).identifierToString());
      }
   }
   
   /*
   *  Prints all visitors at a given exhibit, given its ID
   *  int exhibitId - ID of exhibit
   */
   public void printVisitorsAtExhibit(int exhibitId)
   {
      int foundIndex = findExhibitIndexById(exhibitId);
      Exhibit foundExhibit;
      ArrayList <Visitor> currentVisitors;
      
      allVisitors.trimToSize();
      if (foundIndex == -1)
         System.out.println("The exhibit with ID " + exhibitId + " cannot be found");
      else
      {
         foundExhibit = allExhibits.get(foundIndex);
         currentVisitors = foundExhibit.getCurrentVisitors();
         
         for (int i = 0; i < currentVisitors.size(); i ++)
         {
            System.out.println((currentVisitors.get(i)).identifierToString());
         }
      }
   }
   
   /*
   *  Prints all visitors at a given exhibit, given its name
   *  String exhibitName - name of exhibit
   */
   public void printVisitorsAtExhibit(String exhibitName)
   {
      int foundIndex = findExhibitIndexByName(exhibitName);
      Exhibit foundExhibit;
      ArrayList <Visitor> currentVisitors;
      
      if (foundIndex == -1)
         System.out.println("The exhibit called " + exhibitName + " cannot be found");
      else
      {
         foundExhibit = allExhibits.get(foundIndex);
         currentVisitors = foundExhibit.getCurrentVisitors();
         
         for (int i = 0; i < currentVisitors.size(); i ++)
         {
            System.out.println((currentVisitors.get(i)).identifierToString());
         }
      }
   }
   
   /*
   *  Prints all visitors at a given artifact, given its ID
   *  int artifactId - ID of artifact
   */
   public void printVisitorsAtArtifact(int artifactId)
   {
      int foundIndex = findArtifactIndexById(artifactId);
      Artifact foundArtifact;
      ArrayList <Visitor> currentVisitors;
      
      if (foundIndex == -1)
         System.out.println("The artifact with ID " + artifactId + " cannot be found");
      else
      {
         foundArtifact = allArtifacts.get(foundIndex);
         currentVisitors = foundArtifact.getCurrentVisitors();
         
         for (int i = 0; i < currentVisitors.size(); i ++)
         {
            System.out.println((currentVisitors.get(i)).identifierToString());
         }
      }
   }
   
   /*
   *  Prints all visitors at a given artifact, given its name
   *  String artifactName - name of artifact
   */
   public void printVisitorsAtArtifact(String artifactName)
   {
      int foundIndex = findArtifactIndexByName(artifactName);
      Artifact foundArtifact;
      ArrayList <Visitor> currentVisitors;
      
      if (foundIndex == -1)
         System.out.println("The artifact called " + artifactName + " cannot be found");
      else
      {
         foundArtifact = allArtifacts.get(foundIndex);
         currentVisitors = foundArtifact.getCurrentVisitors();
         
         for (int i = 0; i < currentVisitors.size(); i ++)
         {
            System.out.println((currentVisitors.get(i)).identifierToString());
         }
      }
   }
   
   /*
   *  Uses sequential search to find a specific visitor, given their ID
   *  int visitorId - ID of visitor
   *  Returns Visitor object if found, null if not found
   */
   public Visitor searchVisitorById(int visitorId)
   {
      int foundIndex = findVisitorIndexById(visitorId);
      
      if (foundIndex == -1)
      {
         System.out.println("Visitor with ID " + visitorId + " cannot be found");
         return null;
      }
      else
      {
         return allVisitors.get(foundIndex);
      }
   }
   
   /*
   *  Uses sequential search to find a specific visitor, given their full name
   *  String visitorName - full name of visitor
   *  Returns Visitor object if found, null if not found
   */
   public Visitor searchVisitorByName(String visitorName)
   {
      int foundIndex = findVisitorIndexByName(visitorName);
      
      if (foundIndex == -1)
      {
         System.out.println("Visitor with name " + visitorName + " cannot be found");
         return null;
      }
      else
      {
         return allVisitors.get(foundIndex);
      }
   }
   
   /*
   *  Finds the visitor with the greater age between two visitors, given their IDs
   *  int visitorId1 - ID of one visitor
   *  int visitorId2 - ID of another visitor
   *  returns Visitor object with greater age
   */
   public Visitor greaterAgeBetweenVisitors(int visitorId1, int visitorId2)
   {
      int index1 = findVisitorIndexById(visitorId1);
      int index2 = findVisitorIndexById(visitorId2);
      Visitor visitor1;
      Visitor visitor2;
      
      if (index1 != -1 && index2 != -1)
      {
         visitor1 = allVisitors.get(index1);
         visitor2 = allVisitors.get(index2);
         
         if (visitor1.compareAge(visitor2) > 0)
            return visitor1;
         else if (visitor1.compareAge(visitor2) < 0)
            return visitor2;
         else
         {
            System.out.println("Both visitors have the same age.");
            return null;
         }
      }
      else
      {
         System.out.println("Visitor ID(s) entered are not valid");
         return null;
      }
   }

   /*
   *  Finds the visitor with the greater age between two visitors, given their names
   *  String visitorName1 - full name of one visitor
   *  String visitorName2 - full name of another visitor
   *  returns Visitor object with greater age
   */
   public Visitor greaterAgeBetweenVisitors(String visitorName1, String visitorName2)
   {
      int index1 = findVisitorIndexByName(visitorName1);
      int index2 = findVisitorIndexByName(visitorName2);
      Visitor visitor1;
      Visitor visitor2;
      
      if (index1 != -1 && index2 != -1)
      {
         visitor1 = allVisitors.get(index1);
         visitor2 = allVisitors.get(index2);
         
         if (visitor1.compareAge(visitor2) > 0)
            return visitor1;
         else if (visitor1.compareAge(visitor2) < 0)
            return visitor2;
         else
         {
            System.out.println("Both visitors have the same age.");
            return null;
         }
      }
      else
      {
         System.out.println("Visitor name(s) entered are not valid");
         return null;
      }
   }
   
   /*
   *  Finds the visitor with the greater number of exhibits visited between two visitors, given their IDs
   *  int visitorId1 - ID of one visitor
   *  int visitorId2 - ID of another visitor
   *  returns Visitor object with greater number of exhibits visited
   */
   public Visitor greaterNumOfExhibitsVisited(int visitorId1, int visitorId2)
   {
      int index1 = findVisitorIndexById(visitorId1);
      int index2 = findVisitorIndexById(visitorId2);
      Visitor visitor1;
      Visitor visitor2;
      
      if (index1 != -1 && index2 != -1)
      {
         visitor1 = allVisitors.get(index1);
         visitor2 = allVisitors.get(index2);
         
         if (visitor1.compareExhibitVisits(visitor2) > 0)
            return visitor1;
         else if (visitor1.compareExhibitVisits(visitor2) < 0)
            return visitor2;
         else
         {
            System.out.println("Both visitors have the same number of exhibits visited.");
            return null;
         }
      }
      else
      {
         System.out.println("Visitor ID(s) entered are not valid");
         return null;
      }
   }
   
   /*
   *  Finds the visitor with the greater number of exhibits visited between two visitors, given their names
   *  String visitorName1 - name of one visitor
   *  String visitorName2 - name of another visitor
   *  returns Visitor object with greater number of exhibits visited
   */
   public Visitor greaterNumOfExhibitsVisited(String visitorName1, String visitorName2)
   {
      int index1 = findVisitorIndexByName(visitorName1);
      int index2 = findVisitorIndexByName(visitorName2);
      Visitor visitor1;
      Visitor visitor2;
      
      if (index1 != -1 && index2 != -1)
      {
         visitor1 = allVisitors.get(index1);
         visitor2 = allVisitors.get(index2);
         
         if (visitor1.compareExhibitVisits(visitor2) > 0)
            return visitor1;
         else if (visitor1.compareExhibitVisits(visitor2) < 0)
            return visitor2;
         else
         {
            System.out.println("Both visitors have the same number of exhibits visited.");
            return null;
         }
      }
      else
      {
         System.out.println("Visitor name(s) entered are not valid");
         return null;
      }
   }
   
   /*
   *  Finds the visitor object with the greater number of artifacts visited, given their IDs
   *  int visitorId1 - ID of one visitor
   *  int visitorId2 - ID of another visitor
   *  returns Visitor object with the greater number of artifacts visited
   */
   public Visitor greaterNumOfArtifactsVisited(int visitorId1, int visitorId2)
   {
      int index1 = findVisitorIndexById(visitorId1);
      int index2 = findVisitorIndexById(visitorId2);
      Visitor visitor1;
      Visitor visitor2;
      
      if (index1 != -1 && index2 != -1)
      {
         visitor1 = allVisitors.get(index1);
         visitor2 = allVisitors.get(index2);
         
         if (visitor1.compareArtifactVisits(visitor2) > 0)
            return visitor1;
         else if (visitor1.compareArtifactVisits(visitor2) < 0)
            return visitor2;
         else
         {
            System.out.println("Both visitors have the same number of artifact visits");
            return null;
         }
      }
      else
      {
         System.out.println("Visitor ID(s) entered are not valid");
         return null;
      }
   }

   /*
   *  Finds the visitor object with the greater number of artifacts visited, given their names
   *  String visitorName1 - full name of one visitor
   *  String visitorName2 - full name of another visitor
   *  returns Visitor object with the greater number of artifacts visited
   */
   public Visitor greaterNumOfArtifactsVisited(String visitorName1, String visitorName2)
   {
      int index1 = findVisitorIndexByName(visitorName1);
      int index2 = findVisitorIndexByName(visitorName2);
      Visitor visitor1;
      Visitor visitor2;
      
      if (index1 != -1 && index2 != -1)
      {
         visitor1 = allVisitors.get(index1);
         visitor2 = allVisitors.get(index2);
         
         if (visitor1.compareArtifactVisits(visitor2) > 0)
            return visitor1;
         else if (visitor1.compareArtifactVisits(visitor2) < 0)
            return visitor2;
         else
         {
            System.out.println("Both visitors have the same number of artifact visits");
            return null;
         }
      }
      else
      {
         System.out.println("Visitor ID(s) entered are not valid");
         return null;
      }
   }
   
   // Prints the identifiers of all exhibits in the museum
   public void printAllExhibits()
   {
      allExhibits.trimToSize();
      for (int i = 1; i < allExhibits.size(); i ++)
      {
         System.out.println((allExhibits.get(i)));
      }
   }
   
   /*
   *  Prints the identifiers of all artifacts in a specific exhibit, given its ID
   *  int exhibitId - ID of exhibit
   */
   public void printAllArtifactsInExhibit(int exhibitId)
   {
      int foundIndex = findExhibitIndexById(exhibitId);
      Exhibit foundExhibit;
      ArrayList <Artifact> artifactsInExhibit;
      
      if (foundIndex == -1)
         System.out.println("Exhibit with ID " + exhibitId + " cannot be found");
      else
      {
         foundExhibit = allExhibits.get(foundIndex);
         
         if (foundExhibit.getNumArtifacts() > 0)
         {
             artifactsInExhibit = foundExhibit.getArtifactList();
             
             for (int i = 0; i < artifactsInExhibit.size(); i ++)
             {
                System.out.println((artifactsInExhibit.get(i)).identifierToString());
             }
          }
          else
            System.out.println("Exhibit " + exhibitId + " has no artifacts");
      }
   }

   /*
   *  Prints the identifiers of all artifacts in a specific exhibit, given its name
   *  String exhibitName - name of exhibit
   */
   public void printAllArtifactsInExhibit(String exhibitName)
   {
      int foundIndex = findExhibitIndexByName(exhibitName);
      Exhibit foundExhibit;
      ArrayList <Artifact> artifactsInExhibit;
      
      if (foundIndex == -1)
         System.out.println("Exhibit called " + exhibitName + " cannot be found");
      else
      {
         foundExhibit = allExhibits.get(foundIndex);
         
         if (foundExhibit.getNumArtifacts() > 0)
         {
             artifactsInExhibit = foundExhibit.getArtifactList();
             
             for (int i = 0; i < artifactsInExhibit.size(); i ++)
             {
                System.out.println((artifactsInExhibit.get(i)).identifierToString());
             }
          }
          else
            System.out.println(exhibitName + " has no artifacts");
      }
   }
   
   /*
   *  Prints the identifier of a specific exhibit, given its ID
   *  int exhibitId - ID of exhibit
   */
   public void printSpecificExhibit(int exhibitId)
   {
      int foundIndex = findExhibitIndexById(exhibitId);
      
      if (foundIndex == -1)
         System.out.println("Exhibit with ID " + exhibitId + " cannot be found");
      else
         System.out.println((allExhibits.get(foundIndex)));
   }
   
   /*
   *  Prints the identifier of a specific exhibit, given its name
   *  String exhibitName - name of exhibit
   */
   public void printSpecificExhibit(String exhibitName)
   {
      int foundIndex = findExhibitIndexByName(exhibitName);
      
      if (foundIndex == -1)
         System.out.println("Exhibit called " + exhibitName + " cannot be found");
      else
         System.out.println((allExhibits.get(foundIndex)));
   }
   
   /*
   *  Prints the identifier of all visitors in an exhibit, given its ID
   *  int exhibitId - ID of exhibit
   */
   public void printAllVisitorsInExhibit(int exhibitId)
   {
      int foundIndex = findExhibitIndexById(exhibitId);
      Exhibit foundExhibit;
      ArrayList <Visitor> visitorsInExhibit;
      
      if (foundIndex == -1)
         System.out.println("Exhibit with ID " + exhibitId + " cannot be found");
      else
      {
         foundExhibit = allExhibits.get(foundIndex);
         visitorsInExhibit = foundExhibit.getCurrentVisitors();
         
         for (int i = 0; i < visitorsInExhibit.size(); i ++)
         {
            System.out.println((visitorsInExhibit.get(i)).identifierToString());
         }
      }
   }
   
   /*
   *  Prints the identifier of all visitors in an exhibit, given its name
   *  String exhibitName - name of exhibit
   */
   public void printAllVisitorsInExhibit(String exhibitName)
   {
      int foundIndex = findExhibitIndexByName(exhibitName);
      Exhibit foundExhibit;
      ArrayList <Visitor> visitorsInExhibit;
      
      if (foundIndex == -1)
         System.out.println("Exhibit called " + exhibitName + " cannot be found");
      else
      {
         foundExhibit = allExhibits.get(foundIndex);
         visitorsInExhibit = foundExhibit.getCurrentVisitors();
         
         for (int i = 0; i < visitorsInExhibit.size(); i ++)
         {
            System.out.println((visitorsInExhibit.get(i)).identifierToString());
         }
      }
   }
   
   /*
   *  Finds the number of artifacts in an exhibit, given its ID
   *  int exhibitId - ID of exhibit
   *  Returns the length of artifact array in exhibit
   */
   public int numArtifactsInExhibit(int exhibitId)
   {
      int foundIndex = findExhibitIndexById(exhibitId);
      
      if (foundIndex == -1)
      {
         System.out.println("Exhibit with ID " + exhibitId + " cannot be found");
         return -1;
      }
      else
         return (allExhibits.get(foundIndex)).getNumArtifacts();
   }

   /*
   *  Finds the number of artifacts in an exhibit, given its name
   *  String exhibitName - name of exhibit
   *  Returns the length of artifact array in exhibit
   */
   public int numArtifactsInExhibit(String exhibitName)
   {
      int foundIndex = findExhibitIndexByName(exhibitName);
      
      if (foundIndex == -1)
      {
         System.out.println("Exhibit called " + exhibitName + " cannot be found");
         return -1;
      }
      else
         return (allExhibits.get(foundIndex)).getNumArtifacts();
   }
   
   /*
   *  Finds the number of visitors in an exhibit, given its ID
   *  int exhibitId - ID of exhibit
   *  Returns the length of visitor array in exhibit
   */
   public int numVisitorsInExhibit(int exhibitId)
   {
      int foundIndex = findExhibitIndexById(exhibitId);
      
      if (foundIndex == -1)
      {
         System.out.println("Exhibit with ID " + exhibitId + " cannot be found");
         return -1;
      }
      else
         return (allExhibits.get(foundIndex)).getNumCurrentVisitors();
   }
   
   /*
   *  Finds the number of visitors in an exhibit, given its name
   *  String exhibitName - name of exhibit
   *  Returns the length of visitor array in exhibit
   */
   public int numVisitorsInExhibit(String exhibitName)
   {
      int foundIndex = findExhibitIndexByName(exhibitName);
      
      if (foundIndex == -1)
      {
         System.out.println("Exhibit called " + exhibitName + " cannot be found");
         return -1;
      }
      else
         return (allExhibits.get(foundIndex)).getNumCurrentVisitors();
   }
   
   // Prints the identifiers of all exhibits currently on display
   public void printExhibitsCurrentlyOnFloor()
   {  
      allExhibits.trimToSize();
      for (int i = 1; i < allExhibits.size(); i ++)
      {
         if ((allExhibits.get(i)).getOnDisplay())
            System.out.println((allExhibits.get(i)).identifierToString());
      }
   }
   
   // Prints the identifiers of all exhibits currently in storage
   public void printExhibitsInStorage()
   {
      allExhibits.trimToSize();
      for (int i = 1; i < allExhibits.size(); i ++)
      {
         if ((allExhibits.get(i)).getOnDisplay() == false)
            System.out.println((allExhibits.get(i)).identifierToString());
      }
   }

   // Prints all exhibits from lowest to highest value, using selection sort
   public void printExhibitsAscendingValue()
   {
      double min;
      int minIndex;
      
      allExhibits.trimToSize();
      for (int i = 1; i < allExhibits.size(); i ++)
      {
         min = (allExhibits.get(i)).getValue(); 
         minIndex = i;
       
         for (int j = i + 1; j < allExhibits.size(); j ++)
         {
            if ((allExhibits.get(j)).getValue() < min)
            {
               min = (allExhibits.get(j)).getValue();
               minIndex = j;
            }
         }
       
         Collections.swap(allExhibits,i,minIndex);
         System.out.println((allExhibits.get(i)).identifierToString());
      }
   }
   
   // Prints all exhibits from highest to lowest value, using selection sort
   public void printExhibitsDescendingValue()
   {
      double max;
      int maxIndex;
   
      allExhibits.trimToSize();
      for (int i = 1; i < allExhibits.size(); i ++)
      {
         max = (allExhibits.get(i)).getValue(); 
         maxIndex = i;
       
         for (int j = i + 1; j < allExhibits.size(); j ++)
         {
            if ((allExhibits.get(j)).getValue() > max)
            {
               max = (allExhibits.get(j)).getValue();
               maxIndex = j;
            }
         }
       
         Collections.swap(allExhibits,i,maxIndex);
         System.out.println((allExhibits.get(i)).identifierToString());
      }
   }
   
   // Prints all exhibits from lowest to highest required floor space, using selection sort
   public void printExhibitsAscendingFloorSpace()
   {
      double min;
      int minIndex;
   
      allExhibits.trimToSize();
      for (int i = 1; i < allExhibits.size(); i ++)
      {
         min = (allExhibits.get(i)).getFloorSpace(); 
         minIndex = i;
       
         for (int j = i + 1; j < allExhibits.size(); j ++)
         {
            if ((allExhibits.get(j)).getFloorSpace() < min)
            {
               min = (allExhibits.get(j)).getFloorSpace();
               minIndex = j;
            }
         }
       
         Collections.swap(allExhibits,i,minIndex);
         System.out.println((allExhibits.get(i)).identifierToString());
      }
   }
   
   // Prints all exhibits from highest to lowest required floor space, using selection sort
   public void printExhibitsDescendingFloorSpace()
   {
      double max;
      int maxIndex;
   
      allExhibits.trimToSize();
      for (int i = 1; i < allExhibits.size(); i ++)
      {
         max = (allExhibits.get(i)).getFloorSpace(); 
         maxIndex = i;
       
         for (int j = i + 1; j < allExhibits.size(); j ++)
         {
            if ((allExhibits.get(j)).getFloorSpace() > max)
            {
               max = (allExhibits.get(j)).getFloorSpace();
               maxIndex = j;
            }
         }
       
         Collections.swap(allExhibits,i,maxIndex);
         System.out.println((allExhibits.get(i)).identifierToString());
      }
   }
   
   // Prints all exhibits from lowest to highest number of current visitors
   public void printExhibitsAscendingNumVisitors()
   {
      int min;
      int minIndex;
   
      allExhibits.trimToSize();
      for (int i = 0; i < allExhibits.size(); i ++)
      {
         min = (allExhibits.get(i)).getNumCurrentVisitors(); 
         minIndex = i;
       
         for (int j = i + 1; j < allExhibits.size(); j ++)
         {
            if ((allExhibits.get(j)).getNumCurrentVisitors() < min)
            {
               min = (allExhibits.get(j)).getNumCurrentVisitors();
               minIndex = j;
            }
         }
       
         Collections.swap(allExhibits,i,minIndex);
         System.out.println((allExhibits.get(i)).identifierToString());
      }
   }
   
   // Prints all exhibits from highest to lowest number of current visitors
   public void printExhibitsDescendingNumVisitors()
   {
      int max;
      int maxIndex;
   
      allExhibits.trimToSize();
      
      for (int i = 0; i < allExhibits.size(); i ++)
      {
         max = (allExhibits.get(i)).getNumCurrentVisitors();
         maxIndex = i;
       
         for (int j = i + 1; j < allExhibits.size(); j ++)
         {
            if ((allExhibits.get(j)).getNumCurrentVisitors() > max)
            {
               max = (allExhibits.get(j)).getNumCurrentVisitors();
               maxIndex = j;
            }
         }
       
         Collections.swap(allExhibits,i,maxIndex);
         System.out.println((allExhibits.get(i)).identifierToString());
      }
   }
   
   // Prints all exhibits in alphabetical order (a-z)
   public void printExhibitAlpha()
   {
      String min;
      int minIndex;
      
      allExhibits.trimToSize();
      for (int i = 1; i < allExhibits.size(); i ++)
      {
         min = (allExhibits.get(i)).getName(); 
         minIndex = i;
       
         for (int j = i + 1; j < allExhibits.size(); j ++)
         {
            if (((allExhibits.get(j)).getName()).compareToIgnoreCase(min) < 0)
            {
               min = (allExhibits.get(j)).getName();
               minIndex = j;
            }
         }
       
         Collections.swap(allExhibits,i,minIndex);
         System.out.println((allExhibits.get(i)).identifierToString());
      }
   }
   
   // Prints all exhibits in reverse alphabetical order (z-a)
   public void printExhibitsReverseAlpha()
   {
      String max;
      int maxIndex;
      
      allExhibits.trimToSize();
      for (int i = 1; i < allExhibits.size(); i ++)
      {
         max = (allExhibits.get(i)).getName(); 
         maxIndex = i;
       
         for (int j = i + 1; j < allExhibits.size(); j ++)
         {
            if (((allExhibits.get(j)).getName()).compareToIgnoreCase(max) > 0)
            {
               max = (allExhibits.get(j)).getName();
               maxIndex = j;
            }
         }
       
         Collections.swap(allExhibits,i,maxIndex);
         System.out.println((allExhibits.get(i)).identifierToString());
      }
   }
   
   /*
   *  Uses sequential sort to find an exhibit, given its ID
   *  int exhibitId - ID of exhibit
   *  Returns exhibit object if found, null if not found
   */
   public Exhibit searchExhibitById(int exhibitId)
   {
      int foundIndex = findExhibitIndexById(exhibitId);
      
      if (foundIndex == -1)
      {
         System.out.println("Exhibit with ID " + exhibitId + " cannot be found");
         return null;
      }
      else
         return allExhibits.get(foundIndex);
   }
   
   /*
   *  Uses sequential sort to find an exhibit, given its name
   *  String exhibitName - name of exhibit
   *  Returns exhibit object if found, null if not found
   */
   public Exhibit searchExhibitByName(String exhibitName)
   {
      int foundIndex = findExhibitIndexByName(exhibitName);
      
      if (foundIndex == -1)
      {
         System.out.println("Exhibit called " + exhibitName + " cannot be found");
         return null;
      }
      else
         return allExhibits.get(foundIndex);
   }
   
   /*
   *  Prints all exhibits exceeding a given value
   *  double givenValue - value exhibit must exceed to be printed
   */
   public void printExhibitsExceedingValue(double givenValue)
   {
      boolean atLeastOne = false;
      
      allExhibits.trimToSize();
      for (int i = 1; i < allExhibits.size(); i ++)
      {  
         if ((allExhibits.get(i)).valueExceeds(givenValue))
         {
            System.out.println((allExhibits.get(i)).identifierToString());
            atLeastOne = true;
         }
      }
      
      if (!atLeastOne)
         System.out.println("No exhibits meet the given criteria");
   }
   
   /*
   *  Prints all exhibits within two given values
   *  double lowerbound - value exhibit must exceed to be printed
   *  double upperbound - value exhibit must be under to be printed
   */
   public void printExhibitsWithinValue(double lowerbound, double upperbound)
   {
      boolean atLeastOne = false;
      
      allExhibits.trimToSize();
      for (int i = 1; i < allExhibits.size(); i ++)
      {
         if ((allExhibits.get(i)).valueInRange(lowerbound, upperbound))
         {
            System.out.println((allExhibits.get(i)).identifierToString());
            atLeastOne = true;
         }
      }
      
      if (!atLeastOne)
         System.out.println("No exhibits meet the given criteria");
   }
   
   /*
   *  Prints all exhibits exceeding a certain number of visitors
   *  int givenNum - number of visitors exhibit must exceed to be printed
   */
   public void printExhibitsExceedingNumVisitors(int givenNum)
   {
      boolean atLeastOne = false;
      
      allExhibits.trimToSize();
      for (int i = 0; i < allExhibits.size(); i ++)
      {
         if ((allExhibits.get(i)).currentNumVisitorsExceeds(givenNum))
         {
            System.out.println((allExhibits.get(i)).identifierToString());
            atLeastOne = true;
         }
      }
      
      if (!atLeastOne)
         System.out.println("No exhibits meet the given criteria");
   }
   
   /*
   *  Prints all exhibits within a range of number of visitors
   *  int lowerbound - number of visitors exhibit must exceed to be printed
   *  int upperbound - number of visitors exhibit must be under to be printed
   */
   public void printExhibitsWithinNumVisitors(int lowerbound, int upperbound)
   {
      boolean atLeastOne = false;
      
      allExhibits.trimToSize();
      for (int i = 0; i < allExhibits.size(); i ++)
      {
         if ((allExhibits.get(i)).currentNumVisitorsInRange(lowerbound, upperbound))
         {
            System.out.println((allExhibits.get(i)).identifierToString());
            atLeastOne = true;
         }
      }
      
      if (!atLeastOne)
         System.out.println("No exhibits meet the given criteria");
   }
   
   /*
   *  Prints all exhibits under a certain area of required floor space
   *  double givenFloorSpace - floor space an exhibit must be under to be printed
   */
   public void printExhibitsLessThanFloorSpace(double givenFloorSpace)
   {
      boolean atLeastOne = false;
      
      allExhibits.trimToSize();
      for (int i = 1; i < allExhibits.size(); i ++)
      {
         if ((allExhibits.get(i)).floorSpaceUnder(givenFloorSpace))
         {
            System.out.println((allExhibits.get(i)).identifierToString());
            atLeastOne = true;
         }
      }
      
      if (!atLeastOne)
         System.out.println("No exhibits meet the given criteria");
   }
   
   /*
   *  Prints all exhibits in a range of floor space
   *  double lowerbound - floor space an exhibit must exceed to be printed
   *  double upperbound - floor space an exhibit must be under to be printed
   */
   public void printExhibitsWithinFloorSpace(double lowerbound, double upperbound)
   {
      boolean atLeastOne = false;
      
      allExhibits.trimToSize();
      for (int i = 1; i < allExhibits.size(); i ++)
      {
         if ((allExhibits.get(i)).floorSpaceInRange(lowerbound, upperbound))
         {
            System.out.println((allExhibits.get(i)).identifierToString());
            atLeastOne = true;
         }
      }
      
      if (!atLeastOne)
         System.out.println("No exhibits meet the given criteria");
   }
   
   /*
   *  Finds the exhibit with the greater value between two exhibits, given their IDs
   *  int exhibitId1 - ID of one exhibit
   *  int exhibitId2 - ID of another exhibit
   *  Returns the Exhibit object with the greater value
   */
   public Exhibit greaterValueBetweenExhibits(int exhibitId1, int exhibitId2)
   {
      int index1 = findExhibitIndexById(exhibitId1);
      int index2 = findExhibitIndexById(exhibitId2);
      Exhibit exhibit1;
      Exhibit exhibit2;
      
      if (index1 != -1 && index2 != -1)
      {
         exhibit1 = allExhibits.get(index1);
         exhibit2 = allExhibits.get(index2);
         
         if (exhibit1.compareValue(exhibit2) > 0)
            return exhibit1;
         else if (exhibit1.compareValue(exhibit2) < 0)
            return exhibit2;
         else
         {
            System.out.println("Both exhibits have the same value");
            return null;
         }
      }
      else
      {
         System.out.println("Exhibit ID(s) are not valid");
         return null;
      }
   }
   
   /*
   *  Finds the exhibit with the greater value between two exhibits, given their names
   *  String exhibitName1 - name of one exhibit
   *  String exhibitName2 - name of another exhibit
   *  Returns the Exhibit object with the greater value
   */
   public Exhibit greaterValueBetweenExhibits(String exhibitName1, String exhibitName2)
   {
      int index1 = findExhibitIndexByName(exhibitName1);
      int index2 = findExhibitIndexByName(exhibitName2);
      Exhibit exhibit1;
      Exhibit exhibit2;
      
      if (index1 != -1 && index2 != -1)
      {
         exhibit1 = allExhibits.get(index1);
         exhibit2 = allExhibits.get(index2);
         
         if (exhibit1.compareValue(exhibit2) > 0)
            return exhibit1;
         else if (exhibit1.compareValue(exhibit2) < 0)
            return exhibit2;
         else
         {
            System.out.println("Both exhibits have the same value");
            return null;
         }
      }
      else
      {
         System.out.println("Exhibit ID(s) are not valid");
         return null;
      }
   }
   
   /*
   *  Finds the exhibit with the greater number of current visitors between two exhibits, given their IDs
   *  int exhibitId1 - ID of one exhibit
   *  int exhibitId2 - ID of another exhibit
   *  Returns the Exhibit object with the greater number of current visitors
   */
   public Exhibit greaterNumVisitorsBetweenExhibits(int exhibitId1, int exhibitId2)
   {
      int index1 = findExhibitIndexById(exhibitId1);
      int index2 = findExhibitIndexById(exhibitId2);
      Exhibit exhibit1;
      Exhibit exhibit2;
      
      if (index1 != -1 && index2 != -1)
      {
         exhibit1 = allExhibits.get(index1);
         exhibit2 = allExhibits.get(index2);
         
         if (exhibit1.compareNumCurrentVisitors(exhibit2) > 0)
            return exhibit1;
         else if (exhibit1.compareNumCurrentVisitors(exhibit2) < 0)
            return exhibit2;
         else
         {
            System.out.println("Both exhibits have the same number of current visitors");
            return null;
         }
      }
      else
      {
         System.out.println("Exhibit ID(s) are not valid");
         return null;
      }
   }
   
   /*
   *  Finds the exhibit with the greater number of current visitors between two exhibits, given their names
   *  String exhibitName1 - name of one exhibit
   *  String exhibitName2 - name of another exhibit
   *  Returns the Exhibit object with the greater number of current visitors
   */
   public Exhibit greaterNumVisitorsBetweenExhibits(String exhibitName1, String exhibitName2)
   {
      int index1 = findExhibitIndexByName(exhibitName1);
      int index2 = findExhibitIndexByName(exhibitName2);
      Exhibit exhibit1;
      Exhibit exhibit2;
      
      if (index1 != -1 && index2 != -1)
      {
         exhibit1 = allExhibits.get(index1);
         exhibit2 = allExhibits.get(index2);
         
         if (exhibit1.compareNumCurrentVisitors(exhibit2) > 0)
            return exhibit1;
         else if (exhibit1.compareNumCurrentVisitors(exhibit2) < 0)
            return exhibit2;
         else
         {
            System.out.println("Both exhibits have the same number of current visitors");
            return null;
         }
      }
      else
      {
         System.out.println("Exhibit ID(s) are not valid");
         return null;
      }
   }
   
   /*
   *  Finds the exhibit with the greater amount of floor space between two exhibits, given their IDs
   *  int exhibitId1 - ID of one exhibit
   *  int exhibitId2 - ID of another exhibit
   *  Returns the Exhibit object with the greater amount of floor space
   */
   public Exhibit greaterFloorSpaceBetweenExhibits(int exhibitId1, int exhibitId2)
   {
      int index1 = findExhibitIndexById(exhibitId1);
      int index2 = findExhibitIndexById(exhibitId2);
      Exhibit exhibit1;
      Exhibit exhibit2;
      
      if (index1 != -1 && index2 != -1)
      {
         exhibit1 = allExhibits.get(index1);
         exhibit2 = allExhibits.get(index2);
         
         if (exhibit1.compareFloorSpace(exhibit2) > 0)
            return exhibit1;
         else if (exhibit1.compareFloorSpace(exhibit2) < 0)
            return exhibit2;
         else
         {
            System.out.println("Both exhibits have the same required floor space");
            return null;
         }
      }
      else
      {
         System.out.println("Exhibit ID(s) are not valid");
         return null;
      }
   }
   
   /*
   *  Finds the exhibit with the greater amount of floor space between two exhibits, given their names
   *  String exhibitName1 - name of one exhibit
   *  String exhibitName2 - name of another exhibit
   *  Returns the Exhibit object with the greater amount of floor space
   */
   public Exhibit greaterFloorSpaceBetweenExhibits(String exhibitName1, String exhibitName2)
   {
      int index1 = findExhibitIndexByName(exhibitName1);
      int index2 = findExhibitIndexByName(exhibitName2);
      Exhibit exhibit1;
      Exhibit exhibit2;
      
      if (index1 != -1 && index2 != -1)
      {
         exhibit1 = allExhibits.get(index1);
         exhibit2 = allExhibits.get(index2);
         
         if (exhibit1.compareFloorSpace(exhibit2) > 0)
            return exhibit1;
         else if (exhibit1.compareFloorSpace(exhibit2) < 0)
            return exhibit2;
         else
         {
            System.out.println("Both exhibits have the same required floor space");
            return null;
         }
      }
      else
      {
         System.out.println("Exhibit ID(s) are not valid");
         return null;
      }
   }
   
   /*
   *  Finds the exhibit with the greater number of artifacts between two exhibits, given their IDs
   *  int exhibitId1 - ID of one exhibit
   *  int exhibitId2 - ID of another exhibit
   *  Returns the Exhibit object with the greater number of artifacts
   */
   public Exhibit greaterNumArtifactsBetweenExhibits(int exhibitId1, int exhibitId2)
   {
      int index1 = findExhibitIndexById(exhibitId1);
      int index2 = findExhibitIndexById(exhibitId2);
      Exhibit exhibit1;
      Exhibit exhibit2;
      
      if (index1 != -1 && index2 != -1)
      {
         exhibit1 = allExhibits.get(index1);
         exhibit2 = allExhibits.get(index2);
         
         if (exhibit1.compareNumArtifacts(exhibit2) > 0)
            return exhibit1;
         else if (exhibit1.compareNumArtifacts(exhibit2) < 0)
            return exhibit2;
         else
         {
            System.out.println("Both exhibits have the same number of artifacts");
            return null;
         }
      }
      else
      {
         System.out.println("Exhibit ID(s) are not valid");
         return null;
      }
   }
   
   /*
   *  Finds the exhibit with the greater number of artifacts between two exhibits, given their names
   *  String exhibitName1 - name of one exhibit
   *  String exhibitName2 - name of another exhibit
   *  Returns the Exhibit object with the greater number of artifacts
   */
   public Exhibit greaterNumArtifactsBetweenExhibits(String exhibitName1, String exhibitName2)
   {
      int index1 = findExhibitIndexByName(exhibitName1);
      int index2 = findExhibitIndexByName(exhibitName2);
      Exhibit exhibit1;
      Exhibit exhibit2;
      
      if (index1 != -1 && index2 != -1)
      {
         exhibit1 = allExhibits.get(index1);
         exhibit2 = allExhibits.get(index2);
         
         if (exhibit1.compareNumArtifacts(exhibit2) > 0)
            return exhibit1;
         else if (exhibit1.compareNumArtifacts(exhibit2) < 0)
            return exhibit2;
         else
         {
            System.out.println("Both exhibits have the same number of artifacts");
            return null;
         }
      }
      else
      {
         System.out.println("Exhibit ID(s) are not valid");
         return null;
      }
   }
   
   /*
   *  Finds a visitor's index in the museum's visitor array using sequential search, given their ID
   *  int givenId - a visitor's ID
   *  Returns the visitor's index, -1 if not found
   */
   private int findVisitorIndexById(int givenId)
   {
      int visitorIndex = -1;
      int currentId;
      
      allExhibits.trimToSize();
      
      for(int i = 0; i < allVisitors.size() && visitorIndex == -1; i ++)
      {
         currentId = (allVisitors.get(i)).getId();
         
         if (currentId == givenId)
            visitorIndex = i;
      }
      
      return visitorIndex;
   }
   
   /*
   *  Finds a visitor's index in the museum's visitor array using sequential search, given their name
   *  String givenName - a visitor's full name
   *  Returns the visitor's index, -1 if not found
   */
   private int findVisitorIndexByName(String givenName)
   {
      int visitorIndex = -1;
      String currentFirstName;
      String currentLastName;
      
      String[] name;
      name = givenName.split(" ");
      allExhibits.trimToSize();
      
      for (int i = 0; i < allVisitors.size() && visitorIndex == -1; i ++)
      {
         currentFirstName = (allVisitors.get(i)).getFirstName();
         currentLastName = (allVisitors.get(i)).getLastName();
         
         if (currentFirstName.equals(name[0]) && currentLastName.equals(name[1]))
            visitorIndex = i;
      }
      
      return visitorIndex;
   }
   
   /*
   *  Finds an exhibit's index in the museum's exhibit array using sequential search, given their ID
   *  int givenId - an exhibit's ID
   *  Returns the exhibit's index, -1 if not found
   */
   private int findExhibitIndexById(int givenId)
   {
      int exhibitIndex = -1;
      int currentId;
      allExhibits.trimToSize();
      
      for (int i = 0; i < allExhibits.size() && exhibitIndex == -1; i ++)
      {
         currentId = (allExhibits.get(i)).getId();
         
         if (currentId == givenId)
            exhibitIndex = i;
      }
      
      return exhibitIndex;
   }
   
   /*
   *  Finds an exhibit's index in the museum's exhibit array using sequential search, given their name
   *  String givenName - an exhibit's name
   *  Returns the exhibit's index, -1 if not found
   */
   private int findExhibitIndexByName(String givenName)
   {
      int exhibitIndex = -1;
      String currentName;
      allExhibits.trimToSize();
      
      for (int i = 0; i < allExhibits.size() && exhibitIndex == -1; i ++)
      {
         currentName = (allExhibits.get(i)).getName();
         
         if (currentName.equals(givenName))
            exhibitIndex = i;
      }
      
      return exhibitIndex;
   }
   
   /*
   *  Finds an artifact's index in the museum's artifact array using sequential search, given their ID
   *  int givenId - an artifact's ID
   *  Returns the artifact's index, -1 if not found
   */
   private int findArtifactIndexById(int givenId)
   {
      int artifactIndex = -1;
      int currentId;
      allExhibits.trimToSize();
      
      for (int i = 0; i < allArtifacts.size() && artifactIndex == -1; i ++)
      {
         currentId = (allArtifacts.get(i)).getId();
         
         if (currentId == givenId)
            artifactIndex = i;
      }
      
      return artifactIndex;
   }
   
   /*
   *  Finds an artifact's index in the museum's artifact array using sequential search, given their name
   *  String givenName - an artifact's name
   *  Returns the artifact's index, -1 if not found
   */
   private int findArtifactIndexByName(String givenName)
   {
      int artifactIndex = -1;
      String currentName;
      allExhibits.trimToSize();
      
      for (int i = 0; i < allArtifacts.size() && artifactIndex == -1; i ++)
      {
         currentName = (allArtifacts.get(i)).getName();
         
         if (currentName.equals(givenName))
            artifactIndex = i;
      }
      
      return artifactIndex;
   }
   
   /*
   *  Calculates the total amount of display space taken up by artifacts
   *  Returns the sum of the floor spaces of on-display artifacts
   */
   private double displaySpaceUsed()
   {
      double spaceUsed = 0;
      
      for (int i = 0; i < allArtifacts.size(); i ++)
      {
         if (allArtifacts.get(i).getOnDisplay())
            spaceUsed += allArtifacts.get(i).getFloorSpace();
      }
      
      return spaceUsed;
   }
   
   /*
   *  Calculates the total amount of storage space taken up by artifacts
   *  Returns the sum of the floor spaces of artifacts that are in-storage
   */
   private double storageSpaceUsed()
   {
      double spaceUsed = 0;
      
      for (int i = 0; i < allArtifacts.size(); i ++)
      {
         if (allArtifacts.get(i).getOnDisplay() == false)
            spaceUsed += allArtifacts.get(i).getFloorSpace();
      }
      
      return spaceUsed;
   }
   
public void printAllArtifacts() {
       try{
           for (Artifact a : getAllArtifacts()) {
               System.out.println(a.identifierToString());
           }
       }catch(Exception e){
           System.out.println("Error printing all artifacts.");
       }  
   }

   public void printSpecificArtifact(String name) {
       try{
           System.out.println(searchArtifactByName(name).identifierToString());
       }catch(Exception e){
           System.out.println("Error printing artifact.");
       }
   }

   public void printSpecificArtifact(int id) {
       try{
           System.out.println(searchArtifactByID(id).identifierToString());
       }catch(Exception e){
           System.out.println("Error printing artifact.");
       }
   }

   public void printAllVisitorsAtArtifact(String name) {
       ArrayList<Visitor> visitorList = searchArtifactByName(name).getCurrentVisitors();
       for (Visitor v : visitorList) {
           System.out.println(v.identifierToString());
       }
   }

   public void printAllVisitorsAtArtifact(int id) {
       ArrayList<Visitor> visitorList = searchArtifactByID(id).getCurrentVisitors();
       for (Visitor v : visitorList) {
           System.out.println(v.identifierToString());
       }
   }

   public int numVisitorsAtArtifact(String name) {
       try {
           return searchArtifactByName(name).getCurrentVisitors().size();
       } catch (Exception e) {
           System.out.println("Artifact \"" + name + "\" doesn't exist.");
           return 0;
       }
   }

   public int numVisitorsAtArtifact(int id) {
       try {
           return searchArtifactByID(id).getCurrentVisitors().size();
       } catch (Exception e) {
           System.out.println("Artifact \"" + id + "\" doesn't exist.");
           return 0;
       }
   }

   public void printArtifactsCurrentlyOnFloor() {
       for (Artifact a : getAllArtifactsArray()) {
           if (a.getOnDisplay()) {
               System.out.println(a.identifierToString());
           }
       }
   }

   public void printArtifactsInStorage() {
       for (Artifact a : getAllArtifactsArray()) {
           if (!a.getOnDisplay()) {
               System.out.println(a.identifierToString());
           }
       }
   }

   public void printArtifactsAscendingValue() {
       Artifact[] sortedList = getAllArtifactsArray().clone();
       Artifact temp;

       for (int i = 0; i < sortedList.length - 1; i++) {
           if (sortedList[i].getValue() > sortedList[i + 1].getValue()) {
               temp = sortedList[i + 1];
               for (int j = i; j >= 0; j--) {
                   if (sortedList[j].getValue() < temp.getValue()) {
                       sortedList[j + 1] = temp;
                   } else {
                       sortedList[j + 1] = sortedList[j];
                       sortedList[j] = temp;
                   }
               }
           }
       }

       for (Artifact a : sortedList) {
           System.out.println(a.identifierToString());
       }
   }

   public void printArtifactsDescendingValue() {
       Artifact[] sortedList = getAllArtifactsArray().clone();
       Artifact temp;

       for (int i = 0; i < sortedList.length - 1; i++) {
           if (sortedList[i].getValue() < sortedList[i + 1].getValue()) {
               temp = sortedList[i + 1];
               for (int j = i; j >= 0; j--) {
                   if (sortedList[j].getValue() > temp.getValue()) {
                       sortedList[j + 1] = temp;
                   } else {
                       sortedList[j + 1] = sortedList[j];
                       sortedList[j] = temp;
                   }
               }
           }
       }

       for (Artifact a : sortedList) {
           System.out.println(a.identifierToString());
       }
   }

   public void printArtifactsAscendingFloorSpace() {
       Artifact[] sortedList = getAllArtifactsArray().clone();
       Artifact temp;

       for (int i = 0; i < sortedList.length - 1; i++) {
           if (sortedList[i].getFloorSpace() > sortedList[i + 1].getFloorSpace()) {
               temp = sortedList[i + 1];
               for (int j = i; j >= 0; j--) {
                   if (sortedList[j].getFloorSpace() < temp.getFloorSpace()) {
                       sortedList[j + 1] = temp;
                   } else {
                       sortedList[j + 1] = sortedList[j];
                       sortedList[j] = temp;
                   }
               }
           }
       }

       for (Artifact a : sortedList) {
           System.out.println(a.identifierToString());
       }
   }

   public void printArtifactsDescendingFloorSpace() {
       Artifact[] sortedList = getAllArtifactsArray().clone();
       Artifact temp;

       for (int i = 0; i < sortedList.length - 1; i++) {
           if (sortedList[i].getFloorSpace() < sortedList[i + 1].getFloorSpace()) {
               temp = sortedList[i + 1];
               for (int j = i; j >= 0; j--) {
                   if (sortedList[j].getFloorSpace() > temp.getFloorSpace()) {
                       sortedList[j + 1] = temp;
                   } else {
                       sortedList[j + 1] = sortedList[j];
                       sortedList[j] = temp;
                   }
               }
           }
       }

       for (Artifact a : sortedList) {
           System.out.println(a.identifierToString());
       }
   }

   public void printArtifactsOldToRecent() {
       Artifact[] sortedList = getAllArtifactsArray().clone();
       Artifact temp;

       for (int i = 0; i < sortedList.length - 1; i++) {
           if (sortedList[i].madeBefore(sortedList[i + 1].getDateMade())) {
               temp = sortedList[i + 1];
               for (int j = i; j >= 0; j--) {
                   if (temp.madeBefore(sortedList[j].getDateMade())) {
                       sortedList[j + 1] = temp;
                   } else {
                       sortedList[j + 1] = sortedList[j];
                       sortedList[j] = temp;
                   }
               }
           }
       }

       for (Artifact a : sortedList) {
           System.out.println(a.identifierToString());
       }
   }

   public void printArtifactsRecentToOld() {
       Artifact[] sortedList = getAllArtifactsArray().clone();
       Artifact temp;

       for (int i = 0; i < sortedList.length - 1; i++) {
           if (sortedList[i + 1].madeBefore(sortedList[i].getDateMade())) {
               temp = sortedList[i + 1];
               for (int j = i; j >= 0; j--) {
                   if (sortedList[j].madeBefore(temp.getDateMade())) {
                       sortedList[j + 1] = temp;
                   } else {
                       sortedList[j + 1] = sortedList[j];
                       sortedList[j] = temp;
                   }
               }
           }
       }

       for (Artifact a : sortedList) {
           System.out.println(a.identifierToString());
       }
   }

   public void printArtifactsAscendingVisitors() {
       Artifact[] sortedList = getAllArtifactsArray().clone();
       Artifact temp;

       for (int i = 0; i < sortedList.length - 1; i++) {
           if (sortedList[i].getNumCurrentVisitors() > sortedList[i + 1].getNumCurrentVisitors()) {
               temp = sortedList[i + 1];
               for (int j = i; j >= 0; j--) {
                   if (sortedList[j].getNumCurrentVisitors() < temp.getNumCurrentVisitors()) {
                       sortedList[j + 1] = temp;
                   } else {
                       sortedList[j + 1] = sortedList[j];
                       sortedList[j] = temp;
                   }
               }
           }
       }

       for (Artifact a : sortedList) {
           System.out.println(a.identifierToString());
       }
   }

   public void printArtifactsDescendingVisitors() {
       Artifact[] sortedList = getAllArtifactsArray().clone();
       Artifact temp;

       for (int i = 0; i < sortedList.length - 1; i++) {
           if (sortedList[i].getNumCurrentVisitors() < sortedList[i + 1].getNumCurrentVisitors()) {
               temp = sortedList[i + 1];
               for (int j = i; j >= 0; j--) {
                   if (sortedList[j].getNumCurrentVisitors() > temp.getNumCurrentVisitors()) {
                       sortedList[j + 1] = temp;
                   } else {
                       sortedList[j + 1] = sortedList[j];
                       sortedList[j] = temp;
                   }
               }
           }
       }

       for (Artifact a : sortedList) {
           System.out.println(a.identifierToString());
       }
   }

   public void printArtifactsAlpha() {
       Artifact[] sortedList = getAllArtifactsArray().clone();
       Artifact temp;

       for (int i = 0; i < sortedList.length - 1; i++) {
           if (sortedList[i].getName().compareTo(sortedList[i + 1].getName()) < 0) {
               temp = sortedList[i + 1];
               for (int j = i; j >= 0; j--) {
                   if (sortedList[j].getName().compareTo(temp.getName()) > 0) {
                       sortedList[j + 1] = temp;
                   } else {
                       sortedList[j + 1] = sortedList[j];
                       sortedList[j] = temp;
                   }
               }
           }
       }

       for (Artifact a : sortedList) {
           System.out.println(a.identifierToString());
       }
   }

   public void printArtifactsReverseAlpha() {
       Artifact[] sortedList = getAllArtifactsArray().clone();
       Artifact temp;

       for (int i = 0; i < sortedList.length - 1; i++) {
           if (sortedList[i].getName().compareTo(sortedList[i + 1].getName()) > 0) {
               temp = sortedList[i + 1];
               for (int j = i; j >= 0; j--) {
                   if (sortedList[j].getName().compareTo(temp.getName()) < 0) {
                       sortedList[j + 1] = temp;
                   } else {
                       sortedList[j + 1] = sortedList[j];
                       sortedList[j] = temp;
                   }
               }
           }
       }

       for (Artifact a : sortedList) {
           System.out.println(a.identifierToString());
       }
   }

   public Artifact searchArtifactByName(String name) {
       for (Artifact a : getAllArtifactsArray()) {
           if (a.getName().equals(name)) {
               return a;
           }
       }
       System.out.println("Artifact " + name + " not found.");
       return null;
   }

   public Artifact searchArtifactByID(int id) {
       for (Artifact a : getAllArtifactsArray()) {
           if (a.getId() == id) {
               return a;
           }
       }
       System.out.println("Artifact " + id + " not found.");
       return null;
   }

   public void printArtifactsExceedingValue(double min) {
       for (Artifact a : getAllArtifactsArray()) {
           if (a.getValue() > min) {
               System.out.println(a.identifierToString());
           }
       }
   }

   public void printArtifactsWithinValue(double min, double max) {
       for (Artifact a : getAllArtifactsArray()) {
           if (a.getValue() >= min && a.getValue() <= max) {
               System.out.println(a.identifierToString());
           }
       }
   }

   public void printArtifactsExceedingNumVisitors(int min) {
       for (Artifact a : getAllArtifactsArray()) {
           if (a.getCurrentVisitors().size() > min) {
               System.out.println(a.identifierToString());
           }
       }
   }

   public void printArtifactsWithinNumVisitors(int min, int max) {
       for (Artifact a : getAllArtifactsArray()) {
           if (a.getCurrentVisitors().size() >= min && a.getCurrentVisitors().size() <= max) {
               System.out.println(a.identifierToString());
           }
       }
   }

   public void printArtifactsLessThanFloorSpace(double max) {
       for (Artifact a : getAllArtifactsArray()) {
           if (a.getFloorSpace() < max) {
               System.out.println(a.identifierToString());
           }
       }
   }

   public void printArtifactsMadeBefore(String date) {
       for (Artifact a : getAllArtifactsArray()) {
           if (a.madeBefore(new Date(date))) {
               System.out.println(a.identifierToString());
           }
       }
   }

   public void printArtifactsMadeWithin(String date1, String date2) {
       for (Artifact a : getAllArtifactsArray()) {
           if (a.madeWithin(new Date(date1), new Date(date2))) {
               System.out.println(a.identifierToString());
           }
       }
   }

   public void printArtifactsPurchasedBefore(String date) {
       for (Artifact a : getAllArtifactsArray()) {
           if (a.purchasedBefore(new Date(date))) {
               System.out.println(a.identifierToString());
           }
       }
   }

   public void printArtifactsPurchasedWithin(String date1, String date2) {
       for (Artifact a : getAllArtifactsArray()) {
           if (a.purchasedWithin(new Date(date1), new Date(date2))) {
               System.out.println(a.identifierToString());
           }
       }
   }

   public Artifact greaterValueBetweenArtifacts(int id1, int id2) {
       Artifact a = searchArtifactByID(id1);
       Artifact b = searchArtifactByID(id2);

       if (a.getValue() < b.getValue()) {
           return b;
       }
       return a;
   }

   public Artifact greaterValueBetweenArtifacts(String name1, String name2) {
       Artifact a = searchArtifactByName(name1);
       Artifact b = searchArtifactByName(name2);

       if (a.getValue() < b.getValue()) {
           return b;
       }
       return a;
   }

   public Artifact olderMadeBetweenArtifacts(int id1, int id2) {
       Artifact a = searchArtifactByID(id1);
       Artifact b = searchArtifactByID(id2);

       if (a.madeBefore(b.getDateMade())) {
           return a;
       }
       return b;
   }

   public Artifact olderMadeBetweenArtifacts(String name1, String name2) {
       Artifact a = searchArtifactByName(name1);
       Artifact b = searchArtifactByName(name2);

       if (a.madeBefore(b.getDateMade())) {
           return a;
       }
       return b;
   }

   public Artifact olderPurchasedBetweenArtifacts(int id1, int id2) {
       Artifact a = searchArtifactByID(id1);
       Artifact b = searchArtifactByID(id2);

       if (a.purchasedBefore(b.getDatePurchased())) {
           return a;
       }
       return b;
   }

   public Artifact olderPurchasedBetweenArtifacts(String name1, String name2) {
       Artifact a = searchArtifactByName(name1);
       Artifact b = searchArtifactByName(name2);

       if (a.purchasedBefore(b.getDatePurchased())) {
           return a;
       }
       return b;
   }

   public Artifact greaterNumVisitorsBetweenArtifacts(int id1, int id2) {
       Artifact a = searchArtifactByID(id1);
       Artifact b = searchArtifactByID(id2);

       if (a.getCurrentVisitors().size() > b.getCurrentVisitors().size()) {
           return a;
       }
       return b;
   }

   public Artifact greaterNumVisitorsBetweenArtifacts(String name1, String name2) {
       Artifact a = searchArtifactByName(name1);
       Artifact b = searchArtifactByName(name2);

       if (a.getCurrentVisitors().size() > b.getCurrentVisitors().size()) {
           return a;
       }
       return b;
   }

   public Artifact greaterFloorSpaceBetweenArtifacts(int id1, int id2) {
       Artifact a = searchArtifactByID(id1);
       Artifact b = searchArtifactByID(id2);

       if (a.getFloorSpace() > b.getFloorSpace()) {
           return a;
       }
       return b;
   }

   public Artifact greaterFloorSpaceBetweenArtifacts(String name1, String name2) {
       Artifact a = searchArtifactByName(name1);
       Artifact b = searchArtifactByName(name2);

       if (a.getFloorSpace() > b.getFloorSpace()) {
           return a;
       }
       return b;
   }

   @Override
   public String toString() {
       String s = "MUSEUM: " + "\nOpening Date: " + openingDate + "\nMax Display Space: " + maxDisplaySpace + "\nMax Storage Space" + maxStorageSpace;

       for (Artifact a : getAllArtifactsArray()) {
           s = s + a;
       }

       for (Exhibit e : allExhibits) {
           s = s + e;
       }

       for (Visitor v : allVisitors) {
           s = s + v;
       }

       return s;
   }

   public boolean equals(Museum museum2) {
       if (museum2 != null) {
           if (this.getOpeningDate() == museum2.getOpeningDate()
                   && this.getCurrentDate() == museum2.getCurrentDate()
                   && this.getMaxDisplaySpace() == museum2.getMaxDisplaySpace()
                   && this.getMaxStorageSpace() == museum2.getMaxStorageSpace()
                   && this.getNumCurrentVisitors() == museum2.getNumCurrentVisitors()
                   && this.getDaysOpenCount() == museum2.getDaysOpenCount()) {
               return true;
           }
       }

       return false;
   }

   public Exhibit[] findOptimalExhibitArrangement(double maxSpace) {
        Exhibit[] exhibits = findOptimalExhibitArrangement(getAllExhibitsArray(), 0, allExhibits.size() - 1, maxSpace).clone();
        ArrayList<Exhibit> finalExhibitList = new ArrayList<>();
        double usedSpace = 0;

        try {
            for (Exhibit e : exhibits) {
                if (usedSpace + e.getFloorSpace() <= maxSpace) {
                    usedSpace += e.getFloorSpace();
                    finalExhibitList.add(e);
                }
            }
        } catch (Exception e) {
            return null;
        }

        return finalExhibitList.toArray(new Exhibit[finalExhibitList.size()]);
    }

    private Exhibit[] findOptimalExhibitArrangement(Exhibit[] exhibits, int startIndex, int endIndex, double maxSpace) {
        Exhibit[] bestArrangement = exhibits.clone();

        if (startIndex == endIndex) {
            return exhibits;
        } else {
            for (int i = startIndex; i <= endIndex; i++) {
                exhibits = swap(exhibits, startIndex, i);

                bestArrangement = findOptimalExhibitArrangement(exhibits, startIndex + 1, endIndex, maxSpace);
                if (getFloorSpaceUsed(exhibits, maxSpace) > getFloorSpaceUsed(bestArrangement, maxSpace)) {
                    bestArrangement = exhibits;
                }

                exhibits = swap(exhibits, startIndex, i);
            }

            return bestArrangement;

        }
    }

    public static double getFloorSpaceUsed(Exhibit[] exhibits, double maxSpace) {
        double usedSpace = 0;

        try {
            for (Exhibit e : exhibits) {
                if (usedSpace + e.getFloorSpace() <= maxSpace) {
                    usedSpace += e.getFloorSpace();
                }
            }
        } catch (Exception e) {
            return 0;
        }

        return usedSpace;
    }

   //Swaps exhibits at indexes i and j
   private Exhibit[] swap(Exhibit[] exhibits, int i, int j) {
       Exhibit temp;
       exhibits = exhibits.clone();
       temp = exhibits[i];
       exhibits[i] = exhibits[j];
       exhibits[j] = temp;
       return exhibits;
   }
}
