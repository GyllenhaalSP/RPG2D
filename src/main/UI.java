package main;

import java.awt.*;

public class UI {
    GamePanel gp;
    Graphics2D g2;
    Font arial40;
    Font arial70B;
    public boolean messageOn = false;
    public String message = "";
    int messageTimer = 0;
    public boolean gameFinished = false;
    public String currentDialogue = "";

    public UI(GamePanel gp) {
        this.gp = gp;
        arial40 = new Font("Arial", Font.PLAIN, 40);
        arial70B = new Font("Arial", Font.BOLD, 70);
    }

    public void showMessage(String text) {
        message = text;
        messageOn = true;
    }

    public void draw(Graphics2D g2) {
        this.g2 = g2;
        g2.setFont(arial40);
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

        g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 28));
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
