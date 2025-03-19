package entity;

import main.GamePanel;
import main.UtilityTool;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Objects;
import java.util.Random;

public class Entity {
    GamePanel gp;
    // imagini pentru diferite directii si actiuni ale entitatii
    public BufferedImage up1, up2, up3, up4, up5, left1, left2, left3, left4, left5, right1, right2, right3, right4, right5, down1, down2, down3, down4, down5;
    public BufferedImage attackUp1, attackUp2, attackUp3, attackUp4, attackUp5, attackDown1, attackDown2, attackDown3, attackDown4, attackDown5,
            attackLeft1, attackLeft2, attackLeft3, attackLeft4, attackLeft5, attackRight1, attackRight2, attackRight3, attackRight4, attackRight5;
    public BufferedImage image, image2, image3;
    // zone pentru coliziuni
    public Rectangle solidArea = new Rectangle(0, 0, 48, 48);
    public Rectangle attackArea = new Rectangle(0, 0, 0, 0);
    // stare
    public String name;
    public int worldX, worldY;
    public String direction = "down";
    public int spriteNum = 1;

    public boolean onPath;

    public boolean collision = false;
    public boolean collisionOn = false;
    public boolean alive = true;
    public boolean dying = false;
    public int type;

    boolean attacking = false;
    // contor pentru animatii
    public int spriteCounter = 0;
    public int solidAreaDefaultX, solidAreaDefaultY;
    int dyingCounter = 0;

    // atribute caracter
    public int maxLife;
    public int life;
    public int speed;

    // monstru
    public int actionLockCounter = 0;
    public boolean invincible = false;
    public int invincibleCounter = 0;
    boolean hpBarOn = false;
    int hpBarCounter = 0;

    // constructor
    public Entity(GamePanel gp) {
        this.gp = gp;
    }

    // metoda pentru setarea actiunii
    public void setAction() {
    }

    // reactie la primirea de daune
    public void damageReaction() {
    }

    // verifica coliziunea monstrului
    public void checkCollisionMonster() {
        collisionOn = false;
        gp.cChecker.checkTile(this); // verifica coliziunea cu dalele
        gp.cChecker.checkObject(this, false); // verifica coliziunea cu obiectele
        boolean contactPlayer = gp.cChecker.checkPlayer(this); // verifica coliziunea cu jucatorul
        gp.cChecker.checkEntity(this, gp.monster); // verifica coliziunea cu alte entitati
        gp.cChecker.checkPlayer(this); // verifica din nou coliziunea cu jucatorul

        // daca nu exista coliziune, entitatea se deplaseaza in directia specificata
        if (!collisionOn) {
            switch (direction) {
                case "up":
                    worldY -= speed; // deplasare in sus
                    break;
                case "down":
                    worldY += speed; // deplasare in jos
                    break;
                case "left":
                    worldX -= speed; // deplasare la stanga
                    break;
                case "right":
                    worldX += speed; // deplasare la dreapta
                    break;
            }
        }

        // gestioneaza animatia entitatii
        spriteCounter++;
        if (spriteCounter > 12) {
            if (spriteNum == 1) {
                spriteNum = 2;
            } else if (spriteNum == 2) {
                spriteNum = 3;
            } else if (spriteNum == 3) {
                spriteNum = 1;
            }
            spriteCounter = 0;
        }

        // gestioneaza invincibilitatea entitatii
        if (invincible == true) {
            invincibleCounter++;
            if (invincibleCounter > 40) {
                invincible = false;
                invincibleCounter = 0;
            }
        }

        // daca entitatea este de tip 2 si intra in contact cu jucatorul
        if (this.type == 2 && contactPlayer) {
            if (!gp.player.invincible) {
                gp.player.life -= 1; // scade viata jucatorului
                gp.player.invincible = true;
            }
        }
    }

    // metoda pentru configurarea imaginilor
    public BufferedImage setup(String imagePath, int width, int height) {
        UtilityTool uTool = new UtilityTool();
        BufferedImage image = null;
        try {
            image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(imagePath + ".png")));
            image = uTool.scaleImage(image, width, height); // redimensioneaza imaginea

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return image;
    }

    // metoda pentru cautarea unui drum catre un obiectiv
    public void searchPath(int goalCol, int goalRow) {
        int startCol = (worldX + solidArea.x) / gp.tileSize;
        int startRow = (worldY + solidArea.y) / gp.tileSize;

        gp.pFinder.setNodes(startCol, startRow, goalCol, goalRow, this); // seteaza nodurile pentru cautare

        if (gp.pFinder.search()) { // daca calea a fost gasita
            int nextX = gp.pFinder.pathList.getFirst().col * gp.tileSize;
            int nextY = gp.pFinder.pathList.getFirst().row * gp.tileSize;

            int enLeftX = worldX + solidArea.x;
            int enRightX = worldX + solidArea.x + solidArea.width;
            int enTopY = worldY + solidArea.y;
            int enBottomY = worldY + solidArea.y + solidArea.height;

            if (enTopY > nextY && enLeftX >= nextX && enRightX < nextX + gp.tileSize) {
                direction = "up";
            } else if (enTopY < nextY && enLeftX >= nextX && enRightX < nextX + gp.tileSize) {
                direction = "down";
            } else if (enTopY >= nextY && enBottomY < nextY + gp.tileSize) {
                if (enLeftX > nextX) {
                    direction = "left";
                }
                if (enLeftX < nextX) {
                    direction = "right";
                }
            } else if (enTopY > nextY && enLeftX > nextX) {
                direction = "up";
                checkCollisionMonster(); // verifica coliziunea
                if (collisionOn) {
                    direction = "left";
                }
            } else if (enTopY > nextY && enLeftX < nextX) {
                direction = "up";
                checkCollisionMonster();
                if (collisionOn) {
                    direction = "right";
                }
            } else if (enTopY < nextY && enLeftX > nextX) {
                direction = "down";
                checkCollisionMonster();
                if (collisionOn) {
                    direction = "left";
                }
            } else if (enTopY < nextY && enLeftX < nextX) {
                direction = "down";
                checkCollisionMonster();
                if (collisionOn) {
                    direction = "right";
                }
            }
        }
    }

    // metoda pentru desenarea entitatii pe ecran
    public void draw(Graphics2D g2) {
        BufferedImage image = null;
        int screenX = worldX - gp.player.worldX + gp.player.screenX;
        int screenY = worldY - gp.player.worldY + gp.player.screenY;

        if (worldX + gp.tileSize > gp.player.worldX - gp.player.screenX &&
                worldX - gp.tileSize < gp.player.worldX + gp.player.screenX &&
                worldY + gp.tileSize > gp.player.worldY - gp.player.screenY &&
                worldY - gp.tileSize < gp.player.worldY + gp.player.screenY) {

            switch (direction) {
                case "up":
                    if (spriteNum == 1) image = up1;
                    if (spriteNum == 2) image = up2;
                    if (spriteNum == 3) image = up3;
                    break;
                case "down":
                    if (spriteNum == 1) image = down1;
                    if (spriteNum == 2) image = down2;
                    if (spriteNum == 3) image = down3;
                    break;
                case "left":
                    if (spriteNum == 1) image = left1;
                    if (spriteNum == 2) image = left2;
                    if (spriteNum == 3) image = left3;
                    break;
                case "right":
                    if (spriteNum == 1) image = right1;
                    if (spriteNum == 2) image = right2;
                    if (spriteNum == 3) image = right3;
                    break;
            }

            // bara de viata pentru monstru
            if (type == 2 && hpBarOn == true) {
                double oneScale = (double) gp.tileSize / maxLife;
                double hpBarValue = oneScale * life;

                g2.setColor(new Color(35, 35, 35));
                g2.fillRect(screenX - 1, screenY - 16, gp.tileSize + 2, 12);
                g2.setColor(new Color(255, 0, 30));
                g2.fillRect(screenX, screenY - 15, (int) hpBarValue, 10);

                hpBarCounter++;

                if (hpBarCounter > 600) {
                    hpBarCounter = 0;
                    hpBarOn = false;
                }
            }

            // daca entitatea este invincibila, schimba transparenta
            if (invincible == true) {
                hpBarOn = true;
                hpBarCounter = 0;
                changeAlpha(g2, 0.4f); // schimba transparenta
            }

            // daca entitatea este pe cale sa moara, incepe animatia de moarte
            if (dying == true) {
                dyingAnimation(g2); // animatie de moarte
            }

            g2.drawImage(image, screenX, screenY, gp.tileSize, gp.tileSize, null);

            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
        }
    }

    // animatia pentru moartea entitatii
    public void dyingAnimation(Graphics2D g2) {
        dyingCounter++;
        int i = 5;
        if (dyingCounter <= i) {
            changeAlpha(g2, 0f);
        }
        if (dyingCounter > i && dyingCounter <= i * 2) {
            changeAlpha(g2, 1f);
        }
        if (dyingCounter > i * 2 && dyingCounter <= i * 3) {
            changeAlpha(g2, 0f);
        }
        if (dyingCounter > i * 3 && dyingCounter <= i * 4) {
            changeAlpha(g2, 1f);
        }
        if (dyingCounter > i * 4 && dyingCounter <= i * 5) {
            changeAlpha(g2, 0f);
        }
        if (dyingCounter > i * 5 && dyingCounter <= i * 6) {
            changeAlpha(g2, 1f);
        }
        if (dyingCounter > i * 6 && dyingCounter <= i * 7) {
            changeAlpha(g2, 0f);
        }
        if (dyingCounter > i * 7 && dyingCounter <= i * 8) {
            changeAlpha(g2, 1f);
        }
        if (dyingCounter > i * 8) {
            dying = false;
            alive = false;
        }
    }

    // schimba transparenta entitatii
    public void changeAlpha(Graphics2D g2, float alphaValue) {
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alphaValue));
    }

    // actualizeaza starea monstrului
    public void updateMonster() {
        setAction();
        checkCollisionMonster();
    }
}
