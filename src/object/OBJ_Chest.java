package object;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import entity.Entity;
import main.GamePanel;
import main.UtilityTool;

public class OBJ_Chest extends SuperObject{
	
	GamePanel gp;
	
	public OBJ_Chest(GamePanel gp, int x, int y){
		super(gp);
		this.gp = gp;
		name = "Chest";
		direction = "none";
		worldX = x * gp.tileSize;
		worldY = y * gp.tileSize;
		
		getObjectSprite();
	}
	
	public void getObjectSprite() {
		
		UtilityTool uTool = new UtilityTool();
		
		standing = new BufferedImage[numberOfFrames];
		uTool.readSpriteSheet(standing, "/objects/Chest_sheet", numberOfFrames, spriteWidth, spriteHeight);
	}
	
}
