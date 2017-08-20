package model;

import java.awt.Rectangle;
import java.util.ArrayList;

import robotgame.StartingClass;

public class Robot {
    // Constants are Here
    final int JUMPSPEED = -15;
    final int MOVESPEED = 5;

    // centerX, centerY are the x, y coordinates of our robot character's
    // center.
    private int centerX = 100;
    private int centerY = 377;
    private boolean jumped = false;
    private boolean movingLeft = false;
    private boolean movingRight = false;
    private boolean ducked = false;
    private boolean readyToFire = true;

    private static Background bg1 = StartingClass.getBg1();
    private static Background bg2 = StartingClass.getBg2();

    // speedX, speed Y are the rate at which these x and y positions change.
    private int speedX = 0;
    private int speedY = 0;
    //rect refers to the higher body and rect2 refers to the lower body
    public static Rectangle rect = new Rectangle(0, 0, 0, 0);
    public static Rectangle rect2 = new Rectangle(0, 0, 0, 0);
    //rect3 refers to the left hand and rect4 refers to the right hand
    public static Rectangle rect3 = new Rectangle(0, 0, 0, 0);
    public static Rectangle rect4 = new Rectangle(0, 0, 0, 0);
    public static Rectangle yellowRed = new Rectangle(0, 0, 0, 0);
    public static Rectangle footleft = new Rectangle(0, 0, 0, 0);
    public static Rectangle footright = new Rectangle(0, 0, 0, 0);

    private ArrayList<Projectile> projectiles = new ArrayList<Projectile>();

    public void update() {
        // Moves Character or Scrolls Background accordingly.
        if (speedX < 0) {
            // speedX is negative, which means that a character with negative
            // speedX would move to the left.
            // This changes centerX by adding speedX.
            centerX += speedX;
        }
        if (speedX == 0 || speedX < 0) {
            System.out.println("Do not scroll the background.");
            bg1.setSpeedX(0);
            bg2.setSpeedX(0);
        }

        if (centerX <= 200 && speedX > 0) {
            // If the character's centerX coordinate is less than 200, he can
            // move freely.
            centerX += speedX;
        }

        if (speedX > 0 && centerX > 200) {
            // we will scroll the background
            System.out.println("Scroll Background Here");
            
            bg1.setSpeedX(-MOVESPEED/5);
            bg2.setSpeedX(-MOVESPEED/5);
        }

        // Updates Y Position
        // Add speedY to centerY to determine its new position
        centerY += speedY;

        // Handles Jumping
        //Add 1 to his speedY.
        speedY += 1;

        // This if statements prevents small fluctuations in speedY from registering as jumps.
        if (speedY > 3) {
            jumped = true;
        }

        // Prevents going beyond X coordinate of 0
        if (centerX + speedX <= 60) {
            // If speedX plus centerX would bring the character outside the
            // screen,
            centerX = 61;
            // Fix the character's centerX at 60 pixels.
        }

        rect.setRect(centerX - 34, centerY - 63, 68, 63);
        rect2.setRect(rect.getX(), rect.getY() + 63, 68, 64);
        rect3.setRect(rect.getX() - 26, rect.getY() + 32, 26, 20);
        rect4.setRect(rect.getX() + 68, rect.getY() + 32, 26, 20);
        yellowRed.setRect(centerX - 110, centerY - 110, 180, 180);
        footleft.setRect(centerX - 50, centerY + 20, 50, 15);
        footright.setRect(centerX, centerY + 20, 50, 15);
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

    public void shoot() {
        if (readyToFire) {
            Projectile p = new Projectile(centerX + 50, centerY - 25);
            projectiles.add(p);
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

    public ArrayList<Projectile> getProjectiles() {
        return projectiles;
    }

    public boolean isReadyToFire() {
        return readyToFire;
    }

    public void setReadyToFire(boolean readyToFire) {
        this.readyToFire = readyToFire;
    }
}
