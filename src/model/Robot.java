package model;

import java.awt.Graphics;

public class Robot {
	//centerX, centerY are the x, y coordinates of our robot character's center.
	private int centerX = 100;
	private int centerY = 382;
	private boolean jumped = false;
	//speedX, speed Y are the rate at which these x and y positions change.
	private int speedX = 0;
	private int speedY = 1;

	public void update() {

		// Moves Character or Scrolls Background accordingly.
		if (speedX < 0) {
			//speedX is negative, which means that a character with negative speedX would move to the left.
			//This changes centerX by adding speedX.
			centerX += speedX;
		} else if (speedX == 0) {
			System.out.println("Do not scroll the background.");
		} else {
			if (centerX <= 150) {
				//If the character's centerX coordinate is less than 150, he can move freely. 
				centerX += speedX;
			} else {
				//Else, we will scroll the background and stop moving the character.
				System.out.println("Scroll Background Here");
			}
		}
		
		// Updates Y Position

		if (centerY + speedY >= 382) {
			//The character has a positive speedY, he is FALLING, not RISING.
			//382 is where the character's centerY would be if he were standing on the ground.
			centerY = 382;
		}else{               
			//Add speedY to centerY to determine its new position
			centerY += speedY;
        }

		// Handles Jumping
		if (jumped == true) {
			//While the character is in the air, add 1 to his speedY.   
			speedY += 1;
			//NOTE: This will bring the character downwards!
			if (centerY + speedY >= 382) {
				centerY = 382;
				speedY = 0;
				jumped = false;
			}
		}

		// Prevents going beyond X coordinate of 0
		if (centerX + speedX <= 60) {
			//If speedX plus centerX would bring the character outside the screen,
			centerX = 61;
			//Fix the character's centerX at 60 pixels.
		}
	}

	public void moveRight() {
		speedX = 6;
	}

	public void moveLeft() {
		speedX = -6;
	}

	public void stop() {
		speedX = 0;
	}

	public void jump() {
		if (jumped == false) {
			speedY = -15;
			jumped = true;
		}
	}

	public int getCenterX() {
		return centerX;
	}

	public int getCenterY() {
		return centerY;
	}

	public boolean isJumped() {
		return jumped;
	}

	public int getSpeedX() {
		return speedX;
	}

	public int getSpeedY() {
		return speedY;
	}

	public void setCenterX(int centerX) {
		this.centerX = centerX;
	}

	public void setCenterY(int centerY) {
		this.centerY = centerY;
	}

	public void setJumped(boolean jumped) {
		this.jumped = jumped;
	}

	public void setSpeedX(int speedX) {
		this.speedX = speedX;
	}

	public void setSpeedY(int speedY) {
		this.speedY = speedY;
	}
}
