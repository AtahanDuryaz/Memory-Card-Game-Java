import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class Level3 extends JFrame {
    private static final int GRID_SIZE = 4;
    private static final int PAIR_NUM = (GRID_SIZE * GRID_SIZE) / 2;
    private ArrayList<JButton> buttons;
    private ArrayList<ImageIcon> icons;
    private ArrayList<ImageIcon> matchedIcons;
    private ArrayList<JButton> matchedButtons;
    private ImageIcon noimage;
    private JButton firstButton, secondButton;
    private JLabel scoreLabel;
    private JLabel triesLeftLabel;
    private Timer timer;
    public int Level_Status = 3;
    public int TriesLeft = 10; // Adjust triesLeft for Level 3
    public int MatchCount = 0;
    private boolean LevelOver = false;

    public Level3() {
        setTitle("Memory Card Game - Level 3");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 700);
        setLayout(new BorderLayout());

        icons = new ArrayList<>(GRID_SIZE * GRID_SIZE);
        matchedIcons = new ArrayList<>(GRID_SIZE * GRID_SIZE);
        matchedButtons = new ArrayList<>(GRID_SIZE * GRID_SIZE);
        noimage = new ImageIcon(getClass().getResource("no_image3.png"));
        IconInitialized();

        JPanel controlPanel = createControlPanel();
        JPanel gamePanel = createGamePanel();

        add(controlPanel, BorderLayout.NORTH);
        add(gamePanel, BorderLayout.CENTER);

        setVisible(true);
    }

    private void IconInitialized() {
        ImageIcon[] icon = new ImageIcon[16];
        icon[0] = new ImageIcon(getClass().getResource("30.png"));
        icon[1] = new ImageIcon(getClass().getResource("30.png"));
        icon[2] = new ImageIcon(getClass().getResource("31.png"));
        icon[3] = new ImageIcon(getClass().getResource("31.png"));
        icon[4] = new ImageIcon(getClass().getResource("32.png"));
        icon[5] = new ImageIcon(getClass().getResource("32.png"));
        icon[6] = new ImageIcon(getClass().getResource("33.png"));
        icon[7] = new ImageIcon(getClass().getResource("33.png"));
        icon[8] = new ImageIcon(getClass().getResource("34.png"));
        icon[9] = new ImageIcon(getClass().getResource("34.png"));
        icon[10] = new ImageIcon(getClass().getResource("35.png"));
        icon[11] = new ImageIcon(getClass().getResource("35.png"));
        icon[12] = new ImageIcon(getClass().getResource("36.png"));
        icon[13] = new ImageIcon(getClass().getResource("36.png"));
        icon[14] = new ImageIcon(getClass().getResource("37.png"));
        icon[15] = new ImageIcon(getClass().getResource("37.png"));

        // Add icons to the ArrayList
        Collections.addAll(icons, icon);

        // Shuffle the icons
        Collections.shuffle(icons);
    }

    private JPanel createGamePanel() {
        JPanel gamePanel = new JPanel(new GridLayout(GRID_SIZE, GRID_SIZE));
        buttons = new ArrayList<>(GRID_SIZE * GRID_SIZE);
        for (int i = 0; i < GRID_SIZE * GRID_SIZE; i++) {
            JButton button = new JButton();
            button.setIcon(noimage); // Set default no image
            int index = i;
            button.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    button.setIcon(icons.get(index)); // Set image to button
                    if (firstButton == null) {
                        firstButton = button;
                    } else if (secondButton == null) {
                        if (button != firstButton) {
                            secondButton = button;
                            MatchCheck();
                            EndCheck();
                        }
                    }
                }
            });
            buttons.add(button);
            gamePanel.add(button); // Add the JButton to the gamePanel
        }
        return gamePanel;
    }

    private JPanel createControlPanel() {
        JPanel controlPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel indication = new JLabel("Level 3   ");
        JLabel gameLabel = new JLabel("Game: ");
        JLabel about = new JLabel("About: ");
        JButton exitButton = new JButton("Exit");
        triesLeftLabel = new JLabel("Tries Left: " + TriesLeft);  // Initialize triesLeftLabel
        scoreLabel = new JLabel("Score: " + GameState.getScore());  // Initialize scoreLabel

        String[] gameOptions = {"Restart Game", "Restart Level", "High Score"};
        JComboBox<String> gameComboBox = new JComboBox<>(gameOptions);
        String[] aboutOptions = {"Developer", "Game"};
        JComboBox<String> aboutComboBox = new JComboBox<>(aboutOptions);

        exitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Menu menu = new Menu();
                menu.setVisible(true);
                dispose();
            }
        });

        gameComboBox.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String selectedOption = (String) gameComboBox.getSelectedItem();
                if (selectedOption.equals("Restart Game")) {
                    restartGame();
                } else if (selectedOption.equals("Restart Level")) {
                    restartLevel();
                } else if (selectedOption.equals("High Score")) {
                    showHighScore();
                }
            }
        });

        String messageDev = "Atahan Düryaz 20220702085";
        String messageGame = "This game is called Memory Game. You get points for right matches and penalties for wrong matches. Good luck and have fun!";

        aboutComboBox.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String selectedOption = (String) aboutComboBox.getSelectedItem();
                if (selectedOption.equals("Developer")) {
                    JOptionPane.showMessageDialog(createGamePanel(), messageDev);
                } else if (selectedOption.equals("Game")) {
                    JOptionPane.showMessageDialog(createGamePanel(), messageGame);
                }
            }
        });

        controlPanel.add(indication);
        controlPanel.add(gameLabel);
        controlPanel.add(gameComboBox);
        controlPanel.add(about);
        controlPanel.add(aboutComboBox);
        controlPanel.add(exitButton);
        controlPanel.add(scoreLabel); // Add the scoreLabel to the control panel
        controlPanel.add(triesLeftLabel); // Add triesLeftLabel to the control panel
        return controlPanel;
    }

    private void restartGame() {
        this.dispose();
        new Level1().setVisible(true);
    }

    private void restartLevel() {
        this.dispose();
        new Level3().setVisible(true);
    }

    private void showHighScore() {
        StringBuilder highScoreText = new StringBuilder();
        for (HighScoreManager.ScoreEntry entry : HighScoreManager.loadScores()) {
            highScoreText.append(entry.name).append(": ").append(entry.score).append("\n");
        }
        JOptionPane.showMessageDialog(this, highScoreText.toString(), "High Score", JOptionPane.INFORMATION_MESSAGE);
    }

    private void MatchCheck() {
        if (firstButton.getIcon().toString().equals(secondButton.getIcon().toString())) {
            MatchCount++;
            GameState.addScore(6); // Use GameState to add score
            matchedButtons.add(firstButton);
            matchedButtons.add(secondButton);
            matchedIcons.add((ImageIcon) firstButton.getIcon());
            firstButton.setEnabled(false); // Disable matched buttons
            secondButton.setEnabled(false);
            firstButton = null;
            secondButton = null;
        } else {
            TriesLeft--;
            GameState.subtractScore(3); // Use GameState to subtract score
            timer = new Timer(500, new ActionListener() {
                public void actionPerformed(ActionEvent evt) {
                    new Thread(new Shuffler()).start(); // Start the shuffling in a new thread
                    resetButtons();
                }
            });
            timer.setRepeats(false);
            timer.start();
        }
        scoreLabel.setText("Score: " + GameState.getScore()); // Update score label text
        triesLeftLabel.setText("Tries Left: " + TriesLeft);  // Update triesLeftLabel text
    }

    private void resetButtons() {
        firstButton.setIcon(noimage);
        secondButton.setIcon(noimage);
        firstButton = null;
        secondButton = null;
        timer.stop();
    }

    private void EndCheck() {
        String winMessage = "You have finished level 3. Thank you for playing!";
        String loseMessage = "You failed to finish level 3. Please try again.";
        if (MatchCount == 8) {
            JOptionPane.showMessageDialog(this, winMessage, "Game Instructions", JOptionPane.INFORMATION_MESSAGE);
            new Menu().setVisible(true);
            this.dispose();
        } else if (TriesLeft == 0) JOptionPane.showMessageDialog(this, loseMessage, "Game Instructions", JOptionPane.INFORMATION_MESSAGE);
        String playerName = JOptionPane.showInputDialog(this, "Enter your name for the high score:", "High Score", JOptionPane.QUESTION_MESSAGE);
        if (playerName != null && !playerName.trim().isEmpty()) {
            HighScoreManager.addHighScore(playerName, GameState.getScore());
        }
            showHighScore();
            new Menu().setVisible(true);
            this.dispose();
        }
    

    private class Shuffler implements Runnable {
        @Override
        public void run() {
            // Create a list of indices for matched buttons and non-matched buttons
            ArrayList<Integer> indices = new ArrayList<>(GRID_SIZE * GRID_SIZE);
            ArrayList<JButton> allButtons = new ArrayList<>(buttons); // Create a copy of the buttons list

            // Remove the matched buttons from the list
            allButtons.removeAll(matchedButtons);

            // Add indices of non-matched buttons to the list
            for (int i = 0; i < allButtons.size(); i++) {
                indices.add(i);
            }

            // Shuffle the indices
            Collections.shuffle(indices);

            // Shuffle the icons of non-matched buttons
            ArrayList<ImageIcon> nonMatchedIcons = new ArrayList<>();
            for (JButton button : allButtons) {
                nonMatchedIcons.add((ImageIcon) button.getIcon());
            }
            Collections.shuffle(nonMatchedIcons);

            // Reassign shuffled icons to non-matched buttons
            for (int i = 0; i < indices.size(); i++) {
                allButtons.get(indices.get(i)).setIcon(nonMatchedIcons.get(i));
            }
        }
    }
}
