package main;

import entity.Entity;
import object.SuperObject;
import weapons.AttackCollider;

public class CollisionChecker {
	
	GamePanel gp;
	
	public CollisionChecker(GamePanel gp) {
		this.gp = gp;
	}
	
	public void CheckTile(Entity entity) {
		int entityLeftWorldX = entity.worldX + entity.solidArea.x;
		int entityRightWorldX = entity.worldX + entity.solidArea.x + entity.solidArea.width;
		int entityTopWorldY = entity.worldY + entity.solidArea.y;
		int entityBottomWorldY = entity.worldY + entity.solidArea.y + entity.solidArea.height;
		
		int entityLeftCol = entityLeftWorldX/gp.tileSize;
		int entityRightCol = entityRightWorldX/gp.tileSize;
		int entityTopRow = entityTopWorldY/gp.tileSize;
		int entityBottomRow = entityBottomWorldY/gp.tileSize;
		
		int tileNum1 = 0, tileNum2 = 0;
		
		switch(entity.direction) {
			case "up": 
				entityTopRow = (entityTopWorldY - entity.speed)/gp.tileSize;
				tileNum1 = gp.tileM.mapTileNum[entityLeftCol][entityTopRow];
				tileNum2 = gp.tileM.mapTileNum[entityRightCol][entityTopRow];
				break;
			case "down":
				entityBottomRow = (entityBottomWorldY + entity.speed)/gp.tileSize;
				tileNum1 = gp.tileM.mapTileNum[entityLeftCol][entityBottomRow];
				tileNum2 = gp.tileM.mapTileNum[entityRightCol][entityBottomRow];
				break;
			case "left":
				entityLeftCol = (entityLeftWorldX - entity.speed)/gp.tileSize;
				tileNum1 = gp.tileM.mapTileNum[entityLeftCol][entityTopRow];
				tileNum2 = gp.tileM.mapTileNum[entityLeftCol][entityBottomRow];
				break;
			case "right":
				entityRightCol = (entityRightWorldX + entity.speed)/gp.tileSize;
				tileNum1 = gp.tileM.mapTileNum[entityRightCol][entityTopRow];
				tileNum2 = gp.tileM.mapTileNum[entityRightCol][entityBottomRow];
				break;
		}
		if(gp.tileM.tile[tileNum1].isSolid || gp.tileM.tile[tileNum2].isSolid) {
			entity.collidedWithTile = true;
		}
	}
	
	//Entity to entity collision
	public int checkEntity(Entity entity, Entity[] target) {
		
		int index = 999;
		
		for(int i = 0; i < target.length; i++) {
			if(target[i] != null && entity != target[i]) {
			
			Entity targetEntity = target[i];
			
			entity.solidArea.x = entity.worldX + entity.solidArea.x;
			entity.solidArea.y = entity.worldY + entity.solidArea.y;
			
			targetEntity.solidArea.x = targetEntity.worldX + targetEntity.solidArea.x;
			targetEntity.solidArea.y = targetEntity.worldY + targetEntity.solidArea.y;
			
			switch(entity.direction) {
			case"up":entity.solidArea.y -= entity.speed;break;
			case"down":entity.solidArea.y += entity.speed;break;
			case"left":entity.solidArea.x -= entity.speed;break;
			case"right":entity.solidArea.x += entity.speed;break;
			}
			if(entity.solidArea.intersects(targetEntity.solidArea) && targetEntity.isSolid) {
				System.out.println("collided");
				entity.collidedWithEntity = true;
				index = i;
			}
			entity.solidArea.x = entity.solidAreaDefaultX;
			entity.solidArea.y = entity.solidAreaDefaultY;
			
			targetEntity.solidArea.x = targetEntity.solidAreaDefaultX;
			targetEntity.solidArea.y = targetEntity.solidAreaDefaultY;
			}
		}
		return index;
	}
	
	//Entity to player collision
	public void checkPlayer(Entity entity) {
		
		entity.solidArea.x = entity.worldX + entity.solidArea.x;
		entity.solidArea.y = entity.worldY + entity.solidArea.y;
		
		gp.player.solidArea.x = gp.player.worldX + gp.player.solidArea.x;
		gp.player.solidArea.y = gp.player.worldY + gp.player.solidArea.y;
		
		switch(entity.direction) {
		case"up":entity.solidArea.y -= entity.speed;break;
		case"down":entity.solidArea.y += entity.speed;break;
		case"left":entity.solidArea.x -= entity.speed;break;
		case"right":entity.solidArea.x += entity.speed;break;
		}
		if(entity.solidArea.intersects(gp.player.solidArea) && entity.isSolid) {
			System.out.println("collided");
			entity.collidedWithPlayer = true;
		}
		entity.solidArea.x = entity.solidAreaDefaultX;
		entity.solidArea.y = entity.solidAreaDefaultY;
		
		gp.player.solidArea.x = gp.player.solidAreaDefaultX;
		gp.player.solidArea.y = gp.player.solidAreaDefaultY;
	}
	
	public int checkHit(AttackCollider attackC, Entity[] target) {
		int i = 0;
		
		
		return i;
	}
}









