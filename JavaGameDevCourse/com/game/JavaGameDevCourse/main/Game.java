// Try not to use much static
package com.game.JavaGameDevCourse.main;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.LinkedList;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import com.game.JavaGamDevCourse.entities.EnemyEntity;
import com.game.JavaGamDevCourse.entities.FriendlyEntity;

public class Game extends Canvas implements Runnable {

	private static final long serialVersionUID = 1L;
	public static final int WIDTH = 320; // static final means it is constant and will never change
	public static final int HEIGHT = WIDTH / 12 * 9;
	public static final int SCALE = 2;
	public static int HEALTH = 250;

	private boolean running = false;
	static long score;
	private Thread thread;

	//Buffered Images load an image before projecting it and load imgs quicker
	private BufferedImage image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
	private BufferedImage spriteSheet = null;
	private BufferedImage background = null;
	private BufferedImage menuBackground;
	static Graphics2D g;

	private boolean shooting = false;

	private int enemyCount = 5;
	int enemyKilled = 0;

	private Player p;
	private Controller c;
	private Menu menu;

	public LinkedList<FriendlyEntity> fe;
	public LinkedList<EnemyEntity> ee;

	enum STATE {
		MENU,
		GAME,
		HELP
	};

	public static STATE state = STATE.MENU;

	public Game() {
		score = 0;
	}

	public void init() {
		requestFocus();
		BufferedImageLoader loader = new BufferedImageLoader();
		try {
			spriteSheet = loader.loadImage("/spriteSheet.PNG");
			background = loader.loadImage("/background2.png");
			menuBackground = loader.loadImage("/menuBackground.png");

		}
		catch(IOException e) {
			e.printStackTrace();
		}
		c = new Controller(this);
		p = new Player(300 , 800, this, c);
		menu = new Menu();

		fe = c.getFriendlyEntity();
		ee = c.getEnemyEntity();

		this.addKeyListener(new KeyInput(this));
		this.addMouseListener(new MouseInput());

		c.addEnemy(enemyCount);
	}
	//Above; Loads whole window

	public synchronized void start() { //Initialize thread
		if(running) { //If running is false, break out of the stop method
			return;
		}

		running = true;
		thread = new Thread(this);
		thread.start();
	}

	private synchronized void stop() { //Initialize thread
		if(!running) { //If running is false, break out of the start method
			return;
		}

		running = false;
		try {
			thread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.exit(1);
	}

	public static void main(String[] args) {
		Game game = new Game();
		JFrame frame = new JFrame("Food Fighters PC");

		game.setPreferredSize(new Dimension(WIDTH * SCALE, HEIGHT * SCALE));
		game.setMaximumSize(new Dimension(WIDTH * SCALE, HEIGHT * SCALE));
		game.setMinimumSize(new Dimension(WIDTH * SCALE, HEIGHT * SCALE));

		frame.add(game);
		frame.pack();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		game.start();
	}
	public BufferedImage getSpriteSheet() {
		return spriteSheet;
	}
	//The game loop is where the game constantly updates
	//The game loop is essential in any game
	//Game loop cycles until you exit your game
	//Game loop also counts FPS

	public void run() {
		/*Long is almost the same as an int
		 * Long can store a higher positive and lower negative #
		 */
		init();
		long lastTime = System.nanoTime(); 
		final double amountOfTicks = 60.0;
		double ns = 1000000000 / amountOfTicks;
		double delta = 0;
		int updates = 0;
		int frames = 0;
		long timer = System.currentTimeMillis(); //Current time in millisecond return

		while(running) { //GAME LOOP
			long now = System.nanoTime();
			delta += (now - lastTime) / ns;
			lastTime = now; //Sets lastTime to current time
			if (delta >= 1) {
				tick();
				updates++;
				delta--;
			}
			render();
			frames++;

			if(System.currentTimeMillis() - timer > 1000) {
				timer += 1000;
				System.out.println(String.format("%d Ticks, FPS: %d, ENTITY COUNT: %d, SCORE: %d",
						updates, frames, c.getFriendlyEntity().size(), score));
				updates = 0; //Resets updates
				frames = 0; //Resets frames
			}
		}

		stop();
	}

	//Everything in the game that renders
	//Creates BufferStrategy which controls all loading
	/*111 (this.getBufferStrategy): 
	 *Returns the BufferStrategy used
	 *by Canvas
	 */
	public void render() { 
		//Initializes BufferStrategy
		BufferStrategy bs = this.getBufferStrategy();

		//Ensures we only initialize bs once
		if(bs == null) {
			//Hover 121 for info on 121
			createBufferStrategy(3); 
			return;
		}
		//Draws out Buffers
		Graphics g = bs.getDrawGraphics();
		//////////////////////////////////


		g.drawImage(image, 0, 0, getWidth(), getHeight(), this);
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, 800, 800);

		g.drawImage(background, 0, 0, null);

		if (state == STATE.MENU) {
			g.drawImage(menuBackground, 0, 0, this);
		}

		if (state == STATE.GAME) {
			p.render(g);
			c.render(g);

			g.setColor(Color.gray);
			g.fillRect(5, 5, 250, 50);

			g.setColor(Color.green);
			g.fillRect(5, 5, HEALTH, 50);

			g.setColor(Color.white);
			g.drawRect(5, 5, 250, 50);


		} else if (state == state.MENU) {
			menu.render(g);
		}

		////////////
		g.dispose();
		bs.show();
	}


	public void keyPressed(KeyEvent e) {
		int key = e.getKeyCode();

		if(state == STATE.GAME) {
			if (key == KeyEvent.VK_RIGHT) {
				p.setVelX(5);
			}

			else if (key == KeyEvent.VK_LEFT) {
				p.setVelX(-5);
			} else if (key == KeyEvent.VK_DOWN) {
				p.setVelY(5);
			} else if (key == KeyEvent.VK_UP) {
				p.setVelY(-5);
			}
			if (key == KeyEvent.VK_SPACE && !shooting) {
				shooting = true;
				c.addEntity(new Fries(p.getX(),p.getY()));
			}
		}
	}

	public void keyReleased(KeyEvent e) {
		int key = e.getKeyCode();

		if (key == KeyEvent.VK_RIGHT) {
			p.setVelX(0);
		}

		else if (key == KeyEvent.VK_LEFT) {
			p.setVelX(0);
		} else if (key == KeyEvent.VK_DOWN) {
			p.setVelY(0);
		} else if (key == KeyEvent.VK_UP) {
			p.setVelY(0);
		}
		else if (key == KeyEvent.VK_SPACE) {
			shooting = false;
		}
	}


	public void tick() { //Everything in the game that updates
		if(state == STATE.GAME) {
			p.tick();
			c.removeBullets();
			c.tick();

		}

		if (enemyKilled >= enemyCount) {
			enemyCount += 2;
			enemyKilled = 0;
			c.addEnemy(enemyCount);
		}

		if(state == STATE.HELP) {
			JOptionPane.showMessageDialog(null, "Press ARROW KEYS for movement and SPACE to fire fries from the burger. And hurry, you are obese foods last hope!");
			JOptionPane.showMessageDialog(null, "You lose health by letting the enemies off the screen or getting hit by them.");
			state = STATE.GAME;
		}
	}

	public int getEnemyCount() {
		return enemyCount;
	}

	public void setEnemyCount(int enemyCount) {
		this.enemyCount = enemyCount;
	}

	public int getEnemyKilled() {
		return enemyKilled;
	}

	public void setEnemyKilled(int enemyKilled) {
		this.enemyKilled = enemyKilled;
	}


}
