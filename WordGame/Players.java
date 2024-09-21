package WordGame;

public class Players extends Person {
	
	//set up variable for money
	private int currentMoney;
	
	//constructor to set money to 100
	public Players(String fname) {
		super(fname); //call to the parent class (Person) constructor for the first name only
		currentMoney = 1000;
	}
	
	public Players (String fName, String lName) {
		super(fName, lName); //call to the parent class (person constructor for first and last name
		currentMoney = 1000;
	}
	
	//Getter for current money
	public int getCurrentMoney() {
		return currentMoney;
	}
	
	//Setter for current money
	public void setCurrentMoney(int cMoney) {
		currentMoney = cMoney;
	}
	
	//Override the player first and last name toString to include money
	@Override
	public String toString() {
		return super.toString() + ": $" + currentMoney; 
	}

}
