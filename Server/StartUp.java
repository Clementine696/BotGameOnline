import java.util.Arrays;
import java.util.Random;

public class StartUp {

    final int maxWorldCol = 100;
	final int maxWorldRow = 80;
	final int bomb_rate = 4;
	int Object_Location[][] = new int[maxWorldCol][maxWorldRow];
    String map;

    public StartUp(){
        System.out.println("Start Running");
    }

    public String RandomBomb(){
        Random R = new Random();
			for(int col=1;col<maxWorldCol-1;col++){
				for(int row=1;row<maxWorldRow-1;row++){
					int x = R.nextInt(100);
					if(x<bomb_rate){
						Object_Location[col][row] = 1;
					}
				}
			}

            String[] strArray = new String[8000];
            int i=0;
            for(int col=0;col<maxWorldCol;col++){
				for(int row=0;row<maxWorldRow;row++){
                    strArray[i++] = String.valueOf(Object_Location[col][row]);
                }
            }
			String map = Arrays.toString(strArray).replaceAll("\\[|\\]||\\s", "");
            return map;
    }

	public void RandomNewbomb(){
        Random R = new Random();
        while(true){
            int x =  R.nextInt(maxWorldCol-2) + 1;
            int y =  R.nextInt(maxWorldRow-2) + 1;
            if(Object_Location[x][y] == 0){
                Object_Location[x][y] = 1;
                break;
            }
        }
    }

	public void removebombindex(int i){
		int count = -1;
		for(int col=1;col<maxWorldCol-1;col++){
			for(int row=1;row<maxWorldRow-1;row++){
				if(Object_Location[col][row] == 1)
					count++;
				if(count == i){
					Object_Location[col][row] = 0;
					break;
				}
					
			}
		}
	}

	public String getMap(){
		String[] strArray = new String[8000];
        int i=0;
        for(int col=0;col<maxWorldCol;col++){
			for(int row=0;row<maxWorldRow;row++){
                strArray[i++] = String.valueOf(Object_Location[col][row]);
            }
        }
		String map = Arrays.toString(strArray).replaceAll("\\[|\\]||\\s", "");
        return map;
	}
}
