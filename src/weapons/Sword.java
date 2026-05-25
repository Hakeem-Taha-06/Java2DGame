package weapons;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import main.GamePanel;
import main.UtilityTool;

public class Sword extends HitCollider{
	
	UtilityTool uTool = new UtilityTool();
	GamePanel gp;
	
	
	public Sword(GamePanel gp, int x, int y) {
		super(gp);
		this.gp = gp;
		
		worldX = x;
		worldY = y;
		isSolid = false;
		
		horizontalBox = new Rectangle(0, 18, gp.tileSize, 12);
		verticalBox = new Rectangle(18, 0, 12, gp.tileSize);
		
		getWeaponSprite();
		
		up = staticUp;
		down = staticDown;
		left = staticLeft;
		right = staticRight;
	}
	
	public void getWeaponSprite() {
		
		if(imagesLoaded) {return;}
		
		staticUp = new BufferedImage[numberOfFrames];
		staticDown = new BufferedImage[numberOfFrames];
		staticLeft = new BufferedImage[numberOfFrames];
		staticRight = new BufferedImage[numberOfFrames];
		
		//change later to uTool.readSpriteSheet()
		for(int i = 0; i < numberOfFrames; i++) {
			try {
				staticUp[i] = ImageIO.read(getClass().getResourceAsStream("/weapons/sword_up.png"));
				staticDown[i] = ImageIO.read(getClass().getResourceAsStream("/weapons/sword_down.png"));
				staticLeft[i] = ImageIO.read(getClass().getResourceAsStream("/weapons/sword_left.png"));
				staticRight[i] = ImageIO.read(getClass().getResourceAsStream("/weapons/sword_right.png"));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		imagesLoaded = true;
		
	}
}
