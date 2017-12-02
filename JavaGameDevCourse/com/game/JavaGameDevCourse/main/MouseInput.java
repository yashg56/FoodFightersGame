package com.game.JavaGameDevCourse.main;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class MouseInput implements MouseListener {

	public void mouseClicked(MouseEvent e) {

	}

	public void mouseEntered(MouseEvent e) {

	}

	public void mouseExited(MouseEvent e) {

	}

	public void mousePressed(MouseEvent e) {

		int mx = e.getX();
		int my = e.getY();

		/*
		 * public Rectangle playButton = new Rectangle(Game.WIDTH / 2 + 120, 150, 100,
		 * 50); public Rectangle helpButton = new Rectangle(Game.WIDTH / 2 + 120, 250,
		 * 100, 50); public Rectangle quitButton = new Rectangle(Game.WIDTH / 2 + 120,
		 * 350, 100, 50);
		 */

		// Play
		if (mx >= Game.WIDTH / 2 + 120 && mx <= Game.WIDTH / 2 + 220) {
			if (my >= 150 & my <= 200) {
				// Pressed Play Button
				Game.state = Game.STATE.GAME;
			}
		}
		
		//Help
		if (mx >= Game.WIDTH / 2 + 120 && mx <= Game.WIDTH / 2 + 220) {
			
			if(my >= 250 & my <= 400) {
				Game.state = Game.STATE.HELP;
			}
			
		}

		// Quit
		if (mx >= Game.WIDTH / 2 + 120 && mx <= Game.WIDTH / 2 + 220) {
			if (my >= 350 & my <= 400) {
				// Pressed Quit Button
				System.exit(1);
			}
		}
	}

	public void mouseReleased(MouseEvent e) {

	}

}
