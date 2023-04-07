package main;

import object.OBJ_Key;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.text.DecimalFormat;

public class UI {
    GamePanel gp;
    Font arial40;
    Font arial70B;
    BufferedImage keyImage;
    public boolean messageOn = false;
    public String message = "";
    int messageTimer = 0;
    public boolean gameFinished = false;

    double playTime;
    DecimalFormat dFormat = new DecimalFormat("#0.00");

    public UI(GamePanel gp){
        this.gp = gp;
        arial40 = new Font("Arial", Font.PLAIN, 40);
        arial70B = new Font("Arial", Font.BOLD, 70);
        OBJ_Key key = new OBJ_Key();
        keyImage = key.image;
    }

    public void showMessage(String text) {
        message = text;
        messageOn = true;
    }

    public void draw(Graphics2D g2) {
        if (gameFinished) {
            g2.setFont(arial40);
            g2.setColor(Color.WHITE);

            String text;
            int textLength;
            int x;
            int y;

            text = "YOU FOUND THE TREASURE!";
            textLength = (int) g2.getFontMetrics().getStringBounds(text, g2).getWidth();
            x = gp.SCREEN_WIDTH/2 - textLength/2;
            y = gp.SCREEN_HEIGHT/2 - (gp.TILE_SIZE * 3);
            g2.drawString(text, x, y);

            text = "YOUR TIME IS: " + dFormat.format(playTime) + " SECONDS!";
            textLength = (int) g2.getFontMetrics().getStringBounds(text, g2).getWidth();
            x = gp.SCREEN_WIDTH/2 - textLength/2;
            y = gp.SCREEN_HEIGHT/2 - (gp.TILE_SIZE * 4);
            g2.drawString(text, x, y);

            g2.setFont(arial70B);
            g2.setColor(Color.YELLOW);
            text = "CONGRATULATIONS!";
            textLength = (int) g2.getFontMetrics().getStringBounds(text, g2).getWidth();
            x = gp.SCREEN_WIDTH/2 - textLength/2;
            y = gp.SCREEN_HEIGHT/2 + (gp.TILE_SIZE * 2);
            g2.drawString(text, x, y);

            gp.gameThread = null;

        } else {
            g2.setFont(arial40);
            g2.setColor(Color.WHITE);
            g2.drawImage(keyImage, gp.TILE_SIZE / 2, gp.TILE_SIZE / 2, gp.TILE_SIZE, gp.TILE_SIZE, null);
            g2.drawString("x " + gp.player.hasKey, 74, 65);

            // TIME
            playTime += (double) 1/60;
            g2.drawString("Time: " + dFormat.format(playTime) + " secs", gp.TILE_SIZE * 9, 65);

            // MESSAGE
            if (messageOn) {
                g2.setFont(g2.getFont().deriveFont(30f));
                g2.setColor(Color.WHITE);
                g2.drawString(message, gp.TILE_SIZE / 2, gp.TILE_SIZE * 11);
                messageTimer++;
                if (messageTimer > 120) {
                    messageOn = false;
                    messageTimer = 0;
                }
            }

        }
    }
}
