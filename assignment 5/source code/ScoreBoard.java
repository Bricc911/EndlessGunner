import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Rectangle2D;


public class ScoreBoard {
    private File file;
    private ScoreRecord[] records = new ScoreRecord[10];

    public ScoreBoard(File file) throws FileNotFoundException {

        this.file = file;

        if(file.exists()){
            readScores();
        }
    }

    public void addScore(int score, JFrame frame) throws IOException{

        if(insertScore(score, frame)){
            writeScores();
        }
    }

    private boolean insertScore(int score, JFrame frame){

        for (int i = 0; i < records.length; i++) {

            if (records[i] == null || records[i].score < score) {

                for (int j = records.length - 2; j >= i ; j--) {
                    records[j + 1] =  records[j];
                }

                records[i] = new ScoreRecord(score, frame);
                return true;
            }
        }

        return false;
    }

    public void showScoreboard(GameLoop game){
        HighscoreScreen screen = new HighscoreScreen(game);
        game.frame.add(screen);
        game.frame.validate();
        game.frame.repaint();
    }

    private void readScores() throws FileNotFoundException{
        Scanner scanner = new Scanner(file);

        for(int i = 0; scanner.hasNext() && i < records.length; i++){

            records[i] = new ScoreRecord(scanner.nextLine(), scanner.nextInt());
            scanner.nextLine();
        }

        scanner.close();
    }

    private void writeScores() throws IOException{

        FileWriter rawWriter = new FileWriter(file);
        BufferedWriter writer = new BufferedWriter(rawWriter);

        for (ScoreRecord record : records) {
            if(record == null){
                break;
            }
            writer.write(record.name + "\n" + record.score + "\n");
        }

        writer.close();
        rawWriter.close();
    }




    private class ScoreRecord{
        private String name;
        private int score;
        private boolean isNew = false;

        public ScoreRecord(String name, int score){
            this.name = name;
            this.score = score;
        }

        /*
         * Insert new score in the highscore list and ask for name when needed.
         * 
         * Also importend is that this constructor wil set the isNew boolean.
         * This is the only time isNew is set;
         */
        private ScoreRecord(int score, JFrame frame){

            isNew = true;

            this.score = score;

            String name = (String)JOptionPane.showInputDialog(
                frame,
                "What is your name?",
                "Your name",
                JOptionPane.PLAIN_MESSAGE,
                null,
                null,
                null
            );

            this.name = name != null ? name : "";
        }
    }


    private class HighscoreScreen extends JPanel implements ActionListener {
        private JButton exitButton;
        private GameLoop game;

        public HighscoreScreen(GameLoop game) {
            this.game = game;
            setBackground(Color.BLACK);
            setLayout(null);

            exitButton = new JButton("Exit");
            exitButton.setFont(new Font("Arial", Font.BOLD, 20));
            exitButton.setBounds(340, 400, 100, 50);
            exitButton.addActionListener(this);

            add(exitButton);
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g;

            super.paintComponent(g2);

            g2.setColor(Color.RED);
            g2.setFont(new Font("Arial", Font.BOLD, 64));
            g2.drawString("Highscores", 215, 75);
            g2.setColor(Color.ORANGE);
            g2.setFont(new Font("Arial", Font.BOLD, 32));
            g2.drawString("Final score: " + game.maxScore, 285, 140);

            g2.setFont(new Font("Arial", Font.BOLD, 16));

            FontMetrics f = g2.getFontMetrics();
            ScoreRecord[] records = game.scoreBoard.records;

            int leftClip = 220;
            int rightClip = 570;
            int topClipStart = 200;
            int clipHeight = 20;

            Rectangle2D clip = new Rectangle2D.Float();
            
            for (int i = 0; i < records.length && records[i] != null; i++) {

                String name = records[i].name;
                String score = String.valueOf(records[i].score);
                
                g2.setColor(records[i].isNew ? Color.GREEN : Color.ORANGE);
                
                // Sets clip to prevent names from writing outside their own spot
                // and changing their (apparent score) score.
                clip.setFrame(
                    leftClip,
                    topClipStart + i * clipHeight - clipHeight / 4 * 3,
                    rightClip - leftClip - f.stringWidth(score) - 50,
                    clipHeight
                );
                g2.setClip(clip);
                g2.drawString(name, leftClip, topClipStart + i * clipHeight);

                // Bigger clip so the score can be written.
                clip.setFrame(
                    leftClip,
                    topClipStart + i * clipHeight - clipHeight / 4 * 3,
                    rightClip - leftClip,
                    clipHeight
                );
                g2.setClip(clip);
                g2.drawString(score, rightClip - f.stringWidth(score), topClipStart + i * clipHeight);
            }
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == exitButton) {
                System.exit(0);
            }
        }
    }
}
