package main;

import entity.NPC_OldMan;

public class AssetSetter {
    GamePanel gp;

    public AssetSetter(GamePanel gp) {
        this.gp = gp;
    }

    public void setObject() {
    }

    public void setNPC() {
        gp.npcs[0] = new NPC_OldMan(gp);
        gp.npcs[0].worldX = gp.TILE_SIZE*21;
        gp.npcs[0].worldY = gp.TILE_SIZE*21;
    }
}
