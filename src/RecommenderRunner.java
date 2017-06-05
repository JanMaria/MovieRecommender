import java.util.ArrayList;

public class RecommenderRunner {
	private static boolean HTML_OUT = false; //Whether or not to print results as an HTML table.
	
	public static void main (String[] args) {
		printSimilarRatings();		
	}
	
	private static void printSimilarRatings () {
		FourthRatings fr = new FourthRatings();
		String id = "42"; //ID of a user for whom we want to get recommendations
		int numSimilarRaters = 500; //A number of users with highest similarity measures that will be taken into consideration when creating recommendation list
		int minimalRaters = 50; //Minimal number of raters that rated particular movie
		int printSize = 15; //Number of recommendations to print
		ArrayList<Rating> recommes = fr.getSimilarRatingsWithTester(id, numSimilarRaters, minimalRaters, t -> true);
		if (recommes.size() < printSize) {printSize = recommes.size();}
		if (printSize==0) {System.out.println("No movies to recommend found. "
				+ "You have very exceptional taste!");
		} else {
			if (!HTML_OUT) {
				if (printSize < 15) {
					System.out.println("Small number of recomendations. You have very exceptional taste!");
				}
				for (int i = 0; i < printSize; ++i) {
					String movID = recommes.get(i).getItem();
					System.out.println((i + 1) + ". \"" + MovieDatabase.getTitle(movID) + "\"\tYear: "
							+ MovieDatabase.getYear(movID) + "\tGenres: " + MovieDatabase.getGenres(movID));
				} 
			} else {
				System.out.print(
						"<HTML>"
							+ "<style>"
								+ "table {border-collapse: collapse;}"
								+ "table,th,td {border:2px solid #233237;}"
								+ "th{background-color: #233237;"
								+ "color: #EAC67A;"
								+ "padding: 10px;"
								+ "text-align: center;}"
								+ "tr {background-color: #984B43;"
								+ "color: #18121E;}"
								+ "td {padding: 5px;}"
						+ "</style>"
								+ "<body><dev>"
									+ "<table>"
										+ "<tr>"
											+ "<th>#</th>"
											+ "<th>Title</th>"
											+ "<th>Year</th>"
											+ "<th>Genre(s)</th>"
										+ "</tr>"
									);
				for (int k = 0; k < printSize; ++k) {
					System.out.println(
								"<tr>"
									+ "<td>"+(k+1)+"</td>"
									+ "<td>"+MovieDatabase.getTitle(recommes.get(k).getItem())+"</td>"
									+ "<td>"+MovieDatabase.getYear(recommes.get(k).getItem())+"</td>"
									+ "<td>"+MovieDatabase.getGenres(recommes.get(k).getItem())+"</td>"
								+ "</tr>"
							);
				}
					System.out.print("</table></dev></body></HTML>");
			}
		}
	}

}
