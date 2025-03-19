package monster;

import entity.Entity;
import main.GamePanel;

import java.util.Random;

public class Final_Boss extends Entity {
    GamePanel gp;

    public Final_Boss(GamePanel gp)
    {
        super(gp);
        this.gp=gp;
        name = "Boss";
        speed = 2;
        maxLife = 6;
        life = maxLife;
        type=2;

        solidArea.x = 3;
        solidArea.y = 18;
        solidArea.width=42;
        solidArea.height=30;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;

        getImage();
        setAction();

    }
    // metoda pentru obținerea imaginii entitatii boss
    public void getImage() {
        down1 = setup("/monsters/boss_down1",gp.tileSize,gp.tileSize);
        down2 = setup("/monsters/boss_down2",gp.tileSize,gp.tileSize);
        down3 = setup("/monsters/boss_down3",gp.tileSize,gp.tileSize);
        up1 = setup("/monsters/boss_up1",gp.tileSize,gp.tileSize);
        up2 = setup("/monsters/boss_up2",gp.tileSize,gp.tileSize);
        up3 = setup("/monsters/boss_up3",gp.tileSize,gp.tileSize);
        left1 = setup("/monsters/boss_left1",gp.tileSize,gp.tileSize);
        left2 = setup("/monsters/boss_left2",gp.tileSize,gp.tileSize);
        left3 = setup("/monsters/boss_left3",gp.tileSize,gp.tileSize);
        right1 = setup("/monsters/boss_right1",gp.tileSize,gp.tileSize);
        right2 = setup("/monsters/boss_right1",gp.tileSize,gp.tileSize);
        right3 = setup("/monsters/boss_right1",gp.tileSize,gp.tileSize);
    }
    // metoda pentru actualizarea comportamentului boss-ului
    public void updateMonster() {
        super.updateMonster();
        int xDistance = Math.abs(worldX - gp.player.worldX);
        int yDistance = Math.abs(worldY - gp.player.worldY);
        int tileDistance = (xDistance + yDistance) / gp.tileSize;

        if (!onPath && tileDistance < 10) {
            int i = new Random().nextInt(100) + 1;
            if (i > 50) {
                onPath = true;
            }
        }
        if (onPath && tileDistance > 20) {
            onPath = false;
        }
    }

    // metoda pentru stabilirea acțiunii boss-ului
    public void setAction() {

        if (onPath) {
            int goalCol = (gp.player.worldX + gp.player.solidArea.x) / gp.tileSize;
            int goalRow = (gp.player.worldY + gp.player.solidArea.y) / gp.tileSize;

            searchPath(goalCol, goalRow);
        } else {
            actionLockCounter++;
            if (actionLockCounter == 120) {
                Random random = new Random();
                int i = random.nextInt(100) + 1;

                if (i <= 25) {
                    direction = "up";
                }
                if (i > 25 && i <= 50) {
                    direction = "down";
                }
                if (i > 50 && i <= 75) {
                    direction = "left";
                }
                if (i > 75) {
                    direction = "right";
                }
                actionLockCounter = 0;
            }
        }
    }


}
