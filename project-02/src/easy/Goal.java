package easy;

import java.awt.*;

public class Goal extends Actor {

    public Goal(int row, int col) {
        super(row, col);
    }

    public static Image getImage() {
        String imagePath = "/home/iman/Documents/AUCA/SP/Java/auca-oop-2021/project-02/src/easy/resources/Goal.png";
        Toolkit t = Toolkit.getDefaultToolkit();
        return  t.getImage(imagePath);
    }
}
