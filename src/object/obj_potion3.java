package object;

import entity.Entity;
import main.GamePanel;

import javax.imageio.ImageIO;
import java.io.IOException;

public class obj_potion3 extends Entity {
    GamePanel gp;
    public obj_potion3(GamePanel gp){
        super(gp);
        name="Green_potion";
        down1=setup("/objects/pverdebun2",gp.tileSize,gp.tileSize);

        solidArea.x= 2;
        solidArea.y =2;
    }
}