
public class FreePark extends Square{

	public FreePark(int square_no) {
		super(square_no);
	}

	@Override
	public String do_the_square_command(Person[] player, int current_player) {
		String action = " is in Free Parking";
		return action;
	}

}
