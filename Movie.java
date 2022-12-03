/* 
	Class used to store movies for engine. 
*/
public class Movie {
	// Variables
	private String name;	
	private float rating; 
	private int[] genre;
	
	// Constructor
	public Movie(String name, String[] genre_string) {
		this.name = name;
		this.rating = 0;
		this.genre = stringToInt(genre_string);
	}
	
	
	// Converts string array with genres into integer array
	public int[] stringToInt(String[] array) {
		int[] ret = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
		
		for (int i = 0; i < array.length; i++) {
			switch (array[i]) {
				case "action": 		ret[0] = 1; break;
				case "adventure": 	ret[1] = 1; break;
				case "animation": 	ret[2] = 1; break;
				case "comedy":		ret[3] = 1; break;
				case "crime":		ret[4] = 1; break;
				case "drama": 		ret[5] = 1; break;
				case "family":		ret[6] = 1; break;
				case "fantasy":		ret[7] = 1; break;
				case "horror":		ret[8] = 1; break;
				case "musical": 	ret[9] = 1; break;
				case "mystery": 	ret[10] = 1; break;
				case "romance": 	ret[11] = 1; break;
				case "sci-fi": 		ret[12] = 1; break;
				default: System.out.println("Genre error: " + name + ": " + array[i]); break;
			}
		}
		
		return ret;
	}
	
	// Getters and setters
	public String getName() {
		return name;
	}
	
	public int[] getGenre() {
		return genre;
	}
	
	public float getRating() {
		return rating;
	}
	
	public void setRating(float rating) {
		this.rating = rating;
	}
	
	// toString for printing
	public String toString() {
		return String.format("    %.2f:  %s", rating, name);
	}
}