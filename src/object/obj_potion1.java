package object;

import main.GamePanel;
import entity.Entity;

public class obj_potion1 extends Entity {
    public obj_potion1(GamePanel gp){
        super(gp);
        name="Red_potion";
        down1=setup("/objects/prosiebun",gp.tileSize,gp.tileSize);
        solidArea.x= 2;
        solidArea.y =2;
    }
}
