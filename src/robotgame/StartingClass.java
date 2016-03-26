package robotgame;

import java.applet.Applet;
import java.awt.Color;
import java.awt.Frame;

public class StartingClass extends Applet implements Runnable {
	@Override
	public void init() {
		setSize(800, 480);
		setBackground(Color.BLACK);
		/* This statement makes sure that when the game starts, the applet takes focus and that
		 * our input goes directly into it. If this is not enabled, then you would have to click inside the applet
		 * before it starts handling keyboard events. */
		setFocusable(true);
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
			 * repaint(); - built in method - calls the paint method (in which we draw objects onto the screen).
			 * We haven't created the paint method yet, but every 17 milliseconds, the paint method will be called..
			 *  */
			repaint();
			try {
				Thread.sleep(17);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
