package WordGame;

import java.util.Scanner;

public class Turn {
    private Money moneyManager = new Money();
    private Physical physicalPrizeManager = new Physical();

    // Method for the players' turns
    public boolean takeTurn(Players player, Hosts host) {
        Scanner keyboard = new Scanner(System.in);
        Phrases phrases = host.getPhrases();

        System.out.println(host.getFirstName() + " is hosting. " + player.getFirstName() + ", guess a letter:");
        String guess = keyboard.nextLine().trim();

        boolean correctGuess = false;

        try {
            correctGuess = phrases.findLetters(guess);
            System.out.println("Current phrase: " + phrases.getPlayingPhrase());

            // Update money based on whether the guess was correct
            int moneyChange = moneyManager.displayWinnings(player, correctGuess);
            player.setCurrentMoney(player.getCurrentMoney() + moneyChange);

            // Check if the player has won the entire phrase
            if (!phrases.getPlayingPhrase().contains("_")) {
                System.out.println(player.getFirstName() + " has guessed the phrase!");
                physicalPrizeManager.displayWinnings(player, true);
                return true; // Signal that the game should end for this round
            }
        } catch (MultipleLettersException e) {
            System.out.println(e.getMessage());
            return false; // Let the player take another turn
        } catch (Exception e) {
            System.out.println("Invalid input! Please enter a single letter.");
            return false; // Let the player take another turn
        }

        return false; // Continue with the game
    }
}