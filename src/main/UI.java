package main;

import java.awt.*;
import java.text.DecimalFormat;

public class UI {
    public boolean messageOn = false;
    public String message = "";
    public boolean gameFinished = false;
    GamePanel gp;
    Graphics2D g2;
    Font arial40;
    Font arial70B;
    int messageTimer = 0;
    double playTime;
    DecimalFormat dFormat = new DecimalFormat("#0.00");

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

        if (gp.gameState == gp.playState) {
            //playState stuff
        }

        if (gp.gameState == gp.pauseState) {
            drawPauseScreen();
        }
    }

    public void drawPauseScreen() {
        g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 80F));
        String text = "PAUSED";
        int x = getXForCenteredText(text);
        int y = gp.SCREEN_HEIGHT / 2;

        g2.drawString(text, x, y);

    }

    public int getXForCenteredText(String text) {
        int length = (int) g2.getFontMetrics().getStringBounds(text, g2).getWidth();
        return gp.SCREEN_WIDTH / 2 - length / 2;
    }

}
