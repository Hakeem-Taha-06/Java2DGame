package object;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import main.GamePanel;
import main.UtilityTool;

public class OBJ_SpeedBoot extends SuperObject {
	
	GamePanel gp;
	
	public OBJ_SpeedBoot(GamePanel gp, int x, int y){
		super(gp);
		this.gp = gp;
		name = "SpeedBoot";
		direction = "none";
		worldX = x * gp.tileSize;
		worldY = y * gp.tileSize;
		
		getObjectSprite();
	}
	
	public void getObjectSprite() {
		
		UtilityTool uTool = new UtilityTool();
		
		standing = new BufferedImage[numberOfFrames];
		uTool.readSpriteSheet(standing, "/objects/Brown_Boot_Sheet", numberOfFrames, spriteWidth, spriteHeight);
	}
	
	
}
