package object;

import entity.Entity;
import main.GamePanel;


public class obj_potion2 extends Entity {

    public obj_potion2(GamePanel gp){
        super(gp);
        name="Blue_potion";
        down1=setup("/objects/palbastrabun",gp.tileSize,gp.tileSize);
        solidArea.x= 2;
        solidArea.y =2;
    }
}
