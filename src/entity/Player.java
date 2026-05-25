package entity;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import main.GamePanel;
import main.KeyHandler;
import main.UtilityTool;
import object.OBJ_Marker;
import object.SuperObject;
import weapons.HitCollider;
import weapons.Sword;

public class Player extends Entity{

	KeyHandler keyH;
	
	public int screenX;
	public int screenY;
	public int heldKeys = 0;
	public int NPCIndex = 999;
	public int enemyIndex = 999;
	
	public HitCollider weapon;
	
	public Player(GamePanel gp, KeyHandler keyH) {
		super(gp);
		
		this.keyH = keyH;
		
		screenX = gp.screenWidth/2 - gp.tileSize/2;
		screenY = gp.screenHeight/2 - gp.tileSize/2;
		
		solidArea = new Rectangle(8, 16, 32, 32);
		solidAreaDefaultX = solidArea.x;
		solidAreaDefaultY = solidArea.y;
		
		setDefaultValues();
		getPlayerSprite();
	}
	
	public void getPlayerSprite() {
			standing = new BufferedImage[numberOfFrames];
			uTool.readSpriteSheet(standing, "/player/Player_idle_sheet", numberOfFrames, spriteWidth, spriteHeight);
			
			up = new BufferedImage[numberOfFrames];
			uTool.readSpriteSheet(up, "/player/Player_up_sheet", numberOfFrames, spriteWidth, spriteHeight);
			
			down = new BufferedImage[numberOfFrames];
			uTool.readSpriteSheet(down, "/player/Player_down_sheet", numberOfFrames, spriteWidth, spriteHeight);
			
			left = new BufferedImage[numberOfFrames];
			uTool.readSpriteSheet(left, "/player/Player_left_sheet", numberOfFrames, spriteWidth, spriteHeight);
			
			right = new BufferedImage[numberOfFrames];
			uTool.readSpriteSheet(right, "/player/Player_right_sheet", numberOfFrames, spriteWidth, spriteHeight);
			
	}
	
	
	public void setDefaultValues() {
		worldX = gp.tileSize * 25;
		worldY = gp.tileSize * 25;
		defaultSpeed = 4;
		speed = defaultSpeed;
		maxHealth = 6;
		health = maxHealth;
		direction = "down";
	}
	
	public void update() {
		boolean isAtHorizontalEdge = worldX < gp.screenWidth/2 - gp.tileSize/2 ||
						             worldX > gp.worldWidth - screenX - gp.tileSize;
		boolean isAtVerticalEdge = worldY < gp.screenHeight/2 - gp.tileSize/2 ||
				   				   worldY > gp.worldHeight - screenY - gp.tileSize;
		int nextX = 0;
		int nextY = 0;
		
		if(!isAtHorizontalEdge) {
			screenX = gp.screenWidth/2 - gp.tileSize/2;
		}
		if(!isAtVerticalEdge) {
			screenY = gp.screenHeight/2 - gp.tileSize/2;
		}
		
		System.out.print(worldX/gp.tileSize);
		System.out.print("-");
		System.out.println(worldY/gp.tileSize);
						   
		if(keyH.upPressed) {
			direction = "up";
			nextY = worldY - speed;
			isMoving = true;
		}
		else if(keyH.downPressed){
			direction = "down";
			nextY = worldY + speed;
			isMoving = true;
		}
		else if(keyH.leftPressed){
			direction = "left";
			nextX = worldX - speed;
			isMoving = true;
		}
		else if(keyH.rightPressed){
			direction = "right";
			nextX = worldX + speed;
			isMoving = true;
		}
		else {
			isMoving = false;
		}
		
		speed = defaultSpeed;
		if(keyH.shiftPressed) {
			speed = defaultSpeed + 2;
		}
		
		
		//CHECK TILE COLLISION
		collidedWithTile = false;
		gp.cChecker.CheckTile(this);
		
		//CHECK OBJECT COLLISION
		collidedWithObject = false;
		int objectIndex = gp.cChecker.checkEntity(this, gp.objects);
		if(objectIndex != 999) {
			collidedWithObject = true;
			gp.objects[18].worldX = gp.objects[objectIndex].worldX;
			gp.objects[18].worldY = gp.objects[objectIndex].worldY;
		}else {
			gp.objects[18].worldX = 99*gp.tileSize;
			gp.objects[18].worldY = 99*gp.tileSize;
		}
		
		//CHECK ENTITY COLLISION
		collidedWithEntity = false;
		NPCIndex = gp.cChecker.checkEntity(this, gp.npcs);
		enemyIndex = gp.cChecker.checkEntity(this, gp.enemies);
		if(NPCIndex != 999) {
			gp.objects[19].worldX = gp.npcs[NPCIndex].worldX;
			gp.objects[19].worldY = gp.npcs[NPCIndex].worldY;
		}else {
			gp.objects[19].worldX = 99*gp.tileSize;
			gp.objects[19].worldY = 99*gp.tileSize;
		}
		
		//INTERACT WITH OBJECTS AND ENTITIES
		if(keyH.ePressed) {
			interactWithObject(objectIndex);
			interactWithNPC(NPCIndex);
		}
		interactWithEnemy(enemyIndex);
		
		if(knockback) {
			switch(knockbackDirection) {
			case"up": worldY -= knockbackSpeed;break;
			case"down": worldY += knockbackSpeed;break;
			case"left": worldX -= knockbackSpeed;break;
			case"right": worldX += knockbackSpeed;break;
			}
			knockbackSpeed -= 2;
			if(knockbackSpeed < 0) {
				knockback = false;
			}
		}else {
			knockbackSpeed = 15;
		}
		
		if(invincible) {
			invincibilityTimer++;
			if(invincibilityTimer >= 60) {
				invincible = false;
				invincibilityTimer = 0;
			}
		}
		
		if(!isAttacking) {
			if(keyH.fPressed) {
				attack();
			}
			facingDirection = direction;
		}
		
		if(isAttacking) {
			attackTimer++;
			
			switch(facingDirection) {
			case"up":   weapon.worldY = worldY - gp.tileSize; weapon.worldX = worldX; break;
			case"down": weapon.worldY = worldY + gp.tileSize; weapon.worldX = worldX; break;
			case"left": weapon.worldX = worldX - gp.tileSize; weapon.worldY = worldY; break;
			case"right":weapon.worldX = worldX + gp.tileSize; weapon.worldY = worldY; break;
			}
			weapon.direction = facingDirection;
			if(attackTimer >= 20) {
				isAttacking = false;
				attackTimer = 0;
				gp.weapons.remove(weapon);
			}
		}
		
		if(isMoving) {
		if(!collidedWithTile && !collidedWithObject && !collidedWithEntity) {
			switch(direction) {
			case"up":
				worldY -= speed;
				if(isAtVerticalEdge) {screenY -= speed;}
				break;
			case"down":
				worldY += speed;
				if(isAtVerticalEdge) {screenY += speed;}
				break;
			case"left":
				worldX -= speed;
				if(isAtHorizontalEdge) {screenX -= speed;}
				break;
			case"right":
				worldX += speed;
				if(isAtHorizontalEdge) {screenX += speed;}
				break;
			}
		}else if(collidedWithTile && !collidedWithObject){
			switch(direction) {
			case"up":
				worldY = (int)(nextY/gp.tileSize + 1) * gp.tileSize - solidArea.y ;
				break;
			case"down":
				worldY = (int)(nextY/gp.tileSize + 1) * gp.tileSize - solidArea.y - solidArea.height - 1;
				break;
			case"left":
				worldX = (int)(nextX/gp.tileSize + 1) * gp.tileSize - solidArea.x;
				break;
			case"right":
				worldX = (int)(nextX/gp.tileSize) * gp.tileSize + solidArea.x - 1;
				break;
			}
		}
		}
		
		
		if(spriteCounter == spriteRefreshRate) {
			spriteNum = (spriteNum + 1)%numberOfFrames;
			spriteCounter = 0;
		}
		spriteCounter++;
	}
	
	public void interactWithObject(int i) {
		if(i != 999) {
			switch(gp.objects[i].name) {
			case"Key":
				gp.objects[i] = null;
				heldKeys++;
				gp.playSFX(7);
				gp.ui.displayMessage("You got a key!");
				break;
			case"Door":
				if(heldKeys > 0) {
					gp.objects[i] = null;
					heldKeys--;
					gp.playSFX(6);
					gp.ui.displayMessage("You used up a key!");
				}else {
					gp.ui.displayMessage("You need a key to open this door!");
				}
				break;
			case"SpeedBoot":
				gp.objects[i] = null;
				defaultSpeed += 2;
				gp.playSFX(6);
				gp.ui.displayMessage("Your speed increased!");
				break;
			case"FullHeart":
				health += 2;
				gp.objects[i] = null;
				gp.ui.displayMessage("You healed a full heart!");
				break;
			case"HalfHeart":
				health++;
				gp.objects[i] = null;
				gp.ui.displayMessage("You healed half a heart");
				break;
			case"Chest":
				gp.objects[i] = null;
				gp.stopMusic();
				gp.playSFX(6);
				gp.ui.displayMessage("YOU WON!");
				gp.ui.gameFinished = true;
				break;
			}
		}
	}
	
	public void interactWithNPC(int i) {
		if(i != 999) {
			gp.gameState = gp.dialogState;
			gp.npcs[i].speak();
		}
	}
	
	public void interactWithEnemy(int i) {
		if(i != 999) {
			if(!invincible) {
				health -= gp.enemies[i].damage;
				knockback(uTool.flipDirection(direction));
				gp.playSFX(8);
				invincible = true;
			}
		}
	}
	
	public void knockback(String direction) {
		knockback = true;
		knockbackDirection = direction;
	}
	
	public void attack() {
		
		weapon = new Sword(gp, worldX, worldY);
		gp.weapons.add(weapon);
		switch(facingDirection) {
		case"up":   weapon.worldY -= gp.tileSize;break;
		case"down": weapon.worldY += gp.tileSize;break;
		case"left": weapon.worldX -= gp.tileSize;break;
		case"right":weapon.worldX += gp.tileSize;break;
		}
		weapon.direction = facingDirection;
		
		isAttacking = true;
	}
	
}








