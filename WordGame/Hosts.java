package WordGame;

public class Hosts extends Person {
	
	public Hosts(String fName) {
		super(fName); // Call to the parent class (Person) COnstructor to get the first name
	}
	
	public Hosts(String fName, String lName) {
		super(fName, lName); // Call to the parent class (Person) Constructor to get the first and last name
	}

	// Method to randomly pick a number between 0-100
	public void randomizeNum() {
		Numbers.generateNumber(); //Calls the generate number method in Numbers
	}
}
