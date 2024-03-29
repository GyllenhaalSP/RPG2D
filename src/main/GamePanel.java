package main;

import entity.Entity;
import entity.Player;
import object.SuperObject;
import tile.TileManager;

import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel implements Runnable {
    public final int MAX_SCREEN_COL = 16;
    public final int MAX_SCREEN_ROW = 12;

    //WORLD SETTINGS
    public final int MAX_WORLD_COL = 50;
    public final int MAX_WORLD_ROW = 50;

    //SCREEN SETTINGS
    final int ORIGINAL_TILE_SIZE = 16; //16x16 pixels
    final int SCALE = 3;
    public final int TILE_SIZE = ORIGINAL_TILE_SIZE * SCALE; //48x48 pixels
    public final int SCREEN_WIDTH = MAX_SCREEN_COL * TILE_SIZE; //768 pixels
    public final int SCREEN_HEIGHT = MAX_SCREEN_ROW * TILE_SIZE; //576 pixels
    public CollisionChecker collisionChecker = new CollisionChecker(this);
    public AssetSetter assetSetter = new AssetSetter(this);
    public UI ui = new UI(this);

    // GAME STATE
    public int gameState = 0;
    public final int titleState = 0;
    public final int playState = 1;
    public final int pauseState = 2;
    public final int dialogueState = 3;

    public SuperObject[] obj = new SuperObject[10];
    public Entity[] npcs = new Entity[10];

    // FPS
    int fps = 60;

    //SYSTEM
    TileManager tileManager = new TileManager(this);
    public KeyHandler keyHandler = new KeyHandler(this);

    // ENTITY AND OBJECT
    public Player player = new Player(this, keyHandler);
    Sound music = new Sound();
    Sound effect = new Sound();
    Thread gameThread;

    public GamePanel() {
        this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
        this.setBackground(Color.BLACK);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyHandler);
        this.setFocusable(true);
    }

    public void setupGame() {
        assetSetter.setObject();
        assetSetter.setNPC();
        //playMusic(0);
        gameState = titleState;
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

    public void delta() {
        //DELTA OR ACCUMULATOR METHOD
        double nanoSecond = 1000000000;
        double drawInterval = nanoSecond / fps; //0.01666
        double delta = 0;
        long lastTime = System.nanoTime();
        long timer = 0;
        long currentTime;
        int drawCount = 0;

        while (gameThread != null) {
            currentTime = System.nanoTime();
            delta += (currentTime - lastTime) / drawInterval;
            timer += (currentTime - lastTime);
            lastTime = currentTime;

            if (delta >= 1) {
                // 1 UPDATE: update information such as character position
                update();
                // 2 RENDER: render the screen with the updated information
                repaint();
                delta--;
                drawCount++;
            }

            if (timer >= nanoSecond) {
                //System.out.println("FPS: " + drawCount);
                drawCount = 0;
                timer = 0;
            }
        }
    }

    public void update() {
        if (gameState == playState) {
            // PLAYER
            player.update();
            // NPC
            for (Entity npc : npcs) {
                if (npc != null) {
                    npc.update();
                }
            }
        }

        if (gameState == pauseState) {
            //pause menu
        }
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        //DEBUG
        long drawStart = 0;
        if (keyHandler.checkDrawTime) {
            drawStart = System.nanoTime();
        }

        // TITLE SCREEN
        if(gameState == titleState){
            ui.draw(g2);
        } else { // OTHERS
            // TILES
            tileManager.drawWorld(g2);

            // OBJECTS
            for (SuperObject superObject : obj) {
                if (superObject != null) {
                    superObject.draw(g2, this);
                }
            }

            // NPC
            for (Entity npc : npcs) {
                if (npc != null) {
                    npc.draw(g2);
                }
            }

            //PLAYER
            player.draw(g2);

            //UI
            ui.draw(g2);
        }

        // DEBUG
        if (keyHandler.checkDrawTime) {
            long drawEnd = System.nanoTime();
            long passed = drawEnd - drawStart;
            g2.setColor(Color.WHITE);
            g2.drawString("Draw Time: " + passed, 10, 400);
            System.out.println("Draw Time: " + passed);
        }

        g2.dispose();
    }

    public void playMusic(int i) {
        music.setFile(i);
        music.play();
        music.loop();
    }

    public void stopMusic() {
        music.stop();
    }

    public void playSoundEffect(int i) {
        effect.setFile(i);
        effect.play();
    }
}
