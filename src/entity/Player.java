package entity;

import main.GamePanel;
import main.KeyHandler;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public class Player extends Entity{

    GamePanel gp;
    KeyHandler keyH;

    public final int screenX;
    public final int screenY;

    public Player(GamePanel gp, KeyHandler keyH) {
        this.gp = gp;
        this.keyH = keyH;
        screenX = gp.SCREEN_WIDTH / 2 - (gp.TILE_SIZE / 2);
        screenY = gp.SCREEN_HEIGHT / 2 - (gp.TILE_SIZE / 2);

        solidArea = new Rectangle(8, 15, 32, 32);

        setDefaultValues();
        getPlayerImage();
    }

    public void setDefaultValues(){
        worldX = gp.TILE_SIZE * 23;
        worldY = gp.TILE_SIZE * 21;
        speed = 5;
        direction = "down";
    }

    public void getPlayerImage(){
        try {
            up1 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/player/boy_up_1.png")));
            up2 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/player/boy_up_2.png")));
            down1 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/player/boy_down_1.png")));
            down2 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/player/boy_down_2.png")));
            left1 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/player/boy_left_1.png")));
            left2 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/player/boy_left_2.png")));
            right1 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/player/boy_right_1.png")));
            right2 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/player/boy_right_2.png")));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void update() {
        if(keyH.upPressed || keyH.downPressed || keyH.leftPressed || keyH.rightPressed){
            move();
        }
    }

    public void move(){
        // CHECK TILE COLLISION
        collisionON = false;
        gp.collisionChecker.checkTile(this);

        if (keyH.upPressed){
            direction = "up";
            if (!collisionON){
                worldY -= speed;
            }
        } else if (keyH.downPressed){
            direction = "down";
            if (!collisionON){
                worldY += speed;
            }
        } else if (keyH.leftPressed){
            direction = "left";
            if (!collisionON){
                worldX -= speed;
            }
        } else if (keyH.rightPressed){
            direction = "right";
            if (!collisionON){
                worldX += speed;
            }
        }

        spriteCounter++;
        if(spriteCounter > 15){
            if(spriteNum == 1) spriteNum = 2;
            else if(spriteNum == 2) spriteNum = 1;
            spriteCounter = 0;
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

        g2.drawImage(image, screenX, screenY, gp.TILE_SIZE, gp.TILE_SIZE, null);
        // COLLISION RECTANGLE DEBUG
        //g2.fillRect(screenX + solidArea.x, screenY + solidArea.y, solidArea.width, solidArea.height);
    }
}
