package main;

import entity.Player;

import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel implements Runnable {
    //SCREEN SETTINGS
    final int ORIGINAL_TILE_SIZE = 16; //16x16 pixels
    final int SCALE = 3;

    public final int TILE_SIZE = ORIGINAL_TILE_SIZE * SCALE; //48x48 pixels
    final int MAX_SCREEN_COL = 16;
    final int MAX_SCREEN_ROW = 12;
    final int SCREEN_WIDTH = MAX_SCREEN_COL * TILE_SIZE; //768 pixels
    final int SCREEN_HEIGHT = MAX_SCREEN_ROW * TILE_SIZE; //576 pixels
    // FPS
    int fps = 60;

    KeyHandler keyHandler = new KeyHandler();
    Thread gameThread;
    Player player = new Player(this, keyHandler);

    // Set Player's default position
    int playerX = 100;
    int playerY = 100;
    int playerSpeed = 5;

    public GamePanel() {
        this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
        this.setBackground(Color.BLACK);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyHandler);
        this.setFocusable(true);
    }

    public void startGameThread() {
        gameThread = new Thread(this);
        gameThread.start();
    }

    @Override
    public void run() {
        delta();
        //thread();
    }

/*    public void thread(){
        double nanoSecond = 1000000000;
        double miliSecond = 1000000;
        //THREAD METHOD
        double drawInterval = nanoSecond / fps; //0.01666
        double nextDrawTime = System.nanoTime() + drawInterval;

        while (gameThread != null) {
            // 1 UPDATE: update information such as character position
            update();
            // 2 RENDER: render the screen with the updated information
            repaint();

            try {
                double remainingTime = nextDrawTime - System.nanoTime();
                remainingTime /= miliSecond;

                if (remainingTime < 0) {
                    remainingTime = 0;
                }

                Thread.sleep((long) remainingTime);

                nextDrawTime += drawInterval;

            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }*/

    public void delta(){
        //DELTA OR ACCUMULATOR METHOD
        double nanoSecond = 1000000000;
        double drawInterval = nanoSecond / fps; //0.01666
        double delta = 0;
        long lastTime = System.nanoTime();
        long timer = 0;
        long currentTime;
        int drawCount = 0;

        while(gameThread != null){
            currentTime = System.nanoTime();
            delta += (currentTime - lastTime) / drawInterval;
            timer += (currentTime - lastTime);
            lastTime = currentTime;

            if(delta >= 1){
                // 1 UPDATE: update information such as character position
                update();
                // 2 RENDER: render the screen with the updated information
                repaint();
                delta--;
                drawCount++;
            }

            if (timer >= nanoSecond) {
                System.out.println("FPS: " + drawCount);
                drawCount = 0;
                timer = 0;
            }
        }
    }

    public void update() {
        player.update();
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        player.draw(g2);
        g2.dispose();
    }
}
