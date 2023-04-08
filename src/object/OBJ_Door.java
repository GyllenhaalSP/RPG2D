package object;

import main.GamePanel;

import javax.imageio.ImageIO;
import java.io.IOException;
import java.util.Objects;

public class OBJ_Door extends SuperObject{
    GamePanel gp;
    public OBJ_Door(GamePanel gp){
        this.gp = gp;
        name = "Door";
        try{
            image = ImageIO.read((Objects.requireNonNull(getClass().getResourceAsStream("/objects/door.png"))));
            utilityTools.scaleImage(image, gp.TILE_SIZE, gp.TILE_SIZE);
        }catch(IOException e) {
            e.printStackTrace();
        }
        collision = true;
    }
}
