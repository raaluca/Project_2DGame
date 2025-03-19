package main;

import java.awt.*;
import java.awt.image.BufferedImage;

// clasa pentru instrumente utilitare
public class UtilityTool {
    // metoda pentru redimensionarea unei imagini
    public BufferedImage scaleImage(BufferedImage original, int width, int height){
        // crearea unei imagini redimensionate
        BufferedImage scaledImage = new BufferedImage(width, height, original.getType());
        // obtinerea contextului grafic al imaginii redimensionate
        Graphics2D g2 = scaledImage.createGraphics();
        // desenarea imaginii originale pe cea redimensionata
        g2.drawImage(original, 0, 0, width, height, null);
        // eliberarea resurselor contextului grafic
        g2.dispose();

        return scaledImage; // returnarea imaginii redimensionate
    }
}
