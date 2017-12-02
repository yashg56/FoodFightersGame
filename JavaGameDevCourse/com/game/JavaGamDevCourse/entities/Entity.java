package com.game.JavaGamDevCourse.entities;

import java.awt.Graphics;
import java.awt.Rectangle;

public interface Entity {

	public void tick();
	public void render(Graphics g);
	public Rectangle getBounds();
	
	public double getX();
	public double getY();
	public boolean remove();
}
