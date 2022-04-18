public class PlayerC {
    public int count;
    public int MaxPlayer;
    public String PlayerLocation[][];
    public String lastdone[][];
    public String Et[][];
    int MaxEt;

    public PlayerC(){
        count=0;
        MaxPlayer = 5; // !!MAXPLAYER!!
        MaxEt = 3;
        PlayerLocation = new String[MaxPlayer][5];
        lastdone = new String[MaxPlayer][6];
        Et = new String[MaxEt][3];
        
        for(int i=0;i<MaxPlayer;i++){
            PlayerLocation[i][0] = "Jeff12"; //name
            PlayerLocation[i][1] = "-10000"; //x
            PlayerLocation[i][2] = "-10000"; //y
            PlayerLocation[i][3] = "down"; //direction
            PlayerLocation[i][4] = "1000"; //health
        }

        for(int i=0;i<MaxPlayer;i++){
            lastdone[i][0] = "Jeff12"; //Name
            lastdone[i][1] = "Yee"; //Action
            lastdone[i][2] = "0"; //X
            lastdone[i][3] = "0"; //Y
            lastdone[i][4] = "0"; //Index
            lastdone[i][5] = "100"; //Time
        }

        for(int i=0;i<MaxEt;i++){ //hp //x //y
            Et[i][0] = "0";
            Et[i][1] = "0";
            Et[i][2] = "0";
        }
    }

    public void addNewplayer(){
        count++;
    }

    public int getPlayerNum(){
        return count;
    }

    public void addPlayerLocation(String name,String X,String Y,String Direction,String health){
        for(int i=0;i<count;i++){
            if(PlayerLocation[i][0]== "Jeff12" || PlayerLocation[i][0].equals(name)){
                PlayerLocation[i][0] = name;
                PlayerLocation[i][1] = X;
                PlayerLocation[i][2] = Y;
                PlayerLocation[i][3] = Direction;
                PlayerLocation[i][4] = health;
                break;
            }
        }
    }

    public void addAction(String name, String Action, String X,String Y, String index, String Time){
        for(int i=0;i<count;i++){
            if(lastdone[i][0]== "Jeff12" || lastdone[i][0].equals(name)){
                lastdone[i][0] = name;
                lastdone[i][1] = Action;
                lastdone[i][2] = X;
                lastdone[i][3] = Y;
                lastdone[i][4] = index;
                lastdone[i][5] = Time;
                break;
            }
        }
    }
}
