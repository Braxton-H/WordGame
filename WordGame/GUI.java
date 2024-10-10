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
    private JLabel turnLabel;
    private JTextField guessField;
    private JButton addPlayerButton;
    private JButton setHostButton;
    private JButton startTurnButton;
    private JTextArea messageArea;
    private JCheckBox saveMessagesCheckbox;
    
    private ArrayList<Players> playersList = new ArrayList<>();
    private Hosts host;
    private Phrases phrases;
    private int currentPlayerIndex = -1;
    private Physical physical = new Physical();

    public GUI() {
        setTitle("Word Game");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        
        JMenuBar menuBar = new JMenuBar();

        //The game menu
        JMenu gameMenu = new JMenu("Game");
        gameMenu.setMnemonic('G');
        JMenuItem addPlayerItem = new JMenuItem("Add Player");
        JMenuItem setHostItem = new JMenuItem("Set Host");

        addPlayerItem.addActionListener(new AddPlayerAction());
        setHostItem.addActionListener(new SetHostAction());

        gameMenu.add(addPlayerItem);
        gameMenu.add(setHostItem);
        menuBar.add(gameMenu);

        //the about menu
        JMenu aboutMenu = new JMenu("About");
        aboutMenu.setMnemonic('A');
        JMenuItem layoutItem = new JMenuItem("Layout");

        layoutItem.addActionListener(e -> showLayoutInfo());

        aboutMenu.add(layoutItem);
        menuBar.add(aboutMenu);

        setJMenuBar(menuBar);

        //Panel for the layout
        JPanel statusPanel = new JPanel(new GridLayout(4, 1));
        playersLabel = new JLabel("Current Players: ");
        hostLabel = new JLabel("Current Host: ");
        playingPhraseLabel = new JLabel("Current Phrase: ");
        turnLabel = new JLabel("Current Turn: ");

        statusPanel.add(playersLabel);
        statusPanel.add(hostLabel);
        statusPanel.add(playingPhraseLabel);
        statusPanel.add(turnLabel);

        JPanel inputPanel = new JPanel();
        guessField = new JTextField(10);
        guessField.setVisible(false);
        JButton startTurnButton = new JButton("Start Game");

        startTurnButton.addActionListener(new StartGameAction());
        inputPanel.add(guessField);
        inputPanel.add(startTurnButton);

        //area the messages are at
        messageArea = new JTextArea(5, 30);
        messageArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(messageArea);
        JPanel messagePanel = new JPanel();
        messagePanel.add(scrollPane);

        //saving messages
        saveMessagesCheckbox = new JCheckBox("Save Messages");
        saveMessagesCheckbox.setToolTipText("Check this to keep messages in the text area.");
        saveMessagesCheckbox.addActionListener(e -> {
            if (!saveMessagesCheckbox.isSelected()) {
                messageArea.setText("");
            }
        });
        messagePanel.add(saveMessagesCheckbox);

        //added panels in the frame
        add(statusPanel, BorderLayout.NORTH);
        add(inputPanel, BorderLayout.CENTER);
        add(messagePanel, BorderLayout.SOUTH);

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
                logMessage("Added player: " + newPlayer);
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
            logMessage("Set host: " + host);
        }
    }

    private class StartGameAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            // Check if there are players and a host
            if (playersList.isEmpty() || host == null) {
                logMessage("Please add players and set a host first.");
                return;
            }

            if (currentPlayerIndex == -1) {
                guessField.setVisible(true);
                ((JButton) e.getSource()).setText("Next Turn");
                currentPlayerIndex = 0;
                updatePlayingPhraseLabel();
                updateTurnLabel();
            } else {
                String guess = guessField.getText().trim();
                guessField.setText("");

                if (guess.length() != 1) {
                    logMessage("Please enter a single letter.");
                    return;
                }

                boolean correctGuess = false;

                try {
                    correctGuess = phrases.findLetters(guess);
                    playingPhraseLabel.setText("Current Phrase: " + phrases.getPlayingPhrase());
                    String message = playersList.get(currentPlayerIndex).getFirstName();

                    if (correctGuess) {
                        if (Math.random() < 0.2) {
                            physical.displayWinnings(playersList.get(currentPlayerIndex), correctGuess);
                            logMessage(" You won a physical prize!");
                        } else {
                            //Win or lsoe money instead
                            int moneyChange = new Money().displayWinnings(playersList.get(currentPlayerIndex), correctGuess);
                            playersList.get(currentPlayerIndex).setCurrentMoney(playersList.get(currentPlayerIndex).getCurrentMoney() + moneyChange);
                            logMessage(" You won $" + moneyChange + "!");
                            updatePlayersLabel();
                        }
                    } else {
                        int moneyChange = new Money().displayWinnings(playersList.get(currentPlayerIndex), correctGuess);
                        playersList.get(currentPlayerIndex).setCurrentMoney(playersList.get(currentPlayerIndex).getCurrentMoney() + moneyChange);
                        logMessage(" You lost $" + moneyChange + "!");
                        updatePlayersLabel();
                    }

                    logMessage(message);
                    currentPlayerIndex = (currentPlayerIndex + 1) % playersList.size();
                    
                    //Check if all Letters are guessed
                    if (!phrases.getPlayingPhrase().contains("_")) {
                        int option = JOptionPane.showConfirmDialog(GUI.this, "Congratulations! You've guessed the phrase. Do you want to play again?", "Game Over", JOptionPane.YES_NO_OPTION);
                        if (option == JOptionPane.YES_OPTION) {
                            String newPhrase = JOptionPane.showInputDialog("Enter a new phrase:");
                            host.setGamePhrase(newPhrase);
                            phrases = host.getPhrases();
                            currentPlayerIndex = 0; //Reset to the first player for the new game
                            updatePlayingPhraseLabel();
                            updateTurnLabel();
                            logMessage("New game started with a new phrase!");
                        } else {
                            System.exit(0);//Exist the game if selected No
                        }
                    }

                } catch (Exception ex) {
                    logMessage("An error occurred: " + ex.getMessage());
                }
            }

            updateTurnLabel();
        }
    }


    private void updatePlayersLabel() {
        StringBuilder playerNames = new StringBuilder("Current Players: ");
        for (Players player : playersList) {
            playerNames.append(player.toString()).append(", ");
        }
        if (playerNames.length() > 0) {
            playerNames.setLength(playerNames.length() - 2);
        }
        playersLabel.setText(playerNames.toString());
    }

    private void updateTurnLabel() {
        turnLabel.setText("Current Turn: " + playersList.get(currentPlayerIndex).getFirstName());
    }

    private void updateHostLabel() {
        hostLabel.setText("Current Host: " + host.toString());
    }

    private void updatePlayingPhraseLabel() {
        playingPhraseLabel.setText("Current Phrase: " + phrases.getPlayingPhrase());
    }

    private void logMessage(String message) {
        if (saveMessagesCheckbox.isSelected()) {
            messageArea.append(message + "\n");
        } else {
            messageArea.setText(message + "\n");
        }
    }

    private void showLayoutInfo() {
        JOptionPane.showMessageDialog(this, "I chose this layout cause it is simple and I think it looks nicer than the rest that I tested. Flow made it move around a lot when people were added.");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(GUI::new);
    }
}