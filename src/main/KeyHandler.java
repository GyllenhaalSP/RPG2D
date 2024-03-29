package main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener {

    public boolean upPressed;
    public boolean downPressed;
    public boolean leftPressed;
    public boolean rightPressed;
    public boolean spacePressed;
    GamePanel gp;

    // DEBUG
    boolean checkDrawTime = false;

    public KeyHandler(GamePanel gp) {
        this.gp = gp;
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();

        // TITLE STATE
        if (gp.gameState == gp.titleState){

            if (gp.ui.titleScreenState == 0) {
                if (code == KeyEvent.VK_UP || code == KeyEvent.VK_W) {
                    gp.ui.commandNumber --;
                    if(gp.ui.commandNumber < 0) gp.ui.commandNumber = 2;
                }
                if (code == KeyEvent.VK_DOWN || code == KeyEvent.VK_S) {
                    gp.ui.commandNumber ++;
                    if(gp.ui.commandNumber > 2) gp.ui.commandNumber = 0;
                }
                if(code == KeyEvent.VK_ENTER){
                    if(gp.ui.commandNumber == 0){
                        gp.gameState = gp.playState;
                        gp.playMusic(0);
                    }
                    if(gp.ui.commandNumber == 1){
                        //Add later
                    }
                    if(gp.ui.commandNumber == 2){
                        System.exit(0);
                    }
                }
            }
        }

        //PLAY STATE
        if (gp.gameState == gp.playState){
            if (code == KeyEvent.VK_UP || code == KeyEvent.VK_W) {
                upPressed = true;
                //System.out.println("UP");
            }

            if (code == KeyEvent.VK_DOWN || code == KeyEvent.VK_S) {
                downPressed = true;
                //System.out.println("DOWN");
            }

            if (code == KeyEvent.VK_LEFT || code == KeyEvent.VK_A) {
                leftPressed = true;
                //System.out.println("LEFT");
            }

            if (code == KeyEvent.VK_RIGHT || code == KeyEvent.VK_D) {
                rightPressed = true;
                //System.out.println("RIGHT");
            }
            if (code == KeyEvent.VK_P) {
                gp.gameState = gp.pauseState;
            }
            if(code == KeyEvent.VK_SPACE){
                spacePressed = true;
            }
        }

        // PAUSE
        else if(gp.gameState == gp.pauseState){
            if (code == KeyEvent.VK_P) {
                gp.gameState = gp.playState;
            }
        }

        // DIALOGUE STATE
        else if(gp.gameState == gp.dialogueState){
            if (code == KeyEvent.VK_SPACE) {
                gp.gameState = gp.playState;
            }
        }

        // DEBUG
        if (code == KeyEvent.VK_T) {
            checkDrawTime = !checkDrawTime;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int code = e.getKeyCode();
        if (code == KeyEvent.VK_UP || code == KeyEvent.VK_W) {
            upPressed = false;
        }

        if (code == KeyEvent.VK_DOWN || code == KeyEvent.VK_S) {
            downPressed = false;
        }

        if (code == KeyEvent.VK_LEFT || code == KeyEvent.VK_A) {
            leftPressed = false;
        }

        if (code == KeyEvent.VK_RIGHT || code == KeyEvent.VK_D) {
            rightPressed = false;
        }
    }
}
