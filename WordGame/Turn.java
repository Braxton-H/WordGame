package WordGame;

import java.util.Scanner;

public class Turn {
	
	private static final int winAmount = 500;
	private static final int lossAmount = 50;
	
	//Method for the players turns
	public boolean takeTurn(Players players, Hosts host) {
		Scanner keyboard = new Scanner(System.in);
		
		// Simulate host prompting player
        System.out.println(host.getFirstName() + " " + host.getLastName() + " is hosting. " +
        		players.getFirstName() + " " + players.getLastName() + ", guess a number between 0-100:");
        
        // Get player's guess
        int guess = keyboard.nextInt();
        
        // Check if the guess is correct
        boolean isCorrect = Numbers.compareNumber(guess);
        
        // Update player's money based on the guess result
        if (isCorrect) {
            players.setCurrentMoney(players.getCurrentMoney() + winAmount);
            System.out.println("Congratulations, " + players.getFirstName() + " " + players.getLastName() + 
            		"! You won 500$ with a guess of " + guess + ".");
            System.out.println(players); // Displays player's name and updated money
            return true;
        } else {
            players.setCurrentMoney(players.getCurrentMoney() - lossAmount);
            System.out.println(players.getFirstName() + " " + players.getLastName() + 
            		". Your guess of " + guess + " was incorrect. You lost 50$");
            System.out.println(players); // Displays player's name and updated money
            return false;
        }
    }

}
