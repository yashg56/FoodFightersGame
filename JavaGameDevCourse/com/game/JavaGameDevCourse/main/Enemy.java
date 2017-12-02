package com.game.JavaGameDevCourse.main;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

import com.game.JavaGamDevCourse.entities.EnemyEntity;
import com.game.JavaGamDevCourse.entities.FriendlyEntity;

public class Enemy extends GameObject implements EnemyEntity {
	BufferedImage enemy;
	Random r = new Random();
	private Game game;
	private Controller c;

	private int speed = (r.nextInt(3) + 1);

	public Enemy(double x, double y, Game game, Controller c) {
		super(x, y);
		this.game = game;
		this.c = c;

		BufferedImageLoader loader = new BufferedImageLoader();

		try {
			enemy = loader.loadImage("/enemy.PNG");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void tick() {
		y += speed;

		if (y > (Game.HEIGHT * Game.SCALE)) {
			speed = (r.nextInt(3) + 1);
			y = -10;
			x = r.nextInt(Game.WIDTH * Game.SCALE);
			Game.HEALTH -= 3;
		}

		for (int i = 0; i < game.fe.size(); i++) {
			FriendlyEntity tempEnt = game.fe.get(i);

			if (Physics.Collision(this, tempEnt)) {
				c.removeEntity(tempEnt);
				c.removeEntity(this);
				game.setEnemyKilled(game.getEnemyKilled() + 1);
				Game.score++;
			}
		}
	}

	public void render(Graphics g) {
		g.drawImage(enemy, (int) x, (int) y, null);

	}

	public Rectangle getBounds() {
		return new Rectangle((int) x, (int) y, 32, 32);
	}

	public double getX() {
		return x;
	}

	public double getY() {
		return y;
	}

	public void setY(double y) {
		this.y = y;
	}

	public boolean remove() {
		return false;
	}
}
