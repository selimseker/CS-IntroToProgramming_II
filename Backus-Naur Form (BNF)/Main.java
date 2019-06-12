
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;

public class Main{

    public static void main(String [ ] args) {
    	HashMap<String, ArrayList<ArrayList<String>>> non_terminal_data= new HashMap<>();    	
		
    	String key;
		ArrayList<ArrayList<String>> possible_list = new ArrayList<ArrayList<String>>();

		ArrayList<String> one_possibility = new ArrayList<String>();
		String value;    	
		ArrayList<String> lines = read_file(args[0]);
		for (String line : lines) {
			
    		key = line.split("->")[0];
    		value = String.valueOf(line.split("->")[1]);
    		
    		
    		for (String possibility : value.split("\\|")) {
    			for (int i = 0; i < possibility.length(); i++) {
					one_possibility.add(String.valueOf(possibility.charAt(i)));
				}
    			possible_list.add(one_possibility);
    			one_possibility = new ArrayList<String>();
			}
    		non_terminal_data.put(key, possible_list);
    		possible_list = new ArrayList<ArrayList<String>>();
    	
    	}

    	String lis = "S";
    	lis = recursive_method(non_terminal_data, lis, 0);
        	
		System.out.println(lis);
		
		
    }
    
    /////////////// RECURSION: BEGIN ///////////////

    public static String recursive_method(HashMap<String, ArrayList<ArrayList<String>>> non_terminal, String lis, int counter) {
    	if (counter == lis.length()-1 && lis.length()!=1) {
			return lis;
		}else {
			if (non_terminal.get(String.valueOf(lis.charAt(counter))) == null ){
				counter++;
				return recursive_method(non_terminal, lis, counter);
			}else {
				String to_terminal = "(";
				for (ArrayList<String> possibility : non_terminal.get(String.valueOf(lis.charAt(counter)))) {
					for (String letter : possibility) {
						to_terminal = to_terminal + letter;
					}
					to_terminal = to_terminal + "|";
				}
				to_terminal = to_terminal.substring(0, to_terminal.length()-1) + ")";
				lis = lis.replaceFirst(String.valueOf(lis.charAt(counter)), to_terminal);
				counter++;
				return recursive_method(non_terminal, lis, counter);
			}
		}
    }    
    /////////////// RECURSION: END   ///////////////
    
    
    //Helper method
    public static ArrayList<String> read_file(String file_name){
		ArrayList<String> lines = new ArrayList<String>();
    	try {
			RandomAccessFile file = new RandomAccessFile(file_name, "r");
			String str;
			while ((str = file.readLine()) != null) {
				if(!str.equals("")){
				lines.add(str);
				}
			}			
			file.close();
		}catch (IOException e) {
			System.out.println("File could not opened!");
			System.exit(0);
		}
		return lines;
    }
}
