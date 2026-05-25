package object;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import main.GamePanel;
import main.UtilityTool;

public class OBJ_Door extends SuperObject {
	GamePanel gp;
	
	public OBJ_Door(GamePanel gp, int x, int y){
		super(gp);
		this.gp = gp;
		name = "Door";
		direction = "none";
		worldX = x * gp.tileSize;
		worldY = y * gp.tileSize;
		
		solidArea.y = 32;
		solidArea.height = 16;
		solidAreaDefaultY = 32;
		
		getObjectSprite();
	}
	
	public void getObjectSprite() {
		
		UtilityTool uTool = new UtilityTool();
		
		standing = new BufferedImage[numberOfFrames];
		uTool.readSpriteSheet(standing, "/objects/Door_Sheet", numberOfFrames, spriteWidth, spriteHeight);
	}

}
