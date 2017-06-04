
//This class is designed to get a list of movie recommendations based on similarities between raters.

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.function.Predicate;

public class FourthRatings {
	
	/*
	Dot product is a measure of similarity between two numeric vectors.
	The way it is measured here takes into consideration the fact, that the middle rating values 
	don't add up to the real similarity as much as extreme values do (which is only partially true,
	but let's not delve into statistics). It also consider the fact that
	there is an essential difference between ratings that are below 5.5 (rather negative) 
	and above 5.5 (rather positive).
	*/
	private double dotProduct (Rater me, Rater r) {
		double dotP = 0.0;
		double midPoint = 5.5; //Twitter ratings are from 0 to 10 but 0 means that a user didn't watch, so the middle point between 1 and 10 is 5.5
		ArrayList<String> myRatings = me.getItemsRated();
		for (String s : myRatings) {
			double rr = r.getRating(s);
			if (r.hasRating(s) && rr != 0.0) {dotP += (me.getRating(s) - midPoint)*(rr - midPoint);}
		}
		
		return dotP;
	}
	
	//This method returns a list of similarity measures between one rater and others in database
	private ArrayList<Rating> getSimilarities (String myID) {
		Rater me = RaterDatabase.getRater(myID);
		ArrayList<Rating> simils = new ArrayList<Rating>();
		
		for (Rater r : RaterDatabase.getRaters()) {
			String rID = r.getID();
			if (!rID.equals(myID)) {
				double dot = dotProduct(me, r);
				if (dot > 0.0) {simils.add(new Rating(rID, dot));}
			}
		}
		Collections.sort(simils, Collections.reverseOrder());
		
		return simils;
	}
	
	/*
	The method below return a list of recommended movies based on ratings of specified 
	number of other, most similar users and also based on a filtering method we can specify 
	(e.g. only comedies released on 2000 or later). It also takes into consideration only those
	movies that were rated by at least some specified number of similar raters.
	*/
	public ArrayList<Rating> getSimilarRatingsWithTester 
		(String id, int numSimilarRaters, int minimalRaters, Predicate<String> tester) {
		Rater me = RaterDatabase.getRater(id);
		ArrayList<Rating> recommes = new ArrayList<Rating>();
		ArrayList<String> watched = me.getItemsRated();
		HashMap<String, ArrayList<Double>> unwatched = new HashMap<String, ArrayList<Double>>();
		ArrayList<Rating> simils = getSimilarities(id);
		simils.subList(numSimilarRaters, simils.size()).clear();
		for (Rating r : simils) {
			String rID = r.getItem();
			double weight = r.getValue();
			Rater rtr = RaterDatabase.getRater(rID);
			ArrayList<String> items = rtr.getItemsRated();
			for (String mov : items) {
				if (!watched.contains(mov) && tester.test(mov)){
					ArrayList<Double> al;
					if (unwatched.containsKey(mov)) {
						al = unwatched.get(mov);
					}
					else {al = new ArrayList<Double>();}
					al.add(weight*rtr.getRating(mov));
					unwatched.put(mov, al);
				}
			}
		}
		for (String mID : unwatched.keySet()) {
			ArrayList<Double> vals = unwatched.get(mID);
			if (vals.size() >= minimalRaters) {
				Double weightedSum = vals.stream().reduce(0.0, Double::sum);
				recommes.add(new Rating(mID, weightedSum / vals.size()));
			}
		}
		Collections.sort(recommes, Collections.reverseOrder());
		
		return recommes;
	}

}
