package entity;

import main.GamePanel;

public class Enemy extends Entity{
	
	GamePanel gp;
	
	public Enemy(GamePanel gp) {
		super(gp);
		this.gp = gp;
	}

	public void update() {
		
		setAction();
		
		//CHECK TILE COLLISION
		collidedWithTile = false;
		gp.cChecker.CheckTile(this);
		
		//CHECK PLAYER AND OTHER ENTITIES COLLISION
		collidedWithEntity = false;
		collidedWithPlayer = false;
		gp.cChecker.checkPlayer(this);
		gp.cChecker.checkEntity(this, gp.npcs);
		gp.cChecker.checkEntity(this, gp.enemies);
		
		if(collidedWithPlayer) {
			gp.player.direction = uTool.flipDirection(direction);
			gp.player.interactWithEnemy(gp.player.enemyIndex);
		}
		
		if(invincible) {
			invincibilityTimer++;
			if(invincibilityTimer >= 20) {
				invincible = false;
				invincibilityTimer = 0;
			}
		}
		
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
	
	
}
