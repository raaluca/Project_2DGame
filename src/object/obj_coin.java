package object;

import entity.Entity;
import main.GamePanel;
public class obj_coin extends Entity {
    public obj_coin(GamePanel gp){
        super(gp);
        name="Coin";
        down1=setup("/objects/coin",gp.tileSize,gp.tileSize);

        solidArea.x = 5;
        solidArea.y = 5;

    }

}