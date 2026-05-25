package weapons;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import main.CollisionChecker;
import main.GamePanel;

public class AttackCollider {
	
	GamePanel gp;
	CollisionChecker cChecker;
	Rectangle hitbox;
	Rectangle verticalHitbox, horizontalHitbox;
	int worldX, worldY;
	String direction;
	int solidAreaDefaultX, solidAreaDefaultY;
	public static BufferedImage[] staticUp, staticDown, staticLeft, staticRight;
	public static boolean imagesLoaded = false;
	public BufferedImage[] up, down, left, right;
	int spriteCounter;
	int spriteNum;
	int spriteRefreshRate;
	int numberOfFrames;
	
	public boolean collided;
	
	
	public void update() {
		
		//CHECK PLAYER AND OTHER ENTITIES COLLISION
		collided = false;
		int enemyIndex = cChecker.checkHit(this, gp.enemies);
		
		if(collided) {
			//gp.enemies[enemyIndex].getHit();
		}
		
		switch(direction) {
		case"up":
		case"down":
			hitbox = verticalHitbox;
			break;
		case"left":
		case"right":
			hitbox = horizontalHitbox;
			break;	
		}
		solidAreaDefaultX = hitbox.x;
		solidAreaDefaultY = hitbox.y;
		
		if(spriteCounter == spriteRefreshRate) {
			spriteNum = (spriteNum + 1)%numberOfFrames;
			spriteCounter = 0;
		}
		spriteCounter++;
	}
}
