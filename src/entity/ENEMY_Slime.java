package entity;

import java.awt.image.BufferedImage;
import java.util.Random;

import main.GamePanel;
import main.UtilityTool;

public class ENEMY_Slime extends Enemy{
	
	GamePanel gp;
	
	public ENEMY_Slime(GamePanel gp, int x, int y) {
		super(gp);
		this.gp = gp;
		name = "GreenSlime";
		speed = 1;
		maxHealth = 3;
		health = maxHealth;
		damage = 1;
		
		solidArea.x = 12;
		solidArea.width = 24;
		solidAreaDefaultX = solidArea.x;
		solidArea.y = 18;
		solidArea.height = 18;
		solidAreaDefaultY = solidArea.y;
		
		worldX = x * gp.tileSize;
		worldY = y * gp.tileSize;
		
		getEnemySprite();
	}
	
	public void getEnemySprite() {
		UtilityTool uTool = new UtilityTool();
		
		standing = new BufferedImage[numberOfFrames];
		uTool.readSpriteSheet(standing, "/enemies/slime_idle_sheet", numberOfFrames, spriteWidth, spriteHeight);
		
		up = new BufferedImage[numberOfFrames];
		uTool.readSpriteSheet(up, "/enemies/slime_moving_sheet", numberOfFrames, spriteWidth, spriteHeight);
		
		down = new BufferedImage[numberOfFrames];
		uTool.readSpriteSheet(down, "/enemies/slime_moving_sheet", numberOfFrames, spriteWidth, spriteHeight);
		
		left = new BufferedImage[numberOfFrames];
		uTool.readSpriteSheet(left, "/enemies/slime_moving_sheet", numberOfFrames, spriteWidth, spriteHeight);
		
		right = new BufferedImage[numberOfFrames];
		uTool.readSpriteSheet(right, "/enemies/slime_moving_sheet", numberOfFrames, spriteWidth, spriteHeight);
	}
	
	public void setAction() {
		
		actionLockCounter++;
		
		if(actionLockCounter == 48) {
			Random random = new Random();
			
			int i = random.nextInt(100) + 1;
			int j = random.nextInt(100) + 1;
			
			if(j <= 50) {
				isMoving = true;
			}else {
				isMoving = false;
			}
			
			if(i <= 25) {
				direction = "up";
			}else if(i <= 50) {
				direction = "down";
			}else if(i <= 75) {
				direction = "left";
			}else if(i <= 100) {
				direction = "right";
			}
			
			actionLockCounter = 0;
		}
	}
}
