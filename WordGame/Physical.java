package WordGame;

import java.util.Random;

public class Physical implements Award {
    private static final String[] prizes = {
        "Television", "Laptop", "Smartphone", "Bicycle", "Headphones"
    };
    
    private Random random = new Random();
    
    private int getRandomPrize() {
        return random.nextInt(prizes.length);
    }
    
    @Override
    public int displayWinnings(Players player, boolean correctGuess) {
        int prizeIndex = getRandomPrize();
        String prize = prizes[prizeIndex];
        
        if (correctGuess) {
            System.out.println(player.getFirstName() + " " + player.getLastName() + " won a " + prize + "!");
            return 0;
        } else {
            System.out.println(player.getFirstName() + " " + player.getLastName() + " lost. You could have won a " + prize + ".");
            return 0;
        }
    }
}