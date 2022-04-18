package main;
import java.awt.Color;
import java.awt.*;

public class Minimap{

    GamePanel gp;

    public Minimap(GamePanel gp){
        this.gp = gp;
    }

    public void draw(Graphics2D g2){
        //Draw Background
        g2.setPaint(Color.GRAY);
        g2.fillRect(0, 0, gp.maxWorldCol*2, gp.maxWorldRow*2);

        //Draw Self
        int x = gp.player.worldX/gp.tileSize*2;
        int y = gp.player.worldY/gp.tileSize*2;
        g2.setPaint(Color.blue);
        g2.fillRect(x, y, 4, 4);

        //Draw Enemy
        for(int i=0;i<gp.MaxPlayer;i++){
            if(gp.EnemyLocation[i][0].equals("Jeff12")==false && gp.EnemyLocation[i][0].equals(gp.name)==false && Integer.parseInt(gp.EnemyLocation[i][4]) > 0){
                int X = Integer.parseInt(gp.EnemyLocation[i][1]) / gp.tileSize*2;
                int Y = Integer.parseInt(gp.EnemyLocation[i][2]) / gp.tileSize*2;
                g2.setPaint(Color.red);
                g2.fillRect(X, Y, 4, 4);
            }
        }

        //Draw Et
        for(int i=0;i<gp.max_et;i++){
            int xX = gp.obj[gp.aSetter.EtStart+i].worldX /gp.tileSize*2;
            int yY = gp.obj[gp.aSetter.EtStart+i].worldY /gp.tileSize*2;
            g2.setPaint(Color.GREEN);
            g2.fillRect(xX, yY, 4, 4);
        }
    }
}
