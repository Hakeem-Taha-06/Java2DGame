package object;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import main.GamePanel;
import main.UtilityTool;

public class OBJ_Heart extends SuperObject{
	
	GamePanel gp;
	
	public OBJ_Heart(GamePanel gp){
		super(gp);
		this.gp = gp;
		name = "Chest";
		direction = "none";
		isSolid = false;
		
		getObjectSprite();
	}
	
	public void getObjectSprite() {
		
		UtilityTool uTool = new UtilityTool();
		
		standing = new BufferedImage[3];
		uTool.readSpriteSheet(standing, "/objects/Heart_Sheet", 3, spriteWidth, spriteHeight);
	}

}
