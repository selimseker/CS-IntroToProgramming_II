
import java.util.ArrayList;

public abstract class Card extends Square{
	private ArrayList<String> actions;
	private int top_of_deck;
	
	public Card(int id, ArrayList<String> action) {
		super(id);
		this.actions = action;
	}
	
	public void advance_to_go(Person[] player, int current_player) {
		player[current_player].take_charge(200);
		((Player) player[current_player]).setInstant_square(1);
		player[2].pay_dept(200);
	}
	
	
	public void pay_card(Person[] player, int current_player, int amount) {
		player[current_player].pay_dept(amount);
		player[2].take_charge(amount);;
	}
	
	public void collect_card(Person[] player, int current_player, int amount) {
		player[current_player].take_charge(amount);
		player[2].pay_dept(amount);
	}
	
	public int getTop_of_deck() {
		return top_of_deck;
	}

	public void setTop_of_deck(int top_of_deck) {
		this.top_of_deck = top_of_deck;
	}

	public ArrayList<String> getAction() {
		return actions;
	}

	public void setAction(ArrayList<String> action) {
		this.actions = action;
	}
	
	
	
	
	
	

}
