package WordGame;

import java.util.Random;

public class Physical implements Award {
    private static final String[] prizes = {
        "Television", "Laptop", "Smartphone", "Bicycle", "Headphones"
    };

    private static final String[] prizeImages = {
        "Pictures/Television.jpg", "Pictures/Laptop.jpg", 
        "Pictures/Smartphone.jpg", "Pictures/Bicycle.jpg", 
        "Pictures/Headphones.jpg"
    };
    
    private Random random = new Random();
    
    private int getRandomPrizeIndex() {
        return random.nextInt(prizes.length);
    }
    
    @Override
    public int displayWinnings(Players player, boolean correctGuess) {
        int prizeIndex = getRandomPrizeIndex();
        String prize = prizes[prizeIndex];
        
        if (correctGuess) {
            System.out.println(player.getFirstName() + " " + player.getLastName() + " won a " + prize + "!");
        } else {
            System.out.println(player.getFirstName() + " " + player.getLastName() + " lost. You could have won a " + prize + ".");
        }
        
        return correctGuess ? prizeIndex : -1;
    }
    
    public String getPrizeImagePath(int index) {
        return index >= 0 && index < prizeImages.length ? prizeImages[index] : null;
    }
}
