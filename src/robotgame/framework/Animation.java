package robotgame.framework;

import java.awt.Image;
import java.util.ArrayList;

public class Animation {
    private ArrayList frames;
    private int currentFrame; 
    private long animTime; // long takes up more memory than int but can hold more accurate numbers.
    private long totalDuration; //totalDuration, refers to the amount of time that each frame (image) will be displayed for.

    public Animation() {
        frames = new ArrayList();
        totalDuration = 0;

        synchronized (this) {
            animTime = 0;
            currentFrame = 0;
        }
    }
    
    //This method will add take an AnimFrame object and "append" it to the ArrayList called frames.
    public synchronized void addFrame(Image image, long duration) {
        totalDuration += duration;
        frames.add(new AnimFrame(image, totalDuration));
    }

    //The update method will be called repeatedly, and will switch frames as necessary.
    public synchronized void update(long elapsedTime) {
        if (frames.size() > 1) {
            // elapsedTime is the amount of time that passed since the last update. 
            animTime += elapsedTime;
            if (animTime >= totalDuration) {
                animTime = animTime % totalDuration;
                currentFrame = 0;

            }

            while (animTime > getFrame(currentFrame).endTime) {
                currentFrame++;

            }
        }
    }
    
    // This method will return the image that belongs to the currentFrame.
    public synchronized Image getImage() {
        if (frames.size() == 0) {
            return null;
        } else {
            return getFrame(currentFrame).image;
        }
    }

    //returns the current AnimFrame of the animation sequence
    private AnimFrame getFrame(int i) {
        return (AnimFrame) frames.get(i);
    }
    
    private class AnimFrame {
        Image image;
        long endTime;

        public AnimFrame(Image image, long endTime) {
            this.image = image;
            this.endTime = endTime;
        }
    }
}
