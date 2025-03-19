package main;

import entity.Entity;
import object.Score;
import object.obj_coin;
import object.obj_heart;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Scanner;

// clasa pentru interfața utilizatorului
public class ui {

    GamePanel gp;
    Graphics2D g2;
    Font arial_40, arial_80B, arial_60;
    BufferedImage coinImage;
    public boolean messageOn = false;
    public String message ="";
    int messageCounter = 0;
    public boolean finished = false;
    public int commandNum=0;
    BufferedImage heart0, heart1, heart2;
    public boolean gameIsOver=false;

    // constructor pentru obiectul ui
    public ui(GamePanel gp) {
        this.gp=gp;
        arial_40 = new Font("Times New Roman", Font.PLAIN,18);
        arial_60 = new Font("Times New Roman", Font.PLAIN,60);
        arial_80B = new Font("Times New Roman", Font.BOLD,80);
        Score coin = new Score(gp);
        coinImage = coin.image;

        //creare hud scor
        Entity heart = new obj_heart(gp);
        heart2=heart.image;
        heart1=heart.image2;
        heart0=heart.image3;
    }

    // metoda pentru afișarea unui mesaj
    public void showMessage(String text)
    {
        message = text;
        messageOn = true;
    }

    // metoda pentru desenarea ecranului de titlu
    public void drawTitleSceen(){
        BufferedImage image;
        try {
            image = ImageIO.read(getClass().getResourceAsStream("/texture/1fe06422-8d90-4d2d-9ba1-e33382e329f3.jpg"));
            g2.drawImage(image, 0, 0, gp.screenWidth, gp.screenHeight, null);
        } catch(IOException e) {
            e.printStackTrace();
        }

        int y= gp.tileSize*3;

        //MENU
        g2.setFont(g2.getFont().deriveFont(Font.BOLD,30F));
        String text = "New Game";
        int x= getXforCenterText(text);
        y += gp.tileSize*3.5;
        g2.drawString(text,x,y);
        if (commandNum == 0)
        {
            g2.drawString(">",x-gp.tileSize,y);
        }

        text = "Load Game";
        x= getXforCenterText(text);
        y += gp.tileSize;
        g2.drawString(text,x,y);
        if (commandNum == 1)
        {
            g2.drawString(">",x-gp.tileSize,y);
        }

        text = "Quit";
        x= getXforCenterText(text);
        y += gp.tileSize;
        g2.drawString(text,x,y);
        if (commandNum == 2)
        {
            g2.drawString(">",x-gp.tileSize,y);
        }
    }

    // metoda pentru desenarea ecranului de pauză
    public void drawPauseScreen(){
        String text ="PAUSE";
        int x= getXforCenterText(text);
        int y= gp.screenHeight/2;
        g2.drawString(text,x,y);
    }

    // metoda pentru a obține poziția x pentru textul centrat
    public int getXforCenterText(String text)
    {
        int length = (int)g2.getFontMetrics().getStringBounds(text,g2).getWidth();
        int x = gp.screenWidth/2-length/2;
        return x;

    }

    // metoda pentru desenarea vieților jucătorului
    public void drawPlayerLife() {

        int x = gp.getWidth() - gp.tileSize*2;
        int y = gp.tileSize-6;
        int i=0;
        while(i < gp.player.maxLife/2){
            g2.drawImage(heart0,x,y,null);
            i++;
            x -= gp.tileSize;
        }
        // RESET
        x = gp.getWidth() - gp.tileSize*2;
        y = gp.tileSize-6;
        i=0;

        // DRAW CURRENT LIFE
        while(i<gp.player.life){
            g2.drawImage(heart1,x,y,null);
            i++;
            if(i < gp.player.life){
                g2.drawImage(heart2,x,y,null);
            }
            i++;
            x -= gp.tileSize;
        }

    }

    // metoda pentru desenarea ecranului de sfârșit de joc
    public void drawGameOverScreen(){
        g2.setColor(new Color(0,0,0,100));
        g2.fillRect(0,0,gp.screenWidth,gp.screenHeight);

        int x;int y;
        String text ;
        g2.setFont(g2.getFont().deriveFont(Font.BOLD,100f));

        text= "Game Over :(";
        //umbra
        g2.setColor(Color.black);
        x = getXforCenterText(text);
        y = gp.tileSize*4;
        g2.drawString(text,x,y);
        //principal
        g2.setColor(Color.yellow);
        g2.drawString(text,x-4,y-4);

        //retry
        g2.setFont(g2.getFont().deriveFont(50f));
        text = "Retry";
        x = getXforCenterText(text);
        y +=gp.tileSize*4;
        g2.drawString(text,x,y);
        if(commandNum == 0){
            g2.drawString(">",x-40,y);
        }

        //înapoi la ecranul titlului
        text = "Quit";
        x = getXforCenterText(text);
        y +=60;
        g2.drawString(text,x,y);
        if(commandNum == 1){
            g2.drawString(">",x-40,y);
        }
    }

    // metoda pentru desenarea ecranului de sfârșit de joc finalizat
    public void drawFinishedGame() {
        g2.setColor(new Color(0,0,0,100));
        g2.fillRect(0,0,gp.screenWidth,gp.screenHeight);

        g2.setFont(arial_60);
        g2.setColor(Color.yellow);

        String text;
        int textLength, x, y;

        text = "YOU WIN!";
        textLength = (int) g2.getFontMetrics().getStringBounds(text, g2).getWidth();
        x = gp.screenWidth / 2 - textLength / 2;
        y = gp.screenHeight / 2 - (gp.tileSize * 3);
        g2.drawString(text, x, y);

        g2.setFont(arial_80B);
        g2.setColor(Color.yellow);
        text = "Congratulations!";
        textLength = (int) g2.getFontMetrics().getStringBounds(text, g2).getWidth();
        x = gp.screenWidth / 2 - textLength / 2;
        y = gp.screenHeight / 2 + (gp.tileSize * 2);
        g2.drawString(text, x, y);

        g2.setFont(arial_60);
        g2.setColor(Color.yellow);
        text = "Your score: " + gp.player.hasScore;
        textLength = (int) g2.getFontMetrics().getStringBounds(text, g2).getWidth();
        x = gp.screenWidth / 2 - textLength / 2;
        y = gp.screenHeight / 2 + (gp.tileSize * 4);
        g2.drawString(text, x, y);

        if (!gameIsOver) {
            Scanner scanner = new Scanner(System.in);
            System.out.print("Introdu numele: ");
            String nume = scanner.nextLine();
            gp.scor.insertData(nume, gp.player.hasScore);
            System.out.println("Scor salvat în baza de date");

            // Afișează conținutul bazei de date
            gp.showDatabaseContents();

            gameIsOver = true;
        }
        gp.gameThread = null;
    }

    // metoda pentru desenarea ecranului de încărcare a jocului
    public void drawLoadGame(){
        gp.getDateDB();
        //gp.player.retry0();
        gp.gameState = gp.playState;
        gp.player.nivel=gp.niv;
        if(gp.player.nivel == 2) {
            gp.currentMap++;
        }
        gp.eHandler.checkEvent();
        //System.out.print("nivel= "+gp.player.nivel); //!!!!!!!!!
        gp.player.worldX =gp.ix;
        gp.player.worldY=gp.iy;
        gp.player.worldX =10* gp.tileSize;
        gp.player.worldY =10* gp.tileSize;
        gp.player.hasScore =gp.c;
        gp.player.life=gp.h;
        gp.tileM.update();
    }

    // metoda pentru desenarea componentelor
    public void draw(Graphics2D g2)
    {
        this.g2=g2;
        g2.setFont(arial_60);
        g2.setColor(Color.yellow);
        //starea de titlu
        if(gp.gameState == gp.titleState)
        {
            drawTitleSceen();
        }
        if(gp.gameState == gp.playState)
        {
            drawPlayerLife();
        }
        if(gp.gameState == gp.pauseState)
        {
            drawPlayerLife();
            drawPauseScreen();
        }
        //starea de sfârșit de joc
        if(gp.gameState == gp.gameOverState){
            drawGameOverScreen();
        }
        //starea de încărcare a jocului
        if(gp.gameState == gp.loadGame){
            drawLoadGame();
        }
        if(gp.gameState == gp.finishGame){
            drawFinishedGame();
            if(gp.gameState == gp.finishGame && gameIsOver == false){
                gp.endGame();
                gameIsOver=true;
            }
        }

        if (finished == true)
        {
            g2.setColor(new Color(0,0,0,100));
            g2.fillRect(0,0,gp.screenWidth,gp.screenHeight);

            g2.setFont(arial_60);
            g2.setColor(Color.yellow);

            String text;
            int textLength;
            int x,y;

            text= "You Win!";
            textLength = (int)g2.getFontMetrics().getStringBounds(text,g2).getWidth();
            x = gp.screenWidth/2 - textLength/2;
            y = gp.screenHeight/2 - gp.tileSize*3;
            g2.drawString(text,x,y);

            g2.setFont(arial_80B);
            g2.setColor(Color.WHITE);
            text= "Congratulations!";
            textLength = (int)g2.getFontMetrics().getStringBounds(text,g2).getWidth();
            x = gp.screenWidth/2 - textLength/2;
            y = gp.screenHeight/2 + gp.tileSize*3;
            g2.drawString(text,x,y);

            g2.setFont(arial_60);
            g2.setColor(Color.yellow);
            text= "Your score is "+ gp.player.hasScore + "!";
            textLength = (int)g2.getFontMetrics().getStringBounds(text,g2).getWidth();
            x = gp.screenWidth/2 - textLength/2;
            y = gp.screenHeight/2 + gp.tileSize*1/2;
            g2.drawString(text,x,y);

            gp.gameThread = null;

        }
        else{
            g2.setFont(arial_40);
            g2.setColor(Color.yellow);
            g2.drawImage(coinImage,gp.tileSize/2,gp.tileSize/2,gp.tileSize,gp.tileSize,null);
            g2.drawString("x "+gp.player.hasCoin,62,52);

            if ( messageOn == true)
            {
                g2.setFont(g2.getFont().deriveFont(30F));
                g2.drawString(message,gp.tileSize/2,gp.tileSize*5);

                messageCounter++;
                if( messageCounter >  90)
                {
                    messageCounter=0;
                    messageOn= false;
                }
            }
        }
    }
}
