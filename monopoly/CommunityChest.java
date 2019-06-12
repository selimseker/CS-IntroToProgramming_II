
import java.util.ArrayList;

public class CommunityChest extends Card{
	
	
	public CommunityChest(int id, ArrayList<String> action) {
		super(id, action);
	}
	
	@Override
	public String do_the_square_command(Person[] player, int current_player) {	
		if (getTop_of_deck() == 0) {
			advance_to_go(player, current_player);
		}else if (getTop_of_deck() == 1) {
			collect_card(player, current_player, 75);
		}else if (getTop_of_deck() == 2) {
			pay_card(player, current_player, 75);
		}else if (getTop_of_deck() == 3) {
			collect_card(player, current_player, 10);
		}else if (getTop_of_deck() == 4) {
			collect_card(player, current_player, 50);
		}else if (getTop_of_deck() == 5) {
			collect_card(player, current_player, 20);
		}else if (getTop_of_deck() == 6) {
			collect_card(player, current_player, 100);
		}else if (getTop_of_deck() == 7) {
			pay_card(player, current_player, 100);
		}else if (getTop_of_deck() == 8) {
			pay_card(player, current_player, 50);
		}else if (getTop_of_deck() == 9) {
			collect_card(player, current_player, 100);
		}else if (getTop_of_deck() == 10) {
			collect_card(player, current_player, 50);
		}
		
		String action = " draw "+getAction().get(getTop_of_deck());
		
		if (getTop_of_deck() != 5) 
			setTop_of_deck(getTop_of_deck()+1);
		else
			setTop_of_deck(0);
		
		return action;
	}

}
