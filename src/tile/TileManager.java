package tile;

import java.awt.Graphics2D;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Random;

import javax.imageio.ImageIO;

import main.GamePanel;
import main.UtilityTool;

public class TileManager {
	GamePanel gp;
	public Tile[] tile;
	public int mapTileNum[][];
	Random rand;
	
	public TileManager(GamePanel gp) {
		this.gp = gp;
		tile = new Tile[50];
		mapTileNum = new int[gp.maxWorldCol][gp.maxWorldRow];
		rand = new Random();
		
		getTileImage();
		loadMap("/maps/worldmap01.txt");
	}
	public void getTileImage() {
			
			setup(0, "grass00", false);
			setup(1, "grass00", false);
			setup(2, "grass00", false);
			setup(3, "grass00", false);
			setup(4, "grass00", false);
			setup(5, "grass00", false);
			setup(6, "grass00", false);
			setup(7, "grass00", false);
			setup(8, "grass00", false);
			setup(9, "grass00", false);
			
			setup(10, "grass00", false);
			setup(11, "grass01", false);
			
			setup(12, "tree00", true);
			setup(13, "desert00", false);
			setup(14, "cactus00", true);
			setup(15, "brickwall00", true);
			
			setup(16, "dirtpathH", false);
			setup(17, "dirtpathV", false);
			setup(18, "dirtpathtopleft", false);
			setup(19, "dirtpathtop", false);
			setup(20, "dirtpathtopright", false);
			setup(21, "dirtpathleft", false);
			setup(22, "dirtpathright", false);
			setup(23, "dirtpathbottomleft", false);
			setup(24, "dirtpathbottom", false);
			setup(25, "dirtpathbottomright", false);
			setup(26, "dirtpathtopleftcorner", false);
			setup(27, "dirtpathtoprightcorner", false);
			setup(28, "dirtpathbottomleftcorner", false);
			setup(29, "dirtpathbottomrightcorner", false);
			
			setup(30, "water00", true);
			setup(31, "water01", true);
			setup(32, "watertopleft", true);
			setup(33, "watertop", true);
			setup(34, "watertopright", true);
			setup(35, "waterleft", true);                
			setup(36, "waterright", true);
			setup(37, "waterbottomleft", true);
			setup(38, "waterbottom", true);
			setup(39, "waterbottomright", true);
			setup(40, "watertopleftcorner", true);
			setup(41, "watertoprightcorner", true);
			setup(42, "waterbottomleftcorner", true);
			setup(43, "waterbottomrightcorner", true);
			
		
	}
	
	public void setup(int index, String imagePath, boolean solid) {
		UtilityTool uTool = new UtilityTool();
		
		try {
			
			tile[index] = new Tile();
			tile[index].sprite = ImageIO.read(getClass().getResourceAsStream("/tiles/"+imagePath+".png"));
			tile[index].sprite = uTool.scaleImage(tile[index].sprite, gp.tileSize, gp.tileSize);
			tile[index].isSolid = solid;
			
			
		}catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	public void loadMap(String filePath) {
		try {
			InputStream is = getClass().getResourceAsStream(filePath);
			BufferedReader br = new BufferedReader(new InputStreamReader(is));
			
			for(int row = 0; row < gp.maxWorldRow; row++) {
				
				String line = br.readLine();
				String numbers[] = line.split(" ");
				
				for(int col = 0; col < gp.maxWorldCol; col++) {
				
					int num = Integer.parseInt(numbers[col]);
					mapTileNum[col][row] = num;
				}
			}
			br.close();
			
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void draw(Graphics2D g2) {
		for(int worldCol = 0; worldCol < gp.maxWorldCol; worldCol++) {
			for(int worldRow = 0; worldRow < gp.maxWorldRow; worldRow++) {
				
				int tileNum = mapTileNum[worldCol][worldRow];
				
				int worldX = gp.tileSize*worldCol;
				int worldY = gp.tileSize*worldRow;
				int screenX = worldX - gp.player.worldX + gp.player.screenX;
				int screenY = worldY - gp.player.worldY + gp.player.screenY;
				
				if(screenX + gp.tileSize >= 0 && 
				   screenX < gp.screenWidth && 
				   screenY + gp.tileSize >= 0 && 
				   screenY < gp.screenHeight) {
				g2.drawImage(tile[tileNum].sprite, screenX, screenY, null);
				}
			}
		}
	}
}
