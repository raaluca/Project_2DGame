package main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

// clasa care gestioneaza evenimentele tastaturii
public class KeyHandler implements KeyListener {
    GamePanel gp; // referinta catre panoul jocului
    public boolean upPressed, downPressed, leftPressed, rightPressed, enterPressed; // variabile pentru a urmari daca anumite taste sunt apasate
    boolean showDebugText = false; // variabila pentru a afisa sau ascunde textul de debug

    // constructorul clasei
    public KeyHandler(GamePanel gp)
    {
        this.gp=gp; // initializarea referintei catre panoul jocului
    }


    @Override
    public void keyTyped(KeyEvent e) {

    }

    // metoda apelata cand o tasta este apasata
    @Override
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode(); // obtine codul tastei apasate
        // starea titlului
        if(gp.gameState == gp.titleState)
        {
            // navigare in meniu
            if(code == KeyEvent.VK_W){
                gp.ui.commandNum--;
                if(gp.ui.commandNum<0)
                {
                    gp.ui.commandNum=2;
                }
            }
            if(code == KeyEvent.VK_S){
                gp.ui.commandNum++;
                if (gp.ui.commandNum >2)
                {
                    gp.ui.commandNum=0;
                }
            }
            // confirmare actiune
            if(code == KeyEvent.VK_ENTER)
            {
                if(gp.ui.commandNum==0)
                {
                    gp.gameState = gp.playState;
                    gp.playMusic(0);
                }
                if(gp.ui.commandNum ==1)
                {
                    gp.gameState=gp.loadGame;
                }
                if( gp.ui.commandNum == 2)
                {
                    System.exit(0);
                }
                if(code == KeyEvent.VK_R){
                    switch(gp.currentMap){
                        case 0: gp.tileM.loadMap("/maps/worldmap.txt",0); break;
                        case 1: gp.tileM.loadMap("/maps/worldmap02.txt",1); break;
                        case 2: gp.tileM.loadMap("/maps/worldmap03.txt",2); break;
                    }
                }
            }
        }
        // starea de joc
        else if(gp.gameState == gp.playState) {
            // miscare si alte actiuni ale jucatorului
            if (code == KeyEvent.VK_W) {
                upPressed = true;
            }
            if (code == KeyEvent.VK_S) {
                downPressed = true;
            }
            if (code == KeyEvent.VK_A) {
                leftPressed = true;
            }
            if (code == KeyEvent.VK_D) {
                rightPressed = true;
            }
            if (code == KeyEvent.VK_P) {
                gp.gameState = gp.pauseState;
            }
            if (code == KeyEvent.VK_ENTER) {
                enterPressed = true;
            }

            // afisarea sau ascunderea textului de debug
            if (code == KeyEvent.VK_T) {
                if (showDebugText == false)
                    showDebugText = true;
                else if (showDebugText == true)
                    showDebugText = false;
            }
            // salvarea progresului jocului
            if (code == KeyEvent.VK_L) {
                gp.save.insertData(gp.player.hasScore,gp.player.life,gp.player.nivel,gp.player.worldX/ gp.tileSize,gp.player.worldY/gp.tileSize);
                System.out.print("Score saved");
            }
        }
        // starea de pauza a jocului
        if(gp.gameState == gp.pauseState){
            if(code == KeyEvent.VK_P){
                gp.gameState=gp.playState;
            }
        }
        // starea de joc game over
        if(gp.gameState == gp.gameOverState){
            gameOverState(code);
        }
    }

    // metoda pentru gestionarea evenimentului de game over
    public void gameOverState(int code){
        if(code == KeyEvent.VK_W){
            gp.ui.commandNum--;
            if(gp.ui.commandNum<0){
                gp.ui.commandNum=1;
            }
        }
        if(code == KeyEvent.VK_S){
            gp.ui.commandNum++;
            if(gp.ui.commandNum>1){
                gp.ui.commandNum=0;
            }
        }
        if(code == KeyEvent.VK_ENTER){
            if(gp.ui.commandNum == 0){
                gp.gameState = gp.playState;
                gp.retry();
            }
            else if(gp.ui.commandNum == 1){
                gp.gameState = gp.titleState;
                gp.restart();
            }
        }
    }

    // metoda apelata cand o tasta este eliberata
    @Override
    public void keyReleased(KeyEvent e) {
        int code = e.getKeyCode(); // obtine codul tastei eliberate
        // se actualizeaza variabilele corespunzatoare tastelor care au fost eliberate
        if(code == KeyEvent.VK_W){
            upPressed=false;
        }
        if(code == KeyEvent.VK_S){
            downPressed=false;
        }
        if(code == KeyEvent.VK_A){
            leftPressed=false;
        }
        if(code == KeyEvent.VK_D){
            rightPressed=false;
        }
        if(code == KeyEvent.VK_ENTER){
            enterPressed=false;
        }

    }
}
