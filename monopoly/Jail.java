
public class Jail extends Square{

	public Jail(int square_no) {
		super(square_no);
	}

	@Override
	public String do_the_square_command(Person[] player, int current_player) {
		((Player)player[current_player]).setIn_jail(true);
		String action = " went to jail.";
		return action;
	}
	
	public String one_hand_in_jail(Person[] player, int current_player) {
		((Player) player[current_player]).setJail_counter(((Player) player[current_player]).getJail_counter()+1);
		String action= " in jail (count="+((Player) player[current_player]).getJail_counter()+")";
		
		if (((Player) player[current_player]).getJail_counter() == 3) {
			((Player) player[current_player]).setIn_jail(false);
			((Player) player[current_player]).setJail_counter(0);
		}
		return action;
	}

}
