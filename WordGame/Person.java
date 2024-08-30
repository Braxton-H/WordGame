package WordGame;

public class Person {
	private String firstName;
	private String lastName;
	
	public Person(String fName) {
		firstName = fName;
		lastName = "";
	}
	
	public Person(String fName, String lName) {
		firstName = fName;
		lastName = lName;
	}
	
	
	//Set Players First Name
	public void setFirstName (String fName) {
		firstName = fName;
	} 
	
	//Set Players Last Name
	public void setLastName (String lName) {
		lastName = lName;
	}
	
	//Return Players First Name
	public String getFirstName () {
		return  firstName;
	}

	//Return Players Last Name
	public String getLastName () {
		return  lastName;
	}
	
	public String toString() {
        if (lastName.isEmpty()) {
            return firstName;  // Return only the first name if no last name
        } else {
            return firstName + " " + lastName;  // Return full name if both names are present
        }
	}
}
