package entity;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;
import main.GamePanel;
import main.KeyHandler;
import main.UtilityTool;

public class Player extends Entity{
    
    GamePanel gp;
    KeyHandler keyH;

    public final int screenX;
    public final int screenY;

    public int Health;

    public Player(GamePanel gp, KeyHandler keyH){
        super(gp);
        
        this.gp = gp;
        this.keyH = keyH;

        screenX = gp.screenWidth/2 - gp.tileSize/2;
        screenY = gp.screenHeight/2 - gp.tileSize/2;

        solidArea = new Rectangle(20, 20, 12, 5);
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;

        setDefaultValues(); 
        getPlayerImage();
    }
    public void setDefaultValues(){
        Random R = new Random();
        int X = R.nextInt(gp.maxScreenCol-1)+1;
        int Y = R.nextInt(gp.maxScreenRow-1)+1;
        worldX = gp.tileSize * X;
        worldY = gp.tileSize * Y;
        speed = 4;
        Health = 100;
        direction = "down";
    }

    public void getPlayerImage(){
        up = setup("robot_w");
        down = setup("robot_s");
        left = setup("robot_a");
        right = setup("robot_d");
    }

    public BufferedImage setup(String imageName){
        UtilityTool uTool = new UtilityTool();
        BufferedImage image = null;
        try{
            image = ImageIO.read(getClass().getResourceAsStream("/res/player/" + imageName +".png"));
            image = uTool.scaleImage(image, gp.tileSize, gp.tileSize);
        }catch(IOException e){
            e.printStackTrace();
        }
        return image;
    }

    public void update(){
        if(keyH.upPressed == true || keyH.downPressed == true || keyH.leftPressed == true || keyH.rightPressed == true)
        {
            if(keyH.upPressed == true){
                direction = "up";
            }
            else if(keyH.downPressed == true){
                direction = "down";
            }
            else if(keyH.leftPressed == true){
                direction = "left";
            }
            else if(keyH.rightPressed == true){
                direction = "right";
            }
    
            // CHECK TILE COLLISION
            collisionOn = false;
            gp.cChecker.checkTile(this);

            // CHECK OBJECT COLLISION
            int objIndex = gp.cChecker.checkObject(this, true);
            pickUpObject(objIndex);

            // IF COLLISIO IS FALSE, PLAYER CAN MOVE
            if(collisionOn == false){
                switch(direction){
                    case "up" : worldY -= speed;
                        break;
                    case "down" : worldY += speed;
                        break;
                    case "left" : worldX -= speed;
                        break;
                    case "right" : worldX += speed;
                        break;
                }
            }
        }
        if(keyH.spacePressed == true){
            gp.createBullet(worldX, worldY, direction);
        }
    }

    public void hitBullet(){
        Health-=25;
        gp.ui.showMessage("-50");
        gp.serX = 0;
        gp.serY = 0;
        if(Health<=0)
        {
            gp.gameState = gp.gameOverState;
        }
    }

    public void pickUpObject(int i) {
        if(i != 999) {

            String objectName = gp.obj[i].name;

            switch(objectName) {
                case "Bomb":
                    Health-=5;
                    if(Health<=0)
                    {
                        gp.gameState = gp.gameOverState;
                        break;
                    }
                    gp.setActionTime();
                    gp.RemoveBombIndex(i);
                    gp.serX = 0;
                    gp.serY = 0;
                    gp.ui.showMessage("-5");
                break;

                case "Et":
                    if(Health<100){
                        long elapsed = (System.nanoTime() - gp.healingTimer) / 1000000;
                        if(elapsed > gp.healingDelay){
                            gp.healingTimer = System.nanoTime();
                            Health+=5;
                            gp.ui.showMessage("+5");
                            int X = gp.obj[i].worldX / gp.tileSize;
                            int Y = gp.obj[i].worldY / gp.tileSize;
                            gp.collectEt(X, Y);
                        }
                    }
                break;
            }
        }
    }

    public void draw(Graphics2D g2){

        BufferedImage image = null;

        switch(direction){
            case "up" : image = up;
                break;
            case "down" : image = down;
                break;
            case "left" : image = left;
                break;
            case "right" : image = right;
                break;
        }
        g2.drawImage(image, screenX, screenY, null);
    }
}
