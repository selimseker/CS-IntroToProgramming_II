
public class Go extends Square{

	public Go(int square_no) {
		super(square_no);
	}

	@Override
	public String do_the_square_command(Person[] player, int current_player) {
		player[2].pay_dept(200);
		player[current_player].take_charge(200);
		String action = " is in GO square";
		return action;
	}

}
