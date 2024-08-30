package WordGame;

import java.util.Scanner;

public class GamePlay {
	private Person person;
	
	//Create the SCanner
	public static void main (String[] args) {
		//Set up Scanner
		Scanner keyboard = new Scanner(System.in);
	
		//Ask player for first name
		System.out.print("Enter your first name: ");
		String firstName = keyboard.nextLine();
	
		//Ask player if they want to have a last name
		System.out.print("Would you like to enter a last name? (Yes/No): ");
		String response = keyboard.nextLine();
	
		//If the person said yes, let them type in last name, otherwise, don't
		Person person;
		if (response.equalsIgnoreCase("yes")) {
			System.out.print("Enter last name: ");
			String lastName = keyboard.nextLine();
			person = new Person(firstName, lastName);	
		} else {
			person = new Person(firstName);
		}
		
		Numbers numbers = new Numbers();
        numbers.generateNumber();
		
		boolean winCondition = false;
		
		do {
		System.out.println(person + ", Guess a number between 0-100");
		int input = keyboard.nextInt();
		winCondition = numbers.compareNumber(input);
			
		} while (!winCondition);
	}
}
