package object;

import entity.Entity;
import main.GamePanel;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Score extends Entity {
    public BufferedImage image;
    public String name;

    public Score(GamePanel gp) {
        super(gp);
        name="coin";
        try{
            image = ImageIO.read(getClass().getResourceAsStream("/objects/coin.png"));
        }catch(IOException e){
            e.printStackTrace();
        }
    }



}
