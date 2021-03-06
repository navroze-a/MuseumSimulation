import java.util.*;

abstract class Visitor {

    //Fields
    private int id;
    private String firstName;
    private String lastName;
    private int age;
    private Exhibit currentExhibit;
    private Artifact currentArtifact;
    private ArrayList <Exhibit> visitedExhibits;
    private ArrayList <Artifact> visitedArtifacts;

    //Default constructor if no parameters are entered
    public Visitor (){
        id = 00000;
        firstName = "firstName";
        lastName = "lastName";
        age = 0;
    }

    //Main constructor
    public Visitor (int id, String firstName, String lastName, int age, Exhibit currentExhibit, Artifact currentArtifact, ArrayList <Exhibit> visitedExhibits, ArrayList <Artifact> visitedArtifacts){
        //Check if ID is of correct length
        if (id >= 100000 && (id+"").length() == 6)
            this.id = id;
        else
            throw new InputMismatchException();

        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.currentExhibit = currentExhibit;
        this.currentArtifact = currentArtifact;
        this.visitedExhibits = visitedExhibits;
        this.visitedArtifacts = visitedArtifacts;
    }

    //Alternate constructor without visited arrayLists
    public Visitor (int id, String firstName, String lastName, int age, Exhibit currentExhibit, Artifact currentArtifact){
        if (id >= 100000 && (id+"").length() == 6)
            this.id = id;
        else
            throw new InputMismatchException();

        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.currentExhibit = currentExhibit;
        this.currentArtifact = currentArtifact;
        visitedExhibits = new ArrayList();
        visitedArtifacts = new ArrayList();
    }

    //Accessors
    public int getId (){
        return id;
    }

    public String getFirstName (){
        return firstName;
    }

    public String getLastName (){
        return lastName;
    }

    public int getAge (){
        return age;
    }

    public Exhibit getCurrentExhibit (){
        return currentExhibit;
    }

    public Artifact getCurrentArtifact (){ return currentArtifact; }

    public ArrayList<Exhibit> getVisitedExhibits (){
        visitedExhibits.trimToSize();
        return visitedExhibits;
    }

    public ArrayList<Artifact> getVisitedArtifacts (){
        visitedArtifacts.trimToSize();
        return visitedArtifacts;
    }

    //Mutators
    public void setId (int id){
        this.id = id;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) { this.lastName = lastName; }

    public void setAge(int age) {
        this.age = age;
    }

    public void setCurrentArtifact(Artifact currentArtifact) {
        this.currentArtifact = currentArtifact;
    }

    public void setCurrentExhibit(Exhibit currentExhibit) {
        this.currentExhibit = currentExhibit;
    }

    public void setVisitedArtifacts(ArrayList<Artifact> visitedArtifacts) {
        this.visitedArtifacts = visitedArtifacts;
    }

    public void setVisitedExhibits(ArrayList<Exhibit> visitedExhibits) {
        this.visitedExhibits = visitedExhibits;
    }

    //Compare Visitor Age
    public int compareAge (Visitor other){
        return this.getAge() - other.getAge();
    }

    //Compare the number of exhibits visited by two visitors
    public int compareExhibitVisits (Visitor other){
        int output;
        output = (this.getVisitedExhibits()).size() - (other.getVisitedExhibits()).size();
        return output;
    }

    //Compare the number of artifacts visited by two visitors
    public int compareArtifactVisits (Visitor other){
        int output;
        output = (this.getVisitedArtifacts()).size() - (other.getVisitedArtifacts()).size();
        return output;
    }

    //Compare to see if two visitors are the same
    public boolean visitorEquals (Visitor other){
        if (this.id == other.id){
            return true;
        } else {
            return false;
        }
    }

    //ToString method
    public String toString (){
        String output;
        //Output fields
        output = "ID - " + id + "\n First Name - " + firstName + "\n Last Name - " + lastName + "\n Age - " + age + "\n Current Exhibit - " + currentExhibit.identifierToString() + "\n Current Artifact - " + currentArtifact.identifierToString();
        //Output visited artifact and exhibit lists
        output = output + "\n Visited Artifacts - ";
        for (int i = 0; i < visitedArtifacts.size(); i++){
            output = output + ((visitedArtifacts.get(i)).getName() + ", ");
        }
        output = output + "\n Visited Exhibits - ";
        for (int i = 0; i < visitedExhibits.size(); i++){
            output = output + ((visitedExhibits.get(i)).getName() + ", ");
        }
        return output;
    }

    //Identifier ToString method
    public String identifierToString (){
        String output;
        output = id + " " + firstName + " " + lastName;
        return output;
    }

    //Move visitor to different location
    public boolean moveVisitor (Artifact newArtifact){
        //Create boolean to track uniqueness of artifact/exhibit
        boolean isUnique = true;
        //Store newArtifact's exhibit as newExhibit
        Exhibit newExhibit = newArtifact.getExhibitLocation();

        //Check if current exhibit is already visited or if visitor is already there
        if (newExhibit.equals(currentExhibit)){
            isUnique = false;
        }

        for (int i = 0; i < visitedExhibits.size(); i++){
            if (visitedExhibits.get(i).equals(newExhibit)){
                //If found in array, set boolean to false
                isUnique = false;
            }
        }
        //If boolean is true, meaning this exhibit is unique, add exhibit to visited exhibit array and set new exhibit
        if (isUnique){
            // If the visitor is not in the lobby, store their initial exhibit into the visited exhibits array
            if (!(currentExhibit.getName()).equals("Lobby"))
                visitedExhibits.add(currentExhibit);

            currentExhibit = newExhibit;
        }

        //Reset variable
        isUnique = true;

        //Check if current artifact is already visited or if visitor is already there
        if (newArtifact.equals(currentArtifact)){
            isUnique = false;
        }

        for (int i = 0; i < visitedArtifacts.size(); i++){
            //If found in array, set boolean to false
            if (visitedArtifacts.get(i) == newArtifact){
                isUnique = false;
            }
        }

        //If boolean is true, meaning this artifact is unique, add artifact to visited artifact array and set new artifact
        if (isUnique) {
            // If the visitor is not in the lobby, store their initial artifact in the visited artifacts array
            if (currentArtifact != null)
                visitedArtifacts.add(currentArtifact);

            //Set current artifact the new artifact
            currentArtifact = newArtifact;
        }
        //Return boolean to see if Museum needs to add Visitor needs to artifact/exhibit current visitors
        return isUnique;
    }

    //Moves visitor to lobby
    public boolean moveToLobby(Exhibit lobby)
    {
        // If the visitor is not already in the lobby
        if (!currentExhibit.getName().equals("Lobby"))
        {
            // add visitor's initial exhibit to history and move them to the lobby
            visitedExhibits.add(currentExhibit);
            currentExhibit = lobby;

            // add visitor's initial artifact to history and note that they are not at any artifact
            visitedArtifacts.add(currentArtifact);
            currentArtifact = null;

            return true;
        }
        // If the visitor is already in the lobby, return false
        else
            return false;
    }
}