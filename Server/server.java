import java.io.*;
import java.net.*;
import java.util.Random;

public class server {

	static StartUp sUp = new StartUp();
	static PlayerC pC = new PlayerC();
	static String mapp = sUp.RandomBomb();
	static int MaxPlayer = 5; // !!MAXPLAYER!!
	static int MaxEt = 3;
	static String lastTime = "0";

    public static void main(String[] args){

        ServerSocket server = null;
		
        try{

            server = new ServerSocket(6789);
			server.setReuseAddress(true);
            System.out.println("A socket is created waiting for connection...");

            while (true) {

				// socket object to receive incoming client
				// requests
				Socket client = server.accept();

				// Displaying that new client is connected
				// to server
				System.out.println("New client connected"+ client.getInetAddress().getHostAddress());

				// create a new thread object
				ClientHandler clientSock = new ClientHandler(client);

				// This thread will handle the client
				// separately
				new Thread(clientSock).start();
			}

        }
        catch(IOException e){
            System.out.println(e);;
        }
    }

    private static class ClientHandler implements Runnable {
		private final Socket clientSocket;

		// Constructor
		public ClientHandler(Socket socket)
		{
			this.clientSocket = socket;
			pC.addNewplayer();
			System.out.println("COUNT = " + pC.count);
		}

		public void run()
		{
			try {
					
				DataInputStream din = new DataInputStream(clientSocket.getInputStream());
                DataOutputStream dout = new DataOutputStream(clientSocket.getOutputStream());

                
                String str = (String) din.readUTF();

                str = str + "Success";

                dout.writeUTF(str);
				dout.writeUTF(mapp);

				while(true){

					String str1 = (String) din.readUTF();
					String[] seStrings = str1.replaceAll("\\[", "").replaceAll("]", "").split(",");
					pC.addPlayerLocation(seStrings[0], seStrings[1], seStrings[2], seStrings[3], seStrings[4]);
					// LOCATION OF PLAYER

					String str3 = (String) din.readUTF();
					String[] seStrings3 = str3.replaceAll("\\[", "").replaceAll("]", "").split(",");
					pC.addAction(seStrings3[0], seStrings3[1], seStrings3[2], seStrings3[3], seStrings3[4], seStrings3[5]);
					
					//           String name,   String Action, String X,     String Y,      String index,  String Time

					//dout.writeUTF(str3);
					//sUp.removebombindex(Integer.parseInt(seStrings3[2]));
					//TODO:
					if(seStrings3[1].equals("RBomb")){
						if(seStrings3[2].equals("0") && seStrings3[3].equals("0")){
							Random R = new Random();
							while(true){
								int x =  R.nextInt(sUp.maxWorldCol-1) + 1;
								int y =  R.nextInt(sUp.maxWorldRow-1) + 1;
								if(sUp.Object_Location[x][y] == 0){
									sUp.Object_Location[x][y] = 1;
									seStrings3[2] = String.valueOf(x); 
									seStrings3[3] = String.valueOf(y);
									pC.addAction(seStrings3[0], seStrings3[1], seStrings3[2], seStrings3[3], seStrings3[4], seStrings3[5]);
									break;
								}
							}
						}
					}
					// sUp.RandomNewbomb();
					// mapp = sUp.getMap();
					// dout.writeUTF(mapp);

					// dout.writeUTF("Yee,Yee,Yee");

					// System.out.println(mapp);


					for(int i=0;i<MaxPlayer;i++){
						String str2 = pC.PlayerLocation[i][0] + "," + pC.PlayerLocation[i][1] + "," + pC.PlayerLocation[i][2] + "," + pC.PlayerLocation[i][3] + "," + pC.PlayerLocation[i][4];
						dout.writeUTF(str2);
						//System.out.println(pC.PlayerLocation[i][0] + " " + pC.PlayerLocation[i][1] + pC.PlayerLocation[i][2] + pC.PlayerLocation[i][3] + "," + pC.PlayerLocation[i][4]);
					}

					for(int i=0;i<MaxPlayer;i++){
						String str4 = pC.lastdone[i][0] + "," + pC.lastdone[i][1] + "," + pC.lastdone[i][2] + "," + pC.lastdone[i][3] + "," + pC.lastdone[i][4] + "," + pC.lastdone[i][5];
						System.out.println(str4);
						if(pC.lastdone[i][1].equals("useEt") && pC.lastdone[i][5].equals(lastTime)==false ){
							for(int j=0;j<MaxEt;j++){
								if(pC.Et[j][1].equals(pC.lastdone[i][2])&&pC.Et[j][2].equals(pC.lastdone[i][3])){
									pC.Et[j][0] = String.valueOf(Integer.parseInt(pC.Et[j][0])-5);
									lastTime = pC.lastdone[i][5];
									break;
								}
							}
						}
						//TODO:
						dout.writeUTF(str4);
					}

					for(int i=0;i<MaxEt;i++){
						if(pC.Et[i][0].equals("0")){
							Random R = new Random();
							while(true){
								int x =  R.nextInt(sUp.maxWorldCol-1) + 1;
								int y =  R.nextInt(sUp.maxWorldRow-1) + 1;
								if(sUp.Object_Location[x][y] == 0){
									sUp.Object_Location[x][y] = 1;
									pC.Et[i][1] = String.valueOf(x); 
									pC.Et[i][2] = String.valueOf(y);
									break;
								}
							}
							int hp = (R.nextInt(10)+10) * 5;
							pC.Et[i][0] = String.valueOf(hp);
						}

						String str5 = pC.Et[i][0] + "," + pC.Et[i][1] + "," + pC.Et[i][2];
						dout.writeUTF(str5);
					}
					// System.out.println();
					
				}

                // clientSocket.close();
			}
			catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}