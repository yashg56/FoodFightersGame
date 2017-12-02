package com.game.JavaGameDevCourse.main;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;

import com.game.JavaGamDevCourse.entities.FriendlyEntity;

public class Fries extends GameObject implements FriendlyEntity {
	
	private static final int SPEED = 10;

	BufferedImage image;
	
	BufferedImageLoader loader = new BufferedImageLoader();
	
	Fries(double x, double y) {
		super(x,y);
	}
	
	public void tick() {
		y -= SPEED;
	}
	
	public void render(Graphics g) {
		try {
			image = loader.loadImage("/bullet.PNG");
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		g.drawImage(image, (int)x,(int) y, null);
	}

	public double getY() {
		return y;
	}
	
	public double getX() {
		return x;
	}

	public Rectangle getBounds() {
		return new Rectangle((int)x, (int)y, 32, 32);
	}

	public boolean remove() {
		return y < 0;
	}
}
