import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.util.Random;

public class WhackAMole {
    int boardWidth = 600;
    int boardHeight = 650;

    JFrame frame = new JFrame("Whack A Mole");
    JLabel textLabel = new JLabel();
    JPanel textPanel = new JPanel();
    JPanel boardPanel = new JPanel();

    JButton[] board = new JButton[9];
    ImageIcon moleIcon;
    ImageIcon plantIcon;

    JButton currentMoleTile;
    JButton currentPlantTile;

    Random random = new Random();
    Timer setMoleTimer;
    Timer setPlantTimer;
    int score = 0;

    WhackAMole() {
        frame.setSize(boardWidth, boardHeight);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        textLabel.setFont(new Font("Arial", Font.BOLD, 40));
        textLabel.setHorizontalAlignment(JLabel.CENTER);
        textLabel.setText("Click on the mole to play!");
        textLabel.setOpaque(true);

        textPanel.setLayout(new BorderLayout());
        textPanel.add(textLabel);
        frame.add(textPanel, BorderLayout.NORTH);

        boardPanel.setLayout(new GridLayout(3, 3));
        frame.add(boardPanel);

        Image moleImage = new ImageIcon(getClass().getResource("./capybara.png")).getImage();
        moleIcon = new ImageIcon(moleImage.getScaledInstance(150, 150, java.awt.Image.SCALE_SMOOTH));

        Image plantImage = new ImageIcon(getClass().getResource("./piranha.png")).getImage();
        plantIcon = new ImageIcon(plantImage.getScaledInstance(150, 150, java.awt.Image.SCALE_SMOOTH));

        for (int i = 0; i < 9; i++) {
            JButton tile = new JButton();
            board[i] = tile;
            boardPanel.add(tile);
            tile.setFocusable(false);
            tile.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    JButton tile = (JButton) e.getSource();
                    if (tile == currentMoleTile) {
                        score++;
                        textLabel.setText("Score: " + score);
                    } else if (tile == currentPlantTile) {
                        textLabel.setText("Game over. Final score: " + score);
                        setMoleTimer.stop();
                        setPlantTimer.stop();
                        for (int i = 0; i < 9; i++) {
                            board[i].setEnabled(false);
                        }
                    }
                }
            });
        }

        setMoleTimer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (currentMoleTile != null) {
                    currentMoleTile.setIcon(null);
                    currentMoleTile = null;
                }

                int num = random.nextInt(9);
                JButton tile = board[num];

                if(currentPlantTile == tile) return;

                currentMoleTile = tile;
                currentMoleTile.setIcon(moleIcon);
            }
        });

        setPlantTimer = new Timer(1500, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (currentPlantTile != null) {
                    currentPlantTile.setIcon(null);
                    currentPlantTile = null;
                }

                int num = random.nextInt(9);
                JButton tile = board[num];

                if (currentMoleTile == tile) return;

                currentPlantTile = tile;
                currentPlantTile.setIcon(plantIcon);
            }
        });

        setMoleTimer.start();
        setPlantTimer.start();
        frame.setVisible(true);
    }
}
