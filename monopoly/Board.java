
import java.util.ArrayList;
import java.util.HashMap;

public class Board {
	private Square[] squares = new Square[41];
	private Person[] people = {new Player(), new Player(), new Banker()};
	private Card[] chance_deck = new Card[6];
	private Card[] chest_deck = new Card[11];
	
	
	public void setup(ArrayList<Properties> property_data, HashMap<String, ArrayList<String>> cards) {	
		Square[] squares = getSquares();
		for (int i = 0; i < property_data.size(); i++) {
			squares[property_data.get(i).getSquare_no()] = property_data.get(i);
		}
		
		//Constants
		squares[1] = new Go(1);
		squares[11] = new Jail(11);
		squares[21] = new FreePark(21);
		squares[31] = new GoToJail(31);
		squares[5] = new Tax(5);
		squares[39] = new Tax(39);
		
		//Cards
		squares[8] = squares[23] = squares[37] = new Chance(-1, cards.get("chance"));
		squares[3] = squares[18] = squares[34] = new CommunityChest(-2, cards.get("community_chest"));
	}
	
	public void execute_commands(ArrayList<String> commands) {
		boolean game_over = false;
		for (String command: commands) {
			if (command.equals("show()")) 
				show();
			else { 
				game_over = one_hand(command);
			}
			if (game_over) {
				InputOutput.print_to_out(" goes bankrupt", "same_line");
				break;
			}
		}
		show();
	}
	
	public void show() {
		InputOutput.print_to_out("-----------------------------------------------------------------------------------------------------------","new_line");
		String format;
		for (int i = 0; i < 2; i++) {
			format = "Player "+String.valueOf(i+1)+"\t"+String.valueOf(people[i].getBudget())+"\t"+"have: ";
			for (String property : ((Player)people[i]).getBought_properties()) {
				format = format + property+",";
			}
			format = format.substring(0, format.length()-1);
			InputOutput.print_to_out(format,"new_line");
		}
		format = "Banker\t"+String.valueOf(people[2].getBudget());
		InputOutput.print_to_out(format,"new_line");
		if (people[0].getBudget()>people[1].getBudget()) {
			InputOutput.print_to_out("Winner\tPlayer 1","new_line");
		}else
			InputOutput.print_to_out("Winner\tPlayer 2","new_line");
		InputOutput.print_to_out("-----------------------------------------------------------------------------------------------------------","new_line");
	}
		
	public boolean one_hand(String command) {
		int player_id = Integer.valueOf(command.split(" ")[1].split(";")[0])-1;
		int step = Integer.valueOf(command.split(" ")[1].split(";")[1]);
		String action;
		
		if (!((Player) people[player_id]).isIn_jail()) {	
			int new_location_id = ((Player) people[player_id]).move(step);
			action = squares[new_location_id].do_the_square_command(people, player_id);
			if (((Player) people[player_id]).getInstant_square() != new_location_id && ((Player) people[player_id]).getInstant_square() != 1) {
				new_location_id = ((Player) people[player_id]).getInstant_square();
				action = action + " Player "+(player_id+1)+ squares[new_location_id].do_the_square_command(people, player_id);
			}
		}else{
			action = ((Jail)squares[11]).one_hand_in_jail(people, player_id);
		}
		
		String format = print_format(people, player_id) + "Player "+(player_id+1);
		InputOutput.print_to_out(format, "new_line");
		if (((Player)people[player_id]).isBankrupt()) {
			return true;
		}
		InputOutput.print_to_out(action, "same_line");
		return false;
		
	}
	
	public String print_format(Person[] player, int current_player) {
		String format = "Player "+String.valueOf(current_player+1)+"\t"+String.valueOf(((Player)player[current_player]).getLast_dice())+"\t"
				+String.valueOf(((Player)player[current_player]).getInstant_square())+"\t"+String.valueOf(player[0].getBudget())+"\t"+
				String.valueOf(player[1].getBudget())+"\t";
		return format;
	}
	
	
		
	
	public Square[] getSquares() {
		return squares;
	}
	public void setSquares(Square[] squares) {
		this.squares = squares;
	}
	public Person[] getPeople() {
		return people;
	}
	public void setPeople(Person[] people) {
		this.people = people;
	}
	public Card[] getChance_deck() {
		return chance_deck;
	}
	public void setChance_deck(Card[] chance_deck) {
		this.chance_deck = chance_deck;
	}
	public Card[] getChest_deck() {
		return chest_deck;
	}
	public void setChest_deck(Card[] chest_deck) {
		this.chest_deck = chest_deck;
	}

}
