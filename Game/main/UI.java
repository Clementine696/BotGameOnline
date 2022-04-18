package main;
import java.awt.Color;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import object.OBJ_Bomb;

public class UI {
    GamePanel gp;
    Graphics2D g2;
    Font arial_40;
    BufferedImage keyImage;
    public boolean messageOn = false;
    public String message = "";
    int messageCounter = 0;

    public boolean messageOn2 = false;
    public String message2 = "";
    int messageCounter2 = 0;

    public UI(GamePanel gp){
        this.gp = gp;
        arial_40 = new Font("Arial", Font.PLAIN, 40);
        OBJ_Bomb key = new OBJ_Bomb(gp);
        keyImage = key.image;
    }

    public void showMessage(String text) {
        messageCounter=0;
        message = text;
        messageOn = true;
    }

    public void showDeathMessage(String text) {
        messageCounter2=0;
        message2 = text;
        messageOn2 = true;
    }
    
    public void drawHp(Graphics2D g2) {
        g2.setFont(g2.getFont().deriveFont(Font.BOLD,20));
        g2.setColor(Color.white);
        for(int i = 0; i < gp.obj.length; i++) {
            if(gp.obj[i]!=null){
                if(gp.obj[i].hp > 0){

                    int screenX = gp.obj[i].worldX - gp.player.worldX + gp.player.screenX;
                    int screenY = gp.obj[i].worldY - gp.player.worldY + gp.player.screenY;
            
                    if( gp.obj[i].worldX + gp.tileSize > gp.player.worldX - gp.player.screenX && 
                        gp.obj[i].worldX - gp.tileSize < gp.player.worldX + gp.player.screenX &&
                        gp.obj[i].worldY + gp.tileSize > gp.player.worldY - gp.player.screenY &&
                        gp.obj[i].worldY - gp.tileSize < gp.player.worldY + gp.player.screenY) {
                            g2.drawString("hp : " + gp.obj[i].hp, screenX - 15, screenY);
                        }
                }
            }
        }
    }

    public void draw(Graphics2D g2) {

        g2.setFont(arial_40);
        g2.setColor(Color.white);
        g2.drawString("Life : " + gp.player.Health, 210, 40);

        g2.setFont(g2.getFont().deriveFont(Font.BOLD,20));
        g2.drawString(gp.name, gp.screenWidth/2 - gp.tileSize/2, gp.screenHeight/2 - gp.tileSize/2);

        if(messageOn == true) {
            g2.drawString(message, 380, 40);
            messageCounter++;

            if(messageCounter>120) {
                messageCounter=0;
                messageOn = false;
            }
        }

        if(messageOn2 == true) {
            g2.setFont(arial_40);
            g2.setColor(Color.red);
            g2.drawString(message2, 210, 80);
            messageCounter2++;

            if(messageCounter2>120) {
                messageCounter2=0;
                messageOn2 = false;
            }
        }
    }

    public void drawTitleScreen(Graphics2D g2){

        BufferedImage startscreen = null;
        try {
            startscreen = ImageIO.read(getClass().getResourceAsStream("/res/ui/start.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        g2.drawImage(startscreen , 0, 0, gp.screenWidth, gp.screenHeight, null);
    }

    public void drawGameOver(Graphics2D g2){

        BufferedImage startscreen = null;
        try {
            startscreen = ImageIO.read(getClass().getResourceAsStream("/res/ui/death.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        g2.drawImage(startscreen , 0, 0, gp.screenWidth, gp.screenHeight, null);
    }

    public void drawFinishScreen(Graphics2D g2){

        BufferedImage startscreen = null;
        try {
            startscreen = ImageIO.read(getClass().getResourceAsStream("/res/ui/death.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        g2.drawImage(startscreen , 0, 0, gp.screenWidth, gp.screenHeight, null);
    }
}
