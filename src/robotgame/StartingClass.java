package robotgame;

import java.applet.Applet;
import java.awt.Color;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.net.URL;

import model.Background;
import model.Heliboy;
import model.Robot;

public class StartingClass extends Applet implements Runnable, KeyListener {
	private Robot robot;
	private Heliboy hb, hb2;
	private Image image, currentSprite, character, characterDown, characterJumped, background, heliboy;
	private URL base;
	private Graphics second;
	//We make them static so that we can create getters and setters for them to be used in othe classes for movement)
	private static Background bg1, bg2;
	
	@Override
	public void init() {
		setSize(800, 480);
		setBackground(Color.BLACK);
		/* This statement makes sure that when the game starts, the applet takes
		 * focus and that our input goes directly into it. If this is not
		 * enabled, then you would have to click inside the applet before it
		 * starts handling keyboard events. */
		setFocusable(true);
		addKeyListener(this);
		Frame frame = (Frame) this.getParent().getParent();
		frame.setTitle("Q-Bot Alpha");
		try {
			base = getDocumentBase();
		} catch (Exception e) {
			// TODO: handle exception
		}
		// Image Setups
		
		character = getImage(base, "data/character.png");
		characterDown = getImage(base, "data/down.png");
		characterJumped = getImage(base, "data/jumped.png");
		currentSprite = character;
		background = getImage(base, "data/background.png");
		heliboy = getImage(base, "data/heliboy.png");
	}

	@Override
	public void start() {
		// super.start();
		bg1 = new Background(0, 0);
		bg2 = new Background(2160, 0);
		hb = new Heliboy(340, 360);
		hb2 = new Heliboy(700, 360);
		robot = new Robot();
		Thread thread = new Thread(this);
		thread.start();
	}

	@Override
	public void stop() {

	}

	@Override
	public void destroy() {

	}

	@Override
	public void run() {
		while (true) {
			robot.update();
			if (robot.isJumped()){
				currentSprite = characterJumped;
			}else if (robot.isJumped() == false && robot.isDucked() == false){
				currentSprite = character;
			}
			hb.update();
			hb2.update();
			bg1.update();
			bg2.update();
			/*
			 * repaint(); - built in method - calls the paint method (in which
			 * we draw objects onto the screen). We haven't created the paint
			 * method yet, but every 17 milliseconds, the paint method will be
			 * called..
			 */
			repaint();
			try {
				Thread.sleep(17);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	/* The update() method is implicitly called automatically, and will loop over and over again.
	 * The paint() method will similarly be always called, with the repaint() statement within the run() method.
	 * We will use update() method for double buffering - a technique that is used to prevent tearing
	 * and flickering. Feel free to read up more on this subject, as I am not an expert in this topic. 
	 * The basic concept is that it works by retaining the previous position of the screen's current image 
	 * for a short amount of time, so that the movement of the image looks smooth and natural.
	*/
	@Override
	public void update(Graphics g) {
		if (image == null) {
			image = createImage(this.getWidth(), this.getHeight());
			second = image.getGraphics();
		}
		second.setColor(getBackground());
		second.fillRect(0, 0, getWidth(), getHeight());
		second.setColor(getForeground());
		paint(second);

		g.drawImage(image, 0, 0, this);
	}
	
    @Override
    public void paint(Graphics g) {
    	g.drawImage(background, bg1.getBgX(), bg1.getBgY(), this);
    	g.drawImage(background, bg2.getBgX(), bg2.getBgY(), this);
    	/* we will use the currentSprite variable to represent our robot image, and then draw the top left corner
    	 * of the robot 61 pixels to the left, and 63 pixels above the (centerX, centerY), and then use 
    	 * the "this" keyword as our ImageObserver.*/
    	g.drawImage(currentSprite, robot.getCenterX() - 61, robot.getCenterY() - 63, this);
    	g.drawImage(heliboy, hb.getCenterX() - 48, hb.getCenterY() - 48, this);
    	g.drawImage(heliboy, hb2.getCenterX() - 48, hb2.getCenterY() - 48, this);
    }
	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
	}

	@Override
	public void keyPressed(KeyEvent e) {
	     switch (e.getKeyCode()) {
	        case KeyEvent.VK_UP:
	            System.out.println("Move up");
	            break;
	        case KeyEvent.VK_DOWN:
	            currentSprite = characterDown;
	            if (robot.isJumped() == false){
	                robot.setDucked(true);
	                robot.setSpeedX(0);
	            }
	            break;
	        case KeyEvent.VK_LEFT:
	            robot.moveLeft();
	            robot.setMovingLeft(true);
	            break;
	        case KeyEvent.VK_RIGHT:
	            robot.moveRight();
	            robot.setMovingRight(true);	
	            break;
	        case KeyEvent.VK_SPACE:
	            System.out.println("Jump");
	            robot.jump();
	            break;
        }
	}

	@Override
	public void keyReleased(KeyEvent e) {
		switch (e.getKeyCode()) {
			case KeyEvent.VK_UP:
				System.out.println("Stop moving up");
				break;
			case KeyEvent.VK_DOWN:
				currentSprite = character;
				robot.setDucked(false);
				break;
			case KeyEvent.VK_LEFT:
				robot.stopLeft();
//				robot.stop();
				break;
			case KeyEvent.VK_RIGHT:
				robot.stopRight();
//				robot.stop();
				break;
			case KeyEvent.VK_SPACE:
				System.out.println("Stop jumping");
				break;
		}
	}
	
    public static Background getBg1() {
        return bg1;
    }

    public static Background getBg2() {
        return bg2;
    }
}
