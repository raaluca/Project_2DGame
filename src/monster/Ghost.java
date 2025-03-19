package monster;

import entity.Entity;
import main.GamePanel;
import java.util.Random;

public class Ghost extends Entity {
    GamePanel gp;

    public Ghost(GamePanel gp)
    {
        super(gp);
        this.gp=gp;
        name = "Ghost";
        speed = 1;
        maxLife=2;
        life = maxLife;
        type=2;

        solidArea.x = 3;
        solidArea.y = 18;
        solidArea.width=30;
        solidArea.height=30;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;

        getImage();
        setAction();
    }

    // metoda pentru obținerea imaginii entitatii ghost
    public void getImage()
    {
        down1=setup("/monsters/down",gp.tileSize,gp.tileSize);
        down2=setup("/monsters/down",gp.tileSize,gp.tileSize);
        down3=setup("/monsters/down",gp.tileSize,gp.tileSize);
        up1= setup("/monsters/left",gp.tileSize,gp.tileSize);
        up2= setup("/monsters/left",gp.tileSize,gp.tileSize);
        up3= setup("/monsters/left",gp.tileSize,gp.tileSize);
        left1=setup("/monsters/left",gp.tileSize,gp.tileSize);
        left2=setup("/monsters/left",gp.tileSize,gp.tileSize);
        left3=setup("/monsters/left",gp.tileSize,gp.tileSize);
        right1=setup("/monsters/right",gp.tileSize,gp.tileSize);
        right2=setup("/monsters/right_attack",gp.tileSize,gp.tileSize);
        right3=setup("/monsters/right",gp.tileSize,gp.tileSize);
    }

    // metoda pentru stabilirea acțiunii ghost-ului
    public void setAction() {
        actionLockCounter++;
        if (actionLockCounter == 120) {
            Random random = new Random();
            int i = random.nextInt(100) + 1;
            if (i <= 25)
                direction = "up";
            if (i > 25 && i <= 50)
                direction = "down";
            if (i > 50 && i <= 75)
                direction = "left";
            if (i > 75 && i< 100)
                direction = "right";
            actionLockCounter = 0;
        }
    }

    // metoda pentru reacția la daune a ghost-ului
    public void damageReaction(){
        actionLockCounter=0;
        direction = gp.player.direction;
        //onPath=true;
    }

}
