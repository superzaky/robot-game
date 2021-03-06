package robotgame;

import java.applet.Applet;
import java.awt.Color;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

import model.Background;
import model.Heliboy;
import model.Projectile;
import model.Robot;
import model.Tile;
import robotgame.framework.Animation;

public class StartingClass extends Applet implements Runnable, KeyListener {
    private static Robot robot;
    public static Heliboy hb, hb2;
    //int variable to store the score
    public static int score = 0;
    //Font object to display the score
    private Font font = new Font(null, Font.BOLD, 30);
    private Image image, currentSprite, character, character2, character3, characterDown,
    characterJumped, background, heliboy, heliboy2, heliboy3, heliboy4, heliboy5;
    
    public static Image tilegrassTop, tilegrassBot, tilegrassLeft, tilegrassRight, tiledirt;
    
    private URL base;
    // Anim will be used to animate the main character and hanim to animate the heliboys.
    private Animation anim, hanim;
    
    private ArrayList<Tile> tilearray;
    
    private Graphics second;
    // We make them static so that we can create getters and setters for them to
    // be used in othe classes for movement)
    private static Background bg1, bg2;
    
    enum GameState {
        Running, Dead
    }

    GameState state = GameState.Running;

    @Override
    public void init() {
        setSize(800, 480);
        setBackground(Color.BLACK);
        /*
         * This statement makes sure that when the game starts, the applet takes
         * focus and that our input goes directly into it. If this is not
         * enabled, then you would have to click inside the applet before it
         * starts handling keyboard events.
         */
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
        character2 = getImage(base, "data/character2.png");
        character3 = getImage(base, "data/character3.png");
        
        characterDown = getImage(base, "data/down.png");
        characterJumped = getImage(base, "data/jumped.png");
        
        heliboy = getImage(base, "data/heliboy.png");
        heliboy2 = getImage(base, "data/heliboy2.png");
        heliboy3 = getImage(base, "data/heliboy3.png");
        heliboy4 = getImage(base, "data/heliboy4.png");
        heliboy5 = getImage(base, "data/heliboy5.png");

        background = getImage(base, "data/background.png");
        
        tiledirt = getImage(base, "data/tiledirt.png");
        tilegrassTop = getImage(base, "data/tilegrasstop.png");
        tilegrassBot = getImage(base, "data/tilegrassbot.png");
        tilegrassLeft = getImage(base, "data/tilegrassleft.png");
        tilegrassRight = getImage(base, "data/tilegrassright.png");

        anim = new Animation();
        anim.addFrame(character, 1250);
        anim.addFrame(character2, 50);
        anim.addFrame(character3, 50);
        anim.addFrame(character2, 50);
        
        hanim = new Animation();
        hanim.addFrame(heliboy, 100);
        hanim.addFrame(heliboy2, 100);
        hanim.addFrame(heliboy3, 100);
        hanim.addFrame(heliboy4, 100);
        hanim.addFrame(heliboy5, 100);
        hanim.addFrame(heliboy4, 100);
        hanim.addFrame(heliboy3, 100);
        hanim.addFrame(heliboy2, 100);
        
        currentSprite = anim.getImage();
    }

    @Override
    public void start() {
        // super.start();
        tilearray = new ArrayList<Tile>();
        bg1 = new Background(0, 0);
        bg2 = new Background(2160, 0);
        robot = new Robot();
        Robot.init();
        
        // Initialize Tiles
        try {
            loadMap("data/map1.txt");
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        hb = new Heliboy(340, 360);
        hb2 = new Heliboy(700, 360);
        Thread thread = new Thread(this);
        thread.start();
    }

    private void loadMap(String filename) throws IOException {
        ArrayList lines = new ArrayList();
        int width = 0;
        int height = 0;

        BufferedReader reader = new BufferedReader(new FileReader(filename));
        while (true) {
            String line = reader.readLine();
            // no more lines to read
            if (line == null) {
                reader.close();
                break;
            }
            //If the line begins with an "!", we ignore it (We used ! to begin comments in the map file).
            if (!line.startsWith("!")) {
                lines.add(line);
                width = Math.max(width, line.length());

            }
        }
        height = lines.size();

        for (int j = 0; j < 12; j++) {
            String line = (String) lines.get(j);
            for (int i = 0; i < width; i++) {
                System.out.println(i + "is i ");

                if (i < line.length()) {
                    char ch = line.charAt(i);
                    /*Since the characters we read from the text file are characters, not integers (there's a 
                     * distinction between '1' and 1 much like there's a distinction between "a" and a), we use a 
                     * built-in method: Character.getNumericValue(ch) to convert it to a number.*/
                    Tile t = new Tile(i, j, Character.getNumericValue(ch));
                    tilearray.add(t);
                }
            }
        }
    }
    
    @Override
    public void stop() {

    }

    @Override
    public void destroy() {

    }

    @Override
    public void run() {
        
        if (state == GameState.Running) { 
            while (true) {
                robot.update();
                if (robot.isJumped()) {
                    currentSprite = characterJumped;
                } else if (robot.isJumped() == false && robot.isDucked() == false) {
                    currentSprite = anim.getImage();
                }
                
                ArrayList projectiles = robot.getProjectiles();
                for (int i = 0; i < projectiles.size(); i++) {
                    Projectile p = (Projectile) projectiles.get(i);
                    if (p.isVisible() == true) {
                        p.update();
                    } else {
                        projectiles.remove(i);
                    }
                }
                
                updateTiles();
                hb.update();
                hb2.update();
                bg1.update();
                bg2.update();
                
                animate();
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
                if (robot.getCenterY() > 500) {
                    state = GameState.Dead;
                }
            }
        }
    }
    
    // The parameters (adjust as necessary) make it easy to change how quickly you animate. 
    // The higher the value, the faster each frame changes.
    public void animate() {
        anim.update(10);
        hanim.update(50);
    }

    /*
     * The update() method is implicitly called automatically, and will loop
     * over and over again. The paint() method will similarly be always called,
     * with the repaint() statement within the run() method. We will use
     * update() method for double buffering - a technique that is used to
     * prevent tearing and flickering. Feel free to read up more on this
     * subject, as I am not an expert in this topic. The basic concept is that
     * it works by retaining the previous position of the screen's current image
     * for a short amount of time, so that the movement of the image looks
     * smooth and natural.
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
        if (state == GameState.Running) {
            g.drawImage(background, bg1.getBgX(), bg1.getBgY(), this);
            g.drawImage(background, bg2.getBgX(), bg2.getBgY(), this);
            paintTiles(g);
            
            ArrayList projectiles = robot.getProjectiles();
            for (int i = 0; i < projectiles.size(); i++) {
                Projectile p = (Projectile) projectiles.get(i);
                g.setColor(Color.YELLOW);
                g.fillRect(p.getX(), p.getY(), 10, 5);
            }
            
            g.drawRect((int)robot.rect.getX(), (int)robot.rect.getY(), (int)robot.rect.getWidth(), (int)robot.rect.getHeight());
            g.drawRect((int)robot.rect2.getX(), (int)robot.rect2.getY(), (int)robot.rect2.getWidth(), (int)robot.rect2.getHeight());
            g.drawRect((int)robot.rect3.getX(), (int)robot.rect3.getY(), (int)robot.rect3.getWidth(), (int)robot.rect3.getHeight());
            g.drawRect((int)robot.rect4.getX(), (int)robot.rect4.getY(), (int)robot.rect4.getWidth(), (int)robot.rect4.getHeight());
            g.drawRect((int)robot.yellowRed.getX(), (int)robot.yellowRed.getY(), (int)robot.yellowRed.getWidth(), (int)robot.yellowRed.getHeight());
            /*
             * we will use the currentSprite variable to represent our robot image,
             * and then draw the top left corner of the robot 61 pixels to the left,
             * and 63 pixels above the (centerX, centerY), and then use the "this"
             * keyword as our ImageObserver.
             */
            g.drawImage(currentSprite, robot.getCenterX() - 61, robot.getCenterY() - 63, this);
            g.drawImage(hanim.getImage(), hb.getCenterX() - 48, hb.getCenterY() - 48, this);
            g.drawImage(hanim.getImage(), hb2.getCenterX() - 48, hb2.getCenterY() - 48, this);
            g.setFont(font);
            //sets g to white, so that whatever follows it will be done in white (unless it is an image).
            g.setColor(Color.WHITE);
            //draws a String by parsing the integer variable score as a String object. It does this at the location 740, 30.
            g.drawString(Integer.toString(score), 740, 30); 
        } else if (state == GameState.Dead) {
            g.setColor(Color.BLACK);
            g.fillRect(0, 0, 800, 480);
            g.setColor(Color.WHITE);
            g.drawString("Dead", 360, 240);
            g.drawString("Press ENTER to restart", 215, 290);
        }

            
    }

    private void updateTiles() {
        for (int i = 0; i < tilearray.size(); i++) {
            Tile t = (Tile) tilearray.get(i);
            t.update();
        }
    }

    private void paintTiles(Graphics g) {
        for (int i = 0; i < tilearray.size(); i++) {
            Tile t = (Tile) tilearray.get(i);
            g.drawImage(t.getTileImage(), t.getTileX(), t.getTileY(), this);
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
            currentSprite = characterDown;
            if (robot.isJumped() == false) {
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
        case KeyEvent.VK_CONTROL:
            if (robot.isDucked() == false && robot.isJumped() == false) {
                robot.shoot();
                robot.setReadyToFire(false);
            }
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
            currentSprite = anim.getImage();
            robot.setDucked(false);
            break;
        case KeyEvent.VK_LEFT:
            robot.stopLeft();
            // robot.stop();
            break;
        case KeyEvent.VK_RIGHT:
            robot.stopRight();
            // robot.stop();
            break;
        case KeyEvent.VK_SPACE:
            System.out.println("Stop jumping");
            break;
        case KeyEvent.VK_CONTROL:
            robot.setReadyToFire(true);
            break;
        case KeyEvent.VK_ENTER:
            if (state == GameState.Dead) {
                restart();
            }
            break;
        }
    }
    
    private void restart() {
        tilearray = null;
        bg2 = null;
        bg1 = null;
        robot = Robot.getNull();
        hb = null;
        hb2 = null;
        score = 0;
        state = GameState.Running;

        start();
    }

    public static Background getBg1() {
        return bg1;
    }

    public static Background getBg2() {
        return bg2;
    }
    
    public static Robot getRobot(){
        return robot;
    }
}
