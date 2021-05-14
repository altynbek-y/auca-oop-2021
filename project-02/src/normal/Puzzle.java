package normal;

import normal.GameModel;
import normal.actors.BlueBox;
import normal.actors.Goal;
import normal.actors.Robot;

import java.util.ArrayList;

public class Puzzle {

    private char[][] data;
    private int height;
    private int width;
    private int moves;
    private int maxMoves;
    private int boxId;

    private ArrayList<BlueBox> boxes;
    private ArrayList<Goal> goals;
    private Robot robot;
    private GameModel game;

    public Puzzle(char[][] level, GameModel game) {
        this.game = game;
        this.boxes = new ArrayList<>();
        this.goals = new ArrayList<>();
        this.height = level.length;
        this.width = level[0].length;
        this.moves = 0;
        this.boxId = 0;
        this.data = new char[height][width];

        for(int r=0; r<height; ++r) {
            for(int c=0; c<width; ++c) {
                if(level[r][c]=='R') {
                    robot = new Robot(r,c);
                    data[r][c] = ' ';
                } else if(level[r][c]=='$') {
                    boxes.add(new BlueBox(r,c,++boxId));
                    data[r][c] = ' ';
                } else if(level[r][c]=='E') {
                    goals.add(new Goal(r,c));
                    data[r][c] = ' ';
                } else {
                    data[r][c] = level[r][c];
                }
            }
        }
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public char get(int row, int col) {
        return data[row][col];
    }

    public char getCurElement(int row, int col) {
        return data[row][col];
    }

    public int getMoves() {
        return moves;
    }

    public void decreaseMoves() {
        if(moves>0)
            moves-=1;
    }

    public void increaseMoves() {
        if(moves<maxMoves)
            moves+=1;
    }

    // Move robot and box
    public void move(int dr, int dc, int collision) {
        switch (collision) {
            case GameModel.LEFT_COLLISION:
                for (BlueBox box : boxes) {
                    if (isLeftCollision(robot.getRow(), robot.getCol(), box.getRow(), box.getCol())) {
                        if (moveBox(dr, dc, box)) {
                            game.addState(new State(dr, dc, collision, box.getId()));
                            moveRobot(dr, dc, true);
                        }
                        return;
                    }
                }
                moveRobot(dr,dc,true);
                game.addState(new State(dr, dc, collision));
                break;
            case GameModel.RIGHT_COLLISION:
                for (BlueBox box : boxes) {
                    if (isRightCollision(robot.getRow(), robot.getCol(), box.getRow(), box.getCol())) {
                        if (moveBox(dr, dc, box)) {
                            game.addState(new State(dr, dc, collision, box.getId()));
                            moveRobot(dr, dc,true);
                        }
                        return;
                    }
                }
                moveRobot(dr,dc,true);
                game.addState(new State(dr, dc, collision));
                break;
            case GameModel.TOP_COLLISION:
                for (BlueBox box : boxes) {
                    if (isTopCollision(robot.getRow(), robot.getCol(), box.getRow(), box.getCol())) {
                        if (moveBox(dr, dc, box)) {
                            game.addState(new State(dr, dc, collision, box.getId()));
                            moveRobot(dr, dc,true);
                        }
                        return;
                    }
                }
                moveRobot(dr,dc,true);
                game.addState(new State(dr, dc, collision));
                break;
            case GameModel.BOTTOM_COLLISION:
                for (BlueBox box : boxes) {
                    if (isBottomCollision(robot.getRow(), robot.getCol(), box.getRow(), box.getCol())) {
                        if (moveBox(dr, dc, box)) {
                            game.addState(new State(dr, dc, collision, box.getId()));
                            moveRobot(dr, dc,true);
                        }
                        return;
                    }
                }
                moveRobot(dr,dc,true);
                game.addState(new State(dr, dc, collision));
                break;
            case GameModel.TIME_TRAVEL:
                moveRobot(dr,dc,false);
                State state = game.getState();

                for (BlueBox box : boxes) {
                    if (state.isHasMovedBox())
                        if (state.getBoxId() == box.getId()) {
                            moveBox(dr, dc, box);
                            break;
                        }
                }
                break;
            default:
                break;
        }
    }

    // Move robot if possible
    public void moveRobot(int dr, int dc, boolean isCount) {
        int tRow = robot.getRow()+dr;
        int tCol = robot.getCol()+dc;

        if(data[tRow][tCol] == ' ') {
            if(isCount) {
                ++moves;
                maxMoves = moves;
            }
            robot.setLocation(tRow, tCol);
        }
    }

    // Move box if possible
    public boolean moveBox(int dr, int dc, BlueBox box) {
        int tRow = box.getRow()+dr;
        int tCol = box.getCol()+dc;

        if(data[tRow][tCol] == ' ') {
            box.setLocation(tRow, tCol);
            return true;
        }
        return false;
    }

    // Four direction collision detection
    public boolean isLeftCollision(int robotRow, int robotCol, int boxRow, int boxCol) {
        return robotRow == boxRow && robotCol-1 == boxCol;
    }

    // Is right collision
    public boolean isRightCollision(int robotRow, int robotCol, int boxRow, int boxCol) {
        return robotRow == boxRow && robotCol+1 == boxCol;
    }

    // Is top collision
    public boolean isTopCollision(int robotRow, int robotCol, int boxRow, int boxCol) {
        return robotCol == boxCol && robotRow-1 == boxRow;
    }

    // Is bottom collision
    public boolean isBottomCollision(int robotRow, int robotCol, int boxRow, int boxCol) {
        return robotCol == boxCol && robotRow+1 == boxRow;
    }

    // Check if the game is finished
    public boolean isWin() {
        int numberOfBoxes = boxes.size(), finishedBoxes = 0;
        for (int i = 0; i<numberOfBoxes; ++i) {
            if (goals.get(i).getRow() == boxes.get(i).getRow() && goals.get(i).getCol() == boxes.get(i).getCol()) {
                boxes.get(i).setInGoal(true);
                ++finishedBoxes;
            } else {
                boxes.get(i).setInGoal(false);
            }
        }
        return finishedBoxes==numberOfBoxes;
    }

    // Get boxes
    public ArrayList<BlueBox> getBoxes() {
        return boxes;
    }

    // Get goals
    public ArrayList<Goal> getGoals() {
        return goals;
    }

    // Get robot
    public Robot getRobot() {
        return robot;
    }
}

