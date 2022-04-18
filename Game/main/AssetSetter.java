package main;
import object.OBJ_EnergyTank;
import object.OBJ_Bomb;

public class AssetSetter {
    
    GamePanel gp;
    int count = 0;
    public int EtStart;
    public int Object_Location[][];

    public AssetSetter(GamePanel gp){
        this.gp = gp;
        Object_Location = new int[gp.maxWorldCol][gp.maxWorldRow];
    }

    public void setObject(){
        createBomb();
        EtStart = count;
        createEt();
    }

    public void createBomb(){
        for(int col=0;col<gp.maxWorldCol;col++){
            for(int row=0;row<gp.maxWorldRow;row++){
                if(Object_Location[col][row] == 1){
                    gp.obj[count] = new OBJ_Bomb(gp);
                    gp.obj[count].worldX = col * gp.tileSize;
                    gp.obj[count].worldY = row * gp.tileSize;
                    count++;
                }
            }
        }
    }

    public void createEt(){
        for(int i=0;i<gp.max_et;i++){
            gp.obj[count] = new OBJ_EnergyTank(gp);
            gp.obj[count].hp = Integer.parseInt(gp.Et[i][0]);
            gp.obj[count].worldX = Integer.parseInt(gp.Et[i][1]) * gp.tileSize;
            gp.obj[count].worldY = Integer.parseInt(gp.Et[i][2]) * gp.tileSize;
            count++;
        }
    }
}
