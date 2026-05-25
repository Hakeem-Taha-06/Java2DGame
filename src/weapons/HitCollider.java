package weapons;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import entity.Entity;
import main.GamePanel;
import main.UtilityTool;

public class HitCollider extends Entity{
	
	GamePanel gp;
	Rectangle verticalBox;
	Rectangle horizontalBox;
	public static BufferedImage[] staticUp;
	public static BufferedImage[] staticDown;
	public static BufferedImage[] staticLeft;
	public static BufferedImage[] staticRight;
	public static boolean imagesLoaded = false; 
	
	public HitCollider(GamePanel gp) {
		super(gp);
		this.gp = gp;
		solidArea = new Rectangle(0, 0 ,gp.tileSize, gp.tileSize);
		solidAreaDefaultX = solidArea.x;
		solidAreaDefaultY = solidArea.y;
	}
	
	public void update() {
		
		//CHECK PLAYER AND OTHER ENTITIES COLLISION
		collidedWithEntity = false;
		int enemyIndex = gp.cChecker.checkEntity(this, gp.enemies);
		
		if(collidedWithEntity) {
			hitEnemy(enemyIndex);
		}
		
		switch(direction) {
		case"up":
		case"down":
			solidArea = verticalBox;
			break;
		case"left":
		case"right":
			solidArea = horizontalBox;
			break;	
		}
		solidAreaDefaultX = solidArea.x;
		solidAreaDefaultY = solidArea.y;
		
		if(spriteCounter == spriteRefreshRate) {
			spriteNum = (spriteNum + 1)%numberOfFrames;
			spriteCounter = 0;
		}
		spriteCounter++;
	}
	
	public void draw(Graphics2D g2) {
		BufferedImage image = null;
			switch(direction){
			case"up":image = up[spriteNum]; break;
			case"down":image = down[spriteNum]; break;
			case"left":image = left[spriteNum]; break;
			case"right":image = right[spriteNum]; break;
			}
			
		int screenX = worldX - gp.player.worldX + gp.player.screenX;
		int screenY = worldY - gp.player.worldY + gp.player.screenY;
			
		if(screenX + gp.tileSize >= 0 && 
		   screenX < gp.screenWidth && 
		   screenY + gp.tileSize >= 0 && 
		   screenY < gp.screenHeight) {
			g2.drawImage(image, screenX, screenY, gp.tileSize, gp.tileSize, null);
		}
			
			
		if(gp.keyH.tPressed) {
			g2.setColor(new Color(255, 0, 0, 200));
			g2.drawRect(screenX + solidArea.x, screenY + solidArea.y, solidArea.width, solidArea.height);
		}
			
	}
	
	public void hitEnemy(int i) {
		if(i != 999) {
			if(!gp.enemies[i].invincible) {
				gp.enemies[i].health--;
				gp.enemies[i].invincible = true;
				
				if(gp.enemies[i].health <= 0) {
					gp.enemies[i] = null;
				}
			}
		}
	}
}
