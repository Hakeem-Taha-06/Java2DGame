package object;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import main.GamePanel;
import main.UtilityTool;

public class OBJ_Marker extends SuperObject {
	GamePanel gp;
	
	public OBJ_Marker(GamePanel gp, int x, int y){
		super(gp);
		this.gp = gp;
		name = "Marker";
		direction = "none";
		isSolid = false;
		worldX = x * gp.tileSize;
		worldY = y * gp.tileSize;
		
		getObjectSprite();
	}
	
	public void getObjectSprite() {
		
		UtilityTool uTool = new UtilityTool();
		
		standing = new BufferedImage[numberOfFrames];
		uTool.readSpriteSheet(standing, "/objects/Marker_Sheet", numberOfFrames, spriteWidth, spriteHeight);
	}
}
