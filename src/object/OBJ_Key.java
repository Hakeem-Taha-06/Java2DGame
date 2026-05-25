package object;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import main.GamePanel;
import main.UtilityTool;

public class OBJ_Key extends SuperObject{
	GamePanel gp;
	
	public OBJ_Key(GamePanel gp, int x, int y){
		super(gp);
		this.gp = gp;
		name = "Key";
		direction = "none";
		worldX = x * gp.tileSize;
		worldY = y * gp.tileSize;
		
		getObjectSprite();
	}
	
	public void getObjectSprite() {
		
		UtilityTool uTool = new UtilityTool();
		
		standing = new BufferedImage[numberOfFrames];
		uTool.readSpriteSheet(standing, "/objects/Key_Sheet", numberOfFrames, spriteWidth, spriteHeight);
	}
}
