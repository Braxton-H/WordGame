package WordGame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class GUI extends JFrame {
    private JLabel playersLabel;
    private JLabel hostLabel;
    private JLabel playingPhraseLabel;
    private JLabel turnLabel; // New label to display whose turn it is
    private JTextField guessField;
    private JButton addPlayerButton;
    private JButton setHostButton;
    private JButton startTurnButton;
    
    private ArrayList<Players> playersList = new ArrayList<>();
    private Hosts host;
    private Phrases phrases;
    private int currentPlayerIndex = -1; // Make this an instance variable
    private Physical physical = new Physical(); // Create an instance of Physical

    public GUI() {
        setTitle("Word Game");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new FlowLayout());

        playersLabel = new JLabel("Current Players: ");
        hostLabel = new JLabel("Current Host: ");
        playingPhraseLabel = new JLabel("Current Phrase: ");
        turnLabel = new JLabel("Current Turn: "); // Initialize the turn label
        guessField = new JTextField(10);
        guessField.setVisible(false); // Initially hide the guess field
        addPlayerButton = new JButton("Add Player");
        setHostButton = new JButton("Set Host");
        startTurnButton = new JButton("Start Game");

        add(playersLabel);
        add(hostLabel);
        add(playingPhraseLabel);
        add(turnLabel); // Add the turn label to the UI
        add(guessField);
        add(addPlayerButton);
        add(setHostButton);
        add(startTurnButton);

        addPlayerButton.addActionListener(new AddPlayerAction());
        setHostButton.addActionListener(new SetHostAction());
        startTurnButton.addActionListener(new StartGameAction());

        setVisible(true);
    }

    private class AddPlayerAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String playerName = JOptionPane.showInputDialog("Enter Player's First Name:");
            if (playerName != null && !playerName.trim().isEmpty()) {
                String lastName = JOptionPane.showInputDialog("Enter Player's Last Name (or leave blank):");
                Players newPlayer = lastName != null && !lastName.trim().isEmpty()
                        ? new Players(playerName, lastName) 
                        : new Players(playerName);
                playersList.add(newPlayer);
                updatePlayersLabel();
            }
        }
    }

    private class SetHostAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String hostFirstName = JOptionPane.showInputDialog("Enter Host's First Name:");
            String hostLastName = JOptionPane.showInputDialog("Enter Host's Last Name (or leave blank):");
            host = hostLastName != null && !hostLastName.trim().isEmpty()
                    ? new Hosts(hostFirstName, hostLastName)
                    : new Hosts(hostFirstName);
            
            String gamePhrase = JOptionPane.showInputDialog("Enter Game Phrase:");
            host.setGamePhrase(gamePhrase);
            phrases = host.getPhrases();
            updateHostLabel();
            updatePlayingPhraseLabel();
        }
    }

    private class StartGameAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            // Check if players and host are set
            if (playersList.isEmpty() || host == null) {
                JOptionPane.showMessageDialog(null, "Please add players and set a host first.");
                return;
            }

            // If it's the first time starting the game
            if (currentPlayerIndex == -1) {
                // Show the guess field and update button label
                guessField.setVisible(true);
                startTurnButton.setText("Next Turn");
                addPlayerButton.setEnabled(false); // Disable add player button
                setHostButton.setEnabled(false); // Disable set host button
                currentPlayerIndex = 0; // Start with the first player
                updatePlayingPhraseLabel();
                updateTurnLabel(); // Display current turn
            } else {
                // Get the guess and validate it
                String guess = guessField.getText().trim();
                guessField.setText(""); // Clear the text field immediately after reading

                // Validate the guess
                if (guess.length() != 1) {
                    JOptionPane.showMessageDialog(null, "Please enter a single letter.");
                    return; // Don't change the player, just return
                }

                boolean correctGuess = false;

                try {
                    correctGuess = phrases.findLetters(guess);
                    playingPhraseLabel.setText("Current Phrase: " + phrases.getPlayingPhrase());

                    String message = playersList.get(currentPlayerIndex).getFirstName() + ", ";

                    if (correctGuess == true) {
                        // Determine if the player wins a physical prize (20% chance)
                        if (Math.random() < 0.2) {
                            physical.displayWinnings(playersList.get(currentPlayerIndex), correctGuess);
                            message += "You won a physical prize!";
                        } else {
                            // Win money instead
                            int moneyChange = new Money().displayWinnings(playersList.get(currentPlayerIndex), correctGuess);
                            playersList.get(currentPlayerIndex).setCurrentMoney(playersList.get(currentPlayerIndex).getCurrentMoney() + moneyChange);
                            message += "You won $" + moneyChange + "!";
                            updatePlayersLabel();
                        }
                    } else {
                    	int moneyChange = new Money().displayWinnings(playersList.get(currentPlayerIndex), correctGuess);
                        playersList.get(currentPlayerIndex).setCurrentMoney(playersList.get(currentPlayerIndex).getCurrentMoney() + moneyChange);
                        message += "You lost $" + moneyChange + "!";
                        updatePlayersLabel();

                    }

                    // Show the message to the player
                    message += "\nYou currently have $" + playersList.get(currentPlayerIndex).getCurrentMoney() + ".";
                    JOptionPane.showMessageDialog(null, message);

                    // Check if the player has won
                    if (!phrases.getPlayingPhrase().contains("_")) {
                        // Display winner message and prompt for a new game
                        String winnerMessage = playersList.get(currentPlayerIndex).getFirstName() + " has guessed the phrase!";
                        int playAgain = JOptionPane.showConfirmDialog(null, winnerMessage + "\nDo you want to play a new game?", "Play Again", JOptionPane.YES_NO_OPTION);
                        
                        if (playAgain == JOptionPane.YES_OPTION) {
                            // Prompt for a new phrase
                            String newPhrase = JOptionPane.showInputDialog("Enter a new phrase for the next game:");
                            if (newPhrase != null && !newPhrase.trim().isEmpty()) {
                                host.setGamePhrase(newPhrase);
                                phrases.createPlayingPhrase(); // Create a new phrase for the next round
                                updatePlayingPhraseLabel();
                            }
                            currentPlayerIndex = 0; // Reset to the first player for the new game
                        } else {
                            currentPlayerIndex = -1; // Reset to -1 to allow starting fresh
                            startTurnButton.setText("Start Game"); // Reset button label
                            guessField.setVisible(false); // Hide the guess field
                            addPlayerButton.setEnabled(true); // Re-enable add player button
                            setHostButton.setEnabled(true); // Re-enable set host button
                            return; // Exit the method after the game ends
                        }
                    }
                } catch (MultipleLettersException ex) {
                    JOptionPane.showMessageDialog(null, ex.getMessage());
                    return; // Don't change the player, just return
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "An error occurred: " + ex.getMessage());
                    return; // Don't change the player, just return
                }

                // Move to the next player
                currentPlayerIndex = (currentPlayerIndex + 1) % playersList.size();
            }

            updateTurnLabel(); // Update the turn label for the next player
        }
    }



    private void updatePlayersLabel() {
        StringBuilder playerNames = new StringBuilder("Current Players: ");
        for (Players player : playersList) {
            playerNames.append(player.toString()).append(", "); // Removed money display
        }
        // Remove the trailing comma and space
        if (playerNames.length() > 0) {
            playerNames.setLength(playerNames.length() - 2);
        }
        playersLabel.setText(playerNames.toString());
    }

    private void updateTurnLabel() {
        turnLabel.setText("Current Turn: " + playersList.get(currentPlayerIndex).getFirstName()); // Display the current player's name
    }

    private void updateHostLabel() {
        hostLabel.setText("Current Host: " + host.toString());
    }

    private void updatePlayingPhraseLabel() {
        playingPhraseLabel.setText("Current Phrase: " + phrases.getPlayingPhrase());
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(GUI::new);
    }
}