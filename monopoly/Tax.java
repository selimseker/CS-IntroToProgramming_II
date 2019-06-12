
public class Tax extends Square{
	@Override
	public String do_the_square_command(Person[] player, int current_player) {
		player[current_player].pay_dept(100);
		player[2].take_charge(100);
		String action = " paid Tax";
		return action;
	}
	public Tax(int id) {
		super(id);
	}

}
