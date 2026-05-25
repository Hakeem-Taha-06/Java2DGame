package main;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class UtilityTool {
	public BufferedImage scaleImage(BufferedImage originalImage, int width, int height) {
		
		BufferedImage scaledImage = new BufferedImage(width, height, originalImage.getType());
		Graphics2D g2 = scaledImage.createGraphics();
		g2.drawImage(originalImage, 0, 0, width, height, null);
		g2.dispose();
		
		return scaledImage;
	}
	
	public void readSpriteSheet(BufferedImage[] spriteArray, String sheetPath, int numberOfFrames, int spriteWidth, int spriteHeight) {
		
		try {
			BufferedImage spriteSheet = ImageIO.read(getClass().getResourceAsStream(sheetPath+".png"));
			for(int i = 0; i < numberOfFrames; i++) {
				
				//with pre-scaling
				//BufferedImage image =  spriteSheet.getSubimage(i*spriteWidth, 0, spriteWidth, spriteHeight);
				//spriteArray[i] = scaleImage(image, gp.tileSize, gp.tileSize);
				
				//Without pre-scaling
				spriteArray[i] = spriteSheet.getSubimage(i*spriteWidth, 0, spriteWidth, spriteHeight);;
			}
		}catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	public String flipDirection(String direction) {
		switch(direction) {
		case"up": return"down";
		case"down": return"up";
		case"left": return"right";
		case"right": return"left";
		}
		return "none";
	}
	
}
