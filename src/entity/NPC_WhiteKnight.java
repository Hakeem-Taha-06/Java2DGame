package entity;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;

import main.GamePanel;
import main.UtilityTool;

public class NPC_WhiteKnight extends Entity{
	
	public NPC_WhiteKnight(GamePanel gp, int x, int y) {
		super(gp);
		name = "WhiteKnight";
		direction = "down";
		speed = 2;
		worldX = x * gp.tileSize;
		worldY = y * gp.tileSize;
		
		solidArea = new Rectangle(8, 16, 32, 32);
		solidAreaDefaultX = solidArea.x;
		solidAreaDefaultY = solidArea.y;
		
		
		getNPCSprite();
		setDialog();
	}
	
	public void getNPCSprite() {
		UtilityTool uTool = new UtilityTool();
		
		standing = new BufferedImage[numberOfFrames];
		uTool.readSpriteSheet(standing, "/npc/NPC_idle_sheet", numberOfFrames, spriteWidth, spriteHeight);
		
		up = new BufferedImage[numberOfFrames];
		uTool.readSpriteSheet(up, "/npc/NPC_up_sheet", numberOfFrames, spriteWidth, spriteHeight);
		
		down = new BufferedImage[numberOfFrames];
		uTool.readSpriteSheet(down, "/npc/NPC_down_sheet", numberOfFrames, spriteWidth, spriteHeight);
		
		left = new BufferedImage[numberOfFrames];
		uTool.readSpriteSheet(left, "/npc/NPC_down_sheet", numberOfFrames, spriteWidth, spriteHeight);
		
		right = new BufferedImage[numberOfFrames];
		uTool.readSpriteSheet(right, "/npc/NPC_down_sheet", numberOfFrames, spriteWidth, spriteHeight);
	}
	
	public void setDialog() {
		dialogs[0] = "In a remote corner of the world, hidden beyond a ridge of ancient, craggy mountains and swathed in perpetual mist, lies a valley untouched by time:";
		dialogs[1] = "the Valley of the Forgotten. Few maps mark its existence, and even fewer travelers have dared to seek it.";
		dialogs[2] = "But those who have returned—changed, quiet, burdened with wonder—speak of a place that defies logic, where the natural and the supernatural converge like rivers.";
		dialogs[3] = "Streams fed by hidden springs crisscross the valley floor, their waters so clear that fish seem to float in air.  ";
		dialogs[18] = "bye";
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
	
	public void speak() {
		super.speak();
	}
}
