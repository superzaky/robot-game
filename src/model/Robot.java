package model;

import robotgame.StartingClass;

public class Robot {
    // Constants are Here
    final int JUMPSPEED = -15;
    final int MOVESPEED = 5;
    final int GROUND = 382;
    
	//centerX, centerY are the x, y coordinates of our robot character's center.
	private int centerX = 100;
	private int centerY = GROUND;
	private boolean jumped = false;
    private boolean movingLeft = false;
    private boolean movingRight = false;
    private boolean ducked = false;
 

    private static Background bg1 = StartingClass.getBg1();                 
    private static Background bg2 = StartingClass.getBg2();
    
	//speedX, speed Y are the rate at which these x and y positions change.
	private int speedX = 0;
	private int speedY = 1;
	

	
	public void update() {
		// Moves Character or Scrolls Background accordingly.
		if (speedX < 0) {
			//speedX is negative, which means that a character with negative speedX would move to the left.
			//This changes centerX by adding speedX.
			centerX += speedX;
		} 
       if (speedX == 0 || speedX < 0) {
    	   	System.out.println("Do not scroll the background.");
            bg1.setSpeedX(0);
            bg2.setSpeedX(0);
        }
       
       if (centerX <= 200 && speedX > 0) {
    	 //If the character's centerX coordinate is less than 200, he can move freely. 
           centerX += speedX;
       }
       
       if (speedX > 0 && centerX > 200){
    	   //we will scroll the background
    	   System.out.println("Scroll Background Here");
           bg1.setSpeedX(-MOVESPEED);
           bg2.setSpeedX(-MOVESPEED);
       }
       
       // Updates Y Position
       //Add speedY to centerY to determine its new position
       centerY += speedY;
       if (centerY + speedY >= GROUND) {
			//The character has a positive speedY, he is FALLING, not RISING.
			//382 is where the character's centerY would be if he were standing on the ground.
           centerY = GROUND;
       }

		// Handles Jumping
		if (jumped == true) {
			//While the character is in the air, add 1 to his speedY.   
			speedY += 1;
			//NOTE: This will bring the character downwards!
			if (centerY + speedY >= GROUND) {
				centerY = GROUND;
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
		if (ducked == false) {
			speedX = MOVESPEED;
		}
	}

	public void moveLeft() {
		if (ducked == false) {
			speedX = -MOVESPEED;
		}
	}

	public void stopRight() {
		setMovingRight(false);
		stop();
	}
	
    public void stopLeft() {
        setMovingLeft(false);
        stop();
    }
	 
	public void stop() {
        if (isMovingRight() == false && isMovingLeft() == false) {
            speedX = 0;
        }
 
        if (isMovingRight() == false && isMovingLeft() == true) {
            moveLeft();
        }
 
        if (isMovingRight() == true && isMovingLeft() == false) {
            moveRight();
        }
	}

	public void jump() {
		if (jumped == false) {
			speedY = JUMPSPEED;
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
    public boolean isDucked() {
        return ducked;
    }
 
    public void setDucked(boolean ducked) {
        this.ducked = ducked;
    }
 
    public boolean isMovingRight() {
        return movingRight;
    }
 
    public void setMovingRight(boolean movingRight) {
        this.movingRight = movingRight;
    }
 
    public boolean isMovingLeft() {
        return movingLeft;
    }
 
    public void setMovingLeft(boolean movingLeft) {
        this.movingLeft = movingLeft;
    }
}
