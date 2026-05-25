package main;

import java.util.HashSet;
import java.util.Iterator;

public class EventHandler {

	GamePanel gp;
	EventRect eventRect[][];
	HashSet<EventRect> triggeredEvents;
	int eventTimer;
	int lastEventX, lastEventY;
	boolean canTriggerEvents = true;
	
	public EventHandler(GamePanel gp) {
		this.gp = gp;
		eventRect = new EventRect[gp.maxWorldCol][gp.maxWorldRow];
		triggeredEvents = new HashSet<>();
		
		for(int row = 0; row < gp.maxWorldRow; row++) {
			for(int col = 0; col < gp.maxWorldCol; col++) {
				eventRect[col][row] = new EventRect(col, row);
				eventRect[col][row].x = 23; 
				eventRect[col][row].y = 23;
				eventRect[col][row].width = 2; 
				eventRect[col][row].height = 2;
				eventRect[col][row].defaultX = eventRect[col][row].x;
				eventRect[col][row].defaultY = eventRect[col][row].y;
			}
		}
		
		eventTimer = 0;
	}
	
	public void checkEvent() {
		int dx = Math.abs(gp.player.worldX - lastEventX);
		int dy = Math.abs(gp.player.worldY - lastEventY);
		long distance = Math.round(Math.sqrt(Math.pow(dx, 2) + Math.pow(dy, 2)));
		if(distance > gp.tileSize) {
			canTriggerEvents = true;
		}
		
		if(canTriggerEvents) {
			if(eventTimer == 60) {
				Iterator<EventRect> it = triggeredEvents.iterator();
				while(it.hasNext()) {
					EventRect er = it.next();
					if(!er.isOneTimeEvent) {
						it.remove();
						System.out.println("Removed event");
					}
				}
				eventTimer = 0;
			}
			eventTimer++;
			
			if(hit(23, 14, "any")) {lakeHealing(23, 14);}
			if(hit(17, 37, "any")) {spikeDamage(17, 37);}
			if(hit(17, 34, "any")) {pitDamage(17, 34);}
		}
	}
	
	public boolean hit(int col, int row, String direction) {
		
		boolean hit = false;
		
		gp.player.solidArea.x = gp.player.worldX + gp.player.solidArea.x;
		gp.player.solidArea.y = gp.player.worldY + gp.player.solidArea.y;
		eventRect[col][row].x = col*gp.tileSize + eventRect[col][row].x;
		eventRect[col][row].y = row*gp.tileSize + eventRect[col][row].y;
		
		if(gp.player.solidArea.intersects(eventRect[col][row]) && !triggeredEvents.contains(eventRect[col][row])) {
			if(gp.player.direction.contentEquals(direction) || direction.contentEquals("any")) {
				hit = true;
				
				lastEventX = col*gp.tileSize;
				lastEventY = row*gp.tileSize;
			}
		}
		
		gp.player.solidArea.x = gp.player.solidAreaDefaultX;
		gp.player.solidArea.y = gp.player.solidAreaDefaultY;
		
		eventRect[col][row].x = eventRect[col][row].defaultX;
		eventRect[col][row].y = eventRect[col][row].defaultY;
		
		return hit;
		
	}
	
	
	//CONTINUOUS EVENTS
	public void spikeDamage(int col, int row) {
		gp.player.health--;
		gp.ui.displayMessage("You took 1 damage");
		gp.playSFX(8);
		//screen shake
		
		triggeredEvents.add(eventRect[col][row]);
	}
	
	//DISABLING EVENTS
	public void pitDamage(int col, int row) {
		gp.player.health--;
		gp.ui.displayMessage("You took 1 damage");
		gp.playSFX(8);
		//screen shake
		
		triggeredEvents.add(eventRect[col][row]);
		canTriggerEvents = false;
	}
	
	//ONE-TIME-ONLY EVENTS
	public void lakeHealing(int col, int row) {
		if(gp.keyH.ePressed) {
			gp.player.health = gp.player.maxHealth;
			gp.ui.displayMessage("Your health fully recovered");
			gp.playSFX(7);
			
			eventRect[col][row].isOneTimeEvent = true;
			triggeredEvents.add(eventRect[col][row]);
		}
	}
	
}
