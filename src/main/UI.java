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
    public int commandNumber = 0;
    public int titleScreenState = 0; // 0: the first screen, 1: the second screen

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
        //g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        g2.setColor(Color.WHITE);

        //TITLE STATE
        if(gp.gameState == gp.titleState){
            drawTitleScreen();
        }

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

    public void drawTitleScreen(){
        if(titleScreenState == 0){
            g2.setColor(new Color(70, 120, 80));
            g2.fillRect(0, 0, gp.SCREEN_WIDTH, gp.SCREEN_HEIGHT);

            //TITLE NAME
            g2.setFont(g2.getFont().deriveFont(Font.BOLD, 94F));
            String text = "BLUE BOY ADVENTURE";
            int x = getXForCenteredText(text);
            int y = gp.TILE_SIZE * 3;

            // SHADOW
            g2.setColor(Color.GRAY);
            g2.drawString(text, x+5, y+5);

            // MAIN COLOR
            g2.setColor(Color.WHITE);
            g2.drawString(text, x, y);

            // BLUE BOY IMAGE
            x = gp.SCREEN_WIDTH / 2 - (gp.TILE_SIZE*2)/2;
            y += gp.TILE_SIZE * 2;
            g2.drawImage(gp.player.down1, x, y, gp.TILE_SIZE*2, gp.TILE_SIZE*2, null);

            // MENU
            g2.setFont(g2.getFont().deriveFont(Font.BOLD, 48F));
            text = "NEW GAME";
            x = getXForCenteredText(text);
            y += gp.TILE_SIZE * 4;
            g2.drawString(text, x, y);
            if(commandNumber == 0){
                g2.drawString(">", x - gp.TILE_SIZE, y);
            }

            text = "LOAD GAME";
            x = getXForCenteredText(text);
            y += gp.TILE_SIZE;
            g2.drawString(text, x, y);

            if(commandNumber == 1){
                g2.drawString(">", x - gp.TILE_SIZE, y);
            }

            text = "QUIT";
            x = getXForCenteredText(text);
            y += gp.TILE_SIZE;
            g2.drawString(text, x, y);

            if(commandNumber == 2){
                g2.drawString(">", x - gp.TILE_SIZE, y);
            }

        }else if (titleScreenState == 1){

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
