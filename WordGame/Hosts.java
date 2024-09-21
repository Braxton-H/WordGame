package WordGame;

public class Hosts extends Person {
	
	private Phrases phrases;
	
	public Hosts(String fName) {
		super(fName); //Call to the parent class (Person) COnstructor to get the first name
		phrases = new Phrases();
	}
	
	public Hosts(String fName, String lName) {
		super(fName, lName); //Call to the parent class (Person) Constructor to get the first and last name
		phrases = new Phrases();
	}

	public void setGamePhrase(String phrase) {
		phrases.setGamePhrase(phrase);
		phrases.createPlayingPhrase();
	}
	
	public Phrases getPhrases() {
		return phrases;
	}
}
