package com.game.JavaGameDevCourse.main;

import java.awt.Graphics;
import java.util.LinkedList;
import java.util.Random;

import com.game.JavaGamDevCourse.entities.EnemyEntity;
import com.game.JavaGamDevCourse.entities.FriendlyEntity;

public class Controller {
	
	private LinkedList<FriendlyEntity> fe = new LinkedList<FriendlyEntity>();
	private LinkedList<EnemyEntity> ee = new LinkedList<EnemyEntity>();
	
	Game game;
	Random r = new Random();
	/*In our Bullet constructor, we 
	 *need a Game instance <-- The 
	 *reason we make this constructor
	*/
	Controller(Game game) {
		this.game = game;
	}
	public void addEnemy(int enemyCount) {
		for (int i = 0; i < enemyCount; i++) {
			
			addEntity(new Enemy(r.nextInt(640), -10, game, this));
		}
	}
	public void tick() {
		FriendlyEntity entf;
		EnemyEntity ente;
		//A CLASS
		for (int i = 0; i < fe.size(); i++) {
			entf = fe.get(i);
			
			entf.tick();
		}
		
		//B CLASS
		for (int i = 0; i < ee.size(); i++) {
			ente = ee.get(i);
			
			ente.tick();
		}
	}
	
	public void render(Graphics g) {
		FriendlyEntity entf;
		EnemyEntity ente;
		//A CLASS
		for (int i = 0; i < fe.size(); i++) {
			entf = fe.get(i);
			
			entf.render(g);
		}
		
		//B CLASS
		for (int i = 0; i < ee.size(); i++) {
			ente = ee.get(i);
			
			ente.render(g);
		}
	}
	
	public void removeBullets() {
		LinkedList<FriendlyEntity> entitiesToRemove = new LinkedList<>();
		for (FriendlyEntity entf: fe) {
			if(entf.remove()) {
				entitiesToRemove.add(entf);
			}
		}
		
		fe.removeAll(entitiesToRemove);
	}
	
	public void addEntity(FriendlyEntity block) {
		fe.add(block);
	}
	public void removeEntity(FriendlyEntity block) {
		fe.remove(block);
	}
	public void addEntity(EnemyEntity block) {
		ee.add(block);
	}
	public void removeEntity(EnemyEntity block) {
		ee.remove(block);
	}
	
	public LinkedList<FriendlyEntity> getFriendlyEntity() {
		return fe;
	}
	public LinkedList<EnemyEntity> getEnemyEntity() {
		return ee;
	}
}
