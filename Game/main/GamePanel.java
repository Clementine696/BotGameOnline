package main;
import java.awt.Color;
import java.awt.Dimension;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

import javax.swing.JOptionPane;
import javax.swing.JPanel;
import entity.Player;
import object.SuperObject;
import entity.Bullet;
import entity.Enemy;
import tile.TileManager;
import java.awt.*;

public class GamePanel extends JPanel implements Runnable{
    
    // SCREEN SETTINGS
    String name = JOptionPane.showInputDialog("Please type your name");
    Socket s;
    DataInputStream din;
    DataOutputStream dout;

    String Texttext;
    int index_remove_bomb;
    public int serX;
    public int serY;
    long LastActionTime;

    public final int MaxPlayer = 5; // !!MAXPLAYER!!

    final int originalTileSize = 16; //16x16tile
    public int scale = 2;

    public final int bomb_rate = 4;
    public int et_count = 0;
    public final int max_et = 3;

    public int tileSize = originalTileSize * scale; //48x48tile
    public int maxScreenCol = 25;
    public int maxScreenRow = 20;
    public int screenWidth = tileSize * maxScreenCol; //768pixel
    public int screenHeight = tileSize * maxScreenRow; //576pixel

    // WORLD SETTINGS
    public final int maxWorldCol = 100;
    public final int maxWorldRow = 80;
    int lengtharray = maxWorldCol*maxWorldRow;

    // FPS
    int FPS = 60;

    public int gameState = 0;
    public final int titleState = 0;
    public final int playState = 1;
    public final int gameOverState = 2;
    public final int finishState = 3;

    long firingTimer;
    int firingDelay = 600;
    public long healingTimer;
    public int healingDelay = 600;

    //SYSTEM
    TileManager tileM = new TileManager(this);
    KeyHandler keyH = new KeyHandler(this);
    public CollisionChecker cChecker = new CollisionChecker(this);
    public AssetSetter aSetter = new AssetSetter(this);
    public UI ui = new UI(this);
    Thread gameThread;

    //ENTITY AND OBJECT
    public Player player = new Player(this, keyH);
    public Enemy enemy[] = new Enemy[MaxPlayer];
    public SuperObject obj[] = new SuperObject[500];
    public Bullet bullets[] = new Bullet[10];

    public Minimap minimap = new Minimap(this);

    public String EnemyLocation[][];
    public String lastdone[][];
    public String Et[][];

    public GamePanel(){
        this.setPreferredSize(new Dimension(screenWidth,screenHeight));
        this.setBackground(Color.black);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyH);
        this.setFocusable(true);   

        Texttext = "Yee";
        index_remove_bomb = 999;
        LastActionTime = 0;
        serX = 0;
        serY = 0;

        EnemyLocation = new String[MaxPlayer][5];
        lastdone = new String[MaxPlayer][6];
        Et = new String[max_et][3];
        
        for(int i=0;i<MaxPlayer;i++){
            enemy[i] = new Enemy(this);
            EnemyLocation[i][0] = "Jeff12"; //name
            EnemyLocation[i][1] = "0"; //x
            EnemyLocation[i][2] = "0"; //y
            EnemyLocation[i][3] = "down"; //direction
            EnemyLocation[i][4] = "1000"; //hp
        }

        for(int i=0;i<MaxPlayer;i++){
            lastdone[i][0] = "Jeff12"; //Name
            lastdone[i][1] = "Yee"; //Action
            lastdone[i][2] = "0"; //X
            lastdone[i][3] = "0"; //Y
            lastdone[i][4] = "0"; //Index
            lastdone[i][5] = "100"; //Time
        }

        for(int i=0;i<max_et;i++){ 
            Et[i][0] = "0"; //hp
            Et[i][1] = "0"; //x
            Et[i][2] = "0"; //y
        }
        
        try{
            s = new Socket("localhost", 6789);
            System.out.println("A connection is created and I'm sending connected.");
            
            din = new DataInputStream(s.getInputStream());
            dout = new DataOutputStream(s.getOutputStream());

            dout.writeUTF("Connected");
            dout.flush();

            String str = (String) din.readUTF();
            System.out.println("The server says: " + str);

            String mapp = (String) din.readUTF();
            System.out.println("The server says: " + mapp);
            String[] separatedStrings = mapp.replaceAll("\\[", "").replaceAll("]", "").split(", ");
            System.out.println("Mapp.length = " + mapp.length());
            int[] intArray = new int[8000];
            int c=0;
            for (int i = 0; i < 8000; i++) {
                try {
                    intArray[c++] = Integer.parseInt(separatedStrings[i]);
                } 
                catch (Exception e) {
                    System.out.println("Unable to parse string to int: " + e.getMessage());
                }
            }

            c=0;
            for(int i=0;i<100;i++){
                for(int j=0;j<80;j++){
                    aSetter.Object_Location[i][j] = intArray[c++];
                }
            }           

        }
        catch(IOException e){
            System.out.println(e);;
        }
    }

    public void SetupGame() {

        aSetter.setObject();
    }

    public void startGameThread(){
        gameThread = new Thread(this);
        gameThread.start();
    }

    @Override
    public void run(){
        double drawInterval = 1000000000/FPS;
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;

        while(gameThread != null){
            currentTime = System.nanoTime();

            delta += (currentTime - lastTime) / drawInterval;
            lastTime = currentTime;
            if(delta >= 1){
                update();
                repaint();
                delta--;

                int x = player.worldX;
                int y = player.worldY;
                int h = player.Health;

                try {
                    dout.writeUTF(name + "," + String.valueOf(x) + "," + String.valueOf(y) + "," + player.direction + "," + String.valueOf(h));
                    dout.flush();

                    dout.writeUTF(name + "," + Texttext + "," + String.valueOf(serX) + "," + String.valueOf(serY) + "," + String.valueOf(index_remove_bomb) + "," + String.valueOf(LastActionTime));
                    dout.flush();

                    for(int i=0;i<MaxPlayer;i++){
                        String str1 = (String) din.readUTF();
                        String[] seStrings = str1.replaceAll("\\[", "").replaceAll("]", "").split(",");
                        addPlayerLocation(seStrings[0], seStrings[1], seStrings[2], seStrings[3], seStrings[4]);
                    }
                    
                    for(int i=0;i<MaxPlayer;i++){
                        String str4 = (String) din.readUTF();
                        String[] seStrings3 = str4.replaceAll("\\[", "").replaceAll("]", "").split(",");
                        addPlayerLastDone(seStrings3[0], seStrings3[1], seStrings3[2], seStrings3[3], seStrings3[4], seStrings3[5]);
                    }

                    for(int i=0;i<max_et;i++){
                        String str5 = (String) din.readUTF();
                        String[] seStrings4 = str5.replaceAll("\\[", "").replaceAll("]", "").split(",");
                        obj[aSetter.EtStart+i].hp = Integer.parseInt(seStrings4[0]);
                        obj[aSetter.EtStart+i].worldX = Integer.parseInt(seStrings4[1]) * tileSize;
                        obj[aSetter.EtStart+i].worldY = Integer.parseInt(seStrings4[2]) * tileSize;
                    }

                } catch (IOException e){
                    e.printStackTrace();
                }
            }
        }
    }

    public void createBullet(int X, int Y, String Direction){
        long elapsed = (System.nanoTime() - firingTimer) / 1000000;
        if(elapsed > firingDelay){
            for(int i=0; i<bullets.length; i++)
            {
                if(bullets[i]==null){
                    bullets[i] = new Bullet(this,X,Y,Direction);
                    firingTimer = System.nanoTime();
                    Texttext = "Cbull";
                    serX = X;
                    serY = Y;
                    setActionTime();
                    switch(Direction){
                        case "up" : index_remove_bomb = 1;
                            break;
                        case "down" : index_remove_bomb = 2;
                            break;
                        case "left" : index_remove_bomb = 3;
                            break;
                        case "right" : index_remove_bomb = 4;
                            break;
                    }
                    break;
                }
            }
        }
    }

    public void linkServerBullet(int X, int Y, String Direction){
        for(int i=0; i<bullets.length; i++)
            {
                if(bullets[i]==null){
                    bullets[i] = new Bullet(this,X,Y,Direction);
                    break;
                }
            }
    }

    public void update(){
        if(gameState==playState){
            player.update();
            for(int i=0;i<bullets.length;i++)
            {
                if(bullets[i]!=null){
                    if(bullets[i].update()==false){
                        setActionTime();
                        Texttext = "Rbull";
                        index_remove_bomb = i;
                        bullets[i] = null;
                    }
                }
            }
        }
    }


    public void paintComponent(Graphics g){
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        if(gameState==titleState){
            ui.drawTitleScreen(g2);
        }
        else if(gameState==playState){
            // TILE
            tileM.draw(g2);

            //OBJECT
            for(int i = 0; i < obj.length; i++){
                if(obj[i] != null){
                    obj[i].draw(g2, this);
                }
            }

            // PLAYER
            player.draw(g2);

            // ENEMY
            for(int i = 0; i < MaxPlayer; i++){
                if(enemy[i] != null && EnemyLocation[i][0]!= "Jeff12" && Integer.parseInt(EnemyLocation[i][4]) > 0){
                    enemy[i].draw(g2);
                }
            }

            for(int i=0; i<bullets.length; i++){
                if(bullets[i] != null){
                    bullets[i].draw(g2);
                }
            }
            
            minimap.draw(g2);
            ui.draw(g2);
            ui.drawHp(g2);
            g2.dispose();
        }

        else if(gameState==gameOverState){
            ui.drawGameOver(g2);
            setActionTime();
            Texttext = "Death";
            gameState = finishState;
        }
        else if(gameState==finishState){
            ui.drawFinishScreen(g2);
        }
    }

    public void addPlayerLocation(String name, String X, String Y, String Direction, String health){
        for(int i=0;i<MaxPlayer;i++){
            if(EnemyLocation[i][0].equals("Jeff12") || EnemyLocation[i][0].equals(name)){
                EnemyLocation[i][0] = name;
                EnemyLocation[i][1] = X;
                EnemyLocation[i][2] = Y;
                EnemyLocation[i][3] = Direction;
                EnemyLocation[i][4] = health;
                if(name.equals(this.name)){
                    enemy[i].worldX = -10000;
                    enemy[i].worldY = -10000;
                }
                else{
                    enemy[i].name = name;
                    enemy[i].worldX = Integer.parseInt(X);
                    enemy[i].worldY = Integer.parseInt(Y);
                    enemy[i].Health = Integer.parseInt(health);
                    enemy[i].direction = Direction;
                }
                break;
            }
        }
    }

    public void addPlayerLastDone(String Name, String Action, String X,String Y, String index, String Time){
        for(int i=0;i<MaxPlayer;i++){
            if(lastdone[i][0].equals("Jeff12")){
                lastdone[i][0] = Name;
                lastdone[i][1] = Action;
                lastdone[i][2] = X;
                lastdone[i][3] = Y;
                lastdone[i][4] = index;
                lastdone[i][5] = Time;
                if(Action.equals("RBomb")){
                    removeBomb(Integer.parseInt(index), Integer.parseInt(X), Integer.parseInt(Y));
                    System.out.println(lastdone[i][0] + lastdone[i][1] + lastdone[i][4] + lastdone[i][5]);
                }
                else if(Action.equals("Cbull")){
                    if(Name.equals(name)){
                        System.out.println("It's me");
                        break;
                    }
                    String direction = "up";
                    switch(index){
                        case "1" : direction = "up";
                            break;
                        case "2" : direction = "down";
                            break;
                        case "3" : direction = "left";
                            break;
                        case "4" : direction = "right";
                            break;
                    }
                    linkServerBullet(Integer.parseInt(X), Integer.parseInt(Y), direction);
                }
                else if(Action.equals("Rbull")){
                    if(bullets[Integer.parseInt(index)] != null){}
                        bullets[Integer.parseInt(index)] = null;
                    removeBomb(Integer.parseInt(X), Integer.parseInt(X), Integer.parseInt(Y));
                }
                break;
            }
            if(lastdone[i][0].equals(Name)){
                if(lastdone[i][5].equals(Time)){
                    break;
                }
                else{
                    lastdone[i][1] = Action;
                    lastdone[i][2] = X;
                    lastdone[i][3] = Y;
                    lastdone[i][4] = index;
                    lastdone[i][5] = Time;

                    if(Action.equals("RBomb")){
                        removeBomb(Integer.parseInt(index), Integer.parseInt(X), Integer.parseInt(Y));
                        //System.out.println(lastdone[i][0] + lastdone[i][1] + lastdone[i][4] + lastdone[i][5]);
                    }
                    else if(Action.equals("Cbull")){
                        if(Name.equals(name)){
                            break;
                        }
                        String direction = "up";
                        switch(index){
                            case "1" : direction = "up";
                                break;
                            case "2" : direction = "down";
                                break;
                            case "3" : direction = "left";
                                break;
                            case "4" : direction = "right";
                                break;
                        }
                        linkServerBullet(Integer.parseInt(X), Integer.parseInt(Y), direction);
                    }
                    else if(Action.equals("Rbull")){
                        if(bullets[Integer.parseInt(index)] != null)
                            bullets[Integer.parseInt(index)] = null;
                        removeBomb(Integer.parseInt(X), Integer.parseInt(X), Integer.parseInt(Y));
                    }
                    else if(Action.equals("Death")){
                        ui.showDeathMessage(lastdone[i][0] + " killed");
                    }
                }
                break;
            }
        }
    }

    public void RemoveBombIndex(int xi){
        Texttext ="RBomb";
        index_remove_bomb = xi;
    }
    public void removeBomb(int xi, int X, int Y){
        if(X!=0 && Y!= 0 && xi < 500)
            if(obj[xi] != null){
                int x = obj[xi].worldX / tileSize;
                int y = obj[xi].worldY / tileSize;
                aSetter.Object_Location[x][y] = 0;
                aSetter.Object_Location[X][Y] = 1;
                obj[xi].worldX = X * tileSize;
                obj[xi].worldY = Y * tileSize;
                System.out.println("New bomb at " + X + " " + Y);
            }
    }

    public void collectEt(int X, int Y){
        Texttext = "useEt";
        serX = X;
        serY = Y;
        setActionTime();
    }

    public void setActionTime(){
        LastActionTime = System.nanoTime();
    }
}
