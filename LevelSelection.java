import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;

public class LevelSelection extends JFrame {
    private ImageIcon background;
    private JLabel backgroundLabel;
    private JLayeredPane layeredPane;
    private JButton level1Button;
    private JButton level2Button;
    private JButton level3Button;
    private JButton exitButton;

    public LevelSelection() {
        setTitle("Select Level");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 300);
        setLayout(null); // Use absolute layout
        setLocationRelativeTo(null);

        // Initialize layered pane
        layeredPane = new JLayeredPane();
        layeredPane.setBounds(0, 0, 600, 300);
        this.add(layeredPane);

        // Set background
        Background_set();

        // Add buttons
        Button_Places();

        // Ensure frame is visible at the end of initialization
        this.setVisible(true);
    }

    public void Background_set() {
        //arka plan resmini yüklüyor
        background = new ImageIcon(getClass().getResource("/background.jpg"));

        // Create and configure the background label
        backgroundLabel = new JLabel(background);
        backgroundLabel.setBounds(0, 0, getWidth(), getHeight()); // Set initial bounds to match the frame size

        // Add the background label to the layered pane at the lowest layer
        layeredPane.add(backgroundLabel, JLayeredPane.DEFAULT_LAYER);
    }

    public void Button_Places() {
        level1Button = new JButton("Level 1");
        level2Button = new JButton("Level 2");
        level3Button = new JButton("Level 3");
        exitButton = new JButton("Exit");

        level1Button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Level1 level1 = new Level1();
                level1.setVisible(true);
                dispose();
            }
        });

        level2Button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Level2 level2 = new Level2();
                level2.setVisible(true);
                dispose(); 
            }
        });

        level3Button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Level3 level3 = new Level3();
                level3.setVisible(true);
                dispose(); 
            }
        });

        exitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
               Menu menu=new Menu();
               setVisible(true);
               dispose();
            }
        });

        // GridbagLayout u kullanıp buttonlar arası boşluk yaratıyorsun
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setOpaque(false); // Panel gözükmeyecek 

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 0, 10, 0); // Boşluk ölcüleri

        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(level1Button, gbc);

        gbc.gridy = 1;
        panel.add(level2Button, gbc);

        gbc.gridy = 2;
        panel.add(level3Button, gbc);

        gbc.gridy = 3;
        panel.add(exitButton, gbc);

        panel.setBounds(0, 0, getWidth(), getHeight()); // frame boyutuna ayarlamak için 
        layeredPane.add(panel, JLayeredPane.PALETTE_LAYER);
    }

   
}
