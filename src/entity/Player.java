package entity;

import main.GamePanel;
import main.KeyHandler;
import main.UtilityTool;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public class Player extends Entity {
    KeyHandler keyH;
    public final int screenX;
    public final int screenY;

    public int hasCoin = 0;
    public int hasScore = 0;
    public int nivel = 1;

    public Player(GamePanel gp, KeyHandler keyH) {
        super(gp);
        this.keyH = keyH;

        screenX = gp.screenWidth / 2 - (gp.tileSize / 2);
        screenY = gp.screenHeight / 2 - gp.tileSize / 2;

        solidArea = new Rectangle();
        solidArea.x = 8;
        solidArea.y = 16;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
        solidArea.width = 36;
        solidArea.height = 36;

        attackArea.width = 60;
        attackArea.height = 60;

        setDefaultValues();
        getPlayerImage();
        getPlayerAttackImage();
    }

    // seteaza valorile implicite pentru player
    public void setDefaultValues() {
        worldX = gp.tileSize * 10;
        worldY = gp.tileSize * 10;
        speed = 4;
        direction = "down1";

        // statusul jucatorului
        maxLife = 6;
        life = maxLife;
    }

    // seteaza pozitiile implicite pentru player
    public void setDefaultPositions() {
        worldX = gp.tileSize * 11;
        worldY = gp.tileSize * 8;
        direction = "down";
    }

    // restaureaza viata jucatorului
    public void restoreLife() {
        life = maxLife;
        invincible = false;
    }

    // incarca imaginile jucatorului
    public void getPlayerImage() {
        up1 = setup("/texture/spate1", gp.tileSize, gp.tileSize);
        up2 = setup("/texture/spate2", gp.tileSize, gp.tileSize);
        up3 = setup("/texture/spate3", gp.tileSize, gp.tileSize);
        up4 = setup("/texture/spate4", gp.tileSize, gp.tileSize);
        up5 = setup("/texture/spate5", gp.tileSize, gp.tileSize);

        down1 = setup("/texture/still", gp.tileSize, gp.tileSize);
        down2 = setup("/texture/fata2", gp.tileSize, gp.tileSize);
        down3 = setup("/texture/fata3", gp.tileSize, gp.tileSize);
        down4 = setup("/texture/fata4", gp.tileSize, gp.tileSize);
        down5 = setup("/texture/fata5", gp.tileSize, gp.tileSize);

        left1 = setup("/texture/stanga1", gp.tileSize, gp.tileSize);
        left2 = setup("/texture/stanga2", gp.tileSize, gp.tileSize);
        left3 = setup("/texture/stanga3", gp.tileSize, gp.tileSize);
        left4 = setup("/texture/stanga4", gp.tileSize, gp.tileSize);
        left5 = setup("/texture/stanga5", gp.tileSize, gp.tileSize);

        right1 = setup("/texture/dreapta1", gp.tileSize, gp.tileSize);
        right2 = setup("/texture/dreapta2", gp.tileSize, gp.tileSize);
        right3 = setup("/texture/dreapta3", gp.tileSize, gp.tileSize);
        right4 = setup("/texture/dreapta4", gp.tileSize, gp.tileSize);
        right5 = setup("/texture/dreapta5", gp.tileSize, gp.tileSize);
    }

    // incarca imaginile pentru atacul jucatorului
    public void getPlayerAttackImage() {
        attackUp1 = setup("/texture/attack_up1", gp.tileSize, gp.tileSize);
        attackUp2 = setup("/texture/attack_up2", gp.tileSize, gp.tileSize);
        attackUp3 = setup("/texture/attack_up3", gp.tileSize, gp.tileSize);
        attackUp4 = setup("/texture/attack_up4", gp.tileSize, gp.tileSize);
        attackUp5 = setup("/texture/attack_up5", gp.tileSize, gp.tileSize);

        attackDown1 = setup("/texture/attack_down1", gp.tileSize, gp.tileSize);
        attackDown2 = setup("/texture/attack_down2", gp.tileSize, gp.tileSize);
        attackDown3 = setup("/texture/attack_down3", gp.tileSize, gp.tileSize);
        attackDown4 = setup("/texture/attack_down4", gp.tileSize, gp.tileSize);
        attackDown5 = setup("/texture/attack_down5", gp.tileSize, gp.tileSize);

        attackLeft1 = setup("/texture/attack_left1", gp.tileSize, gp.tileSize);
        attackLeft2 = setup("/texture/attack_left2", gp.tileSize, gp.tileSize);
        attackLeft3 = setup("/texture/attack_left3", gp.tileSize, gp.tileSize);
        attackLeft4 = setup("/texture/attack_left4", gp.tileSize, gp.tileSize);
        attackLeft5 = setup("/texture/attack_left5", gp.tileSize, gp.tileSize);

        attackRight1 = setup("/texture/attack_right1", gp.tileSize, gp.tileSize);
        attackRight2 = setup("/texture/attack_right2", gp.tileSize, gp.tileSize);
        attackRight3 = setup("/texture/attack_right3", gp.tileSize, gp.tileSize);
        attackRight4 = setup("/texture/attack_right4", gp.tileSize, gp.tileSize);
        attackRight5 = setup("/texture/attack_right5", gp.tileSize, gp.tileSize);
    }

    // incarca imaginea si o scaleaza
    public BufferedImage setup(String imagePath) {
        UtilityTool uTool = new UtilityTool();
        BufferedImage image = null;
        try {
            image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(imagePath + ".png")));
            image = uTool.scaleImage(image, gp.tileSize, gp.tileSize);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return image;
    }

    // actualizeaza starea jucatorului
    public void update() {
        if (keyH.enterPressed && attacking == true) {
            attacking();
        } else if (keyH.upPressed == true || keyH.downPressed == true || keyH.leftPressed == true || keyH.rightPressed == true || keyH.enterPressed == true) {
            if (keyH.upPressed == true) {
                direction = "up";
            } else if (keyH.downPressed == true) {
                direction = "down";
            } else if (keyH.leftPressed == true) {
                direction = "left";
            } else if (keyH.rightPressed == true) {
                direction = "right";
            }

            // verifica coliziunea cu placile
            collisionOn = false;
            gp.cChecker.checkTile(this);

            // verifica coliziunea cu obiectele
            int objIndex = gp.cChecker.checkObject(this, true);
            pickUpObject(objIndex);

            // verifica coliziunea cu monstrii
            int monsterIndex = gp.cChecker.checkEntity(this, gp.monster);
            contactMonster(monsterIndex);

            // verifica evenimentele
            gp.eHandler.checkEvent();

            // daca nu exista coliziuni, jucatorul se poate misca
            if (collisionOn == false && keyH.enterPressed == false) {
                attacking = false;
                switch (direction) {
                    case "up":
                        worldY -= speed;
                        break;
                    case "down":
                        worldY += speed;
                        break;
                    case "left":
                        worldX -= speed;
                        break;
                    case "right":
                        worldX += speed;
                        break;
                }
                gp.keyH.enterPressed = false;
            } else if (keyH.enterPressed)
                attacking = true;

            // actualizeaza animatia
            spriteCounter++;
            if (spriteCounter > 12) {
                if (spriteNum == 1) {
                    spriteNum = 2;
                } else if (spriteNum == 2) {
                    spriteNum = 3;
                } else if (spriteNum == 3) {
                    spriteNum = 4;
                } else if (spriteNum == 4) {
                    spriteNum = 5;
                } else if (spriteNum == 5) {
                    spriteNum = 1;
                }
                spriteCounter = 0;
            }
        }

        // actualizeaza starea de invincibilitate
        if (invincible == true) {
            invincibleCounter++;
            if (invincibleCounter > 60) {
                invincible = false;
                invincibleCounter = 0;
            }
        }

        // verifica daca viata jucatorului este zero
        if (life <= 0) {
            gp.gameState = gp.gameOverState;
        }
    }

    // gestioneaza atacul jucatorului
    public void attacking() {
        spriteCounter++;
        if (spriteCounter <= 5) {
            spriteNum = 1;
        }
        if (spriteCounter > 5 && spriteCounter <= 10) {
            spriteNum = 2;
        }
        if (spriteCounter > 10 && spriteCounter <= 15) {
            spriteNum = 3;
        }
        if (spriteCounter > 15 && spriteCounter <= 20) {
            spriteNum = 4;
        }
        if (spriteCounter > 20 && spriteCounter <= 25) {
            spriteNum = 5;
            int currentWorldX = worldX;
            int currentWorldY = worldY;
            int solidAreaWidth = solidArea.width;
            int solidAreaHeight = solidArea.height;

            switch (direction) {
                case "up":
                    worldY -= attackArea.height;
                    break;
                case "down":
                    worldY += attackArea.height;
                    break;
                case "left":
                    worldX -= attackArea.width;
                    break;
                case "right":
                    worldX += attackArea.width;
                    break;
            }

            // zona de atac
            solidArea.width = attackArea.width;
            solidArea.height = attackArea.height;

            // verifica coliziunea cu monstrii
            int monsterIndex = gp.cChecker.checkEntity(this, gp.monster);
            damageMonster(monsterIndex);

            worldX = currentWorldX;
            worldY = currentWorldY;
            solidArea.width = solidAreaWidth;
            solidArea.height = solidAreaHeight;
        }

        if (spriteCounter > 25) {
            spriteNum = 1;
            spriteCounter = 0;
            attacking = false;
        }
    }

    // gestioneaza contactul cu un monstru
    public void contactMonster(int i) {
        if (i != 999) {
            if (!invincible) {
                life -= 2;
                invincible = true;
            }
        }
    }

    // gestioneaza daunele provocate unui monstru
    public void damageMonster(int i) {
        if (i != 999) {
            if (gp.monster[gp.currentMap][i].invincible == false) {
                gp.monster[gp.currentMap][i].life -= 1;
                gp.monster[gp.currentMap][i].invincible = true;
                gp.monster[gp.currentMap][i].damageReaction();
                if (gp.monster[gp.currentMap][i].life <= 0) {
                    gp.monster[gp.currentMap][i].dying = true;
                }
            }
        }
    }

    // gestioneaza ridicarea obiectelor
    public void pickUpObject(int i) {
        if (i != 999) {
            String objectName = gp.obj[gp.currentMap][i].name;

            switch (objectName) {
                case "Coin":
                    gp.playSE(1);
                    hasCoin++;
                    hasScore++;
                    gp.obj[gp.currentMap][i] = null;
                    break;
                case "Red_potion":
                    gp.obj[gp.currentMap][i] = null;
                    gp.ui.showMessage("Extra life! Your life is full.");
                    if (gp.player.life < 6) {
                        gp.ui.showMessage("Extra life!");
                        gp.player.life += 2;
                    }
                    break;
                case "Blue_potion":
                    speed += 1;
                    gp.obj[gp.currentMap][i] = null;
                    gp.ui.showMessage("Speed up!");
                    break;
                case "Green_potion":
                    gp.obj[gp.currentMap][i] = null;
                    gp.ui.showMessage("Oops! That's poison. You lost a life.");
                    gp.player.life -= 2;
                    break;
                case "portal":
                    gp.stopMusic();
                    gp.playSE(2);
                    break;
            }
        }
    }

    // deseneaza jucatorul pe ecran
    public void draw(Graphics2D g2) {
        BufferedImage image = null;
        switch (direction) {
            case "up":
                if (attacking == false) {
                    if (spriteNum == 1) {
                        image = up1;
                    }
                    if (spriteNum == 2) {
                        image = up2;
                    }
                    if (spriteNum == 3) {
                        image = up3;
                    }
                    if (spriteNum == 4) {
                        image = up4;
                    }
                    if (spriteNum == 5) {
                        image = up5;
                    }
                }
                if (attacking == true) {
                    if (spriteNum == 1) {
                        image = attackUp1;
                    }
                    if (spriteNum == 2) {
                        image = attackUp2;
                    }
                    if (spriteNum == 3) {
                        image = attackUp3;
                    }
                    if (spriteNum == 4) {
                        image = attackUp4;
                    }
                    if (spriteNum == 5) {
                        image = attackUp5;
                    }
                }
                break;
            case "down":
                if (attacking == false) {
                    if (spriteNum == 1) {
                        image = down1;
                    }
                    if (spriteNum == 2) {
                        image = down2;
                    }
                    if (spriteNum == 3) {
                        image = down3;
                    }
                    if (spriteNum == 4) {
                        image = down4;
                    }
                    if (spriteNum == 5) {
                        image = down5;
                    }
                }
                if (attacking == true) {
                    if (spriteNum == 1) {
                        image = attackDown1;
                    }
                    if (spriteNum == 2) {
                        image = attackDown2;
                    }
                    if (spriteNum == 3) {
                        image = attackDown3;
                    }
                    if (spriteNum == 4) {
                        image = attackDown4;
                    }
                    if (spriteNum == 5) {
                        image = attackDown5;
                    }
                }
                break;
            case "left":
                if (attacking == false) {
                    if (spriteNum == 1) {
                        image = left1;
                    }
                    if (spriteNum == 2) {
                        image = left2;
                    }
                    if (spriteNum == 3) {
                        image = left3;
                    }
                    if (spriteNum == 4) {
                        image = left4;
                    }
                    if (spriteNum == 5) {
                        image = left5;
                    }
                }
                if (attacking == true) {
                    if (spriteNum == 1) {
                        image = attackLeft1;
                    }
                    if (spriteNum == 2) {
                        image = attackLeft2;
                    }
                    if (spriteNum == 3) {
                        image = attackLeft3;
                    }
                    if (spriteNum == 4) {
                        image = attackLeft4;
                    }
                    if (spriteNum == 5) {
                        image = attackLeft5;
                    }
                }
                break;
            case "right":
                if (attacking == false) {
                    if (spriteNum == 1) {
                        image = right1;
                    }
                    if (spriteNum == 2) {
                        image = right2;
                    }
                    if (spriteNum == 3) {
                        image = right3;
                    }
                    if (spriteNum == 4) {
                        image = right4;
                    }
                    if (spriteNum == 5) {
                        image = right5;
                    }
                }
                if (attacking == true) {
                    if (spriteNum == 1) {
                        image = attackRight1;
                    }
                    if (spriteNum == 2) {
                        image = attackRight2;
                    }
                    if (spriteNum == 3) {
                        image = attackRight3;
                    }
                    if (spriteNum == 4) {
                        image = attackRight4;
                    }
                    if (spriteNum == 5) {
                        image = attackRight5;
                    }
                }
                break;
        }

        if (invincible == true) {
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.4f));
        }
        g2.drawImage(image, screenX, screenY, null);
        // reseteaza alpha
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
    }
}
