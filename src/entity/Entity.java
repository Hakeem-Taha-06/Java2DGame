package entity;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import main.GamePanel;
import main.UtilityTool;

public class Entity {
	
	GamePanel gp;
	UtilityTool uTool;
	
	public int worldX, worldY;
	public int speed, defaultSpeed;
	public String name;
	
	public BufferedImage[] up,
						 down,
						 left,
						 right,
						 standing;
	
	public int spriteCounter;
	public int spriteNum = 1;
	public int numberOfFrames = 4;
	public int spriteRefreshRate = 12;
	public int spriteWidth = 32*10;
	public int spriteHeight = 32*10;
	
	public String direction = "down";
	public String facingDirection = "down";
	public String currentFacingDirection = "down";
	public boolean isMoving = false;
	public int actionLockCounter = 0;
	
	public Rectangle solidArea;
	public int solidAreaDefaultX;
	public int solidAreaDefaultY;
	public boolean collidedWithTile = false;
	public boolean collidedWithObject = false;
	public boolean collidedWithEntity = false;
	public boolean collidedWithPlayer = false;
	public boolean isSolid = true;
	
	String dialogs[] = new String[20];
	int dialogIndex = 0;
	
	//STATS
	public int maxHealth;
	public int health;
	public int damage;
	
	//COMBAT 
	public boolean knockback = false;
	public String knockbackDirection = "down";
	public int knockbackSpeed = 0;
	public boolean invincible = false;
	public int invincibilityTimer = 0;
	public boolean isAttacking = false;
	public int attackTimer = 0;
	public int attackCooldown = 0;
	public boolean isAlive = true;
	public boolean isDying = false;
	public int dyingTimer = 0;
	
	public Entity(GamePanel gp) {
		this.gp = gp;
		uTool = new UtilityTool();
		
		solidArea = new Rectangle(0, 0 ,gp.tileSize, gp.tileSize);
		solidAreaDefaultX = solidArea.x;
		solidAreaDefaultY = solidArea.y;
	}
	
	public void setAction() {}
	
	public void speak() {
		
		if(dialogs[dialogIndex] != null) {
			gp.ui.currentDialog = dialogs[dialogIndex];
			dialogIndex++;
			}else {
				gp.gameState = gp.playState;
				dialogIndex = 18;
			}
			
			switch(gp.player.direction) {
			case"up": direction = "down"; break;
			case"down": direction = "up"; break;
			case"left": direction = "right"; break;
			case"right": direction = "left"; break;
			}
	}
	
	public void update() {
		
		setAction();
		
		//CHECK TILE COLLISION
		collidedWithTile = false;
		gp.cChecker.CheckTile(this);
		
		//CHECK PLAYER AND OTHER ENTITIES COLLISION
		collidedWithEntity = false;
		gp.cChecker.checkPlayer(this);
		gp.cChecker.checkEntity(this, gp.npcs);
		gp.cChecker.checkEntity(this, gp.enemies);
		
		
		if(isMoving) {
			if(!collidedWithTile && !collidedWithEntity) {
				switch(direction) {
				case"up":
					worldY -= speed;
					break;
				case"down":
					worldY += speed;
					break;
				case"left":
					worldX -= speed;
					break;
				case"right":
					worldX += speed;
					break;
				}
			}
		}
		
		spriteCounter++;
		if(spriteCounter == spriteRefreshRate) {
			spriteNum = (spriteNum + 1)%numberOfFrames;
			spriteCounter = 0;
		}
	}
	
	public void draw(Graphics2D g2) {
		BufferedImage image = null;
		if(isMoving) {
			switch(facingDirection){
			case"up":image = up[spriteNum]; break;
			case"down":image = down[spriteNum]; break;
			case"left":image = left[spriteNum]; break;
			case"right":image = right[spriteNum]; break;
			}
		}else {
			if(gp.gameState == gp.dialogState) {
				switch(facingDirection){
				case"up":image = up[0]; break;
				case"down":image = down[0]; break;
				case"left":image = left[0]; break;
				case"right":image = right[0]; break;
				}
			}else {
			image = standing[spriteNum];
			}
		}
		
		int screenX = worldX - gp.player.worldX + gp.player.screenX;
		int screenY = worldY - gp.player.worldY + gp.player.screenY;
		
		if(screenX + gp.tileSize >= 0 && 
		   screenX < gp.screenWidth && 
		   screenY + gp.tileSize >= 0 && 
		   screenY < gp.screenHeight) {
		
		if(invincible) {
			g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.3f));
		}
		g2.drawImage(image, screenX, screenY, gp.tileSize, gp.tileSize, null);
		
		g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f));
		}
		
		if(gp.keyH.tPressed) {
		g2.setColor(new Color(255, 0, 0, 200));
		g2.drawRect(screenX + solidArea.x, screenY + solidArea.y, solidArea.width, solidArea.height);
		}
		
	}
}
