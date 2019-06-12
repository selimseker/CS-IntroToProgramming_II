
public class GoToJail extends Square{

	public GoToJail(int square_no) {
		super(square_no);
	}

	@Override
	public String do_the_square_command(Person[] player, int current_player) {
		((Player)player[current_player]).setInstant_square(11);
		return "";
				
	}

}
