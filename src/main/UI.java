package main;

import java.awt.*;
import java.io.IOException;
import java.io.InputStream;

public class UI {
    GamePanel gp;
    Graphics2D g2;
    Font maruMonica;
    Font purisa;
    public boolean messageOn = false;
    public String message = "";
    int messageTimer = 0;
    public boolean gameFinished = false;
    public String currentDialogue = "";

    public UI(GamePanel gp) {
        this.gp = gp;
        try {
            InputStream is = getClass().getResourceAsStream("/font/x12y16pxMaruMonica.ttf");
            maruMonica = Font.createFont(Font.TRUETYPE_FONT, is);
            is = getClass().getResourceAsStream("/font/Purisa Bold.ttf");
            purisa = Font.createFont(Font.TRUETYPE_FONT, is);
        } catch (FontFormatException | IOException | NullPointerException e) {
            e.printStackTrace();
            maruMonica = Font.getFont("Arial");
            purisa = Font.getFont("Arial");
        }
    }

    public void showMessage(String text) {
        message = text;
        messageOn = true;
    }

    public void draw(Graphics2D g2) {
        this.g2 = g2;
        g2.setFont(maruMonica);
        //g2.setFont(purisa);
        g2.setColor(Color.WHITE);

        //PLAY STATE
        if (gp.gameState == gp.playState) {
            //playState stuff
        }
        //PAUSE STATE
        if (gp.gameState == gp.pauseState) {
            drawPauseScreen();
        }
        //DIALOGUE STATE
        if(gp.gameState == gp.dialogueState) {
            drawDialogueScreen();
        }
    }

    public void drawPauseScreen() {
        g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 80F));
        String text = "PAUSED";
        int x = getXForCenteredText(text);
        int y = gp.SCREEN_HEIGHT / 2;

        g2.drawString(text, x, y);

    }

    private void drawDialogueScreen() {
        //WINDOW
        int x = gp.TILE_SIZE*2;
        int y = gp.TILE_SIZE/2;
        int width = gp.SCREEN_WIDTH - (gp.TILE_SIZE*4);
        int height = gp.TILE_SIZE*4;

        drawSubWindow(x, y, width, height);

        g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 32));
        x += gp.TILE_SIZE/2;
        y += gp.TILE_SIZE;

        for(String line : currentDialogue.split("\n")){
            g2.drawString(line, x, y);
            y += gp.TILE_SIZE;
        }
    }

    public void drawSubWindow(int x, int y, int width, int height) {
        Color c = new Color(0,0,0, 200);
        g2.setColor(c);
        g2.fillRoundRect(x, y, width, height, 35, 35);

        c = new Color(255,255,255);
        g2.setColor(c);
        g2.setStroke(new BasicStroke(5));
        g2.drawRoundRect(x+5, y+5, width-10, height-10, 25, 25);
    }

    public int getXForCenteredText(String text) {
        int length = (int) g2.getFontMetrics().getStringBounds(text, g2).getWidth();
        return gp.SCREEN_WIDTH / 2 - length / 2;
    }

}
