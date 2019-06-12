
public abstract class Square {
	
	private int square_no;
		
	public Square(int square_no) {
		this.setSquare_no(square_no);
	}
	
	public abstract String do_the_square_command(Person[] player, int current_player);
	


	public int getSquare_no() {
		return square_no;
	}

	public void setSquare_no(int square_no) {
		this.square_no = square_no;
	}
	
	
	
}
