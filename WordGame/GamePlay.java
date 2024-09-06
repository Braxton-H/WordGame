package WordGame;

import java.util.Scanner;

public class GamePlay {
	private Person person;
	
	//Create the SCanner
	public static void main (String[] args) {
		//Set up Scanner
		Scanner keyboard = new Scanner(System.in);
		
		//Set up the turns
		Turn turn = new Turn();
		
		//Keep the player playing until they want to start a new game
		boolean newGame = true;
		
		while(newGame) {
		
			// Create an instance of Hosts first name or first and last name
			System.out.print("Host, Enter your first name: ");
			String hostFirstName = keyboard.nextLine();
			System.out.print("Would you like to enter a last name? (Yes/No): ");
			String hostresponse = keyboard.nextLine();
			Hosts host;
			if (hostresponse.equalsIgnoreCase("yes")) {
				System.out.print("Enter last name: ");
				String hostLastName = keyboard.nextLine();
				host = new Hosts(hostFirstName, hostLastName);    
	    	} else {
	    		host = new Hosts(hostFirstName);
	    	}
	    
			// Create an instance of Players first name or first and last name
			System.out.print("Player, Enter your first name: ");
			String playerFirstName = keyboard.nextLine();
			System.out.print("Would you like to enter a last name? (Yes/No): ");
			String playerResponse = keyboard.nextLine();
			Players player;
			if (playerResponse.equalsIgnoreCase("yes")) {
				System.out.print("Enter last name: ");
				String playerLastName = keyboard.nextLine();
				player = new Players(playerFirstName, playerLastName);    
			} else {
				player = new Players(playerFirstName);
			}
			
			//Keeps the player playing in the current round until said otherwise
			boolean keepPlaying = true;
            
			//This while loop keeps the random number set for the specific round
            while (keepPlaying) {
            	
                // New number for each round
                host.randomizeNum();
                
                //condition that must be met for player to win
                boolean winCondition = false;
                
                // Game loop until the player guesses correctly
                while (!winCondition) {
                    winCondition = turn.takeTurn(player, host);
                }
                
                // Ask the player if they want to play another round
                System.out.print("Do you want to play another round? (Yes/No): ");
                String playAgain = keyboard.nextLine();
                
                if (playAgain.equalsIgnoreCase("no")) {
                    keepPlaying = false;
                    System.out.println("Thanks for playing!");
                }
            }
            
            // Ask if the player wants to start a new game
            System.out.print("Do you want to start a new game? (Yes/No): ");
            String newGameResponse = keyboard.nextLine();
            
            if (newGameResponse.equalsIgnoreCase("no")) {
                newGame = false;
                System.out.println("Goodbye!");
            }
        }
	}
}