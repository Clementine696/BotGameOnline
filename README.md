# BotGameOnline

## Description
- You can play this game on multiplayer thorugh java socket setting in `Server/server.java`.
  ```
  server = new ServerSocket(6789);
  ```
- And in `Game/main/GamePanel.java` with the same address. 
  ```
  s = new Socket("localhost", 6789);
  ```
- In this project, this socket will be run on localhost for educational purposes. In which you can play multiplayer over lan in the same network.

## How to open the game
- Start by opening the server. Go to server floder and run `server.java` first.

- Then go to Game/main and run `AppMain.java`.

- If you want to play multiplayer you can run `AppMain.java` again and add more player.

## How to play

- วิธีการควบคุม การเดิน W A S D การยิง Spacebar

- ในส่วนของ Minimap นั้น สีน้ำเงินคือตัวผู้เล่นเอง สีแดงคือศัตรู และสีเขียวคือ Energy Tank

- ในส่วนของ Energy Tank การที่จะเพิ่มเลือดนั้น ต้องเดินชนใส่ Enerygy Tank เช่น ถ้า Enerygy Tank อยู่ขวามือเรา ต้องกดตัว D ค้างไว้

## ข้อควรระวัง 
- ตัว player เดินเร็วกว่ากระสุน หากเดินไปด้วยยิงไปด้วยกระสุนจะโดนตัวเอง

- การเพิ่มผู้เล่นใหม่มาหลังจากที่ผู้เล่นคนอื่นเล่นไปแล้ว จะทำให้เกิด Bug
<p float="left">
  <img src="https://user-images.githubusercontent.com/53567265/211375440-51c807e8-51a3-4852-8381-a3f9f547454e.png" width="600" />
  <img src="https://user-images.githubusercontent.com/53567265/211375595-5129cfb6-4dec-4c29-9d34-c8a48880c05d.png" width="600" />
  <img src="https://user-images.githubusercontent.com/53567265/211375646-8c2a8c57-24c4-493b-a83d-43618ed6e246.png" width="600" />
  <img src="https://user-images.githubusercontent.com/53567265/211375731-1b00eb3a-a525-4d11-a5da-523c4564554b.png" width="600" />
  <img src="https://user-images.githubusercontent.com/53567265/211375863-c29905c4-a647-4035-bc07-27241b3d5cad.png" width="600" />
  <img src="https://user-images.githubusercontent.com/53567265/211375923-21249238-d7f9-4b50-9850-9e91e3784023.png" width="600" />
  <img src="https://user-images.githubusercontent.com/53567265/211375923-21249238-d7f9-4b50-9850-9e91e3784023.png" width="600" />
  <img src="https://user-images.githubusercontent.com/53567265/211376028-37819941-4e95-4855-a3d1-c1daf7159d00.png" width="600" />
  <img src="https://user-images.githubusercontent.com/53567265/211376139-9b129ba1-c94d-4bf8-9e0d-3344a989c83e.png" width="600" />
  <img src="https://user-images.githubusercontent.com/53567265/211376191-fe0b9876-f916-46af-8c3a-b734d75cea80.png" width="600" />
</p>

<!-- 
## วิธีเปิดเกมส์
- เวลาเปิดเกมส์จะมี 2 Floder ให้เข้า Floder server แล้ว run ไฟล์ `server.java` ก่อน

- จากนั้นเวลาจะเล่นให้เข้าไปที่ไฟล์ Game/main จากนั้นให้ run ไฟล์ `AppMain.java`

- โดยหากต้องการจะเล่นหลายคน ให้ run `AppMain.java` ให้เสร็จก่อนที่จะเริ่มเล่น เช่น ถ้าจะเล่น 3 คนก็ให้รันไฟล์ 3 ครั้ง แล้วใส่ชื่อ 3 คนแล้วค่อยกด X เริ่มเล่นพร้อมกัน

## วิธีการเล่น

- วิธีการควบคุม การเดิน W A S D การยิง Spacebar

- ในส่วนของ Minimap นั้น สีน้ำเงินคือตัวผู้เล่นเอง สีแดงคือศัตรู และสีเขียวคือ Energy Tank

- ในส่วนของ Energy Tank การที่จะเพิ่มเลือดนั้น ต้องเดินชนใส่ Enerygy Tank เช่น ถ้า Enerygy Tank อยู่ขวามือเรา ต้องกดตัว D ค้างไว้

## ข้อควรระวัง 
- ตัว player เดินเร็วกว่ากระสุน หากเดินไปด้วยยิงไปด้วยกระสุนจะโดนตัวเอง

- การเพิ่มผู้เล่นใหม่มาหลังจากที่ผู้เล่นคนอื่นเล่นไปแล้ว จะทำให้เกิด Bug
-->

