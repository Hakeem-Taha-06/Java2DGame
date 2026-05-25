package main;

import entity.ENEMY_Slime;
import entity.NPC_WhiteKnight;
import object.OBJ_Chest;
import object.OBJ_Door;
import object.OBJ_Key;
import object.OBJ_Marker;
import object.OBJ_SpeedBoot;
import object.OBJ_FullHeart;
import object.OBJ_HalfHeart;

public class AssetSetter {
	GamePanel gp;
	
	public AssetSetter(GamePanel gp) {
		this.gp = gp;
	}
	
	public void setObjects() {
		gp.objects[0] = new OBJ_Key(gp, 11,42);
		
		gp.objects[1] = new OBJ_SpeedBoot(gp, 38, 42);
		
		gp.objects[2] = new OBJ_Door(gp, 38, 39);
		
		gp.objects[3] = new OBJ_Door(gp, 37, 11);
		
		gp.objects[4] = new OBJ_Key(gp, 40, 42);
		
		gp.objects[5] = new OBJ_Door(gp, 5, 10);
		
		gp.objects[6] = new OBJ_Door(gp, 5, 8);
		
		gp.objects[7] = new OBJ_Chest(gp, 5, 5);
		
		gp.objects[8] = new OBJ_Key(gp, 40, 9);
		
		gp.objects[9] = new OBJ_Key(gp, 42, 9);
		
		gp.objects[10] = new OBJ_FullHeart(gp, 27, 25);
		
		gp.objects[11] = new OBJ_HalfHeart(gp, 27, 26);
		
		gp.objects[18] = new OBJ_Marker(gp, 99, 99);
		
		gp.objects[19] = new OBJ_Marker(gp, 99, 99);
	}
	
	public void setNPCs() {
//		gp.npcs[0] = new NPC_WhiteKnight(gp, 27, 27);
//		
//		gp.npcs[1] = new NPC_WhiteKnight(gp, 27, 28);
		
	}
	
	public void setEnemies() {
		gp.enemies[0] = new ENEMY_Slime(gp, 27, 29);
		
		gp.enemies[1] = new ENEMY_Slime(gp, 27, 28);
		
		gp.enemies[2] = new ENEMY_Slime(gp, 27, 27);
		
	}
}




