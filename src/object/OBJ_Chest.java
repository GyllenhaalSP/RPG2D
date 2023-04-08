package object;

import main.GamePanel;

import javax.imageio.ImageIO;
import java.io.IOException;
import java.util.Objects;

public class OBJ_Chest extends SuperObject{
    GamePanel gp;
    public OBJ_Chest(GamePanel gp){
        this.gp = gp;
        name = "Chest";
        try{
            image = ImageIO.read((Objects.requireNonNull(getClass().getResourceAsStream("/objects/chest.png"))));
            utilityTools.scaleImage(image, gp.TILE_SIZE, gp.TILE_SIZE);
        }catch(IOException e) {
            e.printStackTrace();
        }
    }
}