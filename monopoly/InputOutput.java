
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.io.Reader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public class InputOutput{
		
	public HashMap<String, ArrayList<String>> readCards(){	
		ArrayList<String> chance_action_list = new ArrayList<String>();
		ArrayList<String> chest_action_list = new ArrayList<String>();
		HashMap<String, ArrayList<String>> cards = new HashMap<String, ArrayList<String>>();
		
		JSONParser parser = new JSONParser();
		try {
			Reader file = new FileReader("list.json");
			JSONObject object = (JSONObject) parser.parse(file);
			
			JSONArray chance_array = (JSONArray) object.get("chanceList");
			for (Object chance_object : chance_array) {
				chance_action_list.add((String) ((JSONObject)chance_object).get("item"));
			}
			
			JSONArray chest_array = (JSONArray) object.get("communityChestList");
			for (Object chest_object : chest_array) {
				chest_action_list.add((String) ((JSONObject)chest_object).get("item"));
			}
			
			cards.put("chance", chance_action_list);
			cards.put("community_chest", chest_action_list);
			file.close();
			
		}catch(IOException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		return cards;
	}
	
	public ArrayList<Properties> readProperty() {
		Properties square;
		ArrayList<Properties> readed_squares = new ArrayList<Properties>();
		
        JSONParser parser = new JSONParser();        
        try {
        	Reader reader = new FileReader("property.json");
            JSONObject jsonObject = (JSONObject) parser.parse(reader);
            
            JSONArray lands = (JSONArray) jsonObject.get("1");
            Iterator<JSONObject> itr = lands.iterator();
            while (itr.hasNext()) {
				JSONObject land_object = (JSONObject) itr.next();
				square = new Land(String.valueOf(land_object.get("name")), Integer.valueOf(String.valueOf(land_object.get("id"))), Integer.valueOf(String.valueOf(land_object.get("cost"))));
				readed_squares.add(square);
            }

            JSONArray railroads = (JSONArray) jsonObject.get("2");
            itr = railroads.iterator();
            while (itr.hasNext()) {
				JSONObject railroad_object = (JSONObject) itr.next();
				square = new Railroad(String.valueOf(railroad_object.get("name")), Integer.valueOf(String.valueOf(railroad_object.get("id"))), Integer.valueOf(String.valueOf(railroad_object.get("cost"))));
				readed_squares.add(square);
            }
            
            
            JSONArray companies = (JSONArray) jsonObject.get("3");
            itr = companies.iterator();
            while (itr.hasNext()) {
				JSONObject company_object= (JSONObject) itr.next();
				square = new Railroad(String.valueOf(company_object.get("name")), Integer.valueOf(String.valueOf(company_object.get("id"))), Integer.valueOf(String.valueOf(company_object.get("cost"))));
				readed_squares.add(square);
            }
            

        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        

        return readed_squares;
    
	}
	
	public static void print_to_out(String format, String print_type) {



	    try {
	    	File file = new File("output.txt");
	    	FileWriter fr = new FileWriter(file, true);
	    	BufferedWriter brfile = new BufferedWriter(fr);
	    	
	    	
		    if (print_type.equals("new_line")) {
		    	brfile.write("\n"+format);
		    	System.out.printf("\n%s", format);
			}else {
				brfile.write(format);
			}

		    brfile.close();
		    fr.close();

	    } catch (IOException e) {
			e.printStackTrace();
		}
	    
	}
	
	public static ArrayList<String> readCommands(String fileName){		
		ArrayList<String> lines = new ArrayList<String>();
		try {
			RandomAccessFile file = new RandomAccessFile(fileName, "r");
			String str;
			while ((str = file.readLine()) != null) {
				lines.add(str);
			}			
			file.close();
		}catch (IOException e) {
			System.out.println("File could not opened!");
			return null;
		}
		return lines;
		

	}
	
	
}
