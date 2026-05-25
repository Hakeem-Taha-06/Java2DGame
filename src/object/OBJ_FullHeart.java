package object;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import entity.Entity;
import main.GamePanel;
import main.UtilityTool;

public class OBJ_FullHeart extends SuperObject{
		
	GamePanel gp;
	
	public OBJ_FullHeart(GamePanel gp, int x, int y){
		super(gp);
		this.gp = gp;
		name = "FullHeart";
		direction = "none";
		worldX = x * gp.tileSize;
		worldY = y * gp.tileSize;
		
		solidArea.y = 16;
		solidArea.height = 16;
		solidAreaDefaultY = 16;
		
		getObjectSprite();
	}
	
	public void getObjectSprite() {
		
		UtilityTool uTool = new UtilityTool();
		
		standing = new BufferedImage[numberOfFrames];
		uTool.readSpriteSheet(standing, "/objects/full_heart_sheet", numberOfFrames, spriteWidth, spriteHeight);
	}
	
}

