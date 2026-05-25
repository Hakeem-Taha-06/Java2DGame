package object;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import entity.Entity;
import main.GamePanel;

public class SuperObject extends Entity{
	
	GamePanel gp;
	
	public SuperObject(GamePanel gp) {
		super(gp);
		this.gp = gp;
		// TODO Auto-generated constructor stub
	}
	
	public void update() {
		
		if(spriteCounter == spriteRefreshRate) {
			spriteNum = (spriteNum + 1)%numberOfFrames;
			spriteCounter = 0;
		}
		spriteCounter++;
		
	}
	
	public void draw(Graphics2D g2) {
		BufferedImage image = standing[spriteNum];
		
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
}
