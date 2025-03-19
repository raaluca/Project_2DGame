
package object;

import entity.Entity;
import main.GamePanel;
public class obj extends Entity {
    public obj(GamePanel gp){
        super(gp);
        name="Portal_roz";
        down1=setup("/objects/portal42",gp.tileSize,gp.tileSize);

        solidArea.x = 5;
        solidArea.y = 5;

    }

}