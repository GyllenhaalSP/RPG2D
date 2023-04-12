package entity;

import main.GamePanel;
import main.KeyHandler;
import main.UtilityTools;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public class Player extends Entity {

    public final int screenX;
    public final int screenY;
    KeyHandler keyH;

    public Player(GamePanel gp, KeyHandler keyH) {
        super(gp);
        this.keyH = keyH;
        screenX = gp.SCREEN_WIDTH / 2 - (gp.TILE_SIZE / 2);
        screenY = gp.SCREEN_HEIGHT / 2 - (gp.TILE_SIZE / 2);

        solidArea = new Rectangle(8, 15, 32, 32);
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;

        setDefaultValues();
        getPlayerImage();
    }

    public void setDefaultValues() {
        worldX = gp.TILE_SIZE * 23;
        worldY = gp.TILE_SIZE * 21;
        speed = 5;
        direction = "down";
    }

    public void getPlayerImage() {
        up1 = setup("/player/boy_up_1");
        up2 = setup("/player/boy_up_2");
        down1 = setup("/player/boy_down_1");
        down2 = setup("/player/boy_down_2");
        left1 = setup("/player/boy_left_1");
        left2 = setup("/player/boy_left_2");
        right1 = setup("/player/boy_right_1");
        right2 = setup("/player/boy_right_2");
    }

    public void update() {
        if (keyH.upPressed || keyH.downPressed || keyH.leftPressed || keyH.rightPressed) {
            move();
        }
    }

    public void move() {
        // CHECK TILE COLLISION
        collisionON = false;
        gp.collisionChecker.checkTile(this);

        //CHECK OBJECT COLLISION
        int objIndex = gp.collisionChecker.checkObject(this, true);
        pickUpObject(objIndex);

        // CHECK NPC COLLISION
        int npcIndex = gp.collisionChecker.checkEntity(this, gp.npcs);
        interactNPC(npcIndex);

        //IF COLLISION IS FALSE, PLAYER CAN MOVE
        if (keyH.upPressed) {
            direction = "up";
            if (!collisionON) {
                worldY -= speed;
            }
        } else if (keyH.downPressed) {
            direction = "down";
            if (!collisionON) {
                worldY += speed;
            }
        } else if (keyH.leftPressed) {
            direction = "left";
            if (!collisionON) {
                worldX -= speed;
            }
        } else if (keyH.rightPressed) {
            direction = "right";
            if (!collisionON) {
                worldX += speed;
            }
        }

        spriteCounter++;
        if (spriteCounter > 15) {
            if (spriteNum == 1) spriteNum = 2;
            else if (spriteNum == 2) spriteNum = 1;
            spriteCounter = 0;
        }
    }

    public void pickUpObject(int index) {
        if (index != 999) {

        }
    }

    public void interactNPC(int index) {
        if (index != 999) {
            System.out.println("You are hitting an NPC!");
        }
    }

    public void draw(Graphics2D g2) {
        BufferedImage image = null;

        switch (direction) {
            case "up" -> {
                if (spriteNum == 1) image = up1;
                if (spriteNum == 2) image = up2;
            }
            case "down" -> {
                if (spriteNum == 1) image = down1;
                if (spriteNum == 2) image = down2;
            }
            case "left" -> {
                if (spriteNum == 1) image = left1;
                if (spriteNum == 2) image = left2;
            }
            case "right" -> {
                if (spriteNum == 1) image = right1;
                if (spriteNum == 2) image = right2;
            }
        }

        g2.drawImage(image, screenX, screenY, null);
        // COLLISION RECTANGLE DEBUG
        //g2.fillRect(screenX + solidArea.x, screenY + solidArea.y, solidArea.width, solidArea.height);
    }
}
