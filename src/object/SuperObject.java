package object;

import main.GamePanel;
import main.UtilityTools;

import java.awt.*;
import java.awt.image.BufferedImage;

public class SuperObject {
    public BufferedImage image;
    public String name;
    public boolean collision = false;
    public int worldX;
    public int worldY;
    public Rectangle solidArea = new Rectangle(0, 0, 48, 48);
    public int solidAreaDefaultX = 0;
    public int solidAreaDefaultY = 0;
    UtilityTools utilityTools = new UtilityTools();

    public void draw(Graphics2D g2, GamePanel gp){
        int screenX = worldX - gp.player.worldX + gp.player.screenX;
        int screenY = worldY - gp.player.worldY + gp.player.screenY;

        if (worldX  + gp.TILE_SIZE > gp.player.worldX - gp.player.screenX
                && worldX - gp.TILE_SIZE < gp.player.worldX + gp.player.screenX
                && worldY + gp.TILE_SIZE > gp.player.worldY - gp.player.screenY
                && worldY - gp.TILE_SIZE < gp.player.worldY + gp.player.screenY) {
            g2.drawImage(image, screenX, screenY, null);
        }


    }
}
