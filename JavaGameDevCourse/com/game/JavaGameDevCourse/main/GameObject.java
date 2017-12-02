package com.game.JavaGameDevCourse.main;

import java.awt.Rectangle;

public abstract class GameObject {
	
	protected double x, y;
	
	public GameObject(double x, double y) {
		this.x = x;
		this.y = y;
	}
	
	public Rectangle getBounds(int width, int height) {
		return new Rectangle((int)x, (int)y, width, height);
	}
}
