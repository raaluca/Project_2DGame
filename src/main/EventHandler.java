package main;

public class EventHandler {

    GamePanel gp;
    EventRect eventRect[][][];
    int previousEventX, previousEventY;
    boolean canTouchEvent = true;
    public boolean aux = true;
    int a = 0;

    // constructorul clasei EventHandler care initializeaza matricea eventRect
    public EventHandler(GamePanel gp) {
        this.gp = gp;
        eventRect = new EventRect[gp.maxMap][gp.maxWorldCol][gp.maxWorldRow];
        int map = 0;
        int col = 0;
        int row = 0;
        // initializeaza fiecare EventRect din matricea eventRect
        while (map < gp.maxMap && col < gp.maxWorldCol && row < gp.maxWorldRow) {
            eventRect[map][col][row] = new EventRect();
            eventRect[map][col][row].x = 23;
            eventRect[map][col][row].y = 23;
            eventRect[map][col][row].width = 2;
            eventRect[map][col][row].height = 2;
            eventRect[map][col][row].eventRectDefaultX = eventRect[map][col][row].x;
            eventRect[map][col][row].eventRectDefaultY = eventRect[map][col][row].y;

            col++;
            if (col == gp.maxWorldCol) {
                col = 0;
                row++;
                if (row == gp.maxWorldRow) {
                    row = 0;
                    map++;
                }
            }
        }
    }

    // metoda care verifica daca un eveniment poate fi declansat
    public void checkEvent() {
        int xDistance = Math.abs(gp.player.worldX - previousEventX);
        int yDistance = Math.abs(gp.player.worldY - previousEventY);
        int distance = Math.max(xDistance, yDistance);
        if (distance > gp.tileSize) {
            canTouchEvent = true;
        }
        if (canTouchEvent == true) {
            // verifica evenimentele in functie de nivelul jucatorului
            if (gp.player.nivel == 1) {
                if (hit(0, 33, 14, "any") == true && gp.player.hasCoin >= 5) {
                    gp.ui.showMessage("Level up!");
                    teleport(1, 11, 8);
                    gp.player.nivel++;
                    gp.tileM.update();
                } else if (hit(0, 33, 14, "any") == true && gp.player.hasCoin < 5) {
                    gp.ui.showMessage("To level up you need to collect 5 coins.");
                }
            } else if (gp.player.nivel == 2) {
                if (hit(1, 33, 10, "any") == true && gp.player.hasCoin >=10) {
                    gp.ui.showMessage("Level up!");
                    teleport(2, 10, 12);
                    gp.player.nivel++;
                    gp.tileM.update();
                } else if (hit(1, 33, 10, "any") == true && gp.player.hasCoin < 10) {
                    gp.ui.showMessage("To level up you need to collect 10 coins.");
                }
            } else if (gp.player.nivel == 3) {
                if (hit(2, 33, 23, "any") == true && gp.monster[2][6] == null && gp.player.hasCoin >=5) {
                    gp.gameState = gp.finishGame;
                } else if (hit(2, 33, 23, "any") == true && gp.player.hasCoin < 5) {
                    gp.ui.showMessage("You need to collect 5 coins.");
                } else if (hit(2, 33, 23, "any") == true && gp.monster[2][6] != null){
                    gp.ui.showMessage("You need beat Vorax!");
                }
            }
        }
    }

    // metoda care teleporteaza jucatorul la o noua locatie
    public void teleport(int map, int col, int row) {
        gp.currentMap = map;
        gp.player.worldX = gp.tileSize * col;
        gp.player.worldY = gp.tileSize * row;
        previousEventX = gp.player.worldX;
        previousEventY = gp.player.worldY;
        canTouchEvent = false;
        gp.player.hasCoin = 0;
    }

    // metoda care verifica daca jucatorul a lovit un eveniment specific
    public boolean hit(int map, int col, int row, String reqDirection) {
        boolean hit = false;

        // ajustare pentru nivelul 3
        if (gp.player.nivel == 3 && gp.currentMap == 0)
            gp.currentMap = 2;

        // verifica daca evenimentul se afla pe harta curenta
        if (map == gp.currentMap) {
            gp.player.solidArea.x = gp.player.worldX + gp.player.solidArea.x;
            gp.player.solidArea.y = gp.player.worldY + gp.player.solidArea.y;

            eventRect[map][col][row].x = col * gp.tileSize + eventRect[map][col][row].x;
            eventRect[map][col][row].y = row * gp.tileSize + eventRect[map][col][row].y;

            // verifica daca exista coliziune intre solidArea jucatorului si eventRect
            if (gp.player.solidArea.intersects(eventRect[map][col][row]) && eventRect[map][col][row].eventDone == false) {
                if (gp.player.direction.contentEquals(reqDirection) || reqDirection.contentEquals("any")) {
                    hit = true;
                    previousEventX = gp.player.worldX;
                    previousEventY = gp.player.worldY;
                }
            }
            gp.player.solidArea.x = gp.player.solidAreaDefaultX;
            gp.player.solidArea.y = gp.player.solidAreaDefaultY;
            eventRect[map][col][row].x = eventRect[map][col][row].eventRectDefaultX;
            eventRect[map][col][row].y = eventRect[map][col][row].eventRectDefaultY;
        }
        return hit;
    }
}
