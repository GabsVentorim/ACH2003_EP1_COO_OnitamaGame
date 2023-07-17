import java.util.Scanner;

public class Program {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        GameImpl game = new GameImpl("Ga", "Gu");
        game.printBoard();
    }
}
