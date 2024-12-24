import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.List;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;

public class Menu extends JFrame {
    private JButton start;
    private JButton levelSelect;
    private JButton instructions;
    private JButton exit;
    private JButton highScore;
    private ImageIcon background;
    private JLabel backgroundLabel;
    private JLabel titleLabel;
    private JLayeredPane layeredPane;
    public static int LevelStatus = 1;

    Menu() {
        // Frame boyutları ve ayarlarını hallediyor 
        this.setSize(600, 400);
        this.setTitle("Memory Game");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(null); // Use absolute layout
        this.setLocationRelativeTo(null);
        this.setResizable(false);

        //  layered pane kullanımı
        layeredPane = new JLayeredPane();
        layeredPane.setBounds(0, 0, 600, 500);
        this.add(layeredPane);

        // background
        Background_set();

        

        // button ekliyor fonksiyonu çağırarak
        Button_Places();

        

        // Ensure frame is visible at the end of initialization
        this.setVisible(true);
    }

    public void Background_set() {
        // resimlerin yüklenmesi
        background = new ImageIcon(getClass().getResource("/background.jpg"));

        // bakcgroundu label olarak alıp arkada durmasını sağlıyoruz
        backgroundLabel = new JLabel(background);
        backgroundLabel.setBounds(0, 0, getWidth(), getHeight()); // frame boyutunu ayarlıyor

        // layerların en alt kısmına labela attığımız backgroundu yerleştiriyoruz
        layeredPane.add(backgroundLabel, JLayeredPane.DEFAULT_LAYER);
    }

    public void Button_Places() { //buttonların oluşturulması ve işlevlendirilmesi
        boolean instruction_clicked = false;
        String message = "Instructions:\n\n" +
                "There are 3 levels in the game. It gets gradually harder!\nMatch all pairs to win!! ";

        start = new JButton("Start");
        start.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                GameState.resetScore();
                startLevel1();
            }
        });

        levelSelect = new JButton("Select Level");
        levelSelect.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                LevelSelection levelslc = new LevelSelection();
                dispose();
            }
        });

        instructions = new JButton("Instructions");
        instructions.addActionListener(e -> {
            JOptionPane.showMessageDialog(this, message, "Game Instructions", JOptionPane.INFORMATION_MESSAGE);
        });

        highScore = new JButton("High Score");
        highScore.addActionListener(e -> {
            showHighScores();
        });

        exit = new JButton("Exit");
        exit.addActionListener(e -> System.exit(0));

        // Add buttons to the layered pane at the higher layer
        layeredPane.add(start, JLayeredPane.PALETTE_LAYER);
        layeredPane.add(levelSelect, JLayeredPane.PALETTE_LAYER);
        layeredPane.add(instructions, JLayeredPane.PALETTE_LAYER);
        layeredPane.add(highScore, JLayeredPane.PALETTE_LAYER);
        layeredPane.add(exit, JLayeredPane.PALETTE_LAYER);

        //button yerleştirmek için 
        adjustComponents();
    }

    private void showHighScores() {
        List<HighScoreManager.ScoreEntry> scores = HighScoreManager.loadScores();
        StringBuilder highScoreText = new StringBuilder("Top 10 High Scores:\n");
        for (HighScoreManager.ScoreEntry entry : scores) {
            highScoreText.append(entry.name).append(": ").append(entry.score).append("\n");
        }
        JOptionPane.showMessageDialog(this, highScoreText.toString(), "High Score", JOptionPane.INFORMATION_MESSAGE);
    }

    private void startLevel1() {
        Level1 level1 = new Level1();
        level1.setVisible(true);
        this.dispose(); // Close the menu window
    }

    private void adjustComponents() {
        Dimension size = this.getSize();

        
        
        int buttonWidth = 150;
        int buttonHeight = 30;
        int buttonX = (size.width - buttonWidth) / 2;

        start.setBounds(buttonX, (size.height / 2) - 100, buttonWidth, buttonHeight);
        levelSelect.setBounds(buttonX, (size.height / 2) - 60, buttonWidth, buttonHeight);
        instructions.setBounds(buttonX, (size.height / 2) - 20, buttonWidth, buttonHeight);
        highScore.setBounds(buttonX, (size.height / 2) + 20, buttonWidth, buttonHeight);
        exit.setBounds(buttonX, (size.height / 2) + 60, buttonWidth, buttonHeight);
    }
}
