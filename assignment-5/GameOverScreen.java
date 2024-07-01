import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class GameOverScreen extends JPanel implements ActionListener {
    private JButton highscoreBT;
    private gameLoop game;

    public GameOverScreen(gameLoop game) {
        this.game = game;
        setBackground(Color.BLACK);
        setLayout(null);

        highscoreBT = new JButton("Scoreboard");
        highscoreBT.setFont(new Font("Arial", Font.BOLD, 20));
        highscoreBT.setBounds(315, 400, 150, 50);
        highscoreBT.addActionListener(this);

        add(highscoreBT);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.setColor(Color.RED);
        g.setFont(new Font("Arial", Font.BOLD, 64));
        g.drawString("Game Over", 215, 200);
        g.setColor(Color.ORANGE);
        g.setFont(new Font("Arial", Font.BOLD, 32));
        g.drawString("Final score: " + game.maxScore, 275, 300);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == highscoreBT) {
            try{
                game.scoreBoard.addScore(game.maxScore, game.frame);
            }catch(IOException ignore){
                
            }
            game.frame.getContentPane().remove(this);

            game.scoreBoard.showScoreboard(game);
        }
    }
}