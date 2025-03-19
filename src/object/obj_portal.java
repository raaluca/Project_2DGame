package object;

import entity.Entity;
import main.GamePanel;

public class obj_portal extends Entity {

    public obj_portal(GamePanel gp) {
        super(gp);
        name = "portal";
        down1 = setup("/objects/portal01",gp.tileSize,gp.tileSize);
    }
}

