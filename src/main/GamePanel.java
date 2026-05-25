package main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;

import javax.swing.JPanel;

import entity.Entity;
import entity.NPC_WhiteKnight;
import entity.Player;
import object.SuperObject;
import tile.TileManager;

public class GamePanel extends JPanel implements Runnable{
	
	//SCREEN SETTINGS
	final int originalTileSize = 16;
	final int scale = 3;
	
	final public int tileSize = originalTileSize * scale; // 16x16 tile
	final public int maxScreenCol = 16;
	final public int maxScreenRow = 12;
	final public int screenWidth = tileSize * maxScreenCol; // 768 pixels
	final public int screenHeight = tileSize * maxScreenRow; // 576 pixels
	
	//WORLD SETTINGS
	final public int maxWorldCol = 50;
	final public int maxWorldRow = 50;
	final public int worldWidth = tileSize * maxWorldCol;
	final public int worldHeight = tileSize * maxWorldRow;
	
	//FPS
	int FPS = 60;
	
	//SYSTEM
	TileManager tileM = new TileManager(this);
	public KeyHandler keyH = new KeyHandler(this);
	public UI ui = new UI(this);
	EventHandler eventH = new EventHandler(this);
	Thread gameThread;
	public CollisionChecker cChecker = new CollisionChecker(this);
	public AssetSetter aSetter = new AssetSetter(this);
	public Sound music = new Sound();
	public Sound SFX = new Sound();
	
	//ENTITIES AND OBJECTS
	public Player player = new Player(this, keyH);
	public SuperObject objects[] = new SuperObject[20];
	public Entity npcs[] = new Entity[10];
	public Entity enemies[] = new Entity[10];
	public ArrayList<Entity> weapons = new ArrayList<>();
	ArrayList<Entity> allEntities = new ArrayList<>();
	
	//GAME STATE
	public int gameState;
	public final int titleState = 0;
	public final int playState = 1;
	public final int pauseState = 2;
	public final int dialogState = 3;
	
	public String gameDifficulty = "";
	
	
	public GamePanel() {
		this.setPreferredSize(new Dimension(screenWidth, screenHeight));
		this.setBackground(Color.black);
		this.setDoubleBuffered(true);
		this.addKeyListener(keyH);
		this.setFocusable(true);
		
	}
	
	public void setupGame() {
		aSetter.setObjects();
		aSetter.setNPCs();
		aSetter.setEnemies();
		playMusic(2);
		gameState = titleState;
		
	}
	
	public void startGameThread() {
		
		gameThread = new Thread(this);
		gameThread.start();
	}
	
	@Override
	public void run() {
		
		double drawInterval = 1000000000/FPS; // 1/60 of a second
		double delta = 0;
		long lastTime = System.nanoTime();
		long currentTime;
		long timer = 0;
		int drawCount =0;
		
		while(gameThread != null) {
			
			currentTime = System.nanoTime();
			delta += (currentTime - lastTime)/drawInterval;
			timer += (currentTime - lastTime);
			lastTime = currentTime;
			
			if(delta >= 1) {
				update();
				repaint();
				delta--;
				drawCount++;
			}
			
			if(timer >= 1000000000) {
				System.out.println("FPS : "+ drawCount);
				timer = 0;
				drawCount = 0;
			}
		}
	}
	
	public void update() {
		if(gameState == playState) {
			
			fillEntitiesArray();
			
			for(int i = 0; i < allEntities.size(); i++) {
				allEntities.get(i).update();
			}
			
			eventH.checkEvent();
			}
		
		System.out.println(gameState);
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		Graphics2D g2 = (Graphics2D)g;
		
		long drawStart = 0;
		if(keyH.tPressed) {
			drawStart = System.nanoTime();
		}
		
		///////MENU STATE///////
		if(gameState == titleState) {
			
		}
		////////////////////////
		
		
		
		///////PLAY STATE///////
		else{
			tileM.draw(g2);
			
			allEntities.sort((a, b) -> Integer.compare(a.worldY, b.worldY));
			
			for(int i = 0; i < allEntities.size(); i++) {
				allEntities.get(i).draw(g2);
			}
			
			allEntities.clear();
			
		}
		////////////////////////
		
		ui.draw(g2);
		
		if(keyH.tPressed) {
		long drawEnd = System.nanoTime();
		long drawTime = drawEnd - drawStart;
		g2.setColor(Color.white);
		g2.drawString("Draw time = "+drawTime, 10, 400);
		System.out.println("Draw time = "+drawTime);
		}
		
		g2.dispose();
	}
	
	public void playMusic(int i) {
		music.setFile(i);
		music.play();
		music.loop();
	}
	
	public void stopMusic() {
		if(music.clip != null) {
			music.stop();
		}
	}
	
	public void resumeMusic() {
		if(music.clip != null) {
			music.play();
		}
	}
	
	public void playSFX(int i) {
		if(SFX.clip != null) {
			SFX.stop();
		}
		SFX.setFile(i);
		SFX.play();
	}
	
	public void fillEntitiesArray() {
		for(Entity npc : npcs) {
			if(npc != null) {
				allEntities.add(npc);
			}
		}
		for(Entity obj : objects) {
			if(obj != null) {
				allEntities.add(obj);
			}
		}
		for(Entity enemy : enemies) {
			if(enemy != null) {
				allEntities.add(enemy);
			}
		}
		System.out.println(weapons.size());
		allEntities.addAll(weapons);
		allEntities.add(player);
	}
}





