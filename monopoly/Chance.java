
import java.util.ArrayList;

public class Chance extends Card{
	
	public Chance(int id, ArrayList<String> action) {
		super(id, action);
	}
	
	@Override
	public String do_the_square_command(Person[] player, int current_player) {
		// TODO Auto-generated method stub
		if (getTop_of_deck() == 0) {
			advance_to_go(player, current_player);
		}else if (getTop_of_deck() == 1) {
			go_to_Leicester(player, current_player);
		}else if (getTop_of_deck() == 2) {
			go_back_three_space(player, current_player);
		}else if (getTop_of_deck() == 3) {
			pay_card(player, current_player, 15);
		}else if (getTop_of_deck() == 4) {
			collect_card(player, current_player, 150);
		}else if (getTop_of_deck() == 5) {
			collect_card(player, current_player, 100);
		}	
		String action= " draw "+getAction().get(getTop_of_deck());
		
		if (getTop_of_deck() != 5) 
			setTop_of_deck(getTop_of_deck()+1);
		else
			setTop_of_deck(0);
		
		return action;
	}
	
	public void go_to_Leicester(Person[] player, int current_player) {
		if (((Player) player[current_player]).getInstant_square() == 37) {
			player[current_player].take_charge(200);
			player[2].pay_dept(200);
		}
		((Player) player[current_player]).setInstant_square(27);
	}
	
	public void go_back_three_space(Person[] player, int current_player) {		
		((Player) player[current_player]).setInstant_square(((Player) player[current_player]).getInstant_square()-3);
	}

}
