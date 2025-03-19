package main;

import monster.Final_Boss;
import monster.Ghost;
import object.*;

public class AssetSetter {
    GamePanel gp;
    public AssetSetter(GamePanel gp){this.gp=gp;
    }
    // plaseaza obiectele pe harta in functie de nivel
    // nivelul este redat de mapNum
    public void setObject(){

        int mapNum=0;
        gp.obj[mapNum][0]=new obj_coin(gp);
        gp.obj[mapNum][0].worldX = 12 * gp.tileSize;
        gp.obj[mapNum][0].worldY = 10 * gp.tileSize;

        gp.obj[mapNum][1] = new obj_coin(gp);
        gp.obj[mapNum][1].worldX = 11 * gp.tileSize;
        gp.obj[mapNum][1].worldY = 19 * gp.tileSize;

        gp.obj[mapNum][2] = new obj_coin(gp);
        gp.obj[mapNum][2].worldX = 16 * gp.tileSize;
        gp.obj[mapNum][2].worldY = 18 * gp.tileSize;

        gp.obj[mapNum][3] = new obj_coin(gp);
        gp.obj[mapNum][3].worldX = 18 * gp.tileSize;
        gp.obj[mapNum][3].worldY = 13 * gp.tileSize;

        gp.obj[mapNum][4] = new obj_coin(gp);
        gp.obj[mapNum][4].worldX = 20 * gp.tileSize;
        gp.obj[mapNum][4].worldY = 16 * gp.tileSize;

        gp.obj[mapNum][5] = new obj_coin(gp);
        gp.obj[mapNum][5].worldX = 23 * gp.tileSize;
        gp.obj[mapNum][5].worldY = 9 * gp.tileSize;

        gp.obj[mapNum][6] = new obj_coin(gp);
        gp.obj[mapNum][6].worldX = 28 * gp.tileSize;
        gp.obj[mapNum][6].worldY = 10 * gp.tileSize;

        gp.obj[mapNum][7] = new obj_coin(gp);
        gp.obj[mapNum][7].worldX = 27 * gp.tileSize;
        gp.obj[mapNum][7].worldY = 21 * gp.tileSize;

        gp.obj[mapNum][8] = new obj_coin(gp);
        gp.obj[mapNum][8].worldX = 31 * gp.tileSize;
        gp.obj[mapNum][8].worldY = 19 * gp.tileSize;

        gp.obj[mapNum][9] = new obj_coin(gp);
        gp.obj[mapNum][9].worldX = 20 * gp.tileSize;
        gp.obj[mapNum][9].worldY = 22 * gp.tileSize;

        gp.obj[mapNum][10] = new obj_potion1(gp);
        gp.obj[mapNum][10].worldX = 16 * gp.tileSize;
        gp.obj[mapNum][10].worldY = 11 * gp.tileSize;

        gp.obj[mapNum][11] = new obj_potion2(gp);
        gp.obj[mapNum][11].worldX = 17 * gp.tileSize;
        gp.obj[mapNum][11].worldY = 21 * gp.tileSize;

        gp.obj[mapNum][12] = new obj_potion3(gp);
        gp.obj[mapNum][12].worldX = 30 * gp.tileSize;
        gp.obj[mapNum][12].worldY = 22 * gp.tileSize;

        gp.obj[mapNum][13] = new obj_portal(gp);
        gp.obj[mapNum][13].worldX = 33 * gp.tileSize;
        gp.obj[mapNum][13].worldY = 14 * gp.tileSize;


        gp.obj[mapNum][14] = new obj_potion1(gp);
        gp.obj[mapNum][14].worldX = 26 * gp.tileSize;
        gp.obj[mapNum][14].worldY = 16 * gp.tileSize;



        //lvl 2

        mapNum+=1;
        gp.obj[mapNum][13] = new obj_portal(gp);
        gp.obj[mapNum][13].worldX = 33 * gp.tileSize;
        gp.obj[mapNum][13].worldY = 10 * gp.tileSize;

        gp.obj[mapNum][14] = new obj_coin(gp);
        gp.obj[mapNum][14].worldX = 8* gp.tileSize;
        gp.obj[mapNum][14].worldY = 11 * gp.tileSize;

        gp.obj[mapNum][15] = new obj_coin(gp);
        gp.obj[mapNum][15].worldX = 16 * gp.tileSize;
        gp.obj[mapNum][15].worldY = 9 * gp.tileSize;

        gp.obj[mapNum][16] = new obj_potion1(gp);
        gp.obj[mapNum][16].worldX = 9 * gp.tileSize;
        gp.obj[mapNum][16].worldY = 16 * gp.tileSize;

        gp.obj[mapNum][17] = new obj_coin(gp);
        gp.obj[mapNum][17].worldX = 31 * gp.tileSize;
        gp.obj[mapNum][17].worldY = 7 * gp.tileSize;

        gp.obj[mapNum][18] = new obj_coin(gp);
        gp.obj[mapNum][18].worldX = 20 * gp.tileSize;
        gp.obj[mapNum][18].worldY = 19 * gp.tileSize;

        gp.obj[mapNum][19] = new obj_coin(gp);
        gp.obj[mapNum][19].worldX = 20 * gp.tileSize;
        gp.obj[mapNum][19].worldY = 22 * gp.tileSize;

        gp.obj[mapNum][20] = new obj_coin(gp);
        gp.obj[mapNum][20].worldX = 31 * gp.tileSize;
        gp.obj[mapNum][20].worldY = 24 * gp.tileSize;

        gp.obj[mapNum][21] = new obj_coin(gp);
        gp.obj[mapNum][21].worldX = 32 * gp.tileSize;
        gp.obj[mapNum][21].worldY = 18 * gp.tileSize;

        gp.obj[mapNum][22] = new obj_coin(gp);
        gp.obj[mapNum][22].worldX = 33 * gp.tileSize;
        gp.obj[mapNum][22].worldY = 12 * gp.tileSize;

        gp.obj[mapNum][23] = new obj_potion1(gp);
        gp.obj[mapNum][23].worldX = 11 * gp.tileSize;
        gp.obj[mapNum][23].worldY = 12 * gp.tileSize;

        gp.obj[mapNum][24] = new obj_coin(gp);
        gp.obj[mapNum][24].worldX = 14 * gp.tileSize;
        gp.obj[mapNum][24].worldY = 12 * gp.tileSize;

        gp.obj[mapNum][25] = new obj_potion1(gp);
        gp.obj[mapNum][25].worldX = 24 * gp.tileSize;
        gp.obj[mapNum][25].worldY = 22 * gp.tileSize;

        gp.obj[mapNum][26] = new obj_potion1(gp);
        gp.obj[mapNum][26].worldX = 30 * gp.tileSize;
        gp.obj[mapNum][26].worldY = 11 * gp.tileSize;

        gp.obj[mapNum][26] = new obj_potion1(gp);
        gp.obj[mapNum][26].worldX = 20 * gp.tileSize;
        gp.obj[mapNum][26].worldY = 7 * gp.tileSize;

        gp.obj[mapNum][27] = new obj_potion2(gp);
        gp.obj[mapNum][27].worldX = 25 * gp.tileSize;
        gp.obj[mapNum][27].worldY = 18 * gp.tileSize;

        gp.obj[mapNum][28] = new obj_coin(gp);
        gp.obj[mapNum][28].worldX = 33 * gp.tileSize;
        gp.obj[mapNum][28].worldY = 12 * gp.tileSize;

        gp.obj[mapNum][29] = new obj_coin(gp);
        gp.obj[mapNum][29].worldX = 18 * gp.tileSize;
        gp.obj[mapNum][29].worldY = 22 * gp.tileSize;

        gp.obj[mapNum][30] = new obj_potion3(gp);
        gp.obj[mapNum][30].worldX = 30 * gp.tileSize;
        gp.obj[mapNum][30].worldY = 11 * gp.tileSize;

        gp.obj[mapNum][31] = new obj_potion3(gp);
        gp.obj[mapNum][31].worldX = 8 * gp.tileSize;
        gp.obj[mapNum][31].worldY = 20 * gp.tileSize;

        //lvl3
        mapNum+=1;
        gp.obj[mapNum][32] = new obj(gp);
        gp.obj[mapNum][32].worldX = 33 * gp.tileSize;
        gp.obj[mapNum][32].worldY = 23 * gp.tileSize;

        gp.obj[mapNum][33] = new obj_coin(gp);
        gp.obj[mapNum][33].worldX = 14 * gp.tileSize;
        gp.obj[mapNum][33].worldY = 9 * gp.tileSize;

        gp.obj[mapNum][38] = new obj_coin(gp);
        gp.obj[mapNum][38].worldX = 10 * gp.tileSize;
        gp.obj[mapNum][38].worldY = 15 * gp.tileSize;

        gp.obj[mapNum][34] = new obj_coin(gp);
        gp.obj[mapNum][34].worldX = 19 * gp.tileSize;
        gp.obj[mapNum][34].worldY = 17 * gp.tileSize;

        gp.obj[mapNum][36] = new obj_coin(gp);
        gp.obj[mapNum][36].worldX = 26 * gp.tileSize;
        gp.obj[mapNum][36].worldY = 13 * gp.tileSize;

        gp.obj[mapNum][37] = new obj_coin(gp);
        gp.obj[mapNum][37].worldX = 31 * gp.tileSize;
        gp.obj[mapNum][37].worldY = 23 * gp.tileSize;

        gp.obj[mapNum][39] = new obj_potion1(gp);
        gp.obj[mapNum][39].worldX = 33 * gp.tileSize;
        gp.obj[mapNum][39].worldY = 11 * gp.tileSize;

        gp.obj[mapNum][40] = new obj_potion1(gp);
        gp.obj[mapNum][40].worldX = 12 * gp.tileSize;
        gp.obj[mapNum][40].worldY = 21 * gp.tileSize;

        gp.obj[mapNum][41] = new obj_potion1(gp);
        gp.obj[mapNum][41].worldX = 18 * gp.tileSize;
        gp.obj[mapNum][41].worldY = 13 * gp.tileSize;

        gp.obj[mapNum][42] = new obj_potion2(gp);
        gp.obj[mapNum][42].worldX = 22 * gp.tileSize;
        gp.obj[mapNum][42].worldY = 22 * gp.tileSize;


    }
    // plaseaza monstrii pe harta in functie de nivel
    public void setMonster(){
        int mapNum=0;
        gp.monster[mapNum][0] = new Ghost(gp);
        gp.monster[mapNum][0].worldX = gp.tileSize*13;
        gp.monster[mapNum][0].worldY = gp.tileSize*18;

        gp.monster[mapNum][1] = new Ghost(gp);
        gp.monster[mapNum][1].worldX = gp.tileSize*26;
        gp.monster[mapNum][1].worldY = gp.tileSize*10;


        mapNum+=1;
        gp.monster[mapNum][2] = new Ghost(gp);
        gp.monster[mapNum][2].worldX = gp.tileSize*29;
        gp.monster[mapNum][2].worldY = gp.tileSize*7;

        gp.monster[mapNum][3] = new Ghost(gp);
        gp.monster[mapNum][3].worldX = gp.tileSize*17;
        gp.monster[mapNum][3].worldY = gp.tileSize*22;

        gp.monster[mapNum][4] = new Ghost(gp);
        gp.monster[mapNum][4].worldX = gp.tileSize*33;
        gp.monster[mapNum][4].worldY = gp.tileSize*22;

        gp.monster[mapNum][5] = new Ghost(gp);
        gp.monster[mapNum][5].worldX = gp.tileSize*14;
        gp.monster[mapNum][5].worldY = gp.tileSize*14;


        //lvl 3
        mapNum+=1;
        gp.monster[mapNum][6] = new Final_Boss(gp);
        gp.monster[mapNum][6].worldX = gp.tileSize*30;
        gp.monster[mapNum][6].worldY = gp.tileSize*18;

        gp.monster[mapNum][7] = new Ghost(gp);
        gp.monster[mapNum][7].worldX = gp.tileSize*32;
        gp.monster[mapNum][7].worldY = gp.tileSize*11;

        gp.monster[mapNum][8] = new Ghost(gp);
        gp.monster[mapNum][8].worldX = gp.tileSize*29;
        gp.monster[mapNum][8].worldY = gp.tileSize*23;

        gp.monster[mapNum][9] = new Ghost(gp);
        gp.monster[mapNum][9].worldX = gp.tileSize*12;
        gp.monster[mapNum][9].worldY = gp.tileSize*20;
    }
}
