package robotgame;

import java.applet.Applet;
import java.awt.Color;
import java.awt.Frame;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class StartingClass extends Applet implements Runnable, KeyListener {
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
	}

	@Override
	public void start() {
		// super.start();
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
	            System.out.println("Move left");
	            break;

	        case KeyEvent.VK_RIGHT:
	            System.out.println("Move right");
	            break;

	        case KeyEvent.VK_SPACE:
	            System.out.println("Jump");
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
				System.out.println("Stop moving left");
				break;
	
			case KeyEvent.VK_RIGHT:
				System.out.println("Stop moving right");
				break;
	
			case KeyEvent.VK_SPACE:
				System.out.println("Stop jumping");
				break;
		}
	}
}
