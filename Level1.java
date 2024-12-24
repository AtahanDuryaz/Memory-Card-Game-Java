import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;

public class Level1 extends JFrame {
    private static final int GRID_SIZE = 4;
    private static final int PAIR_NUM = (GRID_SIZE * GRID_SIZE) / 2;
    private ArrayList<JButton> buttons;
    private ArrayList<ImageIcon> icons;
    private ImageIcon noimage;
    private JButton firstButton, secondButton;
    private Timer timer;
    public int Level_Status = 1;
    public int TriesLeft = 18 / Menu.LevelStatus;
    public int MatchCount = 0;
    private boolean LevelOver = false;
    private JLabel scoreLabel;
    private JLabel triesLeftLabel;

    public Level1() {
        setTitle("Memory Card Game - Level 1");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 700);
        setLayout(new BorderLayout());

        icons = new ArrayList<>(GRID_SIZE * GRID_SIZE);
        noimage = new ImageIcon(getClass().getResource("no_image.png"));
        IconInitialized();

        JPanel controlPanel = createControlPanel();
        JPanel gamePanel = createGamePanel();

        add(controlPanel, BorderLayout.NORTH);
        add(gamePanel, BorderLayout.CENTER);

        setVisible(true);
    }

    private void IconInitialized() {
        ImageIcon[] icon = new ImageIcon[16];
        icon[0] = new ImageIcon(getClass().getResource("0.png"));
        icon[1] = new ImageIcon(getClass().getResource("0.png"));

        icon[2] = new ImageIcon(getClass().getResource("1.png"));
        icon[3] = new ImageIcon(getClass().getResource("1.png"));

        icon[4] = new ImageIcon(getClass().getResource("2.png"));
        icon[5] = new ImageIcon(getClass().getResource("2.png"));

        icon[6] = new ImageIcon(getClass().getResource("3.png"));
        icon[7] = new ImageIcon(getClass().getResource("3.png"));

        icon[8] = new ImageIcon(getClass().getResource("4.png"));
        icon[9] = new ImageIcon(getClass().getResource("4.png"));

        icon[10] = new ImageIcon(getClass().getResource("5.png"));
        icon[11] = new ImageIcon(getClass().getResource("5.png"));

        icon[12] = new ImageIcon(getClass().getResource("6.png"));
        icon[13] = new ImageIcon(getClass().getResource("6.png"));

        icon[14] = new ImageIcon(getClass().getResource("7.png"));
        icon[15] = new ImageIcon(getClass().getResource("7.png"));

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
        JLabel indication = new JLabel("Level 1   ");
        JLabel gameLabel = new JLabel("Game: ");
        JLabel about = new JLabel("About: ");
        JButton exitButton = new JButton("Exit");

        // Initialize scoreLabel and set initial text
        scoreLabel = new JLabel("Score: " + GameState.getScore());
        triesLeftLabel = new JLabel("Tries Left: " + TriesLeft);

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
        String messageGame = "This game called Memory Game. You are going to get points for right matches and penalty for wrong matches. Good luck and have fun!";

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
        controlPanel.add(scoreLabel);
        controlPanel.add(triesLeftLabel);
        return controlPanel;
    }

    private void restartGame() {
        this.dispose();
        new Level1().setVisible(true);
    }

    private void restartLevel() {
        this.dispose();
        new Level1().setVisible(true);
    }

    public void showHighScore() {
        StringBuilder highScoreText = new StringBuilder();
        for (HighScoreManager.ScoreEntry entry : HighScoreManager.loadScores()) {
            highScoreText.append(entry.name).append(": ").append(entry.score).append("\n");
        }
        JOptionPane.showMessageDialog(this, highScoreText.toString(), "High Score", JOptionPane.INFORMATION_MESSAGE);
    }

    private void MatchCheck() {
        if (firstButton.getIcon().toString().equals(secondButton.getIcon().toString())) {
            MatchCount++;
            GameState.addScore(5); // gamestate classı içerisindeki fonksiyonla puan arttırımı
            firstButton = null;
            secondButton = null;
        } else {
            TriesLeft--;
            GameState.subtractScore(1); // game state puan azaltım
            timer = new Timer(500, new ActionListener() {
                public void actionPerformed(ActionEvent evt) {
                    resetButtons();
                }
            });
            timer.setRepeats(false);
            timer.start();
        }
        scoreLabel.setText("Score: " + GameState.getScore()); //her puan değişikliğinde labelın yazdığı değerinde değişmesi
        triesLeftLabel.setText("Tries Left: " + TriesLeft); // aynı mantıkta kalan denemeleri gösteriyor
    }

    private void resetButtons() {
        firstButton.setIcon(noimage);
        secondButton.setIcon(noimage);
        firstButton = null;
        secondButton = null;
        timer.stop();
    }

    private void EndCheck() {
        String winMessage = "You finish the level 1 thank you for playing";
        String loseMessage = "You fail to finish level 1 please try again";
        if (MatchCount == 8) {
            JOptionPane.showMessageDialog(this, winMessage, "Game Instructions", JOptionPane.INFORMATION_MESSAGE);
            new Level2().setVisible(true);
            this.dispose();
        } else if (TriesLeft == 0) { // kaybettin
            JOptionPane.showMessageDialog(this, loseMessage, "Game Instructions", JOptionPane.INFORMATION_MESSAGE);
            String playerName = JOptionPane.showInputDialog(this, "Enter your name for the high score:", "High Score", JOptionPane.QUESTION_MESSAGE);
            if (playerName != null && !playerName.trim().isEmpty()) {
                HighScoreManager.addHighScore(playerName, GameState.getScore());
            }
            new Menu().setVisible(true);
            this.dispose();
        }
    }
}
