package com.game.JavaGameDevCourse.main;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.swing.JOptionPane;

import com.game.JavaGamDevCourse.entities.EnemyEntity;
import com.game.JavaGamDevCourse.entities.FriendlyEntity;

public class Player extends GameObject implements FriendlyEntity{

	private double velX = 0;
	private double velY = 0;
	String proceed;

	BufferedImage player;
	Game game;
	Controller c;
	
	public Player(double x, double y, Game game, Controller c) {
		super(x,y);
		this.game = game;
		this.c = c;
		
		BufferedImageLoader loader = new BufferedImageLoader();
		
		try {
			player= loader.loadImage("/spriteSheet2.PNG");
		} catch (IOException e) {
			
			e.printStackTrace();
		}
		
		
	}
	public void tick() {
		x += velX; //x = x + velX
		y += velY; //y = y + velY
		
		if (x <= 0) 
			x = 0;
		if (x >= 640 - 18) 
			x = 640 - 18;
		if (y < 0)
			y = 0;
		if(y>= 480-32)
			y = 480 - 32;
		
		for (int i = 0; i < game.ee.size(); i++) {
			EnemyEntity tempEnt = game.ee.get(i);
			
			if(Physics.Collision(this, tempEnt)) {
				c.removeEntity(tempEnt);
				Game.HEALTH -= 20;
				game.setEnemyKilled(game.getEnemyKilled() + 1);
			}
		}
		
		if(Game.HEALTH <= 0) {
			JOptionPane.showMessageDialog(null, "GAME OVER! BETTER LUCK NEXT TIME!");
			JOptionPane.showMessageDialog(null, "You killed " + Game.score + " enemies.");
			Game.state = Game.STATE.MENU;
		}
 	}
	
	public void render(Graphics g) {
		g.drawImage(player, (int)x, (int)y, null);
	}
	
	public double getX() {
		return x;
		
	}
	public double getY() {
		return y;
		
	}	
	public void setX(double x) {
		this.x = x;
	}	
	public void setY(double y) {
		this.y = y;
	}
	
	public void setVelX(double velX) {
		this.velX = velX;
	}
	public void setVelY(double velY) {
		this.velY = velY;
	}

	public Rectangle getBounds() {
		return new Rectangle((int)x, (int)y, 32, 32);
	}

	public boolean remove() {
		return false;
	}
}
