//This class stores data about movies in a HashMap

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.*;
import java.util.function.Predicate;

public class MovieDatabase {
    private static HashMap<String, Movie> ourMovies;
    
    
    private static void initialize () {
    	if (ourMovies == null) {
    		ourMovies = new HashMap<String,Movie>();
    		loadMoviesFromURL();
    	}
    }

	
    private static void loadMoviesFromURL() {
    	initialize();
    	String url = "https://raw.githubusercontent.com/sidooms/MovieTweetings/master/latest/movies.dat";
    	Scanner scanner;
    	
    	try {
    		URL movs = new URL(url);
    		scanner = new Scanner(new BufferedReader(new InputStreamReader(movs.openStream())));
    		while (scanner.hasNextLine()) {
    			Movie mov = parseMovie(scanner.nextLine());
    			ourMovies.put(mov.getID(), mov);
    		}
    	} catch (IOException ex) {
    		throw new RuntimeException(ex);
    	}
    	scanner.close();
    }
    
    private static Movie parseMovie (String entry) {
		String [] parse1 = entry.split("::");
		String id = parse1[0].trim();
		String genres = (parse1.length > 2) ? parse1[2].trim().replaceAll("\\|", ", ") : "";
		String [] parse2 = parse1[1].split(" \\(");
		String title = parse2[0].trim();
		String [] parse3 = parse2[1].split("\\)");
		String year = parse3[0].trim();
		
		return new Movie(id, title, year, genres);
		}

    public static boolean containsID(String id) {
        initialize();
        return ourMovies.containsKey(id);
    }

    public static int getYear(String id) {
        initialize();
        return ourMovies.get(id).getYear();
    }

    public static String getGenres(String id) {
        initialize();
        return ourMovies.get(id).getGenres();
    }

    public static String getTitle(String id) {
        initialize();
        return ourMovies.get(id).getTitle();
    }

    public static Movie getMovie(String id) {
        initialize();
        return ourMovies.get(id);
    }

    public static int size() {
        initialize();
    	return ourMovies.size();
    }
    
    public static ArrayList<String> filterByPredicate(Predicate<String> tester) {
    	initialize();
    	ArrayList<String> filteredIDs = new ArrayList<String>();
    	for (String id : ourMovies.keySet()) {
    		if (tester.test(id)) {filteredIDs.add(id);}
    	}
    	
    	return filteredIDs;
    }

}
