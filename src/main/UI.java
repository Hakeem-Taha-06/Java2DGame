package main;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

import entity.Entity;
import object.OBJ_Heart;

public class UI {
	
	GamePanel gp;
	Graphics2D g2;
	UtilityTool uTool;
	Font defaultFont;
	Font maruMonica;
	BufferedImage keyImage;
	BufferedImage cursorImage;
	BufferedImage fullHeart, halfHeart, emptyHeart;
	public boolean messageOn = false;
	public String message = "";
	public int messageTimer = 90;
	
	public boolean gameFinished = false;
	
	public int menuChoice = 0;
	public int maxMenuChoice = 1;
	
	public int titleScreenState = 0;
	
	public String currentDialog = "";
	
	public UI(GamePanel gp) {
		this.gp = gp;
		
		try {
			InputStream is = getClass().getResourceAsStream("/fonts/x12y16pxMaruMonica.ttf");
			maruMonica = Font.createFont(Font.TRUETYPE_FONT, is);
			is = getClass().getResourceAsStream("/fonts/CAMBRIA.TTC");
			defaultFont = Font.createFont(Font.TRUETYPE_FONT, is);
			
			keyImage = ImageIO.read(getClass().getResourceAsStream("/objects/Key_1.png"));
			cursorImage = ImageIO.read(getClass().getResourceAsStream("/UI/Cursor.png"));
			
		} catch (FontFormatException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		OBJ_Heart heart = new OBJ_Heart(gp);
		
		fullHeart = heart.standing[0];
		halfHeart = heart.standing[1];
		emptyHeart = heart.standing[2];
		
		uTool = new UtilityTool();
		
	}
	
	public void displayMessage(String text) {
		message = text;
		messageOn = true;
	}
	
	public void draw(Graphics2D g2) {
		this.g2 = g2;
		
		g2.setFont(maruMonica.deriveFont(20F));
		g2.setColor(Color.white);
		
		if(gp.gameState == gp.playState) {
			drawGameScreen();
		}
		else if(gp.gameState == gp.pauseState) {
			drawPauseScreen();
		}
		else if(gp.gameState == gp.dialogState) {
			drawDialogBox();
		}
		else if(gp.gameState == gp.titleState) {
			drawMenuScreen();
		}
	}
	
	
	public void drawGameScreen() {
		if(gameFinished) {
			g2.setFont(maruMonica.deriveFont(60F));
			g2.setColor(Color.white);
			
			int x = getCenterX(message);
			int y = getCenterY(message);
					
			drawSubWindow(0*gp.tileSize, 0*gp.tileSize, gp.screenWidth, gp.screenHeight, 255);
			g2.drawString(message, x, y);
			
			gp.gameThread = null;
		}else {
			
			drawHealth(gp.player);
			
			//DISPLAY MESSAGE
			if(messageOn) {
				drawSubWindow(3*gp.tileSize, 10*gp.tileSize, 10*gp.tileSize, 2*gp.tileSize, 200);
				g2.drawString("- "+message, 5*gp.tileSize - gp.tileSize/2, 11*gp.tileSize);
				messageTimer--;
				if(messageTimer < 0) {
					messageOn = false;
					messageTimer = 90;
				}
			}
		}
	}
	
	public void drawPauseScreen() {
		String text = "PAUSED";
		
		g2.setFont(maruMonica.deriveFont(60F));
		int x = getCenterX(text);
		int y = getCenterY(text);
		g2.drawString(text, x, y);
	}
	
	public void drawDialogBox() {
		
		int x = (int)(gp.tileSize*0.5);
		int y = (int)(gp.screenHeight - gp.tileSize * 4);
		int width = gp.screenWidth - gp.tileSize;
		int height = gp.tileSize * 4;
		
		drawSubWindow(x, y, width, height, 200);
		
		x += gp.tileSize/2;
		y += gp.tileSize;
		
		String[] lines = textWrapUp(currentDialog, 40);
		
		g2.setFont(maruMonica.deriveFont(40F));
		for(String line : lines) {
			if(line != null) {
			g2.drawString(line, x, y);
			y += 40;
			}
		}
	}
	
	public void drawMenuScreen() {
		drawSubWindow(0, 0, gp.screenWidth, gp.screenHeight, 255);
		
		if(titleScreenState == 0) {
			maxMenuChoice = 1;
			
			String text = "TITLE SCREEN";
			g2.setFont(maruMonica.deriveFont(80F).deriveFont(Font.BOLD));
			int x = getCenterX(text);
			int y = getCenterY(text) - 2*gp.tileSize;
			
			g2.setColor(new Color(120, 120, 120));
			g2.drawString(text, x + 3, y + 3);
			g2.setColor(Color.white);
			g2.drawString(text, x, y);
			
			
			text = "Start Game";
			g2.setFont(maruMonica.deriveFont(40F));
			x = getCenterX(text);
			y += 2*gp.tileSize;
			int firstChoiceY = y;
			g2.drawString(text, x, y);
			
			text = "Exit";
			x = getCenterX(text);
			y += gp.tileSize;
			g2.drawString(text, x, y);
			
			if(menuChoice <= maxMenuChoice && menuChoice >= 0) {
				g2.setColor(Color.black);
				x = getCenterX(text) - 2*gp.tileSize;
				y = firstChoiceY - gp.tileSize/2 + menuChoice*gp.tileSize;
				g2.drawImage(cursorImage, x, y , 32, 32, null);
			}
		}
		else if (titleScreenState == 1) {
			maxMenuChoice = 3;
			String text = "Choose the Difficulty";
			g2.setFont(maruMonica.deriveFont(80F).deriveFont(Font.BOLD));
			int x = getCenterX(text);
			int y = getCenterY(text) - 2*gp.tileSize;
			g2.drawString(text, x, y);
			
			text = "Easy";
			g2.setFont(maruMonica.deriveFont(40F));
			x = getCenterX(text);
			y += 2*gp.tileSize;
			int firstChoiceY = y;
			g2.drawString(text, x, y);
			
			text = "Medium";
			x = getCenterX(text);
			y += gp.tileSize;
			g2.drawString(text, x, y);
			
			text = "Hard";
			x = getCenterX(text);
			y += gp.tileSize;
			g2.drawString(text, x, y);
			
			text = "Back";
			x = getCenterX(text);
			y += gp.tileSize;
			g2.drawString(text, x, y);
			
			if(menuChoice <= maxMenuChoice && menuChoice >= 0) {
				g2.setColor(Color.black);
				x = getCenterX(text) - (int)(1.5*gp.tileSize);
				y = firstChoiceY - gp.tileSize/2 + menuChoice*gp.tileSize;
				g2.drawImage(cursorImage, x, y , 32, 32, null);
			}
			
		}
		
	}
	
	public void drawSubWindow(int x, int y, int width, int height, int opacity) {
		
		Color black = new Color(0, 0, 0, opacity);
		g2.setColor(black);
		g2.fillRoundRect(x, y, width, height, 20, 20);
		//g2.drawImage(displayBox, x, y, width, height,null);
		
		Color white = new Color(255, 255, 255);
		g2.setColor(white);
		g2.setStroke(new BasicStroke(5));
		g2.drawRoundRect(x + 5, y + 5, width - 10, height - 10, 10 , 10);
	}
	
	public void drawHealth(Entity entity) {
		
		for(int i = 0; i < entity.maxHealth; i++) {
			if(i%2 == 0) {
				g2.drawImage(emptyHeart, i*gp.tileSize/2, 0, gp.tileSize, gp.tileSize, null);
			}
		}
		
		int fullHearts = gp.player.health/2;
		int halfHearts = gp.player.health%2;
		int i = 0;
		for(; i < fullHearts; i++) {
			g2.drawImage(fullHeart, i*gp.tileSize, 0, gp.tileSize, gp.tileSize, null);
		}
		if(halfHearts == 1) {
			g2.drawImage(halfHeart, i*gp.tileSize, 0, gp.tileSize, gp.tileSize, null);
		}
		
	}
	
	public int getCenterX(String text) {
		int textLength = (int)g2.getFontMetrics().getStringBounds(text, g2).getWidth();
		return gp.screenWidth/2 - textLength/2;
	}
	public int getCenterY(String text) {
		int textHeight = (int)g2.getFontMetrics().getStringBounds(text, g2).getHeight();
		return gp.screenHeight/2 + textHeight/2;
	}
	
	public String[] textWrapUp(String text, int lineLength) {

		String[] lines = new String[256];
		int characterIndex = 0;
		int lineStartIndex = 0;
		int lineEndIndex = 0;
		int lineNumber = 0;
		String currentSubstring = "";

		for(char c : text.toCharArray()) {
			if(characterIndex != 0 && characterIndex%lineLength == 0) {

				while(Character.isLetter(text.toCharArray()[lineEndIndex])) {
					lineEndIndex--;
				}
				    currentSubstring = text.substring(lineStartIndex, lineEndIndex);
    				lines[lineNumber] = currentSubstring;
    				lineStartIndex = lineEndIndex;
    				lineNumber++;
    				characterIndex++;
			}else{
				characterIndex++;
				lineEndIndex = characterIndex;
			}
		}
		
		currentSubstring = text.substring(lineStartIndex, lineEndIndex);
		lines[lineNumber] = currentSubstring;

		return lines;
	}
}
