package easy;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

public class GameLogic extends JPanel {

    private final int OFFSET = 30;
    private final int SPACE = 20;
    private final int LEFT_COLLISION = 1;
    private final int RIGHT_COLLISION = 2;
    private final int TOP_COLLISION = 3;
    private final int BOTTOM_COLLISION = 4;

    private ArrayList<Wall> walls;
    private ArrayList<BlueBox> boxes;
    private ArrayList<Ground> grounds;
    private ArrayList<Goal> goals;

    private Robot robot;

    private int w = 0;
    private int h = 0;

    private ArrayList<String[][]> levels;

    private String level =  "#####\n" +
                            "###   #\n" +
                            "# $ # ##\n" +
                            "# #  . #\n" +
                            "#    # #\n" +
                            "## #   #\n" +
                            " #@  ###\n" +
                            " #####";

    public GameLogic() {
        setFocusable(true);
        initWorld();
    }

    public int getWidth() {
        return w;
    }

    public int getHeight() {
        return h;
    }

    private void initWorld() {
        walls = new ArrayList<>();
        boxes = new ArrayList<>();
        grounds = new ArrayList<>();
        goals = new ArrayList<>();

        int x = OFFSET;
        int y = OFFSET;

        Wall wall;
        BlueBox box;
        Ground ground;
        Goal goal;

        for(int i=0; i<level.length(); ++i) {
            char item = level.charAt(i);

            switch (item) {
                case '\n':
                    y += SPACE;
                    if(this.w < x) {
                        this.w = x;
                    }
                    x = OFFSET;
                    break;
                case '#':
                    wall = new Wall(x, y);
                    walls.add(wall);
                    x += SPACE;
                    break;
                case '$':
                    box = new BlueBox(x, y);
                    boxes.add(box);
                    x += SPACE;
                    break;
                case '.':
                    goal = new Goal(x, y);
                    goals.add(goal);
                    x += SPACE;
                    break;
                case '@':
                    robot = new Robot(x, y);
                    x += SPACE;
                    break;
                case ' ':
                    ground = new Ground(x, y);
                    grounds.add(ground);
                    x += SPACE;
                    break;
                default:
                    break;
            }
        }
    }

    private void buildWorld(Graphics g) {
        g.setColor(new Color(250,240,170));
        g.fillRect(0,0,this.getWidth(), this.getHeight());

        ArrayList<Actor> world = new ArrayList<>();

        world.addAll(walls);
        world.addAll(grounds);
        world.addAll(goals);
        world.addAll(boxes);
        world.addAll(robot);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        buildWorld(g);
    }

    // Canvas panel listener
    private class CanvasPanelListener extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent keyEvent) {
            int code = keyEvent.getKeyCode();

            if(code==KeyEvent.VK_F1) {
                JOptionPane.showMessageDialog(null, "Move robot!");
            } else if(code==KeyEvent.VK_UP) {
                // game.moveUp();
            } else if(code==KeyEvent.VK_DOWN) {
                // game.moveDown();
            } else if(code==KeyEvent.VK_LEFT) {
                // game.moveLeft();
            } else if(code==KeyEvent.VK_RIGHT) {
                // game.moveRight();
            }
            repaint();
        }
    }
}