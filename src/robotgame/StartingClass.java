package robotgame;

import java.applet.Applet;
import java.awt.Color;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.net.URL;

import model.Robot;

public class StartingClass extends Applet implements Runnable, KeyListener {
	private Robot robot;
	private Image image, character;
	private URL base;
	private Graphics second;
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
	}

	@Override
	public void start() {
		// super.start();
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
    	/* we will use the character variable to represent our robot image, and then draw the top left corner
    	 * of the robot 61 pixels to the left, and 63 pixels above the (centerX, centerY), and then use 
    	 * the "this" keyword as our ImageObserver.*/
    	g.drawImage(character, robot.getCenterX() - 61, robot.getCenterY() - 63, this);
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
	            System.out.println("Move down");
	            break;
	        case KeyEvent.VK_LEFT:
	            robot.moveLeft();
	            break;
	        case KeyEvent.VK_RIGHT:
	            robot.moveRight();
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
				System.out.println("Stop moving down");
				break;
			case KeyEvent.VK_LEFT:
				robot.stop();
				break;
			case KeyEvent.VK_RIGHT:
				robot.stop();
				break;
			case KeyEvent.VK_SPACE:
				System.out.println("Stop jumping");
				break;
		}
	}
}
