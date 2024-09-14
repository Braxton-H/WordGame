package WordGame;

import java.util.Scanner;

public class GamePlay {
    private Person person;
    private static final int numPlayers = 3;
    
    public static void main(String[] args) {
        //Set up Scanner
        Scanner keyboard = new Scanner(System.in);
        
        //Set up the turns
        Turn turn = new Turn();
        
        //Create an array of players
        Players[] currentPlayers = new Players[numPlayers];
        
        //Keep the player playing until they want to start a new game
        boolean newGame = true;
        
        while (newGame) {
            // Create the Host
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
            
            //Create the Players
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
            
            //Keeps the game running for the current round
            boolean keepPlaying = true;
            
            while (keepPlaying) {
                //New number for each round
                host.randomizeNum();
                
                //Game loop until all players have taken their turn
                boolean roundOver = false;
                int currentPlayerIndex = 0;
                
                while (!roundOver) {
                    //Let the current player take their turn
                    boolean winCondition = turn.takeTurn(currentPlayers[currentPlayerIndex], host);
                    
                    //Move to the next player
                    currentPlayerIndex = (currentPlayerIndex + 1) % numPlayers;
                    
                    //Check if all players have taken their turns
                    if (currentPlayerIndex == 0) {
                        roundOver = true;
                    }
                }
                
                //Ask the players if they want to play another round
                System.out.print("Do you want to play another round? (Yes/No): ");
                String playAgain = keyboard.nextLine();
                
                if (playAgain.equalsIgnoreCase("no")) {
                    keepPlaying = false;
                    System.out.println("Thanks for playing!");
                }
            }
            
            //Ask if the players want to start a new game
            System.out.print("Do you want to start a new game? (Yes/No): ");
            String newGameResponse = keyboard.nextLine();
            
            if (newGameResponse.equalsIgnoreCase("no")) {
                newGame = false;
                System.out.println("Goodbye!");
            }
        }
        
        keyboard.close();
    }
}