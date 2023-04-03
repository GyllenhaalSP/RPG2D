package tile;

import main.GamePanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;

public class TileManager {
    GamePanel gp;
    public Tile[] tiles;
    public int[][] mapTileNum;

    public TileManager(GamePanel gp){
        this.gp = gp;
        tiles = new Tile[10];
        mapTileNum = new int[gp.MAX_WORLD_COL][gp.MAX_WORLD_ROW];
        getTileImage();
        loadMap("/maps/world01.txt");
    }

    public void getTileImage(){
        try{
            tiles[0] = new Tile();
            tiles[0].image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/tiles/Old Version/grass.png")));

            tiles[1] = new Tile();
            tiles[1].image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/tiles/Old Version/wall.png")));
            tiles[1].collision = true;

            tiles[2] = new Tile();
            tiles[2].image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/tiles/Old Version/water.png")));
            tiles[2].collision = true;

            tiles[3] = new Tile();
            tiles[3].image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/tiles/Old Version/earth.png")));

            tiles[4] = new Tile();
            tiles[4].image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/tiles/Old Version/tree.png")));
            tiles[4].collision = true;

            tiles[5] = new Tile();
            tiles[5].image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/tiles/Old Version/sand.png")));

        }catch(IOException e){
            e.printStackTrace();
        }
    }

    public void loadMap(String filePath){
        try(InputStream is = Objects.requireNonNull(getClass().getResourceAsStream(filePath))){
            BufferedReader br = new BufferedReader(new java.io.InputStreamReader(is));
            int col = 0;
            int row = 0;

            while(col < gp.MAX_WORLD_COL && row < gp.MAX_WORLD_ROW){
                String line = br.readLine();
                while (col < gp.MAX_WORLD_COL){
                    String[] tokens = line.split(" ");
                    int num = Integer.parseInt(tokens[col]);
                    mapTileNum[col][row] = num;
                    col++;
                }
                if(col == gp.MAX_WORLD_COL){
                    col = 0;
                    row++;
                }
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public void drawNormal(Graphics2D g2){
        int col = 0;
        int row = 0;
        int x = 0;
        int y = 0;

        while(col < gp.MAX_SCREEN_COL && row < gp.MAX_SCREEN_ROW){
            int tileNum = mapTileNum[col][row];
            g2.drawImage(tiles[tileNum].image, x, y, gp.TILE_SIZE, gp.TILE_SIZE, null);
            col++;
            x += gp.TILE_SIZE;
            if(col == gp.MAX_SCREEN_COL){
                col = 0;
                x = 0;
                row++;
                y += gp.TILE_SIZE;
            }
        }
    }

    public void drawWorld(Graphics2D g2){
        int worldCol = 0;
        int worldRow = 0;

        while(worldCol < gp.MAX_WORLD_COL && worldRow < gp.MAX_WORLD_ROW){
            int tileNum = mapTileNum[worldCol][worldRow];
            int worldX = worldCol * gp.TILE_SIZE;
            int worldY = worldRow * gp.TILE_SIZE;
            int screenX = worldX - gp.player.worldX + gp.player.screenX;
            int screenY = worldY - gp.player.worldY + gp.player.screenY;

            if (worldX  + gp.TILE_SIZE> gp.player.worldX - gp.player.screenX &&
                    worldX - gp.TILE_SIZE < gp.player.worldX + gp.player.screenX &&
                    worldY + gp.TILE_SIZE > gp.player.worldY - gp.player.screenY &&
                    worldY - gp.TILE_SIZE < gp.player.worldY + gp.player.screenY) {
                g2.drawImage(tiles[tileNum].image, screenX, screenY, gp.TILE_SIZE, gp.TILE_SIZE, null);
            }

            worldCol++;

            if(worldCol == gp.MAX_WORLD_COL){
                worldCol = 0;
                worldRow++;
            }
        }
    }
}
