package model;

import java.awt.Rectangle;

import robotgame.StartingClass;

public class Enemy {
    private int power, speedX, centerX, centerY;
    private Background bg = StartingClass.getBg1();
    
    public Rectangle r = new Rectangle(0,0,0,0);
    
    public int health = 5;

    // Behavioral Method
    public void update() {
        centerX += speedX;
        speedX = bg.getSpeedX()*5;
        r.setBounds(centerX - 25, centerY-25, 50, 60);
        
        if (r.intersects(Robot.yellowRed)){
            checkCollision();
        }
    }
    
    private void checkCollision() {
        if (r.intersects(Robot.rect) || r.intersects(Robot.rect2) || r.intersects(Robot.rect3) || r.intersects(Robot.rect4)){
            System.out.println("collision");
        }
    }

    // Behavioral Method
    public void die() {
    }

    // Behavioral Method
    public void attack() {
    }

    public int getPower() {
        return power;
    }

    public int getSpeedX() {
        return speedX;
    }

    public int getCenterX() {
        return centerX;
    }

    public int getCenterY() {
        return centerY;
    }

    public Background getBg() {
        return bg;
    }

    public void setPower(int power) {
        this.power = power;
    }

    public void setSpeedX(int speedX) {
        this.speedX = speedX;
    }

    public void setCenterX(int centerX) {
        this.centerX = centerX;
    }

    public void setCenterY(int centerY) {
        this.centerY = centerY;
    }

    public void setBg(Background bg) {
        this.bg = bg;
    }
}
