
import java.util.ArrayList;
import java.util.HashMap;

public class Main {

	public static void main(String[] args) {
		Board board = new Board();
		InputOutput file = new InputOutput();
		ArrayList<Properties> squares = file.readProperty();
		HashMap<String, ArrayList<String>> cards = file.readCards();
		board.setup(squares, cards);
		ArrayList<String> commands = InputOutput.readCommands(args[0]);
		
		board.execute_commands(commands);
		
		
		
	}

}
