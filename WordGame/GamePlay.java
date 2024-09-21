package WordGame;

import java.util.Scanner;

public class GamePlay {
    private static final int numPlayers = 3;

    public static void main(String[] args) {
        Scanner keyboard = new Scanner(System.in);
        
        Turn turn = new Turn();
        Players[] currentPlayers = new Players[numPlayers];
        
        boolean newGame = true;
        
        while (newGame) {
            System.out.print("Host, Enter your first name: ");
            String hostFirstName = keyboard.nextLine();
            System.out.print("Would you like to enter a last name? (Yes/No): ");
            String hostResponse = keyboard.nextLine();
            Hosts host;
            if (hostResponse.equalsIgnoreCase("yes")) {
                System.out.print("Enter last name: ");
                String hostLastName = keyboard.nextLine();
                host = new Hosts(hostFirstName, hostLastName);
            } else {
                host = new Hosts(hostFirstName);
            }

            // Set the game phrase
            System.out.print("Enter a phrase for the players to guess: ");
            String gamePhrase = keyboard.nextLine();
            host.setGamePhrase(gamePhrase);

            // Create the Players
            for (int i = 0; i < numPlayers; i++) {
                System.out.print("Player " + (i + 1) + ", Enter your first name: ");
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
                currentPlayers[i] = player;
            }

            boolean keepPlaying = true;

            while (keepPlaying) {
                boolean roundOver = false;
                int currentPlayerIndex = 0;

                while (!roundOver) {
                    boolean hasWon = turn.takeTurn(currentPlayers[currentPlayerIndex], host);
                    
                    if (hasWon) {
                        //Player has won, break out of the turn loop
                        roundOver = true;
                    } else {
                        //Move to the next player
                        currentPlayerIndex = (currentPlayerIndex + 1) % numPlayers;
                    }
                }

                //Ask players if they want to play again
                System.out.print("Do you want to play another round? (Yes/No): ");
                String playAgain = keyboard.nextLine();
                if (playAgain.equalsIgnoreCase("yes")) {
                    System.out.print("Enter a new phrase for the next round: ");
                    String newPhrase = keyboard.nextLine();
                    host.setGamePhrase(newPhrase);
                    System.out.println("The host has picked a new phrase for the next round!");
                } else {
                    keepPlaying = false;
                    System.out.println("Thanks for playing!");
                }
            }
        }
    }
}
