package main;

import object.OBJ_Key;

import java.awt.*;
import java.awt.image.BufferedImage;

public class UI {
    GamePanel gp;
    Font arial40;
    BufferedImage keyImage;


    public UI(GamePanel gp){
        this.gp = gp;
        arial40 = new Font("Arial", Font.PLAIN, 40);
        OBJ_Key key = new OBJ_Key();
        keyImage = key.image;
    }

    public void draw(Graphics2D g2){
        g2.setFont(arial40);
        g2.setColor(Color.WHITE);
        g2.drawImage(keyImage, gp.TILE_SIZE/2, gp.TILE_SIZE/2, gp.TILE_SIZE, gp.TILE_SIZE, null);
        g2.drawString("x "+ gp.player.hasKey, 74, 65);
    }
}
