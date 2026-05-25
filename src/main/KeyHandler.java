package main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener{

	GamePanel gp;

	public boolean upPressed, downPressed, leftPressed, rightPressed, 
				   ePressed, tPressed, pPressed, shiftPressed, fPressed, pHeld = false;
	
	public KeyHandler(GamePanel gp) {
		this.gp = gp;
	}
	
	
	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		
		//PLAY STATE
		if(gp.gameState == gp.playState) {
			if(e.getKeyCode() == KeyEvent.VK_P) {
				if(!pHeld) {
					pHeld = true;
					gp.gameState = gp.pauseState;
					gp.stopMusic();
					gp.playSFX(4);
				}
			}
			if(e.getKeyCode() == KeyEvent.VK_W) {
				upPressed = true;
			}
			if(e.getKeyCode() == KeyEvent.VK_S) {
				downPressed = true;
			}
			if(e.getKeyCode() == KeyEvent.VK_A) {
				leftPressed = true;
			}
			if(e.getKeyCode() == KeyEvent.VK_D) {
				rightPressed = true;
			}
			if(e.getKeyCode() == KeyEvent.VK_E) {
				ePressed = true;
			}
			if(e.getKeyCode() == KeyEvent.VK_T) {
				tPressed = true;
			}
			if(e.getKeyCode() == KeyEvent.VK_SHIFT) {
				shiftPressed = true;
			}
			if(e.getKeyCode() == KeyEvent.VK_F) {
				fPressed = true;
			}
		}
		
		//PAUSE STATE
		else if(gp.gameState == gp.pauseState) {
			if(!pHeld) {
				pHeld = true;
				gp.gameState = gp.playState;
				gp.resumeMusic();
			}
		}
		
		//DIALOG STATE
		else if(gp.gameState == gp.dialogState) {
			if(e.getKeyCode() == KeyEvent.VK_E) {
				gp.player.interactWithNPC(gp.player.NPCIndex);
			}
		}
		
		//TITLE STATE
		else if(gp.gameState == gp.titleState) {
			
			//MAIN TITLE SCREEN
			if(gp.ui.titleScreenState == 0) {
				if(e.getKeyCode() == KeyEvent.VK_S) {
					if(gp.ui.menuChoice < gp.ui.maxMenuChoice) {
						gp.ui.menuChoice++;
					}else {
						gp.ui.menuChoice = 0;
					}
				}
				if(e.getKeyCode() == KeyEvent.VK_W) {
					if(gp.ui.menuChoice > 0) {
						gp.ui.menuChoice--;
					}else {
						gp.ui.menuChoice = gp.ui.maxMenuChoice;
					}
				}
				
				if(e.getKeyCode() == KeyEvent.VK_E) {
					if(gp.ui.menuChoice == 0) {
						gp.ui.titleScreenState = 1;
					}else if(gp.ui.menuChoice == 1) {
						System.exit(0);
					}
				}
			//DIFFICULTY CHOOSE SCREEN
			}else if(gp.ui.titleScreenState == 1) {
				if(e.getKeyCode() == KeyEvent.VK_S) {
					if(gp.ui.menuChoice < gp.ui.maxMenuChoice) {
						gp.ui.menuChoice++;
					}else {
						gp.ui.menuChoice = 0;
					}
				}
				if(e.getKeyCode() == KeyEvent.VK_W) {
					if(gp.ui.menuChoice > 0) {
						gp.ui.menuChoice--;
					}else {
						gp.ui.menuChoice = gp.ui.maxMenuChoice;
					}
				}
				if(e.getKeyCode() == KeyEvent.VK_E) {
					
					boolean startGame = false;
					switch(gp.ui.menuChoice) {
					
					case 0:
						gp.gameDifficulty = "Easy";
						startGame = true;
						break;
					case 1:
						gp.gameDifficulty = "Medium";
						startGame = true;
						break;
					case 2:
						gp.gameDifficulty = "Hard";
						startGame = true;
						break;
					case 3:
						gp.ui.titleScreenState = 0;
						gp.ui.menuChoice = 0;
						break;
					}
					if(startGame) {
						gp.stopMusic();
						gp.playMusic(3);
						gp.gameState = gp.playState;
					}
				}
			}
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_P) {
			pHeld = false;
		}
		if(e.getKeyCode() == KeyEvent.VK_W) {
			upPressed = false;
		}
		if(e.getKeyCode() == KeyEvent.VK_S) {
			downPressed = false;
		}
		if(e.getKeyCode() == KeyEvent.VK_A) {
			leftPressed = false;
		}
		if(e.getKeyCode() == KeyEvent.VK_D) {
			rightPressed = false;
		}
		if(e.getKeyCode() == KeyEvent.VK_E) {
			ePressed = false;
		}
		if(e.getKeyCode() == KeyEvent.VK_T) {
			tPressed = false;
		}
		if(e.getKeyCode() == KeyEvent.VK_SHIFT) {
			shiftPressed = false;
		}
		if(e.getKeyCode() == KeyEvent.VK_F) {
			fPressed = false;
		}
	}
	
}
