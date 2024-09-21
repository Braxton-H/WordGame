package WordGame;

import java.util.Arrays;

public class Phrases {
    private static String gamePhrase;
    private String playingPhrase;

    public static void setGamePhrase(String phrase) {
        gamePhrase = phrase;
    }

    public void createPlayingPhrase() {
        //Replace each letter with an underline
        playingPhrase = gamePhrase.replaceAll("[a-zA-Z]", "_");
    }

    public String getPlayingPhrase() {
        return playingPhrase;
    }

    public boolean findLetters(String letter) throws MultipleLettersException {
        if (letter.length() != 1) {
            throw new MultipleLettersException();
        }

        char[] phraseChars = playingPhrase.toCharArray();
        char targetChar = letter.toLowerCase().charAt(0);
        boolean found = false;

        //Compare in lowercase
        for (int i = 0; i < gamePhrase.length(); i++) {
            if (Character.toLowerCase(gamePhrase.charAt(i)) == targetChar) {
                phraseChars[i] = gamePhrase.charAt(i);
                found = true;
            }
        }

        playingPhrase = new String(phraseChars);
        return found;
    }
}
