package main;
import javax.swing.JFrame;

public class AppMain {
      public static void main(String[] args) {

                  JFrame window = new JFrame();
                  window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                  window.setResizable(false);
                  window.setTitle("YahooGame");

                  GamePanel gamePanel = new GamePanel();
                  window.add(gamePanel);
                  window.pack();
                  window.setLocationRelativeTo(null);
                  window.setVisible(true);

                  gamePanel.SetupGame();
                  gamePanel.startGameThread();

      }
}