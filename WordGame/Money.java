package WordGame;

public class Money implements Award {
    private static final int winAmount = 50;
    private static final int lossAmount = 25;
    
    @Override
    public int displayWinnings(Players player, boolean correctGuess) {
        if (correctGuess) {
            System.out.println(player.getFirstName() + " " + player.getLastName() + " won $" + winAmount + "!");
            return winAmount;
        } else {
            System.out.println(player.getFirstName() + " " + player.getLastName() + " lost $" + lossAmount + ".");
            return -lossAmount;
        }
    }
}