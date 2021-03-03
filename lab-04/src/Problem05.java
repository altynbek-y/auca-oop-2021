import java.util.ArrayList;
import java.util.Scanner;

public class Problem05 {
    public static void main(String[] args) {

        Scanner scan = new Scanner(System.in);
        ArrayList<Integer> list = new ArrayList<>();

        while (scan.hasNextInt()) {
            int x = scan.nextInt();
            list.add(x);
        }

        for (int i = 0; i < list.size();) {
            if (list.get(i) % 2 == 0) {
                list.add(i, 0);
                i += 2;
            } else {
                ++i;
            }
        }

        System.out.println("After insertions:");
        System.out.println(list);

        for (int i = 0; i < list.size(); ++i) {
            if (list.get(i) % 2 > 0) {
                list.remove(list.get(i));
            }
        }

        System.out.println("After deletions:");
        System.out.println(list);
    }
}
