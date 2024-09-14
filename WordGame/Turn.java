package WordGame;

import java.util.Random;
import java.util.Scanner;

public class Turn {
    
    private static final int winThreshold = 5;
    
    // Method for the players' turns
    public boolean takeTurn(Players player, Hosts host) {
        Random random = new Random();
        Scanner keyboard = new Scanner(System.in);
        
        //Have the host prompt the player
        System.out.println(host.getFirstName() + " " + host.getLastName() + " is hosting. " +
                player.getFirstName() + " " + player.getLastName() + ", guess a number between 0-100:");
        
        //Get player's guess
        int guess = keyboard.nextInt();
        
        //Check if the guess is correct
        boolean isCorrect = Numbers.compareNumber(guess);
        
        //Determine whether to award money or a physical prize
        Award award;
        int decision = random.nextInt(winThreshold);
        
        if (decision < winThreshold / 2) {
            award = new Money();
        } else {
            award = new Physical();
        }
        
        //Update player's current money based on the result
        int winnings = award.displayWinnings(player, isCorrect);
        player.setCurrentMoney(player.getCurrentMoney() + winnings);
        
        System.out.println(player); //Displays player's name and updated money
        
        return isCorrect;
    }
}