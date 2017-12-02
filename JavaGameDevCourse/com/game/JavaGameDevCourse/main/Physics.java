package com.game.JavaGameDevCourse.main;

import com.game.JavaGamDevCourse.entities.Entity;

public class Physics {
	
	public static boolean Collision(Entity fe, Entity ee) {
		return fe.getBounds().intersects(ee.getBounds());
	}
}
