package idi135;
import javax.swing.*;
import java.awt.*;

public class ScorePanel extends JPanel {

    private JLabel scoreLabel;
    private JLabel levelLabel;

    public ScorePanel(){

        scoreLabel = new JLabel("Score: 0");
        levelLabel = new JLabel("Level: 1");

        setLayout(new FlowLayout(FlowLayout.LEFT));
        add(scoreLabel);
        add(levelLabel);

        scoreLabel.setFont(new Font("Arial", Font.BOLD, 16));
        levelLabel.setFont(new Font("Arial", Font.BOLD, 16));

        setBackground(new Color(0, 0, 0));

        scoreLabel.setForeground(Color.YELLOW);
        levelLabel.setForeground(Color.YELLOW);

        setBorder(BorderFactory.createEmptyBorder(3, 200, 5, 20));
    }
    
    public void updateScore(int score){
        scoreLabel.setText("Score: " + score);
    }

    public void updateLevel(int level){
        levelLabel.setText("Level: " + level);
    }


}