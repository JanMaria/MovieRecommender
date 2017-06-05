//This is a class that deliver a few instances of tests that can be used to filter movies in 
//database to better suit our wishes, including two methods to join different filters.   

import java.util.function.Predicate;
import java.util.Arrays;
import java.util.List;

public class Tester {
	
	//This method tests if particular movie was released in a specified year or later.
	public static Predicate<String> yearsAfterTester (int year) {
		return s -> MovieDatabase.getYear(s) >= year;
	}
	
	//This method tests if a movie falls into secified genre.
	public static Predicate<String> genreTester (String genre) {
		String newGen = genre.substring(0,1).toUpperCase() + genre.substring(1).toLowerCase();
		return s -> MovieDatabase.getGenres(s).contains(newGen);
	}
	
	//This method always evaluate to true.
	public static Predicate<String> trueTester() {
		return s -> true;
	}
	
	//Next two methods enable user to merge more than one filter.
	public static Predicate<String> customTesters (List<Predicate<String>> list) {
		return s -> list.stream().allMatch(t -> t.test(s));
	}
	
	@SafeVarargs
	public static Predicate<String> customTesters(Predicate<String>... predicates) {
		return s -> Arrays.stream(predicates).allMatch(t -> t.test(s));
	}

}
