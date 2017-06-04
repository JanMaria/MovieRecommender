//This class stores data about movie ratings in a HashMap.

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.*;
import org.apache.commons.csv.*;

public class RaterDatabase {
    private static HashMap<String,Rater> ourRaters;

    private static void initialize() {
 		if (ourRaters == null) {
 			ourRaters= new HashMap<String,Rater>();
 			addRatingsFromURL();
 		}
 	}	
    
    private static void addRatingsFromURL() {
    	initialize();
    	String url = "https://raw.githubusercontent.com/sidooms/MovieTweetings/master/latest/ratings.dat";
    	Scanner scanner;
    	try{
    		URL twitters = new URL(url);
    		scanner = new Scanner(new BufferedReader(new InputStreamReader(twitters.openStream())));
    		while (scanner.hasNextLine()) {
    			String [] rating = scanner.nextLine().split("::");
    			addRaterRating(rating[0], rating[1], Double.parseDouble(rating[2]));
    		}
    	} catch (IOException ex) {
    		throw new RuntimeException(ex);
    	}
    	scanner.close();
    }
    
    
 	
    public static void addRatings(String filename) {
        initialize(); 
        CSVParser csvp;
        try {
        BufferedReader br = new BufferedReader(new FileReader(filename));
        csvp = new CSVParser(br, CSVFormat.DEFAULT.withFirstRecordAsHeader());
        } catch (IOException ex) {
        	throw new RuntimeException(ex);
        }
        for(CSVRecord rec : csvp) {
                String id = rec.get("rater_id");
                String item = rec.get("movie_id");
                String rating = rec.get("rating");
                addRaterRating(id,item,Double.parseDouble(rating));
        } 
        
        try {
        csvp.close();
        } catch (IOException ex) {
        	throw new RuntimeException(ex);
        }
        
    }
    
    public static void addRaterRating(String raterID, String movieID, double rating) {
        initialize(); 
        Rater rater =  null;
                if (ourRaters.containsKey(raterID)) {
                    rater = ourRaters.get(raterID); 
                } 
                else { 
                    rater = new EfficientRater(raterID);
                    ourRaters.put(raterID,rater);
                 }
                 rater.addRating(movieID,rating);
    } 
	         
    public static Rater getRater(String id) {
    	initialize();
    	
    	return ourRaters.get(id);
    }
    
    public static ArrayList<Rater> getRaters() {
    	initialize();
    	ArrayList<Rater> list = new ArrayList<Rater>(ourRaters.values());
    	
    	return list;
    }
 
    public static int size() {
	    return ourRaters.size();
    }
    
    
        
}