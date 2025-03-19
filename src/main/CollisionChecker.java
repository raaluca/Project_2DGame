package main;

import entity.Entity;

public class CollisionChecker {
    GamePanel gp;

    // constructorul clasei CollisionChecker care initializeaza campul gp
    public CollisionChecker(GamePanel gp) {
        this.gp = gp;
    }

    // metoda care verifica coliziunea cu placile
    public void checkTile(Entity entity) {
        int entityLeftWorldX = entity.worldX + entity.solidArea.x;
        int entityRightWorldX = entity.worldX + entity.solidArea.x + entity.solidArea.width;
        int entityTopWorldY = entity.worldY + entity.solidArea.y;
        int entityBottomWorldY = entity.worldY + entity.solidArea.y + entity.solidArea.height;

        int entityLeftCol = entityLeftWorldX / gp.tileSize;
        int entityRightCol = entityRightWorldX / gp.tileSize;
        int entityTopRow = entityTopWorldY / gp.tileSize;
        int entityBottomRow = entityBottomWorldY / gp.tileSize;

        int tileNum1, tileNum2;

        // verifica coliziunea in functie de directia entitatii
        switch (entity.direction) {
            case "up":
                entityTopRow = (entityTopWorldY - entity.speed) / gp.tileSize;
                tileNum1 = gp.tileM.mapTileNum[gp.currentMap][entityLeftCol][entityTopRow];
                tileNum2 = gp.tileM.mapTileNum[gp.currentMap][entityRightCol][entityTopRow];
                if (gp.tileM.tile[tileNum1].collision == true || gp.tileM.tile[tileNum2].collision == true) {
                    entity.collisionOn = true;
                }
                break;
            case "down":
                entityBottomRow = (entityBottomWorldY - entity.speed) / gp.tileSize;
                tileNum1 = gp.tileM.mapTileNum[gp.currentMap][entityLeftCol][entityBottomRow];
                tileNum2 = gp.tileM.mapTileNum[gp.currentMap][entityRightCol][entityBottomRow];
                if (gp.tileM.tile[tileNum1].collision == true || gp.tileM.tile[tileNum2].collision == true) {
                    entity.collisionOn = true;
                }
                break;
            case "left":
                entityLeftCol = (entityLeftWorldX - entity.speed) / gp.tileSize;
                tileNum1 = gp.tileM.mapTileNum[gp.currentMap][entityLeftCol][entityTopRow];
                tileNum2 = gp.tileM.mapTileNum[gp.currentMap][entityLeftCol][entityBottomRow];
                if (gp.tileM.tile[tileNum1].collision == true || gp.tileM.tile[tileNum2].collision == true) {
                    entity.collisionOn = true;
                }
                break;
            case "right":
                entityRightCol = (entityRightWorldX - entity.speed) / gp.tileSize;
                tileNum1 = gp.tileM.mapTileNum[gp.currentMap][entityRightCol][entityTopRow];
                tileNum2 = gp.tileM.mapTileNum[gp.currentMap][entityRightCol][entityBottomRow];
                if (gp.tileM.tile[tileNum1].collision == true || gp.tileM.tile[tileNum2].collision == true) {
                    entity.collisionOn = true;
                }
                break;
        }

    }

    // metoda care verifica coliziunea cu obiectele si returneaza indexul obiectului cu care s-a produs coliziunea
    public int checkObject(Entity entity, boolean player) {
        int index = 999;

        for (int i = 0; i < gp.obj[1].length; i++) {
            if (gp.obj[gp.currentMap][i] != null) {
                // obtine pozitia solidArea a entitatii
                entity.solidArea.x = entity.worldX + entity.solidArea.x;
                entity.solidArea.y = entity.worldY + entity.solidArea.y;
                // obtine pozitia solidArea a obiectului
                gp.obj[gp.currentMap][i].solidArea.x = gp.obj[gp.currentMap][i].worldX + gp.obj[gp.currentMap][i].solidArea.x;
                gp.obj[gp.currentMap][i].solidArea.y = gp.obj[gp.currentMap][i].worldY + gp.obj[gp.currentMap][i].solidArea.y;

                // verifica coliziunea in functie de directia entitatii
                switch (entity.direction) {
                    case "up":
                        entity.solidArea.y -= entity.speed;
                        break;
                    case "down":
                        entity.solidArea.y += entity.speed;
                        break;
                    case "left":
                        entity.solidArea.x -= entity.speed;
                        break;
                    case "right":
                        entity.solidArea.x += entity.speed;
                        break;
                }
                // verifica daca exista intersectie intre solidArea entitatii si solidArea obiectului
                if (entity.solidArea.intersects(gp.obj[gp.currentMap][i].solidArea)) {
                    if (gp.obj[gp.currentMap][i].collision == true)
                        entity.collisionOn = true;
                    if (player == true)
                        index = i;
                }
                // reseteaza pozitia solidArea la valorile implicite
                entity.solidArea.x = entity.solidAreaDefaultX;
                entity.solidArea.y = entity.solidAreaDefaultY;
                gp.obj[gp.currentMap][i].solidArea.x = gp.obj[gp.currentMap][i].solidAreaDefaultX;
                gp.obj[gp.currentMap][i].solidArea.y = gp.obj[gp.currentMap][i].solidAreaDefaultY;
            }
        }
        return index;
    }

    // metoda care verifica coliziunea cu alte entitati si returneaza indexul entitatii cu care s-a produs coliziunea
    public int checkEntity(Entity entity, Entity[][] target) {
        int index = 999;

        for(int i=0;i<target[1].length;i++) {
            if(target[gp.currentMap][i]!=null){
                entity.solidArea.x = entity.worldX + entity.solidArea.x;
                entity.solidArea.y = entity.worldY + entity.solidArea.y;

                target[gp.currentMap][i].solidArea.x = target[gp.currentMap][i].worldX + target[gp.currentMap][i].solidArea.x;
                target[gp.currentMap][i].solidArea.y = target[gp.currentMap][i].worldY + target[gp.currentMap][i].solidArea.y;

                // verifica coliziunea in functie de directia entitatii
                switch (entity.direction) {
                    case "up":
                        entity.solidArea.y -= entity.speed;break;
                    case "down":
                        entity.solidArea.y += entity.speed; break;
                    case "left":
                        entity.solidArea.x -= entity.speed; break;
                    case "right":
                        entity.solidArea.x += entity.speed; break;
                }
                // verifica daca exista intersectie intre solidArea entitatii si solidArea celeilalte entitati
                if (entity.solidArea.intersects(target[gp.currentMap][i].solidArea)) {
                    if(target[gp.currentMap][i]!=entity) {
                        entity.collisionOn = true;
                        index = i;
                    }
                }
                // reseteaza pozitia solidArea la valorile implicite
                entity.solidArea.x = entity.solidAreaDefaultX;
                entity.solidArea.y = entity.solidAreaDefaultY;

                target[gp.currentMap][i].solidArea.x=entity.solidAreaDefaultX;
                target[gp.currentMap][i].solidArea.y=entity.solidAreaDefaultY;
            }
        }
        return index;
    }

    // metoda care verifica coliziunea cu jucatorul si returneaza true daca exista coliziune
    public boolean checkPlayer(Entity entity) {
        boolean contactPlayer = false;

        // obtine pozitia solidArea a entitatii
        entity.solidArea.x = entity.worldX + entity.solidArea.x;
        entity.solidArea.y = entity.worldY + entity.solidArea.y;

        // obtine pozitia solidArea a jucatorului
        gp.player.solidArea.x = gp.player.worldX + gp.player.solidArea.x;
        gp.player.solidArea.y = gp.player.worldY + gp.player.solidArea.y;

        // verifica coliziunea in functie de directia entitatii
        switch (entity.direction) {
            case "up":
                entity.solidArea.y -= entity.speed;
                break;
            case "down":
                entity.solidArea.y += entity.speed;
                break;
            case "left":
                entity.solidArea.x -= entity.speed;
                break;
            case "right":
                entity.solidArea.x += entity.speed;
                break;
        }
        // verifica daca exista intersectie intre solidArea entitatii si solidArea jucatorului
        if (entity.solidArea.intersects(gp.player.solidArea)) {
            entity.collisionOn = true;
            contactPlayer = true;
        }
        // reseteaza pozitia solidArea la valorile implicite
        entity.solidArea.x = entity.solidAreaDefaultX;
        entity.solidArea.y = entity.solidAreaDefaultY;
        gp.player.solidArea.x = gp.player.solidAreaDefaultX;
        gp.player.solidArea.y = gp.player.solidAreaDefaultY;

        return contactPlayer;
    }
}
