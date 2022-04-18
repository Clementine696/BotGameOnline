package entity;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
import main.GamePanel;
import main.UtilityTool;

public class Enemy extends Entity{
    
    GamePanel gp;

    public int Health = 1000;
    public String name;

    public Enemy(GamePanel gp){
        super(gp);
        this.gp = gp;
        name = "Jeff";
        solidArea = new Rectangle(20, 20, 12, 5);
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;

        //solidArea = new Rectangle(gp.tileSize/18*gp.scale, gp.tileSize/9*gp.scale, gp.tileSize*gp.scale*2/9, (gp.tileSize/3*2)/3*gp.scale); //8 16 32 32 
        setDefaultValues(); 
        getPlayerImage();
    }
    public void setDefaultValues(){
        worldX = gp.tileSize * -100;
        worldY = gp.tileSize * -100;
        speed = 4;
        direction = "down";
    }

    public void getPlayerImage(){
        up = setup("enemy_w");
        down = setup("enemy_s");
        left = setup("enemy_a");
        right = setup("enemy_d");
    }

    public BufferedImage setup(String imageName){
        UtilityTool uTool = new UtilityTool();
        BufferedImage image = null;
        try{
            image = ImageIO.read(getClass().getResourceAsStream("/res/enemy/" + imageName +".png"));
            image = uTool.scaleImage(image, gp.tileSize, gp.tileSize);
        }catch(IOException e){
            e.printStackTrace();
        }
        return image;
    }

    public void update(){
        
    }

    public void hitBullet(){
        
    }

    public void pickUpObject(int i) {
        
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
        int screenX = worldX - gp.player.worldX + gp.player.screenX;
        int screenY = worldY - gp.player.worldY + gp.player.screenY;

        g2.setFont(g2.getFont().deriveFont(Font.BOLD,20));
        g2.setColor(Color.white);

        if(worldX + gp.tileSize > gp.player.worldX - gp.player.screenX && 
           worldX - gp.tileSize < gp.player.worldX + gp.player.screenX &&
           worldY + gp.tileSize > gp.player.worldY - gp.player.screenY &&
           worldY - gp.tileSize < gp.player.worldY + gp.player.screenY) {
                g2.drawImage(image, screenX, screenY, gp.tileSize, gp.tileSize, null);
                g2.drawString(name + " Hp:" + Health, screenX - 20, screenY);
            }
    }
}
