package model;

public class Background {
	private int bgX, bgY, speedX;

	public Background(int x, int y) {
		bgX = x;
		bgY = y;
		speedX = 0;
	}

	public void update() {
		bgX += speedX;
		/*
		 * When the x coordinate of the background is at -2160 or below (it is
		 * no longer visible), we move it up by 4320 pixels (so that it can go
		 * 2160 pixels ahead of the currently visible image.
		 */
		if (bgX <= -2160) {
			bgX += 4320;
		}
	}

	public int getBgX() {
		return bgX;
	}

	public int getBgY() {
		return bgY;
	}

	public int getSpeedX() {
		return speedX;
	}

	public void setBgX(int bgX) {
		this.bgX = bgX;
	}

	public void setBgY(int bgY) {
		this.bgY = bgY;
	}

	public void setSpeedX(int speedX) {
		this.speedX = speedX;
	}

}
